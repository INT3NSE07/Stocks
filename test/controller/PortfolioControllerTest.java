package controller;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import model.IPortfolioModel;
import model.Portfolio;
import model.PortfolioModel;
import repository.IRepository;
import repository.XMLPortfolioRepository;
import view.IPortfolioView;
import view.PortfolioTextView;

import static org.junit.Assert.assertEquals;
public class PortfolioControllerTest {

  @Test
  public void testGo() throws UnsupportedEncodingException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
//    final String utf8 = StandardCharsets.UTF_8.name();
    PrintStream stream = new PrintStream(baos);
    String a = "1 1 Aditya RETIREMENT";

    IRepository<Portfolio> temp = new XMLPortfolioRepository();
    IPortfolioModel model = new PortfolioModel(temp);
    IPortfolioView view = new PortfolioTextView(System.out);
    PortfolioController controller = new PortfolioController(model,view,new ByteArrayInputStream(a.getBytes("UTF-8"))
    );
    controller.go();
//    try (PrintStream ps = new PrintStream(baos, true, utf8)) {
//      (view.showOutputStream(),ps);
//    }
//    String data = baos.toString(utf8);
    String output = "1) Create Portfolio\n" +
            "2) Add Stocks\n" +
            "3) Get Value of a Portfolio\n" +
            "4) Examine Portfolio\n" +
            "5) Exit\n" +
            "Please Select an Menu Option: ";
//    assertEquals(stream.append(output.ch),view.showOutputStream());
//    view.
  }


}