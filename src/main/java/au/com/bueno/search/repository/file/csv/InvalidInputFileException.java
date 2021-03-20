package au.com.bueno.search.repository.file.csv;

public class InvalidInputFileException extends RuntimeException {
  public InvalidInputFileException(String message) {
    super(message);
  }
}
