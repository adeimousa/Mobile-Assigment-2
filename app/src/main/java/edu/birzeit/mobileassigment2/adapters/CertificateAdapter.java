package edu.birzeit.mobileassigment2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.birzeit.mobileassigment2.R;
import edu.birzeit.mobileassigment2.models.Certificate;
import edu.birzeit.mobileassigment2.models.Class;
import edu.birzeit.mobileassigment2.models.Student;

public class CertificateAdapter extends BaseAdapter {
    Context context;
    Certificate[] certificates;
    LayoutInflater inflter;

    public CertificateAdapter(Context applicationContext, Certificate[] certificates) {
        this.context = context;
        this.certificates = certificates;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return certificates.length;
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
        view = inflter.inflate(R.layout.certificate_item, null);

        TextView orgName = (TextView) view.findViewById(R.id.organizationNameItem);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);

        orgName.setText(certificates[i].getORGANIZATION_NAME());
        icon.setImageResource(R.drawable.ic_gpa_reult);
        return view;
    }

}