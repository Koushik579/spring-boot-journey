package practice_02._0._6.koushik.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(ResourceNotFoundException ex){
        ErrorResponse errResponse = new ErrorResponse();
        errResponse.setErrStatus(404);
        errResponse.setMsg(ex.getMessage());
        return ResponseEntity.status(404).body(errResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleValidationError(MethodArgumentNotValidException ex){
        List<FieldError> error = ex.getBindingResult().getFieldErrors();
        List<ValidationErrorResponse> errList = new ArrayList<>();
        for(FieldError err : error){
            ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse();
            validationErrorResponse.setField(err.getField());
            validationErrorResponse.setRejectedValue(err.getRejectedValue());
            validationErrorResponse.setMsg(err.getDefaultMessage());
            errList.add(validationErrorResponse);
        }
        return ResponseEntity.badRequest().body(errList);
    }

}
