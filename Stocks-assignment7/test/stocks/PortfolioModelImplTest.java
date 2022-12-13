package stocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import stocks.model.PortfolioModel;
import stocks.model.PortfolioModelImpl;

/**
 * This class tests the model for the program.
 */
public class PortfolioModelImplTest {

  PortfolioModel model;
  String name;
  String path;
  String flexiblePath;

  @Before
  public void setup() {
    model = new PortfolioModelImpl();
    name = "test";
    path = "test/resources/portfolio.csv";
    flexiblePath = "test/resources/flexibleportfolio.csv";
  }

  @Test
  public void createModelTest() {
    assertEquals(0, model.getPortfolioList().size());
  }

  @Test
  public void createPortfolioFromCSVFileTest() {
    try {
      model.createInflexiblePortfolioFromFile(name, path);
    } catch (FileNotFoundException e) {
      e.toString();
    }
    assertEquals(1, model.getPortfolioList().size());
    assertEquals(name, model.getPortfolio(name).getName());
  }

  @Test
  public void createPortfolioFileCSVDoesNotExistTest() throws FileNotFoundException {
    String path = "random.csv";
    try {
      model.createInflexiblePortfolioFromFile(name, path);
    } catch (RuntimeException e) {
      System.out.println("File not found at path.");
    }
    assertEquals(0, model.getPortfolioList().size());
  }

  @Test
  public void createPortfolioFromXMLFileTest() {
    try {
      model.createInflexiblePortfolioFromFile(name, "test/resources/portfolio.xml");
    } catch (FileNotFoundException e) {
      e.toString();
    }
    assertEquals(1, model.getPortfolioList().size());
    assertEquals(name, model.getPortfolio(name).getName());
  }

  @Test
  public void createPortfolioFileXMLDoesNotExistTest() throws FileNotFoundException {
    String path = "random.xml";
    try {
      model.createInflexiblePortfolioFromFile(name, path);
    } catch (FileNotFoundException e) {
      System.out.println("File not found at path.");
    } catch (RuntimeException e) {
      System.out.println(e.getMessage());
    }
    assertEquals(0, model.getPortfolioList().size());
  }

  @Test(expected = RuntimeException.class)
  public void getValueWhenPortfolioDoesNotExistTest() throws ParseException {
    try {
      model.createInflexiblePortfolioFromFile(name, path);
    } catch (FileNotFoundException e) {
      e.toString();
    }
    model.getValueOfPortfolio("InvalidName", "2020-12-12");
  }

  @Test
  public void getValueWhenNameExistsTest() throws ParseException {
    String date = "2020-03-23";
    try {
      model.createInflexiblePortfolioFromFile(name, path);
    } catch (FileNotFoundException e) {
      e.toString();
    }
    assertEquals("$" + (model.getPortfolio(name).getValue(date)),
        model.getValueOfPortfolio(name, date));
  }

  @Test(expected = RuntimeException.class)
  public void getPortfolioInvalidNameTest() {
    try {
      model.createInflexiblePortfolioFromFile(name, path);
    } catch (FileNotFoundException e) {
      e.toString();
    }
    assertEquals(1, model.getPortfolioList().size());
    model.getPortfolio("invalid");
  }

  @Test
  public void createFlexiblePortfolioFromFile() {
    try {
      model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    } catch (IOException e) {
      e.toString();
    }
    assertEquals(1, model.getPortfolioList().size());
    assertEquals(name, model.getPortfolioNames().get(0));
  }

  @Test(expected = RuntimeException.class)
  public void createFlexiblePortfolioFromInvalidFile() {
    try {
      model.createFlexiblePortfolioFromFile(name, path, "2022-11-12", 10.0);
    } catch (IOException e) {
      e.toString();
    }
  }

