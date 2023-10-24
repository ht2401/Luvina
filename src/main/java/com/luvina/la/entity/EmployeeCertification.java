/**
 * Copyright(C) 2023 Luvina Software Company
 * EmployeeCertification.java, June 13/2023  hathang
 */
package com.luvina.la.entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
/**
 * Cấu hình entity với table employees_certifications của database
 * @author hathang
 */
@Entity
@Table(name = "employees_certifications")
@Data
public class EmployeeCertification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_certification_id", unique = true, columnDefinition = "BIGINT", nullable = false)
    private Long employeeCertificationId;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", columnDefinition = "BIGINT", nullable = false)
    private Employee employeeId;
    @ManyToOne
    @JoinColumn(name = "certification_id", columnDefinition = "BIGINT", nullable = false)
    private Certification certificationId;
    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    private Date startDate;
    @Column(name = "end_date", columnDefinition = "DATE", nullable = false)
    private Date endDate;
    @Column(name = "score", columnDefinition = "DECIMAL", nullable = false)
    private BigDecimal score;
}