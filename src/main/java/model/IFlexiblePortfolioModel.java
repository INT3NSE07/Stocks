package model;

import java.io.IOException;
import java.util.List;
import utilities.Pair;

public interface IFlexiblePortfolioModel extends IPortfolioModel {

  void buyStock(String portFolioName, Pair<String, Double> stockPair, String Date) throws IOException;

  void sellStock(String portfolioName, Pair<String, Double> stockPair, String date) throws IOException;

  double calCommission();

  double costBasis(Portfolio portfolio);

  void getPerformanceOverView(Portfolio portfolio);
}
