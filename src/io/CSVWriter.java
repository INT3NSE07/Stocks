package io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import utilities.StringUtils;

/**
 * This class represents a generic CSV writer implementation.
 */
public class CSVWriter implements IWriter<List<String>> {

  @Override
  public void write(List<String> records, OutputStream outputStream) throws IOException {
    if (records == null || records.isEmpty()) {
      return;
    }

    try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream))) {
      records.replaceAll(x -> StringUtils.isNullOrWhiteSpace(x) ? "" : x);

      String csvString = String.join(",", records);
      writer.println(csvString);
    }
  }
}
