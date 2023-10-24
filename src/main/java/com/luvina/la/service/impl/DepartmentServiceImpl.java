/**
 * Copyright(C) 2023 Luvina Software Company
 * DepartmentServiceImpl.java, June 13/2023  hathang
 */
package com.luvina.la.service.impl;

import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * DepartmentServiceImpl: Lớp triển khai giao diện DepartmentService.
 * @author hathang
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository repo;

    /**
     * getAllDepartments: Lấy danh sách tất cả các phòng ban.
     * @return List<Object[]>: Danh sách các đối tượng phòng ban.
     */
    @Override
    public List<Object[]> getAllDepartments(){
        return repo.getListDepartment();
    }

    /**
     * isDepartmentIdExist: Kiểm tra xem phòng ban có tồn tại trong cơ sở dữ liệu hay không, dựa trên ID của phòng ban.
     * @param departmentId ID của phòng ban cần kiểm tra sự tồn tại.
     * @return boolean: true nếu phòng ban tồn tại, false nếu không tồn tại.
     * Phương thức này sử dụng DepartmentRepository để kiểm tra sự tồn tại của phòng ban dựa trên ID.
     */
    public boolean isDepartmentIdExist(Long departmentId) {
        boolean isExitDepartment = repo.existsById(departmentId);
        return isExitDepartment;
    }
}