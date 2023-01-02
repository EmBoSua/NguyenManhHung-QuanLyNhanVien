package vn.embosua.ltddquanlynhanvien.QRCode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.hmsscankit.WriterException;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import vn.embosua.ltddquanlynhanvien.R;

public class GenCodeActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    Button gen,save;
    ImageView imgCode;
    EditText edtNhap;

    Bitmap resultImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_code);

        int permission = ActivityCompat.checkSelfPermission(GenCodeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    GenCodeActivity.this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        gen = findViewById(R.id.gen);
        save = findViewById(R.id.save);
        imgCode = findViewById(R.id.imageCode);
        edtNhap = findViewById(R.id.edtNhap);

        gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCodeBtnClick(v);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCodeBtnClick(v);
            }
        });
    }

    private void saveCodeBtnClick(View v) {
        if (resultImage == null) {
            Toast.makeText(GenCodeActivity.this, "Please generate barcode first!", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            String fileName = System.currentTimeMillis() + ".jpg";
            String storePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            File appDir = new File(storePath);
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            File file = new File(appDir, fileName);

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            boolean isSuccess = resultImage.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Uri uri = Uri.fromFile(file);
            GenCodeActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                Toast.makeText(GenCodeActivity.this, "Barcode has been saved locally", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(GenCodeActivity.this, "Barcode save failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("GenCode", Objects.requireNonNull(e.getMessage()));
            Toast.makeText(GenCodeActivity.this, "Unkown Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateCodeBtnClick(View v) {
        String noidung = edtNhap.getText().toString().trim();
        if(noidung == "" || TextUtils.isEmpty(noidung)){
            Toast.makeText(getApplicationContext(),"Nhap noi dung",Toast.LENGTH_LONG).show();
            return;
        }
        try {
            //Generate the barcode.
            HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator().setBitmapMargin(1).setBitmapColor(Color.BLACK).setBitmapBackgroundColor(Color.WHITE).create();
            resultImage = ScanUtil.buildBitmap(noidung, HmsScan.QRCODE_SCAN_TYPE, 400, 400, options);
            imgCode.setImageBitmap(resultImage);
        } catch (WriterException e) {
            Toast.makeText(this, "Parameter Error!", Toast.LENGTH_SHORT).show();
        }
    }
}