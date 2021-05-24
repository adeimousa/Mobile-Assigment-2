package edu.birzeit.mobileassigment2.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.StudentAdapter;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Student;

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

    }
    public void onClickbtn(View view) {

//        EditText edtCat = findViewById(R.id.edtCat);


        String url = "http://localhost/school-project/student.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            GetStudentsTask runner = new GetStudentsTask();
            runner.execute(url);
        }



    }
    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
        return str;
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
                    startActivity(intent);
                }
            });
        }
    }
    private class GetClassesTask extends AsyncTask<String, Void, String> {
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