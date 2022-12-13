package stocks.portfolio;

import java.util.Map;

/**
 * This interface represents a portfolio visitor. It applies an operation to a specific portfolio.
 *
 * @param <T> the return type of the result
 */
public interface IPortfolioVisitor<T> {

  T apply(FlexiblePortfolio portfolio, String portfolioName, Map<String, Double> stockWeights,
      String date, Map<String, Map<String, String>> stocks) throws IllegalArgumentException;
}
