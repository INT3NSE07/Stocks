package constants;

public final class CSVConstants {

  private CSVConstants() {
  }

  public static final String SEPARATOR = ",";

  public static final String[] CSV_HEADER = new String[]{
      "symbol",
      "date",
      "open",
      "high",
      "low",
      "close",
      "volume"
  };
}
