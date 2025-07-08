package com.book.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ErrorResponse {

    @JsonProperty("detail")
    private List<ErrorDetail> detail;

    public List<ErrorDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ErrorDetail> detail) {
        this.detail = detail;
    }

    public static class ErrorDetail {

        @JsonProperty("type")
        private String type;

        @JsonProperty("loc")
        private List<String> loc;

        @JsonProperty("msg")
        private String msg;

        @JsonProperty("input")
        private Object input;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getLoc() {
            return loc;
        }

        public void setLoc(List<String> loc) {
            this.loc = loc;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getInput() {
            return input;
        }

        public void setInput(Object input) {
            this.input = input;
        }
    }
}
