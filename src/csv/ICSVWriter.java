package csv;

import java.io.IOException;
import java.io.OutputStream;

public interface ICSVWriter<T> {

  void writeHeader(T header, OutputStream outputStream);

  void writeRecords(T records, OutputStream outputStream)
      throws IOException;
}
