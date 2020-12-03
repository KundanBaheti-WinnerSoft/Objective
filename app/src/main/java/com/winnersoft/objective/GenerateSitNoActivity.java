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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class GenerateSitNoActivity extends AppCompatActivity implements ItemClickListener {
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    private RecyclerView rvGenerateSitNo;
    private EditText edtSearch;
    private ImageView imgSearch;
    String searchKey;
    CommonCode commonCode = new CommonCode();
    ProgressDialog progressDialog;
    String jsonurl;
   public static String sitnogenerated = "",id="",batch="";
//   int id;
    Urllink urllink = new Urllink();
    final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
    private GenerateSitNoAdapter madapter;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_sit_no);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.generateSitNo));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");
        myDialog = new Dialog(GenerateSitNoActivity.this);
        rvGenerateSitNo = findViewById(R.id.rv_generatesitno);
        edtSearch = findViewById(R.id.edt_search);
        imgSearch = findViewById(R.id.img_search);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey = edtSearch.getText().toString();
                if (!commonCode.isValidString(GenerateSitNoActivity.this, searchKey)) {
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

        getAllList();
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


    private void getAllList() {
        arraymap.clear();
        if (commonCode.checkConnection(GenerateSitNoActivity.this)) {
            String jsonurl = urllink.url + "stdExamCenterAllocation1/getAllSeatNumberAllocation";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(GenerateSitNoActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);

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
                                hashMap.put("noOfQuestions", object.getString("noOfQuestions"));
                                hashMap.put("totalMarks", object.getString("totalMarks"));
                                hashMap.put("dateOfExam", object.getString("dateOfExam"));
//                                hashMap.put("course", object.getString("course"));
                                hashMap.put("batch", object.getString("batch"));
                                batch=object.getString("batch");
                                hashMap.put("day", object.getString("day"));
                                hashMap.put("sTime", object.getString("sTime"));
                                hashMap.put("eTime", object.getString("eTime"));
                                hashMap.put("seatnumber", object.getString("seatnumber"));
                                hashMap.put("seatnumber_allocation", object.getString("seatnumber_allocation"));
//                                sitnogenerated = object.getString("seatnumber_allocation");
                                hashMap.put("question_allocation", String.valueOf(object.getBoolean("question_allocation")));
                                hashMap.put("exam_center_allocation", String.valueOf(object.getBoolean("exam_center_allocation")));


                                JSONObject courseId = object.getJSONObject("course");
                                hashMap.put("courseId", courseId.getString("id"));
                                hashMap.put("courseName", courseId.getString("courseName"));
                                hashMap.put("courseDesc", courseId.getString("courseDesc"));
                                hashMap.put("duration", courseId.getString("duration"));
                                hashMap.put("courseCode", courseId.getString("courseCode"));
                                hashMap.put("fees", courseId.getString("fees"));
                                arraymap.add(hashMap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        commonCode.AlertDialog_Pbtn(GenerateSitNoActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.notFound), getResources().getString(R.string.ok));
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
            commonCode.AlertDialog_Pbtn(GenerateSitNoActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void showdata(ArrayList<HashMap<String, String>> arraymap) {
        madapter = new GenerateSitNoAdapter(getApplicationContext(), arraymap);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvGenerateSitNo.setLayoutManager(mLayoutManager);
        rvGenerateSitNo.setAdapter(madapter);
        madapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {

        GenerateSitNoDetail generateSitNoDetail = new GenerateSitNoDetail();
        generateSitNoDetail = (GenerateSitNoDetail) view.getTag();
        id = generateSitNoDetail.getId();
        sitnogenerated = generateSitNoDetail.getSeatnumber_allocation();
        batch=generateSitNoDetail.getBatch();


        switch (view.getId()) {

            case R.id.tv_status:
                if (sitnogenerated.equalsIgnoreCase("allocated")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.sitNoGenerated), Toast.LENGTH_LONG).show();
                } else if (sitnogenerated.equalsIgnoreCase("not_allocated")) {
//                    addCommentPopup(view);
                    generateAlertDialog(GenerateSitNoActivity.this, getResources().getString(R.string.generateSitNo), getResources().getString(R.string.doYouWantToGenerateSitNoOf) +" "+ batch + " ?", getResources().getString(R.string.cancel), getResources().getString(R.string.generate));

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.plsContactAdminForIssue), Toast.LENGTH_SHORT).show();
                }
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
                    sitnogenerated="allocated";
                    generateSitNo();
                    dialog.cancel();
                }
            });

            builder.create();
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateSitNo() {
        if (commonCode.checkConnection(GenerateSitNoActivity.this)) {
            String jsonurl = urllink.url + "stdExamCenterAllocation1/seatNumberGeneration/" + id;
            RequestQueue requestQueue = Volley.newRequestQueue(GenerateSitNoActivity.this);
            progressDialog = new ProgressDialog(GenerateSitNoActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            JSONObject object = new JSONObject();
            try {

                object.put("seatnumber_allocation", sitnogenerated);

//                JSONObject ob = new JSONObject();
//                ob.put("id", userId);
//                object.put("resoved_by", ob);

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
                        if (message.equals("Students Seatnumber Generation Allocated Successfully.")) {
                            Toast.makeText(GenerateSitNoActivity.this, getResources().getString(R.string.sitNoGenerated), Toast.LENGTH_LONG).show();
                            getAllList();
                        }else if(message.equalsIgnoreCase("Error Occured... Students Seatnumber not Allocated.")) {
                            Toast.makeText(GenerateSitNoActivity.this, "Error Occured... Students Seatnumber not Allocated.", Toast.LENGTH_SHORT).show();
                        }else if(message.equalsIgnoreCase("No Students in Batch...Seatnumber not Allocated")) {
                            Toast.makeText(GenerateSitNoActivity.this, "No Students in Batch...Seatnumber not Allocated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(GenerateSitNoActivity.this, getResources().getString(R.string.failedTryAgain), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(GenerateSitNoActivity.this, getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(GenerateSitNoActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(GenerateSitNoActivity.this, getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
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
            commonCode.AlertDialog_Pbtn(GenerateSitNoActivity.this, getResources().getString(R.string.noInternetConnection), "", getResources().getString(R.string.ok));
        }
    }



}
