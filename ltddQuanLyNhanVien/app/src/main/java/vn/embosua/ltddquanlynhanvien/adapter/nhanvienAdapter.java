package vn.embosua.ltddquanlynhanvien.adapter;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import vn.embosua.ltddquanlynhanvien.R;
import vn.embosua.ltddquanlynhanvien.modle.nhanvien;

import java.util.List;

public class nhanvienAdapter extends ArrayAdapter<nhanvien> {

    private Activity activity;
    private int resource;
    private List<nhanvien> objects;

    public nhanvienAdapter(@NonNull Activity activity, int resource, @NonNull List<nhanvien> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View view =inflater.inflate(resource,null);

        TextView fullname = view.findViewById(R.id.fullname);
        TextView employeecode = view.findViewById(R.id.employeecode);
        TextView positions = view.findViewById(R.id.positions); // chuc vu
        ImageView imgstaff = view.findViewById(R.id.img_staff);

        nhanvien nv = this.objects.get(position);

        imgstaff.setImageResource(R.drawable.ic_launcher_background);
        fullname.setText(nv.getFullName());
        employeecode.setText(nv.getId());
        positions.setText(nv.getPositions());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity,""+(float)position+1,Toast.LENGTH_LONG).show();
            }
        });


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(activity,"Nhan du",Toast.LENGTH_LONG).show();
                return false;
            }
        });

        return view;
    }
}
