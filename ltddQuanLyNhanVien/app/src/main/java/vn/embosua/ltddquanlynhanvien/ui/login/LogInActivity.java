package vn.embosua.ltddquanlynhanvien.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;

import vn.embosua.ltddquanlynhanvien.R;
import vn.embosua.ltddquanlynhanvien.ui.forgotpassword.ForgetPasswordActivity;
import vn.embosua.ltddquanlynhanvien.ui.register.CreateProfileActivity;

public class LogInActivity extends AppCompatActivity {

    private String uID, email, password;

    Button btLogin;
    TextView txtCreate, txtForgotPass;
    EditText edtEmail, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // set no actionbar va no statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getViews();

        getcontrols();
    }

    private void getcontrols() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                password = edtPass.getText().toString();
                signInID(email, password);
            }
        });

        txtCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, CreateProfileActivity.class);
                startActivity(intent);
            }
        });

        txtForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getViews() {
        btLogin = findViewById(R.id.bt_login);
        txtCreate = findViewById(R.id.txt_lgCreate);
        txtForgotPass = findViewById(R.id.txt_lgfogotPassword);
        edtEmail = findViewById(R.id.edt_lgEmail);
        edtPass = findViewById(R.id.edt_lgPass);
    }

    private void signInID(String emai, String password) {
        AGConnectAuthCredential credential = EmailAuthProvider.credentialWithPassword(emai, password);
        AGConnectAuth.getInstance()
                .signIn(credential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        // Obtain sign-in information.
                        callMainActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e("login error: ", e.getMessage());
                    }
                });

    }

    private void callMainActivity() {
        Intent intent1 = new Intent(LogInActivity.this, MainScreenActivity.class);
        startActivity(intent1);
        finish();
    }

}