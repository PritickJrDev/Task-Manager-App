package com.example.task_manager_app.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TasksDAO {

    @Insert
    public void insert(Tasks tasks);

    @Update
    public void update(Tasks tasks);

    @Delete
    public void delete(Tasks tasks);

    @Query("select * from tasks_table")
    public LiveData<List<Tasks>> getTasks();

    @Query("select * from tasks_table where task_id==:taskId")
    public Tasks getTask(int taskId);

    @Query("UPDATE tasks_table SET task_status=:newStatus, status_image=:newImage WHERE task_id = :taskId")
    public void updateStatus(String newStatus, int newImage, int taskId);

}
