package com.book.test.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteBookResponse {

    private String message;
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }




}
