package model;

import enums.PortfolioType;

public interface IPortfolioModel {

  Portfolio createPortfolio(String portFolioName, PortfolioType portfolioType);

  void addStocks();

  void readPortfolio();

  void getValueOfPortfolio();
}
