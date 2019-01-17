package com.svatoslavbulgakov.structurize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logButton, authButton;
    private EditText editTextLogin, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        initComponents();

    }

    private void initComponents() {
        this.mAuth = FirebaseAuth.getInstance();
        initToolBar();
        initEditText();
        initButton();
    }

    private void initEditText() {
        this.editTextLogin = findViewById(R.id.signUpEditTextLogin);
        this.editTextPassword = findViewById(R.id.signUpEditTextPassword);
    }


    private void initButton() {
        this.logButton = findViewById(R.id.logButton);
        this.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!(login.equals("") && password.equals(""))) {
                    signInUser(login, password);
                } else
                    Toasty.error(SignInActivity.this, "Incorrect Login or password!").show();
            }
        });
        this.authButton = findViewById(R.id.authorizationButton);
        this.authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signInUser(String email, String password){
        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    //Toast.makeText(SignInActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
