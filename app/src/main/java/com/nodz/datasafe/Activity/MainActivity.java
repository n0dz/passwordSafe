package com.nodz.datasafe.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nodz.datasafe.DBhelper;
import com.nodz.datasafe.R;
import com.nodz.datasafe.databinding.ActivityMainBinding;

import dev.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DBhelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        // Applying Color to button
        ViewCompat.setBackgroundTintList(binding.saveData,
                ContextCompat.getColorStateList(this, R.color.saveBtn));

        //Log.i("DATA: ",appName+userName+password);
        dBhelper = new DBhelper(this);
        binding.saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("INFO",appName+userName);
                String user = binding.etUserName.getText().toString();
                String appname = binding.etAppName.getText().toString();
                String pass = binding.etPassWord.getText().toString();

                if (appname.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                    binding.etAppName.setError("Enter Website name");
                }

                boolean isChecked = dBhelper.checkAppExists(appname);
                if (!isChecked && !appname.isEmpty() && !user.isEmpty() && !pass.isEmpty()) {

                    boolean isInserted = dBhelper.insertData(appname, user, pass);
                    if (isInserted) {
                        Toast.makeText(MainActivity.this, "Inserted !!", Toast.LENGTH_LONG).show();
                        binding.etAppName.setText("");binding.etUserName.setText("");binding.etPassWord.setText("");

                        MaterialDialog mBottomSheetDialog = new MaterialDialog.Builder(MainActivity.this)
                                .setTitle("Inserted Data")
                                .setMessage("Username \t"+user+"\n"+"Password \t"+pass)
                                .setCancelable(true)
                                .build();

                        // Show Dialog
                        mBottomSheetDialog.show();


                    } else
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Data For "+appname+" Exists!!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewDataActivity.class));
            }
        });

    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
/*
                    startActivity(new Intent(MainActivity.this, ViewDataActivity.class)
                            .putExtra("appname",appName).putExtra("user",userName)
                            .putExtra("pass",password))
 */