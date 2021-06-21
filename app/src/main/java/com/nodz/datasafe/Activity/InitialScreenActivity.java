package com.nodz.datasafe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nodz.datasafe.R;
import com.nodz.datasafe.databinding.ActivityInitialScreenBinding;


public class InitialScreenActivity extends AppCompatActivity {

    ActivityInitialScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInitialScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        ViewCompat.setBackgroundTintList(binding.addPassword,
                ContextCompat.getColorStateList(this, R.color.saveBtn));

        ViewCompat.setBackgroundTintList(binding.viewPassword,
                ContextCompat.getColorStateList(this, R.color.saveBtn));

        binding.addPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InitialScreenActivity.this, MainActivity.class));
            }
        });

        binding.viewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InitialScreenActivity.this, ViewDataActivity.class));
            }
        });

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



}