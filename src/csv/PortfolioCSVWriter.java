package csv;

import constants.CSVConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import model.Portfolio;
import model.Stock;

public class PortfolioCSVWriter implements IPortfolioCSVWriter {

  @Override
  public Portfolio writeRecords(Portfolio portfolio, OutputStream outputStream)
      throws IOException {
    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
      String csvHeader = String.join(",", CSVConstants.CSV_HEADER);
      writer.print(csvHeader);

      for (Stock stock : portfolio.getStocks()) {
        String csvString = String.join(",", stock.getSymbol(), stock.getDate());

        writer.print(csvString);
      }
    }

    return portfolio;
  }
}
