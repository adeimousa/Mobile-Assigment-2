package edu.birzeit.mobileassigment2.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Student;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;

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