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
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.adapters.CertificateAdapter;
import edu.birzeit.mobileassigment2.models.Certificate;
import edu.birzeit.mobileassigment2.models.CertificateType;

public class CertificatesActivity extends AppCompatActivity {

    private Certificate[] certificates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        initConnection();
    }


    private void viewCertificates(int i) {

        ArrayList<String> certificateData = new ArrayList<>();

        certificateData.add(Integer.toString(certificates[i].getCERTIFICATE_ID()));
        certificateData.add(Integer.toString(certificates[i].getTEACHER_ID()));
        certificateData.add(Integer.toString(certificates[i].getFIELD_ID()));
        certificateData.add(Integer.toString(certificates[i].getCERTIFICATE_TYPE_ID()));
        certificateData.add(certificates[i].getORGANIZATION_NAME());
        certificateData.add(certificates[i].getDESCRIPTION());
        certificateData.add(certificates[i].getFROM_DATE().toString());
        certificateData.add(certificates[i].getTO_DATE().toString());

        Intent intent = new Intent(this, ViewCertificateActivity.class);

        intent.putStringArrayListExtra("certificate_DATA", certificateData);

        startActivity(intent);
    }

    /*class customAdapter extends BaseAdapter {

        Certificate[] certificateItems;

        customAdapter(Certificate[] _certificateItems) {
            this.certificateItems = _certificateItems;
        }

        @Override
        public int getCount() {
            return certificateItems.length;
        }

        @Override
        public Object getItem(int position) {
            //////////////// temp situation
            return certificateItems[position].getOrganizationName();
        }

        @Override
        public long getItemId(int position) {
            return certificateItems[position].getCertificateId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view1 = inflater.inflate(R.layout.activity_certificate, null);
            TextView certificate_org = (TextView) view1.findViewById(R.id.txt_certificateOrg);
            certificate_org.setText(certificateItems[position].getOrganizationName());
            return view1;
        }
    }*/

    private void initConnection() {
        String url_certificates = "http://10.0.2.2:80/school-project/certificate.php?";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else {
            DownloadCertificateTask certificate_runner = new DownloadCertificateTask();

            certificate_runner.execute(url_certificates);
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

    private class DownloadCertificateTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // System.out.println(result);
            Gson gson = new Gson();
            certificates = gson.fromJson(result, Certificate[].class);
            System.out.println(result);
            displayCertificates();

            /*for (int i = 0; i < certificates.length; i++) {
                System.out.println(certificates[i].toString());
            }*/
        }
    }

    private void displayCertificates() {
        ListView certificatesList;
        certificatesList = (ListView) findViewById(R.id.certificatesListView);
        CertificateAdapter certificateAdapter = new CertificateAdapter(getApplicationContext(), certificates);
        certificatesList.setAdapter(certificateAdapter);
        certificatesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                viewCertificates(position);
            }
        });
    }
}