package com.example.appsselfhy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.appsselfhy.MESSAGE";


    ImageButton imageBtn;
    ImageButton galeryBtn;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        final EditText emailID = (EditText) findViewById(R.id.emailEt);
        final EditText password = (EditText) findViewById(R.id.passwordEt);
        final Button btnLogin = (Button)findViewById(R.id.btnLogin);
        final TextView tvSignup = (TextView) findViewById(R.id.signUptv);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser =  mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(MainActivity.this,"You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent =  new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(MainActivity.this,"Please Login", Toast.LENGTH_SHORT).show();

                }

            }

        };// tutup authlistener

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if (email.isEmpty()){
                    emailID.setError("Please enter your email");
                    emailID.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }

                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }

                else if (!(email.isEmpty() && pwd.isEmpty()) ){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login error! Please login again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });

                }

                else {
                    Toast.makeText(MainActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });
        // tutup login

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });// tutup tv sign up




        // image multimedia
        imageBtn = findViewById(R.id.copyButton);
        imageBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ImageActivity.class);
                startActivity(intent);
            }
        });

        // image

        // galery
        galeryBtn = findViewById(R.id.btnGallery);
        galeryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });





    }// tutup oncreate





}