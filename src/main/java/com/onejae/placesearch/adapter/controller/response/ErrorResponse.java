package com.onejae.placesearch.adapter.controller.response;

import com.onejae.placesearch.adapter.controller.ErrorCode;

public class ErrorResponse extends ResponseBase<String> {
    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.data = errorCode.getMessage();
        this.kind = "error";
    }
}
