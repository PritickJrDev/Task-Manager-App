package com.example.task_manager_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_manager_app.databinding.ActivityMainBinding;
import com.example.task_manager_app.databinding.DescriptionLayoutBinding;
import com.example.task_manager_app.databinding.TasksLayoutBinding;
import com.example.task_manager_app.model.Tasks;
import com.example.task_manager_app.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Tasks> listOfTasks;
    RecyclerView recyclerView;
    TasksAdapter tasksAdapter;

    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding activityMainBinding;
    MainActivityClickHandler clickHandler;

    Button desButton;
    CheckBox statusBox;
    SharedPreferences statusSharedPreferences;

    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final int EDIT_TASK_REQUEST_CODE = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   //     listOfTasks = new ArrayList<>();

//        listOfTasks.add(new Tasks(1,"Task 1","20/12","High","Testing"));
//        listOfTasks.add(new Tasks(1,"Task 1","20/12","High","Testing"));
//        listOfTasks.add(new Tasks(1,"Task 1","20/12","High","Testing"));
//        listOfTasks.add(new Tasks(1,"Task 1","20/12","High","Testing"));
//        listOfTasks.add(new Tasks(1,"Task 1","20/12","High","Testing"));
//        tasksLayoutBinding = DataBindingUtil.setContentView(this,R.layout.tasks_layout);
//        desOnClickHandler = new TaskLayoutClickHandler();
//        tasksLayoutBinding.setDesClickHandler(desOnClickHandler);


        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        clickHandler = new MainActivityClickHandler();
        activityMainBinding.setClickHandler(clickHandler);


        mainActivityViewModel.getTasks().observe(this, new Observer<List<Tasks>>() {
            @Override
            public void onChanged(List<Tasks> tasks) {
                listOfTasks = (ArrayList<Tasks>) tasks;
//                for(Tasks c : tasks){
//                    Log.i("Tag",c.getTaskName());
//                }
                loadRecyclerAndAdapter();
                loadListener();
            }
        });




      //  status check box with shared preferences
//        View inflateView = getLayoutInflater().inflate(R.layout.tasks_layout,null);
//
//        statusBox = inflateView.findViewById(R.id.checkbox_status);
//        statusSharedPreferences = getSharedPreferences("statusPref",MODE_PRIVATE);
//        displayStatusBox();
//        statusBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = statusSharedPreferences.edit();
//                editor.putBoolean("check",statusBox.isChecked());
//                editor.commit();
//
//                if(statusBox.isChecked()){
//                    Toast.makeText(MainActivity.this, "Task completed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public void loadRecyclerAndAdapter(){
        recyclerView = activityMainBinding.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new TasksAdapter();
        recyclerView.setAdapter(tasksAdapter);
        tasksAdapter.setListOfTask(listOfTasks);

    }

    public void loadListener(){
        tasksAdapter.setListener(new TasksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Tasks task) {

            }

            @Override
            public void onItemDescriptionButtonClick() {
                DescriptionLayoutBinding descriptionLayoutBinding = DataBindingUtil.setContentView(MainActivity.this,R.layout.description_layout);
                descriptionLayoutBinding.setTasks(AddAndEdit.tasks);
            }
        });
    }

    private void displayStatusBox(){
        boolean checking = statusSharedPreferences.getBoolean("check",false);
        statusBox.setChecked(checking);
    }

    public class MainActivityClickHandler {
        public void onClickFloatingButton(View view){
            Intent i = new Intent(MainActivity.this, AddAndEdit.class);
            startActivityForResult(i,ADD_TASK_REQUEST_CODE);
            Toast.makeText(MainActivity.this, "floating button clicked", Toast.LENGTH_SHORT).show();
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
            mainActivityViewModel.insertTask(tasks);
        }
    }
}