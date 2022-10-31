package utilities;

/**
 * A utility class that contains string helpers.
 */
public final class StringUtils {

  private StringUtils() {
  }

  /**
   * Checks whether the specified string is null, empty, or consists only of white-space
   * characters.
   *
   * @param value the string to be tested
   * @return true if the value parameter is null, empty, or consists only of white-space characters
   */
  public static boolean IsNullOrWhiteSpace(String value) {
    if (value == null) {
      return true;
    }

    return value.trim().equals("");
  }

  /**
   * Removes the extension from a filename.
   *
   * @param fileName the name of the file
   * @return the name of the file without the extension
   */
  public static String getFileNameWithoutExtension(String fileName) {
    if (fileName.indexOf(".") > 0) {
      return fileName.substring(0, fileName.lastIndexOf("."));
    } else {
      return fileName;
    }
  }
}
