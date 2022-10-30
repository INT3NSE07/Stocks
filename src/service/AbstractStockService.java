package service;

import csv.ICSVReader;
import io.IReader;
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
import model.Portfolio;
import model.Stock;
import utilities.DateUtils;
import utilities.StringUtils;

public abstract class AbstractStockService implements IStockService {

  private final IReader<Portfolio, String, Stock> reader;

  public AbstractStockService(IReader<Portfolio, String, Stock> reader) {
    this.reader = reader;
  }

  protected abstract InputStream getInputStream(String symbol) throws IOException;

  protected abstract Function<Iterable<String>, Stock> getResponseToStockMapper(String symbol);

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
    //List<List<String>> response;

    Portfolio response;
    try {
      //response = getResponse(this.getInputStream(symbol));
      response = this.reader.read(this.getInputStream(symbol), getResponseToStockMapper(symbol));
    } catch (IOException e) {
      throw new IllegalArgumentException(
          String.format("Error occurred while processing response for %s on %s", symbol, date));
    }

//    List<Stock> stocks = new ArrayList<>();
//    Function<Iterable<String>, Stock> mapper = getResponseToStockMapper(symbol);
//
//    for (List<String> record : response) {
//      stocks.add(mapToStock(record, mapper));
//    }

    Predicate<Stock> predicate = stock -> stock.getDate().equals(date);
    List<Stock> filteredStocks = response.getStocks().stream().filter(predicate).collect(Collectors.toList());

    if (filteredStocks.size() == 0) {
      throw new IllegalArgumentException(
          String.format("No price data found for %s on %s", symbol, date));
    }

    return filteredStocks.get(0);
  }

//  private List<List<String>> getResponse(InputStream inputStream) throws IOException {
//    List<List<String>> response;
//
//    Stream<List<String>> lines = this.reader.read(inputStream, getResponseToStockMapper(null)).stream().skip(1);
//    response = lines.collect(Collectors.toList());
//
//    return response;
//  }

  private Stock mapToStock(List<String> record, Function<Iterable<String>, Stock> mapper) {
    return mapper.apply(record);
  }
}
