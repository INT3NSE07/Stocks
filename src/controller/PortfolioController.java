package controller;

import constants.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.IPortfolioModel;
import utilities.Pair;
import view.IPortfolioView;

/**
 *
 */
public class PortfolioController implements IPortfolioController {

  private final IPortfolioModel model;

  private final IPortfolioView view;

  private final Scanner scanner;

  /**
   * @param model
   * @param view
   * @param in
   */
  public PortfolioController(IPortfolioModel model, IPortfolioView view, InputStream in) {
    this.model = model;
    this.view = view;
    this.scanner = new Scanner(in);
  }

  public void run() {
    int selectedMenuItem;
    do {
      selectedMenuItem = 0;
      this.view.showOptions(selectedMenuItem);
      this.view.showPrompt(Constants.PROMPT_CHOICE);

      selectedMenuItem = this.scanner.nextInt();

      switch (selectedMenuItem) {
        case 1: {
          this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
          String portfolioName = this.scanner.next();
          List<Pair<String, Double>> stockPairs = new ArrayList<>();

          int selectedSubmenuItem = 0;
          while (selectedSubmenuItem != 2) {
            this.view.showOptions(selectedMenuItem);
            this.view.showPrompt(Constants.PROMPT_CHOICE);

            selectedSubmenuItem = this.scanner.nextInt();
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
          String portfolioName = this.scanner.next();

          try {
            this.view.showPortfolio(this.model.readPortfolio(portfolioName));
          } catch (IOException e) {
            this.view.showString(
                String.format("The fetching of portfolio %s has failed.", portfolioName));
            break;
          } catch (IllegalArgumentException e) {
            this.view.showString(e.getMessage());
            break;
          }

          break;
        }

        case 3: {
          this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
          String portfolioName = this.scanner.next();

          this.view.showPrompt(Constants.PROMPT_DATE_KEY);
          String date = this.scanner.next();

          try {
            this.model.getPortfolioValueOnDate(portfolioName, date);
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
          break;

        default:
          view.showOptionError();
          break;
      }
    } while (selectedMenuItem != 4);
  }

  private void createPortfolioSubmenu(int selectedSubmenuItem,
      List<Pair<String, Double>> stockPairs) {
    switch (selectedSubmenuItem) {
      case 1:
        this.view.showPrompt(Constants.PROMPT_STOCK_SYMBOL_KEY);
        String symbol = this.scanner.next();

        try {
          if (!this.model.isStockSymbolValid(symbol)) {
            this.view.showString(
                String.format("The stock symbol %s does not exist.", symbol));
            break;
          }
        } catch (IOException e) {
          this.view.showString(
              String.format("Failed to fetch stock symbol %s.", symbol));
          break;
        }

        this.view.showPrompt(Constants.PROMPT_QUANTITY_KEY);

        // model can handle but broker restriction
        double quantity = this.scanner.nextInt();

        stockPairs.add(new Pair<>(symbol, quantity));
        break;
      case 2:
        break;
      default:
        this.view.showOptionError();
        break;
    }
  }
}
