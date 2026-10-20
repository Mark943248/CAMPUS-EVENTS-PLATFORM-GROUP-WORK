package MODELS.Exceptions;

public class DuplicateScannedTicketException extends RuntimeException {
  public DuplicateScannedTicketException(String message) {
    super(message);
  }
}
