package controller;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import model.IPortfolioModel;
import model.Portfolio;
import org.junit.Test;
import utilities.Pair;
import view.IPortfolioView;

public class ControllerTest {

  private static final String MOCK_VIEW_SHOW_OPTIONS = "MockView showOptions() called with option";
  private static final String MOCK_VIEW_SHOW_OPTION_ERROR = "MockView showOptionError() called";

  static class MockModel implements IPortfolioModel {

    private final List<String> log;

    public MockModel(List<String> log) {
      this.log = log;
    }

    @Override
    public void createPortfolio(String portFolioName, List<Pair<String, Double>> stockPairs)
        throws IllegalArgumentException {

    }

    @Override
    public Portfolio readPortfolio(String portFolioName) throws IllegalArgumentException {
      return null;
    }

    @Override
    public double getPortfolioValueOnDate(String portFolioName, String date)
        throws IllegalArgumentException {
      return 0;
    }

    @Override
    public boolean isStockSymbolValid(String symbol) throws IOException {
      return false;
    }
  }

  static class MockView implements IPortfolioView {

    private final List<String> log;

    public MockView(List<String> log) {
      this.log = log;
    }

    @Override
    public void showString(String s) {

    }

    @Override
    public void showOptions(int selectedMenuItem) {
      this.log.add(MOCK_VIEW_SHOW_OPTIONS + " " + selectedMenuItem);
    }

    @Override
    public void showOptionError() {
      this.log.add(MOCK_VIEW_SHOW_OPTION_ERROR);
    }

    @Override
    public void showPrompt(String key) {

    }

    @Override
    public void showPortfolio(Portfolio readPortfolio) {

    }
  }

  @Test
  public void testInitialState() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int initialSelectedMenuItem = 0;
    int selectedMenuItem = 4;
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        Integer.toString(selectedMenuItem).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();
    } catch (IOException e) {
      fail(e.getMessage());
    }

    assertEquals(MOCK_VIEW_SHOW_OPTIONS + " " + initialSelectedMenuItem, mockLog.get(0));
  }

  @Test
  public void testExit() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int initialSelectedMenuItem = 0;
    int selectedMenuItem = 4;
    try (ByteArrayInputStream bais = new ByteArrayInputStream(
        Integer.toString(selectedMenuItem).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);

      controller.run();
    } catch (IOException e) {
      fail(e.getMessage());
    }

    assertEquals(MOCK_VIEW_SHOW_OPTIONS + " " + initialSelectedMenuItem, mockLog.get(0));
  }

  @Test
  public void testRandomInvalidInput() {
    List<String> mockLog = new ArrayList<>();
    MockView mockView = new MockView(mockLog);
    MockModel mockModel = new MockModel(mockLog);

    int count = ThreadLocalRandom.current().nextInt(5, 10);
    String[] inputs = new String[count + 1];
    for (int i = 0; i < count; i++) {
      inputs[i] = ThreadLocalRandom.current().nextInt(5, 10000) + System.lineSeparator();
    }
    inputs[count] = "4";

    try (ByteArrayInputStream bais = new ByteArrayInputStream(String.join("", inputs).getBytes())) {
      IPortfolioController controller = new PortfolioController(mockModel, mockView, bais);
      controller.run();

      List<String> filteredMockLog = mockLog.stream()
          .filter(x -> x.equals(MOCK_VIEW_SHOW_OPTION_ERROR)).collect(Collectors.toList());
      assertEquals(inputs.length - 1, filteredMockLog.size());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }
}
