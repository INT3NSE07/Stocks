package stocks.view;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import stocks.portfolio.Portfolio;

/**
 * This class implements the PortfolioView interface.
 */
public class PortfolioViewImpl implements PortfolioView {

  private final PrintStream out;
  private final Map<Integer, String> options;

  /**
   * This constructor creates a new PortfolioView object with its PrintStream being given by the
   * user.
   * @param out the type of PrintStream
   */
  public PortfolioViewImpl(PrintStream out) {
    this.out = out;
    options = new HashMap<>();
    options.put(1, "Load portfolio from a file");
    options.put(2, "Create portfolio manually");
    options.put(3, "Check value of a portfolio");
    options.put(4, "View composition of a portfolio");
    options.put(5, "Save portfolio to a file");
    options.put(6, "View all portfolio names");
    options.put(7, "Create flexible portfolio manually");
    options.put(8, "Load flexible portfolio from file");
    options.put(9, "Buy stock for a flexible portfolio");
    options.put(10, "Sell stock for a flexible portfolio");
    options.put(11, "View cost basis of a flexible portfolio");
    options.put(12, "View performance of portfolio over time");
  }

  @Override
  public void showOptions() {
    out.println("\nMenu: ");
    options.forEach((k,v) -> out.println(k + " " + v));
    out.println("Q Exit the program");
    out.print("Enter your choice to perform action: ");
  }

  @Override
  public void printComposition(Portfolio p, String date) {
    Map<String, String> stocks = p.viewStocks(date);
    stocks.forEach((k, v) -> out.println(k + " " + v));
  }

  @Override
  public void printMessage(String message) {
    out.println(message);
  }
}
