package controller;

import java.io.IOException;

public interface Features {

  void createPortfolio(String text) throws IOException;

  void examinePortfolio(String portfolioName, String date) throws IOException;

  void valueOfPortfolio(String text, String text1) throws IOException;

  void transactionsOfPortfolio(String commissionFee,
                               String option,
                               String portfolioName,
                               String stockSymbol,
                               String quantity,
                               String date) throws IOException;

  void costBasisOfPortfolio(String portfolioName, String date) throws IOException;

  void performanceOfPortfolio(String portfolioName, String startDate, String endDate)
          throws IOException;
}
