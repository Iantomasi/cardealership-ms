package com.cardealership.employeeservice.datamapperlayer;

import com.cardealership.employeeservice.datalayer.Employee;
import com.cardealership.employeeservice.presentationlayer.EmployeeResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeResponseMapper {
    @Mapping(expression = "java(employee.getEmployeeIdentifier().getEmployeeId())",  target = "employeeId")
    @Mapping(expression = "java(employee.getAddress().getStreetAddress())", target = "streetAddress" )
    @Mapping(expression = "java(employee.getAddress().getCity())", target = "city" )
    @Mapping(expression = "java(employee.getAddress().getProvince())", target = "province" )
    @Mapping(expression = "java(employee.getAddress().getCountry())", target = "country" )
    @Mapping(expression = "java(employee.getAddress().getPostalCode())", target = "postalCode" )
    EmployeeResponseModel entityToResponseModel(Employee employee);

    List<EmployeeResponseModel> entityListToResponseModelList(List<Employee> employees);

}
