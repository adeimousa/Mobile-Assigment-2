package edu.birzeit.mobileassigment2.activities.teacher;

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
import edu.birzeit.mobileassigment2.adapters.TeacherAdapter;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Field;
import edu.birzeit.mobileassigment2.models.Teacher;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;

public class TeacherActivity extends AppCompatActivity {

    Teacher[] teachers;
    Field[] fields;
    Class[] classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        String url = "http://10.0.2.2:80/school-project/field.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            GetFieldsTask runner = new GetFieldsTask();
            runner.execute(url);
        }
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addTeacherBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToAddTeacher();
            }
        });

        EditText serachField = findViewById(R.id.editTextSearchTeacher);
        serachField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                adapter.getFilter().filter(cs);
                System.out.println(cs);
                String url = "http://10.0.2.2:80/school-project/teacher.php?nameFilter="+cs;

                GetTeachersTask runner = new GetTeachersTask();
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




    public void goToAddTeacher() {
        Intent intent = new Intent(this, AddTeacherActivity.class);
        startActivity(intent);
    }
    public  void  goToEditTeacher() {
        Intent intent = new Intent(this, EditTeacherActivity.class);
        startActivity(intent);
    }


    private class GetTeachersTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            teachers = gson.fromJson(result, Teacher[].class);
            ListView simpleList;
            simpleList = (ListView) findViewById(R.id.teachersList);
            TeacherAdapter teacherAdapter = new TeacherAdapter(getApplicationContext(), teachers, fields);
            simpleList.setAdapter(teacherAdapter);
            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), EditTeacherActivity.class);
                    intent.putExtra("teacherId", teachers[position].getTEACHER_ID());
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
            String url = "http://10.0.2.2:80/school-project/teacher.php";

            GetTeachersTask runner = new GetTeachersTask();
            runner.execute(url);
        }
    }
 public  class GetFieldsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            Gson gson = new Gson();
            fields = gson.fromJson(result, Field[].class);
            String url = "http://10.0.2.2:80/school-project/teacher.php";

            GetTeachersTask runner = new GetTeachersTask();
            runner.execute(url);
        }
    }
}