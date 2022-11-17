package utilities;

import java.util.ArrayList;
import java.util.List;

public class Helpers {

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

}
