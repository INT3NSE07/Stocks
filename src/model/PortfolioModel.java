package model;

import enums.PortfolioType;
import repository.IRepository;

public class PortfolioModel implements IPortfolioModel {

  private final IRepository<Portfolio> portfolioRepository;

  public PortfolioModel(IRepository<Portfolio> portfolioRepository) {
    this.portfolioRepository = portfolioRepository;
  }

  @Override
  public Portfolio createPortfolio(String portFolioName, PortfolioType portfolioType) {
    Portfolio portfolio = new Portfolio();
    portfolio.setName(portFolioName);
    portfolio.setPortfolioType(portfolioType);

    return portfolioRepository.create(portfolio);
  }

  @Override
  public void addStocks() {

  }

  @Override
  public void readPortfolio() {

  }

  @Override
  public void getValueOfPortfolio() {

  }
}
