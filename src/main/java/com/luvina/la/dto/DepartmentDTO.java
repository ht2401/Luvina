/**
 * Copyright(C) 2023 Luvina Software Company
 * DepartmentDTO.java, June 13/2023  hathang
 */
package com.luvina.la.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * DepartmentDTO: Đối tượng DTO dùng để chứa thông tin phòng ban của nhân viên
 * @author hathang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = 6868189362900231672L;
    private Long departmentId;
    private String departmentName;
}