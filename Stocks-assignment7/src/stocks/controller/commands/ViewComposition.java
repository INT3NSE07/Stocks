package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to view a composition.
 */
public class ViewComposition extends AbstractCommand {

  /**
   * This is a constructor for a ViewComposition object which works with the model, view and
   * input stream the PortfolioController works with.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public ViewComposition(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    view.printMessage("\nEnter name for portfolio: ");
    String name = in.nextLine();
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
    String date = year + "-" + month + "-" + day;
    view.printMessage("Date entered is: " + date);
    if (checkValidDate(date)) {
      try {
        view.printComposition(model.getPortfolio(name), date);
      } catch (RuntimeException e) {
        view.printMessage(e.getMessage());
      }
    } else {
      view.printMessage("Invalid date entered. Please try again.");
    }
    if (!model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio of name " + name + " does not exist.");
    }

  }
}
