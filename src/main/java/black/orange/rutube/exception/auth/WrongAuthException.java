package black.orange.rutube.exception.auth;

public class WrongAuthException extends RuntimeException {
    public WrongAuthException() {
        super("Неверные логин и/или пароль!!!");
    }
}
