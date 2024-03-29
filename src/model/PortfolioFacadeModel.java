package model;

import constants.Constants;
import enums.PortfolioTypes;
import enums.StrategyTypes;
import java.io.IOException;
import java.util.List;
import repository.IRepository;
import utilities.Pair;

/**
 * An implementation of {@link IPortfolioFacadeModel} model interface that encompasses the
 * functionalities of various types of portfolios which can be created in the system.
 */
public class PortfolioFacadeModel implements IPortfolioFacadeModel {

  private final IFlexiblePortfolioModel flexiblePortfolioModel;
  private final IPortfolioModel portfolioModel;
  private final IRepository<Portfolio> repository;
  private PortfolioTypes portfolioType;

  /**
   * Creates an {@link PortfolioFacadeModel} object. The models of the various portfolios supported
   * by the system are initialized.
   *
   * @param flexiblePortfolioModel the flexible portfolio model
   * @param portfolioModel         the inflexible portfolio model
   * @param repository             the portfolio repository which is used to write the data to the
   *                               actual datastore
   */
  public PortfolioFacadeModel(IFlexiblePortfolioModel flexiblePortfolioModel,
      IPortfolioModel portfolioModel, IRepository<Portfolio> repository) {
    this.flexiblePortfolioModel = flexiblePortfolioModel;
    this.portfolioModel = portfolioModel;
    this.repository = repository;
  }

  @Override
  public void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException {
    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.createPortfolio(portfolioName, stockPairs);
    } else if (this.portfolioType == PortfolioTypes.INFLEXIBLE) {
      this.portfolioModel.createPortfolio(portfolioName, stockPairs);
    } else {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public Portfolio readPortfolio(String portfolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      return this.flexiblePortfolioModel.readPortfolio(portfolioName, date);
    } else if (this.portfolioType == PortfolioTypes.INFLEXIBLE) {
      return this.portfolioModel.readPortfolio(portfolioName);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
      throws IllegalArgumentException, IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      return this.flexiblePortfolioModel.getPortfolioValueOnDate(portfolioName, date);
    } else if (this.portfolioType == PortfolioTypes.INFLEXIBLE) {
      return this.portfolioModel.getPortfolioValueOnDate(portfolioName, date);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public void buyStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.buyStock(portfolioName, stockPair, date, commission);
    } else {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      this.flexiblePortfolioModel.sellStock(portfolioName, stockPair, date, commission);
    } else {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public double getCostBasis(String portfolioName, String date)
      throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      return this.flexiblePortfolioModel.getCostBasis(portfolioName, date);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate,
      String toDate) throws IOException {
    this.validatePortfolioType(portfolioName);

    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      return this.flexiblePortfolioModel.getPerformanceOverview(portfolioName, fromDate, toDate);
    }
    throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
  }

  @Override
  public boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException {
    return this.portfolioModel.isStockSymbolValid(symbol);
  }

  @Override
  public void createInvestmentStrategy(String portfolioName, InvestmentStrategy investmentStrategy)
      throws IOException {
    IPortfolioInvestmentStrategyVisitor<Void> visitor = null;
    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      if (investmentStrategy.getStrategyType() == StrategyTypes.FIXED_AMOUNT) {
        visitor = new FixedCostVisitor<>(true);
      } else if (investmentStrategy.getStrategyType() == StrategyTypes.DOLLAR_COST_AVERAGING) {
        visitor = new DollarCostAveragingVisitor<>(true);
      }
      this.flexiblePortfolioModel.acceptInvestmentStrategy(visitor, portfolioName,
          investmentStrategy);
    } else {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public void applyInvestmentStrategy(String portfolioName, InvestmentStrategy investmentStrategy)
      throws IOException {
    this.validatePortfolioType(portfolioName);

    IPortfolioInvestmentStrategyVisitor<Void> visitor = null;
    if (this.portfolioType == PortfolioTypes.FLEXIBLE) {
      if (investmentStrategy.getStrategyType() == StrategyTypes.FIXED_AMOUNT) {
        visitor = new FixedCostVisitor<>(false);
      } else if (investmentStrategy.getStrategyType() == StrategyTypes.DOLLAR_COST_AVERAGING) {
        visitor = new DollarCostAveragingVisitor<>(false);
      }
      this.flexiblePortfolioModel.acceptInvestmentStrategy(visitor, portfolioName,
          investmentStrategy);
    } else {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }

  @Override
  public PortfolioTypes getPortfolioType() {
    return this.portfolioType;
  }

  @Override
  public void setPortfolioType(PortfolioTypes portfolioType) {
    this.portfolioType = portfolioType;
  }

  private PortfolioTypes readPortfolioType(String portFolioName) throws IOException {
    Portfolio portfolio = this.repository.read(x -> x.getName().equals(portFolioName)).iterator()
        .next();

    return portfolio.getPortfolioType();
  }

  private void validatePortfolioType(String portfolioName) throws IOException {
    if (this.portfolioType == null || this.portfolioType != this.readPortfolioType(portfolioName)) {
      throw new IllegalArgumentException(Constants.INVALID_PORTFOLIO_TYPE);
    }
  }
}
