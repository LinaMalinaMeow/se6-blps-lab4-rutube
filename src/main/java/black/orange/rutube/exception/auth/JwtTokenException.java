package black.orange.rutube.exception.auth;

public class JwtTokenException extends RuntimeException {
    public JwtTokenException() {
        super("Токен недействителен/невалиден!!!");
    }
}
