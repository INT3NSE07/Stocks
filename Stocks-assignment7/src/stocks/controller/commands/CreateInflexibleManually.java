package stocks.controller.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to create an inflexible portfolio manually.
 */
public class CreateInflexibleManually extends AbstractCommand {

  /**
   * This is a constructor for a CreateInflexibleManually object which works with the model, view
   * and input stream the PortfolioController works with.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public CreateInflexibleManually(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    Map<String, String> stocks = new HashMap<>();
    view.printMessage("\nEnter name for portfolio: ");
    String name = in.nextLine();
    if (model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio with this name exists already. PLease try again with "
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
      if (!stocks.containsKey(ticker)) {
        stocks.put(ticker, shares);
      } else {
        stocks.put(ticker, String.valueOf(Double.parseDouble(stocks.get(ticker))
                + Double.parseDouble(shares)).split("\\.")[0]);
      }
      num--;
    }
    try {
      model.createInflexiblePortfolioManually(name, stocks);
    } catch (RuntimeException e) {
      view.printMessage(e.getMessage());
      return;
    }
    view.printMessage("Portfolio with name " + name + " created.");
  }
}
