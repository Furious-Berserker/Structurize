package com.svatoslavbulgakov.structurize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logButton, authButton;
    private EditText editTextLogin, editTextPassword;
    private SharedPreferences sharedPreferences;

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
        mAuth = FirebaseAuth.getInstance();
        initSharedPreferences();
        initToolBar();
        initFloatingActionButton();
        initEditText();
        initButton();
    }

    private void initEditText() {
        editTextLogin = findViewById(R.id.signUpEditTextLogin);
        editTextPassword = findViewById(R.id.signUpEditTextPassword);
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(SignUpActivity.APP_USER_DATA, Context.MODE_PRIVATE);
    }

    private void initButton() {
        logButton = findViewById(R.id.logButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!(login.equals("") && password.equals(""))) {

                    /*if (sharedPreferences.getString(SignUpActivity.APP_PREFERENCES_LOGIN, "§").equals(login) && sharedPreferences.getString(SignUpActivity.APP_PREFERENCES_PASSWORD, "§").equals(password)) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(SignUpActivity.APP_PREFERENCES_AUTH_EXIST, true);
                        editor.putString(SignUpActivity.APP_PREFERENCES_LOGIN, login);
                        editor.putString(SignUpActivity.APP_PREFERENCES_PASSWORD, password);
                        editor.apply();

                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toasty.error(SignInActivity.this, "Incorrect login or password!").show();*/

                    signInUser(login, password);

                } else
                    Toasty.error(SignInActivity.this, "Incorrect Login or password!").show();
            }
        });
        authButton = findViewById(R.id.authorizationButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignInActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                    finish();

                }


            }
        });
    }

    private void initFloatingActionButton() {
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
