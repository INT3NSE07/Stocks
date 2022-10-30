package service;

import csv.ICSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import model.Stock;

public class AlphaVantageStockService extends AbstractStockService {

  private static AlphaVantageStockService instance;
  private final String apiKey;
  private final String apiEndpoint;

  private AlphaVantageStockService(ICSVReader reader) {
    super(reader);

    // TODO: move to config
    this.apiKey = "7JWGAITR6OPG00LM";
    this.apiEndpoint = "https://www.alphavantage.co/query?";
  }

  public static AlphaVantageStockService getInstance(ICSVReader reader) {
    if (instance == null) {
      instance = new AlphaVantageStockService(reader);
    }

    return instance;
  }

  @Override
  protected InputStream getInputStream(String symbol) throws IOException {
    return buildURL(symbol).openStream();
  }

  @Override
  protected Function<List<String>, Stock> getResponseToStockMapper(String symbol) {
    return stockData -> Stock.StockBuilder.create()
        .setSymbol(symbol)
        .setDate(stockData.get(0))
        .setOpen(Double.parseDouble(stockData.get(1)))
        .setHigh(Double.parseDouble(stockData.get(2)))
        .setLow(Double.parseDouble(stockData.get(3)))
        .setClose(Double.parseDouble(stockData.get(4)))
        .setVolume(Double.parseDouble(stockData.get(5)));
  }

  private URL buildURL(String symbol) {
    URL url;

    Map<String, Object> queryParams = Map.of(
        "symbol", symbol,
        "function", "TIME_SERIES_DAILY",
        "outputsize", "full",
        "apikey", this.apiKey,
        "datatype", "csv"
    );

    StringBuilder sb = new StringBuilder();
    for (Map.Entry<?, ?> entry : queryParams.entrySet()) {
      if (sb.length() > 0) {
        sb.append("&");
      }
      sb.append(String.format("%s=%s",
          URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8),
          URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8)
      ));
    }

    sb.insert(0, this.apiEndpoint);

    try {
      url = new URL(sb.toString());
    } catch (MalformedURLException e) {
      throw new RuntimeException("The AlphaVantage API endpoint is invalid.");
    }

    return url;
  }
}
