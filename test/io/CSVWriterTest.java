package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * A JUnit test class for the {@link CSVWriter}s class.
 */
public class CSVWriterTest {

  @Test
  public void testWrite() {
    List<String> records = new ArrayList<>(Arrays.asList(
        "AAP",
        "23.0",
        "2022-10-28",
        "12asdaa",
        "91-2a#9!22",
        "",
        ","
    ));
    IWriter<List<String>> writer = new CSVWriter();

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      writer.write(records, baos);

      assertEquals(String.join(",", records) + System.lineSeparator(), baos.toString());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testWriteNullRecords() {
    List<String> records = null;
    IWriter<List<String>> writer = new CSVWriter();

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      writer.write(records, baos);

      assertEquals(0, baos.size());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testWriteEmptyRecords() {
    List<String> records = new ArrayList<>();
    IWriter<List<String>> writer = new CSVWriter();

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      writer.write(records, baos);

      assertEquals(0, baos.size());
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test(expected = IOException.class)
  public void testWriteOnClosedStream() throws IOException {
    List<String> records = new ArrayList<>(List.of(
        "AAP"
    ));
    IWriter<List<String>> writer = new CSVWriter();

    FileOutputStream fileOutputStream = new FileOutputStream("");
    fileOutputStream.close();

    writer.write(records, fileOutputStream);
  }
}