package vn.embosua.ltddquanlynhanvien.ui.register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

import java.util.Calendar;

import vn.embosua.ltddquanlynhanvien.R;
import vn.embosua.ltddquanlynhanvien.modle.nhanvien;

import static com.huawei.agconnect.auth.VerifyCodeSettings.ACTION_REGISTER_LOGIN;

public class CreateAccountActivity extends AppCompatActivity {

    Button btclear,btcreate,btvercode;
    EditText edtemail,edtvercode,edtpass,edtcomfirmpass;

    private String uFullName, uDateOfBirth, uNumberPhone, uEmail, uCMND,
            uStartDay, uRoomCode, uPositions, uID;
    private String uAdmin;
    private String uCoefficientsSalary;
    String  uVerCode, uPass, uComfirmPass;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // set no actionbar va no statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getViews();
        getInforUser();

        edtemail.setText(uEmail);


        getControls();
    }

    private void getViews() {
        btclear = findViewById(R.id.bt_crClear);
        btcreate = findViewById(R.id.bt_Creeat);
        btvercode = findViewById(R.id.bt_crvercode);

        edtemail = findViewById(R.id.edt_crEmai);
        edtvercode = findViewById(R.id.edt_crVerCode);
        edtpass = findViewById(R.id.edt_crPass);
        edtcomfirmpass = findViewById(R.id.edt_crComfirmPass);
    }

    private void getControls() {
        btvercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendConfirmCode();
            }
        });

        btcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInforUser();
                creatWithEmail();
            }
        });

        btclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //String uFullName, uDateOfBirth, uNumberPhone, uEmail, uCMND,
    //            uStartDay, uRoomCode, uPositions, uID, uCoefficientsSalary, uAdmin;
    private void getInforUser() {
        intent = getIntent();
        uFullName = intent.getStringExtra("fullName");
        uDateOfBirth = intent.getStringExtra("dateOfBirth");
        uNumberPhone = intent.getStringExtra("numberPhone");
        uEmail = intent.getStringExtra("email");
        uCMND = intent.getStringExtra("cmnd");
        uStartDay = timeCreate();
        uRoomCode = intent.getStringExtra("roomCode");
        uPositions = intent.getStringExtra("positions");
        uCoefficientsSalary = intent.getStringExtra("coefficientsSalary");
        uAdmin = intent.getStringExtra("admin");
    }

    private String timeCreate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return day+"/"+(month+1)+"/"+year;
    }

    private void sendConfirmCode() {
        uEmail = edtemail.getText().toString().trim(); // email
        if (uEmail.isEmpty() || uEmail == null) {
            Toast.makeText(getApplicationContext(), "Email is empty or null ", Toast.LENGTH_LONG).show();
        } else {
            VerifyCodeSettings settings = VerifyCodeSettings.newBuilder()
                    .action(ACTION_REGISTER_LOGIN)   //ACTION_REGISTER_LOGIN/ACTION_RESET_PASSWORD
                    .sendInterval(30) // Minimum sending interval, which ranges from 30s to 120s.
                    .build();

            Task<VerifyCodeResult> task = AGConnectAuth.getInstance().requestVerifyCode(uEmail, settings);

            task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
                @Override
                public void onSuccess(VerifyCodeResult verifyCodeResult) {
                    //The verification code application is successful.
                    Toast.makeText(CreateAccountActivity.this, "Login success", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(CreateAccountActivity.this, "Login fail: " + e, Toast.LENGTH_LONG).show();
                    Log.e("mytag", "fail: " + e);
                }
            });
        }
    }

    private void creatWithEmail(){
        uVerCode = edtvercode.getText().toString();
        uPass = edtpass.getText().toString();
        uComfirmPass = edtcomfirmpass.getText().toString();
        if (uEmail.isEmpty() || uVerCode.isEmpty() || uPass.isEmpty() || uComfirmPass.isEmpty()) {

            Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_LONG).show();

        } else if (!uPass.equals(uComfirmPass)){ //so sanh password. neu giong tra ve true
            Toast.makeText(getApplicationContext(), "Passwords are not the same!", Toast.LENGTH_LONG).show();
        }else {
            EmailUser emailUser = new EmailUser.Builder().setEmail(uEmail) // dat ca tham so cho emailuser
                    .setVerifyCode(uVerCode)
                    .setPassword(uPass).build();

            AGConnectAuth.getInstance()
                    .createUser(emailUser)
                    .addOnCompleteListener(new OnCompleteListener<SignInResult>() {
                        @Override
                        public void onComplete(Task<SignInResult> task) {
                            if (task.isSuccessful()) {// neu thanh cong
                                AGConnectUser user = AGConnectAuth.getInstance().getCurrentUser();
                                uID = user.getUid();
                                //insert in realtime db firebase
                                nhanvien nv = new nhanvien(uFullName, uDateOfBirth,uNumberPhone,uEmail, uCMND,
                                        uStartDay,uPositions,uID, uCoefficientsSalary,uAdmin);

                                // save on firebase
                                insContactDB(nv, uID);

                                callMainActivity();

                                finish();//close signup form
                            } else {
                                Log.d("SignUpErr", task.getException().getMessage());
                                Toast.makeText(CreateAccountActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void callMainActivity(){ // chuyen sao mainactivity
        Intent intent = new Intent(CreateAccountActivity.this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }

    private void insContactDB(nhanvien nv, String uID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("dbUser").child(uID).setValue(nv);
    }
}