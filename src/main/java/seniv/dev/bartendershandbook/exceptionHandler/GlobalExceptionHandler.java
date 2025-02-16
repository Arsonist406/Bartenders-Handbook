package seniv.dev.bartendershandbook.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

//TODO: подумати над іншими ExceptionHandler
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ErrorDetail(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                )).toList();

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException ex,
            WebRequest request
    ) {
        List<ErrorDetail> details = ex.getConstraintViolations().stream()
                .map(violation -> new ErrorDetail(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                )).toList();

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }
}

