package commands;

import constants.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import model.IPortfolioFacadeModel;
import utilities.StringUtils;
import view.IPortfolioView;

public class ExaminePortfolio implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public ExaminePortfolio(IPortfolioFacadeModel model, IPortfolioView view,
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

    try {
      this.view.showPortfolio(this.model.readPortfolio(portfolioName));
    } catch (IOException e) {
      this.view.showString(
          String.format(Constants.PORTFOLIO_FETCH_FAIL, portfolioName));
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
    }
  }
}
