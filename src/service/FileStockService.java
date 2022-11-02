package service;

import io.IReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import model.Stock;
import utilities.MapperUtils;

/**
 * This class represents a stock service which loads the stock data from a file stored on the file
 * system. An instance of this class can be obtained by calling
 * {@link FileStockService#getInstance(IReader, String)}
 */
public class FileStockService extends AbstractStockService {

  private static FileStockService instance;
  private final String path;

  private FileStockService(IReader<List<List<String>>> reader, String filePath) {
    super(reader);

    this.path = filePath;
  }

  /**
   * Returns an instance of {@link FileStockService}.
   *
   * @param reader   An implementation of {@link IReader} which is used to read and parse the stock
   *                 data stored in the file
   * @param filePath the path of the file which contains the stock data
   * @return an instance of {@link AlphaVantageStockService}
   */
  public static FileStockService getInstance(IReader<List<List<String>>> reader, String filePath) {
    if (instance == null) {
      instance = new FileStockService(reader, filePath);
    }

    return instance;
  }

  @Override
  protected InputStream getInputStream(String symbol) throws IOException {
    return getClass().getResourceAsStream(this.path);
  }

  @Override
  protected Function<List<String>, Stock> getResponseToStockMapper() {
    return MapperUtils.getCSVFileToStockMapper();
  }
}
