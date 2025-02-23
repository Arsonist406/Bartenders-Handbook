package seniv.dev.bartendershandbook.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import seniv.dev.bartendershandbook.exceptionHandler.errorMessage.ErrorDetail;
import seniv.dev.bartendershandbook.exceptionHandler.errorMessage.ErrorResponse;
import seniv.dev.bartendershandbook.exceptionHandler.exception.IsAlreadyTakenException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.MinMaxException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundByException;
import seniv.dev.bartendershandbook.exceptionHandler.exception.NotFoundInException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        Set<ErrorDetail> details = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError ->
                        new ErrorDetail(
                                fieldError.getField(),
                                fieldError.getDefaultMessage()
                        )
                ).collect(Collectors.toSet());

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
        Set<ErrorDetail> details = ex.getConstraintViolations()
                .stream()
                .map(violation ->
                        new ErrorDetail(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                ).collect(Collectors.toSet());

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotFoundByException.class})
    public ErrorResponse handleNotFoundByException(
            NotFoundByException ex,
            WebRequest request
    ) {
        String message = "%s not found by %s='%s'".formatted(
                ex.getWhatNotFound().getSimpleName(),
                ex.getByWhat(),
                ex.getByWhatArgument()
        );

        Set<ErrorDetail> details = new HashSet<>();
        details.add(new ErrorDetail(ex.getByWhat(), message));

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IsAlreadyTakenException.class})
    public ErrorResponse handleIsAlreadyTakenExceptionException(
            IsAlreadyTakenException ex,
            WebRequest request
    ) {
        String message = "'%s' is already taken".formatted(
                ex.getWhatIsAlreadyTakenArgument()
        );

        Set<ErrorDetail> details = new HashSet<>();
        details.add(new ErrorDetail(ex.getWhatIsAlreadyTaken(), message));

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MinMaxException.class})
    public ErrorResponse handleMinMaxExceptionException(
            MinMaxException ex,
            WebRequest request
    ) {
        Set<ErrorDetail> details = new HashSet<>();
        details.add(new ErrorDetail("min", ex.getMessage()));

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NotFoundInException.class})
    public ErrorResponse handleNotFoundInExceptionException(
            NotFoundInException ex,
            WebRequest request
    ) {
        Set<ErrorDetail> details = ex.getInWhatArguments()
                .stream()
                .map(obj ->
                        new ErrorDetail(
                                ex.getInWhat(),
                                "%s not found by %s=%s".formatted(ex.getWhatNotFound().getSimpleName(), ex.getInWhat(), obj)
                        )
                ).collect(Collectors.toSet());

        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                details,
                request.getDescription(false)
        );
    }
}

