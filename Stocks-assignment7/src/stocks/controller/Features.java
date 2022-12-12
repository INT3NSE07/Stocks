package stocks.controller;

/**
 * Interface to add features to the program.
 */
public interface Features {

  /**
   * Gives the ability to the user to create a new Flexible Portfolio.
   */
  void createNewFlexiblePortfolio();

  /**
   * Gives the ability to the user to purchase stocks to an existing Flexible Portfolio.
   */
  void buyStocks();

  /**
   * Gives the ability to the user to sell stocks from an existing Flexible Portfolio.
   */
  void sellStocks();

  /**
   * Gives the ability to the user to get the cost basis from an existing Flexible Portfolio.
   * On a specific date.
   */
  void getCostBasis();

  /**
   * Gives the ability to the user to get the value of a portfolio for an existing Portfolio.
   */
  void getValue();

  /**
   * Give the user the ability to save the current portfolio to the same folder the program is.
   * running in.
   */
  void savePortfolio();

  /**
   * To load a portfolio from a file .
   */
  void loadPortfolio();

  /**
   * To exit the program when pressed.
   */
  void exitProgram();

  /**
   * Give the user the ability to view a list of all portfolios created in that session.
   */
  void viewPortfolios();

  /**
   * Give the ability to the user to get the composition of a flexible portfolio.
   */
  void viewComposition();

  /**
   * The ability to invest a specific amount in an existing flexible portfolio on a specific.
   * specifying the weights of how that money should be invested in each stock in that portfolio.
   */
  void investPortfolio();

  /**
   * This feature allows the user to apply a dollar cost averaging strategy to an existing
   * portfolio, or to create a new one with the strategy.
   */
  void dollarCostAveraging();

  /**
   * This feature allows the user create a portfolio and apply dollar cost Averaging at the same
   * time.
   */
  void createWithDollarCostAveraging();


  /**
   * This feature allows the user to view the performance of an existing portfolio.
   */
  void viewPerformance();

  void rebalancePortfolio();

}
