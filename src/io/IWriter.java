package io;

import java.io.IOException;
import java.io.OutputStream;

public interface IWriter<T> {

  void write(T t, OutputStream outputStream) throws IOException;
}
