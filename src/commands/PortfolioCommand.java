package commands;

import java.io.IOException;

/**
 * This interface represents a portfolio command which is used to implement the Command design
 * pattern.
 */
public interface PortfolioCommand {

  /**
   * This method is used to execute the portfolio commands.
   *
   * @throws IOException if the underlying portfolio model methods encounter any exceptions related
   *                     to I/O
   */
  void execute() throws IOException;
}
