/**
 * Copyright(C) 2023 Luvina Software Company
 *
 * CertificationController.java, June 13/2023  hathang
 */
package com.luvina.la.controller;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.service.CertificationService;
import com.luvina.la.until.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * CertificationController: Xử lý các yêu cầu API liên quan đến chứng chỉ.
 * @author hathang
 */
@RestController
@RequestMapping("/certifications")
public class CertificationController {
    @Autowired
    CertificationService certificationService;
    /**
     * getAllCertification: Lấy tất cả các chứng chỉ.
     * @return Map<String, Object>: Phản hồi chứa dữ liệu chứng chỉ hoặc thông báo lỗi.
     */
    @GetMapping
    public Map<String, Object> getAllCertification(){
        try{
            List<Object[]> certificationData = certificationService.getAllCertification();
            List<CertificationDTO> certificationDTOList = new ArrayList<>();
            // Duyệt mảng lấy ra thông tin certification trong DB
            for(Object[] certification: certificationData){
                CertificationDTO certificationDTO = new CertificationDTO();
                certificationDTO.setCertificationId((Long) certification[0]);
                certificationDTO.setCertificationName((String) certification[1]);
                certificationDTOList.add(certificationDTO);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("code", MessageConstant.CODE_200);
            response.put("totalRecords", certificationDTOList.size());
            response.put("certificationsList", certificationDTOList);
            return response;
        }
        catch(Exception e){
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", MessageConstant.CODE_500);
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("code", MessageConstant.CODE_ER015);
            errorMessage.put("params", new ArrayList<>());
            errorResponse.put("message", errorMessage);
            return errorResponse;
        }
    }
}