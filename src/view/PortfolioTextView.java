package view;

import java.io.PrintStream;

public class PortfolioTextView implements IPortfolioView {

  private final PrintStream out;

  public PortfolioTextView(PrintStream out) {
    this.out = out;
  }

  @Override
  public void showString(String s) {
    out.println(s);
  }

  @Override
  public void showOptions() {
    // print the UI

    out.println("Menu: ");
    out.println("E: Enter a string");
    out.println("Q: Quit the program");
    out.print("Enter your choice: ");
  }

  @Override
  public void showStringEntry() {
    out.print("\nEnter the string to be echoed: ");
  }

  @Override
  public void showOptionError() {
    out.print("\nInvalid option. Please try again.");
  }
}
