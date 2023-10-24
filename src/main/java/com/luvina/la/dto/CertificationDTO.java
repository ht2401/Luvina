/**
 * Copyright(C) 2023 Luvina Software Company
 * CertificationDTO.java, June 29/2023  hathang
 */
package com.luvina.la.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * CertificationDTO: Đối tượng DTO dùng để chứa thông tin chứng chỉ tiếng Nhật của nhân viên
 * @author hathang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDTO implements Serializable {
    private static final long serialVersionUID = 6868189362900231672L;
    private Long certificationId;
    private String certificationName;
}