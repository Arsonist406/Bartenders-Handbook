package seniv.dev.bartendershandbook.exceptionHandler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IsAlreadyTakenException extends RuntimeException {

    private final String whatIsAlreadyTaken;
    private final String whatIsAlreadyTakenArgument;

}
