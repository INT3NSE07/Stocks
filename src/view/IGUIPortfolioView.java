package view;

import controller.IPortfolioFeatures;
import java.io.IOException;
import model.Portfolio;

/**
 * The interface represents the GUI view of the {@link Portfolio}.
 */
public interface IGUIPortfolioView extends IPortfolioView {

  /**
   * Registers the callbacks from the GUI elements to the corresponding features.
   *
   * @param features the features to be added
   * @throws IOException if the underlying feature implementation encounters an exception related to
   *                     I/O
   */
  void addFeatures(IPortfolioFeatures features) throws IOException;

  /**
   * Resets the focus of an element.
   */
  void resetFocus();

  /**
   * Clears the content in the input fields.
   */
  void clearInputString();
}
