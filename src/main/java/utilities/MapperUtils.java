package utilities;

import enums.Operations;
import java.util.List;
import java.util.function.Function;
import model.Stock;

/**
 * A utility class that contains object mappers.
 */
public final class MapperUtils {

  private MapperUtils() {
  }

  /**
   * Returns a mapping function which maps the API response from
   * {@link service.AlphaVantageStockService} to a {@link Stock}.
   *
   * @return a mapping function
   */
  public static Function<List<String>, Stock> getAlphaVantageResponseToStockMapper() {
    return stockData -> Stock.StockBuilder.create()
        .setDate(stockData.get(0))
        .setOpen(Double.parseDouble(stockData.get(1)))
        .setHigh(Double.parseDouble(stockData.get(2)))
        .setLow(Double.parseDouble(stockData.get(3)))
        .setClose(Double.parseDouble(stockData.get(4)))
        .setVolume(Double.parseDouble(stockData.get(5)));
  }

  /**
   * Returns a mapping function which maps the response from {@link service.FileStockService} to a
   * {@link Stock}.
   *
   * @return a mapping function
   */
  public static Function<List<String>, Stock> getCSVFileToStockMapper() {
    return stockData -> Stock.StockBuilder.create()
        .setDate(stockData.get(0))
        .setSymbol(stockData.get(1))
        .setOpen(Double.parseDouble(stockData.get(2)))
        .setHigh(Double.parseDouble(stockData.get(3)))
        .setLow(Double.parseDouble(stockData.get(4)))
        .setClose(Double.parseDouble(stockData.get(5)))
        .setVolume(Double.parseDouble(stockData.get(6)));
  }

  /**
   * Returns a mapping function which maps the stock data stored in {@link model.Portfolio} to a
   * {@link Stock}.
   *
   * @return a mapping function
   */
  public static Function<List<String>, Stock> getUserPortfolioToStockMapper() {
    //
    return stockData -> Stock.StockBuilder.create()
        .setSymbol(stockData.get(0))
        .setQuantity(Double.parseDouble(stockData.get(1)))
        .setDate(stockData.get(2))
        .setOperation(Operations.getOperationByValue(stockData.get(3)))
        .setCommission(Double.parseDouble(stockData.get(4)));
  }
}
