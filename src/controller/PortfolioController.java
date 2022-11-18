package controller;

import commands.FlexiblePortfolio;
import commands.InflexiblePortfolio;
import constants.Constants;
import enums.PortfolioTypes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import model.IPortfolioFacadeModel;
import view.IPortfolioView;

/**
 * This class represents the portfolio controller. The controller takes inputs from the user and
 * tells the model what to do and the view what to show.
 */
public class PortfolioController implements IPortfolioController {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link PortfolioController} object.
   *
   * @param model the model instance which is used to perform the actual operations
   * @param view  the view which displays output to the end user
   * @param in    the input stream through which user input is taken
   */
  public PortfolioController(IPortfolioFacadeModel model, IPortfolioView view, InputStream in) {
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
            this.model.setPortfolioType(PortfolioTypes.INFLEXIBLE);
            new InflexiblePortfolio(this.model, this.view, this.bufferedReader).execute();
            break;
          }
          case 2: {
            this.model.setPortfolioType(PortfolioTypes.FLEXIBLE);
            new FlexiblePortfolio(this.model, this.view, this.bufferedReader).execute();
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
      }
      while (selectedMenuItem != Constants.PORTFOLIO_OPTIONS_EXIT_CODE);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }
}
