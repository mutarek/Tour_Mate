package com.kazishihan.tourmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private EditText emailEt, passwordEt, confirmpassEt, fnameET, lnameEt;
    private Button signUp;
    private ImageView imageView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEt = findViewById(R.id.emailSignUpEtId);
        passwordEt = findViewById(R.id.passwordSignUpEtId);
        confirmpassEt = findViewById(R.id.confirmpasswordSignUpEtId);
        fnameET = findViewById(R.id.firtnameSignUpEtId);
        lnameEt = findViewById(R.id.lastnameSignUpEtId);
        signUp = findViewById(R.id.SignUpbtnId);
        imageView = findViewById(R.id.cameraSignUpIvId);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = fnameET.getText().toString();
                String lastName = lnameEt.getText().toString();
                String email = emailEt.getText().toString();
                String password = passwordEt.getText().toString();
                String confirmPassword = confirmpassEt.getText().toString();
                if (password.length() < 6) {
                    Toast.makeText(SignUp.this, "password would be upto 6 charecter", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")) {

                    Toast.makeText(SignUp.this, "All the fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (emailEt.getText().toString().trim().matches(emailPattern)) {

                    if (password.contains(confirmPassword)) {

                        signUpWithEmailAndPassword(firstName, lastName, email, password);


                    } else {

                        Toast.makeText(SignUp.this, "password not match", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(SignUp.this, "Invalid Email address", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }






    private void signUpWithEmailAndPassword(final String firstName, String lastName, String email, final String password) {

        final User user = new User(firstName, lastName, email);

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    user.setUserId(userId);

                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("UserList").child(userId);

                    databaseReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                emailEt.setText("");
                                passwordEt.setText("");
                                confirmpassEt.setText("");
                                fnameET.setText("");
                                lnameEt.setText("");
                                Toast.makeText(SignUp.this, "Sign Up success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUp.this, "Sign Up not success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (data != null) {
            if (requestCode == 0) {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);

            }

        }
    }
}
