package com.activeviam.creator.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activeviam.creator.model.TaskStatus;
import com.activeviam.creator.repository.TaskStatusRepository;
import com.activeviam.creator.service.TaskStatusService;

@Service
public class TaskStatusServiceImpl implements TaskStatusService{

    @Autowired
    private TaskStatusRepository taskStatusRepo;
    
	@Override
	public int save(TaskStatus task) {
		return taskStatusRepo.save(task).getId();
		}

	@Override
	public List<TaskStatus> findTaskStatusByLocalDate() {
		  LocalDate date = LocalDate.now();
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		  String text = date.format(formatter);
		  LocalDate localDate = LocalDate.parse(text, formatter);
		return taskStatusRepo.findTaskStatusByLocalDate(localDate);
	}

	@Override
	public TaskStatus findById(int id) {

		return taskStatusRepo.findById(id);
	}


}
