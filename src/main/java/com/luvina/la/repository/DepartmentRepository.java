/**
 * Copyright(C) 2023 Luvina Software Company
 * DepartmentRepository.java, June 13/2023  hathang
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * DepartmentRepository: Giao diện để tương tác với thực thể Department trong cơ sở dữ liệu.
 * Giao diện này mở rộng JpaRepository để kế thừa các hoạt động CRUD cơ bản.
 * Author: hathang
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    /**
     * Lấy danh sách các phòng ban với mã và tên của chúng.
     * @return List<Object[]>: Danh sách dữ liệu phòng ban dưới dạng mảng Object.
     * Phương thức này truy xuất danh sách các phòng ban với mã và tên phòng ban của chúng..
     */
    @Query("SELECT d.departmentId, d.departmentName FROM Department d")
    List<Object[]> getListDepartment();
    /**
     * Kiểm tra xem một phòng ban có tồn tại trong cơ sở dữ liệu hay không, dựa trên departmentId được cung cấp.
     * @param departmentId ID của phòng ban cần kiểm tra sự tồn tại.
     * @return boolean: true nếu có một phòng ban có departmentId được cung cấp, false nếu không tồn tại.
     * Phương thức này kiểm tra xem một phòng ban có tồn tại trong cơ sở dữ liệu hay không, dựa trên departmentId được cung cấp.
     */
    boolean existsById(Long departmentId);
}