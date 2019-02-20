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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    public static final int REQUEST_AVATAR = 1;

    private CircleImageView userImage;
    private TextView userLogin, userEmail;
    private Button exitButton, changeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initComponents();
    }

    private void initComponents() {
        initToolBar();
        initTextView();
        initButton();
        initCircleImageView();
    }

    private void initTextView() {
        userLogin = findViewById(R.id.userLogin);
        userLogin.setText(UserData.getUserName());
        userEmail = findViewById(R.id.userEmail);
        userEmail.setText(UserData.getUserEmail());
    }

    private void initCircleImageView() {
        userImage = findViewById(R.id.content_settings_profile_image);
        Picasso.with(this).load(UserData.getUserImage()).error(R.drawable.ic_launcher_background).resize(100,100).into(userImage);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_AVATAR);
            }
        });
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
