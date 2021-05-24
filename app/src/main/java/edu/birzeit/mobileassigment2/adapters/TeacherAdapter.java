package edu.birzeit.mobileassigment2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Field;
import edu.birzeit.mobileassigment2.models.Student;
import edu.birzeit.mobileassigment2.models.Teacher;

public class TeacherAdapter extends BaseAdapter {
    Context context;
    Teacher[] teachers;
    Field[] fields;
    LayoutInflater inflter;

    public TeacherAdapter(Context applicationContext, Teacher[] teachers, Field[] fields) {
        this.context = context;
        this.teachers = teachers;
        this.fields = fields;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return teachers.length;
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
        view = inflter.inflate(R.layout.teacher_item, null);
        TextView teacherName = (TextView) view.findViewById(R.id.teacherName);
        TextView fieldName = (TextView) view.findViewById(R.id.fieldName);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        String teacherFieldName = "";
        for (Field filedItem :fields ){
            if (filedItem.getFIELD_ID() == teachers[i].getFIELD_ID()){
                teacherFieldName = filedItem.getFIELD_NAME();
            }

        }
        teacherName.setText(teachers[i].getFULL_NAME());
        fieldName.setText(teacherFieldName);
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