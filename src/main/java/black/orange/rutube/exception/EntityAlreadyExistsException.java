package black.orange.rutube.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message + " уже существует в базе данных!!!");
    }
}
