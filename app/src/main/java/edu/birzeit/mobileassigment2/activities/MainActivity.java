package edu.birzeit.mobileassigment2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.student.StudentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }
}