package com.onejae.placesearch.adapter.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MISSING_PARAMS(-1, "Please check parameter"),
    INVALID_URI(-2, "Please check api url"),
    UNKNOWN_ERROR(-3, "We are sorry, operation failed due to internal issue");

    private final int status;
    private final String message;
}
