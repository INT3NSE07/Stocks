package controller;

import commands.CostBasis;
import commands.CreatePortfolio;
import commands.ExaminePortfolio;
import commands.Transactions;
import commands.ValueOfPortfolio;
import constants.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import model.IFlexiblePortfolioModel;
import model.Portfolio;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents the portfolio controller. The controller takes inputs from the user and
 * tells the model what to do and the view what to show.
 */
public class PortfolioController implements IPortfolioController {

  private final IFlexiblePortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link PortfolioController} object.
   *
   * @param model the model instance which is used to perform the actual operations
   * @param view  the view which displays output to the end user
   * @param in    the input stream through which user input is taken
   */
  public PortfolioController(IFlexiblePortfolioModel model, IPortfolioView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.bufferedReader = new BufferedReader(new InputStreamReader(in));
  }

  /**
   * The method acts as the entry point of the controller. It is invoked by the runner.
   */
  public void run() {
    try {
      int selectedMenuItem;
      do {
        selectedMenuItem = 0;
        this.view.showOptions(selectedMenuItem);
        this.view.showPrompt(Constants.PROMPT_CHOICE);

        try {
          selectedMenuItem = Integer.parseInt(this.bufferedReader.readLine());

        } catch (NumberFormatException e) {
          this.view.showString(Constants.INVALID_OPTION);
          continue;
        }

        switch (selectedMenuItem) {
          case 1: {
            new CreatePortfolio(this.bufferedReader, this.model, this.view).go();
            break;
          }
          case 2: {
            new ExaminePortfolio(this.model, this.view, this.bufferedReader).go();
            break;
          }
          case 3: {
            new ValueOfPortfolio(this.model, this.view, this.bufferedReader).go();
            break;
          }
          case 4: {
            new Transactions(this.model, this.view, this.bufferedReader).go();
            break;
          }
          case 5: {
            new CostBasis(this.model, this.view, this.bufferedReader).go();
            break;
          }
          case 6: {
            break;
          }
          case 7: {
            this.view.showString(Constants.EXITING_STATUS);
            break;
          }
          default: {
            view.showOptionError();
            break;
          }
        }
      }
      while (selectedMenuItem != Constants.EXIT_CODE);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }

  private <T> Pair<Boolean, T> validateInput(String input, Function<String, T> validator) {
    try {
      return new Pair<>(true, validator.apply(input));
    } catch (NumberFormatException e) {
      this.view.showString("The entered input is invalid.");
    }

    return new Pair<>(false, null);
  }
}
