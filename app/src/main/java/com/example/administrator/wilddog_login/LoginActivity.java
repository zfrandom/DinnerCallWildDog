package com.example.administrator.wilddog_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;
import com.wilddog.wilddogauth.core.result.AuthResult;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    private WilddogAuth mAuth;
    private EditText mPassword;
    private Button mRegister;
    private Button mSigninBtn;
    private CheckBox mRmCheckBox;
    private AutoCompleteTextView mEmailEditText;
    private static String TAG = "LoginActivity";
    private Button mForgetPwdBtn;
    public static String FORGETPWD ="forget";
    public static int FORGETPASSWORD_CODE=123;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("wilddog website").build();
        WilddogApp.initializeApp(this, options);
        mAuth = WilddogAuth.getInstance();
        mForgetPwdBtn = (Button) findViewById(R.id.forget_pwd_cb);
        mPassword = (EditText) findViewById(R.id.password);
        mEmailEditText = (AutoCompleteTextView) findViewById(R.id.email);
        mRmCheckBox=(CheckBox) findViewById(R.id.rmb_user);
        mSigninBtn = (Button) findViewById(R.id.login_continue_btn);
        mSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  email = mEmailEditText.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete( Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail", task.getException());
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if(mRmCheckBox.isChecked()){
                                        Shared.putBoolean(getApplicationContext(), Shared.DEFINE_IS_REMEMBER, true);
                                        Shared.putString(getApplicationContext(), Shared.DEFINE_EMAIL, email);
                                        Shared.putString(getApplicationContext(), Shared.DEFINE_PASSWORD, password);
                                    }
                                }
                            }
                        });
            }
        });
        mRegister = (Button) findViewById(R.id.email_register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
        mRmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    Shared.putBoolean(getApplicationContext(), Shared.DEFINE_IS_REMEMBER, false);
                }
            }
        });
        if(Shared.getBoolean(getApplicationContext(), Shared.DEFINE_IS_REMEMBER)){
            mEmailEditText.setText(Shared.DEFINE_EMAIL);
            mPassword.setText(Shared.DEFINE_PASSWORD);
            mRmCheckBox.setChecked(true);
        }
        mForgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, resetPwdFragment.class);
                Serializable a = (Serializable) mAuth;
                i.putExtra(FORGETPWD,a );
                startActivityForResult(i, FORGETPASSWORD_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FORGETPASSWORD_CODE){
            if(requestCode == RESULT_OK){
                Toast.makeText(LoginActivity.this, "The password reset email is sent", Toast.LENGTH_LONG).show();

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
