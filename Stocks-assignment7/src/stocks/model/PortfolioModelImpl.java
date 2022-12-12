package stocks.model;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import stocks.portfolio.FlexiblePortfolio;
import stocks.portfolio.IPortfolioVisitor;
import stocks.portfolio.Portfolio;
import stocks.portfolio.PortfolioImpl;
import stocks.portfolio.RebalancePortfolioVisitor;

/**
 * This class implements the PortfolioModel interface.
 */
public class PortfolioModelImpl implements PortfolioModel {

  List<Portfolio> portfolioList;
  List<String> portfolioNames;

  /**
   * Creates a new PortfolioModel object with portfolio list being initialized as an empty ArrayList
   * of portfolios and portfolio names being initialized to hold names of all the portfolios in
   * portfolio list.
   */
  public PortfolioModelImpl() {
    portfolioList = new ArrayList<>();
    portfolioNames = new ArrayList<>();
  }

  @Override
  public void createInflexiblePortfolioFromFile(String name, String filePath) {
    Portfolio p = new PortfolioImpl(name, filePath);
    if (p.getNumberOfShares("") == 0) {
      throw new RuntimeException("No supported tickers provided. Please try again.");
    }
    portfolioNames.add(name);
    portfolioList.add(p);
  }

  @Override
  public String getValueOfPortfolio(String name, String date) throws RuntimeException,
      ParseException {
    return "$" + getPortfolio(name).getValue(date);
  }

  @Override
  public void createInflexiblePortfolioManually(String name, Map<String, String> stocks) {
    Portfolio p = new PortfolioImpl(name, stocks);
    if (p.getNumberOfShares("") == 0) {
      throw new RuntimeException("No supported tickers provided. Please try again.");
    }
    portfolioList.add(p);
    portfolioNames.add(name);
  }

  public void savePortfolioToFile(String name) {
    getPortfolio(name).savePortfolio();
  }

  @Override
  public void createFlexiblePortfolioFromFile(String name, String filePath,
      String dateOfCreation, Double commission)
      throws IOException {
    Portfolio p = new FlexiblePortfolio(name, filePath, dateOfCreation, commission);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    if (p.getNumberOfShares(LocalDate.now().format(dtf)) == 0) {
      throw new RuntimeException("No supported tickers provided. Please try again.");
    }
    portfolioList.add(p);
    portfolioNames.add(name);
  }

  @Override
  public void createFlexiblePortfolioManually(String name,
      Map<String, Map<String, String>> stocks) {
    Portfolio p = new FlexiblePortfolio(name, stocks);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    if (p.getNumberOfShares(LocalDate.now().format(dtf)) == 0) {
      throw new RuntimeException("No supported tickers provide. Please try again.");
    }
    portfolioList.add(p);
    portfolioNames.add(name);
  }

  @Override
  public void buyStock(String name, Integer shares, String ticker, String date,
      Double commissionFee) throws IOException {
    getPortfolio(name).buyStocks(shares, ticker, date, commissionFee);
  }

  @Override
  public void sellStock(
      String name, Integer shares, String ticker,
      String date, Double commissionFee) throws ParseException {
    getPortfolio(name).sellStocks(shares, ticker, date, commissionFee);
  }

  @Override
  public List<Portfolio> getPortfolioList() {
    return portfolioList;
  }

  @Override
  public List<String> getPortfolioNames() {
    return portfolioNames;
  }

  @Override
  public Portfolio getPortfolio(String name) throws RuntimeException {
    for (Portfolio p : portfolioList) {
      if (name.equals(p.getName())) {
        return p;
      }
    }
    throw new RuntimeException("Portfolio with given name does not exist.");
  }

  @Override
  public String getCostBasis(String name, String date) {
    return getPortfolio(name).costBasis(date).toString();
  }

  @Override
  public Map<LocalDate, Double> performanceOverTime(String name,
      String timeStamp, String startDate,
      String endDate) throws ParseException {
    return getPortfolio(name).portfolioPerformance(timeStamp, startDate, endDate);
  }

  @Override
  public Map<String, String> getComposition(String name, String date) {
    return getPortfolio(name).viewStocks(date);
  }

  @Override
  public void investStocks(String name, Double totalInvestment, Map<String, Double> map,
      String date) {
    getPortfolio(name).investPortfolio(totalInvestment, map, date);
  }

  @Override
  public void dollarCostAveraging(String name, Integer frequency, String startDate,
      String endDate, double totalInvestment,
      Map<String, Double> stocksPercent) {
    if (portfolioNames.contains(name)) {
      getPortfolio(name).dollarCostAverage(frequency, startDate, endDate,
          totalInvestment, stocksPercent);
    } else {
      Map<String, Map<String, String>> stocks = new HashMap<>();
      Portfolio p = new FlexiblePortfolio(name, stocks);
      p.dollarCostAverage(frequency, startDate, endDate, totalInvestment, stocksPercent);
      portfolioNames.add(name);
      portfolioList.add(p);
    }
  }

  @Override
  public void rebalancePortfolio(String name, Map<String, Double> stockWeights, String date) {
    IPortfolioVisitor<Void> visitor = new RebalancePortfolioVisitor<>();
    getPortfolio(name).accept(visitor, name, stockWeights, date);
  }
}
