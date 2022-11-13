package service;

import constants.Constants;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import model.Stock;
import utilities.DateUtils;
import utilities.StringUtils;

/**
 * Abstract base class for implementations of {@link IStockService}. This class contains all the
 * method definitions that are common to the concrete implementations of the {@link IStockService}
 * interface.
 */
public abstract class AbstractStockService implements IStockService {

  protected abstract List<Stock> getStocks(String symbol) throws IOException;

  @Override
  public Stock getStockOnDate(String symbol, String date)
      throws IllegalArgumentException, IOException {
    symbol = symbol.toUpperCase();
    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    date = "2022-10-30";

    String finalSymbol = symbol;
    String finalDate = date;

    List<Stock> stocks;
    try {
      stocks = getStocks(symbol);
    } catch (IOException e) {
      throw new IOException(
          String.format(Constants.STOCK_FETCH_FAILED, symbol, date));
    }
    stocks = stocks.stream().filter(x -> x.getSymbol().equals(finalSymbol))
        .collect(Collectors.toList());

    Stock stock = stocks.stream()
        .filter(x -> x.getSymbol().equals(finalSymbol) && x.getDate().equals(finalDate))
        .findFirst()
        .orElse(null);
    if (stock == null) {
      stock = stocks.stream()
          .filter(x -> LocalDate.parse(x.getDate()).isBefore(LocalDate.parse(finalDate)))
          .findFirst().orElse(null);
    }

    if (stock == null) {
      throw new IllegalArgumentException(
          String.format(Constants.NO_STOCK_DATA_FOUND, symbol, date));
    }

    stock.setDate(date);

    return stock;
  }
}
