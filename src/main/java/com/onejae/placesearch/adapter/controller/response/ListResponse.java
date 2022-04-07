package com.onejae.placesearch.adapter.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ListResponse<T> extends ResponseBase<ArrayList<T>> {
    public ListResponse(String kind) {
        this.data = new ArrayList<>();
        this.kind = kind;
    }

    public void appendData(T data) {
        this.data.add(data);
    }
}
