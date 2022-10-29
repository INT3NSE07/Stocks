package controller;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import model.IPortfolioModel;
import model.Portfolio;
import model.PortfolioModel;
import repository.CSVPortfolioRepository;
import repository.IRepository;
import service.AlphaVantageStockService;
import service.IStockService;
import view.IPortfolioView;
import view.PortfolioTextView;
import static org.junit.Assert.assertEquals;
public class PortfolioControllerTest {

  @Test
  public void testGo() throws UnsupportedEncodingException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    final String utf8 = StandardCharsets.UTF_8.name();
    String a = "4";
    PrintStream stream = new PrintStream(baos);
    OutputStream outputStream = new ByteArrayOutputStream();
    IRepository<Portfolio> temp = new CSVPortfolioRepository();
    IStockService service =  AlphaVantageStockService.getInstance(null);
    IPortfolioModel model = new PortfolioModel(temp,service);
    IPortfolioView view = new PortfolioTextView(new PrintStream(outputStream));
    PortfolioController controller = new PortfolioController(model, view,
            new ByteArrayInputStream(a.getBytes("UTF-8"))
    );
    controller.run();
//    try (PrintStream ps = new PrintStream(baos, true, utf8)) {
//      (view.showOutputStream(),ps);
//    }
//    String data = baos.toString(utf8);
//    outputStream = new ByteArrayOutputStream();
    String output = "Portfolio Management Services\n" +
            "1) Create Portfolio\n" +
            "2) Get Value of a Portfolio\n" +
            "3) Examine Portfolio\n" +
            "4) Exit\n" +
            "Please Select an Menu Option: \n";
    assertEquals(output, outputStream.toString());
//    view.
  }


}