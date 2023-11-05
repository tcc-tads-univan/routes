package br.tads.ufpr.routes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ExternalApiException extends RuntimeException {
    public ExternalApiException(Throwable cause) {
        super("Error while requesting external API", cause);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
