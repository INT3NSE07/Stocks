package controller;

import java.io.IOException;

public interface IPortfolioFeatures extends IPortfolioController {

  void createPortfolio(String portfolioName) throws IOException;

  void examinePortfolio(String portfolioName, String date) throws IOException;

  void valueOfPortfolio(String portfolioName, String date) throws IOException;

  void createTransaction(String commissionFee, String option, String portfolioName,
      String stockSymbol, String quantity, String date) throws IOException;

  void costBasisOfPortfolio(String portfolioName, String date) throws IOException;

  void performanceOfPortfolio(String portfolioName, String startDate, String endDate)
      throws IOException;
}
