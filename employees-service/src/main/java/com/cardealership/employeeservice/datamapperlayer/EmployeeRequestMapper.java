package com.cardealership.employeeservice.datamapperlayer;

import com.cardealership.employeeservice.datalayer.Employee;
import com.cardealership.employeeservice.presentationlayer.EmployeeRequestModel;
import org.mapstruct.Mapping;

public interface EmployeeRequestMapper {
    @Mapping(target = "employeeIdentifier", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    Employee requestModelToEntity(EmployeeRequestModel employeeRequestModel);
}
