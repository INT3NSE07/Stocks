package utilities;

import enums.Operations;
import enums.PortfolioTypes;
import enums.StrategyTypes;
import java.util.List;
import java.util.function.Function;
import model.Portfolio;
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
    return stockData -> {
      Stock stock = Stock.StockBuilder.create()
          .setSymbol(stockData.get(0))
          .setQuantity(Double.parseDouble(stockData.get(1)))
          .setDate(stockData.get(2));
      if (stockData.size() > 3) {
        stock.setOperation(Operations.getOperationByValue(stockData.get(3)));
        stock.setCommission(Double.parseDouble(stockData.get(4)));
      }
      if (stockData.size() > 5) {
        stock.setWeight(Double.parseDouble(stockData.get(5)));
        stock.setStrategyName(stockData.get(6));
        stock.setStrategyType(StrategyTypes.getStrategyTypeByValue(stockData.get(7)));
        stock.setStrategyInvestment(Double.parseDouble(stockData.get(8)));
        stock.setStrategyEndDate(stockData.get(9));
        stock.setStrategyPeriod(Integer.parseInt(stockData.get(10)));
      }

      return stock;
    };
  }

  public static Function<List<String>, Portfolio> getCSVFileToPortfolioMapper() {
    return portfolioData -> {
      Portfolio portfolio = new Portfolio();
      portfolio.setName(portfolioData.get(0));
      portfolio.setPortfolioType(PortfolioTypes.getPortfolioTypeByValue(portfolioData.get(1)));

      return portfolio;
    };
  }
}
