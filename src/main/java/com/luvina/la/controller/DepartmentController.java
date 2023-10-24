/**
 * Copyright(C) 2023 Luvina Software Company
 * DepartmentController.java, June 13/2023  hathang
 */

package com.luvina.la.controller;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * DepartmentController: Handles department-related API requests.
 * @author hathang
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    /**
     * getAllDepartments: lấy danh sách department
     * @return Map<String, Object>: trả về danh sách department hoặc thông báo lỗi
     */
    @GetMapping
    public Map<String, Object> getAllDepartments(){
        try{
            // Lấy dữ liệu phòng ban từ service
            List<Object[]> departmentsData = departmentService.getAllDepartments();
            List<DepartmentDTO> departmentDTOList = new ArrayList<>();
            // Chuyển đổi dữ liệu phòng ban thành đối tượng DTO
            for(Object[] department: departmentsData){
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentId((Long) department[0]);
                departmentDTO.setDepartmentName((String) department[1]);
                departmentDTOList.add(departmentDTO);
            }
            // Chuẩn bị phản hồi
            Map<String, Object> response = new HashMap<>();
            response.put("code", "200");
            response.put("totalRecords", departmentDTOList.size());
            response.put("departmentsList", departmentDTOList);
            return response;
        }
        catch(Exception e){
            // Xử lý ngoại lệ và chuẩn bị phản hồi lỗi
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", "500");
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("code", "ER015");
            errorMessage.put("params", new ArrayList<>());
            errorResponse.put("message", errorMessage);
            return errorResponse;
        }
    }
}
