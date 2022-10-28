package utilities;

public final class StringUtils {

  private StringUtils() {
  }

  public static boolean IsNullOrWhiteSpace(String value)
  {
    if (value == null) {
      return true;
    }

    return value.trim().equals("");
  }
}
