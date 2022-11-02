package model;

import java.io.IOException;
import java.util.List;
import utilities.Pair;

/**
 * A {@link Portfolio} Model interface that has functionalities to
 *              create {@link Portfolio},
 *              read {@link Portfolio},
 *              Get Value on a date for a {@link Portfolio},
 *              Validating Ticker Symbol.
 */
public interface IPortfolioModel {

  /**
   * Creates {@link Portfolio} and persists the data the File System.
   * This will Validate Input by not accepting null or empty as arguments.
   *
   * @param portFolioName Name of the Portfolio to be created.
   * @param stockPairs List of {@link Pair} objects with symbol and quantity to be added to
   *                    the Portfolio.
   * @throws IllegalArgumentException When given portFolioName argument is null or empty then
   *                                  an IllegalArgumentException is thrown.
   * @throws IOException Java Files creation Exception coming from {@link repository.IRepository}
   */
  void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException;

  /**
   * Reads the Stock data from the persisted Portfolio.
   *
   * @param portFolioName Name of the portfolio to read from.
   * @return an {@link Portfolio} Object having stock details.
   * @throws IllegalArgumentException If the portfolio name is null or empty or service is
   *                          unable to find the name this exception is thrown.
   * @throws IOException If given portfolio name is found but un able to open/read then
   *                      this exception is thrown.
   */
  Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException, IOException;

  /**
   * Finds the value of port folio on a given date if the date is not given then it sets current
   *                  date to find each stock close value in Portfolio.
   *
   * @param portFolioName Name of Portfolio for which value has to be found.
   * @param date On which specific date the value must be calculated. If None given date equates to
   *                        current date and fetches the
   *                                 latest stock close price in the {@link service.IStockService}.
   * @return A {@link Pair} object of Portfolio mapped to its own total value.
   * @throws IllegalArgumentException If the portfolio name is null or empty or service is
   *                                        unable to find the name this exception is thrown.
   * @throws IOException If given portfolio name is found but un able to open/read then
   *                            this exception is thrown
   */
  Pair<Portfolio, Double> getPortfolioValueOnDate(String portFolioName, String date)
      throws IllegalArgumentException, IOException;

  /**
   * Checks if the stock symbol is found from the underlying {@link service.IStockService}
   *                  and returns a {@link Boolean} value accordingly.
   *
   * @param symbol Ticker/Stock Symbol to be checked.
   * @return True if found, false else wise.
   * @throws IOException Exception from underlying {@link service.IStockService} as reading from
   *                          a file can throw Exception.
   * @throws IllegalArgumentException If the Ticker symbol is Empty or Null
   *                                            then this Exception is thrown.
   */
  boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException;
}
