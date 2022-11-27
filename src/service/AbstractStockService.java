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
  public List<Stock> getHistoricalStockData(String symbol)
      throws IllegalArgumentException, IOException {
    symbol = symbol.toUpperCase();

    String finalSymbol = symbol;
    List<Stock> stocks;
    try {
      stocks = getStocks(symbol);
    } catch (IOException e) {
      throw new IOException(
          String.format(Constants.STOCK_FETCH_FAILED, symbol, ""));
    }
    stocks = stocks.stream().filter(x -> x.getSymbol().equals(finalSymbol))
        .collect(Collectors.toList());

    if (stocks.isEmpty()) {
      throw new IllegalArgumentException(
          String.format(Constants.NO_STOCK_DATA_FOUND, symbol, ""));
    }

    return stocks;
  }

  @Override
  public Stock getStockOnDate(String symbol, String date)
      throws IllegalArgumentException, IOException {
    symbol = symbol.toUpperCase();
    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }

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

  @Override
  public boolean isTradingDay(String date)
      throws IllegalArgumentException, IOException {
    String defaultSymbol = "AAPL";

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    if (LocalDate.parse(date)
        .isAfter(LocalDate.parse(DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT)))) {
      throw new IllegalArgumentException(
          String.format(Constants.NO_STOCK_DATA_FOUND, defaultSymbol, date));
    }

    List<Stock> stocks;
    try {
      stocks = getStocks(defaultSymbol);
    } catch (IOException e) {
      throw new IOException(
          String.format(Constants.STOCK_FETCH_FAILED, defaultSymbol, date));
    }

    String finalDate = date;
    Stock stock = stocks.stream()
        .filter(x -> x.getSymbol().equals(defaultSymbol) && x.getDate().equals(finalDate))
        .findFirst()
        .orElse(null);

    return stock != null;
  }
}
