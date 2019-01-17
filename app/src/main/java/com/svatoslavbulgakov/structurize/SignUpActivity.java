package com.svatoslavbulgakov.structurize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button logButton, authButton;
    private EditText editTextLogin, editTextPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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
        sharedPreferences = getSharedPreferences(LoginActivity.APP_USER_DATA, Context.MODE_PRIVATE);
    }

    private void initButton() {
        logButton = findViewById(R.id.logButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!(login.equals("") && password.equals(""))) {

                    /*if (sharedPreferences.getString(LoginActivity.APP_PREFERENCES_LOGIN, "§").equals(login) && sharedPreferences.getString(LoginActivity.APP_PREFERENCES_PASSWORD, "§").equals(password)) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(LoginActivity.APP_PREFERENCES_AUTH_EXIST, true);
                        editor.putString(LoginActivity.APP_PREFERENCES_LOGIN, login);
                        editor.putString(LoginActivity.APP_PREFERENCES_PASSWORD, password);
                        editor.apply();

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                        Toasty.error(SignUpActivity.this, "Incorrect login or password!").show();*/

                    signInUser(login, password);

                } else
                    Toasty.error(SignUpActivity.this, "Incorrect Login or password!").show();
            }
        });
        authButton = findViewById(R.id.authorizationButton);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignUpActivity.this, "Success!", Toast.LENGTH_SHORT).show();

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
