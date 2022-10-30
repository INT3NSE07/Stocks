package model;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import repository.IRepository;
import service.IStockService;
import utilities.DateUtils;
import utilities.StringUtils;

public class PortfolioModel implements IPortfolioModel {

  private final IRepository<Portfolio> portfolioRepository;
  private final IStockService stockService;

  public PortfolioModel(IRepository<Portfolio> portfolioRepository, IStockService stockService) {
    this.portfolioRepository = portfolioRepository;
    this.stockService = stockService;
  }

  @Override
  public void createPortfolio(String portFolioName, Map<String, Double> stockSymbolQuantityMap)
      throws IllegalArgumentException {
    this.validateInput(portFolioName);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portFolioName);

    portfolioRepository.create(portfolio);

    this.addStock(portFolioName, stockSymbolQuantityMap);
  }

  private void addStock(String portFolioName, Map<String, Double> stockSymbolQuantityMap)
      throws IllegalArgumentException {
    this.validateInput(portFolioName);

    for (String symbol : stockSymbolQuantityMap.keySet()) {
      Double quantity = stockSymbolQuantityMap.get(symbol);

      this.validateInput(symbol);
      if (quantity <= 0) {
        throw new IllegalArgumentException("Quantity of a stock cannot be negative or zero.");
      }

      Stock stock = this.stockService.getStock(symbol, quantity);

      Portfolio portfolio = new Portfolio();
      portfolio.setName(portFolioName);
      portfolio.setStocks(Collections.singletonList(stock));

      this.portfolioRepository.update(portfolio);
    }
  }

  @Override
  public Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException {
    this.validateInput(portFolioName);

    try {
      Iterable<Portfolio> portfolios = this.portfolioRepository.read(
          x -> x.getName().equals(portFolioName));

      return portfolios.iterator().next();
    } catch (IOException e) {

    }

    return null;
  }

  @Override
  public double getPortfolioValueOnDate(String portFolioName, String date)
      throws IllegalArgumentException {
    this.validateInput(portFolioName);

    if (StringUtils.IsNullOrWhiteSpace(date)) {
      date = DateUtils.getCurrentDate();
    }
    this.validateDate(date);

    Portfolio portfolio = this.readPortfolio(portFolioName);
    double value = 0;

    for (Stock stock : portfolio.getStocks()) {
      value += this.stockService.getStockOnDate(stock.getSymbol(), date).getClose();
    }

    return value;
  }

  private void validateInput(String input) {
    if (StringUtils.IsNullOrWhiteSpace(input)) {
      throw new IllegalArgumentException("Input cannot be null or empty.");
    }
  }

  private void validateDate(String date) {
    if (!StringUtils.IsNullOrWhiteSpace(date)) {
      if (!DateUtils.isValidDate(date)) {
        throw new IllegalArgumentException("Date is invalid.");
      }
    }
  }
}
