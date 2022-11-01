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
import java.util.ArrayList;
import java.util.List;
import model.Portfolio;
import model.Stock;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CSVPortfolioRepositoryTest {

  private static final String MOCKREADER_READ_MESSAGE = "MockReader read() called";
  private static final String MOCKWRITER_WRITE_MESSAGE = "MockWriter write() called";

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  static class MockReader<T> implements IReader<T> {

    private final List<String> log;

    public MockReader(List<String> mockLog) {
      this.log = mockLog;
    }

    @Override
    public T read(InputStream inputStream) throws IOException {
      this.log.add(MOCKREADER_READ_MESSAGE);

      return null;
    }
  }

  static class MockWriter<T> implements IWriter<T> {

    private final List<String> log;

    public MockWriter(List<String> mockLog) {
      this.log = mockLog;
    }

    @Override
    public void write(T t, OutputStream outputStream) throws IOException {
      this.log.add(MOCKWRITER_WRITE_MESSAGE);
    }
  }

  @Test
  public void testCreatePortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockReader<List<List<String>>> mockReader = new MockReader<>(mockLog);
    MockWriter<List<String>> mockWriter = new MockWriter<>(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockReader, mockWriter, path);

    File tmpFile = tmpFolder.newFile();
    String fileName = tmpFile.getName() + CSVConstants.EXTENSION;

    Portfolio portfolio = new Portfolio();
    portfolio.setName(fileName);

    Portfolio createdPortfolio = repository.create(portfolio);

    assertEquals(portfolio, createdPortfolio);
    assertEquals(MOCKWRITER_WRITE_MESSAGE, mockLog.get(0));
  }

  @Test
  public void testCreateExistingPortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockReader<List<List<String>>> mockReader = new MockReader<>(mockLog);
    MockWriter<List<String>> mockWriter = new MockWriter<>(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockReader, mockWriter, path);

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
  public void testUpdatePortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    String path = tmpFolder.getRoot().toString();

    MockReader<List<List<String>>> mockReader = new MockReader<>(mockLog);
    MockWriter<List<String>> mockWriter = new MockWriter<>(mockLog);
    IRepository<Portfolio> repository = new CSVPortfolioRepository(mockReader, mockWriter, path);

    File tmpFile = tmpFolder.newFile();
    tmpFolder.newFile(tmpFile.getName() + CSVConstants.EXTENSION);

    Portfolio portfolio = new Portfolio();
    portfolio.setName(tmpFile.getName());

    List<Stock> stocks = new ArrayList<>() {

    };
    //portfolio.setStocks(tmpFile.getName());

    try {
      Portfolio createdPortfolio = repository.create(portfolio);
    } catch (IllegalArgumentException e) {
      assertEquals(Constants.PORTFOLIO_EXISTS, e.getMessage());
    }
  }
}