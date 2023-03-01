package com.example.task_manager_app.Model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TasksRepository {
    private TasksDAO tasksDAO;
    private LiveData<List<Tasks>>  tasks;

    public TasksRepository(Application application){
        TasksDatabase tasksDatabase = TasksDatabase.getInstance(application);
        tasksDAO = tasksDatabase.taskDAO();
    }

    public LiveData<List<Tasks>> getTasks(){
        return tasksDAO.getTasks();
    }

    public Tasks getTask(int taskId){
        return tasksDAO.getTask(taskId);
    }

    public void insertTask(Tasks tasks){
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                tasksDAO.insert(tasks);
            }
        });
    }

    public void updateTask(Tasks tasks){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tasksDAO.update(tasks);
            }
        });
    }

    public void deleteTask(Tasks task){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                tasksDAO.delete(task);
            }
        });
    }
}
