package com.HW.WEEK2.RestWeek.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class APIResponse<T> {
    private LocalDateTime timestamp;


    private T data;
    private APIError error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(APIError error) {
        this();
        this.error = error;
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }
}
