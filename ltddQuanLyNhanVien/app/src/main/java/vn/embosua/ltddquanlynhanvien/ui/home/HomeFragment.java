package vn.embosua.ltddquanlynhanvien.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;

import vn.embosua.ltddquanlynhanvien.QRCode.GenCodeActivity;
import vn.embosua.ltddquanlynhanvien.QRCode.ScanCodeActivity;
import vn.embosua.ltddquanlynhanvien.R;
import vn.embosua.ltddquanlynhanvien.databinding.FragmentHomeBinding;

import static com.huawei.hms.hmsscankit.ScanUtil.RESULT;

public class HomeFragment extends Fragment {
    private  View mView;

    Button gencode, scancode;

    private static final int CAMERA_REQ_CODE = 111;
    private static final int REQUEST_CODE_SCAN_ONE = 0X01;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        getViews();

        getControls();

        return mView;
    }

    private void getControls() {
        gencode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateImageQRCode();
            }
        });

        scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanQRCode();
            }
        });
    }

    private void ScanQRCode() {
        Intent intent = new Intent(getContext(), ScanCodeActivity.class);
        startActivity(intent);
    }


    private void CreateImageQRCode() {
        Intent intent = new Intent(getContext(), GenCodeActivity.class);
        startActivity(intent);
    }

    private void getViews() {
        gencode = mView.findViewById(R.id.bt_gendode);
        scancode = mView.findViewById(R.id.bt_scan);
    }

}