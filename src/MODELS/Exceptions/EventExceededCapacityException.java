package MODELS.Exceptions;

public class EventExceededCapacityException extends RuntimeException {
  public EventExceededCapacityException(String message) {
    super(message);
  }
}
