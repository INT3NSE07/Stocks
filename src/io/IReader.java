package io;

import java.io.IOException;
import java.io.InputStream;

public interface IReader<T> {

  T read(InputStream inputStream) throws IOException;
}
