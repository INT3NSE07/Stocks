package io;

import java.io.IOException;
import java.io.OutputStream;

public interface IWriter<T> {

  /**
   * A write method to write the given T object to the Provided OutputStream.
   *
   * @param t object t of DataType <T>.
   * @param outputStream stream where the output of the t object is to be written.
   * @throws IOException throws Exception when unable to write on the OutputStream is un accessible.
   */
  void write(T t, OutputStream outputStream) throws IOException;
}
