/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeDTO.java, June 29/2023  hathang
 */
package com.luvina.la.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import lombok.Data;
/**
 * EmployeeDTO: Đối tượng DTO dùng để chứa thông tin của nhân viên
 * @author hathang
 */
@Data
public class EmployeeDTO implements Serializable {
    private static final long serialVersionUID = 6868189362900231672L;
    private BigInteger employeeId;
    private String employeeName;
    private Date employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private String departmentName;
    private String certificationName;
    private Date endDate;
    private BigDecimal score;
}