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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


public class QuizScreenActivity extends AppCompatActivity {
    LinearLayout llOptionContainer;
    TextView tvQuestion, tvNoIndicator,tvAnswer;
    Button btnPrevious, btnNext,btnOptionA,btnOptionB,btnOptionC,btnOptionD,btnSubmit;
    private Handler mHandler=new Handler();
    RadioGroup rgOption;
    RadioButton rbAnswer;
    String rbAnsString="";
    private SharedPreferences sharedPreferences;
    String SecurityToken, userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        SecurityToken= sharedPreferences.getString("securitytoken", "");
        userRole=sharedPreferences.getString("roleName","");

        setContentView(R.layout.activity_quiz_screen);

        llOptionContainer=findViewById(R.id.ll_option_container);
        tvQuestion=findViewById(R.id.tv_question);
        tvAnswer=findViewById(R.id.tv_ans);
        tvNoIndicator=findViewById(R.id.tv_no_indicator);
        btnNext=findViewById(R.id.btn_next);
        btnSubmit=findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valid();
            }
        });

        btnPrevious=findViewById(R.id.btn_previous);
        rgOption=findViewById(R.id.rg_option);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mHandler.removeCallbacks(mScreenShotRunnable);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mScreenShotRunnable.run();


            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void valid(){
        int radioButtonId= rgOption.getCheckedRadioButtonId();
        rbAnswer=(RadioButton)findViewById(radioButtonId);
        rbAnsString=(String)rbAnswer.getText().toString();
        tvAnswer.setText(rbAnsString);
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//    }



    private Runnable mScreenShotRunnable=new Runnable() {
        @Override
        public void run() {
            takeSS(getWindow().getDecorView().getRootView(),"result");
            mHandler.postDelayed(this,10000);
        }
    };
    protected static File takeSS(View view, String fileName){
        Date date=new Date();
        CharSequence format= DateFormat.format("yyyy-MM-dd_hh:mm:ss",date);

        try{
            String dirPath = Environment.getExternalStorageDirectory().toString()+"/ObjectiveExam";
            ///storage/emulated/0/ObjectiveExamApp
            File filedir= new File(dirPath);
            if(!filedir.exists()){
                boolean mkdir=filedir.mkdir();
            }

            String path=dirPath+"/"+fileName+"-"+format+".jpeg";

            view.setDrawingCacheEnabled(true);
            Bitmap bitmap=Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile=new File(path);

            FileOutputStream fileOutputStream=new FileOutputStream(imageFile);
            int quality=100;
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return imageFile;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
