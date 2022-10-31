package view;

import model.Portfolio;

/**
 *
 */
public interface IPortfolioView {

  /**
   * @param key
   */
  void showString(String key);

  /**
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
  void showPrompt(String key);

  /**
   * @param readPortfolio
   */
  void showPortfolio(Portfolio readPortfolio);
}
