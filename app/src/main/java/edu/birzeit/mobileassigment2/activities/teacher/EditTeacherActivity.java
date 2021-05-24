package edu.birzeit.mobileassigment2.activities.teacher;

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
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.models.Field;
import edu.birzeit.mobileassigment2.models.Teacher;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;

public class EditTeacherActivity extends AppCompatActivity {
    //Define Views
    EditText fullNameEdt;
    DatePicker dob;
    EditText phone;
    EditText email;
    EditText nationalId;
    EditText address;
    Spinner fieldsSpinner;

    //Define models
    Teacher teacher;
    Field[] fields;
    int teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher);
        setupViews();
        // Get teacher id
        teacherId = getIntent().getIntExtra("teacherId", -1);
        // Get fields from backend
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
    }

    private void setupViews(){
        this.fullNameEdt =  findViewById(R.id.editTextFullNameTeacherEdt);
        this.dob = findViewById(R.id.datePickerTeacherEdt);
        this.phone = findViewById(R.id.editTextPhoneTeacherEdt);
        this.email = findViewById(R.id.editTextEmailAddressTeacherEdt);
        this.nationalId = findViewById(R.id.editTextNationalNumberTeacherEdt);
        this.address = findViewById(R.id.editTextAddressTeacherEdt);
        this.fieldsSpinner = findViewById(R.id.classSpinnerTeacherEdt);

    }

    void setupSpinner(){
        ArrayList<String> fieldsStrings = new ArrayList<String>();
        for (Field fieldItem: fields) {
            fieldsStrings.add(fieldItem.getFIELD_NAME());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fieldsStrings);
        fieldsSpinner.setAdapter(arrayAdapter);
    }

    void addDataToViews(Teacher teacher){
        // add teacher data to views
        System.out.println(teacher);
        fullNameEdt.setText(teacher.getFULL_NAME());
        Calendar calender = Calendar.getInstance();
        calender.setTime(teacher.getDOB());
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);
        dob.init(year,month,day,null);
        phone.setText(teacher.getPHONE());
        email.setText(teacher.getEMAIL());
        nationalId.setText(teacher.getNATIONAL_ID() + "");
        address.setText(teacher.getADDRESS());
        String fieldName = "";
        for (Field fieldItem: fields) {
            if (fieldItem.getFIELD_ID() == teacher.getFIELD_ID()){
                fieldName = fieldItem.getFIELD_NAME();
                break;
            }
        }
        //Set Spinner Item
        for (int i=0;i<fieldsSpinner.getCount();i++){

            if (fieldsSpinner.getItemAtPosition(i).equals(fieldName)){
                fieldsSpinner.setSelection(i);
            }
        }
    }

    public void editTeacher(View view) {
        String restUrl = "http://10.0.2.2:80/school-project/teacher.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPutRequest runner = new SendPutRequest();
            runner.execute(restUrl);
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
            fields = gson.fromJson(result, Field[].class);
            // add fields to spinner
             setupSpinner();
            // Get teacher data
            String url = "http://10.0.2.2:80/school-project/teacher.php?id="+ teacherId;
            GetTeacherTask runner = new GetTeacherTask();
            runner.execute(url);

        }
    }
    private class GetFieldsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            Gson gson = new Gson();
            fields = gson.fromJson(result, Field[].class);
            // add fields to spinner
             setupSpinner();
            // Get teacher data
            String url = "http://10.0.2.2:80/school-project/teacher.php?id="+ teacherId;
            GetTeacherTask runner = new GetTeacherTask();
            runner.execute(url);

        }
    }
    private class GetTeacherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            System.out.println(result);
            teacher = gson.fromJson(result, Teacher.class);
            addDataToViews(teacher);

        }
    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        int fieldId = -1;
        for (Field field: fields) {
            if (field.getFIELD_NAME().equals(fieldsSpinner.getSelectedItem().toString())){
                fieldId = field.getFIELD_ID();
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

        String data = URLEncoder.encode("fullName", "UTF-8")
                + "=" + URLEncoder.encode(fullNameEdt.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("id", "UTF-8") + "="
                + URLEncoder.encode(teacherId+"", "UTF-8");
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
        data += "&" + URLEncoder.encode("fieldId", "UTF-8") + "="
                + URLEncoder.encode(fieldId+"", "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
            System.out.println(text);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;



    }
    private class SendPutRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), TeacherActivity.class);
            startActivity(intent);
//            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }



}