package com.HW.WEEK2.RestWeek.service;

import com.HW.WEEK2.RestWeek.dto.DepartmentDTO;
import com.HW.WEEK2.RestWeek.entities.DepartmentEntity;
import com.HW.WEEK2.RestWeek.exception.ResourceNotFound;
import com.HW.WEEK2.RestWeek.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity department = modelMapper.map(departmentDTO,DepartmentEntity.class);
        DepartmentEntity savedEntity = departmentRepository.save(department);
        return modelMapper.map(savedEntity,DepartmentDTO.class);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();
        return departmentEntities
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<DepartmentDTO> getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(departmentEntity -> modelMapper.map(departmentEntity,DepartmentDTO.class));
    }

    public boolean isExistById(Long id) {
        boolean exists = departmentRepository.existsById(id);
        if(!exists) throw new ResourceNotFound("Department not found with the ID: "+ id);
        return true;
    }

    public boolean deleteById(Long id) {
        isExistById(id);
        departmentRepository.deleteById(id);
        return true;
    }

    public DepartmentDTO update(Long id, DepartmentDTO departmentDTO) {
        isExistById(id);
        DepartmentEntity department = modelMapper.map(departmentDTO,DepartmentEntity.class);
        department.setId(id);
        DepartmentEntity departmentEntity = departmentRepository.save(department);
        return modelMapper.map(departmentEntity,DepartmentDTO.class);
    }

    public DepartmentDTO updatePartialDepartment(Long id, Map<String,Object> updates){
        isExistById(id);
        DepartmentEntity entity = departmentRepository.findById(id).orElse(null);
        updates.forEach((key,value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findRequiredField(DepartmentEntity.class,key);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,entity,value);
        });

        return modelMapper.map(departmentRepository.save(entity),DepartmentDTO.class);
    }
}
