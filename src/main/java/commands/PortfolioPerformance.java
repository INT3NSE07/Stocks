package commands;

import constants.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import model.IPortfolioFacadeModel;
import utilities.DateUtils;
import utilities.StringUtils;
import view.IPortfolioView;

public class PortfolioPerformance implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public PortfolioPerformance(IPortfolioFacadeModel model, IPortfolioView view,
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

    this.view.showPrompt(Constants.PROMPT_START_DATE_KEY);
    String startDate = this.bufferedReader.readLine();
    if (StringUtils.isNullOrWhiteSpace(startDate)) {
      this.view.showString(Constants.DATE_INVALID);
      return;
    }
    if (!DateUtils.isValidDate(startDate, Constants.DEFAULT_DATETIME_FORMAT)) {
      this.view.showString(Constants.DATE_INVALID);
      return;
    }

    this.view.showPrompt(Constants.PROMPT_END_DATE_KEY);
    String endDate = this.bufferedReader.readLine();
    if (StringUtils.isNullOrWhiteSpace(endDate)) {
      endDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }

    try {
      this.view.showPortfolioPerformance(
          portfolioName, startDate, endDate,
          this.model.getPerformanceOverview(portfolioName, startDate,
              endDate));
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
    }
  }
}
