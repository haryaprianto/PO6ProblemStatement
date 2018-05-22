package com.example.a16022596.po5problemstatement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvResult;
    Button btnAddTask;
    ArrayAdapter aa;
    ArrayList<Task> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddTask = (Button)findViewById(R.id.buttonAddTask);
        lvResult = (ListView)findViewById(R.id.listViewTask);
        DatabaseHelper db = new DatabaseHelper(MainActivity.this);
        taskList = db.getTasks();
        db.close();
        aa = new TaskAdapter(MainActivity.this,R.layout.row,taskList);
        lvResult.setAdapter(aa);


        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        AddTasks.class);
                startActivityForResult(i, 9);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9){
            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
            taskList = db.getTasks();
            db.close();
            aa = new TaskAdapter(MainActivity.this,R.layout.row,taskList);
            lvResult.setAdapter(aa);
        }
    }
}
