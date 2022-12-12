package stocks.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import stocks.portfolio.Portfolio;

/**
 * This interface represents the model for the program. It contains operations that a user can
 * perform and is delegated tasks by the controller when the user gives an input that to perform
 * one of these operations.
 */
public interface PortfolioModel {

  /**
   * Creates an inflexible portfolio of stocks from an external file provided by the user.
   * @param name the name of the portfolio being created
   * @param filePath the path of the file where the portfolio information is stored
   * @throws FileNotFoundException when the file path provided does not contain a file
   */
  void createInflexiblePortfolioFromFile(String name, String filePath) throws FileNotFoundException;

  /**
   * Returns monetary value of the specified portfolio at the closing of the specified date.
   * @param name name of portfolio whose value is to be calculated
   * @param date date at which value is to be calculated
   * @return the value of the portfolio
   * @throws RuntimeException when the portfolio does not exist
   */
  String getValueOfPortfolio(String name, String date) throws RuntimeException, ParseException;

  /**
   * Finds and returns a portfolio with the specified name.
   * @param name name of the portfolio to be returned
   * @return the Portfolio object which has the given name
   * @throws RuntimeException when the portfolio does not exist
   */
  Portfolio getPortfolio(String name) throws RuntimeException;

  /**
   * Returns the list of all the portfolios that have been created by the model.
   * @return list of Portfolio objects in the model
   */
  List<Portfolio> getPortfolioList();

  /**
   * Returns the list of the names of all portfolios created by the model.
   * @return list of Portfolio names in the model.
   */
  List<String> getPortfolioNames();

  /**
   * Creates an inflexible portfolio without an external file, or rather creates a portfolio
   * manually by taking in user input of what stocks they want to add and what they want to name it.
   * @param name name of the portfolio to be created
   * @param stocks a map of (Ticker, Number of Shares) for stocks to be added to this portfolio
   */
  void createInflexiblePortfolioManually(String name, Map<String, String> stocks);

  /**
   * Save the portfolio with the given name to an external file.
   * @param name name fo the portfolio to be saved externally
   */
  void savePortfolioToFile(String name);

  /**
   * Create a flexible portfolio of stocks from an external file provided by the user.
   * @param name the name of the portfolio being created
   * @param filePath the path of the file where the portfolio information is stored
   */
  void createFlexiblePortfolioFromFile(String name, String filePath, String dateOfCreation,
                                       Double commission) throws IOException;

  /**
   * Create a flexible portfolio manually.
   * @param name name of the portfolio
   * @param stocks the map containing all the stock history for the portfolio
   */
  void createFlexiblePortfolioManually(String name, Map<String, Map<String, String>> stocks);

  /**
   * Function to buy a stock.
   * @param name name of th eportfolio buying stocks for
   * @param shares number of shares we are buying
   * @param ticker ticker of the stock to buy
   * @param date date of purchase
   * @param commissionFee fee paid while buying the stock
   * @throws IOException when input is not correct
   */
  void buyStock(String name, Integer shares, String ticker, String date,
                Double commissionFee) throws IOException;

  /**
   * Function to sell stock.
   * @param name name of the portfolio selling stocks from
   * @param shares number of stocks to sell
   * @param ticker ticker of stock to sell
   * @param date date of selling
   * @param commissionFee fee paid for the transaction
   * @throws ParseException when input is invalid
   */
  void sellStock(String name, Integer shares, String ticker, String date,
                 Double commissionFee) throws ParseException;

  /**
   * Calculated the cost basis for a portfolio.
   * @param name name of portfolio getting cost basis for
   * @param date date to check value of cost basis on
   * @return the value of the cost basis
   */
  String getCostBasis(String name, String date);

  /**
   * Calculates the performance over time for a portfolio.
   * @param name of the portfolio to check performance for
   * @param timeStamp how often do we want to see performance in the graph
   * @param startDate range start date
   * @param endDate range end date
   * @return a graph showing the graph
   * @throws ParseException when the input is invalid
   */
  Map<LocalDate, Double> performanceOverTime(String name, String timeStamp, String startDate,
                                             String endDate) throws ParseException;

  /**
   * Computes the composition of a portfolio on the specified date.
   * @param name name of the portfolio checking composition for
   * @param date the date checking the portfolio on
   * @return a map containing the ticker and number of shares on the given date
   */
  Map<String, String> getComposition(String name, String date);

  /**
   * Invests a given amount of money into an investment plan of stocks to buy and percentage
   * of money to spend on each stock.
   * @param name name of the portfolio to invest in
   * @param totalInvestment total amount being invested
   * @param map the breakdown of the ticker and the percentage of total amount to invest in it
   * @param date the date of the investment
   */
  void investStocks(String name, Double totalInvestment, Map<String, Double> map, String date);

  void dollarCostAveraging(String name, Integer frequency, String startDate, String endDate,
                           double totalInvestment, Map<String, Double> stocksPercent);

  void rebalancePortfolio(String name, Map<String, Double> stockWeights, String date);
}
