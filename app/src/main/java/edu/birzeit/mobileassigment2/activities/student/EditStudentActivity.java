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
import edu.birzeit.mobileassigment2.models.Student;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;
import static edu.birzeit.mobileassigment2.HttpConnection.postRequest;
import static edu.birzeit.mobileassigment2.HttpConnection.putRequest;

public class EditStudentActivity extends AppCompatActivity {
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

    //Define models
    Student student;
    Class[] classes;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        setupViews();
        // Get student id
        studentId = getIntent().getIntExtra("studentId", -1);
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

   void addDataToViews(Student student){
        // add student data to views
       firstNameEdt.setText(student.getFIRST_NAME());
       lastNameEdt.setText(student.getLAST_NAME());
       middleNameEdt.setText(student.getMIDDLE_NAME());
       if (student.getGENDER().equals("male")){
           maleBtn.toggle();
       }else if (student.getGENDER().equals("female")) {
           femaleBtn.toggle();
       }
       Calendar calender = Calendar.getInstance();
       calender.setTime(student.getDateOfBirth());
       int day = calender.get(Calendar.DAY_OF_MONTH);
       int month = calender.get(Calendar.MONTH);
       int year = calender.get(Calendar.YEAR);
       dob.init(year,month,day,null);
       phone.setText(student.getPHONE());
       email.setText(student.getEMAIL());
       nationalId.setText(student.getNATIONAL_ID() + "");
       address.setText(student.getADDRESS());
       String className = "";
       for (Class classItem: classes) {
           if (classItem.getCLASS_ID() == student.getCLASS_ID()){
               className = classItem.getNAME();
               break;
           }
       }
       //Set Spinner Item
       for (int i=0;i<classesSpinner.getCount();i++){

           if (classesSpinner.getItemAtPosition(i).equals(className)){
               classesSpinner.setSelection(i);
           }
       }
    }

    public void editStudent(View view) {
        String restUrl = "http://10.0.2.2:80/school-project/student.php";
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
                data += "&" + URLEncoder.encode("id", "UTF-8") + "="
                        + URLEncoder.encode(studentId+"", "UTF-8");
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
                return putRequest(urls[0],data);
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
            // Get student data
            String url = "http://10.0.2.2:80/school-project/student.php?id="+ studentId;
            GetStudentTask runner = new GetStudentTask();
            runner.execute(url);

        }
    }
    private class GetStudentTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            student = gson.fromJson(result, Student.class);
            addDataToViews(student);

        }
    }



}