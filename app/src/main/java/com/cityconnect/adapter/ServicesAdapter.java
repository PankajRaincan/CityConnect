package com.cityconnect.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityconnect.R;
import com.cityconnect.model.AddService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by raincan on 25/1/17.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<AddService> announcements;
    private LayoutInflater layoutInflater;

    public ServicesAdapter(Activity activity, ArrayList<AddService> announcements) {
        this.activity = activity;
        this.announcements = announcements;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item_layout, parent, false);

        return new ServicesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServicesAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        final AddService announcement = announcements.get(position);

        holder.tvTitle.setText("Name: " + announcement.getName()
                + "\nMobile: " + announcement.getMobile()
                + "\nEmail: " + announcement.getEmail()
                + "\nDOB: " + announcement.getDob()
                + "\nAddress: " + announcement.getAddress1()
                + "\nDescription: " + announcement.getDesc()
                + "\nLatitude: " + announcement.getLatitude()
                + "\nLongitude: " + announcement.getLongitude());


        Picasso.with(activity)
                .load(announcement.getImage())
                .placeholder(R.drawable.logo)
                .into(holder.shareImageView);

//        Log.v("URL",""+announcement.getImage());


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView shareImageView;

        public ViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.content);
            shareImageView = view.findViewById(R.id.profile);


        }
    }
}