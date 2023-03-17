package black.orange.rutube.exception.auth;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message + " не существует в базе данных!!!");
    }
}
