package io;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Function;
import model.Stock;

public interface IReader<T, U, V> {
  T read(InputStream inputStream, Function<Iterable<U>, V> mapper) throws IOException;
}
