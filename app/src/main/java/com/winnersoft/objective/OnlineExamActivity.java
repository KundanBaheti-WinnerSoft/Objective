package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class OnlineExamActivity extends AppCompatActivity {

    Button btnSS, btnStopSS;
    Button btn;
    String SecurityToken, userName, userRole, userId, fName, mName, lName, mobNo;
    TextView tvUserRole;
    private SharedPreferences sharedPreferences;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_exam);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("roleName", "");

        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnlineExamActivity.this, QuizScreenActivity.class);
                startActivity(i);
                //finish();
            }
        });
        tvUserRole = findViewById(R.id.tv_uname_title);
        tvUserRole.setText(userRole);
        btnSS = findViewById(R.id.btn_ss);
        btnStopSS = findViewById(R.id.btn_stop_ss);
        btnSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(OnlineExamActivity.this,QuizScreenActivity.class);
//                startActivity(i);
//                finish();
                mScreenShotRunnable.run();
            }
        });
        btnStopSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mScreenShotRunnable);
            }
        });

    }

    private Runnable mScreenShotRunnable = new Runnable() {
        @Override
        public void run() {
            takeSS(getWindow().getDecorView().getRootView(), "result");
            mHandler.postDelayed(this, 5000);
        }
    };

    protected static File takeSS(View view, String fileName) {
        Date date = new Date();
        CharSequence format = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);

        try {
            String dirPath = Environment.getExternalStorageDirectory().toString() + "/ObjectiveExam";
            ///storage/emulated/0/ObjectiveExamApp
            File filedir = new File(dirPath);
            if (!filedir.exists()) {
                boolean mkdir = filedir.mkdir();
            }

            String path = dirPath + "/" + fileName + "-" + format + ".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return imageFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
