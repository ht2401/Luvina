/**
 * Copyright(C) 2023 Luvina Software Company
 * CertificationService.java, June 13/2023  hathang
 */
package com.luvina.la.service;
import java.util.List;
/**
 * CertificationService: các phương thức liên quan đến chứng chỉ tiếng Nhật
 * @author hathang
 */
public interface CertificationService {
    /**
     * getAllCertification: Lấy danh sách tất cả các chứng chỉ.
     * @return List<Object[]>: Danh sách các đối tượng chứng chỉ.
     */
    List<Object[]> getAllCertification();
    /**
     * isCertificationIdExist: Kiểm tra xem chứng chỉ có tồn tại trong cơ sở dữ liệu không dựa trên ID.
     * @param certificationId ID của chứng chỉ cần kiểm tra sự tồn tại.
     * @return boolean: true nếu chứng chỉ tồn tại, false nếu không tồn tại.
     * Phương thức này kiểm tra xem chứng chỉ có tồn tại trong cơ sở dữ liệu dựa trên ID đã cho hay không.
     */
    boolean isCertificationIdExist(Long certificationId);
}