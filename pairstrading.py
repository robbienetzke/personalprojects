import numpy as np
import statsmodels.api as sm
import pandas as pd

import quantopian.optimize as opt
import quantopian.algorithm as algo

def initialize(context):

    ### THIS IS A PAIRS TRADING ALGORITH THAT UTILIZES MEAN REVERTING PROPERTIES OF STATIONARY TIME SERIES. COINTEGRATED ASSESTS,
    ### OR ASSESTS WITH A LINEAR COMBINATION THAT HAS FINITE VARIANCE AND 0 MEAN, ARE TRADED ASSUMING THE SPREAD OF PRICES ARE
    ### MEAN REVERTING 

    #set_slippage(slippage.FixedSlippage(spread=0))
    set_commission(commission.PerTrade(cost=0.001))
    set_symbol_lookup_date('2016-01-01')

    #[('AFL', 'SPGI'), ('AFL', 'V'), ('AXP', 'TROW'), ('BK', 'HIG'), ('BK', 'ICE'), ('C', 'SPGI'), ('C', 'AABA'), ('FITB', 'ALLY'), ('HBAN', 'COF'), ('SPGI', 'TROW'), ('SPGI', 'ICE'), ('PNC', 'MS'), ('TROW', 'ETFC'), ('ZION', 'ALLY'), ('COF', 'GS'), ('AABA', 'MA')]

    #Choose pairs

    context.stock_pairs = [
                           (symbol('ADSK'), symbol('WDAY')),
                           (symbol('ADBE'), symbol('CRM')),
                           (symbol('COHR'), symbol('GLW')),
                           (symbol('WDC'), symbol('AKAM')),
                           (symbol('CHKP'), symbol('FFIV')),
                           (symbol('FNSR'), symbol('AYI'))



                           ]

    context.stocks = symbols('ADSK','WDAY','ADBE','CRM', 'COHR', 'GLW', 'WDC','AKAM','CHKP','FFIV','FNSR','AYI')

    context.num_pairs = len(context.stock_pairs)

    context.lookback = 35 # used for regression
    context.z_window = 35 # used for zscore calculation, must be <= lookback

    context.target_weights = pd.Series(index=context.stocks, data=1/2*context.num_pairs)

    context.spread = np.ndarray((context.num_pairs, 0))
    context.inLong = [False] * context.num_pairs
    context.inShort = [False] * context.num_pairs



    schedule_function(func=check_pair_status, date_rule=date_rules.every_day(), time_rule=time_rules.market_close(minutes=30))

def check_pair_status(context, data):

    prices = data.history(context.stocks, 'price', 35, '1d').iloc[-context.lookback::]

    #print(prices)

    new_spreads = np.ndarray((context.num_pairs, 1))

    for i in range(context.num_pairs):

        (stock_y, stock_x) = context.stock_pairs[i]
        Y = prices[stock_y]
        X = prices[stock_x]

        try:
            #X = sm.add_constant(X)
            model = sm.OLS(Y,X).fit()
            hedge = model.params.values
        except ValueError as e:
            log.debug(e)
            return
        #context.target_weights = get_current_portfolio_weights(context, data)

        new_spreads[i, :] = Y[-1] - hedge * X[-1]



        if context.spread.shape[1] > context.z_window:
            spreads = context.spread[i, -context.z_window:]
            zscore = (spreads[-1] - spreads.mean()) / spreads.std()



            if context.inShort[i] and zscore < 0.0:
                context.target_weights[stock_y] = 0
                context.target_weights[stock_x] = 0

                context.inShort[i] = False
                context.inLong[i] = False

                allocate(context, data)
                return
            if context.inLong[i] and zscore > 0.0:
                context.target_weights[stock_y] = 0
                context.target_weights[stock_x] = 0


                context.inShort[i] = False
                context.inLong[i] = False

                allocate(context, data)
                return


            if zscore < -1.5 and (not context.inLong[i]):

                y_target_shares = 1
                X_target_shares = -hedge
                context.inLong[i] = True
                context.inShort[i] = False

                (y_target_pct, x_target_pct) = computeHoldingsPct(y_target_shares,X_target_shares, Y[-1], X[-1])

                context.target_weights[stock_y] = y_target_pct * (1.0/context.num_pairs)
                context.target_weights[stock_x] = x_target_pct * (1.0/context.num_pairs)

                allocate(context, data)
                return


            if zscore > 1.5 and (not context.inShort[i]):

                y_target_shares = -1
                X_target_shares = hedge
                context.inShort[i] = True
                context.inLong[i] = False

                (y_target_pct, x_target_pct) = computeHoldingsPct( y_target_shares, X_target_shares, Y[-1], X[-1] )

                context.target_weights[stock_y] = y_target_pct * (1.0/context.num_pairs)
                context.target_weights[stock_x] = x_target_pct * (1.0/context.num_pairs)


                allocate(context, data)
                return

    context.spread = np.hstack([context.spread, new_spreads])


def computeHoldingsPct(yShares, xShares, yPrice, xPrice):
    yDol = yShares * yPrice
    xDol = xShares * xPrice
    notionalDol =  abs(yDol) + abs(xDol)
    y_target_pct = yDol / notionalDol
    x_target_pct = xDol / notionalDol
    return (y_target_pct, x_target_pct)

def get_current_portfolio_weights(context, data):
    positions = context.portfolio.positions
    positions_index = pd.Index(positions)
    share_counts = pd.Series(
        index=positions_index,
        data=[positions[asset].amount for asset in positions]
    )

    current_prices = data.current(positions_index, 'price')
    current_weights = share_counts * current_prices / context.portfolio.portfolio_value
    return current_weights.reindex(positions_index.union(context.stocks), fill_value=0.0)



def allocate(context, data):
  ## Max Leveage set to 1X

    objective = opt.TargetWeights(context.target_weights)

    constraints = []
    constraints.append(opt.MaxGrossExposure(1.0))


    algo.order_optimal_portfolio(
        objective=objective,
        constraints=constraints,
    )
