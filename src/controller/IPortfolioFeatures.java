package controller;

import java.io.IOException;
import java.util.List;
import utilities.Pair;

/**
 * A features interface with contains the operations supported by various portfolios.
 */
public interface IPortfolioFeatures extends IPortfolioController {

  /**
   * Callback function for GUI elements which invokes the portfolio's create implementation.
   *
   * @param portfolioName name of the portfolio to be created
   * @throws IOException file creation exception from {@link repository.IRepository}
   */
  void createPortfolio(String portfolioName) throws IOException;

  /**
   * Callback function for GUI elements which invokes the portfolio's read implementation.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param date          reads the stock data on the specified date
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void examinePortfolio(String portfolioName, String date) throws IOException;

  /**
   * Callback function for GUI elements which invokes the value of a portfolio implementation.
   *
   * @param portfolioName Name of Portfolio for which value has to be found.
   * @param date          On which specific date the value must be calculated. If None given date
   *                      equates to current date and fetches the latest stock close price in the
   *                      {@link service.IStockService}.
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown
   */
  void valueOfPortfolio(String portfolioName, String date) throws IOException;

  /**
   * Callback function for GUI elements which invokes the portfolio's create transaction
   * implementation.
   *
   * @param commissionFee the commission fee of the transaction
   * @param option        the selected sub-menu option
   * @param portfolioName name of the portfolio to be read from
   * @param stockSymbol   the stock symbol
   * @param quantity      the quantity of stock
   * @param date          the date on which the stock is to be bought/sold
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void createTransaction(String commissionFee, String option, String portfolioName,
      String stockSymbol, String quantity, String date) throws IOException;

  /**
   * Callback function for GUI elements which invokes the portfolio's cost basis implementation.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param date          reads the stock data on the specified date
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void costBasisOfPortfolio(String portfolioName, String date) throws IOException;

  /**
   * Callback function for GUI elements which invokes the portfolio's performance implementation.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param startDate     the date from which the performance is calculated
   * @param endDate       the date till which the performance is calculated
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void performanceOfPortfolio(String portfolioName, String startDate, String endDate)
      throws IOException;

  /**
   * Callback function for GUI elements which invokes the portfolio's apply strategy
   * implementation.
   *
   * @param commissionFee     commission fee for the strategy creation
   * @param strategyOption    the selected sub-menu option
   * @param portfolioName     name of the portofolio
   * @param symbolWeightPairs the symbol, weight pairs
   * @param investment        the total investment
   * @param startDate         start date of the strategy
   * @param endDate           end date of the strategy
   * @param period            the frequency of investments
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void applyStrategy(String commissionFee, Integer strategyOption, String portfolioName,
      List<Pair<String, String>> symbolWeightPairs, String investment, String startDate,
      String endDate, Integer period) throws IOException;
}
