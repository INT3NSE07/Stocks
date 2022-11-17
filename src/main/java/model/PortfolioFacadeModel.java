package model;

import constants.Constants;
import enums.PortfolioTypes;
import java.io.IOException;
import java.util.List;
import repository.IRepository;
import utilities.Pair;

public class PortfolioFacadeModel implements IPortfolioFacadeModel {

  private final IFlexiblePortfolioModel flexiblePortfolioModel;
  private final IPortfolioModel portfolioModel;
  private final IRepository<Portfolio> repository;
  private PortfolioTypes portfolioType;

  public PortfolioFacadeModel(IFlexiblePortfolioModel flexiblePortfolioModel,
      IPortfolioModel portfolioModel, IRepository<Portfolio> repository) {
    this.flexiblePortfolioModel = flexiblePortfolioModel;
    this.portfolioModel = portfolioModel;
    this.repository = repository;
  }

  @Override
  public void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException {
    switch (this.portfolioType) {
      case FLEXIBLE:
        this.flexiblePortfolioModel.createPortfolio(portfolioName, stockPairs);
        break;
      case INFLEXIBLE:
        this.portfolioModel.createPortfolio(portfolioName, stockPairs);
        break;
      default:
        throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public Portfolio readPortfolio(String portfolioName)
      throws IllegalArgumentException, IOException {
    this.validatePortfolioType(portfolioName);

    return this.portfolioModel.readPortfolio(portfolioName);
  }

  @Override
  public Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validatePortfolioType(portfolioName);

    switch (this.portfolioType) {
      case FLEXIBLE:
        return this.flexiblePortfolioModel.getPortfolioValueOnDate(portfolioName, date);
      case INFLEXIBLE:
        return this.portfolioModel.getPortfolioValueOnDate(portfolioName, date);
      default:
        throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public void buyStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.buyStock(portfolioName, stockPair, date, commission);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.sellStock(portfolioName, stockPair, date, commission);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public Pair<Portfolio, Double> getCostBasis(String portfolioName, String date)
      throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.getCostBasis(portfolioName, date);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate,
      String toDate) throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.getPerformanceOverview(portfolioName, fromDate, toDate);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException {
    return this.portfolioModel.isStockSymbolValid(symbol);
  }

  @Override
  public PortfolioTypes getPortfolioType() {
    return this.portfolioType;
  }

  @Override
  public void setPortfolioType(PortfolioTypes portfolioType) {
    this.portfolioType = portfolioType;
  }

  private PortfolioTypes getPortfolioType(String portFolioName) throws IOException {
    Portfolio portfolio = this.repository.read(x -> x.getName().equals(portFolioName)).iterator()
        .next();

    return portfolio.getPortfolioType();
  }

  private void validatePortfolioType(String portfolioName) throws IOException {
    if (this.portfolioType == null || this.portfolioType != this.getPortfolioType(portfolioName)) {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }
}
