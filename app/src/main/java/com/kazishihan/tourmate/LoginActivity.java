package com.kazishihan.tourmate;

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
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private TextView registationTv;
    private EditText emailEt, passwordEt;
    private Button signinBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registationTv = findViewById(R.id.SignUpTvId);
        emailEt = findViewById(R.id.signInEmailEtId);
        passwordEt = findViewById(R.id.signInPasswordEtId);
        signinBtn = findViewById(R.id.signInBtnId);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();


                if (email.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                } else {

                    if (emailEt.getText().toString().trim().matches(emailPattern)) {
                        signInWithEmailAndPassword(email, password);

                    } else {
                        Toast.makeText(LoginActivity.this, "Envalid Email", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


       registationTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(LoginActivity.this,SignUp.class);
               startActivity(intent);
           }
       });




    }

    private void signInWithEmailAndPassword(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
//                    String userId = firebaseAuth.getCurrentUser().getUid();
//                    showInformation(userId);
                }
            }
        });

    }
}
