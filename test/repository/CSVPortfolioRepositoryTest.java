package repository;

import static org.junit.Assert.assertEquals;

import constants.CSVConstants;
import constants.Constants;
import io.IReader;
import io.IWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import model.Portfolio;
import model.Stock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CSVPortfolioRepositoryTest {

  private static final String MOCK_READER_READ_MESSAGE = "MockReader read() called";
  private static final String MOCK_WRITER_WRITE_MESSAGE = "MockWriter write() called";
  private static List<List<String>> stockCSVStrings;

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  @Before
  public void setup() {
    stockCSVStrings = new ArrayList<>();
    stockCSVStrings.add(new ArrayList<>(List.of("AAPL", "12.0", "2022-10-11")));
    stockCSVStrings.add(new ArrayList<>(List.of("AMZN", "77.0", "2022-09-12")));
  }

  @Test
  public void testCreatePortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    File tmpFile = tmpFolder.newFile();
    String fileName = tmpFile.getName() + CSVConstants.EXTENSION;

    Portfolio portfolio = new Portfolio();
    portfolio.setName(fileName);

    Portfolio createdPortfolio = repository.create(portfolio);

    assertEquals(portfolio, createdPortfolio);
    assertEquals(MOCK_WRITER_WRITE_MESSAGE, mockLog.get(0));
  }

  @Test
  public void testCreateExistingPortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    File tmpFile = tmpFolder.newFile();
    tmpFolder.newFile(tmpFile.getName() + CSVConstants.EXTENSION);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(tmpFile.getName());

    try {
      Portfolio createdPortfolio = repository.create(portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals(Constants.PORTFOLIO_EXISTS, e.getMessage());
    }
  }

  @Test
  public void testUpdatePortfolioEmptyFile() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    File tmpFile = tmpFolder.newFile();
    tmpFolder.newFile(tmpFile.getName() + CSVConstants.EXTENSION);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(tmpFile.getName());

    List<Stock> stocks = new ArrayList<>();
    for (List<String> stockCSVString : stockCSVStrings) {
      stocks.add(
          Stock.StockBuilder.create().setSymbol(stockCSVString.get(0))
              .setQuantity(Double.parseDouble(stockCSVString.get(1)))
              .setDate(stockCSVString.get(2)));
    }
    portfolio.setStocks(stocks);

    Portfolio updatedPortfolio = repository.update(portfolio);
    for (int i = 0; i < stocks.size(); i++) {
      assertEquals(MOCK_WRITER_WRITE_MESSAGE, mockLog.get(i));
      assertEquals(stocks.get(i).getSymbol(), updatedPortfolio.getStocks().get(i).getSymbol());
      assertEquals(stocks.get(i).getQuantity(), updatedPortfolio.getStocks().get(i).getQuantity(),
          0);
      assertEquals(stocks.get(i).getDate(), updatedPortfolio.getStocks().get(i).getDate());
    }
  }

  @Test
  public void testUpdatePortfolioNonEmptyFile() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    File tmpFile = tmpFolder.newFile();
    File file = tmpFolder.newFile(tmpFile.getName() + CSVConstants.EXTENSION);

    for (List<String> stockCSVString : stockCSVStrings) {
      Files.writeString(file.toPath(), String.join(",", stockCSVString));
    }

    Portfolio portfolio = new Portfolio();
    portfolio.setName(tmpFile.getName());

    List<Stock> stocks = List.of(
        Stock.StockBuilder.create().setSymbol("AAPL").setQuantity(210.0).setDate("2022-10-28"),
        Stock.StockBuilder.create().setSymbol("AMZN").setQuantity(881.0).setDate("2022-10-11")
    );
    portfolio.setStocks(stocks);

    Portfolio updatedPortfolio = repository.update(portfolio);
    for (int i = 0; i < stocks.size(); i++) {
      assertEquals(MOCK_WRITER_WRITE_MESSAGE, mockLog.get(i));
    }
    assertEquals(4, portfolio.getStocks().size() * stockCSVStrings.size());
  }

  @Test
  public void testUpdateNonExistentPortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    Portfolio portfolio = new Portfolio();
    portfolio.setName("");

    try {
      Portfolio updatedPortfolio = repository.update(portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals(Constants.PORTFOLIO_DOES_NOT_EXIST, e.getMessage());
    }
  }

  @Test
  public void testReadPortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    File tmpFile = tmpFolder.newFile();
    File file = tmpFolder.newFile(tmpFile.getName() + CSVConstants.EXTENSION);

    for (List<String> stockCSVString : stockCSVStrings) {
      Files.writeString(file.toPath(), String.join(",", stockCSVString));
    }

    Iterable<Portfolio> portfolios = repository.read(
        x -> x.getName().equals(tmpFile.getName()));

    assertEquals(1, portfolios.spliterator().getExactSizeIfKnown());
  }

  @Test
  public void testReadNonExistentPortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockCSVReader mockCSVReader = new MockCSVReader(mockLog);
    MockCSVWriter mockCSVWriter = new MockCSVWriter(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockCSVReader, mockCSVWriter,
        path);

    try {
      Iterable<Portfolio> portfolios = repository.read(
          x -> x.getName().equals("aa.q"));
    } catch (IllegalArgumentException e) {
      assertEquals(Constants.PORTFOLIO_DOES_NOT_EXIST, e.getMessage());
    }
  }

  static class MockCSVReader implements IReader<List<List<String>>> {

    private final List<String> log;

    public MockCSVReader(List<String> mockLog) {
      this.log = mockLog;
    }

    @Override
    public List<List<String>> read(InputStream inputStream) throws IOException {
      this.log.add(MOCK_READER_READ_MESSAGE);

      return stockCSVStrings;
    }
  }

  static class MockCSVWriter implements IWriter<List<String>> {

    private final List<String> log;

    public MockCSVWriter(List<String> mockLog) {
      this.log = mockLog;
    }

    @Override
    public void write(List<String> t, OutputStream outputStream) throws IOException {
      this.log.add(MOCK_WRITER_WRITE_MESSAGE);
    }
  }
}