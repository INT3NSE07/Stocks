package io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * This class represents an implementation of a generic writer interface. It can be used to write
 * data in multiple formats.
 */
public class GenericWriter implements IWriter<List<String>> {

  private final IWriter<List<String>> writer;

  /**
   * Constructs a {@link GenericWriter} object.
   *
   * @param writer the writer which is used to write data to an {@link OutputStream}
   */
  public GenericWriter(IWriter<List<String>> writer) {
    this.writer = writer;
  }

  @Override
  public void write(List<String> records, OutputStream outputStream) throws IOException {
    this.writer.write(records, outputStream);
  }
}
