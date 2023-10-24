/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeCreateResponse.java, June 13/2023  hathang
 */
package com.luvina.la.payload;
import lombok.Data;
import java.util.Map;
/**
 * Tạo class để lưu trữ các lỗi
 * @author hathang
 */
@Data
public class EmployeeCreateResponse {
    private int code;
    private long employeeId;
    private Map<String, Object> message;
}
