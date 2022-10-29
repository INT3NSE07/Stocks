package utilities;

public final class StringUtils {

  private StringUtils() {
  }

  public static boolean IsNullOrWhiteSpace(String value) {
    if (value == null) {
      return true;
    }

    return value.trim().equals("");
  }

  public static String getFileNameWithoutExtension(String fileName) {
    if (fileName.indexOf(".") > 0) {
      return fileName.substring(0, fileName.lastIndexOf("."));
    } else {
      return fileName;
    }
  }
}
