package view;

public interface IPortfolioView {

  void showString(String s);

  void showOptions(int selectedMenuItem);

  void showStringEntry();

  void showOptionError();

  void showSelectOption();

  void showMainOptions();

  void displayHeader(int menuItemNumber);

  String showOutputStream();

  void showSubMenuOptions(int selectedMenuItem);

  void promptPortfolioName();

  void promptPortfolioType();

  void promptStockQuantity();

  void promptStockSymbol();
}
