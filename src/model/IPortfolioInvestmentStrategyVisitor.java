package model;

import java.io.IOException;

public interface IPortfolioInvestmentStrategyVisitor<T> {

  /**
   * Apply an investment strategy to a portfolio.
   *
   * @param portfolioModel     the portfolio on which the strategy is to be applied.
   * @param portfolioName      name of the portfolio to which the strategy is applied
   * @param investmentStrategy the various options which are needed to create the strategy
   * @return the value based on a specific implementation
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  T applyStrategy(FlexiblePortfolioModel portfolioModel,
      String portfolioName,
      InvestmentStrategy investmentStrategy) throws IOException;
}
