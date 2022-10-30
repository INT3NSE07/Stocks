package io;

import constants.CSVConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import model.Portfolio;
import model.Stock;

public class PortfolioCSVReaderImpl implements IReader<Portfolio, String, Stock> {

  @Override
  public Portfolio read(InputStream inputStream, Function<Iterable<String>, Stock> mapper)
      throws IOException {
    Portfolio portfolio = new Portfolio();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      reader.lines().skip(1).forEach(line -> {
        List<String> stockData = Arrays.asList(line.split(CSVConstants.SEPARATOR));

        Stock stock = mapper.apply(stockData);
//        Stock stock = Stock.StockBuilder.create()
//            .setSymbol(stockData.get(0))
//            .setQuantity(Double.parseDouble(stockData.get(1)))
//            .setDate(stockData.get(2));
        portfolio.getStocks().add(stock);
      });
    }

    return portfolio;
  }

//  @Override
//  public Portfolio read(InputStream inputStream, Function<Iterable<String>, Stock> mapper)
//      throws IOException {
//    return null;
//  }
}
