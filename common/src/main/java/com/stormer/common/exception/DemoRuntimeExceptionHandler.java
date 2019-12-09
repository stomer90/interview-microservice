package com.stormer.common.exception;

import com.stormer.common.constants.CodeConstants;
import com.stormer.common.models.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class DemoRuntimeExceptionHandler {

    @ExceptionHandler(DemoRuntimeException.class)
    public ResponseEntity<ApiResponse> handlerException(DemoRuntimeException ex) {
        ApiResponse response = ApiResponse.builder().error(ex.getErrMsg()).build();
        log.info(response.toString());
        if(CodeConstants.UNAUTHORIZED.equals(ex.getErrCode())){
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } else if (CodeConstants.NOT_FOUND.equals(ex.getErrCode())) {
            response = ApiResponse.builder().message(ex.getErrMsg()).build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
