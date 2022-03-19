package com.example.appsselfhy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        final EditText emailID = (EditText) findViewById(R.id.emailEt);
        final EditText password = (EditText) findViewById(R.id.passwordEt);
        final Button btnSignup = (Button)findViewById(R.id.btnSignup);
        final TextView tvSignin = (TextView) findViewById(R.id.signIntv);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()){
                    emailID.setError("Please enter email");
                    emailID.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }

                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }

                else if (!(email.isEmpty() && pwd.isEmpty()) ){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this,"SignUp unsuccessful, Please try again",Toast.LENGTH_SHORT).show();
                            }
                            else {

                                startActivity(new Intent(SignUpActivity.this,MainActivity.class));


                            }
                        }
                    });

                }

                else {
                    Toast.makeText(SignUpActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }// tutup oncreate
}