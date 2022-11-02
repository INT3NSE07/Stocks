package view;

import model.Portfolio;
import utilities.Pair;

/**
 * The interface represents the view of the {@link Portfolio}.
 */
public interface IPortfolioView {

  /**
   * Displays the specified string to the user.
   *
   * @param s the string to be displayed to the user.
   */
  void showString(String s);

  /**
   * Displays options to the user based on the selected menu item.
   *
   * @param selectedMenuItem the selected menu item.
   */
  void showOptions(int selectedMenuItem);

  /**
   * Displays an error message to the user.
   */
  void showOptionError();

  /**
   * Displays a prompt to enter input to the user.
   *
   * @param key the key defined in {@link constants.Constants} used to determine the corresponding
   *            prompt message to display to the user.
   */
  void showPrompt(String key);

  /**
   * Displays {@link Portfolio} object in a table format to the user.
   *
   * @param portfolio {@link Portfolio} object that needs to be displayed.
   */
  void showPortfolio(Portfolio portfolio);

  /**
   * Displays {@link Portfolio} object and its total value to the user.
   *
   * @param portfolioValue A {@link Pair} object of Portfolio linked to its value.
   */
  void showPortfolioValue(Pair<Portfolio, Double> portfolioValue);
}
