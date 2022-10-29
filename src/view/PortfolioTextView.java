package view;

import constants.Constants;
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
  public void showOptions(int selectedMenuItem) {
    // print the UI

//    out.println("Menu: ");
//    out.println("E: Enter a string");
//    out.println("Q: Quit the program");
//    out.print("Enter your choice: ");

    this.out.println(System.lineSeparator() + Constants.MAIN_MENU_ITEMS[selectedMenuItem]);

    switch (selectedMenuItem) {
      case 0:
        for (int i = 1; i < Constants.MAIN_MENU_ITEMS.length; i++) {
          this.out.println(i + ") " + Constants.MAIN_MENU_ITEMS[i]);
        }
        break;
      case 1:
        for (int i = 0; i < Constants.CREATE_PORTFOLIO_SUBMENU_ITEMS.length; i++) {
          this.out.println((i + 1) + ") " + Constants.CREATE_PORTFOLIO_SUBMENU_ITEMS[i]);
        }
        break;
      default:
        break;
    }
  }

  @Override
  public void showStringEntry() {
    out.print("\nEnter the string to be echoed: ");
  }

  @Override
  public void showOptionError() {
    this.out.println("Invalid option. Please try again." + System.lineSeparator());
  }

  @Override
  public void showSelectOption() {
    this.out.print("Enter your choice: ");
  }

  @Override
  public void showMainOptions() {
//    this.displayHeader(selectedMenuItem);
////
////    for (int i = 1; i < Constants.MAIN_MENU_ITEMS.length; i++) {
////      this.out.println(i + ") " + Constants.MAIN_MENU_ITEMS[i]);
////    }
  }

  @Override
  public void displayHeader(int menuItemNumber) {
    //this.out.println(Constants.MAIN_MENU_ITEMS[menuItemNumber]);
  }

  @Override
  public String showOutputStream() {
    return this.out.toString();
  }

  @Override
  public void showSubMenuOptions(int selectedMenuItem) {
    if (selectedMenuItem == 1) {
      this.out.println("1) Add Portfolio Name and Type\n2) Add Stocks\n3) Back");
    }
  }

  @Override
  public void promptPortfolioName() {
    this.out.print("Enter portfolio name: ");
  }

  @Override
  public void promptStockSymbol() {
    this.out.print("Enter ticker symbol: ");
  }

  @Override
  public void promptStockQuantity() {
    this.out.print("Enter quantity: ");
  }


  @Override
  public void promptPortfolioType() {
    this.out.println("Enter Portfolio Type: ");
  }
}
