package commands;

import constants.Constants;
import enums.MenuItems;
import enums.PortfolioTypes;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.IPortfolioFacadeModel;
import utilities.Pair;
import utilities.StringUtils;
import view.IPortfolioView;

/**
 * This class represents a command in the command design pattern. It handles the creation
 * functionality of a portfolio.
 */
public class CreatePortfolio implements PortfolioCommand {

  private final IPortfolioFacadeModel model;

  private final IPortfolioView view;

  private final BufferedReader bufferedReader;

  /**
   * Constructs a {@link CreatePortfolio} command object and initializes the model, view and reader
   * fields.
   *
   * @param model          the model instance which is used to perform the actual operations
   * @param view           the view which displays output to the end user
   * @param bufferedReader the input stream through which user input is taken
   */
  public CreatePortfolio(IPortfolioFacadeModel model,
      IPortfolioView view, BufferedReader bufferedReader) {
    this.bufferedReader = bufferedReader;
    this.model = model;
    this.view = view;
  }

  @Override
  public void execute() throws IOException {
    this.view.showPrompt(Constants.PROMPT_PORTFOLIO_NAME_KEY);
    String portfolioName = this.bufferedReader.readLine();
    if (StringUtils.isNullOrWhiteSpace(portfolioName)) {
      this.view.showString(Constants.INPUT_NULL_OR_EMPTY);
      return;
    }

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    if (this.model.getPortfolioType() != PortfolioTypes.FLEXIBLE) {
      int selectedSubmenuItem = MenuItems.CREATE_PORTFOLIO.getValue();
      while (selectedSubmenuItem != Constants.CREATE_PORTFOLIO_EXIT_CODE) {
        this.view.showOptions(MenuItems.CREATE_PORTFOLIO.getValue());
        this.view.showPrompt(Constants.PROMPT_CHOICE);

        try {
          selectedSubmenuItem = Integer.parseInt(this.bufferedReader.readLine());
        } catch (NumberFormatException e) {
          this.view.showString(Constants.INVALID_OPTION);
          continue;
        }

        createPortfolioSubmenu(selectedSubmenuItem, stockPairs);
      }
    }

    try {
      this.model.createPortfolio(portfolioName, stockPairs);
    } catch (IOException e) {
      this.view.showString(
          String.format("The creation of portfolio %s has failed.", portfolioName));
      return;
    } catch (IllegalArgumentException e) {
      this.view.showString(e.getMessage());
      return;
    }

    this.view.showString(
        String.format("The portfolio %s has been created.", portfolioName));
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
}
