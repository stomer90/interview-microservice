package com.stormer.common.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ApiResponse{
    private String error;
    private String message;
}
