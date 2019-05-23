package com.activeviam.creator.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.activeviam.creator.model.Task;


public interface TaskRepository extends JpaRepository<Task, Long>{

	public List<Task> findTaskStatusByLocalDate(LocalDate localDate);
	public List<Task> findTaskStatusByLocalDateAndStatus(LocalDate localDate,int status);

	Task findById(int id);
}
