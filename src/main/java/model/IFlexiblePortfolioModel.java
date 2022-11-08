package model;

import java.util.List;
import utilities.Pair;

public interface IFlexiblePortfolioModel extends IPortfolioModel {

  void buyStock(String portFolioName, List<Pair<String, Double>> stockPairs, String Date);

  double sellStock(String portfolioName, List<Pair<String, Double>> stockPair, String date);

  double calCommission();

  double costBasis();

  void getPerformanceOverView(Portfolio portfolio);
}
