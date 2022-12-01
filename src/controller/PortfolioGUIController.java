package controller;

import commands.ApplyStrategy;
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
import java.util.List;
import model.IPortfolioFacadeModel;
import utilities.Pair;
import view.IGUIPortfolioView;

/**
 * This class represents the GUI portfolio controller. The controller takes inputs from the user
 * through the UI and tells the model what to do and the view what to show.
 */
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

  @Override
  public void applyStrategy(
      String commissionFee,
      Integer strategyOption,
      String portfolioName,
      List<Pair<String, String>> symbolWeightPairs,
      String investment,
      String startDate,
      String endDate,
      Integer period
  )
      throws IOException {

    StringBuilder inputForWeights = new StringBuilder();
    for (int i = 0; i < symbolWeightPairs.size(); i++) {
      inputForWeights.append(symbolWeightPairs.get(i).getKey()).append(System.lineSeparator())
          .append(symbolWeightPairs.get(i).getValue())
          .append(System.lineSeparator());

      // if last element append  q else newline
      if (i == symbolWeightPairs.size() - 1) {
        inputForWeights.append("q");
      } else {
        inputForWeights.append(System.lineSeparator());
      }
    }

    this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
    BufferedReader bufferedReader = null;
    if (strategyOption + 1 == 1) {
      bufferedReader = getBufferedReader(
          commissionFee,
          String.valueOf(strategyOption + 1),
          portfolioName,
          inputForWeights.toString(),
          investment,
          startDate,
          String.valueOf(Constants.APPLY_STRATEGY_SUBMENU_EXIT_CODE)
      );
    } else {
      bufferedReader = getBufferedReader(
          commissionFee,
          String.valueOf(strategyOption + 1),
          portfolioName,
          inputForWeights.toString(),
          investment,
          startDate,
          endDate,
          period.toString(),
          String.valueOf(Constants.APPLY_STRATEGY_SUBMENU_EXIT_CODE)
      );
    }
    new ApplyStrategy(this.model, this.view, bufferedReader).execute();
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
