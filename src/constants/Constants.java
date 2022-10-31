package constants;

import java.time.format.DateTimeFormatter;

/**
 * An immutable constants class that holds immutable constants fields which is used in
 * <li>{@link repository.CSVPortfolioRepository}</li>
 * <li>{@link service.FileStockService}</li>
 * <li>{@link view.PortfolioTextView}</li>
 */
public final class Constants {

  public static final String DATA_DIR = "resources/default_user";

  public static final String STOCK_DATA_PATH = "resources/stock_data";

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

  public static final DateTimeFormatter DEFAULT_DATETIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;


  private Constants() {
  }
}
