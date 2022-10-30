package io;

import java.io.IOException;
import java.io.OutputStream;

public interface IWriter<T> {

  void write(byte[] bytes, OutputStream outputStream) throws IOException;
}
