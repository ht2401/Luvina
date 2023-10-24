/**
 * Copyright(C) 2023 Luvina Software Company
 * Department.java, June 13/2023  hathang
 */
package com.luvina.la.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
/**
 * Cấu hình entity với table departments của database
 * @author hathang
 */
@Entity
@Table(name = "departments")
@Data
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", unique = true, columnDefinition = "BIGINT", nullable = false)
    private Long departmentId;
    @Column(name = "department_name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String departmentName;
}