package constants;

import enums.MenuItems;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * An immutable constants class that holds immutable constants fields.
 */
public final class Constants {

  public static final String DATA_DIR = "portfolios/default_user";

  public static final String STOCK_DATA_PATH = "stock_data";
  public static final String PORTFOLIO_MAPPING_PATH = "portfolios_mapping";

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
  public static final String PROMPT_COMMISSION_KEY = "PROMPT_COMMISSION_KEY";
  public static final String PROMPT_START_DATE_KEY = "PROMPT_START_DATE_KEY";
  public static final String PROMPT_END_DATE_KEY = "PROMPT_END_DATE_KEY";
  public static final Map<String, String> TEXT_VIEW_CONSTANTS = Map.ofEntries(
      Map.entry(PROMPT_CHOICE, "Enter your choice"),
      Map.entry(PROMPT_PORTFOLIO_NAME_KEY, "Enter portfolio name"),
      Map.entry(PROMPT_STOCK_SYMBOL_KEY,
          "Enter stock symbol"),
      Map.entry(PROMPT_DATE_KEY,
          "Enter date in format YYYY-MM-DD (if no input is given, the default is current date)"),
      Map.entry(PROMPT_QUANTITY_KEY, "Enter quantity"),
      Map.entry(PROMPT_COMMISSION_KEY,
          "Please enter commission for this instance of transaction"),
      Map.entry(PROMPT_START_DATE_KEY,
          "Enter start date in format YYYY-MM-DD"),
      Map.entry(PROMPT_END_DATE_KEY,
          "Enter end date in format YYYY-MM-DD (if no input is given, the default "
              + "is current date)")
  );
  public static final String EXITING_STATUS = "Exiting...";
  public static final String PORTFOLIO_FETCH_FAIL = "The fetching of portfolio %s has failed.";
  public static final String SYMBOL_FETCH_FAIL = "The stock symbol %s does not exist.";
  public static final String QUANTITY_MUST_BE_A_WHOLE_NUMBER =
      "Invalid quantity. Quantity must be a whole number.";
  //  public static final String TRANSACTIONS_SUBMENU = "TRANSACTIONS_SUBMENU";
  public static final String GOING_BACK_STATUS = "Going back...";
  public static final String INVALID_PORTFOLIO_TYPE = "Invalid portfolio type.";

//  public static final String PORTFOLIO_OPTIONS = "PORTFOLIO_OPTIONS";

//  public static final String INFLEXIBLE_PORTFOLIO_MAIN_MENU = "INFLEXIBLE_PORTFOLIO_MAIN_MENU";

  public static final Map<Integer, String[]> MENU_TYPE = Map.ofEntries(
      Map.entry(MenuItems.TYPE_OF_PORTFOLIO.getValue(), new String[]{
          "Enter the type of portfolio",
          "Inflexible portfolio",
          "Flexible portfolio",
          "Exit"
      }),
      Map.entry(MenuItems.INFLEXIBLE_PORTFOLIO.getValue(), new String[]{
          "Portfolio Management Services",
          "Create a portfolio",
          "Examine a portfolio",
          "Determine value of a portfolio on a certain date",
          "Back"
      }),
      Map.entry(MenuItems.FLEXIBLE_PORTFOLIO.getValue(), new String[]{
          "Portfolio Management Services",
          "Create a portfolio",
          "Examine a portfolio",
          "Determine value of a portfolio on a certain date",
          "Make a transactions in portfolio",
          "Calculate cost basis",
          "Get performance of portfolio over period",
          "Back"
      }),
      Map.entry(MenuItems.CREATE_PORTFOLIO.getValue(), new String[]{
          "Create Portfolio Menu",
          "Add a stock to this portfolio",
          "Back"
      }),
      Map.entry(MenuItems.CREATE_TRANSACTION.getValue(), new String[]{
          "Transaction Menu",
          "Buy Stocks",
          "Sell Stocks",
          "Back"
      })
  );

  public static final int INFLEXIBLE_EXIT_CODE =
      Constants.MENU_TYPE.get(MenuItems.INFLEXIBLE_PORTFOLIO.getValue()).length - 1;
  public static final int FLEXIBLE_EXIT_CODE =
      Constants.MENU_TYPE.get(MenuItems.FLEXIBLE_PORTFOLIO.getValue()).length - 1;
  public static final int PORTFOLIO_OPTIONS_EXIT_CODE =
      Constants.MENU_TYPE.get(MenuItems.TYPE_OF_PORTFOLIO.getValue()).length - 1;
  public static final int CREATE_PORTFOLIO_EXIT_CODE =
      Constants.MENU_TYPE.get(MenuItems.CREATE_PORTFOLIO.getValue()).length - 1;
  public static final int TRANSACTION_SUBMENU_EXIT_CODE =
      Constants.MENU_TYPE.get(MenuItems.CREATE_TRANSACTION.getValue()).length - 1;
  public static final int BAR_CHART_MIN_LINES = 5;
  public static final int BAR_CHART_MAX_LINES = 30;
  public static final int BAR_CHART_MAX_STARS = 50;
  public static final String BAR_CHART_MIN_DAYS_INPUT =
      "The minimum number of days to analyze the portfolio performance is " + BAR_CHART_MIN_LINES;
  public static final String COST_BASIS = "Cost basis of portfolio %s on is %.2f";
  public static final String API_ERROR_MESSAGE = "Invalid API call";
  public static final String COMMISSION_NON_NEGATIVE =
      "Commission for a stock transaction cannot be negative.";

  private Constants() {
  }
}
