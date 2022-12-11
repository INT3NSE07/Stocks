package stocks.portfolio;

import java.util.Map;

/**
 * This interface represents a portfolio visitor.
 *
 * @param <T> the type of the portfolio
 */
public interface IPortfolioVisitor<T> {

  T apply(FlexiblePortfolio portfolio, String portfolioName, Map<String, Double> stockWeights,
      String date, Map<String, Map<String, String>> stocks);
}
