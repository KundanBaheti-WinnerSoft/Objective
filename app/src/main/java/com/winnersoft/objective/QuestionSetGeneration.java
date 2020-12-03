package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import static com.winnersoft.objective.GenerateSitNoActivity.sitnogenerated;

public class QuestionSetGeneration extends AppCompatActivity implements ItemClickListener{
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    Dialog myDialog;
    CommonCode commonCode = new CommonCode();
    ProgressDialog progressDialog;
    String jsonurl;
    Urllink urllink = new Urllink();
    private RecyclerView rvQueSetGen;
    private EditText edtSearch;
    private ImageView imgSearch;
    String searchKey;
    final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
    private QueSetGenAdapter madapter;
    private static String examScheduleId;
    private static Boolean queSetGen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_set_generation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.quesetGen));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");
        myDialog = new Dialog(QuestionSetGeneration.this);
        rvQueSetGen = findViewById(R.id.rv_quesetgen);
        edtSearch = findViewById(R.id.edt_search);
        imgSearch = findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = edtSearch.getText().toString();
                if (!commonCode.isValidString(QuestionSetGeneration.this, searchKey)) {
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
        getAllQueSetList();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getAllList();
        if (progressDialog != null) {
//            getAllCourse();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
    private void getAllQueSetList(){
//        jsonurl = urllink.url + "regVolunteer/getAll";
        jsonurl = urllink.url + "examSchedule/getAll";
        arraymap.clear();
        if (commonCode.checkConnection(getApplicationContext())) {
            //        jsonurl = urllink.url + "regVolunteer/getAll";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(QuestionSetGeneration.this);
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
                                hashMap.put("id", object.getString("id"));
                                examScheduleId=object.getString("id");

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
                        commonCode.AlertDialog_Pbtn(QuestionSetGeneration.this, getResources().getString(R.string.notFound), getResources().getString(R.string.examControllerNotFound), getResources().getString(R.string.ok));
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
            commonCode.AlertDialog_Pbtn(QuestionSetGeneration.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

//    private void getAllQueSetList() {
//        arraymap.clear();
//        if (commonCode.checkConnection(QuestionSetGeneration.this)) {
//            String jsonurl = urllink.url + "questionSet/getAll";
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            progressDialog = new ProgressDialog(QuestionSetGeneration.this);
//            progressDialog.setMessage(getResources().getString(R.string.loading));
//            progressDialog.show();
//            progressDialog.setCancelable(true);
//
//            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, jsonurl, null, new Response.Listener<JSONArray>() {
//                @Override
//                public void onResponse(JSONArray response) {
//                    arraymap.clear();
//                    progressDialog.dismiss();
//                    if (response.length() > 0) {
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                HashMap<String, String> hashMap = new HashMap<>();
//                                JSONObject object = response.getJSONObject(i);
//                                hashMap.put("id", object.getString("id"));
//                                hashMap.put("isRandom", String.valueOf(object.getBoolean("isRandom")));
////                                hashMap.put("objectiveQueations", object.getString("objectiveQueations"));
//                                hashMap.put("setId", object.getString("setId"));
//
//                                JSONObject exam_schedule = object.getJSONObject("exam_schedule");
//                                hashMap.put("examScheduleId", exam_schedule.getString("id"));
//                                examScheduleId=exam_schedule.getString("id");
//                                hashMap.put("noOfQuestions", exam_schedule.getString("noOfQuestions"));
//                                hashMap.put("totalMarks", exam_schedule.getString("totalMarks"));
//                                hashMap.put("dateOfExam", exam_schedule.getString("dateOfExam"));
//                                hashMap.put("batch", exam_schedule.getString("batch"));
//                                hashMap.put("day", exam_schedule.getString("day"));
//                                hashMap.put("sTime", exam_schedule.getString("sTime"));
//                                hashMap.put("eTime", exam_schedule.getString("eTime"));
//                                hashMap.put("question_allocation", String.valueOf(exam_schedule.getBoolean("question_allocation")));
//                                hashMap.put("exam_center_allocation", String.valueOf(exam_schedule.getBoolean("exam_center_allocation")));
//                                hashMap.put("seatnumber", exam_schedule.getString("seatnumber"));
//                                hashMap.put("seatnumber_allocation", exam_schedule.getString("seatnumber_allocation"));
//
//                                JSONObject course = object.getJSONObject("course");
//                                hashMap.put("courseId", course.getString("id"));
//                                hashMap.put("courseName", course.getString("courseName"));
////                                batch=object.getString("batch");
//                                hashMap.put("courseDesc", course.getString("courseDesc"));
//                                hashMap.put("duration", course.getString("duration"));
//                                hashMap.put("courseCode", course.getString("courseCode"));
//                                hashMap.put("fees", course.getString("fees"));
//                                arraymap.add(hashMap);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    } else {
//                        commonCode.AlertDialog_Pbtn(QuestionSetGeneration.this, getResources().getString(R.string.notFound), getResources().getString(R.string.notFound), getResources().getString(R.string.ok));
//                    }
//                    showdata(arraymap);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
//                    String err = error.toString();
//                    if (err.equals("com.android.volley.AuthFailureError")) {
//                        Toast.makeText(getApplicationContext(), R.string.tokenExpire, Toast.LENGTH_LONG).show();
//
//                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
//                        sp.edit().clear().commit();
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(getApplicationContext(), R.string.serverError, Toast.LENGTH_LONG).show();
//                    }
//                }
//            })
//                    //This is for Headers If You Needed
//            {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("x-Auth-token", SecurityToken);
//                    return params;
//                }
//            };
//            requestQueue.add(jsonArrayRequest);
//        } else {
//            commonCode.AlertDialog_Pbtn(QuestionSetGeneration.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
//        }
//    }

    private void showdata(ArrayList<HashMap<String, String>> arraymap) {
        madapter = new QueSetGenAdapter(getApplicationContext(), arraymap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvQueSetGen.setLayoutManager(mLayoutManager);
        rvQueSetGen.setAdapter(madapter);
        madapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        QueSetGenDetails queSetGenDetails = new QueSetGenDetails();
        queSetGenDetails = (QueSetGenDetails) view.getTag();
        examScheduleId = queSetGenDetails.getId();
        queSetGen = queSetGenDetails.getQuestion_allocation();
        switch (view.getId()) {

            case R.id.tv_status:
                if (!queSetGen) {
                    generateAlertDialog(QuestionSetGeneration.this, getResources().getString(R.string.quesetGen), getResources().getString(R.string.doYouWantToGenerateQueSet), getResources().getString(R.string.cancel), getResources().getString(R.string.generate));

                }
                else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.queSetGenerated), Toast.LENGTH_LONG).show();

                }
//                if (queSetGen.equalsIgnoreCase("allocated")) {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.sitNoGenerated), Toast.LENGTH_LONG).show();
//                } else if (sitnogenerated.equalsIgnoreCase("not_allocated")) {
////                    addCommentPopup(view);
//                    generateAlertDialog(GenerateSitNoActivity.this, getResources().getString(R.string.generateSitNo), getResources().getString(R.string.doYouWantToGenerateSitNoOf) +" "+ batch + " ?", getResources().getString(R.string.cancel), getResources().getString(R.string.generate));
//
//                } else {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plsContactAdminForIssue), Toast.LENGTH_SHORT).show();
//                }
                break;


            default:
                break;
        }

    }

    public void generateAlertDialog(final Context context, String Title, String Message, String cancel, String delete) {
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
                    queSetGen=true;
                    generateQueSet();
                    dialog.cancel();
                }
            });

            builder.create();
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateQueSet() {
        if (commonCode.checkConnection(QuestionSetGeneration.this)) {
            String jsonurl = urllink.url + "questionSet/save" ;
            RequestQueue requestQueue = Volley.newRequestQueue(QuestionSetGeneration.this);
            progressDialog = new ProgressDialog(QuestionSetGeneration.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            JSONObject object = new JSONObject();
            try {

//                object.put("seatnumber_allocation", sitnogenerated);

                JSONObject ob = new JSONObject();
                ob.put("id", examScheduleId);
                object.put("exam_schedule", ob);

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
                        if (message.equals("Question Set genrated successfully")) {
                            Toast.makeText(QuestionSetGeneration.this, "Question Set genrated successfully", Toast.LENGTH_SHORT).show();
                            getAllQueSetList();
                        }else if(message.equalsIgnoreCase("Number of queation is not available as per exam schadule requirment")) {
                            Toast.makeText(QuestionSetGeneration.this, "Number of queation is not available as per exam schadule requirment", Toast.LENGTH_SHORT).show();
                        }else if(message.equalsIgnoreCase("already exist")) {
                            Toast.makeText(QuestionSetGeneration.this, "already exist", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(QuestionSetGeneration.this, getResources().getString(R.string.failedTryAgain), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(QuestionSetGeneration.this, getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(QuestionSetGeneration.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(QuestionSetGeneration.this, getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
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
            commonCode.AlertDialog_Pbtn(QuestionSetGeneration.this, getResources().getString(R.string.noInternetConnection), "", getResources().getString(R.string.ok));
        }
    }

}
