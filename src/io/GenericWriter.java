package io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GenericWriter implements IWriter<List<String>> {

  private final IWriter<List<String>> writer;

  public GenericWriter(IWriter<List<String>> writer) {
    this.writer = writer;
  }

  @Override
  public void write(List<String> records, OutputStream outputStream) throws IOException {
    this.writer.write(records, outputStream);
  }
}
