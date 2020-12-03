package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ExamControllerProfileActivity extends AppCompatActivity {
    private CommonCode commonCode = new CommonCode();
    private Urllink urllink = new Urllink();
    private String url = urllink.url;
    private ProgressDialog progressDialog;
    String SecurityToken;
    private SharedPreferences sharedPreferences;
    boolean activeInactive;
    public Bundle getBundle = null;
    ImageView ivProfilePic;
    String jsonurl = "";
    private ImageButton imgBtnEdit;
    SwitchCompat switchBtn;
    Dialog myDialog;
    String newId="";
    private TextView tvName,tvMotherName,tvDob,tvAdmissionDate,tvGender,tvEmail,tvMobileNo,tvBloodGroup,tvEmergencyMobNo,tvQualification,tvCountry,tvState,tvCity,tvAddress;
    private String firstname,middlename,lastname,mothername,birthdate,admissiondate,gender,emailid,mobileno,bloodgroup,emergencymobileno,qualification,country,state,city,address,role,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_controller_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.profileDetails));
        getSupportActionBar().setSubtitle("");

        tvName=findViewById(R.id.tv_name);
        tvMotherName=findViewById(R.id.tv_mother_name);
        tvDob=findViewById(R.id.tv_dob);
        tvAdmissionDate=findViewById(R.id.tv_admission_date);
        tvGender=findViewById(R.id.tv_gender);
        tvEmail=findViewById(R.id.tv_email_id);
        tvMobileNo=findViewById(R.id.tv_mobile_no);
        tvBloodGroup=findViewById(R.id.tv_blood_group);
        tvEmergencyMobNo=findViewById(R.id.tv_emergency_mobile_no);
        tvQualification=findViewById(R.id.tv_qualification);
        tvCountry=findViewById(R.id.tv_country);
        tvState=findViewById(R.id.tv_state);
        tvCity=findViewById(R.id.tv_city);
        tvAddress=findViewById(R.id.tv_address);

        imgBtnEdit=findViewById(R.id.img_btn_edit);
        switchBtn=findViewById(R.id.switchButton);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        myDialog = new Dialog(this);
        newId  = getIntent().getStringExtra("id");


        imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

