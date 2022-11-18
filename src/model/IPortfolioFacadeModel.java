package model;

import enums.PortfolioTypes;
import java.io.IOException;
import java.util.List;
import utilities.Pair;

/**
 * A {@link IPortfolioFacadeModel} Model interface that encompasses the functionalities of various
 * types of portfolios which can be created in the system.
 */
public interface IPortfolioFacadeModel {

  /**
   * Creates {@link Portfolio} and persists the data the File System. This will Validate Input by
   * not accepting null or empty as arguments.
   *
   * @param portfolioName Name of the Portfolio to be created.
   * @param stockPairs    List of {@link Pair} objects with symbol and quantity to be added to the
   *                      Portfolio.
   * @throws IllegalArgumentException When given portFolioName argument is null or empty then an
   *                                  IllegalArgumentException is thrown.
   * @throws IOException              Java Files creation Exception coming from
   *                                  {@link repository.IRepository}
   */
  void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException;

  /**
   * Reads the Stock data from the persisted flexible portfolio.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param date          reads the stock data on the specified date
   * @return an {@link Portfolio} Object having stock details.
   * @throws IllegalArgumentException If the portfolio name is null or empty or service is unable to
   *                                  find the name this exception is thrown.
   * @throws IOException              If given portfolio name is found but un able to open/read then
   *                                  this exception is thrown.
   */
  Portfolio readPortfolio(String portfolioName, String date)
      throws IllegalArgumentException, IOException;

  /**
   * Reads the Stock data from the persisted Portfolio.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param date          the date on which the stock is bought
   * @return an {@link Portfolio} Object having stock details.
   * @throws IllegalArgumentException If the portfolio name is null or empty or service is unable to
   *                                  find the name this exception is thrown.
   * @throws IOException              If given portfolio name is found but un able to open/read then
   *                                  this exception is thrown.
   */
  Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
      throws IllegalArgumentException, IOException;

  /**
   * Buys a stock when given a valid portfolio name, stock pair, date and commission. Fetches the
   * stock and its closing price from {@link service.AlphaVantageStockService} and writes into ths
   * portfolio object with operation performed on it.
   *
   * @param portfolioName Name of the portfolio in which the bought stocks will be added.
   * @param stockPair     {@link Pair} objects with symbol and quantity to be added to the
   *                      Portfolio.
   * @param date          the date on which the stock is bought
   * @param commission    the broker commission
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void buyStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException;

  /**
   * Sells a stock when given a valid portfolio name, stock pair, date and commission. Fetches the
   * stock and its closing price from {@link service.AlphaVantageStockService} and writes into ths
   * portfolio object with operation performed on it.
   *
   * @param portfolioName Name of the portfolio in which the stocks will be sold.
   * @param stockPair     {@link Pair} objects with symbol and quantity to be added to the
   *                      Portfolio.   * @param date the date on which the stock is sold
   * @param date          the date on which the stock is bought
   * @param commission    the broker commission
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException;

  /**
   * Calculates the total value that was put in the portfolio in buying the stocks and as commission
   * for each transaction.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param date          the date on which cost basis is calculated
   * @return the calculated cost basis
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  double getCostBasis(String portfolioName, String date) throws IOException;

  /**
   * Draws a bar chart that summarizes the performance of the given portfolio over the given period
   * of time.
   *
   * @param portfolioName Name of the portfolio to read from.
   * @param fromDate      the date from which the performance is calculated
   * @param toDate        the date till which the performance is calculated
   * @return list of {@link PortfolioValue} which contains the performance metrics across ranges.
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate, String toDate)
      throws IOException;

  /**
   * Checks if the stock symbol is found from the underlying {@link service.IStockService} and
   * returns a {@link Boolean} value accordingly.
   *
   * @param symbol Ticker/Stock Symbol to be checked.
   * @return True if found, false else wise.
   * @throws IOException              Exception from underlying {@link service.IStockService} as
   *                                  reading from a file can throw Exception.
   * @throws IllegalArgumentException If the Ticker symbol is Empty or Null then this Exception is
   *                                  thrown.
   */
  boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException;

  /**
   * Returns the type of the portfolio.
   *
   * @return the type of the portfolio
   */
  PortfolioTypes getPortfolioType();

  /**
   * Set the type of the portfolio.
   *
   * @param portfolioType the type of the portfolio
   */
  void setPortfolioType(PortfolioTypes portfolioType);
}
