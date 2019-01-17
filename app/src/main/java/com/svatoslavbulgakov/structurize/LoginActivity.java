package com.svatoslavbulgakov.structurize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String APP_USER_DATA = "userData";

    public static final String APP_PREFERENCES_MAIL = "Mail";
    public static final String APP_PREFERENCES_LOGIN = "Login";
    public static final String APP_PREFERENCES_PASSWORD = "Password";
    public static final String APP_PREFERENCES_AUTH_EXIST = "Authorization Exist";

    private FirebaseAuth mAuth;
    private SharedPreferences userData;
    private EditText editTextMail, editTextLogin, editTextPassword;
    //private TextInputLayout mailInputLayout, loginInputLayout, passwordInputLayout;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void initComponents() {
        mAuth = FirebaseAuth.getInstance();
        initSharedPreferences();
        initToolBar();
        initEditText();
        //initTextInputLayout();
        initButton();
    }

    private void initSharedPreferences() {
        userData = getSharedPreferences(APP_USER_DATA, Context.MODE_PRIVATE);
    }

    /*private void initTextInputLayout() {
        mailInputLayout = findViewById(R.id.mailInputLayout);
        loginInputLayout = findViewById(R.id.loginInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
    }*/
    private void signUpUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Succses! " + user, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, CheckActivity.class);
                            startActivity(intent);

                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initButton() {
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = editTextLogin.getText().toString();
                String mail = editTextMail.getText().toString();
                String password = editTextPassword.getText().toString();

                /*SharedPreferences.Editor editor = userData.edit();
                editor.putString(APP_PREFERENCES_MAIL, mail);
                editor.putString(APP_PREFERENCES_LOGIN, login);
                editor.putString(APP_PREFERENCES_PASSWORD, password);
                editor.apply();*/

                if (!TextUtils.emailChecker(mail) || !TextUtils.passwordChecker(password, 6)) {
                    Toast.makeText(LoginActivity.this, "Email/Password Incorrect!", Toast.LENGTH_SHORT).show();
                    return;
                }

                signUpUser(mail, password);

            }
        });
    }

    private void initEditText() {
        editTextMail = findViewById(R.id.editTextMail);
        editTextMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextMail.getText().toString();
                if (!(TextUtils.emailChecker(text))) {
                    editTextMail.setError("Incorrect Mail!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = editTextPassword.getText().toString();
                if (!(TextUtils.passwordChecker(text, 6))) {
                    editTextPassword.setError("Password");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextLogin = findViewById(R.id.editTextLogin);
    }


    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
