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

public class ExamControllerDashoard extends AppCompatActivity {
    private CardView cvUserRegistration,cvCourse,cvScheduleExam,cvGenerateSitNo,cvQuestionSetGeneration,cvStudents;
    ImageView ivLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_controller_dashoard);
        getSupportActionBar().hide();
        cvUserRegistration=findViewById(R.id.cv_userregistration);
        cvUserRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent i = new Intent(ExamControllerDashoard.this,UserRegistrationList.class);
                Intent i = new Intent(ExamControllerDashoard.this,RegistrationActivity.class);

                startActivity(i);

            }
        });
        cvCourse=findViewById(R.id.cv_course);
        cvCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExamControllerDashoard.this,CourseActivity.class);
                startActivity(i);
            }
        });
        cvScheduleExam=findViewById(R.id.cv_scheduleexam);
        cvScheduleExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExamControllerDashoard.this,ScheduleExamActivity.class);
                startActivity(i);
            }
        });

        cvGenerateSitNo=findViewById(R.id.cv_generatesitno);
        cvGenerateSitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExamControllerDashoard.this,GenerateSitNoActivity.class);
                startActivity(i);
            }
        });

        cvQuestionSetGeneration=findViewById(R.id.cv_quesetgeneration);
        cvQuestionSetGeneration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ExamControllerDashoard.this,QuestionSetGeneration.class);
                startActivity(i);
            }
        });

        cvStudents=findViewById(R.id.cv_students);
        cvStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent students=new Intent(ExamControllerDashoard.this,StudentsActivity.class);
                startActivity(students);
            }
        });


        ivLogout=findViewById(R.id.iv_logout);
        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ExamControllerDashoard.this, R.style.MyDialogTheme);
                builder.setTitle(getResources().getString(R.string.logoutQue));
                builder.setIcon(R.drawable.warning_sign);
                builder.setMessage(getResources().getString(R.string.doYouWantLogout));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(ExamControllerDashoard.this, LoginActivity.class);
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
