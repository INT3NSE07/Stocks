package constants;

/**
 * This class holds the constants related to a CSV file.
 */
public final class CSVConstants {

  public static final String SEPARATOR = ",";
  public static final String EXTENSION = ".csv";
  public static final String[] STOCK_CSV_HEADERS = new String[]{
      "symbol",
      "quantity",
      "date",
      "operation",
      "commission"
  };
  public static final String[] PORTFOLIO_CSV_HEADERS = new String[]{
      "name",
      "type"
  };

  private CSVConstants() {
  }
}
