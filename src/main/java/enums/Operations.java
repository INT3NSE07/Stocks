package enums;

public enum Operations {
  BUY("BUY"),
  SELL("SELL");

  private final String operation;

  Operations(String operation) {
    this.operation = operation;
  }

  public static Operations getOperationByValue(String operation)
      throws IllegalArgumentException {
    for (Operations op : Operations.values()) {
      if (op.operation.equals(operation)) {
        return op;
      }
    }

    throw new IllegalArgumentException("Invalid gear number.");
  }

  public String getOperation() {
    return this.operation;
  }
}
