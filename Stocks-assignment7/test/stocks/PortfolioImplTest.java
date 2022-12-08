package stocks;

import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import stocks.portfolio.FlexiblePortfolio;
import stocks.portfolio.Portfolio;
import stocks.portfolio.PortfolioImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test PortfolioImpl and its methods.
 */
public class PortfolioImplTest {

  @Test
  public void testCSVPortfolioCreation() {
    Portfolio p = new PortfolioImpl("Test CSV Portfolio", "test/resources/portfolio.csv");
    assertEquals("Test CSV Portfolio", p.getName());
    assertEquals(740, p.getNumberOfShares("1-1-2020"));
  }

  @Test
  public void testXMLPortfolioCreation() {
    try {
      Portfolio p = new PortfolioImpl("Test XML Portfolio", "test/resources/portfolio.xml");
      assertEquals("Test XML Portfolio", p.getName());
      assertEquals(700, p.getNumberOfShares("12-12-2020"));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFileTypePortfolioCreation() {
    Portfolio p = new PortfolioImpl("Test XML Portfolio", "test/resources/portfolio.json");
    assertEquals("Test JSON Portfolio", p.getName());
    assertEquals(700, p.getNumberOfShares("1-1-2020"));

  }

  @Test
  public void testSavingPortfolio() {
    Portfolio p = new PortfolioImpl("Test CSV Portfolio", "test/resources/portfolio.csv");
    p.savePortfolio();
    Portfolio p2 = new PortfolioImpl("CSV", Paths.get("").toAbsolutePath()
            + "/" + p.getName() + ".csv");
    assertEquals(p.getNumberOfShares("1-1-2020"), p2.getNumberOfShares("1-1-2020"));
    File f = new File(Paths.get("").toAbsolutePath() + "/" + p.getName() + ".csv");
    boolean fileDeleted = f.delete();
    assertTrue(fileDeleted);
  }

  @Test (expected = RuntimeException.class)
  public void testInvalidFilePath() {
    Portfolio p = new PortfolioImpl("Test CSV Portfolio", "invalidFile.csv");
  }

  @Test
  public void testCostBasis() throws ParseException {
    Map<String, Map<String, String>> map = new HashMap<>();
    Map<String, String> innerMap = new HashMap<>();
    innerMap.put("2022-11-28", "20");
    map.put("AAPL", innerMap);
    Portfolio p = new FlexiblePortfolio("Test", map);
    Map<String, String> testMap = new HashMap<>();
    testMap.put("AAPL", "20");
    assertEquals(testMap, p.viewStocks("2022-11-29"));
    assertEquals(2894.4, p.costBasis("2022-11-29"), 0);
  }

}