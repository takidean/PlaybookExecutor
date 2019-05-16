package com.activeviam.creator.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.activeviam.creator.model.Task;
import com.activeviam.creator.repository.TaskRepository;
import com.activeviam.creator.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskStatusRepo;
    
	@Override
	public int save(Task task) {
		return taskStatusRepo.save(task).getId();
		}

	@Override
	public List<Task> findTaskStatusByLocalDate() {
		  LocalDate date = LocalDate.now();
		  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		  String text = date.format(formatter);
		  System.out.println("local date =  "+text);
		  LocalDate localDate = LocalDate.parse(text, formatter);
		return taskStatusRepo.findTaskStatusByLocalDate(localDate);
	}

	@Override
	public Task findById(int id) {

		return taskStatusRepo.findById(id);
	}


}
