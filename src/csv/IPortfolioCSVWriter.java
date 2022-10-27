package csv;

import java.io.IOException;
import java.io.OutputStream;
import model.Portfolio;

public interface IPortfolioCSVWriter {

  Portfolio writeRecords(Portfolio portfolio, OutputStream outputStream)
      throws IOException;
}
