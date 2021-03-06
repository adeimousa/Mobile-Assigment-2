package edu.birzeit.mobileassigment2.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.adapters.StudentAdapter;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Student;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;

public class StudentActivity extends AppCompatActivity {
    Student[] students;
    Class[] classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        String url = "http://10.0.2.2:80/school-project/class.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            GetClassesTask runner = new GetClassesTask();
            runner.execute(url);
        }
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addStudentBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToAddStudent();
            }
        });

        EditText serachField = findViewById(R.id.editTextSearchStudent);
        serachField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                adapter.getFilter().filter(cs);
                System.out.println(cs);
                String url = "http://10.0.2.2:80/school-project/student.php?nameFilter="+cs;

                GetStudentsTask runner = new GetStudentsTask();
                runner.execute(url);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
//                Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
            }
        });

    }




    public void goToAddStudent() {
        Intent intent = new Intent(this, AddStudentActivity.class);
        startActivity(intent);
    }
    public  void  goToEditStudent() {
        Intent intent = new Intent(this, EditStudentActivity.class);
        startActivity(intent);
    }


    private class GetStudentsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            students = gson.fromJson(result, Student[].class);
            ListView simpleList;
            simpleList = (ListView) findViewById(R.id.studentsList);
            StudentAdapter studentAdapter = new StudentAdapter(getApplicationContext(), students, classes);
            simpleList.setAdapter(studentAdapter);
            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), EditStudentActivity.class);
                    intent.putExtra("studentId", students[position].getSTUDENT_ID());
                    startActivity(intent);
                }
            });
        }
    }

    public  class GetClassesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            Gson gson = new Gson();
            classes = gson.fromJson(result, Class[].class);
            String url = "http://10.0.2.2:80/school-project/student.php";

                GetStudentsTask runner = new GetStudentsTask();
                runner.execute(url);
        }
    }
}