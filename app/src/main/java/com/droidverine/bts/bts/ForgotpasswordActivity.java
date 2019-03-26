package com.droidverine.bts.bts;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotpasswordActivity extends AppCompatActivity {
FirebaseAuth auth;
String email;
EditText emailedt;
Button forgotbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        auth=FirebaseAuth.getInstance();
        emailedt=findViewById(R.id.forgotemail);
        forgotbtn=findViewById(R.id.forgot_btn);
        forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailedt.getText().toString();
                forgotpass();
            }
        });
    }
    public void forgotpass()
    {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotpasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotpasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                       // progressBar.setVisibility(View.GONE);
                    }
                });
    }
}
