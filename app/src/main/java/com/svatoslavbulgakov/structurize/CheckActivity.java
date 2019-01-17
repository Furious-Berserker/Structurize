package com.svatoslavbulgakov.structurize;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CheckActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        checkData();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    private void checkData() {
        if (currentUser != null){
            Toast.makeText(this, "ZBS!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(CheckActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
