package gguip1.community.global.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private ErrorResponse error;
}