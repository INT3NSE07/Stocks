package model;

import java.io.IOException;
import utilities.Pair;

public interface IFlexiblePortfolioModel extends IPortfolioModel {

  void buyStock(String portFolioName, Pair<String, Double> stockPair, String Date, Double commission)
      throws IOException;

  void sellStock(String portfolioName, Pair<String, Double> stockPair, String date, Double commission)
      throws IOException;

  Pair<Portfolio, Double> getCostBasis(String portfolioName, String date) throws IOException;

  void getPerformanceOverView(Portfolio portfolio);
}
