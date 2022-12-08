package stocks.controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents the command to Create a flexible portfolio manually.
 */
public class CreateFlexibleManually extends AbstractCommand {

  /**
   * This is a constructor for a CreateFlexibleManually object which works with the model, view and
   * input stream the PortfolioController works with.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public CreateFlexibleManually(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    Map<String, Map<String, String>> stocks = new HashMap<>();
    view.printMessage("\nEnter name for portfolio: ");
    String name = in.nextLine();
    if (model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio with this name exists already. Please try again with "
              + "a different name.");
      return;
    }
    view.printMessage("Enter number of stocks in this portfolio: ");
    int num;
    try {
      num = Integer.parseInt(in.nextLine());
    } catch (NumberFormatException e) {
      view.printMessage("Please enter a valid integer.");
      return;
    }
    while (num > 0) {
      view.printMessage("Enter ticker of company to buy shares of: ");
      String ticker = in.nextLine();
      view.printMessage("Enter number of shares of company to buy: ");
      String shares;
      try {
        shares = in.nextLine();
        int sharesNum = Integer.parseInt(shares);
      } catch (NumberFormatException e) {
        view.printMessage("Number of shares must be an integer.");
        return;
      }
      Map<String, String> innerStocks;
      LocalDate now = LocalDate.now();
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      if (!stocks.containsKey(ticker)) {
        innerStocks = new HashMap<>();
        innerStocks.put(dtf.format(now), shares);
      } else {
        innerStocks = stocks.get(ticker);
        innerStocks.put(dtf.format(now) ,String.valueOf(Double.parseDouble(
                innerStocks.get(dtf.format(now)))
                + Double.parseDouble(shares)).split("\\.")[0]);
      }
      stocks.put(ticker, innerStocks);
      num--;
    }
    try {
      model.createFlexiblePortfolioManually(name, stocks);
    } catch (RuntimeException e) {
      view.printMessage(e.getMessage());
      return;
    }
    view.printMessage("Portfolio with name " + name + " created.");
  }
}
