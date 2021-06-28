import pandas as pd
import numpy as np
from sklearn.ensemble import RandomForestClassifier

## @author Robbie Netzke
## This code will not work outside of the QuantConnect IDE,
## but all trade logic is present in this script.
## Every month, this script fits a random forest model to classify SPY returns.
## Inspired by the findings of Imandoust and Bolandraftar (2014)

class RandomForest(QCAlgorithm):

    clf = RandomForestClassifier(n_estimators = 500, random_state = 1)
    model = None
    __time = datetime.min

    def Initialize(self):
        self.SetStartDate(2018, 1, 1)
        self.SetCash(100000)
        self.AddEquity("SPY", Resolution.Daily)
        # self.SetBrokerageModel(AlphaStreamsBrokerageModel())
        self.__isReady = False
        self.Schedule.On(self.DateRules.MonthStart("SPY"), self.TimeRules.At(23, 0), self.Train)
        self.Schedule.On(self.DateRules.EveryDay("SPY"), self.TimeRules.At(9, 30), self.TradeSPY)

    def Train(self):

        qb = self

        history = qb.History(qb.Securities.Keys, 5*360, Resolution.Daily)
        history = history.loc[qb.Securities["SPY"].Symbol]

        history['L14'] = history['low'].rolling(window=14).min()
        history['H14'] = history['high'].rolling(window=14).max()
        history['%K'] = 100*((history['close'] - history['L14']) / (history['H14'] - history['L14']) )
        history = history.dropna()[["close","%K","volume"]]

        y = np.where(history["close"].shift(-1) > history["close"], 1, -1)
        X = history[["%K"]]

        self.model = self.clf.fit(X, y)

        self.__isReady = True



    def TradeSPY(self):

        if not self.__isReady:
            return

        qb = self

        history = qb.History(qb.Securities.Keys, 15, Resolution.Daily)
        history = history.loc[qb.Securities["SPY"].Symbol]

        history['L14'] = history['low'].rolling(window=14).min()
        history['H14'] = history['high'].rolling(window=14).max()
        history['%K'] = 100*((history['close'] - history['L14']) / (history['H14'] - history['L14']) )
        history = history.dropna()[["close","%K","volume"]]

        self.Log(history)

        currentKpct = history["%K"][-1]
        currentVolume = history["volume"][-1]

        if self.model.predict([[currentKpct]]) > 0:
            if not self.Portfolio.Invested:
                self.SetHoldings("SPY", 1.0)

        else:
            self.Liquidate()
