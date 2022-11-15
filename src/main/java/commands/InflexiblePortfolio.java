package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import constants.Constants;
import enums.MenuItem;
import model.IFlexiblePortfolioModel;
import model.Portfolio;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

public class InflexiblePortfolio implements PortfolioCommand {
  private final IFlexiblePortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;
  public InflexiblePortfolio(IFlexiblePortfolioModel model, IPortfolioView view, BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
    this.model = model;
    this.view = view;
  }

  @Override
  public void go() throws IOException {
    try {
      int selectedMenuItem;
      do {
        selectedMenuItem = MenuItem.INFLEXIBLE_PORTFOLIO_MAIN_MENU.getValue();
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
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            String portfolioName = this.bufferedReader.readLine();
            if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
              continue;
            }
            List<Pair<String, Double>> stockPairs = new ArrayList<>();

            int selectedSubmenuItem = MenuItem.CREATE_PORTFOLIO_SUBMENU_ITEMS.getValue();
            while (selectedSubmenuItem != Constants.CREATE_PORTFOLIO_EXIT_CODE) {
              this.view.showOptions(selectedSubmenuItem);
              this.view.showPrompt(Constants.PROMPT_CHOICE);

              try {
                selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());
              } catch (NumberFormatException e) {
                this.view.showString(Constants.INVALID_OPTION);
                continue;
              }

              createPortfolioSubmenu(selectedSubmenuItem, stockPairs);
            }

            try {
              this.model.createPortfolio(portfolioName, stockPairs);
            } catch (IOException e) {
              this.view.showString(
                      String.format("The creation of portfolio %s has failed.", portfolioName));
              break;
            } catch (IllegalArgumentException e) {
              this.view.showString(e.getMessage());
              break;
            }

            this.view.showString(
                    String.format("The portfolio %s has been created.", portfolioName));
            break;
          }

          case 2: {
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            String portfolioName = this.bufferedReader.readLine();

            if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
              continue;
            }

            try {
              this.view.showPortfolio(this.model.readPortfolio(portfolioName));
            } catch (IOException e) {
              this.view.showString(
                      String.format(Constants.PORTFOLIO_FETCH_FAIL, portfolioName));
              break;
            } catch (IllegalArgumentException e) {
              this.view.showString(e.getMessage());
              break;
            }

            break;
          }

          case 3: {
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            String portfolioName = this.bufferedReader.readLine();

            if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
              continue;
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
              break;
            } catch (IllegalArgumentException e) {
              this.view.showString(e.getMessage());
              break;
            }
            break;
          }

          case 4:
            this.view.showString(Constants.BACKING);
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

  private void createPortfolioSubmenu(int selectedSubmenuItem,
                                      List<Pair<String, Double>> stockPairs) throws IOException {
    switch (selectedSubmenuItem) {
      case 1:
        this.view.showPrompt(Constants.PROMPT_STOCK_SYMBOL_KEY);
        String symbol = this.bufferedReader.readLine();

        if (StringUtils.isNullOrWhiteSpace(symbol)) {
          this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
          return;
        }

        try {
          if (!this.model.isStockSymbolValid(symbol)) {
            this.view.showString(
                    String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
            break;
          }
        } catch (IOException e) {
          this.view.showString(
                  String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
          break;
        }

        this.view.showPrompt(Constants.PROMPT_QUANTITY_KEY);

        try {
          // model can handle but broker restriction
          double quantity = Integer.parseInt(this.bufferedReader.readLine());

          stockPairs.add(new Pair<>(symbol, quantity));
        } catch (NumberFormatException numberFormatException) {
          this.view.showString(Constants.QUANTITY_MUST_BE_A_WHOLE_NUMBER);
        }
        break;
      case 2:
        break;
      default:
        this.view.showOptionError();
        break;
    }
  }




  @Override
  public void help() {

  }
}
