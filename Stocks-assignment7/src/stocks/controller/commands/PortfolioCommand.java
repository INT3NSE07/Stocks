package stocks.controller.commands;

import java.text.ParseException;

/**
 * This interface represents a PortfolioCommand. The command is decided by the PortfolioController
 * and executed when required through user input.
 */
public interface PortfolioCommand {

  /**
   * This method executes the command that the PortfolioController has received instructions for
   * from the user input.
   */
  void process() throws ParseException;
}
