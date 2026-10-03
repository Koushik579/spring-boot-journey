package practice_02._0._6.koushik.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(ResourceNotFoundException ex){
        ErrorResponse errResponse = new ErrorResponse();
        errResponse.setErrStatus(404);
        errResponse.setMsg(ex.getMessage());
        return ResponseEntity.status(404).body(errResponse);
    }

}
