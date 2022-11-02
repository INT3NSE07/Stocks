package service;

import io.IReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import model.Stock;
import utilities.MapperUtils;

/**
 *
 */
public class FileStockService extends AbstractStockService {

  private static FileStockService instance;
  private final String path;

  private FileStockService(IReader<List<List<String>>> reader, String filePath) {
    super(reader);

    this.path = filePath;
  }

  public static FileStockService getInstance(IReader<List<List<String>>> reader, String filePath) {
    if (instance == null) {
      instance = new FileStockService(reader, filePath);
    }

    return instance;
  }

  @Override
  protected InputStream getInputStream(String symbol) throws IOException {
    return new FileInputStream(this.path);
  }

  @Override
  protected Function<List<String>, Stock> getResponseToStockMapper() {
    return MapperUtils.getCSVFileToStockMapper();
  }
}
