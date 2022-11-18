package commands;

import constants.Constants;
import enums.MenuItems;

import java.io.BufferedReader;
import java.io.IOException;

import model.IPortfolioFacadeModel;
import view.IPortfolioView;

/**
 * This class represents a command in the command design pattern. It handles main menu
 *                    functionality of an inflexible portfolio.
 */
public class InflexiblePortfolio implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link InflexiblePortfolio} command object and initializes the model,
   *                        view and reader fields.
   *
   * @param model          the model instance which is used to perform the actual operations
   * @param view           the view which displays output to the end user
   * @param bufferedReader the input stream through which user input is taken
   */
  public InflexiblePortfolio(IPortfolioFacadeModel model, IPortfolioView view,
                             BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
    this.model = model;
    this.view = view;
  }

  @Override
  public void execute() throws IOException {
    try {
      int selectedMenuItem;
      do {
        selectedMenuItem = MenuItems.INFLEXIBLE_PORTFOLIO.getValue();
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
            new CreatePortfolio(this.bufferedReader, this.model, this.view).execute();
            break;
          }

          case 2: {
            new ExaminePortfolio(this.model, this.view, this.bufferedReader).execute();
            break;
          }

          case 3: {
            new ValueOfPortfolio(this.model, this.view, this.bufferedReader).execute();
            break;
          }

          case 4:
            this.view.showString(Constants.GOING_BACK_STATUS);
            break;

          default:
            view.showOptionError();
            break;
        }
      }
      while (selectedMenuItem != Constants.INFLEXIBLE_EXIT_CODE);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }
}
