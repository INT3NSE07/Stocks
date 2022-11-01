package io;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class CSVReaderTest {

  @Test
  public void testReadSkipsHeader() {
    String csvString = "symbol,quantity,date\n"
        + "AAPL,210.0,2022-10-28\n"
        + "AMZN,881.0,2022-01-11\n";
    List<List<String>> expected = new ArrayList<>();
    expected.add(new ArrayList<>(Arrays.asList("AAPL", "210.0", "2022-10-28")));
    expected.add(new ArrayList<>(Arrays.asList("AMZN", "881.0", "2022-01-11")));

    IReader<List<List<String>>> reader = new CSVReader();

    try (ByteArrayInputStream bais = new ByteArrayInputStream(csvString.getBytes())) {
      List<List<String>> actual = reader.read(bais);

      for (int i = 0; i < expected.size(); i++) {
        for (int j = 0; j < expected.get(i).size(); j++) {
          assertEquals(expected.get(i).get(j), actual.get(i).get(j));
        }
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadCSVWithOnlyHeader() {
    String csvString = "symbol,quantity,date\n";

    IReader<List<List<String>>> reader = new CSVReader();

    try (ByteArrayInputStream bais = new ByteArrayInputStream(csvString.getBytes())) {
      List<List<String>> actual = reader.read(bais);

      assertEquals(actual.size(), 0);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testReadEmptyCSV() {
    String csvString = "";

    IReader<List<List<String>>> reader = new CSVReader();

    try (ByteArrayInputStream bais = new ByteArrayInputStream(csvString.getBytes())) {
      List<List<String>> actual = reader.read(bais);

      assertEquals(actual.size(), 0);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IOException.class)
  public void testReadOnClosedStream() throws IOException {
    IReader<List<List<String>>> reader = new CSVReader();

    FileInputStream fileInputStream = new FileInputStream("");
    fileInputStream.close();

    reader.read(fileInputStream);
  }
}