package seniv.dev.bartendershandbook.exception.customeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IsAlreadyTakenException extends RuntimeException {

    private final String whatIsAlreadyTaken;
    private final String whatIsAlreadyTakenArgument;

}
