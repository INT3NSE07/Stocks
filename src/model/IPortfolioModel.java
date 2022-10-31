package model;

import java.util.List;
import java.util.Map;
import utilities.Pair;

/**
 *
 */
public interface IPortfolioModel {

  /**
   *
   * @param portFolioName
   * @param stockPairs
   * @throws IllegalArgumentException
   */
  void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs)
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
