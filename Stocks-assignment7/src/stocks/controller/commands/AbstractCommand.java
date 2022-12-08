package stocks.controller.commands;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import stocks.model.PortfolioModel;
import stocks.view.PortfolioView;

/**
 * This class represents an AbstractCommand and implements PortfolioCommand. It holds helper
 * functions and a constuctor used by all the classes that inherit it.
 */
public abstract class AbstractCommand implements PortfolioCommand {

  PortfolioView view;
  PortfolioModel model;
  Scanner in;

  /**
   * Creates a PortfolioCommand type object that works with the model, view and input scanner
   * provided to the PortfolioController it is being used by.
   * @param model the model that the PortfolioController uses
   * @param view the view that the PortfolioController uses to show messages to the user
   * @param in the input scanner that the PortfolioController uses to get user input
   */
  public AbstractCommand(PortfolioModel model, PortfolioView view, Scanner in) {
    this.model = model;
    this.view = view;
    this.in = in;
  }

  /**
   * Executes the command.
   */
  public abstract void process() throws ParseException;

  /**
   * Helper method to check if a date entered by a user is valid.
   * @param date date passed in by the user
   * @return true if date is valid, else false
   */
  protected boolean checkValidDate(String date) {
    int yyyy;
    int mm;
    int dd;
    List<Integer> longMonth = (Arrays.asList(1, 3, 5, 7, 8, 10, 12));
    try {
      yyyy = Integer.parseInt(date.split("-")[0]);
      mm = Integer.parseInt(date.split("-")[1]);
      dd = Integer.parseInt(date.split("-")[2]);
    } catch (NumberFormatException e) {
      return false;
    }

    if (yyyy > 2022 || yyyy <= 2015) {
      return false;
    }
    if (mm > 12 || mm < 1) {
      return false;
    }
    if (dd < 1) {
      return false;
    }
    if (mm == 2 && dd > 28) {
      return false;
    }
    if (longMonth.contains(mm)) {
      return dd <= 31;
    } else {
      return dd <= 30;
    }
  }
}
