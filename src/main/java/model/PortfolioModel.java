package model;

import constants.Constants;
import enums.PortfolioTypes;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.Pair;
import utilities.StringUtils;


/**
 * This class represents the portfolio model. It performs the actual operations.
 */
public class PortfolioModel implements IPortfolioModel {

  protected final IRepository<Portfolio> portfolioRepository;
  protected final IStockService stockService;

  /**
   * Constructs a {@link PortfolioModel} object.
   *
   * @param portfolioRepository the portfolio repository which is used the write the data to the
   *                            actual datastore
   * @param stockService        the stock service which is used to fetch the stocks
   */
  public PortfolioModel(IRepository<Portfolio> portfolioRepository, IStockService stockService) {
    this.portfolioRepository = portfolioRepository;
    this.stockService = stockService;
  }

  @Override
  public void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException {
    this.validateInput(portfolioName);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portfolioName);
    portfolio.setPortfolioType(PortfolioTypes.INFLEXIBLE);

    portfolioRepository.create(portfolio);

    this.addStock(portfolioName, stockPairs);
  }

  private void addStock(String portFolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException {
    this.validateInput(portFolioName);

    // Added for model independence.
    if (stockPairs.stream().anyMatch(x -> x.getKey() == null)) {
      throw new IllegalArgumentException(Constants.INPUT_NULL_OR_EMPTY);
    }

    Map<String, Double> uniqueStockPairs = stockPairs.stream()
        .collect(Collectors.groupingBy(Pair::getKey, Collectors.summingDouble(Pair::getValue)));

    for (String symbol : uniqueStockPairs.keySet()) {
      Double quantity = uniqueStockPairs.get(symbol);

      this.validateInput(symbol);
      if (quantity <= 0) {
        throw new IllegalArgumentException(Constants.QUANTITY_NON_NEGATIVE_AND_ZERO);
      }

      Stock stock = this.stockService.getStockOnDate(symbol, null);
      stock.setQuantity(quantity);

      Portfolio portfolio = new Portfolio();
      portfolio.setName(portFolioName);
      portfolio.setStocks(Collections.singletonList(stock));

      this.portfolioRepository.update(portfolio);
    }
  }

  @Override
  public Portfolio readPortfolio(String portfolioName)
      throws IllegalArgumentException, IOException {
    this.validateInput(portfolioName);

    Iterable<Portfolio> portfolios = this.portfolioRepository.read(
        x -> x.getName().equals(portfolioName));

    return portfolios.iterator().next();
  }

  @Override
  public Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validateInput(portfolioName);

    if (StringUtils.isNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    this.validateDate(date);

    Portfolio portfolio = this.readPortfolio(portfolioName);
    double value = 0;

    for (Stock stock : portfolio.getStocks()) {
      stock.setClose(this.stockService.getStockOnDate(stock.getSymbol(), date)
          .getClose());

      value += stock.getQuantity() * stock.getClose();
    }

    return new Pair<>(portfolio, value);
  }

  @Override
  public boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException {
    this.validateInput(symbol);

    try {
      Stock stock = stockService.getStockOnDate(symbol, null);
    } catch (IllegalArgumentException e) {
      return false;
    }

    return true;
  }

  protected void validateInput(String input) {
    if (StringUtils.isNullOrWhiteSpace(input)) {
      throw new IllegalArgumentException(Constants.INPUT_NULL_OR_EMPTY);
    }
  }

  protected void validateDate(String date) {
    if (!StringUtils.isNullOrWhiteSpace(date)) {
      if (!DateUtils.isValidDate(date, Constants.DEFAULT_DATETIME_FORMAT)) {
        throw new IllegalArgumentException(Constants.DATE_INVALID);
      }
    }
  }
}
