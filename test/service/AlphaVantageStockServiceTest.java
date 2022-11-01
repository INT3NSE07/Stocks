//package service;
//
//import constants.Constants;
//import io.IReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import model.Stock;
//import org.junit.Before;
//import utilities.DateUtils;
//import utilities.MapperUtils;
//
//public class AlphaVantageStockServiceTest extends AbstractStockServiceTest {
//
//  private List<String> mockLog;
//
//  private static final String MOCK_READER_READ_MESSAGE = "MockReader read() called";
//
//  private static List<List<String>> stockAlphaVantageAPICSV;
//
//  public AlphaVantageStockServiceTest() {
//    String currentDate = DateUtils.getCurrentDate(Constants.DEFAULT_DATETIME_FORMAT);
//
//    stockAlphaVantageAPICSV = new ArrayList<>();
//    stockAlphaVantageAPICSV.add(new ArrayList<>(
//        List.of(currentDate, "133.7200", "135.8600", "132.8100", "135.0100", "5138863")));
//    stockAlphaVantageAPICSV.add(new ArrayList<>(
//        List.of("2022-10-05", "124.7100", "126.4600", "124.2300", "125.7400", "3212872")));
//  }
//
//  protected static class MockAlphaVantageAPICSVReader implements IReader<List<List<String>>> {
//
//    private final List<String> log;
//
//    public MockAlphaVantageAPICSVReader(List<String> mockLog) {
//      this.log = mockLog;
//    }
//
//    @Override
//    public List<List<String>> read(InputStream inputStream) throws IOException {
//      this.log.add(MOCK_READER_READ_MESSAGE);
//
//      return stockAlphaVantageAPICSV;
//    }
//  }
//
//  @Before
//  public void setup() {
//    mockLog = new ArrayList<>();
//  }
//
//  @Override
//  protected IStockService createStockService() throws IOException {
//    return AlphaVantageStockService.getInstance(
//        new MockAlphaVantageAPICSVReader(mockLog));
//  }
//
//  @Override
//  protected Stock getStock() {
//    List<String> stockData = stockAlphaVantageAPICSV.get(0);
//
//    Stock stock = MapperUtils.getAlphaVantageResponseToStockMapper().apply(stockData);
//    stock.setSymbol("AAPL");
//
//    return stock;
//  }
//
//  @Override
//  protected Stock getStockOnDate() {
//    List<String> stockData = stockAlphaVantageAPICSV.get(1);
//
//    Stock stock = MapperUtils.getAlphaVantageResponseToStockMapper().apply(stockData);
//    stock.setSymbol("AAPL");
//
//    return stock;
//  }
//}