package service;

import constants.Constants;
import io.IReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import model.Stock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import utilities.DateUtils;
import utilities.MapperUtils;

public class FileStockServiceTest extends AbstractStockServiceTest {

  private List<String> mockLog;

  private static final String MOCK_READER_READ_MESSAGE = "MockReader read() called";

  private static List<List<String>> stockFileCSV;

  @Rule
  public TemporaryFolder tmpFolder = new TemporaryFolder();

  public FileStockServiceTest() {
    stockFileCSV = new ArrayList<>();
    stockFileCSV.add(new ArrayList<>(
        List.of(this.currentDate, "AAPL", "136.38", "139.12", "135.02", "138.77", "901900.0")));
    stockFileCSV.add(new ArrayList<>(
        List.of("2022-10-05", "AAPL", "26.48", "26.69", "26.16", "26.22", "2909200.0")));
  }

  protected static class MockCSVReader implements IReader<List<List<String>>> {

    private final List<String> log;

    public MockCSVReader(List<String> mockLog) {
      this.log = mockLog;
    }

    @Override
    public List<List<String>> read(InputStream inputStream) throws IOException {
      this.log.add(MOCK_READER_READ_MESSAGE);

      return stockFileCSV;
    }
  }

  @Before
  public void setup() {
    mockLog = new ArrayList<>();
  }

  @Override
  protected IStockService createStockService() throws IOException {
    return FileStockService.getInstance(
        new MockCSVReader(mockLog), tmpFolder.newFile().toString());
  }

  @Override
  protected Stock getStock() {
    List<String> stockData = stockFileCSV.get(0);

    return MapperUtils.getCSVFileToStockMapper().apply(stockData);
  }

  @Override
  protected Stock getStockOnDate() {
    List<String> stockData = stockFileCSV.get(1);

    return MapperUtils.getCSVFileToStockMapper().apply(stockData);
  }
}