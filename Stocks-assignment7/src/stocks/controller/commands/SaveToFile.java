package stocks.controller.commands;

import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to save a file.
 */
public class SaveToFile extends AbstractCommand {

  /**
   * This is a constructor for a SaveToFile object which works with the model, view and
   * input stream the PortfolioController works with.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public SaveToFile(PortfolioModel model, PortfolioView view, Scanner in) {
    super(model, view, in);
  }

  @Override
  public void process() {
    view.printMessage("\nEnter name for portfolio: ");
    String name = in.nextLine();
    try {
      model.savePortfolioToFile(name);
    } catch (RuntimeException e) {
      view.printMessage(e.getMessage());
    }
    view.printMessage("File saved with name " + name + ".csv");
  }

}
