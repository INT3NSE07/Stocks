package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to print all portfolio names.
 */
public class PrintAllPortfolioNames extends AbstractCommand {

  /**
   * This is a constructor for a PrintAllPortfolioNames object which works with the model, view and
   * input stream the PortfolioController works with.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public PrintAllPortfolioNames(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    if (model.getPortfolioList().size() == 0) {
      view.printMessage("No portfolios have been created yet.");
      return;
    }
    for (String name : model.getPortfolioNames()) {
      view.printMessage(name);
    }
  }
}
