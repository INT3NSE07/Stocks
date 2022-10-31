package service;

import io.IReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
  public Stock getStock(String symbol, double quantity) {
    String currentDate = DateUtils.getCurrentDate();
    currentDate = "2022-10-28";

    Stock stock = getStockOnDate(symbol, currentDate);
    stock = stock.setQuantity(quantity);

    return stock;
  }

  @Override
  public Stock getStockOnDate(String symbol, String date) {
    List<List<String>> response;

    try {
      response = this.reader.read(this.getInputStream(symbol));
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("Error occurred while processing response for %s on %s", symbol, date));
    }

    List<Stock> stocks = new ArrayList<>();
    Function<List<String>, Stock> mapper = getResponseToStockMapper();

    for (List<String> record : response) {
      Stock stock = mapper.apply(record);
      if (StringUtils.IsNullOrWhiteSpace(stock.getSymbol())) {
        stock.setSymbol(symbol);
      }

      stocks.add(stock);
    }

    Predicate<Stock> predicate = stock -> stock.getSymbol().equals(symbol) && stock.getDate()
        .equals(date);
    List<Stock> filteredStocks = stocks.stream().filter(predicate).collect(Collectors.toList());

    if (filteredStocks.size() == 0) {
      throw new IllegalArgumentException(
          String.format("No price data found for %s on %s", symbol, date));
    }

    return filteredStocks.get(0);
  }
}
