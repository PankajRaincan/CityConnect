package com.raincan.app.adapters.announcement;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.raincan.app.R;
import com.raincan.app.model.announcement.Announcement;
import com.raincan.app.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by raincan on 25/1/17.
 */

public class ReAnnouncementAdapter extends RecyclerView.Adapter<ReAnnouncementAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<Announcement> announcements;
    private LayoutInflater layoutInflater;

    public ReAnnouncementAdapter(Activity activity, ArrayList<Announcement> announcements) {
        this.activity = activity;
        this.announcements = announcements;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public ReAnnouncementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.from(parent.getContext())
                .inflate(R.layout.item_announcement, parent, false);

        return new ReAnnouncementAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReAnnouncementAdapter.ViewHolder holder, int position) {


        holder.setIsRecyclable(false);

        final Announcement announcement = announcements.get(position);
        if (null != announcement.getTag() && "" != announcement.getTag() && announcement.getTag().equalsIgnoreCase("poll")) {


            if (CommonUtils.isInternetAvailable(activity)) {
                holder.webView.setVisibility(View.VISIBLE);
                // circularProgressbar.setVisibility(View.VISIBLE);
                // noInternetConnectionView.setVisibility(View.GONE);
                holder.webView.clearHistory();
                holder.webView.clearCache(true);
                holder.webView.setWebViewClient(new myWebClient());
                holder.webView.getSettings().setJavaScriptEnabled(true);
                holder.webView.loadUrl("http://raincan.com/poll");

                //Log.d("dieturl",webView.getUrl());
            } else {
                // noInternetConnectionView.setVisibility(View.VISIBLE);
            }


        } else {
            holder.webView.setVisibility(View.GONE);
            holder.imagerelative.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(announcement.getTitle());
            holder.tvDesc.setText(announcement.getDescription());
            holder.tvDate.setText(CommonUtils.getDMYFormattedDate(announcement.getDate()));


            if (null != announcement.getImageurl() && "" != announcement.getImageurl() && " " != announcement.getImageurl()) {
                Picasso.with(activity)
                        .load(announcement.getImageurl())
                        .placeholder(R.mipmap.ic_raincan_icon_new)
                        .into(holder.imagerelative);
                // app.getImageLoader().displayImage(item.getImageUrl().replace(" ", "%20"), viewHolder.ivImage1);
            } else {
                holder.imagerelative.setVisibility(View.GONE);
            }
//            if (null != announcement.getLink() && "" != announcement.getLink() && " " != announcement.getLink()) {
//                holder.tvhyperlink.setText(Html.fromHtml(announcement.getLink()));
//            } else {
//                holder.tvhyperlink.setVisibility(View.GONE);
//            }
            if (null != announcement.getTag() && "" != announcement.getTag() && " " != announcement.getTag()) {
                holder.bttag.setText(announcement.getTag());
            } else {
                holder.bttag.setVisibility(View.GONE);
            }


            holder.shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
//                    Intent shareIntent = new Intent();
//                    shareIntent.setAction(Intent.ACTION_SEND);
//                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,announcement.getTitle());
//                    shareIntent.putExtra(Intent.EXTRA_TEXT,announcement.getTitle()+"\n"+announcement.getDescription() +" Try them out @ http://bit.ly/1BpxacX");
//                    shareIntent.setType("text/plain");
//                    activity.startActivity(Intent.createChooser(shareIntent, "Send To"));


                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, announcement.getTitle());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, announcement.getTitle() + "\n" + announcement.getDescription() + " Try them out @ " +
                            Html.fromHtml("<a href='" + announcement.getLink() + "'>" + announcement.getLink() + "</a>"));
                    shareIntent.setType("text/plain");
                    activity.startActivity(Intent.createChooser(shareIntent, "Send To"));

                }
            });

            holder.mCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if((null != announcement.getTag() && "" != announcement.getTag() && " " != announcement.getTag())){
//
//                    }
//
//

                    if (null != announcement.getLink() && "" != announcement.getLink() && " " != announcement.getLink() && announcement.getLink().contains("http")) {

                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(announcement.getLink()));
                        activity.startActivity(browserIntent);
                    }


                }
            });


//            holder.imagerelative.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if((null != announcement.getTag() && "" != announcement.getTag() && " " != announcement.getTag())){
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(announcement.getLink()));
//                        activity.startActivity(browserIntent);
//                    }
//
//                }
//            });
//
//            holder.tvDesc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if((null != announcement.getTag() && "" != announcement.getTag() && " " != announcement.getTag())){
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(announcement.getLink()));
//                        activity.startActivity(browserIntent);
//                    }
//
//                }
//            });
        }


    }


    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // circularProgressbar.setVisibility(View.GONE);
        }
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
        private TextView tvDesc;
        private TextView tvDate;
        private TextView tvhyperlink;
        private ImageView imagerelative;
        private WebView webView;
        CardView mCard;
        private Button bttag;
        private ImageView shareImageView;

        public ViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvDesc = (TextView) view.findViewById(R.id.description);
            tvDate = (TextView) view.findViewById(R.id.date_textview);
            mCard = (CardView) view.findViewById(R.id.card_view);
            shareImageView = (ImageView) view.findViewById(R.id.shareImageView);
//            tvhyperlink = (TextView) view.findViewById( R.id.hyperlink);
            bttag = (Button) view.findViewById(R.id.tag);
            imagerelative = (ImageView) view.findViewById(R.id.icon);
            webView = (WebView) view.findViewById(R.id.webview);

        }
    }
}