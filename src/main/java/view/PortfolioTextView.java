package view;

import constants.Constants;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import model.Portfolio;
import model.Stock;
import utilities.DisplayUtils;
import utilities.Pair;

/**
 * This class represents the portfolio view. The controller takes inputs from the user and tells the
 * view what to show.
 */
public class PortfolioTextView implements IPortfolioView {

  private final PrintStream out;

  /**
   * A {@link PortfolioTextView} constructor to initialize the PrintStream.
   *
   * @param out A PrintStream object that assigned to this objects output PrintStream.
   */
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
      case 4:
        for (int i = 0; i < Constants.TRANSACTIONS_SUBMENU.length; i++) {
          this.out.println((i + 1) + ") " + Constants.TRANSACTIONS_SUBMENU[i]);
        }
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
  public void showPortfolio(Portfolio portfolio) {
    String portfolioName = portfolio.getName();
    List<Stock> stocks = portfolio.getStocks();
    if (stocks.size() == 0) {
      this.out.printf("%nThe portfolio %s has no stocks.%n", portfolioName);
    }

    this.out.printf("%nComposition of the portfolio %s%n", portfolioName);

    DisplayUtils.TextTableGenerator tableGenerator = new DisplayUtils.TextTableGenerator(this.out);
    List<String> headers = new ArrayList<>(List.of("ID", "Ticker Symbol", "No of Stocks"));
    for (String header : headers) {
      tableGenerator.addHeader(header);
    }

    for (int i = 0; i < stocks.size(); i++) {
      List<String> row = new ArrayList<>();

      Stock stock = stocks.get(i);
      row.add(String.valueOf(i + 1));
      row.add(stock.getSymbol());
      row.add(String.valueOf(stock.getQuantity()));

      tableGenerator.addRow(row);
    }

    tableGenerator.printTable();
  }

  @Override
  public void showPortfolioValue(Pair<Portfolio, Double> portfolioValue) {
    Portfolio portfolio = portfolioValue.getKey();
    String portfolioName = portfolio.getName();
    List<Stock> stocks = portfolio.getStocks();

    if (stocks.size() == 0) {
      this.out.printf("%nThe portfolio %s has no stocks.%n", portfolioName);
    }

    String date = stocks.get(0).getDate();
    this.out.printf("%nValue of the portfolio %s on %s%n", portfolio.getName(), date);

    DisplayUtils.TextTableGenerator tableGenerator = new DisplayUtils.TextTableGenerator(this.out);
    List<String> headers = new ArrayList<>(
        List.of("ID", "Ticker symbol", "Quantity", "Closing price"));
    for (String header : headers) {
      tableGenerator.addHeader(header);
    }

    for (int i = 0; i < stocks.size(); i++) {
      List<String> row = new ArrayList<>();

      Stock stock = stocks.get(i);
      row.add(String.valueOf(i + 1));
      row.add(stock.getSymbol());
      row.add(String.valueOf(stock.getQuantity()));
      row.add(String.valueOf(stock.getClose()));

      tableGenerator.addRow(row);
    }

    tableGenerator.printTable();

    this.out.printf("%nTotal value: $%.2f%n", portfolioValue.getValue());
  }
}
