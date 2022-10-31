package repository;

import java.io.IOException;
import java.util.function.Predicate;

/**
 *
 * @param <T>
 */
public interface IRepository<T> {

  /**
   *
   * @param item
   * @return
   */
  T create(T item);

  /**
   *
   * @param predicate
   * @return
   * @throws IOException
   */
  Iterable<T> read(Predicate<T> predicate) throws IOException;

  /**
   *
   * @param item
   * @return
   */
  T update(T item);
}
