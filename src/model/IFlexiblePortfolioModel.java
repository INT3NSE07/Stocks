package model;

import java.io.IOException;
import java.util.List;
import utilities.Pair;

/**
 * A {@link IFlexiblePortfolioModel} Model interface that has functionalities to create, examine,
 * determine value, make transactions, calculate cost basis, plot performance of a flexible
 * portfolio.
 */
public interface IFlexiblePortfolioModel extends IPortfolioModel {

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
   * @return the list of {@link PortfolioValue} which contains the performance metrics across time
   *                        ranges.
   * @throws IOException If given portfolio name is found but un able to open/read then this
   *                     exception is thrown.
   */
  List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate, String toDate)
      throws IOException;
}
