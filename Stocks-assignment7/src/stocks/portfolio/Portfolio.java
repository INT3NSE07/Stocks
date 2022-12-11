package stocks.portfolio;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Map;

/**
 * This interface represents a portfolio of stocks. A portfolio is a collection of stocks for all
 * companies listed on the NASDAQ. It contains the unique "ticker symbol" and the number of shares
 * of that company.
 */

public interface Portfolio {

  /**
   * Prints out all the stocks contained in this portfolio.
   */
  Map<String, String> viewStocks(String date);

  /**
   * Calculates the monetary value of the Portfolio for the given date at closing time using the
   * AlphaVantage API. The total value of the portfolio is the sum of the values of its individual
   * holdings (Value of individual holding = numberOfShares * cost of 1 share at closing time)
   *
   * @param date the date to get the value of the portfolio (must be entered in yyyy-mm-dd)
   * @return total value of the portfolio at the given date at closing time
   */
  Double getValue(String date) throws ParseException;

  /**
   * Returns the name of the Portfolio.
   *
   * @return the name of this Portfolio
   */
  String getName();

  /**
   * Saves the current Portfolio into a CSV file with the portfolios name as the file name to
   * be able to read later.
   */
  void savePortfolio();

  /**
   * Calculates the total number of shares in this portfolio.
   *
   * @return total number of shares in this portfolio
   */
  double getNumberOfShares(String date);

  /**
   * Purchase stocks and add them to the portfolio.
   *
   * @param shares specific number of shares .
   * @param ticker specific stock .
   * @param date   specified date .
   */
  void buyStocks(Integer shares, String ticker, String date, Double commisonFee)
          throws IOException;

  /**
   * Sell stocks from a given portfolio.
   *
   * @param shares specific number of shares.
   * @param ticker of a specific stock.
   * @param date   on a specified date.
   */
  void sellStocks(Integer shares, String ticker, String date, Double commsionFee)
          throws ParseException;

  /**
   * Determine the total amount of money invested in a portfolio, by a specific date.
   * This would include all the purchases made in that portfolio till that date.
   *
   * @param date The Specific date the user needs the cost by.
   * @return Double value of the total cost basis.
   */
  Double costBasis(String date);


  /**
   * This method creates a bar chart based on the portfolio performance over a period of time.
   * The title of the barchart specifies the name of the portfolio (XXX), and the
   * time range (YYY to ZZZ).
   * The bar chart has at least 5 lines but no more than 30 lines.
   *
   * @param timeStamp The scale of the timestamps is decided by the timespan.
   * @param endDate   the last date they want performance for
   * @param startDate the first date they want performance for
   */
  Map<LocalDate, Double> portfolioPerformance(String timeStamp, String startDate,
                                              String endDate) throws ParseException;


  /**
   * This method allows Invest a fixed amount into an existing portfolio containing multiple
   * stocks.
   * using a specified weight for each stock in the portfolio.
   *
   * @param totalInvestment fixed amount invested by the user.
   * @param stocksPercent   a map that contains each ticker and the weight of the stock.
   */
  void investPortfolio(double totalInvestment, Map<String, Double> stocksPercent, String date);

  /**
   * An investor creates a portfolio of stocks and determines their relative proportion.
   * Then the investor invests a fixed amount of money in this portfolio at regular frequency.
   *
   * @param totalInvestment a double value of the total investment money value.
   * @param stocksPercent   a map tha contains the tickers to invest as keys and the percentage as
   *                        values in the map.
   * @param frequency       frequency of the investment specified by the user.
   * @param startDate       Start Date of the investment specified by the user for the time range.
   * @param endDate         End Date of the investment specified by the user, if not passed
   *                        it's considered to be 15 years from now.
   */
  void dollarCostAverage(Integer frequency, String startDate, String endDate,
                         double totalInvestment, Map<String, Double> stocksPercent);

  <T> T accept(IPortfolioVisitor<T> visitor, String portfolioName, Map<String, Double> stockWeights,
               String date);
}
