package com.onejae.placesearch.adapter.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ResponseBase<T> {
    protected int status;
    protected String kind;
    protected T data;
}