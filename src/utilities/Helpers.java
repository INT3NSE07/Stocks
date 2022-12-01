package utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A general purpose utility class that contains various helpers.
 */
public class Helpers {

  /**
   * Splits the given value into equal splits if possible while minimizing the difference between
   * the largest and smallest split.
   *
   * @param value  The value to be split
   * @param splits the number of times the given value has to be split
   * @return the list of split values
   */
  public static List<Long> splitValue(long value, int splits) {
    List<Long> splitValues = new ArrayList<>();

    if (value % splits == 0) {
      for (int i = 0; i < splits; i++) {
        splitValues.add((value / splits) - 1);
      }
    } else {
      long maxDivisibleNumber = splits - (value % splits);
      long nonDivisibleNumber = value / splits;
      for (int i = 0; i < splits; i++) {
        if (i >= maxDivisibleNumber) {
          splitValues.add(Long.sum(nonDivisibleNumber, 1) - 1);
        } else {
          splitValues.add(nonDivisibleNumber - 1);
        }
      }
    }

    return splitValues;
  }

  /**
   * A general purpose buffer reader generator for given inputs. Used by
   * {@link controller.PortfolioGUIController} for parsing inputs.
   *
   * @param inputs values that are variable in number for each functionality
   * @return a BufferReader object which has the inputs that we have sent as parameters
   */
  public static BufferedReader getBufferedReader(String... inputs) {
    StringBuilder res = new StringBuilder();
    for (String input : inputs) {
      res.append(input).append(System.lineSeparator());
    }
    return new BufferedReader(
        new InputStreamReader(
            new ByteArrayInputStream(
                res.toString().getBytes()
            )
        )
    );
  }
}
