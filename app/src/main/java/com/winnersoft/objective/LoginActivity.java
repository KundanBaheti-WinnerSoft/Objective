package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp, tvRecoverPassword;
    EditText edtEmail, edtPassword;
    public static String u_email = "", u_password = "";
    private SharedPreferences sharedPreferences;
    String SecurityToken, roleDescription, roleName, roleId, userRoleid, loginStatus, userRoleLoginName, userRoleSoftDelete,userId;
    Button btnLogin;
    private ProgressDialog progressDialog;
    Urllink urllink = new Urllink();
    CommonCode commonCode = new CommonCode();
    boolean result = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //full screen

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //hide task bar
        getSupportActionBar().hide();

        tvSignUp = findViewById(R.id.tv_sign_up);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
        tvRecoverPassword = findViewById(R.id.tv_forgot_password);
        tvRecoverPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);



        btnLogin = findViewById(R.id.btn_log_in);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);

        verifyStoragePermission();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    validation();

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }


    private void verifyStoragePermission(){
        result = Utility.checkPermission((Activity) LoginActivity.this);

    }


    private void validation() {
        //store email and password in string
        u_email = edtEmail.getText().toString();
        u_password = edtPassword.getText().toString();
        if (edtEmail.getText().toString().trim().equals("")) {
            edtEmail.setFocusableInTouchMode(true);
            edtEmail.setError(getResources().getString(R.string.thisFieldCannotBlank));
            edtEmail.requestFocus();
        } else if (!edtEmail.getText().toString().trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
            edtEmail.setError(getResources().getString(R.string.validMailid));
            edtEmail.requestFocus();
        }else if (edtPassword.getText().toString().trim().equals("")) {
            edtPassword.setFocusableInTouchMode(true);
            edtPassword.setError(getResources().getString(R.string.thisFieldCannotBlank));
            edtPassword.requestFocus();
        } else if ((edtEmail.getText().toString().trim().equals("")) && (edtPassword.getText().toString().trim().equals(""))) {
            edtEmail.setFocusableInTouchMode(true);
            edtEmail.setError(getResources().getString(R.string.thisFieldCannotBlank));
            edtEmail.requestFocus();
            edtPassword.setFocusableInTouchMode(true);
            edtPassword.setError(getResources().getString(R.string.thisFieldCannotBlank));
            edtPassword.requestFocus();
        } else {
            if ((!(u_email.equals("")) && ((!u_password.equals(""))))) {
                save();
            }
        }
    }

    public void save() {
        if (commonCode.checkConnection(LoginActivity.this)) {
            String jsonurl = urllink.url + "user/authenticate";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            Map<String, String> jsonParams = new HashMap<String, String>();
            jsonParams.put("username", u_email);
            jsonParams.put("password", u_password);
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, jsonurl, new JSONObject(jsonParams), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //{"token":"admin:1601023318212:0f71dbb44911cfd9cde3eac82dc564df","roleNames":[{"id":1,"role":{"id":1,"roleDescription":"admin","roleName":"admin"},"userRole":{"id":1,"loginName":"admin","language":"Vietnamese","userType":{"userTypeName":"admin","id":1},"softDelete":false,"roles":[{"id":1,"roleDescription":null,"roleName":null}],"userRoles":null}}],"language":"Login Successfull"}
                    progressDialog.dismiss();
                    String re = response.toString();
                    try {
                        SecurityToken = (String) response.getString("token");
                        loginStatus = (String) response.getString("language");
                        if (SecurityToken.equals("Invalid UserName Or Password")) {
                            commonCode.AlertDialog_Pbtn(LoginActivity.this, getResources().getString(R.string.incorrectUsernamePassword), getResources().getString(R.string.plsEnterCorrectUsernamePassword), getResources().getString(R.string.ok));
                        } else if (loginStatus.equalsIgnoreCase("Deactiveted")) {
                            commonCode.AlertDialog_Pbtn(LoginActivity.this, getResources().getString(R.string.deactivated), getResources().getString(R.string.uRTemporarilyDeactivatedPlsContactAdmin), getResources().getString(R.string.ok));

                        }else if (loginStatus.equalsIgnoreCase("Login Successful")) {

                            JSONArray array = response.getJSONArray("roleNames");
                            JSONObject object = array.getJSONObject(0);
//                            userId=object.getString("id");
                            JSONObject roleObj = object.getJSONObject("role");
                            roleId = roleObj.getString("id");
                            roleDescription = roleObj.getString("roleDescription");
                            roleName = roleObj.getString("roleName");

                            JSONObject userRoleObj = object.getJSONObject("userRole");
                            userRoleid = userRoleObj.getString("id");
                            userRoleLoginName = userRoleObj.getString("loginName");
                            userRoleSoftDelete = userRoleObj.getString("softDelete");


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("securitytoken", SecurityToken);
                            editor.putString("username", u_email);
                            editor.putString("password", u_password);
                            editor.putString("userId",userRoleid);
                            editor.putString("roleName", roleName);
                            editor.putString("roleId", roleId);
                            editor.commit();
                            checkAndRedirect();

//                            getLoginDetails();


                            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //startActivity(intent);
                            //finish();

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
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            });
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(LoginActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    public void checkAndRedirect() {
        if (sharedPreferences.contains("username") && (sharedPreferences.contains("password"))) {
            String userTypeName = sharedPreferences.getString("roleName", "");
            //Check If Admin Change Dashboard
            if (userTypeName.equals("SuperAdmin")) {
//                Intent intent = new Intent(LoginActivity.this, ExamControllerDashoard.class);

                Intent intent = new Intent(LoginActivity.this, AdminHome.class);
                startActivity(intent);
                finish();
            }
            else if (userTypeName.equals("student")) {
                Intent intent = new Intent(LoginActivity.this, StudentDashboard.class);
                startActivity(intent);
                finish();
            } else if (userTypeName.equals("Exam Controller")) {
                Intent intent = new Intent(LoginActivity.this, ExamControllerDashoard.class);
                startActivity(intent);
                finish();
            }
//            else if (userTypeName.equals("teacher")) {
//                Intent intent = new Intent(LoginActivity.this, TeacherDashboard.class);
//                startActivity(intent);
//                finish();
//            }
//            else if (userTypeName.equals("user")) {
//                Intent intent = new Intent(LoginActivity.this, UserDashboardActivity.class);
//                startActivity(intent);
//                finish();
//            }
        }
    }


}
