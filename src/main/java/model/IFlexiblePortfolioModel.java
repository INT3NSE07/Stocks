package model;

import java.io.IOException;
import java.util.List;
import utilities.Pair;

public interface IFlexiblePortfolioModel extends IPortfolioModel {

  void buyStock(String portfolioName, Pair<String, Double> stockPair, String Date,
      double commission) throws IOException;

  void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException;

  Pair<Portfolio, Double> getCostBasis(String portfolioName, String date) throws IOException;

  List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate, String toDate)
      throws IOException;
}
