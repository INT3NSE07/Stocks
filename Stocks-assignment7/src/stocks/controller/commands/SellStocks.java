package stocks.controller.commands;

import java.text.ParseException;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.portfolio.PortfolioImpl;
import stocks.view.PortfolioView;

/**
 * This portfolio represents the sell stocks from a portfolio.
 */
public class SellStocks extends AbstractCommand {
  /**
   * Creates a SellStocks type object that works with the model, view and input scanner
   * provided to the PortfolioController it is being used by.
   *
   * @param model the model that the PortfolioController uses
   * @param view  the view that the PortfolioController uses to show messages to the user
   * @param in    the input scanner that the PortfolioController uses to get user input
   */
  public SellStocks(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    view.printMessage("\nEnter name of portfolio you want to sell stocks from: ");
    String name = in.nextLine();
    if (!model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio of name " + name + " does not exist");
      return;
    }
    if (model.getPortfolio(name) instanceof PortfolioImpl) {
      view.printMessage("Cannot sell stocks from an inflexible portfolio.");
      return;
    }
    view.printMessage("Enter ticker of stock you wish to sell: ");
    String ticker = in.nextLine();
    view.printMessage("Enter number of shares you wish to sell: ");
    String sellAmount = in.nextLine();
    try {
      int x = Integer.parseInt(sellAmount);
    } catch (NumberFormatException e) {
      view.printMessage("Please enter a valid integer for number of shares to sell.");
      return;
    }
    view.printMessage("Enter the year to sell stock on: ");
    String year = in.nextLine();
    view.printMessage("Enter the month to sell stock on "
            + "(1-January...12-December): ");
    String month = in.nextLine();
    if (month.length() < 2) {
      month = '0' + month;
    }
    view.printMessage("Enter the day to sell stock on: ");
    String day = in.nextLine();
    String date = year + "-" + month + "-" + day;
    if (checkValidDate(date)) {
      try {
        view.printMessage("Enter commission fee paid on transaction: ");
        Double commission = in.nextDouble();
        model.sellStock(name, Integer.valueOf(sellAmount), ticker, date, commission);
        view.printMessage("\nSelling " + sellAmount + " shares of " + ticker + " on  " + date);
      } catch (RuntimeException | ParseException e) {
        view.printMessage(e.getMessage());
      }
    } else {
      view.printMessage("Invalid date entered. Please try again.");
    }
  }
}
