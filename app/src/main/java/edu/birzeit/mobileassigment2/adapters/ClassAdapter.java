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
import edu.birzeit.mobileassigment2.models.Student;

public class ClassAdapter extends BaseAdapter {
    Context context;
    Class[] classes;
    LayoutInflater inflter;

    public ClassAdapter(Context applicationContext, Class[] classes) {
        this.context = context;
        this.classes = classes;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return classes.length;
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
        view = inflter.inflate(R.layout.class_item, null);
        TextView className = (TextView) view.findViewById(R.id.className);
        TextView capacity = (TextView) view.findViewById(R.id.capacity);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        className.setText(classes[i].getNAME());
        capacity.setText("Capacity: " + classes[i].getCAPACITY() + "");
        icon.setImageResource(R.drawable.ic_action_name);
        return view;
    }

}