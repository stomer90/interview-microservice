package com.stormer.common.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class DemoRuntimeException extends RuntimeException {
    private final String errCode;
    private final String errMsg;
    private final Object response;

    public DemoRuntimeException(String errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.response = null;
    }

}
