package com.svatoslavbulgakov.structurize;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private CustomLayout customLayout;
    private EditText editTextMail, editTextLogin, editTextPassword;
    private Button createAccountButton;
    private boolean isEmailIncorrect, isPasswordIncorrect, isLoginIncorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private void initComponents() {
        this.mAuth = FirebaseAuth.getInstance();
        initCustomLayout();
        initBoolean();
        initToolBar();
        initEditText();
        initButton();
    }

    private void initCustomLayout() {
        customLayout = findViewById(R.id.signUpCustomLayout);
        Picasso.with(this).load(R.drawable.forest).into(customLayout);
    }

    private void initBoolean() {
        isLoginIncorrect = true;
        isEmailIncorrect = true;
        isPasswordIncorrect = true;
    }

    private void signUpUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String name = editTextLogin.getText().toString();
                            if (!isLoginIncorrect) {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                UserProfileChangeRequest nameProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                                user.updateProfile(nameProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Intent intent = new Intent(SignUpActivity.this, CheckActivity.class);
                                            startActivity(intent);

                                            Toast.makeText(SignUpActivity.this, "account created", Toast.LENGTH_SHORT).show();

                                            finish();
                                        } else
                                            Toast.makeText(SignUpActivity.this, "Failed to create account!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if(e instanceof FirebaseAuthUserCollisionException)
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initButton() {
        createAccountButton = findViewById(R.id.button);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = editTextMail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!isLoginIncorrect && !isEmailIncorrect && !isPasswordIncorrect)
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
                    isEmailIncorrect = true;
                    editTextMail.setError("Incorrect Mail!");
                } else
                    isEmailIncorrect = false;
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
                    isPasswordIncorrect = true;
                    editTextPassword.setError("incorrect Password ");
                } else
                    isPasswordIncorrect = false;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = editTextLogin.getText().toString();
                if (text.isEmpty()){
                    isLoginIncorrect = true;
                    editTextLogin.setError("incorrect login");
                } else
                    isLoginIncorrect = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
