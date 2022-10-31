package constants;

public final class CSVConstants {

  public static final String SEPARATOR = ",";
  public static final String EXTENSION = ".csv";
  public static final String[] STOCK_CSV_HEADERS = new String[]{
      "symbol",
      "quantity",
      "date"
  };

  private CSVConstants() {
  }
}
