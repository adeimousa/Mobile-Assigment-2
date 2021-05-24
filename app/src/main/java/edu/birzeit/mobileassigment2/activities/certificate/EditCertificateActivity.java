package edu.birzeit.mobileassigment2.activities.certificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.models.Certificate;
import edu.birzeit.mobileassigment2.models.CertificateType;
import edu.birzeit.mobileassigment2.models.Field;
import edu.birzeit.mobileassigment2.models.Teacher;

public class EditCertificateActivity extends AppCompatActivity {

    private Field[] fields;
    private CertificateType[] certificateTypes;
    private Teacher[] teachers;

    private Certificate certificate;

    private String teacherName;
    private String certificateTypeName;
    private String fieldName;

    TextView txt_certificateId_edit;
    Spinner spn_certificateType;
    TextView txt_teacherName_edit;
    EditText edt_organization;
    EditText edtTxtMultiLine_description_edit;
    DatePicker datePicker_since;
    DatePicker datePicker_to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_certificate);

        initConnection();
        setupViews();
        initIntent();
        initViews();
    }

    private void initCertificatesTypesSpinner() {
        // spn_certificateType
        ArrayList<String> certificateTypesStrings = new ArrayList<String>();
        for (CertificateType certificateType: certificateTypes) {
            certificateTypesStrings.add(certificateType.getCERTIFICATION_TYPE_NAME());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, certificateTypesStrings);
        spn_certificateType.setAdapter(arrayAdapter);
    }

    private void setupViews() {
        txt_certificateId_edit = findViewById(R.id.txt_certificateId_edit);
        spn_certificateType = findViewById(R.id.spn_certificateType);
        txt_teacherName_edit = findViewById(R.id.txt_teacherName_edit);
        edt_organization = findViewById(R.id.edt_organization);
        edtTxtMultiLine_description_edit = findViewById(R.id.edtTxtMultiLine_description_edit);
        datePicker_since = findViewById(R.id.datePicker_since);
        datePicker_to = findViewById(R.id.datePicker_to);
    }

    private void initIntent()  {
        Intent intent = getIntent();

        ArrayList<String> certificateData;

        certificateData = intent.getStringArrayListExtra("certificate_DATA");

        certificate = new Certificate(Integer.parseInt(certificateData.get(0)),
                Integer.parseInt(certificateData.get(1)),
                Integer.parseInt(certificateData.get(2)),
                Integer.parseInt(certificateData.get(3)),
                certificateData.get(4),
                certificateData.get(5),
                new Date(certificateData.get(6)),
                new Date(certificateData.get(7)));
    }

    private void initViews() {

        edt_organization.setText(certificate.getORGANIZATION_NAME());
        edtTxtMultiLine_description_edit.setText(certificate.getDESCRIPTION());

        /*String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String fromDate = simpleDateFormat.format(certificate.getFROM_DATE());
        String toDate = simpleDateFormat.format(certificate.getTO_DATE());

        txt_certificateId.setText(Integer.toString(certificate.getCERTIFICATE_ID()));
        // txt_teacherName.setText(getTeacherNameById(certificate.getTEACHER_ID())); // Displayed Below as the field and certificate type
        txt_organizationName.setText(certificate.getORGANIZATION_NAME());
        edtTxtMultiLine_description.setText(certificate.getDESCRIPTION());
        txt_fromDate.setText(fromDate);
        txt_toDate.setText(toDate);*/
    }

    private void initConnection() {
        String url_fields = "http://10.0.2.2:80/school-project/field.php?";
        String url_certificateTypes = "http://10.0.2.2:80/school-project/certificate-type.php?";
        String url_teachers = "http://10.0.2.2:80/school-project/teacher.php?";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            DownloadFieldTask field_runner = new DownloadFieldTask();
            DownloadCertificateTypeTask certificate_type_runner = new DownloadCertificateTypeTask();
            DownloadTeacherTask teacher_runner = new DownloadTeacherTask();

            field_runner.execute(url_fields);
            certificate_type_runner.execute(url_certificateTypes);
            teacher_runner.execute(url_teachers);
        }
    }

    private InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private String DownloadText(String URL) {
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
            while ((charRead = isr.read(inputBuffer)) > 0) {
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

    private class DownloadFieldTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // System.out.println(result);
            Gson gson = new Gson();
            fields = gson.fromJson(result, Field[].class);

            /*for (int i = 0; i < fields.length; i++) {
                if (fields[i].getFIELD_ID() == certificate.getFIELD_ID()) {
                    fieldName = fields[i].getFIELD_NAME();
                    break;
                }
            }

            txt_certificateField.setText(fieldName);*/
        }
    }

    private class DownloadCertificateTypeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // System.out.println(result);
            Gson gson = new Gson();
            certificateTypes = gson.fromJson(result, CertificateType[].class);

            initCertificatesTypesSpinner();

            /*for (int i = 0; i < certificateTypes.length; i++) {
                if (certificateTypes[i].getCERTICATION_TYPE_ID() == certificate.getCERTIFICATE_TYPE_ID()) {
                    certificateTypeName = certificateTypes[i].getCERTIFICATION_TYPE_NAME();
                    break;
                }
            }

            txt_certificateType.setText(certificateTypeName);*/
        }
    }

    private class DownloadTeacherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new Gson();
            teachers = gson.fromJson(result, Teacher[].class);

            for (int i = 0; i < teachers.length; i++) {
                if (teachers[i].getTEACHER_ID() == certificate.getTEACHER_ID()) {
                    teacherName = teachers[i].getFULL_NAME();
                    break;
                }
            }
            txt_teacherName_edit.setText(teacherName);
        }
    }


    public void btn_update_onClick(View view) {
        Intent intent = new Intent(this, ViewCertificateActivity.class);

        startActivity(intent);
    }
}