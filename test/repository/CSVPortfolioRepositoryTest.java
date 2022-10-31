package repository;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import io.IReader;
import io.IWriter;
import model.Portfolio;

import static org.junit.Assert.*;

public class CSVPortfolioRepositoryTest {

  static class MockIReader<T> implements IReader<T> {

    private List<String> log;
    public MockIReader(List<String> mockLog) {
      this.log = mockLog;
    }

    @Override
    public T read(InputStream inputStream) throws IOException {
      return null;
    }
  }
  static class MockIWriter<T> implements IWriter<T> {
    private List<String> log;

    public MockIWriter(List<String> mockLog) {
      this.log = mockLog;
    }
    @Override
    public void write(T t, OutputStream outputStream) throws IOException {
      this.log.add("Repository delegating write functionality to IO Writer class");
    }
  }

  @Test
  public void testCreateExistingPortfolio() throws IOException {
    List<String> mockLog = new ArrayList<>();
    MockIReader mockIReader = new MockIReader(mockLog);
    MockIWriter mockIWriter = new MockIWriter(mockLog);
    IRepository iRepository = new CSVPortfolioRepository(mockIReader,mockIWriter);

    Portfolio portfolio = new Portfolio();
    portfolio.setName("ASas");
    try {
      iRepository.create(portfolio);
    } catch (IllegalArgumentException illegalArgumentException) {
      assertEquals("A portfolio with this name does not exist.",
              illegalArgumentException.getMessage());
    }
  }

  @Test
  public void TestReadRepo(){

  }

  @Test
  public void TestUpdateRepo(){

  }
}