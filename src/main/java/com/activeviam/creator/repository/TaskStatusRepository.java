package com.activeviam.creator.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.activeviam.creator.model.TaskStatus;


public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long>{

	public List<TaskStatus> findTaskStatusByLocalDate(LocalDate localDate);
	public List<TaskStatus> findTaskStatusByLocalDateAndStatus(LocalDate localDate,int status);

	TaskStatus findById(int id);
}
