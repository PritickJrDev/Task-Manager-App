package com.example.task_manager_app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_manager_app.databinding.TasksLayoutBinding;
import com.example.task_manager_app.model.Tasks;

import java.util.ArrayList;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {
    Context context;

    OnItemClickListener listener;
    ArrayList<Tasks> listOfTask;

    public void setListOfTask(ArrayList<Tasks> listOfTask) {
        this.listOfTask = listOfTask;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TasksLayoutBinding tasksLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.tasks_layout,parent,false);
        return new MyViewHolder(tasksLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tasksLayoutBinding.setTasks(listOfTask.get(position));
    }

    @Override
    public int getItemCount() {
        if(listOfTask == null) {
            return 0;
        }
        return listOfTask.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TasksLayoutBinding tasksLayoutBinding;

        public MyViewHolder(@NonNull TasksLayoutBinding tasksLayoutBinding) {
            super(tasksLayoutBinding.getRoot());
            this.tasksLayoutBinding = tasksLayoutBinding;

            tasksLayoutBinding.getRoot().findViewById(R.id.button_description).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Tag","hello helo helo helo");
                    Toast.makeText(itemView.getContext(), "Description button clicked : "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
                    if(listener != null){
                        listener.onItemDescriptionButtonClick();
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Tasks task);
        void onItemDescriptionButtonClick();
    }

    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
