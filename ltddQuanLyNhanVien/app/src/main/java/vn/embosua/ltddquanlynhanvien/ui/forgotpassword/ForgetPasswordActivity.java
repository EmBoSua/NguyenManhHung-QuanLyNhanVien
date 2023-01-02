package vn.embosua.ltddquanlynhanvien.ui.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.agconnect.auth.EmailUser;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskExecutors;

import java.util.SplittableRandom;

import vn.embosua.ltddquanlynhanvien.R;
import vn.embosua.ltddquanlynhanvien.modle.nhanvien;
import vn.embosua.ltddquanlynhanvien.ui.login.LogInActivity;
import vn.embosua.ltddquanlynhanvien.ui.register.CreateAccountActivity;

import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_REGISTER_LOGIN;
import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_RESET_PASSWORD;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button continues, send, back, back2;

    EditText email, vercode, pass, cfpass;

    TextView tutoriol;

    LinearLayout recover, button;

    private String fpemail, fpvercode, fppass, fpcfpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getViews();

        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmCode();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setVisibility(View.VISIBLE);
                email.setText("");
                tutoriol.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                recover.setVisibility(View.GONE);
                email.setVisibility(View.VISIBLE);
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPasswordWithEemail();
            }
        });
    }

    private void getViews() {
        button = findViewById(R.id.fpSetVisibilityButton);
        send = findViewById(R.id.bt_fpSend);
        back = findViewById(R.id.bt_fpBack);
        back2 = findViewById(R.id.bt_fpBack2);
        continues = findViewById(R.id.bt_continue);
        email = findViewById(R.id.edt_fpEmai);
        vercode = findViewById(R.id.edt_fpVerCode);
        pass = findViewById(R.id.edt_fpPass);
        cfpass = findViewById(R.id.edt_fpComfirmPass);
        tutoriol = findViewById(R.id.fpSetVisibilityTutorial);
        recover = findViewById(R.id.fpSetVisibilityRecover);
    }

    private void sendConfirmCode(){
        fpemail = email.getText().toString().trim();
        if (fpemail == "" || TextUtils.isEmpty(fpemail)){
            Toast.makeText(getApplication(), "Nhập email đã đăng ký của bạn", Toast.LENGTH_SHORT).show();
            Log.e("mytest","email noi");
            return;
        }else {
            VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                    .action(ACTION_RESET_PASSWORD)   //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
                    .sendInterval(30) // Minimum sending interval, which ranges from 30s to 120s.
                    .build();

            Task<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(fpemail, settings);

            task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                @Override
                public void onSuccess(VerifyCodeResult verifyCodeResult) {
                    //The verification code application is successful.
                    Toast.makeText(ForgetPasswordActivity.this, "Da gui ma code ve email", Toast.LENGTH_LONG).show();
                    tutoriol.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                    recover.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ForgetPasswordActivity.this, "Tai khoan khong dung" + e, Toast.LENGTH_LONG).show();
                    Log.e("mytag", "fail: " + e);
                }
            });
        }
    }

    private void resetPasswordWithEemail(){
        fpvercode = vercode.getText().toString();
        fppass = pass.getText().toString();
        fpcfpass = cfpass.getText().toString();
        if (fpvercode.isEmpty() || fppass.isEmpty() || fpcfpass.isEmpty()) {

            Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_LONG).show();

        } else if (!fppass.equals(fpcfpass)){ //so sanh password. neu giong tra ve true
            Toast.makeText(getApplicationContext(), "Passwords are not the same!", Toast.LENGTH_LONG).show();
        }else {
//            AGConnectAuth.getInstance()
//                    .resetPassword(fpemail,fppass,fpvercode)
//                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    Toast.makeText(ForgetPasswordActivity.this,"Doi mat khau thanh cong", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(ForgetPasswordActivity.this, LogInActivity.class);
//                    startActivity(intent);
//                    finish();//close signup form
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(Exception e) {
//                    Log.d("FogotPasswordErr", e.getMessage());
//                    Toast.makeText(ForgetPasswordActivity.this,"Doi mat khau that bai: "+e.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            });


            Task<Void> task = AGConnectAuth.getInstance().resetPassword(fpemail,fppass,fpvercode);

            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ForgetPasswordActivity.this,"Doi mat khau thanh cong", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgetPasswordActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();//close signup form
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(ForgetPasswordActivity.this, "Login fail: " + e, Toast.LENGTH_LONG).show();
                    Log.e("mytag", "fail: " + e);
                }
            });
        }
    }
}