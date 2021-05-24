package edu.birzeit.mobileassigment2.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.activities.student.EditStudentActivity;
import edu.birzeit.mobileassigment2.activities.student.StudentActivity;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Student;

public class StudentAdapter extends BaseAdapter {
    Context context;
    Student[] students;
    Class[] classes;
    LayoutInflater inflter;

    public StudentAdapter(Context applicationContext, Student[] students, Class[] classes) {
        this.context = context;
        this.students = students;
        this.classes = classes;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return students.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.student_item, null);
        TextView studentName = (TextView) view.findViewById(R.id.studentName);
        TextView className = (TextView) view.findViewById(R.id.className);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        String studentClassName = "";
        for (Class classItem :classes ){
            if (classItem.getCLASS_ID() == students[i].getCLASS_ID()){
                studentClassName = classItem.getNAME();
            }

        }
        studentName.setText(students[i].getFIRST_NAME() + " " + students[i].getLAST_NAME());
      className.setText(studentClassName);
        icon.setImageResource(R.drawable.ic_action_name);
//        ImageButton editStudentBtn = (ImageButton) view.findViewById(R.id.editStudentButton);
//        editStudentBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(context.getApplicationContext(), EditStudentActivity.class);
//                context.startActivity(intent);
//            }
//        });
        return view;
    }

}