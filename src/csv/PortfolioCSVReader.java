package csv;

import constants.CSVConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PortfolioCSVReader implements IPortfolioCSVReader {

  @Override
  public List<List<String>> readRecords(InputStream inputStream) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      return reader.lines().map(line -> Arrays.asList(line.split(CSVConstants.SEPARATOR)))
          .collect(Collectors.toList());
    }
  }
}
