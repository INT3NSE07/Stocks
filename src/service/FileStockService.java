package service;

import constants.CSVConstants;
import constants.Constants;
import io.IReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import model.Portfolio;
import model.Stock;

public class FileStockService extends AbstractStockService {

  private static FileStockService instance;

  private FileStockService(IReader<Portfolio, String, Stock> reader) {
    super(reader);
  }

  public static FileStockService getInstance(IReader<Portfolio, String, Stock> reader) {
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
  protected Function<Iterable<String>, Stock> getResponseToStockMapper(String symbol) {
    return iterable -> {
      List<String> stockData = new ArrayList<>();
      iterable.forEach(stockData::add);

      return Stock.StockBuilder.create()
          .setSymbol(symbol)
          .setDate(stockData.get(0))
          .setOpen(Double.parseDouble(stockData.get(2)))
          .setHigh(Double.parseDouble(stockData.get(3)))
          .setLow(Double.parseDouble(stockData.get(4)))
          .setClose(Double.parseDouble(stockData.get(5)))
          .setVolume(Double.parseDouble(stockData.get(6)));
    };
  }
}
