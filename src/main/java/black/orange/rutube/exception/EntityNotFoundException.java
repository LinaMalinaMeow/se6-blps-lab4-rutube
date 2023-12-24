package black.orange.rutube.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message + " не существует в базе данных!!!");
    }
}