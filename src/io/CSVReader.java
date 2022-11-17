package io;

import constants.CSVConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a generic CSV reader implementation.
 */
public class CSVReader implements IReader<List<List<String>>> {

  @Override
  public List<List<String>> read(InputStream inputStream) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      return reader.lines().skip(1)
          .map(line -> Arrays.asList(line.split(CSVConstants.SEPARATOR)))
          .collect(Collectors.toList());
    }
  }
}
