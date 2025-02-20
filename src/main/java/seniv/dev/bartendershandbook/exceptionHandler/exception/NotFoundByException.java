package seniv.dev.bartendershandbook.exceptionHandler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundByException extends RuntimeException {

    private final Class<?> whatNotFound;
    private final String byWhat;
    private final Object byWhatArgument;

}
