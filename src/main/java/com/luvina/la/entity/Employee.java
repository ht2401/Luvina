/**
 * Copyright(C) 2023 Luvina Software Company
 * Employee.java, June 13/2023  hathang
 */
package com.luvina.la.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * Cấu hình entity với table employees của database
 * @author hathang
 */
@Entity
@Table(name = "employees")
@Data
public class Employee implements Serializable {
    private static final long serialVersionUID = 5771173953267484096L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", unique = true, columnDefinition = "BIGINT", nullable = false)
    private Long employeeId;
    @ManyToOne
    @JoinColumn(name = "department_id", columnDefinition = "BIGINT", nullable = false)
    private Department departmentId;
    @OneToMany(mappedBy = "employeeId", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private List<EmployeeCertification> employeeCertifications;
    @Column(name = "employee_name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String employeeName;
    @Column(name = "employee_name_kana", columnDefinition = "VARCHAR(255)")
    private String employeeNameKana;
    @Column(name = "employee_birth_date", columnDefinition = "DATE")
    private Date employeeBirthDate;
    @Column(name = "employee_email", columnDefinition = "VARCHAR(255)", nullable = false)
    private String employeeEmail;
    @Column(name = "employee_telephone", columnDefinition = "VARCHAR(50)")
    private String employeeTelephone;
    @Column(name = "employee_login_id", columnDefinition = "VARCHAR(50)", nullable = false)
    private String employeeLoginId;
    @Column(name = "employee_login_password", columnDefinition = "VARCHAR(100)")
    private String employeeLoginPassword;
}