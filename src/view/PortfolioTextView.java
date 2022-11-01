package view;

import constants.Constants;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import model.Portfolio;
import model.Stock;
import utilities.DisplayUtils;

public class PortfolioTextView implements IPortfolioView {

  private final PrintStream out;

  public PortfolioTextView(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showString(String s) {
    this.out.println(s);
  }

  @Override
  public void showOptions(int selectedMenuItem) {
    this.out.println(System.lineSeparator() + Constants.MAIN_MENU_ITEMS[selectedMenuItem]);

    switch (selectedMenuItem) {
      case 0:
        for (int i = 1; i < Constants.MAIN_MENU_ITEMS.length; i++) {
          this.out.println(i + ") " + Constants.MAIN_MENU_ITEMS[i]);
        }
        break;
      case 1:
        for (int i = 0; i < Constants.CREATE_PORTFOLIO_SUBMENU_ITEMS.length; i++) {
          this.out.println((i + 1) + ") " + Constants.CREATE_PORTFOLIO_SUBMENU_ITEMS[i]);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void showOptionError() {
    this.out.println(Constants.INVALID_OPTION + System.lineSeparator());
  }

  @Override
  public void showPrompt(String key) {
    this.out.print(Constants.TEXT_VIEW_CONSTANTS.get(key) + ": ");
  }

  @Override
  public void showPortfolio(Portfolio readPortfolio) {
    this.out.println(readPortfolio.getName());
    DisplayUtils.TableGenerator tableGenerator = new DisplayUtils.TableGenerator();
    List<String> headersList = new ArrayList<>();
    headersList.add("ID");
    headersList.add("Ticker Symbol");
    headersList.add("No of Stocks");
    List<List<String>> rowsList = new ArrayList<>();
//    rowsList.add(headersList);
    for (int i = 0; i < readPortfolio.getStocks().size(); i++) {
      List<String> row = new ArrayList<>();
      row.add(String.valueOf(i + 1));
      for (Stock stock : readPortfolio.getStocks()) {
        row.add(stock.getSymbol());
        row.add(String.valueOf(stock.getQuantity()));
      }
      rowsList.add(row);
    }
//    this.out.println(rowsList.get(0));
    this.out.println(tableGenerator.generateTable(headersList, rowsList));
  }
}
