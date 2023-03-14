package com.example.task_manager_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_manager_app.databinding.TasksLayoutBinding;
import com.example.task_manager_app.model.Tasks;

import java.util.ArrayList;

public class TaskCompletionAdapter extends RecyclerView.Adapter<TaskCompletionAdapter.TaskCompletionViewHolder> {
    private Context context;
    private ArrayList<Tasks> listOfTask;
    private int pos;

    public void setListOfTask(ArrayList<Tasks> newTask) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new TaskDiffCallback(listOfTask, newTask), false);

                listOfTask =  newTask;

        result.dispatchUpdatesTo(TaskCompletionAdapter.this);
    }

    @NonNull
    @Override
    public TaskCompletionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.task_completion_layout,parent,false);
        return new TaskCompletionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskCompletionViewHolder holder, int position) {
        if(listOfTask.get(position).getStatus().equals("completed")){
            holder.textView.setText(listOfTask.get(position).getTaskName()+"task completed");
        } else if(listOfTask.get(position).getStatus().equals("pending")){
            holder.textView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(listOfTask == null) {
            return 0;
        }
        return listOfTask.size();
//        for(int i=0; i<listOfTask.size(); i++){
//            if(listOfTask.get(i).getStatus().equals("completed")){
//                return listOfTask.size();
//            }
//        }
//        return 0;
    }


    public class TaskCompletionViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public TaskCompletionViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.task_completed);
        }
    }
}
