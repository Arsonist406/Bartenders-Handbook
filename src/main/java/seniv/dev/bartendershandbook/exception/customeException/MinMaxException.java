package seniv.dev.bartendershandbook.exception.customeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MinMaxException extends RuntimeException {
    public MinMaxException(String message) {
        super(message);
    }
}
