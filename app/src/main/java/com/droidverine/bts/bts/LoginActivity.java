package com.droidverine.bts.bts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    EditText emailedt, passwordedt;
    Button loginbtn;
    TextView createtxt,signuptxt;
    String email, password;


    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        signuptxt=findViewById(R.id.signuptxt);
        if (auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
        emailedt = findViewById(R.id.email);
        passwordedt = findViewById(R.id.password);
        loginbtn = findViewById(R.id.login_btn);
        createtxt = findViewById(R.id.fgtpsstxt);
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));

            }
        });
        createtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ForgotpasswordActivity.class));
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailedt.getText().toString();
                password = passwordedt.getText().toString();
                authenticate();
            }
        });

    }

    public void authenticate() {
        //authenticate user
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        //  progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                //    inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                           // finish();
                        }
                    }
                });
    }
}
