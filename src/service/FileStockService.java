package service;

import constants.CSVConstants;
import constants.Constants;
import csv.ICSVReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import model.Stock;

public class FileStockService extends AbstractStockService {

  private static FileStockService instance;

  private FileStockService(ICSVReader reader) {
    super(reader);
  }

  public static FileStockService getInstance(ICSVReader reader) {
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
  protected Function<List<String>, Stock> getResponseToStockMapper(String symbol) {
    return stockData -> Stock.StockBuilder.create()
        .setSymbol(symbol)
        .setDate(stockData.get(0))
        .setOpen(Double.parseDouble(stockData.get(2)))
        .setHigh(Double.parseDouble(stockData.get(3)))
        .setLow(Double.parseDouble(stockData.get(4)))
        .setClose(Double.parseDouble(stockData.get(5)))
        .setVolume(Double.parseDouble(stockData.get(6)));
  }
}
