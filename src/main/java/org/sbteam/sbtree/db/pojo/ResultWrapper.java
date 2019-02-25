package org.sbteam.sbtree.db.pojo;

public class ResultWrapper<T> {
    private T result;

    private String token;

    public ResultWrapper() {}

    public ResultWrapper(T result) {
        this.result = result;
    }

    public ResultWrapper(T result, String token) {
        this.result = result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}