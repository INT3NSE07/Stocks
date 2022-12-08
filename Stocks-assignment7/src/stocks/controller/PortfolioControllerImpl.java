package stocks.controller;

import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import stocks.controller.commands.BuyStocks;
import stocks.controller.commands.CheckValueOfPortfolio;
import stocks.controller.commands.CreateFlexibleManually;
import stocks.controller.commands.CreateInflexibleManually;
import stocks.controller.commands.LoadFlexibleFromFile;
import stocks.controller.commands.PortfolioCommand;
import stocks.controller.commands.LoadInflexibleFromFile;
import stocks.controller.commands.PrintAllPortfolioNames;
import stocks.controller.commands.RebalanceAPortfolio;
import stocks.controller.commands.SaveToFile;
import stocks.controller.commands.SellStocks;
import stocks.controller.commands.ViewComposition;
import stocks.controller.commands.ViewCostBasis;
import stocks.controller.commands.ViewPerformanceOverTime;
import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class implements the PortfolioController interface.
 */
public class PortfolioControllerImpl implements PortfolioController {

  private final PortfolioView view;
  private final Scanner in;
  private final Map<String, PortfolioCommand> knownCommands;

  /**
   * Creates an object of PortfolioController which is responsible for taking in user input and
   * processing what needs to be done, then delegates the work to either the view or the model
   * being passed through it.
   *
   * @param inStream the source where the user input is taken from
   * @param view     the view that is responsible for giving feedback to user
   * @param model    the model that has the functionalities offered by the program
   */
  public PortfolioControllerImpl(InputStream inStream, PortfolioView view, PortfolioModel model) {
    this.view = view;
    this.in = new Scanner(inStream);
    knownCommands = new HashMap<>();
    knownCommands.put("1", new LoadInflexibleFromFile(model, view, this.in));
    knownCommands.put("2", new CreateInflexibleManually(model, view, this.in));
    knownCommands.put("3", new CheckValueOfPortfolio(model, view, this.in));
    knownCommands.put("4", new ViewComposition(model, view, this.in));
    knownCommands.put("5", new SaveToFile(model, view, this.in));
    knownCommands.put("6", new PrintAllPortfolioNames(model, view, this.in));
    knownCommands.put("7", new CreateFlexibleManually(model, view, this.in));
    knownCommands.put("8", new LoadFlexibleFromFile(model, view, this.in));
    knownCommands.put("9", new BuyStocks(model, view, this.in));
    knownCommands.put("10", new SellStocks(model, view, this.in));
    knownCommands.put("11", new ViewCostBasis(model, view, this.in));
    knownCommands.put("12", new ViewPerformanceOverTime(model, view, this.in));
    knownCommands.put("13", new RebalanceAPortfolio(model, view, this.in));
  }

  /**
   * Gives control of the program to the controller. Takes in user input after showing valid choices
   * and deciding what to do on the basis of that. (Give control to view to print something, give
   * input to model to perform some operations etc.).
   */
  @Override
  public void start() throws ParseException {
    while (true) {
      PortfolioCommand cmd;
      view.showOptions();
      String input = in.nextLine();
      if (input.equalsIgnoreCase("Q")) {
        break;
      }
      if (!knownCommands.containsKey(input)) {
        view.printMessage("Please enter a valid command.");
        continue;
      }
      cmd = knownCommands.get(input);
      cmd.process();
    }
  }
}
