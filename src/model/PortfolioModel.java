package model;

import constants.Constants;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import repository.IRepository;
import service.IStockService;
import utilities.StringUtils;

public class PortfolioModel implements IPortfolioModel {

  private final IRepository<Portfolio> portfolioRepository;
  private final IStockService stockService;

  public PortfolioModel(IRepository<Portfolio> portfolioRepository, IStockService stockService) {
    this.portfolioRepository = portfolioRepository;
    this.stockService = stockService;
  }

  @Override
  public Portfolio createPortfolio(String portFolioName)
      throws IllegalArgumentException {
    this.validateInput(portFolioName);

    // validate if a portfolio already exists with this name
    if (doesPortfolioExist(portFolioName)) {
      throw new IllegalArgumentException("A portfolio with the same name already exists.");
    }

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portFolioName);

    return portfolioRepository.create(portfolio);
  }

  @Override
  public void addStock(String portFolioName, String ticker, double quantity)
      throws IllegalArgumentException {
    this.validateInput(portFolioName);
    this.validateInput(ticker);

    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity of a stock cannot be negative or zero.");
    }

    Stock stock = this.stockService.getStock(ticker);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(portFolioName);
    portfolio.setStocks(Arrays.asList(stock));

    this.portfolioRepository.update(portfolio);
  }

  @Override
  public Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException {
    this.validateInput(portFolioName);

    if (!doesPortfolioExist(portFolioName)) {
      throw new IllegalArgumentException("A portfolio with this name does not exist.");
    }

    Iterable<Portfolio> portfolios = this.portfolioRepository.read(
        x -> x.getName().equals(portFolioName));

    return portfolios.iterator().next();
  }

  @Override
  public double getPortfolioValueOnDate(String portFolioName, String date)
      throws IllegalArgumentException {
    this.validateInput(portFolioName);
    this.validateInput(date);

    Portfolio portfolio = this.readPortfolio(portFolioName);
    double value = 0;

    for (Stock stock : portfolio.getStocks()) {
      value += this.stockService.getStockOnDate(stock.getSymbol(), date).getClose();
    }

    return value;
  }

  private boolean doesPortfolioExist(String portFolioName) {
    return Files.exists(Paths.get(Constants.DATA_DIR, portFolioName));
  }

  private void validateInput(String input) {
    if (StringUtils.IsNullOrWhiteSpace(input)) {
      throw new IllegalArgumentException("Input cannot be null or empty.");
    }
  }
}
