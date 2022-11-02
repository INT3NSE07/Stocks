package service;

import io.IReader;
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
import utilities.MapperUtils;

/**
 * This class represents a stock service which loads the stock data by invoking the AlphaVantage
 * REST API. An instance of this class can be obtained by calling
 * {@link AlphaVantageStockService#getInstance(IReader)}
 */
public class AlphaVantageStockService extends AbstractStockService {

  private static AlphaVantageStockService instance;
  private final String apiKey;
  private final String apiEndpoint;

  private AlphaVantageStockService(IReader<List<List<String>>> reader) {
    super(reader);

    // TODO: move to config
    this.apiKey = "7JWGAITR6OPG00LM";
    this.apiEndpoint = "https://www.alphavantage.co/query?";
  }

  /**
   * Returns an instance of {@link AlphaVantageStockService}.
   *
   * @param reader An implementation of {@link IReader} which is used to read and parse the response
   *               from the API
   * @return an instance of {@link AlphaVantageStockService}
   */
  public static AlphaVantageStockService getInstance(IReader<List<List<String>>> reader) {
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
  protected Function<List<String>, Stock> getResponseToStockMapper() {
    return MapperUtils.getAlphaVantageResponseToStockMapper();
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
