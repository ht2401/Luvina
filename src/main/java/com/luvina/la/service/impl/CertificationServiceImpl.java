/**
 * Copyright(C) 2023 Luvina Software Company
 * CertificationServiceImpl.java, June 13/2023  hathang
 */
package com.luvina.la.service.impl;

import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * CertificationServiceImpl: Triển khai giao diện CertificationService.
 * @author hathang
 */
@Service
@Transactional
public class CertificationServiceImpl implements CertificationService {
    @Autowired
    CertificationRepository repo;

    /**
     * getAllCertification: Lấy danh sách tất cả bằng cấp.
     * @return List<Object[]>: Danh sách các đối tượng bằng cấp.
     */
    @Override
    public List<Object[]> getAllCertification() {
        return repo.getListCertification();
    }

    /**
     * isCertificationIdExist: Kiểm tra xem bằng cấp có tồn tại trong cơ sở dữ liệu hay không, dựa trên ID của bằng cấp.
     * @param certificationId ID của bằng cấp cần kiểm tra sự tồn tại.
     * @return boolean: true nếu bằng cấp tồn tại, false nếu không tồn tại.
     * Phương thức này sử dụng CertificationRepository để kiểm tra sự tồn tại của bằng cấp dựa trên ID.
     */
    public boolean isCertificationIdExist(Long certificationId) {
        boolean isExitCertification = repo.existsById(certificationId);
        return isExitCertification;
    }
}