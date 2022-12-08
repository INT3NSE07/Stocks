package stocks.controller.commands;

import java.io.IOException;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to load a flexible portfolio from a file.
 */
public class LoadFlexibleFromFile extends AbstractCommand {
  /**
   * Creates a PortfolioCommand type object that works with the model, view and input scanner
   * provided to the PortfolioController it is being used by.
   *
   * @param model the model that the PortfolioController uses
   * @param view  the view that the PortfolioController uses to show messages to the user
   * @param in    the input scanner that the PortfolioController uses to get user input
   */
  public LoadFlexibleFromFile(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    view.printMessage("Enter name for portfolio: ");
    String name = in.nextLine();
    if (model.getPortfolioNames().contains(name)) {
      view.printMessage("Portfolio with this name exists already. Please try again with a "
              + "different name.");
      return;
    }
    view.printMessage("Enter path for portfolio: ");
    String filePath = in.nextLine();
    view.printMessage("Enter the year of first purchase in the portfolio: ");
    String year = in.nextLine();
    view.printMessage("Enter the month of first purchase "
            + "(1-January...12-December): ");
    String month = in.nextLine();
    if (month.length() < 2) {
      month = '0' + month;
    }
    view.printMessage("Enter the day of first purchase in portfolio : ");
    String day = in.nextLine();
    String date = year + "-" + month + "-" + day;
    view.printMessage("Date entered is: " + date);
    if (checkValidDate(date)) {
      view.printMessage("Enter commission fee paid on transactions: ");
      try {
        Double commission = in.nextDouble();
        model.createFlexiblePortfolioFromFile(name, filePath, date, commission);
      } catch (RuntimeException | IOException e) {
        view.printMessage(e.getMessage());
        return;
      }
    } else {
      view.printMessage("Invalid date entered. Please try again.");
      return;
    }
    view.printMessage("Portfolio with name " + name + " created.");
  }

}
