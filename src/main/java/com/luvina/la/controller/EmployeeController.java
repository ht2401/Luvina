/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeController.java, June 13/2023  hathang
 */
package com.luvina.la.controller;
import com.luvina.la.dto.AddEmployeeDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.GetEmployeeDTO;
import com.luvina.la.dto.UpdateEmployeeDTO;
import com.luvina.la.entity.Employee;
import com.luvina.la.payload.ErrorResponse;
import com.luvina.la.service.CertificationService;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.until.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * EmployeeController: Xử lý các yêu cầu API liên quan đến nhân viên.
 * @author hathang
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CertificationService certificationService;
    /**
     * Lấy danh sách nhân viên, phân trang, sắp xếp.
     * @param employeeName         (tùy chọn) Lọc theo tên nhân viên
     * @param departmentId         (tùy chọn) Lọc theo mã phòng ban
     * @param ordEmployeeName      (mặc định: "ASC") Sắp xếp theo tên nhân viên ("ASC" tăng dần, "DESC" giảm dần)
     * @param ordCertificationName (mặc định: "ASC") Sắp xếp theo tên chứng chỉ ("ASC" tăng dần, "DESC" giảm dần)
     * @param ordEndDate           (mặc định: "ASC") Sắp xếp theo ngày kết thúc chứng chỉ ("ASC" tăng dần, "DESC" giảm dần)
     * @param offset               (mặc định: 0) Vị trí bắt đầu lấy dữ liệu trong phân trang
     * @param limit                (mặc định: 20) Số lượng bản ghi lấy ra trong một trang
     * @return ResponseEntity danh sách nhân viên, tổng số bản ghi, phân trang
     */
    @GetMapping
    public ResponseEntity<?> getAllEmployees(
            @RequestParam(value = "employeeName", required = false) String employeeName,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @RequestParam(value = "ordEmployeeName", defaultValue = "ASC") String ordEmployeeName,
            @RequestParam(value = "ordCertificationName", defaultValue = "ASC") String ordCertificationName,
            @RequestParam(value = "ordEndDate", defaultValue = "ASC") String ordEndDate,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {
        try {
            if (employeeName != null) {
                if (employeeName.contains("%") || employeeName.contains("_")) {
                    employeeName = "\\" + employeeName.trim();
                }
                System.out.println(employeeName);
            }
            if (!ordEmployeeName.equalsIgnoreCase("ASC")
                    && !ordEmployeeName.equalsIgnoreCase("DESC") ||
                    !ordCertificationName.equalsIgnoreCase("ASC")
                            && !ordCertificationName.equalsIgnoreCase("DESC") ||
                    !ordEndDate.equalsIgnoreCase("ASC")
                            && !ordEndDate.equalsIgnoreCase("DESC")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code: ER021");
            }
            if (offset < 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstant.CODE_ER018);
            }
            if (limit <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageConstant.CODE_ER018);
            }
            // Lấy danh sách employee từ database
            List<EmployeeDTO> employees = employeeService.getAllEmployees(employeeName, departmentId, ordEmployeeName,
                    ordCertificationName, ordEndDate, offset, limit);
            // Lấy tổng số bản ghi
            int totalRecords = employeeService.getTotalEmployees(employeeName, departmentId);
            Map<String, Object> response = new HashMap<>();
            response.put("code", MessageConstant.CODE_200);
            response.put("totalRecords", totalRecords);
            response.put("employees", employees);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Xử lý ngoại lệ và chuẩn bị phản hồi lỗi
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", MessageConstant.CODE_500);
            Map<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("code", MessageConstant.CODE_ER015);
            errorMessage.put("params", new ArrayList<>());
            errorResponse.put("message", errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    /**
     * Thêm một nhân viên vào cơ sở dữ liệu.
     * @param addEmployeeDTO Đối tượng chứa thông tin nhân viên cần thêm
     * @return ResponseEntity Kết quả thêm nhân viên vào cơ sở dữ liệu
     */
    @PostMapping("/employees")
    public ResponseEntity<?> addEmployee(@RequestBody AddEmployeeDTO addEmployeeDTO) {
        try {
            // Validate các trường
            List<Map<String, Object>> errorMessages = employeeService.validateEmployee(addEmployeeDTO);
            // Nếu có lỗi, trả về kết quả lỗi
            if (!errorMessages.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", MessageConstant.CODE_400);
                errorResponse.put("message", errorMessages);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
            Long employeeId = employeeService.addEmployee(addEmployeeDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("code", MessageConstant.CODE_400);
            response.put("employeeId", employeeId);
            Map<String, Object> message = new HashMap<>();
            message.put("code", "ユーザの登録が完了しました。");
            message.put("params", new ArrayList<>());
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Trường hợp kết nối với CSDL không thành công
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", MessageConstant.CODE_500);
            Map<String, Object> Message = new HashMap<>();
            Message.put("code", "ER015");
            Message.put("params", "システムエラーが発生しました。");
            errorResponse.put("message", Message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    /**
     * Phương thức tạo lỗi khi validate
     * @param errorCode mã lỗi
     * @param parameterName thông báo lỗi
     * @return danh sách thông báo lỗi
     */
    private Map<String, Object> createErrorMessage(String errorCode, String parameterName) {
        Map<String, Object> Message = new HashMap<>();
        Message.put("code", errorCode);
        Message.put("params", parameterName);
        return Message;
    }
    /**
     * Lấy thông tin của một nhân viên dựa vào employeeId.
     * @param employeeId Id của nhân viên cần lấy thông tin.
     * @return ResponseEntity chứa dữ liệu của nhân viên hoặc trả về lỗi nếu không tồn tại employeeId đó.
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long employeeId) {
       try {
           // lấy thông tin của nhân viên dựa vào employeeId
           GetEmployeeDTO employeeInfoDTO = employeeService.getEmployeeById(employeeId);
           if (employeeInfoDTO != null) {
               // Trả về dữ liệu nhân viên nếu tìm thấy
               return ResponseEntity.ok(employeeInfoDTO);
           } else {
               // Trường hợp kết nối với CSDL không thành công
               Map<String, Object> errorResponse = new HashMap<>();
               errorResponse.put("code", MessageConstant.CODE_500);
               Map<String, Object> errorMessage = new HashMap<>();
               errorMessage.put("code:", "ER013");
               errorMessage.put("params", "該当するユーザは存在していません。");
               errorResponse.put("message", errorMessage);
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
           }
       } catch (Exception e) {
           // Xử lý exception và trả về mã lỗi 500
           Map<String, Object> errorResponse = new HashMap<>();
           errorResponse.put("code", MessageConstant.CODE_500);
           Map<String, Object> errorMessage = new HashMap<>();
           errorMessage.put("code:", MessageConstant.CODE_ER001);
           errorMessage.put("params", "システムエラーが発生しました。");
           errorResponse.put("message", errorMessage);
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
       }
    }
    /**
     * Xóa nhân viên theo employeeId
     * @param employeeId ID của nhân viên cần xóa
     * @return ResponseEntity chứa ErrorResponse với mã lỗi và thông báo tương ứng
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ErrorResponse> deleteEmployee(@PathVariable Long employeeId, HttpServletRequest request) {
        try {
            // Kiểm tra xem id có hợp lệ hay không
            if (employeeId <= 0) {
                ErrorResponse response = new ErrorResponse();
                response.setCode(400);
                response.setEmployeeId(employeeId);
                Map<String, Object> message = new HashMap<>();
                message.put("code", MessageConstant.MSG004);
                message.put("params", new ArrayList<>());
                response.setMessage(message);
                return ResponseEntity.badRequest().body(response);
            }
            // Thực hiện xóa nhân viên
            boolean deleted = employeeService.deleteEmployeeById(employeeId);
            ErrorResponse response = new ErrorResponse();
            if (deleted) {
                // Xóa thành công, trả về mã lỗi 200 và thông báo thành công
                response.setCode(200);
                response.setEmployeeId(employeeId);
                Map<String, Object> message = new HashMap<>();
                message.put("code", MessageConstant.CODE_MSG003);
                message.put("params",MessageConstant.MSG003);
                response.setMessage(message);
                return ResponseEntity.ok(response);
            } else {
                // Xóa thất bại, trả về mã lỗi 500 và thông báo lỗi hệ thống
                response.setCode(500);
                response.setEmployeeId(employeeId);
                Map<String, Object> message = new HashMap<>();
                message.put("code", MessageConstant.CODE_ER015);
                message.put("params", MessageConstant.ER015);
                response.setMessage(message);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            // Bắt lỗi khi xảy ra exception, trả về mã lỗi 400 và thông báo lỗi request không hợp lệ
            ErrorResponse response = new ErrorResponse();
            response.setCode(400);
            response.setEmployeeId(employeeId);
            Map<String, Object> message = new HashMap<>();
            message.put("code", "リクエストが無効です。");
            message.put("params", new ArrayList<>());
            response.setMessage(message);
            return ResponseEntity.badRequest().body(response);
        }
    }
    /**
     * Cập nhật thông tin của nhân viên
     * @param employeeId ID của nhân viên cần sửa
     * @param updateEmployeeDTO DTO chứa thông tin cần cập nhật cho nhân viên
     * @return ResponseEntity chứa dữ liệu cập nhật của nhân viên
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long employeeId, @RequestBody UpdateEmployeeDTO updateEmployeeDTO) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employeeId, updateEmployeeDTO);
            // Trả về mã lỗi 200 khi cập nhật thành công
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("code", MessageConstant.CODE_200);
            successResponse.put("employeeId", updatedEmployee.getEmployeeId());
            Map<String, Object> message = new HashMap<>();
            message.put("code", MessageConstant.CODE_MSG002);
            message.put("params", MessageConstant.MSG002);
            successResponse.put("message", message);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            // Trường hợp xảy ra lỗi không xác định
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", MessageConstant.CODE_500);
            Map<String, Object> message = new HashMap<>();
            message.put("code", MessageConstant.CODE_ER015);
            message.put("params", MessageConstant.ER015);
            errorResponse.put("message", message);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}