package com.droidverine.bts.bts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
FirebaseAuth auth;
EditText emailedt,passwordedt;
Button signupbtn;
TextView existsacc;
EditText busnumedt,busnameedt;
String email,password;
FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth=FirebaseAuth.getInstance();
        emailedt=findViewById(R.id.signupemail);
        passwordedt=findViewById(R.id.signuppassword);
        signupbtn=findViewById(R.id.signup_btn);
        existsacc=findViewById(R.id.existacctxt);
        busnumedt=findViewById(R.id.busnumber);
        busnameedt=findViewById(R.id.busname);
        existsacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=emailedt.getText().toString();
                password=passwordedt.getText().toString();
                createuser();
            }
        });
    }
    public void createuser(){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                       // progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            insertdata();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();

                        }
                    }
                });
    }

    public void insertdata() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        myRef.child("Driver").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("bustitle").setValue(busnameedt.getText().toString());
                dataSnapshot.getRef().child("busnumber").setValue(busnumedt.getText().toString());
                dataSnapshot.getRef().child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                dataSnapshot.getRef().child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                dataSnapshot.getRef().child("lat").setValue("sfffsdf");
                dataSnapshot.getRef().child("lon").setValue("sfffsdf");


                Log.d("firebasevalueset","set");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }
}
