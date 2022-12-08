package stocks.controller.commands;

import java.io.FileNotFoundException;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents a command to load an inflexible portfolio from file.
 */
public class LoadInflexibleFromFile extends AbstractCommand {

  /**
   * This is a constructor for a LoadInflexibleFromFile object which works with the model, view and
   * input stream the PortfolioController works with.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public LoadInflexibleFromFile(PortfolioModel model, PortfolioView view, Scanner in) {
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
    try {
      model.createInflexiblePortfolioFromFile(name, filePath);
    } catch (FileNotFoundException | RuntimeException e) {
      view.printMessage(e.getMessage());
      return;
    }
    view.printMessage("Portfolio with name " + name + " created.");
  }
}
