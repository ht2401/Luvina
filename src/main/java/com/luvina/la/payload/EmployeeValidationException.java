/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeValidationException.java, June 13/2023  hathang
 */
package com.luvina.la.payload;
import lombok.Data;
/**
 * Cấu hình class để khai báo các biến lưu trữ lỗi, mã lỗi và message lỗi
 * @author hathang
 */
@Data
public class EmployeeValidationException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    private String errorParameter;
    public EmployeeValidationException(String errorCode, String errorMessage, String errorParameter) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorParameter = errorParameter;
    }
}
