package utilities;

/**
 * A utility class to represent key-value pairs.
 *
 * @param <K> the type of the key for this pair
 * @param <V> the type of the value to use for this pair
 */
public class Pair<K, V> {

  private final K key;
  private final V value;

  /**
   * Creates a new pair using the provided key and value.
   *
   * @param key   the key for this pair
   * @param value the value to use for this pair
   */
  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Gets the key for this pair.
   *
   * @return the key for this pair
   */
  public K getKey() {
    return key;
  }

  /**
   * Gets the value for this pair.
   *
   * @return the value for this pair
   */
  public V getValue() {
    return value;
  }
}
