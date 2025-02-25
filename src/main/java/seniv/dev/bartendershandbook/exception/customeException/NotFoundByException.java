package seniv.dev.bartendershandbook.exception.customeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundByException extends RuntimeException {

    private final Class<?> whatNotFound;
    private final String byWhat;
    private final Object byWhatArgument;

}
