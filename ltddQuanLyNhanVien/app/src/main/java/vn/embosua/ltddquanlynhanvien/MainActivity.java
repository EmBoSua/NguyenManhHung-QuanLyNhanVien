package vn.embosua.ltddquanlynhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;

public class MainActivity extends AppCompatActivity {

    Button btLogOut;

    private AGConnectUser agConnectUser;
    private AGConnectAuth agConnectAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message3");
//
//        myRef.setValue("Hello, World!");

        btLogOut = findViewById(R.id.bt_mDeleteAc);
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AGConnectAuth.getInstance().signOut();
            }
        });
    }
}