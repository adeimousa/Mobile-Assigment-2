package edu.birzeit.mobileassigment2.activities.classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.student.EditStudentActivity;
import edu.birzeit.mobileassigment2.activities.student.StudentActivity;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Student;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;
import static edu.birzeit.mobileassigment2.HttpConnection.putRequest;

public class EditClassActivity extends AppCompatActivity {
    private EditText className;
    private EditText roomNumber;
    private EditText sectionNumber;
    private EditText capacity;
    private int classId;
    private Class classItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        setupViews();
        classId = getIntent().getIntExtra("classId", -1);
        String url = "http://10.0.2.2:80/school-project/class.php?id=" + classId;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            GetClassTask runner = new GetClassTask();
            runner.execute(url);
        }

    }
    void setupViews(){
        className = findViewById(R.id.editTextClassNameAdd);
        roomNumber = findViewById(R.id.editTextRoomNumberAdd);
        sectionNumber = findViewById(R.id.editTextSectionNumberAdd);
        capacity = findViewById(R.id.editTextCapacityAdd);
    }

    void addDataToViews(Class classItem){
        className.setText(classItem.getNAME());
        roomNumber.setText(classItem.getROOM_NUMBER() +"");
        sectionNumber.setText(classItem.getSECTION_NUMBER() +"");
        capacity.setText(classItem.getCAPACITY() +"");
    }

    public void editClass(View view) {
        String restUrl = "http://10.0.2.2:80/school-project/class.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        }
        else{
            SendPutRequest runner = new SendPutRequest();
            runner.execute(restUrl);
        }
    }
    private class SendPutRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {

                String data = URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(className.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("id", "UTF-8") + "="
                        + URLEncoder.encode(classId+"", "UTF-8");
                data += "&" + URLEncoder.encode("roomNumber", "UTF-8") + "="
                        + URLEncoder.encode(roomNumber.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("sectionNumber", "UTF-8") + "="
                        + URLEncoder.encode(sectionNumber.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("capacity", "UTF-8") + "="
                        + URLEncoder.encode(capacity.getText().toString(), "UTF-8");
                return putRequest(urls[0],data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), ClassActivity.class);
            startActivity(intent);
//            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
    private class GetClassTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            classItem = gson.fromJson(result, Class.class);
            addDataToViews(classItem);

        }
    }
}