  @Test
  public void getCostBasis() throws IOException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    String actual = model.getCostBasis(name, "2021-11-11");
    assertEquals("5185.45", actual);
  }

  @Test(expected = RuntimeException.class)
  public void buyStockForPortfolioNotValid() throws IOException {
    model.buyStock(name, 100, "AAPL", "2022-11-10", 10.0);
  }

  @Test
  public void buyStockForValidPortfolio() throws IOException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    model.buyStock(name, 100, "FRSH", "2010-11-12", 10.0);
    assertTrue(model.getPortfolio(name).viewStocks("2010-11-12").containsKey("FRSH"));
  }

  @Test
  public void sellStockForValidPortfolio() throws IOException, ParseException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    model.buyStock(name, 100, "FRSH", "2010-11-12", 10.0);
    model.sellStock(name, 50, "FRSH", "2010-11-13", 10.0);
    assertEquals("50", model.getPortfolio(name).viewStocks("2010-11-13").get("FRSH"));
  }

  @Test(expected = RuntimeException.class)
  public void sellStockWithInvalidShares() throws IOException, ParseException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    model.buyStock(name, 100, "FRSH", "2010-11-12", 10.0);
    model.sellStock(name, 500, "FRSH", "2010-11-13", 10.0);
    assertEquals("50", model.getPortfolio(name).viewStocks("2010-11-13").get("FRSH"));
  }

  @Test
  public void getValueForFlexiblePortfolio() throws IOException, ParseException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    String expected = model.getValueOfPortfolio(name, "2021-12-31");
    assertEquals("$699943.0", expected);
  }

  @Test
  public void testCompositionOfFlexible() throws IOException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    Map<String, String> expected = new HashMap<>();
    assertEquals(model.getComposition(name, "2021-11-11"), expected);
  }

  @Test
  public void testDollarCostAveraging() {
    Map<String, Double> breakdown = new HashMap<>();
    breakdown.put("AAPL", 50.0);
    breakdown.put("MSFT", 50.0);
    model.dollarCostAveraging("Test", 30, "2020-01-06", "2020-06-26", 1000.0, breakdown);
    assertEquals(1, model.getPortfolioNames().size());
    System.out.println(model.getPortfolio("Test").viewStocks("2020-02-01"));
  }

  @Test
  public void testInvestStocks() throws IOException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    Map<String, Double> breakdown = new HashMap<>();
    Double appleCost = getCostOfStock("AAPL", "2022-11-28");
    breakdown.put("AAPL", 50.5);
    Double msftCost = getCostOfStock("MSFT", "2022-11-28");
    breakdown.put("MSFT", 49.5);
    Double appleShares = ((50. / 100.0) * 10000.0) / appleCost;

    Double msftShares = ((49.5 / 100.0) * 10000.0) / msftCost;
    model.investStocks(name, 10000.0, breakdown, "2022-11-28");

    assertEquals(String.valueOf(300.0 + appleShares), model.getPortfolio(name).
        viewStocks("2022-11-28").get("AAPL"));
    assertEquals(String.valueOf(200.0 + msftShares), model.getPortfolio(name).
        viewStocks("2022-11-28").get("MSFT"));

  }

  @Test
  public void testInvestStocksException() throws IOException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    Map<String, Double> breakdown = new HashMap<>();
    breakdown.put("AAPL", 50.5);
    breakdown.put("MSFT", 49.5);
    try {
      model.investStocks(name, 10000.0, breakdown, "2022-11-27");
    } catch (RuntimeException e) {
      assertEquals(e.getMessage(), "The passed date is a holiday!");

    }
  }

  @Test
  public void testdollarCostAverage() throws IOException {
    model.createFlexiblePortfolioFromFile(name, flexiblePath, "2022-11-12", 10.0);
    Map<String, Double> breakdown = new HashMap<>();
    breakdown.put("AAPL", 50.5);
    breakdown.put("MSFT", 49.5);
    Double appleCost = getCostOfStock("AAPL", "2022-11-12");
    Double msftCost = getCostOfStock("MSFT", "2022-11-12");
    Double totalAapl = 0.0;
    Double totalMsft = 0.0;

    model.dollarCostAveraging(name, 10, "2022-11-12", "2022-11-30",
        10000.0, breakdown);
    Double appleShares = ((50. / 100.0) * 10000.0) / appleCost;
    Double msftShares = ((49.5 / 100.0) * 10000.0) / msftCost;
    totalAapl += appleShares;
    totalMsft += msftShares;
    appleCost = getCostOfStock("AAPL", "2022-11-22");
    msftCost = getCostOfStock("MSFT", "2022-11-22");
    appleShares = ((50. / 100.0) * 10000.0) / appleCost;
    msftShares = ((49.5 / 100.0) * 10000.0) / msftCost;
    totalAapl += appleShares;
    totalMsft += msftShares;

    assertEquals(String.valueOf(368.1534688937459), model.getPortfolio(name).
        viewStocks("2022-11-28").get("AAPL"));
    assertEquals(String.valueOf(240.4934597383815), model.getPortfolio(name).
        viewStocks("2022-11-28").get("MSFT"));
  }

  @Test
  public void testRebalancePortfolioDifferentWeights() throws IOException, ParseException {
    String date = "2021-12-10";

    model.createFlexiblePortfolioFromFile(name, flexiblePath, date, 10.0);
    double initialValue = Double.parseDouble(model.getValueOfPortfolio(name, date)
        .substring(1));

    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("AAPL", 50.00);
    stockWeights.put("GOOGL", 25.00);
    stockWeights.put("MSFT", 25.00);

    model.rebalancePortfolio(name, stockWeights, date);
    double finalValue = Double.parseDouble(model.getValueOfPortfolio(name, date)
        .substring(1));

    Map<String, String> stocks = model.getPortfolio(name).viewStocks(date);

    assertEquals("1857.8845082195598", stocks.get("AAPL"));
    assertEquals("56.31655337952655", stocks.get("GOOGL"));
    assertEquals("486.65466076954516", stocks.get("MSFT"));
    assertEquals(initialValue, finalValue, 0.01);
  }

  @Test
  public void testRebalancePortfolioEqualWeights() throws IOException, ParseException {
    String date = "2021-12-10";

    model.createFlexiblePortfolioFromFile(name, flexiblePath, date, 10.0);
    double initialValue = Double.parseDouble(model.getValueOfPortfolio(name, date)
        .substring(1));

    Map<String, Double> stockWeights = new HashMap<>();
    double weight = 100.00 / 3;
    stockWeights.put("AAPL", weight);
    stockWeights.put("GOOGL", weight);
    stockWeights.put("MSFT", weight);

    model.rebalancePortfolio(name, stockWeights, date);
    double finalValue = Double.parseDouble(model.getValueOfPortfolio(name, date)
        .substring(1));

    Map<String, String> stocks = model.getPortfolio(name).viewStocks(date);

    assertEquals("1238.5896721463735", stocks.get("AAPL"));
    assertEquals("75.08873783936875", stocks.get("GOOGL"));
    assertEquals("648.8728810260602", stocks.get("MSFT"));
    assertEquals(initialValue, finalValue, 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRebalancePortfolioInvalidWeights() throws IOException {
    String date = "2021-12-12";

    model.createFlexiblePortfolioFromFile(name, flexiblePath, date, 10.0);

    Map<String, Double> stockWeights = new HashMap<>();
    stockWeights.put("AAPL", 10.00);
    stockWeights.put("GOOGL", 12.00);
    stockWeights.put("MSFT", 19.00);

    model.rebalancePortfolio(name, stockWeights, date);
  }

  private Double getCostOfStock(String ticker, String date) {
    Double costOfStock = 0.0;
    String apiKey = "RH7EG7YN7BPG9V7Z";
    URL url = null;
    InputStream in = null;
    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
      in = url.openStream();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in));
      String inputLine;
      while ((inputLine = reader.readLine()) != null) {
        String[] values = inputLine.split(",");
        if (values[0].equals(date)) {
          costOfStock += Double.parseDouble(values[4]);
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return costOfStock;
  }


}