/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeServiceImpl.java, June 13/2023  hathang
 */
package com.luvina.la.service.impl;

import com.luvina.la.config.jwt.JwtTokenFilter;
import com.luvina.la.config.jwt.JwtTokenProvider;
import com.luvina.la.dto.AddEmployeeDTO;
import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.Employee_certificationDTO;
import com.luvina.la.dto.GetEmployeeDTO;
import com.luvina.la.dto.UpdateEmployeeDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeeCertification;
import com.luvina.la.payload.EmployeeValidationException;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.CertificationService;
import com.luvina.la.service.DepartmentService;
import com.luvina.la.service.EmployeeService;
import com.luvina.la.until.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * EmployeeServiceImpl: Lớp triển khai giao diện EmployeeService.
 * @author hathang
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository repo;
    @Autowired
    CertificationRepository certificationRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CertificationService certificationService;
    @Autowired
    PasswordEncoder passwordEncoder;
    /**
     * getAllEmployees: Lấy danh sách tất cả các nhân viên với các bộ lọc và thông số trang hiển thị đã chỉ định.
     * @param employeeName         Tên của nhân viên cần tìm kiếm.
     * @param departmentId         ID của phòng ban cần tìm kiếm.
     * @param ordEmployeeName      Tùy chọn sắp xếp theo tên nhân viên.
     * @param ordCertificationName Tùy chọn sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Tùy chọn sắp xếp theo ngày kết thúc chứng chỉ.
     * @param offset               Vị trí bắt đầu lấy dữ liệu (được sử dụng cho phân trang).
     * @param limit                Số lượng bản ghi cần lấy (được sử dụng cho phân trang).
     * @return List<EmployeeDTO>: Danh sách các đối tượng nhân viên được lựa chọn theo các bộ lọc và thông số trang hiển thị.
     */
    @Override
    public List<EmployeeDTO> getAllEmployees(String employeeName,
                                             Long departmentId,
                                             String ordEmployeeName,
                                             String ordCertificationName,
                                             String ordEndDate,
                                             Integer offset,
                                             Integer limit
    ) {
        // Lấy list<Object[]> từ phương thức getListEmployee() ở EmployeeRepository
        List<Object[]> employeeDataList = repo.getListEmployee(employeeName,
                departmentId,
                ordEmployeeName,
                ordCertificationName,
                ordEndDate,
                offset,
                limit
        );
        // Tạo List<EmployeeDTO>
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        // Duyệt mảng
        for (Object[] employeeData : employeeDataList) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployeeId((BigInteger) employeeData[0]);
            employeeDTO.setEmployeeName((String) employeeData[1]);
            employeeDTO.setEmployeeBirthDate((Date) employeeData[2]);
            employeeDTO.setEmployeeEmail((String) employeeData[3]);
            employeeDTO.setEmployeeTelephone((String) employeeData[4]);
            employeeDTO.setDepartmentName((String) employeeData[5]);
            employeeDTO.setCertificationName((String) employeeData[6]);
            employeeDTO.setEndDate((Date) employeeData[7]);
            employeeDTO.setScore((BigDecimal) employeeData[8]);
            // Add đối tượng vào mảng
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }
    /**
     * getTotalEmployees: Lấy tổng số nhân viên với các bộ lọc đã chỉ định.
     * @param employeeName Tên của nhân viên cần tìm kiếm.
     * @param departmentId ID của phòng ban cần tìm kiếm.
     * @return int: Tổng số nhân viên thỏa mãn các bộ lọc đã chỉ định.
     */
    @Override
    public int getTotalEmployees(String employeeName, Long departmentId) {
        return repo.getTotalEmployees(employeeName, departmentId);
    }
    /**
     * addEmployee: Thêm một nhân viên mới vào cơ sở dữ liệu.
     * @param addEmployeeDTO: Dữ liệu của nhân viên mới.
     * @return Long: ID của nhân viên vừa tạo.
     */
    @Override
    @Transactional
    public Long addEmployee(AddEmployeeDTO addEmployeeDTO) {
        // Kiểm tra xem employeeLoginId đã tồn tại trong cơ sở dữ liệu chưa
        if (repo.existsByEmployeeLoginId(addEmployeeDTO.getEmployeeLoginId())) {
            throw new EmployeeValidationException("ER001", MessageConstant.ER001, "employeeLoginId");
        }
        // Kiểm tra các trường hợp lỗi khác ở đây nếu cần
        if (!isValidEmployeeNameKana(addEmployeeDTO.getEmployeeNameKana())) {
            throw new EmployeeValidationException("ER003", MessageConstant.ER003, "employeeNameKana");
        }
        // Kiểm tra định dạng ngày sinh
        Date birthDate = parseDate(addEmployeeDTO.getEmployeeBirthDate());
        // Tiếp tục xử lý thêm nhân viên và lưu vào cơ sở dữ liệu
        Employee employee = new Employee();
        employee.setEmployeeName(addEmployeeDTO.getEmployeeName());
        employee.setEmployeeBirthDate(birthDate);
        employee.setEmployeeEmail(addEmployeeDTO.getEmployeeEmail());
        employee.setEmployeeTelephone(addEmployeeDTO.getEmployeeTelephone());
        employee.setEmployeeNameKana(addEmployeeDTO.getEmployeeNameKana());
        employee.setEmployeeLoginId(addEmployeeDTO.getEmployeeLoginId());
        String encodedPassword = passwordEncoder.encode(addEmployeeDTO.getEmployeeLoginPassword());
        employee.setEmployeeLoginPassword(encodedPassword);
        Department department = new Department();
        department.setDepartmentId(addEmployeeDTO.getDepartmentId());
        employee.setDepartmentId(department);
        // Lưu trữ đối tượng Employee vào cơ sở dữ liệu và nhận employeeId đã được tạo
        Employee savedEmployee = repo.save(employee);
        Long employeeId = savedEmployee.getEmployeeId();
        List<EmployeeCertification> employeeCertifications = new ArrayList<>();
        // Lưu dữ liệu mới certification của 1 employee mới
        for (Employee_certificationDTO certificationDTO : addEmployeeDTO.getCertifications()) {
            Certification certification = new Certification();
            certification.setCertificationID(certificationDTO.getCertificationId());
            EmployeeCertification employeeCertification = new EmployeeCertification();
            employeeCertification.setEmployeeId(new Employee());
            employeeCertification.getEmployeeId().setEmployeeId(employeeId);
            employeeCertification.setCertificationId(certification);
            employeeCertification.setStartDate(parseDate(certificationDTO.getCertificationStartDate()));
            employeeCertification.setEndDate(parseDate(certificationDTO.getCertificationEndDate()));
            employeeCertification.setScore(certificationDTO.getEmployeeCertificationScore());
            employeeCertifications.add(employeeCertification);
        }
        savedEmployee.setEmployeeCertifications(employeeCertifications);
        // lưu dữ liệu vào biến saveEmployee
        repo.save(savedEmployee);
        // Trả về employeeId vừa được tạo
        return employeeId;
    }

    /**
     * parseDate: Chuyển đổi chuỗi ngày tháng sang kiểu dữ liệu Date.
     * @param dateString: Chuỗi đại diện cho ngày tháng.
     * @return Date: Đối tượng Date đã được chuyển đổi từ chuỗi.
     * @throws EmployeeValidationException nếu định dạng ngày tháng không hợp lệ.
     */
    private Date parseDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new EmployeeValidationException("ER004", MessageConstant.ER005_DATE, "employeeBirthDate");
        }
    }

    /**
     * isValidEmployeeNameKana: Kiểm tra xem employeeNameKana chỉ chứa kí tự Katakana hay không.
     * @param employeeNameKana: Chuỗi chứa tên Katakana của nhân viên.
     * @return boolean: true nếu chuỗi chỉ chứa kí tự Katakana, false nếu không phải.
     */
    private boolean isValidEmployeeNameKana(String employeeNameKana) {
        return employeeNameKana.matches("[\\u30A0-\\u30FF\\s]+");
    }

    /**
     * isEmployeeLoginIdExist: Kiểm tra xem employeeLoginId đã tồn tại trong cơ sở dữ liệu hay chưa.
     * @param employeeLoginId: employeeLoginId cần kiểm tra.
     * @return boolean: true nếu employeeLoginId đã tồn tại, false nếu không.
     */
    public boolean isEmployeeLoginIdExist(String employeeLoginId) {
        boolean isExist = repo.existsByEmployeeLoginId(employeeLoginId);
        return isExist;
    }

    /**
     * Tìm kiếm thông tin của một nhân viên dựa vào employeeId.
     * @param employeeId Id của nhân viên cần tìm kiếm thông tin.
     * @return GetEmployeeDTO: Dữ liệu của nhân viên được tìm thấy hoặc null nếu không tồn tại employeeId.
     */
    public GetEmployeeDTO getEmployeeById(Long employeeId) {
        // Tìm nhân viên theo employeeId trong cơ sở dữ liệu
        Optional<Employee> optionalEmployee = repo.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            // Tạo đối tượng EmployeeInfoDTO để chứa thông tin nhân viên cần trả về
            GetEmployeeDTO employeeDTOInfo = new GetEmployeeDTO();
            // Gán thông tin từ đối tượng Employee vào EmployeeInfoDTO
            employeeDTOInfo.setEmployeeId(employee.getEmployeeId());
            employeeDTOInfo.setEmployeeName(employee.getEmployeeName());
            // Định dạng lại chuỗi ngày thành "yyyy/MM/dd"
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String formattedBirthDate = null;
            if (employee.getEmployeeBirthDate() != null) {
                Date birthDate = employee.getEmployeeBirthDate();
                formattedBirthDate = dateFormat.format(birthDate);
            }
            employeeDTOInfo.setEmployeeBirthDate(formattedBirthDate);
            employeeDTOInfo.setEmployeeEmail(employee.getEmployeeEmail());
            employeeDTOInfo.setEmployeeTelephone(employee.getEmployeeTelephone());
            employeeDTOInfo.setEmployeeNameKana(employee.getEmployeeNameKana());
            employeeDTOInfo.setEmployeeLoginId(employee.getEmployeeLoginId());
            // Lấy thông tin phòng ban từ đối tượng Department và gán vào GetEmployeeDTO
            Department department = employee.getDepartmentId();
            if (department != null) {
                employeeDTOInfo.setDepartmentId(department.getDepartmentId());
                employeeDTOInfo.setDepartmentName(department.getDepartmentName());
            }
            // Lấy thông tin chứng chỉ Certification và gán vào GetEmployeeDTO
            List<EmployeeCertification> employeeCertifications = employee.getEmployeeCertifications();
            List<Employee_certificationDTO> employeeCertificationDTOList = new ArrayList<>();
            // duệt mảng để lấy thông tin các chứng chỉ tiếng Nhật của employee đó
            for (EmployeeCertification employeeCertification : employeeCertifications) {
                Employee_certificationDTO employeeCertificationDTO = new Employee_certificationDTO();
                Certification certification = employeeCertification.getCertificationId();
                employeeCertificationDTO.setCertificationId(certification.getCertificationID());
                employeeCertificationDTO.setCertificationName(certification.getCertificationName());
                String formattedCertificationStartDate = null;
                if (employeeCertification.getStartDate() != null) {
                    Date startDate = employeeCertification.getStartDate();
                    formattedCertificationStartDate = dateFormat.format(startDate);
                }
                employeeCertificationDTO.setCertificationStartDate(formattedCertificationStartDate);
                String formattedCertificationEndDate = null;
                if (employeeCertification.getEndDate() != null) {
                    Date endDate = employeeCertification.getEndDate();
                    formattedCertificationEndDate = dateFormat.format(endDate);
                }
                employeeCertificationDTO.setCertificationEndDate(formattedCertificationEndDate);
                employeeCertificationDTO.setEmployeeCertificationScore(employeeCertification.getScore());
                employeeCertificationDTOList.add(employeeCertificationDTO);
            }
            employeeDTOInfo.setCertifications(employeeCertificationDTOList);
            return employeeDTOInfo;
        } else {
            return null;
        }
    }

    /**
     * Xóa nhân viên dựa vào employeeId.
     * @param employeeId ID của nhân viên cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy nhân viên với ID tương ứng
     */
    public boolean deleteEmployeeById(Long employeeId) {
        Optional<Employee> employeeOptional = repo.findById(employeeId);
        if (employeeOptional.isPresent()) {
            repo.deleteById(employeeId);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Phương thức cập nhật thông tin nhân viên
     * @param employeeId mã nhân viên cần cập nhật
     * @param updateEmployeeDTO DTo chưa thông tin cập nhật nhân viên
     * @return Thông tin nhân viên đã được cập nhật.
     * @throws Exception Nếu xảy ra lỗi trong quá trình cập nhật.
     */
    @Override
    public Employee updateEmployee(Long employeeId, UpdateEmployeeDTO updateEmployeeDTO) throws Exception {
        // Kiểm tra xem nhân viên có tồn tại trong cơ sở dữ liệu hay không
        Employee employee = repo.findById(employeeId).orElse(null);
        // Cập nhật thông tin của nhân viên từ DTO
        employee.setEmployeeName(updateEmployeeDTO.getEmployeeName());
        employee.setEmployeeNameKana(updateEmployeeDTO.getEmployeeNameKana());
        // Định dạng lại chuỗi ngày thành "yyyy/MM/dd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // Kiểm tra và cập nhật ngày sinh của nhân viên từ DTO
        if (updateEmployeeDTO.getEmployeeBirthDate() != null && !updateEmployeeDTO.getEmployeeBirthDate().isEmpty()) {
            Date birthDate = dateFormat.parse(updateEmployeeDTO.getEmployeeBirthDate());
            employee.setEmployeeBirthDate(birthDate);
        }
        employee.setEmployeeEmail(updateEmployeeDTO.getEmployeeEmail());
        employee.setEmployeeTelephone(updateEmployeeDTO.getEmployeeTelephone());
        employee.setEmployeeLoginId(updateEmployeeDTO.getEmployeeLoginId());
        // Kiểm tra password nếu không rỗng thì giữ nguyên còn thay đổi thì sẽ bị thay đổi
        String originalEmployeeLoginPass = employee.getEmployeeLoginPassword();
        if (updateEmployeeDTO.getEmployeeLoginPassword() != null
                && !updateEmployeeDTO.getEmployeeLoginPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(originalEmployeeLoginPass);
            updateEmployeeDTO.setEmployeeLoginPassword(encodedPassword);
            employee.setEmployeeLoginPassword(encodedPassword);
        } else {
            updateEmployeeDTO.setEmployeeLoginPassword(originalEmployeeLoginPass);
            employee.setEmployeeLoginPassword(originalEmployeeLoginPass);
        }
        // Cập nhật thông tin về departmentId
        Department department = new Department();
        department.setDepartmentId(updateEmployeeDTO.getDepartmentId());
        employee.setDepartmentId(department);
        // Xóa tất cả chứng chỉ tiếng Nhật cũ của nhân viên
        employee.getEmployeeCertifications().clear();
        // Xử lý và cập nhật dữ liệu chứng chỉ từ createEmployeeDTO
        List<Employee_certificationDTO> certificationsDTOData = updateEmployeeDTO.getCertifications();
        List<EmployeeCertification> employeeCertifications = new ArrayList<>();
        // cập nhật dữ liệu của các chứng chỉ tiếng Nhật của employee và thay đổi giá trị mới vào đó
        for (Employee_certificationDTO employeeCertificationDTO : certificationsDTOData) {
            Certification certification = new Certification();
            certification.setCertificationID(employeeCertificationDTO.getCertificationId());
            EmployeeCertification employeeCertification = new EmployeeCertification();
            employeeCertification.setEmployeeId(employee);
            employeeCertification.setCertificationId(certification);
            employeeCertification.setStartDate(parseDate(employeeCertificationDTO.getCertificationStartDate()));
            employeeCertification.setEndDate(parseDate(employeeCertificationDTO.getCertificationEndDate()));
            employeeCertification.setScore(employeeCertificationDTO.getEmployeeCertificationScore());
            employeeCertifications.add(employeeCertification);
        }
        // Cập nhật danh sách employeeCertifications cho employee
        employee.setEmployeeCertifications(employeeCertifications);
        // Lưu thông tin nhân viên đã cập nhật vào cơ sở dữ liệu
        return repo.save(employee);
    }

    /**
     * Hàm này thực hiện việc kiểm tra thông tin nhân viên được cung cấp trong đối tượng AddEmployeeDTO.
     * @param addEmployeeDTO Đối tượng chứa thông tin nhân viên.
     * @return Danh sách thông báo lỗi dưới dạng các bản đồ chứa mã lỗi và tên tham số.
     */
    public List<Map<String, Object>> validateEmployee(AddEmployeeDTO addEmployeeDTO) {
        // Validate createEmployeeDTO fields
        List<Map<String, Object>> errorMessages = new ArrayList<>();

        // Validate employeeLoginId
        String employeeLoginId = addEmployeeDTO.getEmployeeLoginId();
        if (employeeLoginId == null) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001, "アカウント名"));
        } else {
            if (employeeLoginId.length() > 50) {
                errorMessages.add(createErrorMessage(MessageConstant.ER006_EMPLOYEE_LOGIN_ID,
                        "アカウント名"));
            }
            if (!employeeLoginId.matches("^[a-zA-Z0-9_]*$") || Character.isDigit(employeeLoginId.charAt(0))) {
                errorMessages.add(createErrorMessage(MessageConstant.ER019,
                        "アカウント名"));
            }
            if (isEmployeeLoginIdExist(employeeLoginId)) {
                errorMessages.add(createErrorMessage(MessageConstant.ER003, "アカウント名"));
            }
        }
        // Validate employeeName
        String employeeName = addEmployeeDTO.getEmployeeName();
        if (employeeName == null || employeeName.isEmpty()) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001, "氏名"));
        } else if (employeeName.length() > 125) {
            errorMessages.add(createErrorMessage(MessageConstant.ER006_EMPLOYEE_NAME, "氏名"));
        }
        // Validate employeeNameKana
        String employeeNameKana = addEmployeeDTO.getEmployeeNameKana();
        if (employeeNameKana == null || employeeNameKana.isEmpty()) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001, "カタカナ氏名"));
        } else {
            if (employeeNameKana.length() > 125) {
                errorMessages.add(createErrorMessage(MessageConstant.ER006_EMPLOYEE_NAME,
                        "カタカナ氏名"));
            }
            if (!employeeNameKana.matches("^[\\u30A0-\\u30FF\\s]+$")) {
                errorMessages.add(createErrorMessage(MessageConstant.ER009,
                        "カタカナ氏名"));
            }
        }
        // Validate employeeBirthDate
        String employeeBirthDate = addEmployeeDTO.getEmployeeBirthDate();
        if (employeeBirthDate == null || employeeBirthDate.isEmpty()) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001, "生年月日"));
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            try {
                LocalDate birthDate = LocalDate.parse(employeeBirthDate, formatter);
                LocalDate currentDate = LocalDate.now();
                // Kiểm tra nếu ngày tháng trong quá khứ
                if (birthDate.isAfter(currentDate)) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER011, "生年月日"));
                }
            } catch (DateTimeParseException e) {
                errorMessages.add(createErrorMessage(MessageConstant.ER005_DATE, "生年月日"));
            }
        }
        // Validate employeeEmail
        String employeeEmail = addEmployeeDTO.getEmployeeEmail();
        if (employeeEmail == null || employeeEmail.isEmpty()) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001,
                    "メールアドレス"));
        } else if (employeeEmail.length() > 125) {
            errorMessages.add(createErrorMessage(MessageConstant.ER006_EMPLOYEE_EMAIL,
                    "メールアドレス"));
        } else if (!employeeEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$")) {
            errorMessages.add(createErrorMessage(MessageConstant.ER005_EMAIL, "メールアドレス"));
        }
        // Validate employeeTelephone
        String employeeTelephone = addEmployeeDTO.getEmployeeTelephone();
        if (employeeTelephone == null || employeeTelephone.isEmpty()) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001, "電話番号"));
        } else if (employeeTelephone.length() > 50) {
            errorMessages.add(createErrorMessage(MessageConstant.ER006_EMPLOYEE_TELEPHONE,
                    "電話番号"));
        } else if (!employeeTelephone.matches("^[0-9]+$")) {
            errorMessages.add(createErrorMessage(MessageConstant.ER008_TELEPHONE,
                    "電話番号"));
        }
        // Validate employeeLoginPassword
        String employeeLoginPassword = addEmployeeDTO.getEmployeeLoginPassword();
        if (employeeLoginPassword == null || employeeLoginPassword.isEmpty()) {
            errorMessages.add(createErrorMessage(MessageConstant.ER001, "パスワード"));
        } else if (employeeLoginPassword.length() < 8 || employeeLoginPassword.length() > 50) {
            errorMessages.add(createErrorMessage(MessageConstant.ER007_LOGIN_PASSWORD,
                    "パスワード"));
        }
        // Validate departmentId
        Long departmentId = addEmployeeDTO.getDepartmentId();
        if (departmentId == null) {
            errorMessages.add(createErrorMessage(MessageConstant.ER002, "グループ"));
        } else {
            if (departmentId <= 0) {
                errorMessages.add(createErrorMessage(MessageConstant.ER018,
                        "グループ"));
            } else {
                // kiểm tra tồn tại departmentId trong departments.departmentId
                if (!departmentService.isDepartmentIdExist(departmentId)) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER004,
                            "グループ"));
                }
            }
        }
        // Validate certifications
        List<Employee_certificationDTO> certifications = addEmployeeDTO.getCertifications();
        if (certifications != null && !certifications.isEmpty()) {
            // duyệt vòng for để duyệt mảng các chứng chỉ tiếng Nhật để validate
            for (Employee_certificationDTO certificationDTO : certifications) {
                // Validate startDate
                String startDate = certificationDTO.getCertificationStartDate();
                if (startDate == null || startDate.isEmpty()) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER001, "資格交付日"));
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        sdf.setLenient(false);
                        sdf.parse(startDate);
                    } catch (ParseException e) {
                        errorMessages.add(createErrorMessage(MessageConstant.ER005_DATE,
                                "資格交付日"));
                    }
                }
                // Validate endDate
                String endDate = certificationDTO.getCertificationEndDate();
                if (endDate == null || endDate.isEmpty()) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER001, "失効日"));
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        sdf.setLenient(false);
                        sdf.parse(endDate);
                    } catch (ParseException e) {
                        errorMessages.add(createErrorMessage(MessageConstant.ER005_DATE,
                                "失効日"));
                    }
                }
                // Kiểm tra startDate và endDate
                if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        Date start = sdf.parse(startDate);
                        Date end = sdf.parse(endDate);
                        if (end.before(start)) {
                            errorMessages.add(createErrorMessage(
                                    MessageConstant.ER012, "失効日"));
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
                // Validate score
                BigDecimal score = certificationDTO.getEmployeeCertificationScore();
                if (score == null) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER001, "点数"));
                } else if (score.compareTo(BigDecimal.ZERO) <= 0) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER018,
                            "点数"));
                }
                // Validate certificationId
                Long certificationId = certificationDTO.getCertificationId();
                if (certificationId == null) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER001, "資格"));
                } else if (certificationId <= 0) {
                    errorMessages.add(createErrorMessage(MessageConstant.ER018,
                            "資格"));
                } else {
                    // Kiểm tra tồn tại certificationId trong certifications.certification_id
                    if (!certificationService.isCertificationIdExist(certificationId)) {
                        errorMessages.add(createErrorMessage(MessageConstant.ER004,
                                "資格"));
                    }
                }
            }
        }
        return errorMessages;
    }

    /**
     * Tạo một thông báo lỗi dựa trên mã lỗi và tên tham số cung cấp.
     * @param errorCode     Mã lỗi.
     * @param parameterName Tên tham số liên quan đến lỗi.
     * @return Một bản đồ chứa thông tin lỗi.
     */
    private Map<String, Object> createErrorMessage(String errorCode, String parameterName) {
        Map<String, Object> Message = new HashMap<>();
        Message.put("code", errorCode);
        Message.put("params", parameterName);
        return Message;
    }
}
