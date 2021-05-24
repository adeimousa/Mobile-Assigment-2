package edu.birzeit.mobileassigment2.activities.classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.student.AddStudentActivity;
import edu.birzeit.mobileassigment2.activities.student.EditStudentActivity;
import edu.birzeit.mobileassigment2.activities.student.StudentActivity;
import edu.birzeit.mobileassigment2.adapters.ClassAdapter;
import edu.birzeit.mobileassigment2.adapters.StudentAdapter;
import edu.birzeit.mobileassigment2.models.Class;

import static edu.birzeit.mobileassigment2.HttpConnection.DownloadText;

public class ClassActivity extends AppCompatActivity {
    Class[] classes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
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
        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addClassBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToAddClass();
            }
        });

        EditText serachField = findViewById(R.id.editTextSearchClass);
        serachField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                adapter.getFilter().filter(cs);
                System.out.println(cs);
                String url = "http://10.0.2.2:80/school-project/class.php?nameFilter="+cs;

                GetClassesTask runner = new GetClassesTask();
                runner.execute(url);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
//                Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void goToAddClass() {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivity(intent);
    }


    public  class GetClassesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            Gson gson = new Gson();
            classes = gson.fromJson(result, Class[].class);
            ListView classList;
            classList = (ListView) findViewById(R.id.classesList);
            ClassAdapter classAdapter = new ClassAdapter(getApplicationContext(), classes);
            classList.setAdapter(classAdapter);
            classList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), EditClassActivity.class);
                    intent.putExtra("classId", classes[position].getCLASS_ID());
                    startActivity(intent);
                }
            });
        }
    }
}