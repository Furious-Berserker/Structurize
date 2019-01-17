package com.svatoslavbulgakov.structurize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private EditText editTextLogin, editTextPassword;
    private boolean isEmailIncorrect, isPasswordIncorrect;
    private TextInputLayout textInputLayoutLogin, textInputLayoutPassword;
    private Button exitButton, changeButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    private void initComponents() {
        initBoolean();
        initSharedPreferences();
        initToolBar();
        initFloatingActionButton();
        initButton();
        initTextInputLayout();
        initEditText();
    }

    private void initBoolean() {
        isEmailIncorrect = true;
        isPasswordIncorrect = true;
    }

    private void initTextInputLayout() {
        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
    }

    private void initEditText() {
        editTextLogin = findViewById(R.id.editTextChangeLogin);
        editTextLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.emailChecker(editTextLogin.getText().toString())){
                    isEmailIncorrect = false;
                    textInputLayoutLogin.setErrorEnabled(false);
                } else {
                    if (editTextLogin.getText().toString().isEmpty()){
                        isEmailIncorrect = true;
                        textInputLayoutLogin.setErrorEnabled(false);
                        return;
                    }
                    isEmailIncorrect = true;
                    textInputLayoutLogin.setError("incorrect email");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editTextPassword = findViewById(R.id.editTextChangePassword);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.passwordChecker(editTextPassword.getText().toString(), 6)){
                    isPasswordIncorrect = false;
                    textInputLayoutPassword.setErrorEnabled(false);
                } else {
                    if (editTextPassword.getText().toString().isEmpty()){
                        isPasswordIncorrect = true;
                        textInputLayoutPassword.setErrorEnabled(false);
                        return;
                    }
                    isPasswordIncorrect = true;
                    textInputLayoutPassword.setError("incorrect password");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences(SignUpActivity.APP_USER_DATA, Context.MODE_PRIVATE);
    }

    private void initButton() {
        exitButton = findViewById(R.id.outButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(SettingsActivity.this, CheckActivity.class);
                startActivity(intent);

                finishAffinity();
            }
        });
        changeButton = findViewById(R.id.changeButton);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!isEmailIncorrect && !isPasswordIncorrect){
                    String email = editTextLogin.getText().toString();

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(SettingsActivity.this, "Email changed! Current email: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    String password = editTextPassword.getText().toString();

                    user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Toast.makeText(SettingsActivity.this, "password changed", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(SettingsActivity.this, "incorrect data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
