package io;

import java.io.IOException;
import java.io.InputStream;

public interface IReader<T> {

  /**
   * A read method to read from the given input stream and returns the read T object.
   *
   * @param inputStream contains the inputStream that needs to be read.
   * @return the data which has been read.
   * @throws IOException when unable to read from inputStream.
   */
  T read(InputStream inputStream) throws IOException;
}
