package constants;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import enums.MenuItem;

/**
 * An immutable constants class that holds immutable constants fields.
 */
public final class Constants {

  public static final String DATA_DIR = "portfolios/default_user";

  public static final String STOCK_DATA_PATH = "stock_data";

  public static final DateTimeFormatter DEFAULT_DATETIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

  public static final String PORTFOLIO_EXISTS = "A portfolio with this name already exists.";

  public static final String PORTFOLIO_DOES_NOT_EXIST =
      "A portfolio with this name does not exist.";

  public static final String NO_STOCK_DATA_FOUND =
      "No stock data found for %s on %s";

  public static final String INPUT_NULL_OR_EMPTY = "Input cannot be null or empty.";
  public static final String DATE_INVALID = "Date is invalid.";
  public static final String QUANTITY_NON_NEGATIVE_AND_ZERO =
      "Quantity of a stock cannot be negative or zero.";

  public static final String STOCK_FETCH_FAILED =
      "Error occurred while fetching stock data for symbol: %s on date: %s.";

  // Text UI constants

  public static final String INVALID_OPTION = "Invalid option. Please try again.";

//  public static final String CREATE_PORTFOLIO_SUBMENU_ITEMS = "CREATE_PORTFOLIO_SUBMENU_ITEMS";

  public static final String PROMPT_CHOICE = "PROMPT_CHOICE";
  public static final String PROMPT_PORTFOLIO_NAME_KEY = "PROMPT_PORTFOLIO_NAME";
  public static final String PROMPT_STOCK_SYMBOL_KEY = "PROMPT_STOCK_SYMBOL";
  public static final String PROMPT_DATE_KEY = "PROMPT_DATE";
  public static final String PROMPT_QUANTITY_KEY = "PROMPT_QUANTITY";

  public static final Map<String, String> TEXT_VIEW_CONSTANTS = Map.ofEntries(
      Map.entry(PROMPT_CHOICE, "Enter your choice"),
      Map.entry(PROMPT_PORTFOLIO_NAME_KEY, "Enter portfolio name"),
      Map.entry(PROMPT_STOCK_SYMBOL_KEY,
          "Enter stock symbol (the stock symbol must belong to the list of stocks in S&P 500)"),
      Map.entry(PROMPT_DATE_KEY,
          "Enter date in format YYYY-MM-DD (if no input is given, the default is current date)"),
      Map.entry(PROMPT_QUANTITY_KEY, "Enter quantity")
  );
  public static final String EXITING_STATUS = "Exiting...";
  public static final String PORTFOLIO_FETCH_FAIL = "The fetching of portfolio %s has failed.";
  public static final String SYMBOL_FETCH_FAIL = "The stock symbol %s does not exist.";
  public static final String QUANTITY_MUST_BE_A_WHOLE_NUMBER =
      "Invalid quantity. Quantity must be a whole number.";
//  public static final String TRANSACTIONS_SUBMENU = "TRANSACTIONS_SUBMENU";
  public static final String BACKING = "Going back to Type of Portfolio Menu";

//  public static final String PORTFOLIO_OPTIONS = "PORTFOLIO_OPTIONS";

//  public static final String INFLEXIBLE_PORTFOLIO_MAIN_MENU = "INFLEXIBLE_PORTFOLIO_MAIN_MENU";

  public static final Map<Integer, String[]> MENU_TYPE = Map.ofEntries(
          Map.entry(MenuItem.PORTFOLIO_OPTIONS.getValue(), new String[]{
                  "Type of Portfolios",
                  "Inflexible Portfolio",
                  "Flexible Portfolio",
                  "Exit"
          }),
          Map.entry(MenuItem.INFLEXIBLE_PORTFOLIO_MAIN_MENU.getValue(), new String[]{
                  "Portfolio Management Services",
                  "Create a portfolio",
                  "Examine a portfolio",
                  "Determine value of a portfolio on a certain date",
                  "Back"
          }),
          Map.entry(MenuItem.FLEXIBLE_PORTFOLIO_MAIN_MENU.getValue(), new String[]{
                  "Portfolio Management Services",
                  "Create a portfolio",
                  "Examine a portfolio",
                  "Determine value of a portfolio on a certain date",
                  "Make a transactions in portfolio",
                  "Calculate cost basis",
                  "Get performance of portfolio over period",
                  "Back"
          }),
          Map.entry(MenuItem.CREATE_PORTFOLIO_SUBMENU_ITEMS.getValue(), new String[]{
                  "Create Portfolio Menu",
                  "Add a stock to this portfolio",
                  "Back"
          }),
          Map.entry(MenuItem.TRANSACTIONS_SUBMENU.getValue(), new String[]{
                  "Transaction Menu",
                  "Buy Stocks",
                  "Sell Stocks",
                  "Back"
          })
  );

  public static final int INFLEXIBLE_EXIT_CODE = Constants.MENU_TYPE.get(MenuItem.INFLEXIBLE_PORTFOLIO_MAIN_MENU.getValue()).length - 1;
  public static final int FLEXIBLE_EXIT_CODE = Constants.MENU_TYPE.get(MenuItem.FLEXIBLE_PORTFOLIO_MAIN_MENU.getValue()).length - 1;
  public static final int PORTFOLIO_OPTIONS_EXIT_CODE = Constants.MENU_TYPE.get(MenuItem.PORTFOLIO_OPTIONS.getValue()).length - 1;;
  public static final int CREATE_PORTFOLIO_EXIT_CODE = Constants.MENU_TYPE.get(MenuItem.CREATE_PORTFOLIO_SUBMENU_ITEMS.getValue()).length - 1;;
  public static final int TRANSACTION_SUBMENU_EXIT_CODE = Constants.MENU_TYPE.get(MenuItem.TRANSACTIONS_SUBMENU.getValue()).length - 1;;

  private Constants() {
  }
}
