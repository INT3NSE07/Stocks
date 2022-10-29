package constants;

public final class CSVConstants {

  public static final String SEPARATOR = ",";
  public static final String[] CSV_HEADER = new String[]{
      "symbol",
      "quantity",
      "date",
      "open",
      "high",
      "low",
      "close",
      "volume"
  };

  private CSVConstants() {
  }
}
