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
        return new ResponseEntity(new Response(null, e.getMessage()), HttpStatus.I_AM_A_TEAPOT);
    }
}
