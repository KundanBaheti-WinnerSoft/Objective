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

public class ApplyForCourseActivity extends AppCompatActivity implements ItemClickListener {
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    private Button btnApplyCourse;
    private RecyclerView rvAppliedCourses;
    private EditText edtSearch;
    private ImageView imgSearch;
    String searchKey;
    CommonCode commonCode = new CommonCode();
    ProgressDialog progressDialog;
    String jsonurl;
    Urllink urllink = new Urllink();
    final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
    private CourseListAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_for_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.applyForCourse));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");
        rvAppliedCourses=findViewById(R.id.rv_applied_courses);
        btnApplyCourse=findViewById(R.id.btn_apply_for_course);
        edtSearch=findViewById(R.id.edt_search);
        imgSearch=findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = edtSearch.getText().toString();
                if (!commonCode.isValidString(ApplyForCourseActivity.this, searchKey)) {
                    edtSearch.setError(getResources().getString(R.string.plsEnterSearch));
                    edtSearch.requestFocus();
                } else {
//                    getAllSearchExamControllers();
                }
            }
        });
        btnApplyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApplyCourseStudent.class);
                intent.putExtra("role", "student");
                intent.putExtra("userId", userId);
                startActivity(intent);
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

        getAllAppliedCourseList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllAppliedCourseList();
    }

    private void getAllAppliedCourseList(){
//        jsonurl = urllink.url + "regVolunteer/getAll";
        jsonurl = urllink.url + "studentCourse/getByStudent/"+userId;
        arraymap.clear();
        if (commonCode.checkConnection(getApplicationContext())) {
            //        jsonurl = urllink.url + "regVolunteer/getAll";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(ApplyForCourseActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    arraymap.clear();
                    progressDialog.dismiss();
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                HashMap<String, String> hashMap = new HashMap<>();
                                JSONObject object = response.getJSONObject(i);
                                hashMap.put("id", object.getString("id"));

                                JSONObject courseId = object.getJSONObject("courseId");
                                hashMap.put("courseId", courseId.getString("id"));
                                hashMap.put("courseName", courseId.getString("courseName"));
                                hashMap.put("courseDesc", courseId.getString("courseDesc"));
                                hashMap.put("duration", courseId.getString("duration"));
                                hashMap.put("courseCode", courseId.getString("courseCode"));
                                hashMap.put("fees", courseId.getString("fees"));



                                JSONObject stdUserId = object.getJSONObject("stdUserId");
                                hashMap.put("studId", stdUserId.getString("id"));
//                                hashMap.put("userTypeName", String.valueOf(userObj.getBoolean("inactive_deactivate")));

                                arraymap.add(hashMap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        commonCode.AlertDialog_Pbtn(ApplyForCourseActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.examControllerNotFound), getResources().getString(R.string.ok));
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
            commonCode.AlertDialog_Pbtn(ApplyForCourseActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void showdata(ArrayList<HashMap<String, String>> arraymap) {
        madapter = new CourseListAdapter(getApplicationContext(), arraymap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAppliedCourses.setLayoutManager(mLayoutManager);
        rvAppliedCourses.setAdapter(madapter);
        rvAppliedCourses.setVisibility(View.VISIBLE);
        madapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {

    }
}
