package com.example.task_manager_app.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.task_manager_app.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Tasks.class}, version = 1)
public abstract class TasksDatabase extends RoomDatabase {

    public abstract TasksDAO taskDAO();

    public static TasksDatabase instance;

    public static synchronized TasksDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TasksDatabase.class,
                    "tasks_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            initializeData();
        }
    };

    private static void initializeData(){
        TasksDAO tasksDAO = instance.taskDAO();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
              /*  Tasks tasks1 = new Tasks();
                tasks1.setTaskName("Task Name");
                tasks1.setDueDate("Due Date");
                tasks1.setTaskPriority("Priority");
                tasks1.setDescription("Task Description");
                tasks1.setStatus("task status");
                tasks1.setStatusImage(R.drawable.baseline_check_circle_outline_24);

                tasksDAO.insert(tasks1); */
            }
        });
    }
}
