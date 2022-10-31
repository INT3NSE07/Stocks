package utilities;

import java.util.List;
import java.util.function.Function;
import model.Stock;

public final class MapperUtils {

  private MapperUtils() {
  }

  public static Function<List<String>, Stock> getAlphaVantageResponseToStockMapper() {
    return stockData -> Stock.StockBuilder.create()
        .setDate(stockData.get(0))
        .setOpen(Double.parseDouble(stockData.get(1)))
        .setHigh(Double.parseDouble(stockData.get(2)))
        .setLow(Double.parseDouble(stockData.get(3)))
        .setClose(Double.parseDouble(stockData.get(4)))
        .setVolume(Double.parseDouble(stockData.get(5)));
  }

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

  public static Function<List<String>, Stock> getUserPortfolioToStockMapper() {
    return stockData -> Stock.StockBuilder.create()
        .setSymbol(stockData.get(0))
        .setQuantity(Double.parseDouble(stockData.get(1)))
        .setDate(stockData.get(2));
  }
}
