package service;

import java.io.IOException;
import java.util.List;
import model.Stock;

/**
 * This interface represents a stock service which is used to fetch stock data from various sources.
 * It has the following constraints:
 * <ul>
 *   <li>If the latest stock data is not available, it uses the latest available data</li>
 * </ul>
 */
public interface IStockService {

  List<Stock> getHistoricalStockData(String symbol) throws IllegalArgumentException, IOException;

  /**
   * Fetches the stock data based on the specified stock symbol and date.
   *
   * @param symbol the stock symbol used to fetch the stock data
   * @param date   the date for which the stock data is to be fetched. If null, the current date is
   *               used
   * @return a {@link Stock} object containing the corresponding stock data
   * @throws IllegalArgumentException if the specified stock symbol does not exist
   * @throws IOException              if the fetching of stock data failure of network or I/O
   *                                  operations
   */
  Stock getStockOnDate(String symbol, String date) throws IllegalArgumentException, IOException;
}
