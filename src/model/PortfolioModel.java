package model;

import enums.PortfolioType;
import repository.IRepository;

public class PortfolioModel implements IPortfolioModel {

  private final IRepository<Portfolio> portfolioRepository;

  public PortfolioModel(IRepository<Portfolio> portfolioRepository) {
    this.portfolioRepository = portfolioRepository;
  }

  @Override
  public void createPortfolio(String portFolioName, PortfolioType portfolioType) {
    Portfolio portfolio = new Portfolio();
    portfolio.setName(portFolioName);
    portfolio.setPortfolioType(portfolioType);

    portfolioRepository.create(portfolio);
  }
}
