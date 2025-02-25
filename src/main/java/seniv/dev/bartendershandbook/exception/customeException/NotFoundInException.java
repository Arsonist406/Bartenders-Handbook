package seniv.dev.bartendershandbook.exception.customeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class NotFoundInException extends RuntimeException {

    private final Class<?> whatNotFound;
    private final String inWhat;
    private final Set<String> inWhatArguments;

}
