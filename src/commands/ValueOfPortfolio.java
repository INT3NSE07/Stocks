package commands;

import constants.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import model.IPortfolioFacadeModel;
import model.Portfolio;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

public class ValueOfPortfolio implements PortfolioCommand {


  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public ValueOfPortfolio(IPortfolioFacadeModel model, IPortfolioView view,
      BufferedReader bufferedReader) {
    this.model = model;
    this.view = view;
    this.bufferedReader = bufferedReader;
  }

  @Override
  public void go() throws IOException {
    this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
    String portfolioName = this.bufferedReader.readLine();

    if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
      this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
      return;
    }

    this.view.showPrompt(Constants.PROMPT_DATE_KEY);
    String date = this.bufferedReader.readLine();

    try {
      Pair<Portfolio, Double> portfolioValue = this.model.getPortfolioValueOnDate(
          portfolioName, date);
      this.view.showPortfolioValue(portfolioValue);
    } catch (IOException e) {
      this.view.showString(
          String.format("The fetching of value of the portfolio %s has failed.",
              portfolioName));
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
    }
  }
}
