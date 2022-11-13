package commands;

import java.io.BufferedReader;
import java.io.IOException;

import constants.Constants;
import model.IFlexiblePortfolioModel;
import utilities.StringUtils;
import view.IPortfolioView;

public class CostBasis implements PortfolioCommand {

  private final IFlexiblePortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public CostBasis(IFlexiblePortfolioModel model, IPortfolioView view, BufferedReader bufferedReader) {
    this.model = model;
    this.view =view;
    this.bufferedReader = bufferedReader;
  }

  @Override
  public void go() throws IOException {

    this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
    String portfolioName = this.bufferedReader.readLine();
    String date = null;

    if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
      this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
      return;
    }

    this.view.showPrompt(Constants.PROMPT_DATE_KEY);

    date = this.bufferedReader.readLine();

    this.model.getCostBasis(portfolioName,date);
  }

  @Override
  public void help() {

  }

}
