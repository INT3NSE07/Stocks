package view;

import com.github.freva.asciitable.AsciiTable;
import constants.Constants;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;
import model.Portfolio;
import model.PortfolioValue;
import model.Stock;
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
//    try {
    String[] options = Constants.MENU_TYPE.get(selectedMenuItem);
    this.out.println(System.lineSeparator() + options[0]);
    for (int i = 1; i < options.length; i++) {
      this.out.println(i + ") " + options[i]);
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

    String[] headers = {"ID", "Ticker symbol", "Quantity" };
    String[][] data = new String[stocks.size()][];
    for (int i = 0; i < stocks.size(); i++) {
      Stock stock = stocks.get(i);
      data[i] = new String[]{
          String.valueOf(i + 1),
          stock.getSymbol(),
          String.valueOf(stock.getQuantity())
      };
    }

    out.println(AsciiTable.getTable(headers, data));
  }

  @Override
  public void showPortfolioValue(Pair<Portfolio, Double> portfolioValue) {
    Portfolio portfolio = portfolioValue.getKey();
    String portfolioName = portfolio.getName();
    List<Stock> stocks = portfolio.getStocks();
    String date = stocks.get(0).getDate();

    if (stocks.size() == 0) {
      this.out.printf("%nThe portfolio %s has no stocks.%n", portfolioName);
    }

    this.out.printf("%nValue of the portfolio %s on %s%n", portfolio.getName(), date);

    String[] headers = {"ID", "Ticker symbol", "Quantity", "Closing price", "Date" };
    String[][] data = new String[stocks.size()][];
    for (int i = 0; i < stocks.size(); i++) {
      Stock stock = stocks.get(i);
      data[i] = new String[]{
          String.valueOf(i + 1),
          stock.getSymbol(),
          String.valueOf(stock.getQuantity()),
          String.valueOf(stock.getClose()),
          date
      };
    }

    out.println(AsciiTable.getTable(headers, data));
    this.out.printf("%nTotal value: $%.2f%n", portfolioValue.getValue());
  }

  @Override
  public void showPortfolioPerformance(String portfolioName, String fromDate, String toDate,
      List<PortfolioValue> portfolioValues) {
    this.out.printf("Performance of portfolio %s from %s to %s\n\n", portfolioName, fromDate,
        toDate);

    double maxValue = portfolioValues.stream().max(Comparator.comparing(PortfolioValue::getValue))
        .get().getValue();
    double scale = maxValue / Constants.BAR_CHART_MAX_STARS;

    for (PortfolioValue portfolioValue : portfolioValues) {
      this.out.printf("%s - %s:  ", portfolioValue.getFromDate(), portfolioValue.getToDate());

      int stars = 1;
      double value = portfolioValue.getValue();
      if (value > scale) {
        stars = (int) (portfolioValue.getValue() / scale);
      }

      for (int i = 0; i < stars; i++) {
        this.out.print("*");
      }
      this.out.println();
    }

    this.out.printf("\nScale:  * = %s\n", scale);
  }
}
