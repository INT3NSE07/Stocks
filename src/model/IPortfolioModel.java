package model;

import enums.PortfolioType;

public interface IPortfolioModel {
  void createPortfolio(String portFolioName, PortfolioType portfolioType);
}
