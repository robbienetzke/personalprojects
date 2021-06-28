## @author Robbie Netzke
## Algorithm will not run outside of QuantConnect IDE.
## Net Current Asset Value is a metric that describes a firm's financial standing.
## Companies with high NCAV are said to be undervalued (Investopedia). This algorithm
## attempts to find such undervalued securities.


class NCAV(QCAlgorithm):

    __ncav = 1.5

    def Initialize(self):
        self.SetStartDate(2017,6,15)
        self.SetEndDate(2018,7,15)
        self.SetCash(1000000)
        self.UniverseSettings.Resolution = Resolution.Daily
        self.filteredFine = None
        self.filteredCoarse = None
        self.AddUniverse(self.CoarseSelectionFunction,self.FineSelectionFunction)
        self.SetRiskManagement(MaximumDrawdownPercentPerSecurity(0.2))
        self.AddEquity("SPY", Resolution.Daily)
        self.Schedule.On(self.DateRules.MonthStart("SPY"), self.TimeRules.At(23, 0), self.Rebalance)
        self.__months = -1
        self.__rebalance = False
        self.__ncav = float(self.GetParameter("NCAV"))

    def CoarseSelectionFunction(self, coarse):

        if self.__rebalance:
            # drop stocks which have no fundamental data or have low price
            self.filteredCoarse = [stock.Symbol for stock in coarse if (stock.HasFundamentalData)
                                    and (float(stock.AdjustedPrice) > 5)]
            return self.filteredCoarse

        else:
            return []

    def FineSelectionFunction(self, fine):

        if self.__rebalance:
            fine = [stock for stock in fine if (float(stock.FinancialStatements.BalanceSheet.CurrentAssets.Value) > 0)
                                    and (float(stock.EarningReports.BasicAverageShares.Value) > 0)
                                    and (float(stock.FinancialStatements.BalanceSheet.CurrentLiabilities.Value) > 0)
                                    and (float(stock.FinancialStatements.BalanceSheet.TotalNonCurrentLiabilitiesNetMinorityInterest.Value) > 0)
                                    and (stock.CompanyReference.IndustryTemplateCode!="B")
                                    and (stock.CompanyReference.IndustryTemplateCode!="I")]
            for stock in fine:
                #calculates the net current asset value per share
                totalLiabilities = float(stock.FinancialStatements.BalanceSheet.CurrentLiabilities.Value)+float(stock.FinancialStatements.BalanceSheet.TotalNonCurrentLiabilitiesNetMinorityInterest.Value)
                stock.ncav = (float(stock.FinancialStatements.BalanceSheet.CurrentAssets.Value) - totalLiabilities)/float(stock.EarningReports.BasicAverageShares.Value)

            self.filteredFine = [stock.Symbol for stock in fine if (stock.ncav > self.__ncav)]
            return self.filteredFine

        else:
            return []

    def Rebalance(self):

        self.__months += 1

        if self.__months % 12 == 0:
            self.__rebalance = True

    def OnData(self, data):

        if not self.__rebalance: return

        if self.filteredFine:
            currentPorfolio = [stock.Key for stock in self.Portfolio]
            for stock in currentPorfolio:

                if stock not in self.filteredFine:
                    self.Liquidate(stock)

                elif stock in self.filteredFine:
                    self.SetHoldings(stock, 1/len(self.filteredFine))

            self.__rebalance = False
