package repository;

import java.io.IOException;
import java.util.function.Predicate;

public interface IRepository<T> {

  T create(T item);

  Iterable<T> read(Predicate<T> predicate) throws IOException;

  T update(T item);
}
