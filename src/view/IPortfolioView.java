package view;

import model.Portfolio;

/**
 *
 */
public interface IPortfolioView {

  /**
   *
   * @param s
   */
  void showString(String s);

  /**
   *
   * @param selectedMenuItem
   */
  void showOptions(int selectedMenuItem);

  /**
   *
   */
  void showStringEntry();

  /**
   *
   */
  void showOptionError();

  /**
   *
   */
  void showSelectOption();

  /**
   *
   */
  void showMainOptions();

  /**
   *
   * @param menuItemNumber
   */
  void displayHeader(int menuItemNumber);

  /**
   *
   * @return
   */
  String showOutputStream();

  /**
   *
   * @param selectedMenuItem
   */
  void showSubMenuOptions(int selectedMenuItem);

  /**
   *
   */
  void promptPortfolioName();

  /**
   *
   */
  void promptPortfolioType();

  /**
   *
   */
  void promptStockQuantity();

  /**
   *
   */
  void promptStockSymbol();

  void promptDate();
  
  /**
   *
   * @param readPortfolio
   */
  void showPortfolio(Portfolio readPortfolio);
}
