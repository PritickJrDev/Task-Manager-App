package com.example.task_manager_app;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_manager_app.databinding.TasksLayoutBinding;
import com.example.task_manager_app.model.Tasks;
import com.example.task_manager_app.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class TaskCompletionAdapter extends RecyclerView.Adapter<TaskCompletionAdapter.TaskCompletionViewHolder> {
    private Context context;

    private ArrayList<Tasks> listOfTask;
    private int pos;
    MainActivityViewModel mainActivityViewModel;


    public TaskCompletionAdapter(Context context, MainActivity mainActivity){
        this.context = context;
        mainActivityViewModel = new ViewModelProvider(mainActivity).get(MainActivityViewModel.class);
        setHasStableIds(true);
    }

    public void setListOfTask(ArrayList<Tasks> newTask) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new TaskDiffCallback(listOfTask, newTask), false);

                listOfTask =  newTask;

        result.dispatchUpdatesTo(TaskCompletionAdapter.this);
    }

    @NonNull
    @Override
    public TaskCompletionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.task_completion_layout,parent,false);
//        return new TaskCompletionViewHolder(view);

        TasksLayoutBinding tasksLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.tasks_layout,parent,false);
        return new TaskCompletionViewHolder(tasksLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskCompletionViewHolder holder, int position) {
        if(listOfTask.get(position).getStatus().equals("completed")){
            holder.tasksLayoutBinding.setTasks(listOfTask.get(position));
            holder.textView.setText(listOfTask.get(position).getStatus());
            holder.statusButton.setBackgroundResource(listOfTask.get(position).getStatusImage());
        }
        else if(listOfTask.get(position).getStatus().equals("pending")){
            holder.tasksLayoutBinding.getRoot().findViewById(R.id.textView_taskName).setVisibility(View.GONE);
            holder.tasksLayoutBinding.getRoot().findViewById(R.id.textView_dueDate).setVisibility(View.GONE);
            holder.tasksLayoutBinding.getRoot().findViewById(R.id.textView_priority).setVisibility(View.GONE);
            holder.tasksLayoutBinding.getRoot().findViewById(R.id.button_description).setVisibility(View.GONE);
            holder.tasksLayoutBinding.getRoot().findViewById(R.id.text_status).setVisibility(View.GONE);
            holder.tasksLayoutBinding.getRoot().findViewById(R.id.button_status).setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(listOfTask == null) {
            return 0;
        }
        return listOfTask.size();
    }


    public class TaskCompletionViewHolder extends RecyclerView.ViewHolder {
        TasksLayoutBinding tasksLayoutBinding;
        Button statusButton;
        TextView textView;
        public TaskCompletionViewHolder(@NonNull TasksLayoutBinding tasksLayoutBinding) {
            super(tasksLayoutBinding.getRoot());
            this.tasksLayoutBinding = tasksLayoutBinding;
            statusButton = tasksLayoutBinding.getRoot().findViewById(R.id.button_status);
            textView = tasksLayoutBinding.getRoot().findViewById(R.id.text_status);

            statusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Tasks tasks = listOfTask.get(position);

                    if(tasks.getStatus().equals("pending")){
                        tasks.setStatus("completed");

                        tasks.setStatusImage(R.drawable.baseline_check_circle_filled_24);
                        Toast.makeText(context, "Hurray! Task Completed", Toast.LENGTH_SHORT).show();
                        mainActivityViewModel.updateNewStatus(tasks.getStatus(),tasks.getStatusImage(), tasks.getTaskId());
                    } else{
                        tasks.setStatus("pending");
                        tasks.setStatusImage(R.drawable.baseline_check_circle_outline_24);
                        mainActivityViewModel.updateNewStatus(tasks.getStatus(),tasks.getStatusImage(), tasks.getTaskId());
                    }
                }
            });

            tasksLayoutBinding.getRoot().findViewById(R.id.button_description).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Uncheck to see description", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
