package csv;

import constants.CSVConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import model.Portfolio;
import model.Stock;

public class PortfolioCSVWriter implements ICSVWriter<Portfolio> {

  @Override
  public void writeHeader(Portfolio header, OutputStream outputStream) {
    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
      String csvHeader = String.join(",", CSVConstants.CSV_HEADER);
      writer.println(csvHeader);
    }
  }

  @Override
  public void writeRecords(Portfolio portfolio, OutputStream outputStream)
      throws IOException {
    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
      for (Stock stock : portfolio.getStocks()) {
        String csvString = String.join(",", stock.getSymbol(),
            Double.toString(stock.getQuantity()), stock.getDate(), Double.toString(stock.getOpen()),
            Double.toString(stock.getHigh()), Double.toString(stock.getLow()),
            Double.toString(stock.getClose()), Double.toString(stock.getVolume()));

        writer.println(csvString);
      }
    }
  }
}
