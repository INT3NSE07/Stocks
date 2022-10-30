package utilities;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

  private DateUtils() {
  }

  public static String getCurrentDate() {
    return ZonedDateTime.now(ZoneOffset.systemDefault())
        .format(dateTimeFormatter);
  }

  public static boolean isValidDate(String dateStr) {
    try {
      LocalDate.parse(dateStr, dateTimeFormatter);
    } catch (DateTimeParseException e) {
      return false;
    }

    return true;
  }
}
