/**
 * Copyright(C) 2023 Luvina Software Company
 * AddEmployeeDTO.java, June 13/2023  hathang
 */
package com.luvina.la.dto;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import java.util.List;
/**
 * AddEmployeeDTO: Đối tượng DTO dùng để chứa thông tin của nhân viên mới
 * @author hathang
 */
@Data
@Validated
public class AddEmployeeDTO {
    private Long employeeId;
    private String employeeLoginId;
    private String employeeName;
    private String employeeNameKana;
    private String employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeLoginPassword;
    private Long departmentId;
    private List<Employee_certificationDTO> certifications;
}