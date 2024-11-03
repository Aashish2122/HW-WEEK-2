package com.HW.WEEK2.RestWeek.controllers;

import com.HW.WEEK2.RestWeek.dto.DepartmentDTO;
import com.HW.WEEK2.RestWeek.exception.ResourceNotFound;
import com.HW.WEEK2.RestWeek.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path="/departments")
public class DepartmentController {

private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO inputDep){
        DepartmentDTO response = departmentService.saveDepartment(inputDep);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(){
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    @GetMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long departmentId) {
        return ResponseEntity.ok(departmentService.getDepartmentById(departmentId)
                .orElseThrow(() -> new ResourceNotFound("Department not found")));
    }

    @DeleteMapping(path = "/{departmentId}")
    public ResponseEntity<Boolean> deleteDepartmentById(@PathVariable Long departmentId) {
        boolean gotDeleted = departmentService.deleteById(departmentId);
        if(gotDeleted) return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateByID(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(departmentService.update(departmentId,departmentDTO));
    }
    @PatchMapping(path = "/{departmentId}")
    public ResponseEntity<DepartmentDTO> partialUpdate(@PathVariable Long departmentId, @RequestBody Map<String, Object> updates) {
        DepartmentDTO departmentDTO = departmentService.updatePartialDepartment(departmentId,updates);
        if(departmentDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(departmentDTO);
    }
}
