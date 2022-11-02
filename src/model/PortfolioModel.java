package model;

import constants.Constants;
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
 *
 */
public class PortfolioModel implements IPortfolioModel {

  private final IRepository<Portfolio> portfolioRepository;
  private final IStockService stockService;

  /**
   * @param portfolioRepository
   * @param stockService
   */
  public PortfolioModel(IRepository<Portfolio> portfolioRepository, IStockService stockService) {
    this.portfolioRepository = portfolioRepository;
    this.stockService = stockService;
  }

  @Override
  public void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException {
    this.validateInput(portFolioName);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portFolioName);

    portfolioRepository.create(portfolio);

    this.addStock(portFolioName, stockPairs);
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

      Stock stock = this.stockService.getStock(symbol, quantity);

      Portfolio portfolio = new Portfolio();
      portfolio.setName(portFolioName);
      portfolio.setStocks(Collections.singletonList(stock));

      this.portfolioRepository.update(portfolio);
    }
  }

  @Override
  public Portfolio readPortfolio(String portFolioName)
      throws IllegalArgumentException, IOException {
    this.validateInput(portFolioName);

    Iterable<Portfolio> portfolios = this.portfolioRepository.read(
        x -> x.getName().equals(portFolioName));

    return portfolios.iterator().next();
  }

  @Override
  public Pair<Portfolio, Double> getPortfolioValueOnDate(String portFolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validateInput(portFolioName);

    if (StringUtils.IsNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }
    this.validateDate(date);

    Portfolio portfolio = this.readPortfolio(portFolioName);
    double value = 0;

    for (Stock stock : portfolio.getStocks()) {
      stock.setClose(this.stockService.getStockOnDate(stock.getSymbol(), date)
          .getClose());

      value += stock.getQuantity() * stock.getClose();
    }

    return new Pair<>(portfolio, value);
  }

  @Override
  public boolean isStockSymbolValid(String symbol) throws IOException {
    this.validateInput(symbol);

    return stockService.isStockSymbolValid(symbol);
  }

  private void validateInput(String input) {
    if (StringUtils.IsNullOrWhiteSpace(input)) {
      throw new IllegalArgumentException(Constants.INPUT_NULL_OR_EMPTY);
    }
  }

  private void validateDate(String date) {
    if (!StringUtils.IsNullOrWhiteSpace(date)) {
      if (!DateUtils.isValidDate(date, Constants.DEFAULT_DATETIME_FORMAT)) {
        throw new IllegalArgumentException(Constants.DATE_INVALID);
      }
    }
  }
}
