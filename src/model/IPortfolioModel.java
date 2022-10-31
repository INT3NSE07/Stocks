package model;

import java.io.IOException;
import java.util.List;
import utilities.Pair;

/**
 *
 */
public interface IPortfolioModel {

  /**
   * @param portFolioName
   * @param stockPairs
   * @throws IllegalArgumentException
   */
  void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException;

  /**
   * @param portFolioName
   * @return
   * @throws IllegalArgumentException
   */
  Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException, IOException;

  /**
   * @param portFolioName
   * @param date
   * @return
   * @throws IllegalArgumentException
   */
  double getPortfolioValueOnDate(String portFolioName, String date)
      throws IllegalArgumentException, IOException;

  boolean isStockSymbolValid(String symbol) throws IOException;
}
