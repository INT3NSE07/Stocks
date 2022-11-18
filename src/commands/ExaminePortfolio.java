package commands;

import constants.Constants;
import enums.PortfolioTypes;
import java.io.BufferedReader;
import java.io.IOException;
import model.IPortfolioFacadeModel;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents a command in the command design pattern. It handles examining the
 * composition functionality of a portfolio.
 */
public class ExaminePortfolio implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link ExaminePortfolio} command object and initializes the model, view and reader
   * fields.
   *
   * @param model          the model instance which is used to perform the actual operations
   * @param view           the view which displays output to the end user
   * @param bufferedReader the input stream through which user input is taken
   */
  public ExaminePortfolio(IPortfolioFacadeModel model, IPortfolioView view,
      BufferedReader bufferedReader) {
    this.model = model;
    this.view = view;
    this.bufferedReader = bufferedReader;
  }

  @Override
  public void execute() throws IOException {
    this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
    String portfolioName = this.bufferedReader.readLine();
    if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
      this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
      return;
    }

    String date = null;
    if (this.model.getPortfolioType() == PortfolioTypes.FLEXIBLE) {
      this.view.showPrompt(Constants.PROMPT_DATE_KEY);
      date = this.bufferedReader.readLine();
    }

    try {
      this.view.showPortfolio(this.model.readPortfolio(portfolioName, date));
    } catch (IOException e) {
      this.view.showString(
          String.format(Constants.PORTFOLIO_FETCH_FAIL, portfolioName));
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
    }
  }
}
