package stocks.view;

import stocks.portfolio.Portfolio;

/**
 * This interface represents a View object which contains methods to inform the user of various
 * messages that the controller sees fit.
 */
public interface PortfolioView {

  /**
   * Prompts all the valid options of operations supported by the program to the user.
   */
  void showOptions();

  /**
   * Informs the user of the composition of a portfolio.
   * @param p the portfolio that the user wants the composition of.
   */
  void printComposition(Portfolio p, String date);

  /**
   * Informs the user of a message decided by the controller.
   * @param message the message to be given to the user
   */
  void printMessage(String message);

}
