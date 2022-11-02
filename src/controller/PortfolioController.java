package controller;

import constants.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import model.IPortfolioModel;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 *
 */
public class PortfolioController implements IPortfolioController {

  private final IPortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * @param model
   * @param view
   * @param in
   */
  public PortfolioController(IPortfolioModel model, IPortfolioView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.bufferedReader = new BufferedReader(new InputStreamReader(in));
  }

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
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            String portfolioName = this.bufferedReader.readLine();
            if (StringUtils.IsNullOrWhiteSpace(portfolioName)) {
              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
              continue;
            }
            List<Pair<String, Double>> stockPairs = new ArrayList<>();

            int selectedSubmenuItem = 0;
            while (selectedSubmenuItem != 2) {
              this.view.showOptions(selectedMenuItem);
              this.view.showPrompt(Constants.PROMPT_CHOICE);

              selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());
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

            if (StringUtils.IsNullOrWhiteSpace(portfolioName)) {
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

            if (StringUtils.IsNullOrWhiteSpace(portfolioName)) {
              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
              continue;
            }

            this.view.showPrompt(Constants.PROMPT_DATE_KEY);
            String date = this.bufferedReader.readLine();

            try {
              this.view.showString(
                  String.valueOf(this.model.getPortfolioValueOnDate(portfolioName, date)));
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
            this.view.showString(Constants.EXITING_STATUS);
            break;

          default:
            view.showOptionError();
            break;
        }
      } while (selectedMenuItem != 4);
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

        if (StringUtils.IsNullOrWhiteSpace(symbol)) {
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
              String.format("Failed to fetch stock symbol %s.", symbol));
          break;
        }

        this.view.showPrompt(Constants.PROMPT_QUANTITY_KEY);

        try {
          // model can handle but broker restriction
          double quantity = Integer.parseInt(this.bufferedReader.readLine());

          stockPairs.add(new Pair<>(symbol, quantity));
        } catch (NumberFormatException numberFormatException) {
          this.view.showString("Invalid Quantity Entry.");
        }
        break;
      case 2:
        break;
      default:
        this.view.showOptionError();
        break;
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
