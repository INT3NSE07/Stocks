package controller;

import commands.CostBasis;
import commands.CreatePortfolio;
import commands.CreateTransaction;
import commands.ExaminePortfolio;
import commands.PortfolioPerformance;
import commands.ValueOfPortfolio;
import constants.Constants;
import enums.PortfolioTypes;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import model.IPortfolioFacadeModel;
import view.IGUIPortfolioView;

public class PortfolioGUIController implements IPortfolioFeatures {

  private final IPortfolioFacadeModel model;
  private final IGUIPortfolioView view;

  public PortfolioGUIController(IPortfolioFacadeModel model, IGUIPortfolioView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void run() {
    try {
      view.addFeatures(this);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }

  @Override
  public void createPortfolio(String portfolioName) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = getBufferedReader(portfolioName);
    new CreatePortfolio(this.model, this.view, bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void examinePortfolio(String portfolioName, String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = getBufferedReader(portfolioName, date);
    new ExaminePortfolio(this.model, this.view, bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void valueOfPortfolio(String portfolioName, String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = getBufferedReader(portfolioName, date);
    new ValueOfPortfolio(this.model, this.view, bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void createTransaction(String commissionFee,
      String portfolioName,
      String stockSymbol,
      String quantity,
      String date,
      String selectedOption) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = getBufferedReader(commissionFee, String.valueOf(selectedOption),
        portfolioName,
        stockSymbol, quantity, date, String.valueOf(Constants.TRANSACTION_SUBMENU_EXIT_CODE));
    new CreateTransaction(this.model, this.view, bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void costBasisOfPortfolio(String portfolioName, String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = getBufferedReader(portfolioName, date);
    new CostBasis(this.model, this.view, bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void performanceOfPortfolio(String portfolioName, String startDate, String endDate)
      throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = getBufferedReader(portfolioName, startDate, endDate);
    new PortfolioPerformance(this.model, this.view, bufferedReader).execute();
    this.view.clearInputString();
  }

  private BufferedReader getBufferedReader(String... inputs) {
    StringBuilder res = new StringBuilder();
    for (String input : inputs) {
      res.append(input).append(System.lineSeparator());
    }
    return new BufferedReader(
        new InputStreamReader(
            new ByteArrayInputStream(
                res.toString().getBytes()
            )
        )
    );
  }
}
