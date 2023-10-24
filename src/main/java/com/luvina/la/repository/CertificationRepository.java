/**
 * Copyright(C) 2023 Luvina Software Company
 * CertificationRepository.java, June 13/2023  hathang
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * CertificationRepository: Giao diện để tương tác với thực thể Certification trong cơ sở dữ liệu.
 * Giao diện này mở rộng JpaRepository để kế thừa các hoạt động CRUD cơ bản.
 * Author: hathang
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
    /**
     * Lấy danh sách các chứng chỉ với mã và tên của chúng.
     * @return List<Object[]>: Danh sách dữ liệu chứng chỉ dưới dạng mảng Object.
     * Phương thức này truy xuất danh sách các chứng chỉ với mã và tên chứng chỉ của chúng
     */
    @Query("SELECT c.certificationID, c.certificationName FROM Certification c")
    List<Object[]> getListCertification();
}