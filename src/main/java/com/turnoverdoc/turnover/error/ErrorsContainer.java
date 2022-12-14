package com.turnoverdoc.turnover.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

public class ErrorsContainer {
    public static ErrorDto TURN1 = ErrorConverter.convertToErrorDto("TURN1");
    public static ErrorDto TURN2 = ErrorConverter.convertToErrorDto("TURN2");
    public static ErrorDto TURN3 = ErrorConverter.convertToErrorDto("TURN3");
}
