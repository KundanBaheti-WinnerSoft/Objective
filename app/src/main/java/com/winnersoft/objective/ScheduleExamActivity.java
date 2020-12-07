package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScheduleExamActivity extends AppCompatActivity implements ItemClickListener{
    Button btnScheduleExam;
    RecyclerView rvScheduleExam;
    EditText edtSearch;
    ImageView imgSearch;
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    String searchKey;
    CommonCode commonCode = new CommonCode();
    ProgressDialog progressDialog;
    String jsonurl;
    Urllink urllink = new Urllink();
    final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
    private ScheduleExamListAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_exam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.scheduleExamList));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");
        rvScheduleExam=findViewById(R.id.rv_schedule_exam);
        btnScheduleExam=findViewById(R.id.btn_schedule_exam);
        edtSearch=findViewById(R.id.edt_search);
        imgSearch=findViewById(R.id.img_search);
        btnScheduleExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddScheduleExam.class);
                intent.putExtra("role", "Exam Controller");
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = edtSearch.getText().toString();
                if (!commonCode.isValidString(ScheduleExamActivity.this, searchKey)) {
                    edtSearch.setError(getResources().getString(R.string.plsEnterSearch));
                    edtSearch.requestFocus();
                } else {
//                    getAllSearchExamControllers();
                }
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    edtSearch.getText().clear();
//                    getAllExamControllers();
                }
            }
        });
        getAllScheduleExam();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllScheduleExam();
    }
    private void getAllScheduleExam(){
        //original
        //getall examSchedule list
//        jsonurl = urllink.url + "examSchedule/getAll";

        //list of courses added by particular exam controller
         jsonurl = urllink.url + "examSchedule/getAllExamScheduleByExC";
        arraymap.clear();
        if (commonCode.checkConnection(getApplicationContext())) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(ScheduleExamActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
//                    [      {
//                        "id":6,
//                            "noOfQuestions":100,
//                            "totalMarks":200,
//                            "dateOfExam":"2020-09-10",
//                            "course":{
//                        "id":4,
//                                "courseName":"A",
//                                "courseDesc":"dsfsf",
//                                "duration":"5",
//                                "courseCode":"12",
//                                "fees":5000,
//                                "examcontroller_user_id":null
//                    },
//                        "batch":"104",
//                            "day":"1",
//                            "sTime":"01:30:00",
//                            "eTime":"02:30:00",
//                            "question_allocation":true,
//                            "exam_center_allocation":false,
//                            "seatnumber":"vfhncghvdujvuj104170",
//                            "seatnumber_allocation":"allocated"
//                    }
//                    ]
                    arraymap.clear();
                    progressDialog.dismiss();
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                HashMap<String, String> hashMap = new HashMap<>();
                                JSONObject object = response.getJSONObject(i);
                                hashMap.put("id", object.getString("id"));
                                hashMap.put("noOfQuestions", object.getString("noOfQuestions"));
                                hashMap.put("totalMarks", object.getString("totalMarks"));
                                hashMap.put("dateOfExam", object.getString("dateOfExam"));
//                                hashMap.put("course", object.getString("course"));
                                hashMap.put("batch", object.getString("batch"));
                                hashMap.put("day", object.getString("day"));
                                hashMap.put("sTime", object.getString("sTime"));
                                hashMap.put("eTime", object.getString("eTime"));


                                hashMap.put("question_allocation", String.valueOf(object.getBoolean("question_allocation")));
                                hashMap.put("exam_center_allocation", String.valueOf(object.getBoolean("exam_center_allocation")));

                                JSONObject userObj = object.getJSONObject("course");
                                hashMap.put("courseId", userObj.getString("id"));
                                hashMap.put("courseName", userObj.getString("courseName"));

                                arraymap.add(hashMap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        commonCode.AlertDialog_Pbtn(ScheduleExamActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.examControllerNotFound), getResources().getString(R.string.ok));
                    }
                    showdata(arraymap);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    String err = error.toString();
                    if (err.equals("com.android.volley.AuthFailureError")) {
                        Toast.makeText(getApplicationContext(), R.string.tokenExpire, Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.serverError, Toast.LENGTH_LONG).show();
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
            commonCode.AlertDialog_Pbtn(ScheduleExamActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }
    private void showdata(ArrayList<HashMap<String, String>> arraymap) {
        madapter = new ScheduleExamListAdapter(getApplicationContext(), arraymap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvScheduleExam.setLayoutManager(mLayoutManager);
        rvScheduleExam.setAdapter(madapter);
        rvScheduleExam.setVisibility(View.VISIBLE);
        madapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        ScheduleExamDetails scheduleExamDetails=new ScheduleExamDetails();
        scheduleExamDetails = (ScheduleExamDetails) view.getTag();
        Bundle bundle = new Bundle();
        bundle.putString("courseName", scheduleExamDetails.getCoursename());
        bundle.putString("batch", scheduleExamDetails.getBatch());
        bundle.putString("noOfQuestions", scheduleExamDetails.getNoofquestions());
        bundle.putString("totalMarks", scheduleExamDetails.getTotalmarks());
        bundle.putString("dateOfExam", scheduleExamDetails.getDateofexam());
        bundle.putString("day", scheduleExamDetails.getDay());
        bundle.putString("sTime", scheduleExamDetails.getStarttime());
        bundle.putString("eTime", scheduleExamDetails.getEndtime());
        bundle.putString("id", scheduleExamDetails.getId());
        bundle.putBoolean("question_allocation", scheduleExamDetails.getQuestionallocation());
        bundle.putBoolean("exam_center_allocation", scheduleExamDetails.getExamcenterallocation());


        Intent intent = new Intent(getApplicationContext(), EditScheduleExamActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
