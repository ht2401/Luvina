/**
 * Copyright(C) 2023 Luvina Software Company
 * UpdateEmployeeDTO.java, June 13/2023  hathang
 */
package com.luvina.la.dto;
import lombok.Data;
import java.util.List;

/**
 * UpdateEmployeeDTO: Đối tượng DTO dùng để chứa thông tin của nhân viên cập nhật
 * @author hathang
 */
@Data
public class UpdateEmployeeDTO {
    private Long employeeId;
    private String employeeName;
    private String employeeNameKana;
    private String employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeLoginId;
    private String employeeLoginPassword;
    private Long departmentId;
    private List<Employee_certificationDTO> certifications;
}
