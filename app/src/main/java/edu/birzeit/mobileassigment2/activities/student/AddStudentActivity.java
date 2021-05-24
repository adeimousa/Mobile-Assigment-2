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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import edu.birzeit.mobileassigment2.R;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        setupViews();
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
    private String processRequest(String restUrl) throws UnsupportedEncodingException {

        String data = URLEncoder.encode("firstName", "UTF-8")
                + "=" + URLEncoder.encode(firstNameEdt.getText().toString(), "UTF-8");

        data += "&" + URLEncoder.encode("middleName", "UTF-8") + "="
                + URLEncoder.encode(middleNameEdt.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("lastName", "UTF-8") + "="
                + URLEncoder.encode(lastNameEdt.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("gender", "UTF-8") + "="
                + URLEncoder.encode(middleNameEdt.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("dob", "UTF-8") + "="
                + URLEncoder.encode(middleNameEdt.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("phone", "UTF-8") + "="
                + URLEncoder.encode(phone.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("nationalId", "UTF-8") + "="
                + URLEncoder.encode(nationalId.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("address", "UTF-8") + "="
                + URLEncoder.encode(address.getText().toString(), "UTF-8");
        data += "&" + URLEncoder.encode("classId", "UTF-8") + "="
                + URLEncoder.encode("1", "UTF-8");
        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
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

    public void addStudent(View view) {
        String restUrl = "http://10.0.2.2:80/school-project/student.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }
    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
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
            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
            startActivity(intent);
//            Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

}


