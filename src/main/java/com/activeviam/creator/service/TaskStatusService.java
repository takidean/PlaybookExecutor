package com.activeviam.creator.service;

import java.util.List;

import com.activeviam.creator.model.TaskStatus;

public interface TaskStatusService {

   int  save(TaskStatus task);

   List<TaskStatus> findTaskStatusByLocalDate();

   TaskStatus findById(int id);
   List<TaskStatus> findTaskStatusByLocalDateAndStatus(int status);
}
