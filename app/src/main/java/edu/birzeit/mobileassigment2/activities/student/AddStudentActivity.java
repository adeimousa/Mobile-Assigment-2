package edu.birzeit.mobileassigment2.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.models.Class;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;
import static edu.birzeit.mobileassigment2.HttpConnection.postRequest;

public class AddStudentActivity extends AppCompatActivity {
    //Define Views
    EditText firstNameEdt;
    EditText middleNameEdt;
    EditText lastNameEdt;
    RadioButton maleBtn;
    RadioButton femaleBtn;
    DatePicker dob;
    EditText phone;
    EditText email;
    EditText nationalId;
    EditText address;
    Spinner classesSpinner;

    Class[] classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        setupViews();
        // Get classes from backend
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
    }
    private void setupViews(){
        this.firstNameEdt =  findViewById(R.id.editTextFirstName);
        this.middleNameEdt =  findViewById(R.id.editTextMiddleName);
        this.lastNameEdt =  findViewById(R.id.editTextLastName);
        this.maleBtn = findViewById(R.id.radioButtonMale);
        this.femaleBtn = findViewById(R.id.radioButtonFemale);
        this.dob = findViewById(R.id.datePicker);
        this.phone = findViewById(R.id.editTextPhone);
        this.email = findViewById(R.id.editTextEmailAddress);
        this.nationalId = findViewById(R.id.editTextNationalNumber);
        this.address = findViewById(R.id.editTextAddress);
        this.classesSpinner = findViewById(R.id.classSpinner);

    }

    void setupSpinner(){
        ArrayList<String> classesStrings = new ArrayList<String>();
        for (Class classItem: classes) {
            classesStrings.add(classItem.getNAME());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classesStrings);
        classesSpinner.setAdapter(arrayAdapter);
    }
    public void addStudent(View view) {
        String restUrl = "http://10.0.2.2:80/school-project/student.php";
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
               int classId = -1;
                for (Class classItem: classes) {
                    if (classItem.getNAME().equals(classesSpinner.getSelectedItem().toString())){
                        classId = classItem.getCLASS_ID();
                        break;
                    }
                }
                int day = dob.getDayOfMonth();
                int month = dob.getMonth();
                int year = dob.getYear();
                Calendar mCalender = Calendar.getInstance();
                mCalender.set(Calendar.YEAR, year);
                mCalender.set(Calendar.MONTH, month);
                mCalender.set(Calendar.DAY_OF_MONTH, day);
                android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
               String selectedDate = dateFormat.format("yyyy-MM-dd", mCalender.getTime()).toString();
                String data = URLEncoder.encode("firstName", "UTF-8")
                        + "=" + URLEncoder.encode(firstNameEdt.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("middleName", "UTF-8") + "="
                        + URLEncoder.encode(middleNameEdt.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("lastName", "UTF-8") + "="
                        + URLEncoder.encode(lastNameEdt.getText().toString(), "UTF-8");
                if (maleBtn.isChecked()){
                    data += "&" + URLEncoder.encode("gender", "UTF-8") + "="
                            + URLEncoder.encode("male", "UTF-8");
                }else if (femaleBtn.isChecked()){
                    data += "&" + URLEncoder.encode("gender", "UTF-8") + "="
                            + URLEncoder.encode("female", "UTF-8");
                }
                         data += "&" + URLEncoder.encode("dob", "UTF-8") + "="
                        + URLEncoder.encode(selectedDate, "UTF-8");

                data += "&" + URLEncoder.encode("phone", "UTF-8") + "="
                        + URLEncoder.encode(phone.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                        + URLEncoder.encode(email.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("nationalId", "UTF-8") + "="
                        + URLEncoder.encode(nationalId.getText().toString(), "UTF-8");
                data += "&" + URLEncoder.encode("address", "UTF-8") + "="
                        + URLEncoder.encode(address.getText().toString(), "UTF-8");

                data += "&" + URLEncoder.encode("classId", "UTF-8") + "="
                        + URLEncoder.encode(classId+"", "UTF-8");
                return postRequest(urls[0],data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
            startActivity(intent);
//            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
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
            // add classes to spinner
            setupSpinner();

        }
    }

}


