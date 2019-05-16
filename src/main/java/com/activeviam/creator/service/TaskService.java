package com.activeviam.creator.service;

import java.util.List;

import com.activeviam.creator.model.Task;

public interface TaskService {

   int  save(Task task);

   List<Task> findTaskStatusByLocalDate();

   Task findById(int id);
    
}
