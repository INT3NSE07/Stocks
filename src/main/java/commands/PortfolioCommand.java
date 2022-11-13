package commands;

import java.io.IOException;

public interface PortfolioCommand {
  void go() throws IOException;
  void help();
}
