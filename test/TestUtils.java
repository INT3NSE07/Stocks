import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {

  private Random random;

  private String alphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";

  public TestUtils() {
    random = new Random(TestConstants.SEED);
  }

  private double getRandomDouble() {
    return ThreadLocalRandom.current().nextDouble();
  }

  private double getRandomDouble(double min, double max) {
    return ThreadLocalRandom.current().nextDouble(min, max);
  }

  private String getRandomString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      int index = (int) (alphabetString.length() * Math.random());
      sb.append(alphabetString.charAt(index));
    }

    return sb.toString();
  }

//  private String getRandomDate() {
//    StringBuilder sb = new StringBuilder(length);
//    for (int i = 0; i < length; i++) {
//      int index = (int) (alphabetString.length() * Math.random());
//      sb.append(alphabetString.charAt(index));
//    }
//
//    return sb.toString();
//  }
}
