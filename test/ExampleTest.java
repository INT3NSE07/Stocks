import csv.PortfolioCSVReader;
import model.Stock;
import org.junit.Test;
import service.AlphaVantageStockService;
import service.IStockService;

public class ExampleTest {

  @Test
  public void testDefaultConstructor() {
    //IPortfolioView view = new PortfolioTextView(System.out);
    // view.showOptions();

    IStockService service = AlphaVantageStockService.getInstance(new PortfolioCSVReader());
    //Stock s = service.getStock("GOOG");

    Stock s = service.getStockOnDate("GOOG", "2022-07-08");
  }
}
