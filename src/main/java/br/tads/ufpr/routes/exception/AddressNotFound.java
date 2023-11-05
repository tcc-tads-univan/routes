package br.tads.ufpr.routes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AddressNotFound extends RuntimeException {
    public AddressNotFound() {
        super("No address was found");
    }
    public AddressNotFound(String message) {
        super(message);
    }
}
