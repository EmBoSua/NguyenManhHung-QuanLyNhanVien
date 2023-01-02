package vn.embosua.ltddquanlynhanvien.ui.register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.agconnect.auth.AGConnectUser;

import vn.embosua.ltddquanlynhanvien.R;

public class CreateProfileActivity extends AppCompatActivity {

    private AGConnectUser user;
    private String admin = "0admin";

    EditText fullName, dateOfBirth, numberPhone, email, cmnd,
            positions, coefficientsSalary;
    Button clear, next;
    RadioGroup radioGroup;
    RadioButton yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        // set no actionbar va no statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        getViews();
        getEvents();
    }

    private void getEvents() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAdmin();
                Intent intent = new Intent(CreateProfileActivity.this,CreateAccountActivity.class);
                intent.putExtra("fullName",fullName.getText().toString());
                intent.putExtra("dateOfBirth",dateOfBirth.getText().toString());
                intent.putExtra("numberPhone",numberPhone.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("cmnd",cmnd.getText().toString());
                intent.putExtra("admin",admin);
                intent.putExtra("positions",positions.getText().toString());
                intent.putExtra("coefficientsSalary",coefficientsSalary.getText().toString());
                startActivity(intent);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName.setText("");
                dateOfBirth.setText("");
                numberPhone.setText("");
                email.setText("");
                cmnd.setText("");
                positions.setText("");
                coefficientsSalary.setText("");
                no.setChecked(true);
            }
        });
    }

    private void checkAdmin(){
        int radioID = radioGroup.getCheckedRadioButtonId();
        if (radioID == R.id.rdo_Yes){
            admin = "1admin";
        }
    }

    private void getViews() {
        fullName = findViewById(R.id.edt_fullname);
        dateOfBirth = findViewById(R.id.edt_DateOfBirth);
        numberPhone = findViewById(R.id.edt_NumberPhone);
        email = findViewById(R.id.edt_Email);
        cmnd = findViewById(R.id.edt_CMND);
        positions = findViewById(R.id.edt_Positions);
        coefficientsSalary = findViewById(R.id.edt_CoefficientsSalary);

        radioGroup = findViewById(R.id.rdo_Group);
        yes = findViewById(R.id.rdo_Yes);
        no = findViewById(R.id.rdo_No);

        next = findViewById(R.id.bt_Next);
        clear = findViewById(R.id.bt_Clear);
    }
}