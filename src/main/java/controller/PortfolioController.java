package controller;

import commands.FlexiblePortfolio;
import commands.InflexiblePortfolio;
import constants.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;
import model.IFlexiblePortfolioModel;
import utilities.Pair;
import model.IFlexiblePortfolioModel;
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
      int selectedMenuItem = 0;
      do {
        this.view.showOptions(selectedMenuItem);
        this.view.showPrompt(Constants.PROMPT_CHOICE);
        try {
          selectedMenuItem = Integer.parseInt(this.bufferedReader.readLine());

        } catch (NumberFormatException e) {
          this.view.showString(Constants.INVALID_OPTION);
        }

        switch (selectedMenuItem) {
          case 1: {
            // Should we add Only InFlexible portfolio object model here?
            // this.model = new PortfolioModel();
            new InflexiblePortfolio(this.model, this.view, this.bufferedReader).go();
            break;
          }
          case 2: {
            new FlexiblePortfolio(this.model, this.view, this.bufferedReader).go();
            break;
          }
          case 3: {
            this.view.showString(Constants.EXITING_STATUS);
            break;
          }
          default: {
            view.showOptionError();
            break;
          }
        }
        } while (selectedMenuItem != Constants.MENU_TYPE.get(Constants.PORTFOLIO_OPTIONS).length);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }
}
