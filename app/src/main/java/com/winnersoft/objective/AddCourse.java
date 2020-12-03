package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCourse extends AppCompatActivity {
    EditText edtCourseCode, edtCourseName, edtCourseDescription, edtCourseDuration, edtCourseFees;
    String courseCode="",courseName="",courseDesc="",courseDuration="",courseFees="",jsonurl = "";
    Button btnSubmit;
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    private Urllink urllink = new Urllink();
    CommonCode commonCode = new CommonCode();
    private ProgressDialog progressDialog;
    String hrs = "hrs";
    String courseDuration1,coursedur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.addNewCourse));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");
        InputFilter filtertxt = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        edtCourseCode=findViewById(R.id.edt_course_code);
        edtCourseName=findViewById(R.id.edt_course_name);
//        edtCourseName.setFilters(new InputFilter[]{filtertxt});
        edtCourseDescription=findViewById(R.id.edt_course_description);
//        edtCourseDescription.setFilters(new InputFilter[]{filtertxt});
        edtCourseDuration=findViewById(R.id.edt_course_duration);
        edtCourseFees=findViewById(R.id.edt_course_fees);

        btnSubmit=findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourseValidation();
            }
        });


    }

    private void addCourseValidation(){
        courseCode=edtCourseCode.getText().toString();
        courseName=edtCourseName.getText().toString();
        courseDesc=edtCourseDescription.getText().toString();
        courseDuration=edtCourseDuration.getText().toString();
        courseFees=edtCourseFees.getText().toString();
        hrs = "hr";

        if (!commonCode.isValidString(AddCourse.this, courseFees)||!commonCode.isValidString(AddCourse.this, courseDuration)||!commonCode.isValidString(AddCourse.this, courseDesc)||!commonCode.isValidString(AddCourse.this, courseCode)||!commonCode.isValidString(AddCourse.this, courseName)){
            if (!commonCode.isValidString(AddCourse.this, courseCode)) {
                edtCourseCode.setError(getResources().getString(R.string.plsEnterCourseCode));
                edtCourseCode.requestFocus();
            }  if (!commonCode.isValidString(AddCourse.this, courseName)) {
                edtCourseName.setError(getResources().getString(R.string.plsEnterCourseName));
                edtCourseName.requestFocus();
            }  if (!commonCode.isValidString(AddCourse.this, courseDesc)) {
                edtCourseDescription.setError(getResources().getString(R.string.plsEnterCourseDesc));
                edtCourseDescription.requestFocus();
            } if (!commonCode.isValidString(AddCourse.this, courseDuration)) {
                edtCourseDuration.setError(getResources().getString(R.string.plsEnterCourseDuration));
                edtCourseDuration.requestFocus();
            } if (!commonCode.isValidString(AddCourse.this, courseFees)) {
                edtCourseFees.setError(getResources().getString(R.string.plsEnterCourseFees));
                edtCourseFees.requestFocus();
            }
        }else{
            jsonurl = urllink.url + "Course/save";
//            http://localhost:8081/OnlineOBJExam/Course/save

            addCourse();
        }
//        if (!commonCode.isValidString(AddCourse.this, courseCode)) {
//            edtCourseCode.setError(getResources().getString(R.string.plsEnterCourseCode));
//            edtCourseCode.requestFocus();
//        } else if (!commonCode.isValidString(AddCourse.this, courseName)) {
//            edtCourseName.setError(getResources().getString(R.string.plsEnterCourseName));
//            edtCourseName.requestFocus();
//        } else if (!commonCode.isValidString(AddCourse.this, courseDesc)) {
//            edtCourseDescription.setError(getResources().getString(R.string.plsEnterCourseDesc));
//            edtCourseDescription.requestFocus();
//        }else if (!commonCode.isValidString(AddCourse.this, courseDuration)) {
//            edtCourseDuration.setError(getResources().getString(R.string.plsEnterCourseDuration));
//            edtCourseDuration.requestFocus();
//        }else if (!commonCode.isValidString(AddCourse.this, courseFees)) {
//            edtCourseFees.setError(getResources().getString(R.string.plsEnterCourseFees));
//            edtCourseFees.requestFocus();
//        }else{
//            jsonurl = urllink.url + "Course/save";
////            http://localhost:8081/OnlineOBJExam/Course/save
//
//            addCourse();
//        }
    }


    private void addCourse(){
        if (commonCode.checkConnection(AddCourse.this)) {
            progressDialog = new ProgressDialog(AddCourse.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            courseDuration1 = courseDuration.concat(hrs);

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JSONObject jsonParams = new JSONObject();
            try {
                //jsonParams.put("role", role);
                jsonParams.put("courseCode", courseCode);
                jsonParams.put("courseName", courseName);
                jsonParams.put("courseDesc", courseDesc);
                jsonParams.put("duration", courseDuration1);
                jsonParams.put("fees", courseFees);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, jsonurl,

                    jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String message = response.getString("message");
                        if (message.equals("Saved Successfully")) {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.registeredSuccessfully), Toast.LENGTH_LONG).show();
                            regSuccessPopup();
                        } else if (message.equals("course already exist")) {
//                            mobNoAlreadyPopup();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.courseNameAlreadyExist), Toast.LENGTH_SHORT).show();

                        } else if (message.equals("Course code already exist")) {
//                            mobNoAlreadyPopup();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.courseCodeAlreadyExist), Toast.LENGTH_SHORT).show();

                        } else if (message.equals("Failed")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    String err = error.toString();
                    if (err.equals("com.android.volley.AuthFailureError")) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(AddCourse.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-Auth-token", SecurityToken);
                    return params;
                }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(AddCourse.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }
    private void regSuccessPopup() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_course_added_success, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        Button buttonConfirm = (Button) confirmDialog.findViewById(R.id.btn_ok);

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
