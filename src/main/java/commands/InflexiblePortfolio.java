package commands;

import constants.Constants;
import enums.MenuItems;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.IPortfolioFacadeModel;
import model.Portfolio;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

public class InflexiblePortfolio implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public InflexiblePortfolio(IPortfolioFacadeModel model, IPortfolioView view,
      BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() throws IOException {
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
            new CreatePortfolio(this.bufferedReader,this.model,this.view).go();
            break;
          }

          case 2: {
            new ExaminePortfolio(this.model,this.view,this.bufferedReader).go();
            break;
          }

          case 3: {
            new ValueOfPortfolio(this.model,this.view,this.bufferedReader).go();
            break;
          }

          case 4:
            this.view.showString(Constants.GOING_BACK_STATUS);
            break;

          default:
            view.showOptionError();
            break;
        }
      } while (selectedMenuItem != Constants.INFLEXIBLE_EXIT_CODE);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }
}
