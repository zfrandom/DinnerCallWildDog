package com.example.administrator.wilddog_login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wilddog.wilddogauth.WilddogAuth;
import com.wilddog.wilddogauth.core.Task;
import com.wilddog.wilddogauth.core.listener.OnCompleteListener;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/30.
 */
public class resetPwdFragment extends Fragment {
    private EditText email_tv;
    private Button send;
    private WilddogAuth mAuth;
    private Button cancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = (WilddogAuth) getActivity().getIntent().getSerializableExtra(LoginActivity.FORGETPWD);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reset_pwd_fragment, container);
        email_tv = (EditText) v.findViewById(R.id.resend_email);
        send = (Button) v.findViewById(R.id.resend_btn);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_tv.getText().toString();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if(task.isSuccessful()){
                            onActivityResult(LoginActivity.FORGETPASSWORD_CODE,getActivity().RESULT_OK, null);
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getActivity(), "The email can't be sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        cancel = (Button) v.findViewById(R.id.cancel_resend);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
    }
}
