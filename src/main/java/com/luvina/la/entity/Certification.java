/**
 * Copyright(C) 2023 Luvina Software Company
 * Certification.java, June 13/2023  hathang
 */
package com.luvina.la.entity;
import java.io.Serializable;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Cấu hình entity với table certifications của database
 * @author hathang
 */
@Entity
@Table(name = "certifications")
@Data
public class Certification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id", unique = true, columnDefinition = "BIGINT", nullable = false)
    private Long certificationID;
    @Column(name = "certification_name", columnDefinition = "VARCHAR(50)", nullable = false)
    private String certificationName;
    @Column(name = "certification_level", columnDefinition = "INT", nullable = false)
    private Integer certificationLevel;
}