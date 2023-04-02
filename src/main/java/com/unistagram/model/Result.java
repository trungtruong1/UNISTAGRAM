package com.unistagram.model;

public class Result<T> {
    private boolean is_ok;

    private T result;

    Result() {}

    public Result(boolean is_ok) {
        this.is_ok = false;
    }

    public Result(boolean is_ok, T result) {
        this.is_ok = is_ok;
        this.result = result;
    }
    
    public boolean isOK() {
        return is_ok;
    }

    public T getResult() {
        return result;
    }
}
