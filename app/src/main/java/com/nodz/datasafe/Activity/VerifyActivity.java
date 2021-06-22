package com.nodz.datasafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.nodz.datasafe.databinding.ActivityVerifyBinding;

import java.util.concurrent.Executor;

public class VerifyActivity extends AppCompatActivity {

    ActivityVerifyBinding binding;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //buttonAuthenticate();
        
        executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(VerifyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                 
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(VerifyActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(VerifyActivity.this,InitialScreenActivity.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(VerifyActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Authentication")
                .setNegativeButtonText("Cancel")
                .setConfirmationRequired(false)
                .build();


        BiometricManager biometricManager = BiometricManager.from(this);
        if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS){

            Toast.makeText(VerifyActivity.this, "Biometric Not supported!!", Toast.LENGTH_SHORT).show();
            return;
        }
        biometricPrompt.authenticate(promptInfo);
    }
}