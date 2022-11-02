package repository;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * A generic repository to perform CRUD operations on an item. Data persistence is handled without
 * the need to know any details about where the data is actually persisted.
 *
 * @param <T> the type of the repository
 */
public interface IRepository<T> {

  /**
   * Adds the item to the repository.
   *
   * @param item the item to be added
   * @return the created item
   * @throws IllegalArgumentException if input valid fails
   * @throws IOException              if the fetching of the filtered items fails due to a failure
   *                                  of network or I/O operations
   */
  T create(T item) throws IllegalArgumentException, IOException;

  /**
   * Fetches the items which matches the specified predicate.
   *
   * @param predicate the predicate which is used to filter the items
   * @return the filtered item
   * @throws IllegalArgumentException if input valid fails
   * @throws IOException              if the fetching of the filtered items fails due to a failure
   *                                  of network or I/O operations
   */
  Iterable<T> read(Predicate<T> predicate) throws IllegalArgumentException, IOException;

  /**
   * Updates entity in the repository.
   *
   * @param item the item to be updated
   * @return the updated item
   * @throws IllegalArgumentException if input valid fails
   * @throws IOException              if the fetching of the filtered items fails due to a failure
   *                                  of network or I/O operations
   */
  T update(T item) throws IllegalArgumentException, IOException;
}
