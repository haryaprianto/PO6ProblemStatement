package com.example.a16022596.po5problemstatement;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddTasks extends AppCompatActivity {

    Button btnAdd;
    Button btnCancel;
    EditText etName;
    EditText etDescription;
    DatabaseHelper db;
    int reqCode = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        btnAdd = (Button)findViewById(R.id.buttonAdd);
        btnCancel = (Button)findViewById(R.id.buttonCancel);

        etDescription = (EditText)findViewById(R.id.editTextDescription);
        etName = (EditText)findViewById(R.id.editTextName);

        db = new DatabaseHelper(this);
        db.getWritableDatabase();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDescription.getText().toString().isEmpty() && !etName.getText().toString().isEmpty()) {


                    Log.i("info", "MainActivity");
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.SECOND, 5);

                    Intent intent = new Intent(AddTasks.this,
                            NotificationReciever.class);
                    intent.putExtra("task", etDescription.getText().toString());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            AddTasks.this, reqCode,
                            intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    AlarmManager am = (AlarmManager)
                            getSystemService(Activity.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                            pendingIntent);
                    String name = etName.getText().toString();
                    String description = etDescription.getText().toString();
                    db.insertTask(description, name);
                    setResult(RESULT_OK);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Please Fill in the blank",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
