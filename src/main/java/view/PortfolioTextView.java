package view;

import constants.Constants;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Portfolio;
import model.PortfolioValue;
import model.Stock;
import utilities.DateUtils;
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

    DisplayUtils.TextTableGenerator tableGenerator = new DisplayUtils.TextTableGenerator(this.out);
    String[] headers = {"ID", "Ticker symbol", "Quantity" };
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
  public void showPortfolioValue(Pair<Portfolio, Double> portfolioValue, String date) {
    Portfolio portfolio = portfolioValue.getKey();
    String portfolioName = portfolio.getName();
    List<Stock> stocks = portfolio.getStocks();

    if (stocks.size() == 0) {
      this.out.printf("%nThe portfolio %s has no stocks.%n", portfolioName);
    }
    if (date == null) {
      date = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
    }

    this.out.printf("%nValue of the portfolio %s on %s%n", portfolio.getName(), date);

    DisplayUtils.TextTableGenerator tableGenerator = new DisplayUtils.TextTableGenerator(this.out);
    String[] headers = {"ID", "Ticker symbol", "Quantity", "Closing price" };
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

    this.out.printf("\nScale:  * = %.2f\n", scale);
  }
}
