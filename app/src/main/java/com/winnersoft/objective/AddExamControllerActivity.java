package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AddExamControllerActivity extends AppCompatActivity {
    EditText edtFName, edtMName, edtLName, edtMotherName, edtDob, edtDateofAdmission, edtEmail, edtMobileNo, edtEmerNumber, edtQualification, edtAddress, edtCountry;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    String rbValue = "", jsonurl = "";
    private String role = "", firstName = "", middleName = "", lastName = "", motherName = "", dateOfBirth = "", dateOfAdmission = "", emailId = "", mobileNo = "", emrMobileNo = "", qualification = "", address = "";
    MaterialSpinner spnCountry, spnState, spnDistrict, spnBloodGrp, spnQualification;
    String countryName = "", stateName = "", districtName = "", blood_group = "";
    int countryId = 0, stateId = 0, districtId = 0;
    private Button btnReg;
    CommonCode commonCode = new CommonCode();
    private Urllink urllink = new Urllink();
    private ProgressDialog progressDialog;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> qualArrayList = new ArrayList<>();

    final ArrayList<HashMap<String, String>> arraymapState = new ArrayList<>();
    ArrayList<String> nameListState = new ArrayList<>();

    final ArrayList<HashMap<String, String>> arraymapDistrict = new ArrayList<>();
    ArrayList<String> nameListDistrict = new ArrayList<>();

    //Initialize calender
    Calendar calendar = Calendar.getInstance();
    //get year
    final int year = calendar.get(Calendar.YEAR);
    //get month
    final int month = calendar.get(Calendar.MONTH);
    //get day
    final int day = calendar.get(Calendar.DAY_OF_MONTH);
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_controller);
        getSupportActionBar().hide();
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");

        InputFilter filtertxt = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };

        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rbValue = "";
                rbValue = "Male";
            }
        });
        rbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rbValue = "";
                rbValue = "Female";
            }
        });

        arrayList.add("A+");
        arrayList.add("B+");
        arrayList.add("AB+");
        arrayList.add("O+");
        arrayList.add("A-");
        arrayList.add("B-");
        arrayList.add("AB-");
        arrayList.add("O-");

        qualArrayList.add("10th/Below");
        qualArrayList.add("12th");
        qualArrayList.add("Diploma");
        qualArrayList.add("UG");
        qualArrayList.add("PG");
        qualArrayList.add("Phd");

        spnState = findViewById(R.id.spn_state);
        spnDistrict = findViewById(R.id.spn_district);
        spnQualification = findViewById(R.id.spn_qualification);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, qualArrayList);
        spnQualification.setAdapter(stringArrayAdapter);
        spnQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String qual = parent.getItemAtPosition(position).toString();
                if (!qual.equalsIgnoreCase(getResources().getString(R.string.selQualification))) {
                    qualification = qual;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnBloodGrp = findViewById(R.id.spn_blood_group);
        ArrayAdapter<String> stringArrayAdapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        spnBloodGrp.setAdapter(stringArrayAdapter1);
        spnBloodGrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String blood_group1 = parent.getItemAtPosition(position).toString();
                if (!blood_group1.equals(getResources().getString(R.string.selBloodGrp))) {
                    blood_group = blood_group1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        edtFName = findViewById(R.id.edt_fname);
        edtFName.setFilters(new InputFilter[]{filtertxt});
        edtMName = findViewById(R.id.edt_mname);
        edtMName.setFilters(new InputFilter[]{filtertxt});
        edtLName = findViewById(R.id.edt_lname);
        edtLName.setFilters(new InputFilter[]{filtertxt});
        edtMotherName = findViewById(R.id.edt_mother_name);
        edtMotherName.setFilters(new InputFilter[]{filtertxt});

        edtDob = findViewById(R.id.edt_birth_date);
        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddExamControllerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String dob=dayOfMonth+"/"+month+"/"+year;
                        String dob = year + "-" + (month + 1) + "-" + dayOfMonth;
                        edtDob.setText(dob);
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        edtDateofAdmission = findViewById(R.id.edt_date_admission);
        edtDateofAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddExamControllerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String doa=dayOfMonth+"/"+month+"/"+year;
                        String doa = year + "-" + (month + 1) + "-" + dayOfMonth;

                        edtDateofAdmission.setText(doa);
                    }
                }, year, month, day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        edtEmail = findViewById(R.id.edt_email);
        edtMobileNo = findViewById(R.id.edt_mobileno);
        edtEmerNumber = findViewById(R.id.edt_emr_mobileno);
