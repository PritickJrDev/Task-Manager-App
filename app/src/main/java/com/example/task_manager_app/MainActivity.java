package com.example.task_manager_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_manager_app.databinding.ActivityMainBinding;
import com.example.task_manager_app.databinding.TasksLayoutBinding;
import com.example.task_manager_app.model.Tasks;
import com.example.task_manager_app.viewmodel.MainActivityViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Tasks> listOfTasks;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    TasksAdapter tasksAdapter;
    TaskCompletionAdapter taskCompletionAdapter;

    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding activityMainBinding;
    MainActivityClickHandler clickHandler;

    public int selectedTaskId;
    public int selectedStatusImage;
    public String selectedTaskStatus;

    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final int EDIT_TASK_REQUEST_CODE = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new MainActivityClickHandler();
        activityMainBinding.setClickHandler(clickHandler);


        mainActivityViewModel.getTasks().observe(this, new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {

                listOfTasks = (ArrayList<Tasks>) tasks;

                loadRecyclerAndAdapter();
                loadTaskCompletionRecycleView();
                loadListener();

            }
        });
    }

    public void loadRecyclerAndAdapter(){
        recyclerView = activityMainBinding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TasksAdapter(this,this);
        recyclerView.setAdapter(tasksAdapter);
        tasksAdapter.setListOfTask(listOfTasks);

        TextView textView = findViewById(R.id.sample_textView);
        if(listOfTasks.isEmpty()){
            textView.setText("Start Adding Task");
        } else {
            textView.setText("");
        }
    }

    public void loadTaskCompletionRecycleView(){
        recyclerView2 = activityMainBinding.recyclerView2;
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        taskCompletionAdapter = new TaskCompletionAdapter(this,this);
        recyclerView2.setAdapter(taskCompletionAdapter);
        taskCompletionAdapter.setListOfTask(listOfTasks);

        TextView textView = findViewById(R.id.divider);
        for(int i=0; i<listOfTasks.size(); i++){
            if(listOfTasks.get(i).getStatus().equals("completed")){
                textView.setText("Completed Task");
            } else {
                textView.setText("");
            }
        }


    }

    public void loadListener(){
        tasksAdapter.setListener(new TasksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tasks task) {
                selectedTaskId = task.getTaskId();
                selectedTaskStatus = task.getStatus();
                selectedStatusImage = task.getStatusImage();

                //inflating the existing task data in addEdit activity view
                Intent i = new Intent(MainActivity.this, AddAndEdit.class);
                i.putExtra(AddAndEdit.TASK_ID, selectedTaskId);
                i.putExtra(AddAndEdit.TASK_NAME, task.getTaskName());
                i.putExtra(AddAndEdit.DESCRIPTION, task.getDescription());
                i.putExtra(AddAndEdit.PRIORITY, task.getTaskPriority());
                i.putExtra(AddAndEdit.DUE_DATE, task.getDueDate());
                startActivityForResult(i,EDIT_TASK_REQUEST_CODE);
            }

            @Override
            public void onItemDescriptionButtonClick() {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.description_layout,null);
                TextView textView = view.findViewById(R.id.newDescription_textView);
                textView.setText(tasksAdapter.getMyDescription());

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(view);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                }).create().show();
            }
        });

        //swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Tasks tasks = listOfTasks.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteTask(tasks);
            }
        }).attachToRecyclerView(recyclerView);
    }

    public class MainActivityClickHandler {
        public void onClickFloatingButton(View view){
            Intent i = new Intent(MainActivity.this, AddAndEdit.class);
            startActivityForResult(i,ADD_TASK_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_TASK_REQUEST_CODE && resultCode == RESULT_OK){
            Tasks tasks = new Tasks();
            tasks.setTaskName(data.getStringExtra(AddAndEdit.TASK_NAME));
            tasks.setDescription(data.getStringExtra(AddAndEdit.DESCRIPTION));
            tasks.setTaskPriority(data.getStringExtra(AddAndEdit.PRIORITY));
            tasks.setDueDate(data.getStringExtra(AddAndEdit.DUE_DATE));
            tasks.setStatus("pending");
            tasks.setStatusImage(R.drawable.baseline_check_circle_outline_24);
            mainActivityViewModel.insertTask(tasks);
        } else if(requestCode == EDIT_TASK_REQUEST_CODE && resultCode == RESULT_OK){
            Tasks tasks = new Tasks();
            tasks.setTaskId(selectedTaskId);
            tasks.setTaskName(data.getStringExtra(AddAndEdit.TASK_NAME));
            tasks.setDescription(data.getStringExtra(AddAndEdit.DESCRIPTION));
            tasks.setTaskPriority(data.getStringExtra(AddAndEdit.PRIORITY));
            tasks.setDueDate(data.getStringExtra(AddAndEdit.DUE_DATE));
            tasks.setStatus(selectedTaskStatus);
            tasks.setStatusImage(selectedStatusImage);
            mainActivityViewModel.updateNewTask(tasks);
        }
    }
}