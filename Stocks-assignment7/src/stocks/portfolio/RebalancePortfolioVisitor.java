package stocks.portfolio;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Map;

/**
 * Rebalances an existing flexible portfolio, given a specific date and the intended weights (by
 * value) of the stocks in that portfolio.
 */
public class RebalancePortfolioVisitor implements IPortfolioVisitor<Void> {

  @Override
  public Void apply(FlexiblePortfolio portfolio, String portfolioName,
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
        if (stockPrice == 0.0) {
          throw new IllegalArgumentException(
              String.format("Stock price for ticker %s not found on %s", ticker, date));
        }

        double numOfStocks = (investment * (stockWeight / 100)) / stockPrice;

        Map<String, String> existingTicker = stocks.get(ticker);

        String closestDate = null;
        if (existingTicker.containsKey(date)) {
          closestDate = date;
        } else {
          LocalDate localDate = LocalDate.parse(date);
          LocalDate lastDate = existingTicker.keySet().stream()
              .map(LocalDate::parse)
              .sorted(Comparator.reverseOrder())
              .filter(x -> x.isBefore(localDate))
              .findFirst()
              .orElse(null);
          if (lastDate != null) {
            closestDate = lastDate.format(DateTimeFormatter.ISO_DATE);
          }
        }
        if (closestDate == null) {
          throw new IllegalArgumentException(String.format("Stock not found on date %s", date));
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
