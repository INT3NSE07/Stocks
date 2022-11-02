package io;

import java.io.IOException;
import java.io.OutputStream;

public interface IWriter<T> {

  /**
   * A write method
   *
   * @param t
   * @param outputStream
   * @throws IOException
   */
  void write(T t, OutputStream outputStream) throws IOException;
}
