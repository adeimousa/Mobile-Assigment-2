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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.student.AddStudentActivity;
import edu.birzeit.mobileassigment2.activities.student.StudentActivity;
import edu.birzeit.mobileassigment2.models.Class;

import static edu.birzeit.mobileassigment2.HttpConnection.postRequest;

public class AddClassActivity extends AppCompatActivity {
    private EditText className;
    private EditText roomNumber;
    private EditText sectionNumber;
    private EditText capacity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        setupViews();
    }

    void setupViews(){
        className = findViewById(R.id.editTextClassNameAdd);
        roomNumber = findViewById(R.id.editTextRoomNumberAdd);
        sectionNumber = findViewById(R.id.editTextSectionNumberAdd);
        capacity = findViewById(R.id.editTextCapacityAdd);
    }



    public void addClass(View view) {
        String restUrl = "http://10.0.2.2:80/school-project/class.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        }
        else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }
    }
    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {

                String data = URLEncoder.encode("name", "UTF-8")
                        + "=" + URLEncoder.encode(className.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("roomNumber", "UTF-8") + "="
                        + URLEncoder.encode(roomNumber.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("sectionNumber", "UTF-8") + "="
                        + URLEncoder.encode(sectionNumber.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("capacity", "UTF-8") + "="
                        + URLEncoder.encode(capacity.getText().toString(), "UTF-8");

                return postRequest(urls[0],data);
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
}