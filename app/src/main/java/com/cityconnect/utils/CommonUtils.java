package com.cityconnect.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.cityconnect.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {

    public static void hideKeyboard(Activity activity, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static void showKeyboard(Activity activity, EditText editText) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInputFromInputMethod(editText.getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public static final String DATE_FORMAT_DMY = "dd-MM-yyyy";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_MMMDD = "MMM dd yyyy";

    public static String getDMYFormattedDate(Date d) {
        return new SimpleDateFormat(DATE_FORMAT_DMY).format(d);
    }

    public static String getYMDFormattedDate(Date d) {
        return new SimpleDateFormat(DATE_FORMAT_YMD).format(d);
    }

    public static String getMMMDDFormattedDate(Date d) {
        return new SimpleDateFormat(DATE_FORMAT_MMMDD).format(d);
    }

    public static String getDMYFormattedDate(String date) {
        String format = "yyyy-MM-dd'T'HH:mm:ss";
        try {
            Date d = new SimpleDateFormat(format, Locale.ENGLISH)
                    .parse(date);
            return getMMMDDFormattedDate(d);
        } catch (Exception e) {

        }
        return "";
    }

    public static String getOrderItemDateFormat(String date) {
        String format = "yyyy-MM-dd";
        try {
            Date d = new SimpleDateFormat(format, Locale.ENGLISH)
                    .parse(date);
            return getMMMDDFormattedDate(d);
        } catch (Exception e) {

        }
        return "";
    }

    public static String getYYYYMMDDFormattedDate(String date) {
        String format = "yyyy-MM-dd'T'HH:mm:ss";
        try {
            Date d = new SimpleDateFormat(format, Locale.ENGLISH)
                    .parse(date);
            return getYMDFormattedDate(d);
        } catch (Exception e) {

        }
        return "";
    }

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateEmail(String email) {
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }


    public static Dialog getCircularProgressDialog(Activity activity, String titleText, boolean cancelableFlag) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.circular_progressbar);
        dialog.setCancelable(cancelableFlag);
        TextView title = (TextView) dialog.findViewById(R.id.upgrade_title_textview);
        title.setText(titleText);
        return dialog;
    }


    public static boolean isInternetAvailable(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
