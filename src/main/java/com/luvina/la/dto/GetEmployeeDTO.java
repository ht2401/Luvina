/**
 * Copyright(C) 2023 Luvina Software Company
 * GetEmployeeDTO.java, June 13/2023  hathang
 */
package com.luvina.la.dto;
import lombok.Data;
import java.util.List;
/**
 * GetEmployeeDTO: Đối tượng DTO dùng để chứa thông tin của nhân viên lấy theo employeeId
 * @author hathang
 */
@Data
public class GetEmployeeDTO {
    private Long employeeId;
    private String employeeLoginId;
    private String employeeName;
    private String employeeNameKana;
    private String employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private Long departmentId;
    private String departmentName;
    private List<Employee_certificationDTO> certifications;
}