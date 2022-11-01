package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class CSVWriter implements IWriter<List<String>> {

  @Override
  public void write(List<String> records, OutputStream outputStream) throws IOException {
    if (records == null || records.isEmpty()) {
      return;
    }

    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
      String csvString = String.join(",", records);
      writer.println(csvString);
    }
  }
}