//        edtQualification=findViewById(R.id.edt_qualification);
        edtCountry = findViewById(R.id.edt_country);

        edtAddress = findViewById(R.id.edt_address);
        edtAddress.setMovementMethod(new ScrollingMovementMethod());


        btnReg = findViewById(R.id.btn_sign_in);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        getAllState();
    }

    private void getAllState() {
        arraymapState.clear();
        nameListState.clear();
        countryId = 1269750;
        if (commonCode.checkConnection(AddExamControllerActivity.this)) {
            final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
            String jsonurl = urllink.url + "user/StateList/" + countryId;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(AddExamControllerActivity.this);
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
                            JSONArray jsonArray = response.getJSONArray("geonames");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("geonameId", obj.getString("geonameId"));
                                hashMap.put("name", obj.getString("name"));
                                String name = obj.getString("name");
                                nameListState.add(name);

                                arraymapState.add(hashMap);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(AddExamControllerActivity.this, android.R.layout.simple_spinner_item, nameListState);
                        spnState.setAdapter(stringArrayAdapter2);
                        // spnState.setSelection(21);
                        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String valuename = parent.getItemAtPosition(position).toString();
                                spnDistrict.setSelection(0);
                                if (!valuename.equals(getResources().getString(R.string.selState))) {
                                    stateId = Integer.parseInt(arraymapState.get(position).get("geonameId"));
                                    stateName = (arraymapState.get(position).get("name"));
                                    getAllDistrict();
                                    spnDistrict.setVisibility(View.VISIBLE);
                                    spnDistrict.setSelection(0);
                                } else {
                                    stateId = 0;
                                    stateName = "";
                                    spnDistrict.setSelection(0);
                                }

                                //    Toast.makeText(RegistrationActivity.this, arraymapClass.get(position).get("id"), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
//                        commonCode.AlertDialog_Pbtn(RegistrationActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.complaintsNotFound), getResources().getString(R.string.ok));
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
            });
            requestQueue.add(jsonArrayRequest);
        } else {
            commonCode.AlertDialog_Pbtn(AddExamControllerActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void getAllDistrict() {
//        if (districtId > 0) {
//            spnDistrict.setVisibility(View.GONE);
//
//        } else {
//            spnDistrict.setVisibility(View.VISIBLE);

            arraymapDistrict.clear();
            nameListDistrict.clear();
            if (commonCode.checkConnection(AddExamControllerActivity.this)) {
                final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
                String jsonurl = urllink.url + "user/districtList/" + stateId;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                progressDialog = new ProgressDialog(AddExamControllerActivity.this);
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
                                JSONArray jsonArray = response.getJSONArray("geonames");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("geonameId", obj.getString("geonameId"));
                                    hashMap.put("name", obj.getString("name"));
                                    String name = obj.getString("name");
                                    nameListDistrict.add(name);

                                    arraymapDistrict.add(hashMap);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(AddExamControllerActivity.this, android.R.layout.simple_spinner_item, nameListDistrict);
                            spnDistrict.setAdapter(stringArrayAdapter2);
                            spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String valuename = parent.getItemAtPosition(position).toString();
                                    if (!valuename.equals(getResources().getString(R.string.selDistrict))) {
                                        districtId = Integer.parseInt(arraymapDistrict.get(position).get("geonameId"));
                                        districtName = (arraymapDistrict.get(position).get("name"));
                                    } else {
                                        districtId = 0;
                                        districtName = "";
                                    }

                                    //    Toast.makeText(RegistrationActivity.this, arraymapClass.get(position).get("id"), Toast.LENGTH_LONG).show();

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        } else {
//                        commonCode.AlertDialog_Pbtn(RegistrationActivity.this, getResources().getString(R.string.notFound), getResources().getString(R.string.complaintsNotFound), getResources().getString(R.string.ok));
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
                });
                requestQueue.add(jsonArrayRequest);
            } else {
                commonCode.AlertDialog_Pbtn(AddExamControllerActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
            }
//        }
    }

    private void validation() {
//                [7-9]{9};
//        countryName = "India";
//        int radioButtonId= rgGender.getCheckedRadioButtonId();
//        rbGender=(RadioButton)findViewById(radioButtonId);
//        rbValue=(String)rbGender.getText().toString();

        firstName = edtFName.getText().toString();
        middleName = edtMName.getText().toString();
        lastName = edtLName.getText().toString();
        motherName = edtMotherName.getText().toString();
        dateOfBirth = edtDob.getText().toString();
        dateOfAdmission = edtDateofAdmission.getText().toString();
        emailId = edtEmail.getText().toString();
        mobileNo = edtMobileNo.getText().toString();
        emrMobileNo = edtEmerNumber.getText().toString();
//        qualification=edtQualification.getText().toString();
        countryName = edtCountry.getText().toString();
        address = edtAddress.getText().toString();

        if (!commonCode.isValidString(AddExamControllerActivity.this, address) || !commonCode.isValidString(AddExamControllerActivity.this, stateName)|| !commonCode.isValidString(AddExamControllerActivity.this, districtName) || !commonCode.isValidString(AddExamControllerActivity.this, blood_group) || !edtEmerNumber.getText().toString().trim().matches("^[6789]\\d{9}$") || !commonCode.isValidMobileNo(AddExamControllerActivity.this, emrMobileNo) || !commonCode.isValidString(AddExamControllerActivity.this, qualification) || !edtMobileNo.getText().toString().trim().matches("^[6789]\\d{9}$") || !commonCode.isValidMobileNo(AddExamControllerActivity.this, mobileNo) || !edtEmail.getText().toString().trim().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") || edtEmail.getText().toString().trim().equals("") || rbValue.equals("") || dateOfAdmission.equals("") || dateOfBirth.equals("") || !commonCode.isValidString(AddExamControllerActivity.this, motherName) || !commonCode.isValidString(AddExamControllerActivity.this, firstName) || !commonCode.isValidString(AddExamControllerActivity.this, middleName) || !commonCode.isValidString(AddExamControllerActivity.this, lastName)) {
            if (!commonCode.isValidString(AddExamControllerActivity.this, firstName)) {
                edtFName.setError(getResources().getString(R.string.plsEnterFirstName));
                edtFName.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, middleName)) {
                edtMName.setError(getResources().getString(R.string.plsEnterMiddleName));
                edtMName.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, lastName)) {
                edtLName.setError(getResources().getString(R.string.plsEnterLastName));
                edtLName.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, motherName)) {
                edtMotherName.setError(getResources().getString(R.string.plsEnterMotherName));
                edtMotherName.requestFocus();
            }
            if (dateOfBirth.equals("")) {
                edtDob.setFocusableInTouchMode(true);
                edtDob.setError(getResources().getString(R.string.thisFieldCannotBlank));
                edtDob.requestFocus();
            }
            if (dateOfAdmission.equals("")) {
                edtDateofAdmission.setFocusableInTouchMode(true);
                edtDateofAdmission.setError(getResources().getString(R.string.thisFieldCannotBlank));
                edtDateofAdmission.requestFocus();
            }
            if (rbValue.equals("")) {
                rbMale.setError(getResources().getString(R.string.plsSelectGender));
                rbMale.requestFocus();
            }
            if (edtEmail.getText().toString().trim().equals("")) {
                edtEmail.setFocusableInTouchMode(true);
                edtEmail.setError(getResources().getString(R.string.thisFieldCannotBlank));
                edtEmail.requestFocus();
            }
