package utilities;

import java.util.Random;

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
  public static boolean isNullOrWhiteSpace(String value) {
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

  /**
   * Generates a random string with the specified length consisting of lowercase alphabets.
   *
   * @param maxLength the maximum length of the generated string
   * @return a random string
   */
  public static String getRandomString(int maxLength) {
    int lowerBound = 'a';
    int upperBound = 'z';
    Random random = new Random();

    return random.ints(lowerBound, upperBound + 1)
        .limit(maxLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }
}
