package stocks.controller;

import java.text.ParseException;

/**
 * This interface represents a Controller object for the application which lets users perform
 * various operations.
 */
public interface PortfolioController {

  /**
   * Gives control to the controller after it has been initialized. It is responsible for taking
   * user input and deciding whether the task to be done is delegated to the model or the view.
   */
  void start() throws ParseException;
}
