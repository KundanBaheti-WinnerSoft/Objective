package com.winnersoft.objective;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CALENDAR;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_CALENDAR;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class Utility {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
//            Boolean result1 = ContextCompat.checkSelfPermission(context, CAMERA) != PackageManager.PERMISSION_GRANTED;
//            Boolean result2 = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
//            int result1 = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
//            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
            if ((ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) & (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) ) {
                return true;

            } else {
                if ((ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, READ_EXTERNAL_STORAGE) |(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, WRITE_EXTERNAL_STORAGE)))) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle(R.string.requiredPermission);
                    alertBuilder.setMessage(R.string.requiredPermissionAllow);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    // ActivityCompat.requestPermissions((Activity) context, new String[]{READ_EXTERNAL_STORAGE, CAMERA, WRITE_EXTERNAL_STORAGE, CALL_PHONE, RECORD_AUDIO, READ_CALENDAR, WRITE_CALENDAR, ACCESS_FINE_LOCATION, SEND_SMS, RECEIVE_SMS, READ_SMS}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle(R.string.youHaveDeniedPermission);
                    alertBuilder.setMessage(R.string.pleaseAllowPermission);
                    alertBuilder.setPositiveButton(R.string.goToSetting, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            ComponentName componetName = new ComponentName(
                                    "com.android.settings",
                                    "com.android.settings.applications.InstalledAppDetails");
                            intent = new Intent();
                            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setData(Uri.parse("package:com.winnersoft.objective"));
                            intent.setComponent(componetName);
                            context.startActivity(intent);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                }

                return false;

            }


        } else {
            return true;
        }
    }
}