//                bundle.putString("studentId", studentId);
//                bundle.putString("studFName", studFName);
//                bundle.putString("studMName", studMName);
//                bundle.putString("studLName", studLName);
//                bundle.putString("studAddress", studAddress);


                bundle.putString("id", id);
                bundle.putString("userTypeName", role);
                bundle.putString("first_name", firstname);
                bundle.putString("middle_name", middlename);
                bundle.putString("last_name", lastname);
                bundle.putString("m_name", mothername);
                bundle.putString("birthdate", birthdate);
                bundle.putString("admissionDate", admissiondate);
                bundle.putString("gender", gender);
                bundle.putString("email", emailid);
                bundle.putString("mobile", mobileno);
                bundle.putString("bloodGroup", bloodgroup);
                bundle.putString("emergencyMobNo", emergencymobileno);
                bundle.putString("highestQualification", qualification);
                bundle.putString("nationality", country);
                bundle.putString("state", state);
                bundle.putString("city", city);
                bundle.putString("address", address);
                bundle.putBoolean("softDelete", activeInactive);

                Intent intent = new Intent(ExamControllerProfileActivity.this, EditsProfileActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        switchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Is the switch is on?
                activeInactive = ((SwitchCompat) v).isChecked();
                if(activeInactive)
                {
                    activeInactive = false;
                    //Do something when switch is on/checked
                    deleteAlertDialog(ExamControllerProfileActivity.this, getResources().getString(R.string.activeInactive), getResources().getString(R.string.doYouWantChangeStatus) + role + " ?", getResources().getString(R.string.cancel), getResources().getString(R.string.update));
                }
                else
                {
                    activeInactive = true;
                    //Do something when switch is off/unchecked
                    deleteAlertDialog(ExamControllerProfileActivity.this, getResources().getString(R.string.activeInactive), getResources().getString(R.string.doYouWantChangeStatus) + role + " ?", getResources().getString(R.string.cancel), getResources().getString(R.string.update));
                }
            }
        });

        getBundle = this.getIntent().getExtras();
        id = getBundle.getString("id");
        role = getBundle.getString("userTypeName");
        firstname=  getBundle.getString("first_name");
        middlename=  getBundle.getString("middle_name");
        lastname=  getBundle.getString("last_name");
        mothername=  getBundle.getString("m_name");
        birthdate=  getBundle.getString("birthdate");
        admissiondate=  getBundle.getString("admissionDate");
        gender=  getBundle.getString("gender");
        emailid=  getBundle.getString("email");
        mobileno=  getBundle.getString("mobile");
        bloodgroup=  getBundle.getString("bloodGroup");
        emergencymobileno=  getBundle.getString("emergencyMobNo");
        qualification=  getBundle.getString("highestQualification");
        country=  getBundle.getString("nationality");
        state=  getBundle.getString("state");
        city=  getBundle.getString("city");
        address=  getBundle.getString("address");
        activeInactive =  getBundle.getBoolean("softDelete", activeInactive);
        if (getBundle != null) {

        }

    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (role.equalsIgnoreCase("SuperAdmin")) {
////            getSupportActionBar().setTitle(getResources().getString(R.string.address));
//            getUserDetails();
////            setUserDetails();
//        } else if (role.equalsIgnoreCase("Exam Controller")) {
////            getSupportActionBar().setTitle(getResources().getString(R.string.teacher));
//            getExamControllerDetails();
////            setExamControllerDetails();
//
//
//        }
        getExamControllerDetails();

    }

    public void deleteAlertDialog(final Context context, String Title, String Message, String cancel, String delete) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(Title);
            builder.setIcon(R.drawable.warning_sign);
            builder.setMessage(Message);
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (activeInactive) {
                        switchBtn.setChecked(true);
                    } else {
                        switchBtn.setChecked(false);
                    }
                    dialog.cancel();

                }
            });

            builder.setPositiveButton(delete, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    deleteRole();
                    dialog.cancel();
                }
            });

            builder.create();
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void deleteRole() {
        http://localhost:8081/OnlineOBJExam/user/softDelete/ + userid
        if (commonCode.checkConnection(getApplicationContext())) {
//            if (role.equalsIgnoreCase("SuperAdmin")) {
//                jsonurl = url + "user/softDelete/" + id;
//            } else if (role.equalsIgnoreCase("Exam Controller")) {
//                jsonurl = url + "user/softDelete/" + id;
//            } else if (role.equalsIgnoreCase("student")) {
//                jsonurl = url + "regdriver/deleteDriver/" + id;
//            }
            jsonurl = url + "user/softDelete/" + id;
            // String jsonurl = url + "reglibrarian/deleteLibrarian/" + id + "/" + schoolid;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(ExamControllerProfileActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);

            JSONObject jsonParams = new JSONObject();

            try {
                if (activeInactive) {
                    activeInactive = true;
                } else {
                    activeInactive = false;
                }
                jsonParams.put("softDelete", activeInactive);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, jsonurl,
                    jsonParams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                String message = response.getString("message");
                                if (message.equals("Deleted")) {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.updatedSuccessfully), Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                } else {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
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
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(ExamControllerProfileActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void setExamControllerDetails(){
        tvName.setText(firstname + " " + middlename + " " + " " + lastname);
        tvMotherName.setText(mothername);
        tvDob.setText(birthdate);
        tvAdmissionDate.setText(admissiondate);
        tvGender.setText(gender);
        tvEmail.setText(emailid);
        tvMobileNo.setText(mobileno);
        tvBloodGroup.setText(bloodgroup);
        tvEmergencyMobNo.setText(emergencymobileno);
        tvQualification.setText(qualification);
        tvCountry.setText(country);
        tvState.setText(state);
        tvCity.setText(city);
        tvAddress.setText(address);
        if (activeInactive) {
            switchBtn.setChecked(false);

        } else {
            switchBtn.setChecked(true);
        }
    }
    private void getExamControllerDetails() {
        if (commonCode.checkConnection(getApplicationContext().getApplicationContext())) {
            String jsonurl = url + "user/findById/"+newId ;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
            progressDialog = new ProgressDialog(ExamControllerProfileActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);
            final JSONObject object = new JSONObject();
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, jsonurl,

                    object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                id = "";
                                id = response.getString("id");
                                firstname = response.getString("first_name");
                                middlename = response.getString("middle_name");
                                lastname = response.getString("last_name");
                                mothername = response.getString("m_name");
                                birthdate = response.getString("birthdate");
                                admissiondate = response.getString("admissionDate");
                                gender = response.getString("gender");
                                mobileno = response.getString("mobile");
                                emailid = response.getString("email");
                                bloodgroup = response.getString("bloodGroup");
                                emergencymobileno = response.getString("emergencyMobNo");
                                qualification = response.getString("highestQualification");
                                address = response.getString("address");
                                country = response.getString("nationality");
                                state = response.getString("state");
                                city = response.getString("city");
//                                role = response.getString("userTypeName");
                                activeInactive = response.getBoolean("softDelete");

                                if (activeInactive) {
                                    switchBtn.setChecked(false);

                                } else {
                                    switchBtn.setChecked(true);
                                }
//                                if (role.equals("Student")) {
//                                    tvname.setText(Studentname);
//                                    tbrowParentName.setVisibility(View.VISIBLE);
//                                    dividerView.setVisibility(View.VISIBLE);
//                                    tvParentsInfo.setVisibility(View.VISIBLE);
//                                    tvParentName.setText(firstname + " " + middlename + " " + " " + lastname);
//                                } else {


//                                }


                                tvName.setText(firstname + " " + middlename + " " + " " + lastname);
                                tvMotherName.setText(mothername);
                                tvDob.setText(birthdate);
                                tvAdmissionDate.setText(admissiondate);
                                tvGender.setText(gender);
                                tvEmail.setText(emailid);
                                tvMobileNo.setText(mobileno);
                                tvBloodGroup.setText(bloodgroup);
                                tvEmergencyMobNo.setText(emergencymobileno);
                                tvQualification.setText(qualification);
                                tvCountry.setText(country);
                                tvState.setText(state);
                                tvCity.setText(city);
                                tvAddress.setText(address);


//                                String imagename = response.getString("image");
//                                imageUrl = url + "downloadProfilePic/" + imagename; // profileImageUrl
//                                Picasso.with(getApplicationContext()).load(imageUrl).into(ivProfilePic);

//                                if (ivProfilePic.getDrawable() == null) {
//                                    ivProfilePic.setImageResource(R.mipmap.ic_profile);
//                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            String err = error.toString();
                            if (err.equals("com.android.volley.AuthFailureError")) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                                sp.edit().clear().commit();
                                Intent intent = new Intent(ExamControllerProfileActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
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
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(ExamControllerProfileActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }
    private void getUserDetails() {
        if (commonCode.checkConnection(getApplicationContext().getApplicationContext())) {
            String jsonurl = url + "regteacherparent/getTeacherDetails/" ;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
            progressDialog = new ProgressDialog(ExamControllerProfileActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(false);
            final JSONObject object = new JSONObject();
//            try {
//                object.put("message", loginName);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, jsonurl,

                    object,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                id = "";
                                id = response.getString("id");
                                firstname = response.getString("firstname");
                                middlename = response.getString("middlename");
                                lastname = response.getString("lastname");
                                mothername = response.getString("mothername");
                                birthdate = response.getString("birthdate");
                                admissiondate = response.getString("admissiondate");
                                gender = response.getString("gender");
                                mobileno = response.getString("mobileno");
                                emailid = response.getString("emailid");
                                bloodgroup = response.getString("bloodGroup");
                                emergencymobileno = response.getString("emergencymobileno");
                                qualification = response.getString("qualification");
                                address = response.getString("address");
                                country = response.getString("country");
                                state = response.getString("state");
                                city = response.getString("city");
                                role = response.getString("role");
                                activeInactive = response.getBoolean("softDelete");

                                if (activeInactive) {
                                    switchBtn.setChecked(false);

                                } else {
                                    switchBtn.setChecked(true);
                                }
//                                if (role.equals("Student")) {
//                                    tvname.setText(Studentname);
//                                    tbrowParentName.setVisibility(View.VISIBLE);
//                                    dividerView.setVisibility(View.VISIBLE);
//                                    tvParentsInfo.setVisibility(View.VISIBLE);
//                                    tvParentName.setText(firstname + " " + middlename + " " + " " + lastname);
//                                } else {


//                                }


                                tvName.setText(firstname + " " + middlename + " " + " " + lastname);
                                tvMotherName.setText(mothername);
                                tvDob.setText(birthdate);
                                tvAdmissionDate.setText(admissiondate);
                                tvGender.setText(gender);
                                tvEmail.setText(emailid);
                                tvMobileNo.setText(mobileno);
                                tvBloodGroup.setText(bloodgroup);
                                tvEmergencyMobNo.setText(emergencymobileno);
                                tvQualification.setText(qualification);
                                tvCountry.setText(country);
                                tvState.setText(state);
                                tvCity.setText(city);
                                tvAddress.setText(address);


//                                String imagename = response.getString("image");
//                                imageUrl = url + "downloadProfilePic/" + imagename; // profileImageUrl
//                                Picasso.with(getApplicationContext()).load(imageUrl).into(ivProfilePic);

                                if (ivProfilePic.getDrawable() == null) {
                                    ivProfilePic.setImageResource(R.mipmap.ic_profile);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            String err = error.toString();
                            if (err.equals("com.android.volley.AuthFailureError")) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.tokenExpire), Toast.LENGTH_LONG).show();

                                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                                sp.edit().clear().commit();
                                Intent intent = new Intent(ExamControllerProfileActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
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
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(ExamControllerProfileActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

}
