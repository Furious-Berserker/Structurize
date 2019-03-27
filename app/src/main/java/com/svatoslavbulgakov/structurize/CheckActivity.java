package com.svatoslavbulgakov.structurize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CheckActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        this.mAuth = FirebaseAuth.getInstance();
        this.currentUser = mAuth.getCurrentUser();
        checkData();
    }

    private void checkData() {
        if (currentUser != null){
            Intent intent = new Intent(CheckActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(CheckActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
