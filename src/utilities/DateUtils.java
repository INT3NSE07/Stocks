package utilities;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A utility class that contains common date helpers.
 */
public final class DateUtils {

  private DateUtils() {
  }

  /**
   * Obtains the current date from the system clock in the system default timezone and formats it
   * using the specified {@link DateTimeFormatter} format.
   *
   * @param dateTimeFormatter the date-time formatter used to format the current date-time
   * @return the current date using the system clock
   */
  public static String getCurrentDate(DateTimeFormatter dateTimeFormatter) {
    return ZonedDateTime.now(ZoneOffset.systemDefault())
        .format(dateTimeFormatter);
  }

  /**
   * Checks if the specified date string is a valid {@link java.util.Date} using the specific
   * date-time formatter.
   *
   * @param date              the date text to parse
   * @param dateTimeFormatter the date-time formatter to use
   * @return true if the specified date string is a valid {@link java.util.Date}, else false
   */
  public static boolean isValidDate(String date, DateTimeFormatter dateTimeFormatter) {
    try {
      LocalDate givenDate = LocalDate.parse(date, dateTimeFormatter);
      LocalDate currentDate = LocalDate.parse(getCurrentDate(dateTimeFormatter), dateTimeFormatter);
      if (givenDate.compareTo(currentDate) > 0) {
        return false;
      }
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }
}
