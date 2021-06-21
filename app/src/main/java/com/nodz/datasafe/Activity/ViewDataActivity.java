package com.nodz.datasafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.nodz.datasafe.DBhelper;
import com.nodz.datasafe.Adapter.InfoAdapter;
import com.nodz.datasafe.Model.InfoModel;
import com.nodz.datasafe.R;
import com.nodz.datasafe.databinding.ActivityHomeBinding;

import java.util.ArrayList;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class ViewDataActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    DBhelper helper;
    ArrayList<InfoModel> list;
    InfoAdapter infoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        helper = new DBhelper(this);
        list = helper.getData();

        //appName = getIntent().getStringExtra("appname");userName = getIntent().getStringExtra("user");password = getIntent().getStringExtra("pass");
        infoAdapter = new InfoAdapter(this, list);

        binding.recyclerViewHome.setAdapter(infoAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //GridLayoutManager manager = new GridLayoutManager(this,2);
        binding.recyclerViewHome.setLayoutManager(manager);

        /*
        binding.goToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewDataActivity.this, MainActivity.class));
            }
        });*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addNewPass:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                break;

            case R.id.exitMenu:
                //finishAffinity();
                MaterialDialog mDialog = new MaterialDialog.Builder(ViewDataActivity.this)
                        .setTitle("Are You Sure You Want to Exit?")
                        .setMessage("Doing this will Exit the App")
                        .setCancelable(true)
                        .setPositiveButton("Yes Exit", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                finishAffinity();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No Stay", R.drawable.ic_baseline_not_interested_24, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                // Show Dialog
                mDialog.show();
                break;
        }
        return true;
    }

        @Override
        public boolean onKeyDown ( int keyCode, KeyEvent event){
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }

            return super.onKeyDown(keyCode, event);
        }

        @Override
        public void startActivity (Intent intent){
            super.startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

        @Override
        public void finish () {
            super.finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


    }