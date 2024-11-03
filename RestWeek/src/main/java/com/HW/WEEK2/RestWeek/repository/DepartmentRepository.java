package com.HW.WEEK2.RestWeek.repository;

import com.HW.WEEK2.RestWeek.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {

}
