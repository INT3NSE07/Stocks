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
import model.Portfolio;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents the portfolio controller. The controller takes inputs from the user and
 * tells the model what to do and the view what to show.
 */
public class PortfolioController implements IPortfolioController {

  private final IPortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link PortfolioController} object.
   *
   * @param model the model instance which is used to perform the actual operations
   * @param view  the view which displays output to the end user
   * @param in    the input stream through which user input is taken
   */
  public PortfolioController(IPortfolioModel model, IPortfolioView view, InputStream in) {
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
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            String portfolioName = this.bufferedReader.readLine();
            if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
              continue;
            }
            List<Pair<String, Double>> stockPairs = new ArrayList<>();

            int selectedSubmenuItem = 0;
            while (selectedSubmenuItem != 2) {
              this.view.showOptions(selectedMenuItem);
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
//            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
//            String portfolioName = this.bufferedReader.readLine();
//            if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
//              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
//              continue;
//            }

            int selectedSubmenuItem = 0;
            while (selectedSubmenuItem != 3) {
              this.view.showOptions(selectedMenuItem);
              this.view.showPrompt(Constants.PROMPT_CHOICE);

              try {
                selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());
              } catch (NumberFormatException e) {
                this.view.showString(Constants.INVALID_OPTION);
                break;
              }
              createTransactionSubMenuItem(selectedSubmenuItem);
            }
            break;
          }

//            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
//            String portfolioName = this.bufferedReader.readLine();
//            if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
//              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
//              continue;
//            }
//            List<Pair<String, Double>> stockPair = new ArrayList<>();
//            this.view.showPrompt(Constants.PROMPT_STOCK_SYMBOL_KEY);
//            String symbol = this.bufferedReader.readLine();
//
//            if (StringUtils.isNullOrWhiteSpace(symbol)) {
//              this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
//              return;
//            }
//
//            try {
//              if (!this.model.isStockSymbolValid(symbol)) {
//                this.view.showString(
//                        String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
//                break;
//              }
//            } catch (IOException e) {
//              this.view.showString(
//                      String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
//              break;
//            }
//
//            this.view.showPrompt(Constants.PROMPT_QUANTITY_KEY);
//
//            try {
//              // model can handle but broker restriction
//              double quantity = Integer.parseInt(this.bufferedReader.readLine());
//
//              stockPair.add(new Pair<>(symbol, quantity));
//
//              try {
//                this.model.addStock(portfolioName, stockPair);
//              }
//              catch (IllegalArgumentException illegalArgumentException) {
//                this.view.showString(Constants.PORTFOLIO_DOES_NOT_EXIST);
//              }
//
//            } catch (NumberFormatException numberFormatException) {
//              this.view.showString(Constants.QUANTITY_MUST_BE_A_WHOLE_NUMBER);
//            }

//            break;
//          }
          case 3: {
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            break;
          }
          case 4: {
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            break;
          }
          case 5: {
            this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
            break;
          }
          case 6: {
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

          case 7: {
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
          case 8: {
            this.view.showString(Constants.EXITING_STATUS);
            break;
          }
          default: {
            view.showOptionError();
            break;
          }
        }
      }
      while (selectedMenuItem != 8);
    } catch (IOException e) {
      this.view.showString("Failed to initialize the program.");
    }
  }

  private void createTransactionSubMenuItem(int selectedSubmenuItem) {
    switch (selectedSubmenuItem) {
      case 1: {
        break;
      }
      case 2: {
        break;
      }
      case 3: {
        break;
      }
      default:
        this.view.showOptionError();
        break;
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

  private <T> Pair<Boolean, T> validateInput(String input, Function<String, T> validator) {
    try {
      return new Pair<>(true, validator.apply(input));
    } catch (NumberFormatException e) {
      this.view.showString("The entered input is invalid.");
    }

    return new Pair<>(false, null);
  }
}
