package service;

import constants.Constants;
import io.IReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Stock;
import utilities.DateUtils;
import utilities.StringUtils;

/**
 *
 */
public abstract class AbstractStockService implements IStockService {

  private final IReader<List<List<String>>> reader;

  public AbstractStockService(IReader<List<List<String>>> reader) {
    this.reader = reader;
  }

  protected abstract InputStream getInputStream(String symbol) throws IOException;

  protected abstract Function<List<String>, Stock> getResponseToStockMapper();

  @Override
  public Stock getStock(String symbol, double quantity)
      throws IllegalArgumentException, IOException {
    String currentDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    currentDate = "2022-10-28";

    Stock stock = getStockOnDate(symbol, currentDate);
    stock = stock.setQuantity(quantity);

    return stock;
  }

  @Override
  public Stock getStockOnDate(String symbol, String date)
      throws IllegalArgumentException, IOException {
    List<List<String>> response = getStockData(symbol, date);

    List<Stock> stocks = new ArrayList<>();
    Function<List<String>, Stock> mapper = getResponseToStockMapper();

    for (List<String> record : response) {
      Stock stock = mapper.apply(record);
      if (StringUtils.IsNullOrWhiteSpace(stock.getSymbol())) {
        stock.setSymbol(symbol);
      }

      stocks.add(stock);
    }

    Stock stock;
    Predicate<Stock> symbolPredicate = x -> x.getSymbol().equals(symbol);
    List<Stock> stockWithMatchedSymbol = stocks.stream().filter(symbolPredicate).collect(
        Collectors.toList());

    stock = stockWithMatchedSymbol.stream().filter(x -> x.getDate().equals(date)).findFirst()
        .orElseGet(() -> stockWithMatchedSymbol.stream().findFirst().orElse(null));

    if (stock == null) {
      throw new IllegalArgumentException(
          String.format("No stock data found for %s on %s", symbol, date));
    }

    return stock;
  }

  @Override
  public boolean isStockSymbolValid(String symbol) throws IOException {
    try {
      Stock stock = getStockOnDate(symbol,
          DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT));
    } catch (IllegalArgumentException e) {
      return false;
    }

    return true;
  }

  private List<List<String>> getStockData(String symbol, String date) throws IOException {
    List<List<String>> stockData;

    try {
      stockData = this.reader.read(this.getInputStream(symbol));
    } catch (IOException e) {
      throw new IOException(
          String.format("Error occurred while fetching stock data for symbol: %s on date: %s",
              symbol, date));
    }

    return stockData;
  }
}
