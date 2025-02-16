package seniv.dev.bartendershandbook.exceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        List<ErrorDetail> details,
        String path
) {}
