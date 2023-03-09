package com.example.task_manager_app;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.task_manager_app.model.Tasks;

import java.util.ArrayList;

public class TaskDiffCallback extends DiffUtil.Callback {
    ArrayList<Tasks> oldTaskList;
    ArrayList<Tasks> newTaskList;

    public TaskDiffCallback(ArrayList<Tasks> oldTaskList, ArrayList<Tasks> newTaskList) {
        this.oldTaskList = oldTaskList;
        this.newTaskList = newTaskList;
    }

    @Override
    public int getOldListSize() {
        return oldTaskList == null ? 0 : oldTaskList.size();
    }

    @Override
    public int getNewListSize() {
        return newTaskList == null ? 0 : newTaskList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTaskList.get(oldItemPosition).getTaskId() ==
                newTaskList.get(newItemPosition).getTaskId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldTaskList.get(oldItemPosition).equals
                (newTaskList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}