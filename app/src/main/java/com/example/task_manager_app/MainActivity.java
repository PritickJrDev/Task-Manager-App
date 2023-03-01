package com.example.task_manager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CheckBox statusBox;
    SharedPreferences statusSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //status check box with shared preferences
        statusBox = findViewById(R.id.checkbox_status);
        statusSharedPreferences = getSharedPreferences("statusPref",MODE_PRIVATE);
        displayStatusBox();
        statusBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = statusSharedPreferences.edit();
                editor.putBoolean("check",statusBox.isChecked());
                editor.commit();

                if(statusBox.isChecked()){
                    Toast.makeText(MainActivity.this, "Task completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayStatusBox(){
        boolean checking = statusSharedPreferences.getBoolean("check",false);
        statusBox.setChecked(checking);
    }
}