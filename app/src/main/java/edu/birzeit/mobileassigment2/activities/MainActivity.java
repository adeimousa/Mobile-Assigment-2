package edu.birzeit.mobileassigment2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.classes.ClassActivity;
import edu.birzeit.mobileassigment2.activities.student.StudentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void goToStudent(View view) {
        Intent intent = new Intent(this, StudentActivity.class);
        startActivity(intent);
    }

    public void goToClass(View view) {
        Intent intent = new Intent(this, ClassActivity.class);
        startActivity(intent);
    }
}