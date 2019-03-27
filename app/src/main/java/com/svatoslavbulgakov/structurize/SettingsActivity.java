package com.svatoslavbulgakov.structurize;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {
    public static final int REQUEST_AVATAR = 1;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private Uri imagePath;
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
        initFireBaseComponents();
        initToolBar();
        initTextView();
        initButton();
        initCircleImageView();
    }

    private void initFireBaseComponents() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }


    private void initTextView() {
        userLogin = findViewById(R.id.userLogin);
        userLogin.setText(UserData.getUserName());
        userEmail = findViewById(R.id.userEmail);
        userEmail.setText(UserData.getUserEmail());
    }

    private void initCircleImageView() {
        userImage = findViewById(R.id.content_settings_profile_image);
        downloadImage();
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
                imagePath = data.getData();
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        if(imagePath != null)
        {
            StorageReference ref = storage.getReferenceFromUrl("gs://structurize-b3c41.appspot.com/" + UserData.getUserEmail());
            ref.putFile(imagePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            downloadImage();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this, "Failed upload", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void downloadImage(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://structurize-b3c41.appspot.com/" + UserData.getUserEmail());

        try {
            final File localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Picasso.with(SettingsActivity.this).load(String.valueOf(localFile.toURI())).into(userImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(SettingsActivity.this, "Failed download", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Log.wtf("exception", "error");
        }
    }

    /*public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }*/

}
