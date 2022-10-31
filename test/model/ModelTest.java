package model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import model.IPortfolioModel;
import model.Portfolio;
import model.PortfolioModel;
import model.Stock;
import repository.CSVPortfolioRepository;
import repository.IRepository;
import service.IStockService;
import utilities.Pair;

import static junit.framework.TestCase.assertEquals;

public class ModelTest {

  class MockService implements IStockService {
    @Override
    public Stock getStock(String symbol, double quantity) {
      return null;
    }

    @Override
    public Stock getStockOnDate(String symbol, String date) {
      return null;
    }

    @Override
    public boolean isStockSymbolValid(String symbol) throws IOException {
      return false;
    }
  }

  static class MockRepository<T> implements IRepository<T> {

    private final List<String> log;

    public MockRepository(List<String> log) {
      this.log = log;
    }

    @Override
    public T create(T item) {
      this.log.add("Entered Repository create method.");
      return item;
    }

    @Override
    public Iterable<T> read(Predicate<T> predicate) throws IOException {
      return null;
    }

    @Override
    public T update(T item) {
      this.log.add("Entered Repository Update method.");
      return null;
    }
  }

  @Test
  public void testCreatePortfolioNameNull() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService();
    IPortfolioModel model = new PortfolioModel(temp, service);
    try {
      List<Pair<String, Double>> stockPairs = new ArrayList<>();
      stockPairs.add(new Pair<>("sas",Double.parseDouble("123")));
      model.createPortfolio(null,stockPairs);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("Input cannot be null or empty.", illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCreatePortfolioNameEmpty() {
    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService();
    IPortfolioModel model = new PortfolioModel(temp, service);
    try {
      List<Pair<String, Double>> stockPairs = new ArrayList<>();
      stockPairs.add(new Pair<>("sas",Double.parseDouble("123")));

      model.createPortfolio("",stockPairs);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("Input cannot be null or empty.", illegalArgumentException.getMessage());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testCreatePortfolioCreateCallingCreateRepository() throws IOException {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService();
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>("sas",Double.parseDouble("123")));

    model.createPortfolio("ui", stockPairs);
    assertEquals("Entered Repository create method.", mockLog.get(0));

  }

  @Test
  public void testCreatePortfolioAddCallingUpdateRepository() throws IOException {

    List<String> mockLog = new ArrayList<>();
    MockRepository temp = new MockRepository(mockLog);
    MockService service = new MockService();
    IPortfolioModel model = new PortfolioModel(temp, service);

    List<Pair<String, Double>> stockPairs = new ArrayList<>();
    stockPairs.add(new Pair<>("sas",Double.parseDouble("123")));

    model.createPortfolio("ui", stockPairs);
    assertEquals("Entered Repository Update method.", mockLog.get(1));

  }


  @Test
  public void testReadPortfolio() {

  }

  @Test
  public void testGetPortfolioValueOnDate() {

  }

}
