/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeRepository.java, June 29/2023  hathang
 */
package com.luvina.la.repository;
import com.luvina.la.entity.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * EmployeeRepository: Giao diện để tương tác với thực thể Employee trong cơ sở dữ liệu.
 * Giao diện này cung cấp các hoạt động CRUD và các truy vấn tùy chỉnh liên quan đến thực thể Employee.
 * Author: hathang
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    // Phương thức truy vấn để tìm kiếm nhân viên dựa trên employeeLoginId
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);

    // Phương thức truy vấn để tìm kiếm nhân viên dựa trên employeeId
    Optional<Employee> findByEmployeeId(Long employeeId);

    /**
     * Phương thức truy vấn để lấy danh sách nhân viên với các thông tin mã và tên của chúng.
     * @param employeeName           Tên nhân viên hoặc một phần của tên cần tìm kiếm.
     * @param departmentId           ID của phòng ban để lọc danh sách nhân viên. (null nếu không muốn lọc theo phòng ban)
     * @param ordEmployeeName        Thứ tự sắp xếp theo tên nhân viên ("ASC" cho tăng dần, "DESC" cho giảm dần).
     * @param ordCertificationName   Thứ tự sắp xếp theo tên chứng chỉ của nhân viên ("ASC" cho tăng dần, "DESC" cho giảm dần).
     * @param ordEndDate             Thứ tự sắp xếp theo ngày kết thúc chứng chỉ của nhân viên ("ASC" cho tăng dần, "DESC" cho giảm dần).
     * @param offset                 Vị trí bắt đầu của kết quả trả về trong danh sách kết quả truy vấn.
     * @param limit                  Số lượng kết quả tối đa trả về từ truy vấn.
     * @return List<Object[]>: Danh sách dữ liệu nhân viên dưới dạng mảng Object.
     * Phương thức này truy xuất danh sách các nhân viên với các thông tin employeeId và employeeName của chúng.
     */

    @Query(nativeQuery = true, value = "SELECT e.employee_id as employee_id, e.employee_name as employee_name," +
            " e.employee_birth_date as employee_birthdate, e.employee_email as employee_email," +
            " e.employee_telephone as employee_telephone, d.department_name as department_name," +
            " sub.certification_name as certification_name, sub.end_date as end_date, sub.score as score FROM employees e" +
            " INNER JOIN departments d ON e.department_id = d.department_id" +
            " LEFT JOIN (" +
            "   SELECT ec.employee_id, c.certification_name, ec.end_date, ec.score" +
            "   FROM employees_certifications ec" +
            "   INNER JOIN certifications c ON ec.certification_id = c.certification_id" +
            "   WHERE ec.certification_id = (" +
            "       SELECT MIN(certification_id)" +
            "       FROM employees_certifications" +
            "       WHERE employee_id = ec.employee_id" +
            "   )" +
            " ) AS sub ON e.employee_id = sub.employee_id" +
            " WHERE (:departmentId IS NULL OR e.department_id = :departmentId)" +
            " AND (:employeeName IS NULL OR e.employee_name LIKE CONCAT('%', :employeeName, '%'))" +
            " ORDER BY" +
            "   CASE WHEN :ordEmployeeName = 'ASC' THEN e.employee_name END ASC," +
            "   CASE WHEN :ordEmployeeName = 'DESC' THEN e.employee_name END DESC," +
            "   CASE WHEN :ordCertificationName = 'ASC' THEN sub.certification_name END ASC," +
            "   CASE WHEN :ordCertificationName = 'DESC' THEN sub.certification_name END DESC," +
            "   CASE WHEN :ordEndDate = 'ASC' THEN sub.end_date END ASC," +
            "   CASE WHEN :ordEndDate = 'DESC' THEN sub.end_date END DESC" +
            " LIMIT :limit OFFSET :offset")
    List<Object[]> getListEmployee(
            @Param("employeeName") String employeeName,
            @Param("departmentId") Long departmentId,
            @Param("ordEmployeeName") String ordEmployeeName,
            @Param("ordCertificationName") String ordCertificationName,
            @Param("ordEndDate") String ordEndDate,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit
    );

    /**
     * Lấy tổng số nhân viên với các bộ lọc đã chỉ định.
     * @param employeeName          Tên của nhân viên để tìm kiếm (có thể là null).
     * @param departmentId          ID của phòng ban để lọc (có thể là null).
     * @return Số lượng tổng số nhân viên phù hợp với các bộ lọc đã chỉ định.
     * Phương thức này đếm tổng số nhân viên phù hợp với các bộ lọc đã chỉ định (employeeName và departmentId).
     */
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM employees e" +
            " WHERE (:departmentId IS NULL OR e.department_id = :departmentId)" +
            " AND (:employeeName IS NULL OR e.employee_name LIKE CONCAT('%', :employeeName, '%'))")
    int getTotalEmployees(
            @Param("employeeName") String employeeName,
            @Param("departmentId") Long departmentId
    );

    /**
     * Kiểm tra xem một nhân viên có tồn tại trong cơ sở dữ liệu hay không, dựa trên employeeLoginId được cung cấp.
     * @param employeeLoginId EmployeeLoginId của nhân viên để kiểm tra sự tồn tại.
     * @return boolean: true nếu có một nhân viên có employeeLoginId đã cung cấp tồn tại, false nếu không tồn tại.
     * Phương thức này kiểm tra xem một nhân viên có tồn tại trong cơ sở dữ liệu hay không, dựa trên employeeLoginId được cung cấp.
     */
    boolean existsByEmployeeLoginId(String employeeLoginId);
}