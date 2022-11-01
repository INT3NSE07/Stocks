package constants;

import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * An immutable constants class that holds immutable constants fields which is used in
 * <li>{@link repository.CSVPortfolioRepository}</li>
 * <li>{@link service.FileStockService}</li>
 * <li>{@link view.PortfolioTextView}</li>
 */
public final class Constants {

  public static final String DATA_DIR = "resources/default_user";

  public static final String STOCK_DATA_PATH = "resources/stock_data";

  public static final DateTimeFormatter DEFAULT_DATETIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

  public static final String PORTFOLIO_EXISTS = "A portfolio with this name already exists.";

  public static final String PORTFOLIO_DOES_NOT_EXIST =
      "A portfolio with this name does not exist.";

  public static final String NO_STOCK_DATA_FOUND =
      "No stock data found for %s on %s";

  public static final String INPUT_NULL_OR_EMPTY = "Input cannot be null or empty.";
  public static final String DATE_INVALID = "Date is invalid.";
  public static final String QUANTITY_NON_NEGATIVE_AND_ZERO = "Quantity of a stock cannot be negative or zero.";

  // Text UI constants
  public static final String[] MAIN_MENU_ITEMS = {
      "Portfolio Management Services",
      "Create a portfolio",
      "Examine a portfolio",
      "Determine value of a portfolio on a certain date",
      "Exit"
  };

  public static final String[] CREATE_PORTFOLIO_SUBMENU_ITEMS = {
      "Add a stock to this portfolio",
      "Back"
  };

  public static final String PROMPT_CHOICE = "PROMPT_CHOICE";
  public static final String PROMPT_PORTFOLIO_NAME_KEY = "PROMPT_PORTFOLIO_NAME";
  public static final String PROMPT_STOCK_SYMBOL_KEY = "PROMPT_STOCK_SYMBOL";
  public static final String PROMPT_DATE_KEY = "PROMPT_DATE";
  public static final String PROMPT_QUANTITY_KEY = "PROMPT_QUANTITY";

  public static final Map<String, String> TEXT_VIEW_CONSTANTS = Map.ofEntries(
      Map.entry(PROMPT_CHOICE, "Enter your choice"),
      Map.entry(PROMPT_PORTFOLIO_NAME_KEY, "Enter portfolio name"),
      Map.entry(PROMPT_STOCK_SYMBOL_KEY, "Enter stock symbol"),
      Map.entry(PROMPT_DATE_KEY, "Enter date (if no input is given, the default is current date)"),
      Map.entry(PROMPT_QUANTITY_KEY, "Enter quantity")
  );


  private Constants() {
  }
}
