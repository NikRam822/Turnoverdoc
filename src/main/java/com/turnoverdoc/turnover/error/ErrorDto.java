package com.turnoverdoc.turnover.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDto extends RuntimeException {
    private String name;
    private String description;
}
