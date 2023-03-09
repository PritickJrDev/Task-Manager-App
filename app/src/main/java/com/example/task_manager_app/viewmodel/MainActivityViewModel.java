package com.example.task_manager_app.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.task_manager_app.model.Tasks;
import com.example.task_manager_app.model.TasksRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private TasksRepository tasksRepository;


    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        this.tasksRepository = new TasksRepository(application);
    }

    public LiveData<List<Tasks>> getTasks(){
        return tasksRepository.getTasks();
    }

    public Tasks getTask(int taskId){
        return tasksRepository.getTask(taskId);
    }

    public void insertTask(Tasks task){
        tasksRepository.insertTask(task);
    }

    public void updateNewTask(Tasks task){
        tasksRepository.updateTask(task);
    }

    public void deleteTask(Tasks task){
        tasksRepository.deleteTask(task);
    }

    public void updateNewStatus(String newStatus, int newImage, int taskId){
        tasksRepository.updateNewStatus(newStatus,newImage,taskId);
    }
}
