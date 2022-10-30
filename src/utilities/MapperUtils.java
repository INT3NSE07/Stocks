package utilities;

import java.util.List;
import java.util.function.Function;
import model.Stock;

public final class MapperUtils {

  private MapperUtils() {
  }

  public static Function<List<String>, Stock> getAPIResponseToStockMapper(String symbol) {
    return stockData -> Stock.StockBuilder.create()
        .setSymbol(symbol)
        .setDate(stockData.get(0))
        .setOpen(Double.parseDouble(stockData.get(1)))
        .setHigh(Double.parseDouble(stockData.get(2)))
        .setLow(Double.parseDouble(stockData.get(3)))
        .setClose(Double.parseDouble(stockData.get(4)))
        .setVolume(Double.parseDouble(stockData.get(5)));
  }
}
