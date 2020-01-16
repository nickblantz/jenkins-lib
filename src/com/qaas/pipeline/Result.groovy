package com.qaas.pipeline

class Result implements Serializable {
    Result result
    String message
    Throwable cause
    
    public Result getResult() {
        return this.result
    }

    public String getMessage() {
        return this.message
    }

    public Throwable getCause() {
        return this.cause
    }
}