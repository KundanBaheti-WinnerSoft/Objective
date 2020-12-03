package com.winnersoft.objective;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StudentDashboard extends AppCompatActivity {
    private CardView cvApplyForCourse, cvPrevYearQue, cvOnlineExam, cvDownloadHallTikcet;
    private ImageView ivLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_student_dashboard);
        cvApplyForCourse=findViewById(R.id.cv_apply_for_course);
        cvPrevYearQue=findViewById(R.id.cv_prev_year_que);
        cvOnlineExam=findViewById(R.id.cv_online_exam);
        cvDownloadHallTikcet=findViewById(R.id.cv_download_hall_ticket);
        ivLogout=findViewById(R.id.iv_logout);

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentDashboard.this, R.style.MyDialogTheme);
                builder.setTitle(getResources().getString(R.string.logoutQue));
                builder.setIcon(R.drawable.warning_sign);
                builder.setMessage(getResources().getString(R.string.doYouWantLogout));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(StudentDashboard.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.cancel();
                    }
                }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        cvApplyForCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentDashboard.this,ApplyForCourseActivity.class);
                startActivity(i);
            }
        });
        cvOnlineExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentDashboard.this,OnlineExamActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
