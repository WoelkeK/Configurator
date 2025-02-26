package pl.woelke.configurator.error;

import lombok.Value;

@Value
public class ErrorResponse {

    int status;
    String message;

}
