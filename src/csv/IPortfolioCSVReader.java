package csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface IPortfolioCSVReader {

  List<List<String>> readRecords(InputStream inputStream) throws IOException;
}
