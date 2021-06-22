package com.nodz.datasafe.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nodz.datasafe.DBhelper;
import com.nodz.datasafe.Model.InfoModel;
import com.nodz.datasafe.R;

import java.util.ArrayList;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.customViewHolder> {

    Context context;
    ArrayList<InfoModel> list;

    public InfoAdapter(Context context, ArrayList<InfoModel> list) {
        this.context = context;
        this.list = list;
    }

    public InfoAdapter() {
    }

    @NonNull
    @Override
    public customViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sample_item, parent, false);
        return new customViewHolder(v);
    }

    @Override
    public void onBindViewHolder(InfoAdapter.customViewHolder holder, int position) {
        InfoModel model = list.get(position);

        holder.appname.setText("Appname " + model.getAppname());
        holder.password.setText("Password " + model.getPassword());
        holder.username.setText("Username " + model.getUsername());
        holder.username.setVisibility(View.INVISIBLE);
        holder.password.setVisibility(View.INVISIBLE);

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Username "+model.getUsername())
                        .setMessage("Password "+model.getPassword())
                        .setCancelable(true)
                        .setPositiveButton("Copy username", R.drawable.ic_copy, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                ClipData clip = ClipData.newPlainText("Username", model.getUsername());
                                clipboard.setPrimaryClip(clip);
                            }
                        })
                        .setNegativeButton("copy password", R.drawable.ic_copy, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                ClipData clip = ClipData.newPlainText("Password", model.getPassword());
                                clipboard.setPrimaryClip(clip);
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
            }
            });

        DBhelper helper = new DBhelper(context);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                        .setTitle("Delete data for this site")
                        .setMessage("Your data will be deleted ")
                        .setCancelable(true)
                        .setPositiveButton("Yes delete", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                list.remove(position);
                                helper.deleteData(model.getAppname(),model.getPassword());
                                notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("No ", R.drawable.ic_baseline_not_interested_24, new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .build();
                // Show Dialog
                mDialog.show();
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class customViewHolder extends RecyclerView.ViewHolder {

        TextView appname, username, password;

        public customViewHolder(View itemView) {
            super(itemView);
            appname = itemView.findViewById(R.id.tvAppname);
            username = itemView.findViewById(R.id.tvUsername);
            password = itemView.findViewById(R.id.tvPassword);

        }
    }

}
