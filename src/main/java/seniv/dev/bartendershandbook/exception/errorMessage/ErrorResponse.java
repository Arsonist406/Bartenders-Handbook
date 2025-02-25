package seniv.dev.bartendershandbook.exception.errorMessage;

import java.time.LocalDateTime;
import java.util.Set;

public record ErrorResponse(

        LocalDateTime timestamp,
        int status,
        String error,
        Set<ErrorDetail> details,
        String path

) {}
