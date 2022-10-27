package view;

public interface IPortfolioView {

  void showString(String s);

  void showOptions();

  void showStringEntry();

  void showOptionError();

  void showSelectOption();

  void showMainOptions();

  void displayHeader(int menuItemNumber);

  String showOutputStream();

  void showSubMenuOptions(int selectedMenuItem);

  void promptPortfolioName();

  void promptPortfolioType();
}
