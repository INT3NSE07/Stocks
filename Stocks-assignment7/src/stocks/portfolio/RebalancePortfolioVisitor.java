package stocks.portfolio;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

public class RebalancePortfolioVisitor<T> implements IPortfolioVisitor<T> {

  public RebalancePortfolioVisitor() {

  }

  @Override
  public T apply(FlexiblePortfolio portfolio, String portfolioName,
      Map<String, Double> stockWeights, String date, Map<String, Map<String, String>> stocks)
      throws IllegalArgumentException {
    double weight = stockWeights
        .values()
        .stream()
        .mapToDouble(Double::doubleValue).sum();
    if (weight != 100) {
      throw new IllegalArgumentException(
          "The sum of the weights do not add up to 100%.");
    }

    try {
      double investment = portfolio.getValue(date);
      for (String ticker : stockWeights.keySet()) {
        double stockWeight = stockWeights.get(ticker);
        double stockPrice = portfolio.getCostOfStock(ticker, date);
        double numOfStocks = (investment * (stockWeight / 100)) / stockPrice;

        Map<String, String> existingTicker = stocks.get(ticker);

        String closestDate;
        if (existingTicker.containsKey(date)) {
          closestDate = date;
        } else {
          LocalDate localDate = LocalDate.parse(date);
          closestDate = existingTicker.keySet().stream()
              .filter(x -> LocalDate.parse(x).isBefore(localDate))
              .findFirst().orElse(null);
        }
        if (closestDate == null) {
          throw new IllegalArgumentException(String.format("Stocks not found on date %s", date));
        }

        existingTicker.put(closestDate, Double.toString(numOfStocks));
        stocks.put(ticker, existingTicker);
      }
    } catch (ParseException e) {
      throw new IllegalArgumentException(
          String.format("Failed to read portfolio %s value on date %s", portfolioName, date));
    }

    return null;
  }
}
