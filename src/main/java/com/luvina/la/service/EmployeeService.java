/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeService.java, June 13/2023  hathang
 */
package com.luvina.la.service;

import com.luvina.la.dto.AddEmployeeDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.GetEmployeeDTO;
import com.luvina.la.dto.UpdateEmployeeDTO;
import com.luvina.la.entity.Employee;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * EmployeeService: các phương thức liên quan đến nhân viên
 * @author hathang
 */
@Service
public interface EmployeeService {
    /**
     * getAllEmployees: Lấy danh sách tất cả nhân viên với các bộ lọc và tham số phân trang được chỉ định.
     * @param employeeName           (tùy chọn) Lọc theo tên nhân viên.
     * @param departmentId           (tùy chọn) Lọc theo ID phòng ban.
     * @param ordEmployeeName        (tùy chọn, mặc định: "ASC") Sắp xếp theo tên nhân viên ("ASC" cho sắp xếp tăng dần, "DESC" cho sắp xếp giảm dần).
     * @param ordCertificationName   (tùy chọn, mặc định: "ASC") Sắp xếp theo tên chứng chỉ ("ASC" cho sắp xếp tăng dần, "DESC" cho sắp xếp giảm dần).
     * @param ordEndDate             (tùy chọn, mặc định: "ASC") Sắp xếp theo ngày kết thúc ("ASC" cho sắp xếp tăng dần, "DESC" cho sắp xếp giảm dần).
     * @param offset                 (tùy chọn, mặc định: 0) Bước nhảy cho phân trang.
     * @param limit                  (tùy chọn, mặc định: 5) Giới hạn số lượng bản ghi trả về trong mỗi trang.
     * @return List<EmployeeDTO>: Danh sách các đối tượng nhân viên.
     */
    List<EmployeeDTO> getAllEmployees(String employeeName,
                                      Long departmentId,
                                      String ordEmployeeName,
                                      String ordCertificationName,
                                      String ordEndDate,
                                      Integer offset,
                                      Integer limit
    );

    /**
     * getTotalEmployees: Lấy tổng số nhân viên với các bộ lọc chỉ định.
     * @param employeeName   (tùy chọn) Lọc theo tên nhân viên.
     * @param departmentId   (tùy chọn) Lọc theo ID phòng ban.
     * @return int: Tổng số nhân viên.
     */
    int getTotalEmployees(String employeeName, Long departmentId);

    /**
     * addEmployee: Thêm một nhân viên mới.
     * @param addEmployeeDTO: Dữ liệu cho nhân viên mới.
     * @return Long: ID của nhân viên vừa được tạo.
     */
    Long addEmployee(AddEmployeeDTO addEmployeeDTO);

    /**
     * isEmployeeLoginIdExist: Kiểm tra xem employeeLoginId đã tồn tại trong cơ sở dữ liệu chưa.
     * @param employeeLoginId employeeLoginId cần kiểm tra.
     * @return boolean: true nếu employeeLoginId tồn tại, false nếu không tồn tại.
     */
    boolean isEmployeeLoginIdExist(String employeeLoginId);

    /**
     * Tìm kiếm thông tin của một nhân viên dựa vào employeeId.
     * @param employeeId Id của nhân viên cần tìm kiếm thông tin.
     * @return GetEmployeeDTO: Dữ liệu của nhân viên được tìm thấy hoặc null nếu không tồn tại employeeId.
     */
    GetEmployeeDTO getEmployeeById(Long employeeId);

    /**
     * Xóa nhân viên dựa vào employeeId.
     * @param employeeId ID của nhân viên cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy nhân viên với ID tương ứng
     */
    boolean deleteEmployeeById(Long employeeId);

    /**
     * Cập nhật nhân viên dựa vào employeeId.
     * @param employeeId ID của nhân viên cần Cập nhật
     * @param updateEmployeeDTO Các dữ liệu của nhân viên cần Cập nhật
     * @return true nếu Cập nhật thành công, false nếu không tìm thấy nhân viên với ID tương ứng
     */
    Employee updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) throws Exception;


    public List<Map<String, Object>> validateEmployee(AddEmployeeDTO addEmployeeDTO);
}