package io;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertNotEquals;

public class CSVWriterTest {

  @Test
  public void write() {



    List<String> records = new ArrayList<>(Arrays.asList(
            "AAP",
            "23.0",
            "2022-10-28"
    ));
    IWriter<List<String>> writer = new CSVWriter();

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      writer.write(records, baos);

      assertNotEquals(String.join(",", records), baos.toString());
    } catch (IOException e) {
      fail("");
    }
  }
}