package view;

import java.io.PrintStream;

import static constants.MenuConstants.MainMenuItem;

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

  @Override
  public void showSelectOption() {
    out.println("Please Select an Menu Option: ");
  }

  @Override
  public void showMainOptions() {
    for (int i = 0; i <MainMenuItem.length ; i++) {
      this.out.println((i+1) +") "+MainMenuItem[i]);
    }
  }

  @Override
  public void displayHeader(int menuItemNumber) {
//    MenuItem[menuItemNumber]
//    if(menuItemNumber == 0) {
//      out.println("Portfolio Management Services");
//    } else if () {
//
//    }
    for (int i = 0; i <MainMenuItem.length ; i++) {
      if(menuItemNumber == 0) {
        this.out.println("Portfolio Management Services");
        break;
      } else if (i+1 == menuItemNumber) {
        this.out.println(MainMenuItem[i]);
        break;
      }
    }
  }

  @Override
  public String showOutputStream() {
    return this.out.toString();
  }

  @Override
  public void showSubMenuOptions(int selectedMenuItem) {
      if(selectedMenuItem==1) {
        this.out.println("1) Add Portfolio Name and Type\n2) Add Stocks\n3) Back");
      }
  }

  @Override
  public void promptPortfolioName() {
    this.out.println("Enter Portfolio Name: ");
  }

  @Override
  public void promptPortfolioType() {
    this.out.println("Enter Portfolio Type: ");
  }
}
