package com.winnersoft.objective;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminHome extends AppCompatActivity {

    private TextView tvStudentCount, tvExamControllerCount;
    private CardView cvExamController;
    CommonCode commonCode = new CommonCode();
    private ProgressDialog progressDialog;
    Urllink urllink = new Urllink();
    int teachersCount, studentsCount;
    String SecurityToken, userRole;
    private ImageView ivLogout;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("roleName", "");

        tvStudentCount = findViewById(R.id.tv_student_count);
        tvExamControllerCount = findViewById(R.id.tv_examcontroller_count);
        cvExamController = findViewById(R.id.cv_exam_controller);
        ivLogout = findViewById(R.id.iv_logout);

        cvExamController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminHome.this, ExamControllersList.class);
                startActivity(i);
            }
        });

        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHome.this, R.style.MyDialogTheme);
                builder.setTitle(getResources().getString(R.string.logoutQue));
                builder.setIcon(R.drawable.warning_sign);
                builder.setMessage(getResources().getString(R.string.doYouWantLogout));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(AdminHome.this, LoginActivity.class);
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

        getCount();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getCount();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    private void getCounts() {
        if (commonCode.checkConnection(AdminHome.this)) {
//            String jsonurl ="";
//            urllink.url + "schoolreg/schoolDaschBoardCount/";
            String jsonurl = urllink.url + "user/adminDashboardCount";

            RequestQueue requestQueue = Volley.newRequestQueue(AdminHome.this);
            //  progressDialog = new ProgressDialog(SADashboardActivity.this);
            //  progressDialog.setMessage(getResources().getString(R.string.loading));
            //  progressDialog.show();
            //  progressDialog.setCancelable(true);

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONObject data = response;
                         progressDialog.dismiss();
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //{"Librarian_Count":1,"Driver_Count":1,"Parent_Count":1,"Teacher_Count":2}
                                teachersCount = Integer.parseInt(response.getString("examControllerCount"));
                                studentsCount = Integer.parseInt(response.getString("studentCount"));

                                tvStudentCount.setText(String.valueOf(studentsCount));
                                tvExamControllerCount.setText(String.valueOf(teachersCount));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        commonCode.AlertDialog_Pbtn(AdminHome.this, getResources().getString(R.string.notFound), getResources().getString(R.string.orgNotFound), getResources().getString(R.string.ok));
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
                        Intent intent = new Intent(AdminHome.this, LoginActivity.class);
                        startActivity(intent);
                        //  finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            })
                    //This is for Headers If You Needed
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-Auth-token", SecurityToken);
                    return params;
                }
            };
            requestQueue.add(jsonArrayRequest);
        } else {
            commonCode.AlertDialog_Pbtn(AdminHome.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }
    private void getCount() {
        if (commonCode.checkConnection(AdminHome.this)) {
            final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
            String jsonurl = urllink.url + "user/adminDashboardCount";
            RequestQueue requestQueue = Volley.newRequestQueue(AdminHome.this);
            progressDialog = new ProgressDialog(AdminHome.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    JSONObject data = response;
                    progressDialog.dismiss();
                    if (response.length() > 0) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
//                                tvTotalCount.setText(getResources().getString(R.string.totalComplaints) + " : " + response.getString("Total"));
//                                tvNotResolvedCount.setText(getResources().getString(R.string.notResolved) + " : " + response.getString("NotResolved"));
                                tvExamControllerCount.setText(response.getString("examControllerCount"));
                                tvStudentCount.setText(response.getString("studentCount"));
//                                tvResolveCount.setText(response.getString("resolve"));
//                                tvClosedCount.setText(response.getString("Closed"));

//                                totalCount = Integer.parseInt(response.getString("Total"));
//                                openCount = Integer.parseInt(response.getString("Open"));
                           } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
//                        chardData();
                    } else {
                        commonCode.AlertDialog_Pbtn(AdminHome.this, getResources().getString(R.string.notFound), getResources().getString(R.string.notFound), getResources().getString(R.string.ok));
                    }

//                    getSelfAssignCount();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    String err = error.toString();
                    if (err.equals("com.android.volley.AuthFailureError")) {
                        Toast.makeText(AdminHome.this, getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(AdminHome.this, LoginActivity.class);
                        startActivity(intent);
                        //  finish();
                    } else {
                        Toast.makeText(AdminHome.this, getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            })
                    //This is for Headers If You Needed
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-Auth-token", SecurityToken);
                    return params;
                }
            };
            requestQueue.add(jsonArrayRequest);
        } else {
            commonCode.AlertDialog_Pbtn(AdminHome.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }


}
