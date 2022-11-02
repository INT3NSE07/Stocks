package io;

import java.io.IOException;
import java.io.OutputStream;

public interface IWriter<T> {

  /**
   * A write method to write the given T object to the provided OutputStream.
   *
   * @param t            the object to be written.
   * @param outputStream stream where the output of the t object is to be written.
   * @throws IOException throws Exception when unable to write on the OutputStream is un
   *                     accessible.
   */
  void write(T t, OutputStream outputStream) throws IOException;
}
