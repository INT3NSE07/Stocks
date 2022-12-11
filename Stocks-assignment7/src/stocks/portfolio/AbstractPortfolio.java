package stocks.portfolio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * This class represents an AbstractPortfolio and implements the Portfolio interface.
 */
abstract class AbstractPortfolio implements Portfolio {

  private final String name;

  AbstractPortfolio(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the Portfolio.
   *
   * @return the name of this Portfolio
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Helper method that removes tickers from the portfolio that do not exist in the NASDAQ.
   */
  protected abstract void removeInvalidTickers();

  /**
   * Helper method that rounds down fractional shares to the closest integer.
   */
  protected abstract void removeFractionalShares();

  /**
   * Calculates the monetary value of the Portfolio for the given date at closing time using the
   * AlphaVantage API. The total value of the portfolio is the sum of the values of its individual
   * holdings (Value of individual holding = numberOfShares * cost of 1 share at closing time) on
   * that date.
   *
   * @param date the date to get the value of the portfolio
   * @return total value of the portfolio at the given date at closing time
   */
  public Double getValue(String date) throws ParseException {
    Double totalValue = 0.0;
    String apiKey = "RH7EG7YN7BPG9V7Z";
    URL url = null;
    InputStream in = null;
    Map<String, String> stocks = viewStocks(date);
    try {
      for (Map.Entry<String, String> mapEntry : stocks.entrySet()) {
        totalValue += Double.parseDouble(mapEntry.getValue())
            * getCostOfStock(mapEntry.getKey(), date);
        if (totalValue == 0.0 && this.getNumberOfShares(date) > 0) {
          LocalDate today = LocalDate.parse(date);
          return getValue(today.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    return totalValue;
  }

  /**
   * Saves the current Portfolio into a CSV file with the portfolios name as the file name to
   * be able to read later.
   */
  public abstract void savePortfolio();

  protected Double getCostOfStock(String ticker, String date) {
    Double costOfStock = 0.0;
    String apiKey = "RH7EG7YN7BPG9V7Z";
    URL url = null;
    InputStream in = null;
    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
      in = url.openStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String inputLine;
      while ((inputLine = reader.readLine()) != null) {
        String[] values = inputLine.split(",");
        if (values[0].equals(date)) {
          costOfStock += Double.parseDouble(values[4]);
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return costOfStock;
  }

  @Override
  public <T> T accept(IPortfolioVisitor<T> visitor, String portfolioName, Map<String, Double> stockWeights, String date) {
    return null;
  }
}
