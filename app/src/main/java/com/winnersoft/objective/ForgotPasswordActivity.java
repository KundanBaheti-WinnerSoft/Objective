package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edtEmail;
    Button btnBackToLogin, btnSend;
    private CommonCode commonCode = new CommonCode();
    private Urllink urllink = new Urllink();
    private ProgressDialog progressDialog;
    private String url = urllink.url;
    private String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);
        edtEmail=findViewById(R.id.edt_forgotemail);
        btnBackToLogin=findViewById(R.id.btn_backtologin);
        btnSend=findViewById(R.id.btn_send);

        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=edtEmail.getText().toString();
                if (edtEmail.getText().toString().trim().equals("")) {
                    edtEmail.setFocusableInTouchMode(true);
                    edtEmail.setError(getResources().getString(R.string.thisFieldCannotBlank));
                    edtEmail.requestFocus();
                } else if (!edtEmail.getText().toString().trim().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
                    edtEmail.setError(getResources().getString(R.string.validMailid));
                    edtEmail.requestFocus();
                }else {
//                    emailNotExistPopup();
//                    checkExistOrNot();
                    sendmail();
//                    sendSuccessPopup();
                }
            }
        });


    }

    private void checkExistOrNot() {
        if (commonCode.checkConnection(ForgotPasswordActivity.this)) {
            String jsonurl = urllink.url + "user/forgotPassword" + email;
            progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, jsonurl,

                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String message = response.getString("message");
                        if (message.equals("MobNo Already exists")) {
                            sendmail();
                        } else if (message.equals("MobNo not exists")) {
                            emailNotExistPopup();
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
                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            });    //This is for Headers If You Needed
//            {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("x-Auth-token", SecurityToken);
//                    return params;
//                }
//            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
        } else {
            commonCode.AlertDialog_Pbtn(ForgotPasswordActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void sendmail() {
        if (commonCode.checkConnection(ForgotPasswordActivity.this)) {
            final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
            String jsonurl = url + "user/forgotPassword";
            RequestQueue requestQueue = Volley.newRequestQueue(ForgotPasswordActivity.this);
            progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            JSONObject object = new JSONObject();
            try {

                object.put("loginName", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, jsonurl, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (response.length() == 0) {
                        commonCode.AlertDialog_Pbtn(ForgotPasswordActivity.this, getResources().getString(R.string.notRegister), getResources().getString(R.string.plsRegiFirst), getResources().getString(R.string.ok));
                    } else {
                        try {
                            String message = response.getString("message");
                            if (message.equals("Email id not found")) {
                                Toast.makeText(getApplicationContext(), R.string.emailNotExist, Toast.LENGTH_LONG).show();
                            } else if (message.equals("Failed to Update password")) {
                                Toast.makeText(getApplicationContext(), R.string.failedTryAgain, Toast.LENGTH_LONG).show();
                            } else if (message.equals("Your email has been sent to your registered email address")) {
                                sendSuccessPopup();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.failedTryAgain, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    String err = error.toString();

                    if (err.equals("com.android.volley.AuthFailureError")) {
                        Toast.makeText(getApplicationContext(), R.string.tokenExpire, Toast.LENGTH_LONG).show();

                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().clear().commit();
                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.serverError, Toast.LENGTH_LONG).show();
                    }
                }
            }); //This is for Headers If You Needed
           /* {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-Auth-token", SecurityToken);
                    return params;
                }
            };*/
            requestQueue.add(jsonArrayRequest);
        } else {
            commonCode.AlertDialog_Pbtn(ForgotPasswordActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void emailNotExistPopup() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.email_not_exist_popup, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        Button buttonConfirm = (Button) confirmDialog.findViewById(R.id.btn_ok);

        //Creating an alertdialog builder
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    private void sendSuccessPopup(){

        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_pass_send_success, null);

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
//                Intent abc =new Intent(ForgotPasswordActivity.this,LoginActivity.class);
//                startActivity(abc);
//                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
