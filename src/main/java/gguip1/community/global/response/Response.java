package gguip1.community.global.response;

import lombok.Data;

@Data
public class Response<T, E> {
    private String message;
    private T data;
    private E error;
}