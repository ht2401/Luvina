package com.luvina.la.mapper;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * use:
 *  EmployeeMapper.MAPPER.toEntity(dto);
 *  EmployeeMapper.MAPPER.toList(list);
 */
@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper( EmployeeMapper.class );
    @Mapping(source = "department.departmentName", target = "departmentName")
    @Mapping(source = "certification.certificationName", target = "certificationName")
    @Mapping(source = "employeeCertification.endDate", target = "endDate")
    @Mapping(source = "employeeCertification.score", target = "score")
    Employee toEntity(EmployeeDTO entity);
    Employee toDto(EmployeeDTO entity);
    Iterable<EmployeeDTO> toList(Iterable<Employee> list);

}
