package repository;

import java.util.List;
import java.util.function.Predicate;
import model.Portfolio;

public interface IRepository<T> {

  T create(T item);

  Iterable<T> read(Predicate<T> predicate);
}
