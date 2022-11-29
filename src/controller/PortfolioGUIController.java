package controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import commands.CostBasis;
import commands.CreatePortfolio;
import commands.CreateTransaction;
import commands.ExaminePortfolio;
import commands.PortfolioPerformance;
import commands.ValueOfPortfolio;
import constants.Constants;
import enums.PortfolioTypes;
import model.IPortfolioFacadeModel;
import view.IGUIPortfolioView;

public class PortfolioGUIController implements Features {

  private IPortfolioFacadeModel model;
  private IGUIPortfolioView view;

  private BufferedReader bufferedReader;

  private InputStream in;


  public PortfolioGUIController(IPortfolioFacadeModel model, IGUIPortfolioView view, InputStream in)
          throws IOException {
    this.model = model;
    this.view = view;
    this.in = in;
    this.bufferedReader = new BufferedReader(new InputStreamReader(this.in));
    view.addFeatures(this);
  }

  @Override
  public void createPortfolio(String portfolioName) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    this.bufferedReader = getBufferedReader(portfolioName);
    new CreatePortfolio(this.bufferedReader, this.model, this.view).execute();
    this.view.clearInputString();
  }

  @Override
  public void examinePortfolio(String portfolioName, String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    this.bufferedReader = getBufferedReader(portfolioName, date);
    new ExaminePortfolio(this.model, this.view, this.bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void valueOfPortfolio(String portfolioName, String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    this.bufferedReader = getBufferedReader(portfolioName, date);
    new ValueOfPortfolio(this.model, this.view, this.bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void transactionsOfPortfolio(String commissionFee,
                                      String option,
                                      String portfolioName,
                                      String stockSymbol,
                                      String quantity,
                                      String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    this.bufferedReader = getBufferedReader(commissionFee, String.valueOf(option), portfolioName,
            stockSymbol, quantity, date, String.valueOf(Constants.TRANSACTION_SUBMENU_EXIT_CODE));
    new CreateTransaction(this.model, this.view, this.bufferedReader).execute();
    this.view.clearInputString();
  }

  @Override
  public void costBasisOfPortfolio(String portfolioName, String date) throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    this.bufferedReader = getBufferedReader(portfolioName, date);
    new CostBasis(this.model, this.view, this.bufferedReader).execute();
    this.view.clearInputString();
  }


  @Override
  public void performanceOfPortfolio(String portfolioName, String startDate, String endDate)
          throws IOException {
    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    this.bufferedReader = getBufferedReader(portfolioName, startDate, endDate);
    new PortfolioPerformance(this.model, this.view, this.bufferedReader).execute();
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
