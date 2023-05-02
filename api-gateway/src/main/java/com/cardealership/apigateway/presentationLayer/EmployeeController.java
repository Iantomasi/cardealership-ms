package com.cardealership.apigateway.presentationLayer;

import com.cardealership.apigateway.businessLayer.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(
            value = "/{employeeId}",
            produces = "application/json"
    )
    ResponseEntity<EmployeeResponseModel> getEmployeeAggregate(@PathVariable String employeeId){
        log.debug("1, Received in api-gateway employees controller getEmployeeAggregate with clientId: " + employeeId);
        return ResponseEntity.ok().body(employeeService.getEmployeeAggregate(employeeId));
    }
}
