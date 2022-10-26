package repository;

public interface IRepository<T> {

  T create(T item);

  T read();

  T update(T item);
}
