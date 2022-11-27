package commands;

import constants.Constants;
import enums.MenuItems;
import java.io.BufferedReader;
import java.io.IOException;
import model.IPortfolioFacadeModel;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents a command in the command design pattern. It handles the transaction
 * functionality of a portfolio.
 */
public class CreateTransaction implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link CreateTransaction} command object and initializes the model, view and
   * reader fields.
   *
   * @param model          the model instance which is used to perform the actual operations
   * @param view           the view which displays output to the end user
   * @param bufferedReader the input stream through which user input is taken
   */
  public CreateTransaction(IPortfolioFacadeModel model, IPortfolioView view,
      BufferedReader bufferedReader) {
    this.model = model;
    this.view = view;
    this.bufferedReader = bufferedReader;
  }

  @Override
  public void execute() throws IOException {
    int selectedSubmenuItem = MenuItems.FLEXIBLE_PORTFOLIO.getValue();
    double commission = 0;
    this.view.showPrompt(Constants.PROMPT_COMMISSION_KEY);

    try {
      commission = Double.parseDouble(this.bufferedReader.readLine());
      if (commission < 0) {
        throw new IllegalArgumentException(String.format(Constants.NON_NEGATIVE, "Commission"));
      }
    } catch (NumberFormatException numberFormatException) {
      this.view.showString("Please enter a valid commission value in $");
      return;
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
      return;
    }

    while (selectedSubmenuItem != Constants.TRANSACTION_SUBMENU_EXIT_CODE) {
      this.view.showOptions(MenuItems.CREATE_TRANSACTION.getValue());
      this.view.showPrompt(Constants.PROMPT_CHOICE);

      try {
        selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());

        if ((selectedSubmenuItem > Constants.TRANSACTION_SUBMENU_EXIT_CODE) && (
            selectedSubmenuItem > 0)) {
          this.view.showString(Constants.INVALID_OPTION);
        }
      } catch (NumberFormatException e) {
        this.view.showString(Constants.INVALID_OPTION);
        break;
      }
      createTransactionSubMenuItem(selectedSubmenuItem, commission);
    }
  }

  private void createTransactionSubMenuItem(int selectedSubmenuItem, double commission)
      throws IOException {

    if (selectedSubmenuItem == Constants.TRANSACTION_SUBMENU_EXIT_CODE) {
      return;
    }
    this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
    Pair<String, Double> stockPair = null;
    String portfolioName = this.bufferedReader.readLine();
    String date = null;
    if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
      this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
      return;
    }
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
        return;
      }
    } catch (IOException e) {
      this.view.showString(
          String.format(Constants.SYMBOL_FETCH_FAIL, symbol));
      return;
    }

    this.view.showPrompt(Constants.PROMPT_QUANTITY_KEY);

    try {
      // model can handle but broker restriction
      double quantity = Integer.parseInt(this.bufferedReader.readLine());

      stockPair = new Pair<>(symbol, quantity);

      this.view.showPrompt(Constants.PROMPT_DATE_KEY);
      date = this.bufferedReader.readLine();

    } catch (NumberFormatException numberFormatException) {
      this.view.showString(Constants.QUANTITY_MUST_BE_A_WHOLE_NUMBER);
      return;
    }

    switch (selectedSubmenuItem) {
      case 1: {
        try {
          this.model.buyStock(portfolioName, stockPair, date, commission);
          this.view.showString(
              String.format(Constants.STOCK_BOUGHT_SUCCESSFULLY, stockPair.getKey()));
        } catch (IllegalArgumentException e) {
          this.view.showString(e.getMessage());
        }
        break;
      }
      case 2: {
        try {
          this.model.sellStock(portfolioName, stockPair, date, commission);
          this.view.showString(
              String.format(Constants.STOCK_SOLD_SUCCESSFULLY, stockPair.getKey()));
        } catch (IllegalArgumentException e) {
          this.view.showString(e.getMessage());
        }
        break;
      }
      default:
        this.view.showOptionError();
        break;
    }
  }
}
