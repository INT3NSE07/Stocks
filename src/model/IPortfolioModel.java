package model;

import java.util.Map;

public interface IPortfolioModel {

  void createPortfolio(String portFolioName, Map<String, Double> stockSymbolQuantityMap)
      throws IllegalArgumentException;

  Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException;

  double getPortfolioValueOnDate(String portFolioName, String date) throws IllegalArgumentException;
}
