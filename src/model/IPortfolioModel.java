package model;

import java.util.Map;

/**
 *
 */
public interface IPortfolioModel {

  /**
   *
   * @param portFolioName
   * @param stockSymbolQuantityMap
   * @throws IllegalArgumentException
   */
  void createPortfolio(String portFolioName, Map<String, Double> stockSymbolQuantityMap)
      throws IllegalArgumentException;

  /**
   *
   * @param portFolioName
   * @return
   * @throws IllegalArgumentException
   */
  Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException;

  /**
   *
   * @param portFolioName
   * @param date
   * @return
   * @throws IllegalArgumentException
   */
  double getPortfolioValueOnDate(String portFolioName, String date) throws IllegalArgumentException;
}
