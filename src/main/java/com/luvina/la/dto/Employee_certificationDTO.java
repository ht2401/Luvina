/**
 * Copyright(C) 2023 Luvina Software Company
 * Employee_certificationDTO.java, June 29/2023  hathang
 */
package com.luvina.la.dto;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Employee_certificationDTO: Đối tượng DTO dùng để chứa thông tin chứng chỉ tiếng Nhật của nhân viên
 * @author hathang
 */
@Data
@Validated
public class Employee_certificationDTO implements Serializable {
    private static final long serialVersionUID = 5771173953267484096L;
    private Long certificationId;
    private String certificationName;
    private String certificationStartDate;
    private String certificationEndDate;
    private BigDecimal employeeCertificationScore;
}