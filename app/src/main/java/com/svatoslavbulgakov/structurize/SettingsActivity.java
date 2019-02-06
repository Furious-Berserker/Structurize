package com.svatoslavbulgakov.structurize;

import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    public static final int REQUEST_AVATAR = 1;

    private EditText editTextLogin, editTextNewLogin, editTextPassword, editTextNewPassword;
    private CircleImageView userImage;
    private boolean isEmailIncorrect, isPasswordIncorrect;
    private TextInputLayout textInputLayoutLogin, textInputLayoutNewLogin, textInputLayoutPassword,  textInputLayoutNewPassword;
    private Button exitButton, changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    private void initComponents() {
        initBoolean();
        initToolBar();
        initFloatingActionButton();
        initButton();
        //initTextInputLayout();
        initEditText();
        initCircleImageView();
    }

    private void initCircleImageView() {
        userImage = findViewById(R.id.content_settings_profile_image);
        Picasso.with(this).load(UserData.getUserImage()).error(R.drawable.ic_launcher_background).resize(100,100).into(userImage);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_AVATAR);
            }
        });
    }

    private void initBoolean() {
        isEmailIncorrect = true;
        isPasswordIncorrect = true;
    }

    /*private void initTextInputLayout() {
        textInputLayoutLogin = findViewById(R.id.textInputLayoutLogin);
        textInputLayoutNewLogin = findViewById(R.id.textInputLayoutNewLogin);

        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutNewPassword = findViewById(R.id.textInputLayoutNewPassword);
    }*/

    private void initEditText() {
        /*editTextLogin = findViewById(R.id.editTextChangeLogin);
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

        editTextNewLogin = findViewById(R.id.editTextNewLogin);
        editTextNewLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

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

        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
*/
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_AVATAR){
                final Uri uri = data.getData();

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest photoProfileChange = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
                user.updateProfile(photoProfileChange).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Picasso.with(SettingsActivity.this).load(uri).resize(100, 100).into(userImage);
                        }
                        else
                            Toast.makeText(SettingsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
