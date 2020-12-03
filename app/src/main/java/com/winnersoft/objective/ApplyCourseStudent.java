package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

public class ApplyCourseStudent extends AppCompatActivity implements ItemClickListener {
    String SecurityToken, userName, userRole, userId;
    String CourseId;
    private SharedPreferences sharedPreferences;
    private RecyclerView rvCourse;
    CommonCode commonCode = new CommonCode();
    ProgressDialog progressDialog;
    String jsonurl;
    Urllink urllink = new Urllink();
    final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
    private CourseListAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_course_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.applyForCourse));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");
        rvCourse=findViewById(R.id.rv_course);


        getAllCourse();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getAllCourse();
    }

    private void getAllCourse(){
//        jsonurl = urllink.url + "regVolunteer/getAll";
        jsonurl = urllink.url + "Course/getAll";
        arraymap.clear();
        if (commonCode.checkConnection(getApplicationContext())) {
            //        jsonurl = urllink.url + "regVolunteer/getAll";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(ApplyCourseStudent.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    arraymap.clear();
                    progressDialog.dismiss();
                    if (response.length() > 0) {
//[{"id":17,"firstname":"Amol","middlename":"r","lastname":"Kharade","role":"Volunteer","mobileno":"7350911692","emailid":"amolkharade@gmail.com","birthdate":1561939200000,"country":"India","state":"Dadra and Nagar Haveli","city":"pune","gender":"Male","pincode":"231232","address":"Teerth Technospace, Mumbai - Pune Highway, Baner","image":"Image-yMz2032.jpg","area":"baner","userId":{"id":79,"emailId":null,"loginName":"7350911692","mobileNumber":null,"passwdString":"1234","oldpasswdString":null,"userType":{"userTypeName":"volunteer","id":2},"roles":[{"id":76,"roleDescription":null,"roleName":null}],"userRoles":null,"org_status":null,"inactive_deactivate":true},"admin":{"id":23,"firstname":"Adv.Kishor ","middlename":"Nanasaheb","lastname":"Shinde","role":"Administrator","mobileno":"9011282424","emailid":"mhsjanhit@gmail.com","birthdate":584755200000,"country":"India","state":"Maharashtra","city":"Pune","gender":"Male","pincode":"411028","address":"Bhusari Colony,Kothrud, Pune","image":"Image-PLn2386.jpg","area":"Pune","userId":{"id":77,"emailId":null,"loginName":"9011282424","mobileNumber":null,"passwdString":"1234","oldpasswdString":null,"userType":{"userTypeName":"Administrator","id":1},"roles":[{"id":74,"roleDescription":null,"roleName":null}],"userRoles":null,"org_status":null,"inactive_deactivate":true}}},{"id":18,"firstname":"Deepak","middlename":"pqr","lastname":"Singh","role":"Volunteer","mobileno":"9890281726","emailid":"kennyhrx7@gmail.com","birthdate":1562025600000,"country":"India","state":"Delhi","city":"vdvs","gender":"Male","pincode":"797949","address":"Vsvsbs","image":"Image-tol8651.jpg","area":"hzbzbv","userId":{"id":82,"emailId":null,"loginName":"9890281726","mobileNumber":null,"passwdString":"1234","oldpasswdString":null,"userType":{"userTypeName":"volunteer","id":2},"roles":[{"id":79,"roleDescription":null,"roleName":null}],"userRoles":null,"org_status":null,"inactive_deactivate":true},"admin":{"id":23,"firstname":"Adv.Kishor ","middlename":"Nanasaheb","lastname":"Shinde","role":"Administrator","mobileno":"9011282424","emailid":"mhsjanhit@gmail.com","birthdate":584755200000,"country":"India","state":"Maharashtra","city":"Pune","gender":"Male","pincode":"411028","address":"Bhusari Colony,Kothrud, Pune","image":"Image-PLn2386.jpg","area":"Pune","userId":{"id":77,"emailId":null,"loginName":"9011282424","mobileNumber":null,"passwdString":"1234","oldpasswdString":null,"userType":{"userTypeName":"Administrator","id":1},"roles":[{"id":74,"roleDescription":null,"roleName":null}],"userRoles":null,"org_status":null,"inactive_deactivate":true}}}]
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                HashMap<String, String> hashMap = new HashMap<>();
                                JSONObject object = response.getJSONObject(i);
                                hashMap.put("courseCode", object.getString("courseCode"));
                                hashMap.put("id", object.getString("id"));
                                hashMap.put("courseName", object.getString("courseName"));
                                hashMap.put("courseDesc", object.getString("courseDesc"));
                                hashMap.put("duration", object.getString("duration"));
                                hashMap.put("fees", object.getString("fees"));

//                                JSONObject userObj = object.getJSONObject("userType");
//                                hashMap.put("userId", userObj.getString("id"));
//                                hashMap.put("userTypeName", userObj.getString("userTypeName"));

//                                hashMap.put("userTypeName", String.valueOf(userObj.getBoolean("inactive_deactivate")));

                                arraymap.add(hashMap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        commonCode.AlertDialog_Pbtn(ApplyCourseStudent.this, getResources().getString(R.string.notFound), getResources().getString(R.string.examControllerNotFound), getResources().getString(R.string.ok));
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
            commonCode.AlertDialog_Pbtn(ApplyCourseStudent.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void showdata(ArrayList<HashMap<String, String>> arraymap) {
        madapter = new CourseListAdapter(getApplicationContext(), arraymap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvCourse.setLayoutManager(mLayoutManager);
        rvCourse.setAdapter(madapter);
        rvCourse.setVisibility(View.VISIBLE);
        madapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        CourseDetails courseDetails=new CourseDetails();
        courseDetails = (CourseDetails) view.getTag();
        Bundle bundle = new Bundle();
        bundle.putString("courseCode", courseDetails.getCoursecode());
        bundle.putString("courseName", courseDetails.getCoursename());
        bundle.putString("courseDesc", courseDetails.getCoursedesc());
        bundle.putString("duration", courseDetails.getCourseduration());
        bundle.putString("fees", courseDetails.getCoursefees());
        bundle.putString("id", courseDetails.getId());
        CourseId=courseDetails.getId();

        switch (view.getId()) {

            case R.id.ll_profile_card:
                applyCourseAlertDialog(ApplyCourseStudent.this, getResources().getString(R.string.applyForCourse), getResources().getString(R.string.doYouWantToApplyForThisCourse), getResources().getString(R.string.cancel), getResources().getString(R.string.apply));


                break;


            default:
                break;
        }
    }
    public void applyCourseAlertDialog(final Context context, String Title, String Message, String cancel, String delete) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Title);
            builder.setIcon(R.drawable.warning_sign);
            builder.setMessage(Message);
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });

            builder.setPositiveButton(delete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    applyCourse();
                    dialog.cancel();
                }
            });

            builder.create();
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyCourse() {
        if (commonCode.checkConnection(ApplyCourseStudent.this)) {
            String jsonurl = urllink.url + "studentCourse/save";
            RequestQueue requestQueue = Volley.newRequestQueue(ApplyCourseStudent.this);
            progressDialog = new ProgressDialog(ApplyCourseStudent.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            JSONObject object = new JSONObject();
            try {


                JSONObject ob = new JSONObject();
                ob.put("id", userId);
                object.put("stdUserId", ob);

                JSONObject ob1 = new JSONObject();
                ob1.put("id", CourseId);
                object.put("courseId", ob1);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, jsonurl,

                    object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String message = response.getString("message");
                        if (message.equals("Applied Successfully")) {
                            Toast.makeText(ApplyCourseStudent.this, "Applied Successfully.", Toast.LENGTH_SHORT).show();
                            getAllCourse();
                        }else if(message.equalsIgnoreCase("already applied")) {
                            Toast.makeText(ApplyCourseStudent.this, "Already Applied", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ApplyCourseStudent.this, getResources().getString(R.string.failedTryAgain), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(ApplyCourseStudent.this, getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(ApplyCourseStudent.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ApplyCourseStudent.this, getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
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
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(ApplyCourseStudent.this, getResources().getString(R.string.noInternetConnection), "", getResources().getString(R.string.ok));
        }
    }


}
