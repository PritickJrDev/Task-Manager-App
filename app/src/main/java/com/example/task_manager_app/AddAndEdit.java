package com.example.task_manager_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_manager_app.databinding.ActivityAddAndEditBinding;
import com.example.task_manager_app.databinding.DescriptionLayoutBinding;
import com.example.task_manager_app.model.Tasks;

import java.text.DateFormat;
import java.util.Calendar;

public class AddAndEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ActivityAddAndEditBinding activityAddAndEditBinding;
    private ActivityAddAndEditClickHandler activityAddAndEditClickHandler;
    public static final String TASK_ID = "taskId";
    public static final String TASK_NAME = "taskName";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";
    public static final String DUE_DATE = "dueDate";
    public static Tasks tasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_edit);

        tasks = new Tasks();

         activityAddAndEditBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_and_edit);
         activityAddAndEditBinding.setTasks(tasks);

         activityAddAndEditClickHandler = new ActivityAddAndEditClickHandler(this);
         activityAddAndEditBinding.setClickHandler(activityAddAndEditClickHandler);

//        DescriptionLayoutBinding descriptionLayoutBinding = DataBindingUtil.setContentView(this,R.layout.description_layout);
//        descriptionLayoutBinding.setTasks(tasks);

         Intent i = getIntent();
         if(i.hasExtra(TASK_ID)){
             setTitle("EDIT COURSE");
         } else {
             setTitle("CREATE TASK");
         }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, i);
        cal.set(Calendar.MONTH, i1);
        cal.set(Calendar.DAY_OF_MONTH, i2);

        String date = DateFormat.getDateInstance(DateFormat.FULL).format(cal.getTime());
        TextView dateTextView = findViewById(R.id.textView_pickDate);
        dateTextView.setText(date);

        //String date = i+"-"+i1+"-"+i2;
        Toast.makeText(this,"Date set : "+date,Toast.LENGTH_LONG).show();
    }

    public class ActivityAddAndEditClickHandler {
        Context context;

        public ActivityAddAndEditClickHandler(Context context) {
            this.context = context;
        }

        public void onClickRadioButton(RadioGroup radioGroup, int checked){
            RadioButton radioButton = findViewById(checked);
            TextView priority = findViewById(R.id.textView_setPriority);

            if(radioButton.getText().equals("High")){
                priority.setText("High");
               // priority.setTextColor(getColor(R.color.red));
            } else if(radioButton.getText().equals("Low")){
                priority.setText("Low");
               // priority.setTextColor(getColor(R.color.green));
            }
            Toast.makeText(AddAndEdit.this, "Radio button : "+radioButton.getText(), Toast.LENGTH_SHORT).show();
        }

        public void onClickDatePicker(View view){
            DialogFragment df = new DatePickerFragment();
            df.show(getSupportFragmentManager(),"Pick date now");
        }

        public void onSaveButtonClick(View view){
            if(tasks.getDescription() == null){
                Toast.makeText(context, "Please enter all details", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent i = new Intent();
                i.putExtra(TASK_NAME,tasks.getTaskName());
                i.putExtra(DESCRIPTION,tasks.getDescription());
                i.putExtra(PRIORITY,tasks.getTaskPriority());
                i.putExtra(DUE_DATE,tasks.getDueDate());
                setResult(RESULT_OK,i);
                finish();
            }
        }
    }
}