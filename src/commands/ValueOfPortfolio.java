package commands;

import constants.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import model.IPortfolioFacadeModel;
import model.Portfolio;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents a command in the command design pattern. It handles the value of portfolio
 * functionality.
 */
public class ValueOfPortfolio implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link ValueOfPortfolio} command object and initializes the model, view and reader
   * fields.
   *
   * @param model          the model instance which is used to perform the actual operations
   * @param view           the view which displays output to the end user
   * @param bufferedReader the input stream through which user input is taken
   */
  public ValueOfPortfolio(IPortfolioFacadeModel model, IPortfolioView view,
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
