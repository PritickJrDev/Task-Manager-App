package com.example.task_manager_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;

import com.example.task_manager_app.databinding.TasksLayoutBinding;
import com.example.task_manager_app.model.Tasks;
import com.example.task_manager_app.viewmodel.MainActivityViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder> {
    Context context;
    String myDescription;
    OnItemClickListener listener;
    ArrayList<Tasks> listOfTask;
    MainActivityViewModel mainActivityViewModel;

    int fromPosition;
    int targetPosition;

    public TasksAdapter(Context context, MainActivity mainActivity){
        this.context = context;
        mainActivityViewModel = new ViewModelProvider(mainActivity).get(MainActivityViewModel.class);
        setHasStableIds(true);
    }

    public void setListOfTask(ArrayList<Tasks> newTask) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new TaskDiffCallback(listOfTask, newTask), false);
        listOfTask = newTask;
        result.dispatchUpdatesTo(TasksAdapter.this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TasksLayoutBinding tasksLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.tasks_layout,parent,false);
        return new MyViewHolder(tasksLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         fromPosition = holder.getAdapterPosition();
         targetPosition = listOfTask.indexOf(listOfTask.get(listOfTask.size() - 1));

            holder.tasksLayoutBinding.setTasks(listOfTask.get(position));
            holder.textView.setText(listOfTask.get(position).getStatus());
            holder.statusButton.setBackgroundResource(listOfTask.get(position).getStatusImage());

//            if(listOfTask.get(position).getStatus().equals("completed") && listOfTask != null){
//
//                holder.tasksLayoutBinding.getRoot().setVisibility(View.GONE);
//             }
    }

    @Override
    public int getItemCount() {
        if(listOfTask == null) {
            return 0;
        }
        return listOfTask.size();
    }

    @Override
    public long getItemId(int position) {
        Tasks tasks = listOfTask.get(position);
        return tasks.getTaskId();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TasksLayoutBinding tasksLayoutBinding;
        Button statusButton;
        TextView textView;

        public MyViewHolder(@NonNull TasksLayoutBinding tasksLayoutBinding) {
            super(tasksLayoutBinding.getRoot());
            this.tasksLayoutBinding = tasksLayoutBinding;

            statusButton = tasksLayoutBinding.getRoot().findViewById(R.id.button_status);
            textView = tasksLayoutBinding.getRoot().findViewById(R.id.text_status);

            statusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Tasks tasks = listOfTask.get(position);

                    final MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.complete_chime);

                    if(tasks.getStatus().equals("pending")){
                        tasks.setStatus("completed");
                        mediaPlayer.start();
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


            //for clicking button description
            tasksLayoutBinding.getRoot().findViewById(R.id.button_description).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDescription = listOfTask.get(getAdapterPosition()).getDescription();
                    if(listener != null){
                        listener.onItemDescriptionButtonClick();
                    }
                }
            });

            //for clicking the adapter position to edit
            tasksLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    if(listener != null && clickedPosition != RecyclerView.NO_POSITION){
                        listener.onItemClick(listOfTask.get(clickedPosition));
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

    public String getMyDescription(){
        return myDescription;
    }
}
