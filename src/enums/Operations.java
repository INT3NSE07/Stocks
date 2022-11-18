package enums;

/**
 * This enum represents the operations which can be performed during a transaction.
 */
public enum Operations {
  BUY("BUY"),
  SELL("SELL");

  private final String operation;

  Operations(String operation) {
    this.operation = operation;
  }

  /**
   * Return the enum constant associated with the operation.
   *
   * @param operation the operation to be converted into an enum constant
   * @return the enum constant associated with the operation
   * @throws IllegalArgumentException if the operation is invalid
   */
  public static Operations getOperationByValue(String operation)
      throws IllegalArgumentException {
    for (Operations op : Operations.values()) {
      if (op.operation.equals(operation)) {
        return op;
      }
    }

    throw new IllegalArgumentException("Invalid operation type.");
  }

  /**
   * Return the operation associated with the operation type.
   *
   * @return the operation associated with the operation type
   */
  public String getOperation() {
    return this.operation;
  }
}
