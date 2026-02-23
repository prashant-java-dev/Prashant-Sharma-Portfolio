package com.prashant.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * UTILITY: Generic API Response Wrapper.
 * 
 * Consistent Response Structure is a hallmark of professional enterprise APIs. 
 * This class ensures that every endpoint returns a predictable JSON object 
 * containing success status, a message, and optional data.
 * 
 * @param <T> The type of the data payload.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;

    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Factory method for successful operations with data.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /**
     * Overloaded factory method for success without data payload.
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    /**
     * Factory method for error cases.
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    /**
     * Factory method for validation errors using a specific data map.
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}
