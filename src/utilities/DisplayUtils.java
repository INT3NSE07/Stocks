package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A utility class that contains display related helpers.
 */
public final class DisplayUtils {

  private DisplayUtils() {
  }

  public static final class TextTableGenerator {

    private static final String HORIZONTAL_SEP = "-";
    private static final String VERTICAL_SEP = "|";
    private static final String JOIN_SEP = "+";
    private final List<List<String>> rows;
    private final List<String> headers;

    public TextTableGenerator() {
      this.headers = new ArrayList<>();
      this.rows = new ArrayList<>();
    }

    public void addHeader(String header) {
      this.headers.add(header);
    }

    public void addRow(List<String> cells) {
      rows.add(cells);
    }

    public void printTable() {
      int[] cellWidths = new int[headers.size()];

      for (List<String> row : rows) {
        for (int i = 0; i < row.size(); i++) {
          cellWidths[i] = Math.max(headers.get(i).length(), row.get(i).length());
        }
      }

      printLine(cellWidths);
      printRow(headers, cellWidths);
      printLine(cellWidths);

      for (List<String> row : rows) {
        printRow(row, cellWidths);
      }
      printLine(cellWidths);
    }

    private void printLine(int[] cellWidths) {
      for (int i = 0; i < cellWidths.length; i++) {
        String line = String.join("", Collections.nCopies(cellWidths[i] +
            VERTICAL_SEP.length() + 1, HORIZONTAL_SEP));
        System.out.print(JOIN_SEP + line + (i == cellWidths.length - 1 ? JOIN_SEP : ""));
      }

      System.out.println();
    }

    private void printRow(List<String> row, int[] maxWidths) {
      for (int i = 0; i < row.size(); i++) {
        String value = row.get(i);

        String word = i == row.size() - 1 ? VERTICAL_SEP : "";
        System.out.printf("%s %-" + maxWidths[i] + "s %s", VERTICAL_SEP, value, word);
      }

      System.out.println();
    }
  }
}