//        else if (!edtEmail.getText().toString().trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")) {
//            edtEmail.setError(getResources().getString(R.string.validMailid));
//            edtEmail.requestFocus();
//        }
            if (!edtEmail.getText().toString().trim().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
                edtEmail.setError(getResources().getString(R.string.validMailid));
                edtEmail.requestFocus();
            }
            if (!commonCode.isValidMobileNo(AddExamControllerActivity.this, mobileNo)) {
                edtMobileNo.setError(getResources().getString(R.string.plsEnterMobNo));
                edtMobileNo.requestFocus();
            }
            if (!edtMobileNo.getText().toString().trim().matches("^[6789]\\d{9}$")) {
                edtMobileNo.setError(getResources().getString(R.string.plsEnterValidMobNo));
                edtMobileNo.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, qualification)) {
                spnQualification.setError(getResources().getString(R.string.plsSelectQualification));
                spnQualification.requestFocus();
            }
            if (!commonCode.isValidMobileNo(AddExamControllerActivity.this, emrMobileNo)) {
                edtEmerNumber.setError(getResources().getString(R.string.plsEnterMobNo));
                edtEmerNumber.requestFocus();
            }
            if (!edtEmerNumber.getText().toString().trim().matches("^[6789]\\d{9}$")) {
                edtEmerNumber.setError(getResources().getString(R.string.plsEnterValidMobNo));
                edtEmerNumber.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, blood_group)) {
                spnBloodGrp.setError(getResources().getString(R.string.plsSelectBloodGrp));
                spnBloodGrp.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, stateName)) {
                spnState.setError(getResources().getString(R.string.plsSelectState));
                spnState.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, districtName)) {
                spnDistrict.setError(getResources().getString(R.string.plsSelectDistrict));
                spnDistrict.requestFocus();
            }
            if (!commonCode.isValidString(AddExamControllerActivity.this, address)) {
                edtAddress.setError(getResources().getString(R.string.plsEnterAddress));
                edtAddress.requestFocus();
            }
        }else{
            jsonurl = urllink.url + "user/userRegistration";
            register();
        }

