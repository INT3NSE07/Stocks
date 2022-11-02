package service;

import java.io.IOException;
import model.Stock;

/**
 * This interface represents a stock service which is used to fetch stock data from various sources.
 * It has the following constraints:
 * <ul>
 *   <li>If the latest stock data is not available, it uses the last available data</li>
 *   <li>The stock symbol must belong to the list of stocks in S&P 500</li>
 * </ul>
 */
public interface IStockService {

  /**
   * Fetches the stock data based on the specified stock symbol and quantity.
   *
   * @param symbol   the stock symbol used to fetch the stock data
   * @param quantity the quantity of stocks to be bought
   * @return a {@link Stock} object containing the corresponding stock data
   * @throws IllegalArgumentException if the specified stock symbol does not exist
   * @throws IOException              if the fetching of stock data failure of network or I/O
   *                                  operations
   */
  Stock getStock(String symbol, double quantity) throws IllegalArgumentException, IOException;

  /**
   * Fetches the stock data based on the specified stock symbol and date.
   *
   * @param symbol the stock symbol used to fetch the stock data
   * @param date   the date for which the stock data is to be fetched
   * @return a {@link Stock} object containing the corresponding stock data
   * @throws IllegalArgumentException if the specified stock symbol does not exist
   * @throws IOException              if the fetching of stock data failure of network or I/O
   *                                  operations
   */
  Stock getStockOnDate(String symbol, String date) throws IllegalArgumentException, IOException;

  /**
   * Checks if the specified stock symbol is valid.
   *
   * @param symbol the stock symbol to be checked
   * @return true if the specified stock symbol is valid, else false
   * @throws IOException if the fetching of stock data failure of network or I/O operation
   */
  boolean isStockSymbolValid(String symbol) throws IOException;
}
