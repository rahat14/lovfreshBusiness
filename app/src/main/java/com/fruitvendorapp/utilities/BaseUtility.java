package com.fruitvendorapp.utilities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import com.fruitvendorapp.R;
import com.fruitvendorapp.model.BannerImage;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BaseUtility {
    // this method hide keyboard
    public static void hideKeyboard(View view, Context context) {
        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void showSoftKeyboard(View view, Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public void showDialogOK(Context context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(context.getString(R.string.ok), okListener)
                .setNegativeButton(context.getString(R.string.cancel), okListener)
                .create()
                .show();
    }


    // this method send one activity to another activity
    public static void toastMsg(Context context, String msg) {
        if (msg.length() > 0) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendActivityIntent(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


    public static void sentToScreenIntentWithFlag(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static String getCurrentTimeStamp() {
        Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();
        return ts;
    }

    public static String convertExpriyFormatDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("mm/yy");
        return format.format(calendar.getTime());
    }

    public static String getTimeStampToDate(String time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(time));
        String date = DateFormat.format("dd-MM-yyyy hh:mm a", cal).toString();
        return date;
    }

    public static String getTimeStampToChatDate(String time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(time));
        String date = DateFormat.format("dd MMMM yyyy", cal).toString();
        return date;
    }

    public static String getTimeStamp(String time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(time));
        String date = DateFormat.format("hh:mm a", cal).toString();
        return date;
    }


    public static String convertFormatDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String timeConvertTo12Hours(String time) {
        SimpleDateFormat _24HourSDF = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
        Date _24HourDt = null;
        try {
            _24HourDt = _24HourSDF.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return _12HourSDF.format(_24HourDt);

    }


    @SuppressLint("SimpleDateFormat")
    public static String convertStartAndEndFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("MM/dd/yyyy");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertGetStartAndEndFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateTimeFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("MMM dd,yyyy");
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertOrderDateTimeFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("MMM dd,yyyy");
        return spf.format(newDate);
    }


    @SuppressLint("SimpleDateFormat")
    public static String convertEventDateFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("dd-MM-yyyy");
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String convertDateFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("MM/dd/yyyy");
        return spf.format(newDate);
    }


    @SuppressLint("SimpleDateFormat")
    public static String convertDateSecondFormat(String date) {
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("yyyy-MM-dd");
        return spf.format(newDate);
    }

    @SuppressLint("SimpleDateFormat")
    public static String reviewDateFormat(String date) {//2020-05-16 07:53:10
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf = new SimpleDateFormat("MMMM dd yyyy");
        return spf.format(newDate);
    }

    public static float totalPrice(String price, String quantity) {
        //    double actualPrice= Double.parseDouble(price);
        return Float.parseFloat(price) * Float.parseFloat(quantity);

    }

    public static void openDatePicker(Context context, TextView text, int checkDateTime) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.e("", "DATE SELECTED " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        if (checkDateTime == 1) {
                            String startDate_ = BaseUtility.convertFormatDate(year, monthOfYear, dayOfMonth);
                            text.setText(startDate_);
                            Log.e("startdate", startDate_);
                        } else {
                            String endDate_ = BaseUtility.convertFormatDate(year, monthOfYear, dayOfMonth);
                            text.setText(endDate_);
                            Log.e(" endDate", endDate_);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public String getPath(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();
        return path;
    }

    @SuppressLint("SimpleDateFormat")
    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "HH:mm:ss";
        String outputPattern = "h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void deleteImageCacheFile(Context context, ArrayList<BannerImage> pathList) {

       /* File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + file_dj_path);
            } else {
                System.out.println("file not Deleted :" + file_dj_path);
            }
        }*/

        if (pathList != null) {
            for (int i = 0; i < pathList.size(); i++) {
                File file = new File(pathList.get(i).getImage());
                file.delete();
                if (file.exists()) {
                    try {
                        file.getCanonicalFile().delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (file.exists()) {
                        context.deleteFile(file.getName());
                    }
                }
            }
        }
    }

    public static void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteRecursive(child);
            }
        }

        fileOrDirectory.delete();
    }

    public static String toNullableStr(String s) {
        return s == null ? "" : String.valueOf(s);
    }

    public static String formatDateStr(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            if (e.getMessage() != null) Log.e("BaseUtility", e.getMessage());
        }

        return outputDate;

    }

    public static String toDefaultFormattedDateStr(String inputDate){
        return formatDateStr("yyyy-MM-dd", "dd/MM/yyyy", inputDate);
    }


}

