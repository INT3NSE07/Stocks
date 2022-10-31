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
  void showOptionError();

  /**
   *
   */
  void showSelectOption();

  /**
   *
   */
  void promptPortfolioName();

  /**
   *
   */
  void promptStockQuantity();

  /**
   *
   */
  void promptStockSymbol();

  /**
   *
   */
  void promptDate();
  
  /**
   *
   * @param readPortfolio
   */
  void showPortfolio(Portfolio readPortfolio);
}
