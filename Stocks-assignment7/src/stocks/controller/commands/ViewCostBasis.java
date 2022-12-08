package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This portfolio represents the getting the cost basis of a portfolio.
 */
public class ViewCostBasis extends AbstractCommand {
  /**
   * Creates a PortfolioCommand type object that works with the model, view and input scanner
   * provided to the PortfolioController it is being used by.
   *
   * @param model the model that the PortfolioController uses
   * @param view  the view that the PortfolioController uses to show messages to the user
   * @param in    the input scanner that the PortfolioController uses to get user input
   */
  public ViewCostBasis(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    view.printMessage("\nEnter name for portfolio: ");
    String name = in.nextLine();
    if (!model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio of name " + name + " does not exist.");
      return;
    }
    view.printMessage("Enter the year to check value of portfolio for: ");
    String year = in.nextLine();
    view.printMessage("Enter the month to check value of portfolio for "
            + "(1-January...12-December): ");
    String month = in.nextLine();
    if (month.length() < 2) {
      month = '0' + month;
    }
    view.printMessage("Enter the day to check value of portfolio for: ");
    String day = in.nextLine();
    if (day.length() < 2) {
      day = '0' + day;
    }
    String date = year + "-" + month + "-" + day;
    if (checkValidDate(date)) {
      view.printMessage("$" + model.getCostBasis(name, date));
    } else {
      view.printMessage("Invalid date entered. Please try again");
    }
  }
}
