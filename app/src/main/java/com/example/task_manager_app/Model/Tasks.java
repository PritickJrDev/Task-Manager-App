package com.example.task_manager_app.Model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class Tasks extends BaseObservable {

    @ColumnInfo (name = "task_id")
    @PrimaryKey (autoGenerate = true)
    private int taskId;

    @ColumnInfo (name = "task_name")
    private String taskName;

    @ColumnInfo (name ="task_priority")
    private String taskPriority;

    @ColumnInfo (name = "due_date")
    private String dueDate;

    @ColumnInfo (name = "desc")
    private String description;

    @Ignore
    public Tasks(){

    }

    public Tasks(int taskId, String taskName, String dueDate, String description, String taskPriority) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskPriority = taskPriority;
        this.dueDate = dueDate;
        this.description = description;
    }

    @Bindable
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
        notifyPropertyChanged(BR.taskId);
    }

    @Bindable
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
        notifyPropertyChanged(BR.taskName);
    }

    @Bindable
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
        notifyPropertyChanged(BR.dueDate);
    }

    @Bindable
    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
        notifyPropertyChanged(BR.taskPriority);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
}
