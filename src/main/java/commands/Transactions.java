package commands;

import java.io.BufferedReader;
import java.io.IOException;

import constants.Constants;
import model.IFlexiblePortfolioModel;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

public class Transactions implements PortfolioCommand {

  private final IFlexiblePortfolioModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  public Transactions(IFlexiblePortfolioModel model, IPortfolioView view, BufferedReader bufferedReader) {
    this.model = model;
    this.view =view;
    this.bufferedReader = bufferedReader;
  }

  @Override
  public void go() throws IOException {
    int selectedSubmenuItem = 0;
    double commission = 0;
    this.view.showString(String.format("Please Enter commission for this instance of transaction: "));

    try {
      commission = Double.parseDouble(this.bufferedReader.readLine());
    } catch (NumberFormatException numberFormatException) {
      this.view.showString("Please Enter valid commission value in $");
      return;
    }

    while (selectedSubmenuItem != 3) {
      this.view.showOptions(4);
      this.view.showPrompt(Constants.PROMPT_CHOICE);

      try {
        selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());
      } catch (NumberFormatException e) {
        this.view.showString(Constants.INVALID_OPTION);
        break;
      }
      createTransactionSubMenuItem(selectedSubmenuItem, commission);
    }
  }

  private void createTransactionSubMenuItem(int selectedSubmenuItem, double commission) throws IOException {

    if (selectedSubmenuItem == 3) {
      return;
    }
    this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
    Pair stockPair = new Pair<>(null, null);
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
        } catch (IllegalArgumentException illegalArgumentException) {
          this.view.showString(Constants.DATE_INVALID);
        }
        break;
      }
      case 2: {
        this.model.sellStock(portfolioName, stockPair, date, commission);
        break;
      }
      default:
        this.view.showOptionError();
        break;
    }
  }




  @Override
  public void help() {

  }
}
