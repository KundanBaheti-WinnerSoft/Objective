package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class StudentsActivity extends AppCompatActivity implements ItemClickListener {
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    CommonCode commonCode = new CommonCode();
    ProgressDialog progressDialog;
    String jsonurl;
    Urllink urllink = new Urllink();
    CardView cvTotalStudentsCount;
    RecyclerView rvStudentList;
    MaterialSpinner spnCourse;
    final ArrayList<HashMap<String, String>> arraymapCourse = new ArrayList<>();
    ArrayList<String> nameListCourse = new ArrayList<>();
    final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
    private ExamControllerAdapter madapter;
    TextView tvRegStudCount;
    TableRow trRegCount;
    int courseId = 0;
    String courseName = "";
    String count = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.coursewiseStudentData));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");

        tvRegStudCount = findViewById(R.id.tv_total_students_count);
        trRegCount = findViewById(R.id.tr_reg_count);
        cvTotalStudentsCount = findViewById(R.id.cv_total_students_count);
        rvStudentList = findViewById(R.id.rv_studentList);
        spnCourse = findViewById(R.id.spn_course);
        getExamCotrollerCourseList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getExamCotrollerCourseList();
    }
    private void getExamCotrollerCourseList() {
        arraymapCourse.clear();
        nameListCourse.clear();
        if (commonCode.checkConnection(StudentsActivity.this)) {
            final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
//            original
//            getall course name list
//            String jsonurl = urllink.url + "Course/getAllCourse";

            //list of courses added by particular exam controller
            String jsonurl = urllink.url + "Course/getAllCourseByExController";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(StudentsActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);

            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    JSONObject data = response;
                    progressDialog.dismiss();
                    if (response.length() > 0) {

                        try {


                            count = response.getString("studentCount");
                            tvRegStudCount.setText(count);

//                            or
//                            tvRegStudCount.setText(response.getString("studentCount"));

                            JSONArray jsonArray = response.getJSONArray("courses");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("id", obj.getString("id"));
                                hashMap.put("courseName", obj.getString("courseName"));
                                String name = obj.getString("courseName");
                                nameListCourse.add(name);

                                arraymapCourse.add(hashMap);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(StudentsActivity.this, android.R.layout.simple_spinner_item, nameListCourse);
                        spnCourse.setAdapter(stringArrayAdapter2);
                        // spnState.setSelection(21);
                        spnCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String valuename = parent.getItemAtPosition(position).toString();
                                if (!valuename.equals(getResources().getString(R.string.selCourse))) {
                                    courseId = Integer.parseInt(arraymapCourse.get(position).get("id"));
                                    courseName = (arraymapCourse.get(position).get("courseName"));
                                    getCoursewiseStudentData();
                                    trRegCount.setVisibility(View.VISIBLE);

                                } else {
                                    courseId = 0;
                                    courseName = "";
                                    spnCourse.setSelection(0);
                                    trRegCount.setVisibility(View.GONE);
                                    arraymap.clear();


                                }

                                //    Toast.makeText(RegistrationActivity.this, arraymapClass.get(position).get("id"), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
                        commonCode.AlertDialog_Pbtn(StudentsActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.noDataFound), getResources().getString(R.string.ok));
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
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        //  finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            })       //This is for Headers If You Needed
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
            commonCode.AlertDialog_Pbtn(StudentsActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void getCoursewiseStudentData() {

            jsonurl = urllink.url + "studentCourse/getStudentsByCourse/" + courseId;
            arraymap.clear();
            if (commonCode.checkConnection(getApplicationContext())) {
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                progressDialog = new ProgressDialog(StudentsActivity.this);
                progressDialog.setMessage(getResources().getString(R.string.loading));
                progressDialog.show();
                progressDialog.setCancelable(false);

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//[{"id":176,"otp":null,"loginName":"pk557@gmail.com","language":null,"userType":{"userTypeName":"student","id":4},"softDelete":false,"roles":[{"id":202,"roleDescription":null,"roleName":null}],"userRoles":null,"first_name":"prajkta","middle_name":"b","last_name":"kole","m_name":"m","birthdate":"1111-11-11","admissionDate":"1911-11-11","email":"pk557@gmail.com","mobile":"8787978721","bloodGroup":"O+ve","gender":"Female","emergencyMobNo":"8978709098","address":"pune","highestQualification":"BE","nationality":"india","state":"maharashtra","city":"pune","organization":"id":2,"version":null,"createdDate":null,"createdBy":null,"modifiedDate":null,"modifiedBy":null,"orgOwnner":"Shrutanjay","orgStartedDate":1577817000000,"orgName":"demo","orgRegId":"REE124","orgAddress":"Baner,pune","orgContactNo":"8975730288","softDelete":false},"passwdString":"prajkta0818#"}]
                        arraymap.clear();
                        progressDialog.dismiss();
                        if (response.length() > 0) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    JSONObject object = response.getJSONObject(i);
                                    hashMap.put("id", object.getString("id"));
                                    hashMap.put("first_name", object.getString("first_name"));
                                    hashMap.put("middle_name", object.getString("middle_name"));
                                    hashMap.put("last_name", object.getString("last_name"));
                                    hashMap.put("m_name", object.getString("m_name"));
                                    hashMap.put("birthdate", object.getString("birthdate"));
                                    hashMap.put("admissionDate", object.getString("admissionDate"));
                                    hashMap.put("gender", object.getString("gender"));
                                    hashMap.put("email", object.getString("email"));
                                    hashMap.put("mobile", object.getString("mobile"));
                                    hashMap.put("bloodGroup", object.getString("bloodGroup"));
                                    hashMap.put("emergencyMobNo", object.getString("emergencyMobNo"));
                                    hashMap.put("highestQualification", object.getString("highestQualification"));

                                    hashMap.put("nationality", object.getString("nationality"));
                                    hashMap.put("state", object.getString("state"));
                                    hashMap.put("city", object.getString("city"));
                                    hashMap.put("address", object.getString("address"));

                                    JSONObject userObj = object.getJSONObject("userType");
                                    hashMap.put("userId", userObj.getString("id"));
                                    hashMap.put("userTypeName", userObj.getString("userTypeName"));

                                    hashMap.put("address", object.getString("address"));
                                    hashMap.put("softDelete", String.valueOf(object.getBoolean("softDelete")));


                                    arraymap.add(hashMap);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            commonCode.AlertDialog_Pbtn(StudentsActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.examControllerNotFound), getResources().getString(R.string.ok));
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
                commonCode.AlertDialog_Pbtn(StudentsActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
            }

    }

    private void showdata(ArrayList<HashMap<String, String>> arraymap) {
        madapter = new ExamControllerAdapter(getApplicationContext(), arraymap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvStudentList.setLayoutManager(mLayoutManager);
        rvStudentList.setAdapter(madapter);
        rvStudentList.setVisibility(View.VISIBLE);
        madapter.setClickListener(this);
    }


    @Override
    public void onClick(View view, int position) {

    }
}
