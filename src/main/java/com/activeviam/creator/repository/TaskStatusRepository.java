package com.activeviam.creator.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.activeviam.creator.model.Developper;
import com.activeviam.creator.model.TaskStatus;


public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long>{

	public List<TaskStatus> findTaskStatusByLocalDate(LocalDate localDate);
	
	TaskStatus findById(int id);
}
