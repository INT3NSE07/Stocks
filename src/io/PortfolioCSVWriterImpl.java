package io;

import constants.CSVConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import model.Portfolio;
import model.Stock;

public class PortfolioCSVWriterImpl implements IWriter<Portfolio> {

  @Override
  public void write(byte[] bytes, OutputStream outputStream) throws IOException {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bis)) {
      Portfolio portfolio = (Portfolio) ois.readObject();

      try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
        for (Stock stock : portfolio.getStocks()) {
          if (!stock.getLabels().isEmpty()) {
            String csvHeader = String.join(",", CSVConstants.CSV_HEADER);
            writer.println(csvHeader);
          }

          String csvString = String.join(",", stock.getSymbol(),
              Double.toString(stock.getQuantity()), stock.getDate());

          writer.println(csvString);
        }
      }
    } catch (ClassNotFoundException e) {
      throw new IOException(e);
    }
  }
}
