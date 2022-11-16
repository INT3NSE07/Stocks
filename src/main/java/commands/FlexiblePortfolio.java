package commands;

import constants.Constants;
import enums.MenuItems;
import java.io.BufferedReader;
import java.io.IOException;
import model.IFlexiblePortfolioModel;
import view.IPortfolioView;

public class FlexiblePortfolio implements PortfolioCommand {

  private final IFlexiblePortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public FlexiblePortfolio(IFlexiblePortfolioModel model, IPortfolioView view,
      BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() throws IOException {
    int selectedMenuItem;
    do {
      selectedMenuItem = MenuItems.FLEXIBLE_PORTFOLIO_MAIN_MENU.getValue();
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
          new PortfolioPerformance(this.model, this.view, this.bufferedReader).go();
          break;
        }
        case 7: {
          this.view.showString(Constants.BACKING);
          break;
        }
        default: {
          view.showOptionError();
          break;
        }
      }
    }
    while (selectedMenuItem != Constants.FLEXIBLE_EXIT_CODE);


  }

  @Override
  public void help() {

  }
}
