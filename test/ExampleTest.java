import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.junit.Test;
import view.IPortfolioView;
import view.PortfolioTextView;

public class ExampleTest {

  @Test
  public void testDefaultConstructor() {
    IPortfolioView view = new PortfolioTextView(System.out);

    view.showOptions();
  }
}
