package constants;

public final class Constants {

  public static final String DATA_DIR = "resources/default_user";

  public static final String STOCK_DATA_PATH = "resources/stock_data";

  public static final String[] MAIN_MENU_ITEMS = {
      "Portfolio Management Services",
      "Create a portfolio",
      "Examine a portfolio",
      "Determine value of a portfolio on a date",
      "Exit"
  };

  public static final String[] CREATE_PORTFOLIO_SUBMENU_ITEMS = {
      "Add a stock to this portfolio",
      "Back"
  };

  private Constants() {
  }
}
