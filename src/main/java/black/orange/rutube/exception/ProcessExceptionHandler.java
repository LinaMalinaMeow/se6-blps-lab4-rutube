package black.orange.rutube.exception;

import black.orange.rutube.controller.AdminController;
import black.orange.rutube.controller.AuthController;
import black.orange.rutube.controller.VideoController;
import black.orange.rutube.dto.MessageDto;
import black.orange.rutube.exception.auth.JwtTokenException;
import black.orange.rutube.exception.auth.WrongAuthException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(assignableTypes = {AuthController.class, VideoController.class, AdminController.class})
public class ProcessExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageDto.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageDto.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotExistsException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageDto.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(WrongAuthException.class)
    public ResponseEntity<?> handleWrongAuthException(WrongAuthException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageDto.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<?> handleJwtTokenException(JwtTokenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageDto.builder().message(e.getMessage()).build());
    }

    @ExceptionHandler({ConstraintViolationException.class,
            BindException.class, MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleValidationException(Exception ex) {
        String message = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException exception) {
            message = "Некорректные параметры " + getValidationError(exception);
        }
        return ResponseEntity.badRequest().body(MessageDto.builder().message(message).build());
    }

    private String getValidationError(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining(";"));
    }
}
