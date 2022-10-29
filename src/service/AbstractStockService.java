package service;

import csv.ICSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import model.Stock;

public abstract class AbstractStockService implements IStockService {

  private final ICSVReader reader;

  public AbstractStockService(ICSVReader reader) {
    this.reader = reader;
  }

  protected abstract InputStream getInputStream(String symbol) throws IOException;

  @Override
  public Stock getStock(String symbol, double quantity) {
    String currentDate = ZonedDateTime.now(ZoneOffset.systemDefault())
        .format(DateTimeFormatter.ISO_LOCAL_DATE);

    Stock stock = getStockOnDate(symbol, currentDate);
    stock = stock.setQuantity(quantity);

    return stock;
  }

  @Override
  public Stock getStockOnDate(String symbol, String date) {
    List<List<String>> response;

    try {
      response = getResponse(this.getInputStream(symbol));
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("Error occurred while processing response for %s on %s", symbol, date));
    }

    List<Stock> stocks = new ArrayList<>();
    Function<List<String>, Stock> mapper = getTimeSeriesResponseMapper(symbol);

    for (List<String> record : response) {
      stocks.add(mapToStock(record, mapper));
    }

    Predicate<Stock> predicate = stock -> stock.getDate().equals(date);
    List<Stock> filteredStocks = stocks.stream().filter(predicate).collect(Collectors.toList());

    if (filteredStocks.size() == 0) {
      throw new IllegalArgumentException(
          String.format("No price data found for %s on %s", symbol, date));
    }

    return filteredStocks.get(0);
  }

  private List<List<String>> getResponse(InputStream inputStream) throws IOException {
    List<List<String>> response;

    Stream<List<String>> lines = reader.readRecords(inputStream).stream().skip(1);
    response = lines.collect(Collectors.toList());

    return response;
  }

  private Stock mapToStock(List<String> record, Function<List<String>, Stock> mapper) {
    return mapper.apply(record);
  }

  private Function<List<String>, Stock> getTimeSeriesResponseMapper(String symbol) {
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
