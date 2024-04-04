package Final.Project.dodo.exception;


import Final.Project.dodo.model.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> exceptionHandle(NotFoundException e) {
        return new ResponseEntity<>(new Response(null, e.getMessage()), HttpStatus.I_AM_A_TEAPOT);
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> exceptionHandle(AuthException e) {
        return new ResponseEntity<>(new Response(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> exceptionHandle(RuntimeException e) {
        return new ResponseEntity<>(new Response(null, e.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AuthNotAcceptableException.class)
    public ResponseEntity<?> exceptionHandle(AuthNotAcceptableException e) {
        return new ResponseEntity<>(new Response(null, e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> exceptionHandle(JwtException e) {
        return new ResponseEntity<>(new Response(null, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

}
