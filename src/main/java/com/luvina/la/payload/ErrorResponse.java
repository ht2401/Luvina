/**
 * Copyright(C) 2023 Luvina Software Company
 * ErrorResponse.java, June 13/2023  hathang
 */
package com.luvina.la.payload;
import lombok.Data;
import java.util.Map;
/**
 * dùng để chứa các mã lỗi
 * @author hathang
 */
@Data
public class ErrorResponse {
    private int code;
    private Long employeeId;
    private Map<String, Object> message;
}
