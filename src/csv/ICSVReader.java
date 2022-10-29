package csv;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ICSVReader {

  List<List<String>> readRecords(InputStream inputStream) throws IOException;
}
