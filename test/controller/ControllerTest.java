package controller;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.concurrent.ThreadLocalRandom;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import controller.IPortfolioController;
import controller.PortfolioController;
import model.IPortfolioModel;
import model.Portfolio;
import utilities.Pair;
import view.IPortfolioView;

import static junit.framework.TestCase.assertEquals;

public class ControllerTest {

  static class MockModel implements IPortfolioModel {
    private List<String> log;

    public MockModel(List<String> log) {
      this.log = log;
    }

    @Override
    public void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs) throws IllegalArgumentException {

    }

    @Override
    public Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException {
      return null;
    }

    @Override
    public double getPortfolioValueOnDate(String portFolioName, String date) throws IllegalArgumentException {
      return 0;
    }

    @Override
    public boolean isStockSymbolValid(String symbol) throws IOException {
      return false;
    }
  }

  class MockView implements IPortfolioView {
    private List<String> log;

    public MockView(List<String> log) {
      this.log = log;
    }

    @Override
    public void showString(String s) {

    }

    @Override
    public void showOptions(int selectedMenuItem) {
      this.log.add("Called View with Menu item Option " + selectedMenuItem);
    }

    @Override
    public void showOptionError() {
      this.log.add("Called view for error in selected option.");
    }

    @Override
    public void showPrompt(String key) {

    }

    @Override
    public void showPortfolio(Portfolio readPortfolio) {

    }
  }

  // unable to test.
  @Test
  public void testInitialStateWithNoInput() throws UnsupportedEncodingException {

    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);
    IPortfolioController controller = new PortfolioController(mockModel,
            mockView,
            new ByteArrayInputStream("".getBytes("UTF-8")));
    controller.run();

    assertEquals("Called View with Menu item Option 0", mockLog.get(0));
  }

  @Test
  public void testExitStateWith4Input() throws UnsupportedEncodingException {

    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);
    IPortfolioController controller = new PortfolioController(mockModel,
            mockView,
            new ByteArrayInputStream("4".getBytes("UTF-8")));
    controller.run();

    assertEquals("Called View with Menu item Option 0", mockLog.get(0));
    assertEquals("Called view to show options of selected option.", mockLog.get(1));
  }

  @Test
  public void testRandomInput() throws UnsupportedEncodingException {

    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);
    // Random random = new Random();
    int randomNum = ThreadLocalRandom.current().nextInt(5, 1000);
    Reader a = new StringReader(randomNum + " 4");

    try {
      InputStream ab = new ByteArrayInputStream((randomNum + " 4").getBytes());
      IPortfolioController controller = new PortfolioController(mockModel,
              mockView,
              ab);
      controller.run();

      //System.out.println(mockLog);
      assertEquals("Called view for error in selected option.", mockLog.get(mockLog.size()-1));

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
