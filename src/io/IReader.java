package io;

import java.io.IOException;
import java.io.InputStream;

public interface IReader<T> {

  /**
   * A read method to read from the given input stream and returns the read T object.
   *
   * @param inputStream contains the inputStream that needs to be read.
   * @return Returns the read t object of type <T>.
   * @throws IOException When unable to read from inputStream.
   */
  T read(InputStream inputStream) throws IOException;
}
