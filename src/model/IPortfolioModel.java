package model;

public interface IPortfolioModel {

  Portfolio createPortfolio(String portFolioName) throws IllegalArgumentException;

  void addStock(String portFolioName, String ticker, double quantity)
      throws IllegalArgumentException;

  Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException;

  double getPortfolioValueOnDate(String portFolioName, String date) throws IllegalArgumentException;
}
