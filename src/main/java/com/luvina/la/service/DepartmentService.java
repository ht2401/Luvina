/**
 * Copyright(C) 2023 Luvina Software Company
 * DepartmentService.java, June 13/2023  hathang
 */
package com.luvina.la.service;
import java.util.List;
/**
 * DepartmentService: các phương thức liên quan đến phòng ban
 * @author hathang
 */
public interface DepartmentService {
    /**
     * getAllDepartments: Lấy danh sách tất cả các phòng ban.
     * @return List<Object[]>: Danh sách các đối tượng phòng ban.
     */
    List<Object[]> getAllDepartments();

    /**
     * isDepartmentIdExist: Kiểm tra xem phòng ban có tồn tại trong cơ sở dữ liệu không dựa trên ID.
     * @param departmentId ID của phòng ban cần kiểm tra sự tồn tại.
     * @return boolean: true nếu phòng ban tồn tại, false nếu không tồn tại.
     * Phương thức này kiểm tra xem phòng ban có tồn tại trong cơ sở dữ liệu dựa trên ID đã cho hay không.
     */
    boolean isDepartmentIdExist(Long departmentId);
}