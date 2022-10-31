package controller;

/**
 * A {@link IPortfolioController} interface has methods that is used for delegating works to
 *                {@link model}, {@link view} which tells them when to perform which action.
 */
public interface IPortfolioController {

  /**
   * A run method is used as point of iterator through
   *            which controller runs till exit condition is met, other wise tells view and model to
   *            perform operations.
   */
  void run();
}
