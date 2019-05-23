package com.activeviam.creator.service;

import java.util.List;

import com.activeviam.creator.model.Task;
import com.activeviam.creator.model.TaskStatus;

public interface TaskService {

   int  save(Task task);

   List<Task> findTaskStatusByLocalDate();
   List<Task> findTaskStatusByLocalDateAndStatus(int status);

   Task findById(int id);
    
}