//        if (!commonCode.isValidString(AddExamControllerActivity.this, firstName)) {
//            edtFName.setError(getResources().getString(R.string.plsEnterFirstName));
//            edtFName.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, middleName)) {
//            edtMName.setError(getResources().getString(R.string.plsEnterMiddleName));
//            edtMName.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, lastName)) {
//            edtLName.setError(getResources().getString(R.string.plsEnterLastName));
//            edtLName.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, motherName)) {
//            edtMotherName.setError(getResources().getString(R.string.plsEnterMotherName));
//            edtMotherName.requestFocus();
//        } else if (dateOfBirth.equals("")) {
//            edtDob.setFocusableInTouchMode(true);
//            edtDob.setError(getResources().getString(R.string.thisFieldCannotBlank));
//            edtDob.requestFocus();
//        } else if (dateOfAdmission.equals("")) {
//            edtDateofAdmission.setFocusableInTouchMode(true);
//            edtDateofAdmission.setError(getResources().getString(R.string.thisFieldCannotBlank));
//            edtDateofAdmission.requestFocus();
//        } else if (rbValue.equals("")) {
//            rbMale.setError(getResources().getString(R.string.plsSelectGender));
//            rbMale.requestFocus();
//        } else if (edtEmail.getText().toString().trim().equals("")) {
//            edtEmail.setFocusableInTouchMode(true);
//            edtEmail.setError(getResources().getString(R.string.thisFieldCannotBlank));
//            edtEmail.requestFocus();
//        }
//        else if (!edtEmail.getText().toString().trim().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
//            edtEmail.setError(getResources().getString(R.string.validMailid));
//            edtEmail.requestFocus();
//        } else if (!commonCode.isValidMobileNo(AddExamControllerActivity.this, mobileNo)) {
//            edtMobileNo.setError(getResources().getString(R.string.plsEnterMobNo));
//            edtMobileNo.requestFocus();
//        } else if (!edtMobileNo.getText().toString().trim().matches("^[6789]\\d{9}$")) {
//            edtMobileNo.setError(getResources().getString(R.string.plsEnterValidMobNo));
//            edtMobileNo.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, qualification)) {
//            spnQualification.setError(getResources().getString(R.string.plsSelectQualification));
//            spnQualification.requestFocus();
//        } else if (!commonCode.isValidMobileNo(AddExamControllerActivity.this, emrMobileNo)) {
//            edtEmerNumber.setError(getResources().getString(R.string.plsEnterMobNo));
//            edtEmerNumber.requestFocus();
//        } else if (!edtEmerNumber.getText().toString().trim().matches("^[6789]\\d{9}$")) {
//            edtEmerNumber.setError(getResources().getString(R.string.plsEnterValidMobNo));
//            edtEmerNumber.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, blood_group)) {
//            spnBloodGrp.setError(getResources().getString(R.string.plsSelectBloodGrp));
//            spnBloodGrp.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, stateName)) {
//            spnState.setError(getResources().getString(R.string.plsSelectState));
//            spnState.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, districtName)) {
//            spnDistrict.setError(getResources().getString(R.string.plsSelectDistrict));
//            spnDistrict.requestFocus();
//        } else if (!commonCode.isValidString(AddExamControllerActivity.this, address)) {
//            edtAddress.setError(getResources().getString(R.string.plsEnterAddress));
//            edtAddress.requestFocus();
//        } else {
//            jsonurl = urllink.url + "user/userRegistration";
//            register();
//        }
    }


    private void register() {
        if (commonCode.checkConnection(AddExamControllerActivity.this)) {
            progressDialog = new ProgressDialog(AddExamControllerActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JSONObject jsonParams = new JSONObject();
            try {
                //jsonParams.put("role", role);
                jsonParams.put("first_name", firstName);
                jsonParams.put("middle_name", middleName);
                jsonParams.put("last_name", lastName);
                jsonParams.put("m_name", motherName);
                jsonParams.put("mobile", mobileNo);
                jsonParams.put("email", emailId);
                jsonParams.put("address", address);

                jsonParams.put("gender", rbValue);
                jsonParams.put("nationality", countryName);
                jsonParams.put("state", stateName);
                jsonParams.put("city", districtName);
                jsonParams.put("bloodGroup", blood_group);
                jsonParams.put("highestQualification", qualification);
                jsonParams.put("birthdate", dateOfBirth);
                jsonParams.put("admissionDate", dateOfAdmission);
                jsonParams.put("emergencyMobNo", emrMobileNo);

//                "bloodGroup":"o+ve",
//                        "highestQualification":"be",
//                        "birthdate":"1996-11-11",
//                        "admissionDate":"2019-10-10",
//                        "emergencyMobNo":"123456756"


//                if (role.equals("Volunteer")) {
//                    JSONObject adminObj = new JSONObject();
//                    adminObj.put("id", userId);
//                    jsonParams.put("admin", adminObj);
//                }
                //jsonParams.put("token", newToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, jsonurl,

                    jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String message = response.getString("message");
                        if (message.equals("Registered Successfully")) {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.registeredSuccessfully), Toast.LENGTH_LONG).show();
                            regSuccessPopup();
                        } else if (message.equals("exam controller already exist")) {
//                            mobNoAlreadyPopup();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.userAlreadyExist), Toast.LENGTH_SHORT).show();

                        } else if (message.equals("Failed to register")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(AddExamControllerActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            }) {
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
            commonCode.AlertDialog_Pbtn(AddExamControllerActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void regSuccessPopup() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_reg_success, null);

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
            }
        });

    }

}
