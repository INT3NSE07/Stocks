package service;

import constants.CSVConstants;
import constants.Constants;
import io.IReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import model.Stock;
import utilities.MapperUtils;

public class FileStockService extends AbstractStockService {

  private static FileStockService instance;

  private FileStockService(IReader<List<List<String>>> reader) {
    super(reader);
  }

  public static FileStockService getInstance(IReader<List<List<String>>> reader) {
    if (instance == null) {
      instance = new FileStockService(reader);
    }

    return instance;
  }

  @Override
  protected InputStream getInputStream(String symbol) throws IOException {
    return new FileInputStream(Constants.STOCK_DATA_PATH + CSVConstants.EXTENSION);
  }

  @Override
  protected Function<List<String>, Stock> getResponseToStockMapper() {
    return MapperUtils.getCSVFileToStockMapper();
  }
}
