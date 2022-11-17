package model;

import enums.PortfolioTypes;
import java.io.IOException;
import java.util.List;
import utilities.Pair;

public interface IPortfolioFacadeModel {

  void createPortfolio(String portfolioName, List<Pair<String, Double>> stockPairs)
      throws IllegalArgumentException, IOException;

  Portfolio readPortfolio(String portfolioName, String date)
      throws IllegalArgumentException, IOException;

  Pair<Portfolio, Double> getPortfolioValueOnDate(String portfolioName, String date)
      throws IllegalArgumentException, IOException;

  void buyStock(String portfolioName, Pair<String, Double> stockPair, String Date,
      double commission) throws IOException;

  void sellStock(String portfolioName, Pair<String, Double> stockPair, String date,
      double commission) throws IOException;

  double getCostBasis(String portfolioName, String date) throws IOException;

  List<PortfolioValue> getPerformanceOverview(String portfolioName, String fromDate, String toDate)
      throws IOException;

  boolean isStockSymbolValid(String symbol) throws IOException, IllegalArgumentException;

  PortfolioTypes getPortfolioType();

  void setPortfolioType(PortfolioTypes portfolioType);
}
