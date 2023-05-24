package com.example.captaincat.api.core;

/**
 * 请求类型实体类
 */
public class RequestType<T> {
    public final int status;
    public final String message;
    public final T data;

    public RequestType(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestType{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
