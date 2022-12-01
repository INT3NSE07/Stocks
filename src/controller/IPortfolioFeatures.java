package controller;

import java.io.IOException;
import java.util.List;

import utilities.Pair;

public interface IPortfolioFeatures extends IPortfolioController {

  void createPortfolio(String portfolioName) throws IOException;

  void examinePortfolio(String portfolioName, String date) throws IOException;

  void valueOfPortfolio(String portfolioName, String date) throws IOException;

  void createTransaction(String commissionFee, String option, String portfolioName,
      String stockSymbol, String quantity, String date) throws IOException;

  void costBasisOfPortfolio(String portfolioName, String date) throws IOException;

  void performanceOfPortfolio(String portfolioName, String startDate, String endDate)
      throws IOException;

  void applyStrategy(
          String commissionFee,
          Integer strategyOption,
          String portfolioName,
          List<Pair<String, String>> symbolWeightPair,
          String investment,
          String startDate,
          String endDate,
          Integer period
  ) throws IOException;
}
