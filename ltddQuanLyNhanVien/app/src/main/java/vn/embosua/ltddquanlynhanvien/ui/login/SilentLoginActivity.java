package vn.embosua.ltddquanlynhanvien.ui.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectUser;
import com.huawei.hmf.tasks.Continuation;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;

import vn.embosua.ltddquanlynhanvien.R;

public class SilentLoginActivity extends AppCompatActivity {

    private static final String TAG = "SilentLoginActivity";
    public String email;
    AGConnectUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent_login);

        // set no actionbar va no statusbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        user = AGConnectAuth.getInstance().getCurrentUser();

        if(user==null){
            silentlySignIn();
        } else{ email = user.getEmail(); }
/////////////////////////////

        if (email!=null) {
            Intent intent = new Intent(SilentLoginActivity.this, MainScreenActivity.class);
            startActivity(intent);
            Toast.makeText(SilentLoginActivity.this," Signing you In", Toast.LENGTH_LONG).show();
            finish();
        }else{
            Toast.makeText(SilentLoginActivity.this, "Sign-In to your account",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SilentLoginActivity.this, LogInActivity.class);// goi den loginactivity de dang nhap
            startActivity(intent);
            finish();
        }
    }

    private void silentlySignIn() {
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();
        AccountAuthService service = AccountAuthManager.getService(this, authParams);
        Task<AGConnectAuth> task = service.silentSignIn().continueWithTask(new Continuation<AuthAccount, Task<AGConnectAuth>>() {
            @Override
            public Task<AGConnectAuth> then(Task<AuthAccount> task) throws Exception {
                return null;
            }
        });
        task.addOnSuccessListener(new OnSuccessListener<AGConnectAuth>() {

            @Override
            public void onSuccess(AGConnectAuth agConnectAuth) {
                email = agConnectAuth.getCurrentUser().getEmail();
                Log.e(TAG, "user phone [" + email+"]");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("login error solent: ", e.getMessage());
            }
        });
    }
}