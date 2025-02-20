package seniv.dev.bartendershandbook.exceptionHandler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinMaxException extends RuntimeException {
    public MinMaxException(String message) {
        super(message);
    }
}
