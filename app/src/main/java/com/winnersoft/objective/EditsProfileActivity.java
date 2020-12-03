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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class EditsProfileActivity extends AppCompatActivity {
    public Bundle getBundle = null;
    private String firstname,middlename,lastname,mothername,birthdate,admissiondate,gender,emailid,mobileno,bloodgroup,emergencymobileno,qualification,country,state,city,address,role,id;
    private CommonCode commonCode = new CommonCode();
    private Urllink urllink = new Urllink();
    private String url = urllink.url;
    private ProgressDialog progressDialog;
    String SecurityToken;
    private SharedPreferences sharedPreferences;
    RadioButton rbGender,rbMale,rbFemale;
    EditText edtFName,edtMName,edtLName,edtMotherName,edtDob,edtDateofAdmission, edtEmail, edtMobileNo,edtEmerNumber,edtQualification,edtAddress,edtCountry;
    private Button btnCancel, btnUpdate;
    String jsonurl;
    //Initialize calender
    Calendar calendar=Calendar.getInstance();
    //get year
    final int year=calendar.get(Calendar.YEAR);
    //get month
    final int month=calendar.get(Calendar.MONTH);
    //get day
    final int day=calendar.get(Calendar.DAY_OF_MONTH);
    MaterialSpinner spnCountry, spnState, spnDistrict,spnBloodGrp,spnQualification;
    String countryName = "", stateName = "", districtName = "";
    int countryId = 0, stateId = 0, districtId = 0;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> qualArrayList = new ArrayList<>();

    final ArrayList<HashMap<String, String>> arraymapState = new ArrayList<>();
    ArrayList<String> nameListState = new ArrayList<>();

    final ArrayList<HashMap<String, String>> arraymapDistrict = new ArrayList<>();
    ArrayList<String> nameListDistrict = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edits_profile);
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

        rbMale=findViewById(R.id.rb_male);
        rbFemale=findViewById(R.id.rb_female);
        spnState = findViewById(R.id.spn_state);
        spnDistrict = findViewById(R.id.spn_district);
        spnQualification=findViewById(R.id.spn_qualification);
        spnBloodGrp=findViewById(R.id.spn_blood_group);
        edtFName=findViewById(R.id.edt_fname);
        edtFName.setFilters(new InputFilter[]{filtertxt});
        edtMName=findViewById(R.id.edt_mname);
        edtMName.setFilters(new InputFilter[]{filtertxt});
        edtLName=findViewById(R.id.edt_lname);
        edtLName.setFilters(new InputFilter[]{filtertxt});
        edtMotherName=findViewById(R.id.edt_mother_name);
        edtMotherName.setFilters(new InputFilter[]{filtertxt});

        edtDob=findViewById(R.id.edt_birth_date);
        edtDateofAdmission=findViewById(R.id.edt_date_admission);
        edtEmail=findViewById(R.id.edt_email);
        edtMobileNo=findViewById(R.id.edt_mobileno);
        edtEmerNumber=findViewById(R.id.edt_emr_mobileno);
//        edtQualification=findViewById(R.id.edt_qualification);
        edtCountry=findViewById(R.id.edt_country);

        edtAddress=findViewById(R.id.edt_address);
        edtAddress.setMovementMethod(new ScrollingMovementMethod());

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");


        getBundle = this.getIntent().getExtras();

            id = getBundle.getString("id");
            role=getBundle.getString("userTypeName");
            firstname=getBundle.getString("first_name");
            middlename=getBundle.getString("middle_name");
            lastname=getBundle.getString("last_name");
            mothername=getBundle.getString("m_name");
            birthdate=getBundle.getString("birthdate");
            admissiondate=getBundle.getString("admissionDate");
            gender=getBundle.getString("gender");
            emailid=getBundle.getString("email");
            mobileno=getBundle.getString("mobile");
            bloodgroup=getBundle.getString("bloodGroup");
            emergencymobileno=getBundle.getString("emergencyMobNo");
            qualification=getBundle.getString("highestQualification");
            country=getBundle.getString("nationality");
            state=getBundle.getString("state");
            city=getBundle.getString("city");
            address=getBundle.getString("address");

        // State
//        JSONObject stateobject = districtobject.getJSONObject("state");
//        stateName = stateobject.getString("state_name");
//        stateId = stateobject.getString("id");
//        ArrayList<String> strings = new ArrayList<>();
//        strings.add(state);
//        ArrayAdapter<String> stringArrayAdapterState = new ArrayAdapter(EditsProfileActivity.this, android.R.layout.simple_spinner_item, strings);
//        strings.add(state);
//        spnState.setAdapter(stringArrayAdapterState);



        edtFName.setText(firstname);
        edtMName.setText(middlename);
        edtLName.setText(lastname);
        edtMotherName.setText(mothername);
        edtDob.setText(birthdate);
        edtDateofAdmission.setText(admissiondate);
        if (gender.contains("female")) {
            rbMale.setChecked(false);
            rbFemale.setChecked(true);
        } else {
            rbMale.setChecked(true);
            rbFemale.setChecked(false);
        }
        edtEmail.setText(emailid);
        edtMobileNo.setText(mobileno);

        arrayList.add("A+");
        arrayList.add("B+");
        arrayList.add("AB+");
        arrayList.add("O+");
        arrayList.add("A-");
        arrayList.add("B-");
        arrayList.add("AB-");
        arrayList.add("O-");
        ArrayAdapter<String> stringArrayAdapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        spnBloodGrp.setAdapter(stringArrayAdapter1);
        spnBloodGrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String blood_group1 = parent.getItemAtPosition(position).toString();

                if (!blood_group1.equals(getResources().getString(R.string.selBloodGrp))) {
                    bloodgroup = blood_group1;
                }else {
                    bloodgroup="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (bloodgroup.equals("")) {

        } else {
            switch (bloodgroup) {
                case "A+":
                    spnBloodGrp.setSelection(1);
                    break;
                case "B+":
                    spnBloodGrp.setSelection(2);
                    break;
                case "AB+":
                    spnBloodGrp.setSelection(3);
                    break;
                case "O+":
                    spnBloodGrp.setSelection(4);
                    break;
                case "A-":
                    spnBloodGrp.setSelection(5);
                    break;
                case "B-":
                    spnBloodGrp.setSelection(6);
                    break;
                case "AB-":
                    spnBloodGrp.setSelection(7);
                    break;
                case "O-":
                    spnBloodGrp.setSelection(8);
                    break;
            }
        }
        edtEmerNumber.setText(emergencymobileno);

        qualArrayList.add("10th/Below");
        qualArrayList.add("12th");
        qualArrayList.add("Diploma");
        qualArrayList.add("UG");
        qualArrayList.add("PG");
        qualArrayList.add("Phd");
        spnQualification=findViewById(R.id.spn_qualification);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, qualArrayList);
        spnQualification.setAdapter(stringArrayAdapter);
        spnQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String qual=parent.getItemAtPosition(position).toString();
                if(!qual.equalsIgnoreCase(getResources().getString(R.string.selQualification))){
                    qualification=qual;
                }else {
                    qualification="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        if (qualification.equals("")) {
        } else {
            switch (qualification) {
                case "10th/Below":
                    spnQualification.setSelection(1);
                    break;
                case "12th":
                    spnQualification.setSelection(2);
                    break;
                case "Diploma":
                    spnQualification.setSelection(3);
                    break;
                case "UG":
                    spnQualification.setSelection(4);
                    break;
                case "PG":
                    spnQualification.setSelection(5);
                    break;
                case "Phd":
                    spnQualification.setSelection(6);
                    break;
            }
        }

        edtCountry.setText(country);
        edtAddress.setText(address);
        edtDob=findViewById(R.id.edt_birth_date);
        edtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        EditsProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dob=year+"-"+(month+1)+"-"+dayOfMonth;
                        edtDob.setText(dob);
                    }
                },year,month,day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        edtDateofAdmission=findViewById(R.id.edt_date_admission);
        edtDateofAdmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        EditsProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String doa=year+"-"+(month+1)+"-"+dayOfMonth;
                        edtDateofAdmission.setText(doa);
                    }
                },year,month,day);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

//        if (role.equalsIgnoreCase("Exam Controller")) {
////            getSupportActionBar().setTitle(getResources().getString(R.string.student));
//
//            jsonurl = url + "regparent/updateParent/" + id;
////            getAllClass();
//        }


        btnUpdate=findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
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
        if (commonCode.checkConnection(EditsProfileActivity.this)) {
            final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
            String jsonurl = urllink.url + "user/StateList/" + countryId;
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(EditsProfileActivity.this);
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



                        ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(EditsProfileActivity.this, android.R.layout.simple_spinner_item, nameListState);

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
            commonCode.AlertDialog_Pbtn(EditsProfileActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }
    private void getAllDistrict() {
//        if(districtId>0){
//            spnDistrict.setVisibility(View.GONE);
//
//        }else {
//            spnDistrict.setVisibility(View.VISIBLE);

            arraymapDistrict.clear();
            nameListDistrict.clear();
            if (commonCode.checkConnection(EditsProfileActivity.this)) {
                final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
                String jsonurl = urllink.url + "user/districtList/" + stateId;
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                progressDialog = new ProgressDialog(EditsProfileActivity.this);
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

                            ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(EditsProfileActivity.this, android.R.layout.simple_spinner_item, nameListDistrict);
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
                commonCode.AlertDialog_Pbtn(EditsProfileActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
            }
//        }
    }

    private void validation(){
        firstname=edtFName.getText().toString();
        middlename=edtMName.getText().toString();
        lastname=edtLName.getText().toString();
        mothername=edtMotherName.getText().toString();
        birthdate=edtDob.getText().toString();
        admissiondate=edtDateofAdmission.getText().toString();
        emailid=edtEmail.getText().toString();
        mobileno=edtMobileNo.getText().toString();
        emergencymobileno=edtEmerNumber.getText().toString();
//        qualification=edtQualification.getText().toString();
        countryName=edtCountry.getText().toString();
        address=edtAddress.getText().toString();
        if (!commonCode.isValidString(EditsProfileActivity.this, address) || !commonCode.isValidString(EditsProfileActivity.this, stateName)|| !commonCode.isValidString(EditsProfileActivity.this, districtName) || !commonCode.isValidString(EditsProfileActivity.this, bloodgroup) || !edtEmerNumber.getText().toString().trim().matches("^[6789]\\d{9}$") || !commonCode.isValidMobileNo(EditsProfileActivity.this, emergencymobileno) || !commonCode.isValidString(EditsProfileActivity.this, qualification) || !edtMobileNo.getText().toString().trim().matches("^[6789]\\d{9}$") || !commonCode.isValidMobileNo(EditsProfileActivity.this, mobileno) || !edtEmail.getText().toString().trim().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") || edtEmail.getText().toString().trim().equals("") || gender.equals("") || admissiondate.equals("") || birthdate.equals("") || !commonCode.isValidString(EditsProfileActivity.this, mothername) || !commonCode.isValidString(EditsProfileActivity.this, firstname) || !commonCode.isValidString(EditsProfileActivity.this, middlename) || !commonCode.isValidString(EditsProfileActivity.this, lastname)) {
            if (!commonCode.isValidString(EditsProfileActivity.this, firstname)) {
                edtFName.setError(getResources().getString(R.string.plsEnterFirstName));
                edtFName.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, middlename)) {
                edtMName.setError(getResources().getString(R.string.plsEnterMiddleName));
                edtMName.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, lastname)) {
                edtLName.setError(getResources().getString(R.string.plsEnterLastName));
                edtLName.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, mothername)) {
                edtMotherName.setError(getResources().getString(R.string.plsEnterMotherName));
                edtMotherName.requestFocus();
            }
            if (birthdate.equals("")) {
                edtDob.setFocusableInTouchMode(true);
                edtDob.setError(getResources().getString(R.string.thisFieldCannotBlank));
                edtDob.requestFocus();
            }
            if (admissiondate.equals("")) {
                edtDateofAdmission.setFocusableInTouchMode(true);
                edtDateofAdmission.setError(getResources().getString(R.string.thisFieldCannotBlank));
                edtDateofAdmission.requestFocus();
            }
            if (gender.equals("")) {
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
            if (!commonCode.isValidMobileNo(EditsProfileActivity.this, mobileno)) {
                edtMobileNo.setError(getResources().getString(R.string.plsEnterMobNo));
                edtMobileNo.requestFocus();
            }
            if (!edtMobileNo.getText().toString().trim().matches("^[6789]\\d{9}$")) {
                edtMobileNo.setError(getResources().getString(R.string.plsEnterValidMobNo));
                edtMobileNo.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, qualification)) {
                spnQualification.setError(getResources().getString(R.string.plsSelectQualification));
                spnQualification.requestFocus();
            }
            if (!commonCode.isValidMobileNo(EditsProfileActivity.this, emergencymobileno)) {
                edtEmerNumber.setError(getResources().getString(R.string.plsEnterMobNo));
                edtEmerNumber.requestFocus();
            }
            if (!edtEmerNumber.getText().toString().trim().matches("^[6789]\\d{9}$")) {
                edtEmerNumber.setError(getResources().getString(R.string.plsEnterValidMobNo));
                edtEmerNumber.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, bloodgroup)) {
                spnBloodGrp.setError(getResources().getString(R.string.plsSelectBloodGrp));
                spnBloodGrp.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, stateName)) {
                spnState.setError(getResources().getString(R.string.plsSelectState));
                spnState.requestFocus();
            }
//            if(districtName.equalsIgnoreCase(getResources().getString(R.string.selDistrict))){
//                spnDistrict.setError(getResources().getString(R.string.plsSelectDistrict));
//                spnDistrict.requestFocus();
//            }
            if (!commonCode.isValidString(EditsProfileActivity.this, districtName)) {
                spnDistrict.setError(getResources().getString(R.string.plsSelectDistrict));
                spnDistrict.requestFocus();
            }
            if (!commonCode.isValidString(EditsProfileActivity.this, address)) {
                edtAddress.setError(getResources().getString(R.string.plsEnterAddress));
                edtAddress.requestFocus();
            }
        }else{
            jsonurl = urllink.url + "user/update/"+id;
            updateinfo();
        }
//        if (!commonCode.isValidString(EditsProfileActivity.this, firstname)) {
//            edtFName.setError(getResources().getString(R.string.plsEnterFirstName));
//            edtFName.requestFocus();
//        } else if (!commonCode.isValidString(EditsProfileActivity.this, middlename)) {
//            edtMName.setError(getResources().getString(R.string.plsEnterMiddleName));
//            edtMName.requestFocus();
//        } else if (!commonCode.isValidString(EditsProfileActivity.this, lastname)) {
//            edtLName.setError(getResources().getString(R.string.plsEnterLastName));
//            edtLName.requestFocus();
//        }else if (!commonCode.isValidString(EditsProfileActivity.this, mothername)) {
//            edtMotherName.setError(getResources().getString(R.string.plsEnterMotherName));
//            edtMotherName.requestFocus();
//        } else if (birthdate.equals("")) {
////            edtDob.setFocusableInTouchMode(true);
//            edtDob.setError(getResources().getString(R.string.thisFieldCannotBlank));
//            edtDob.requestFocus();
//        } else if (admissiondate.equals("")) {
////            edtDateofAdmission.setFocusableInTouchMode(true);
//            edtDateofAdmission.setError(getResources().getString(R.string.thisFieldCannotBlank));
//            edtDateofAdmission.requestFocus();
//        }else if (gender.equals("")) {
//            rbMale.setError(getResources().getString(R.string.plsSelectGender));
//            rbMale.requestFocus();
//        }else if (edtEmail.getText().toString().trim().equals("")) {
//            edtEmail.setFocusableInTouchMode(true);
//            edtEmail.setError(getResources().getString(R.string.thisFieldCannotBlank));
//            edtEmail.requestFocus();
//        } else if (!edtEmail.getText().toString().trim().matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
//            edtEmail.setError(getResources().getString(R.string.validMailid));
//            edtEmail.requestFocus();
//        }else if (!edtMobileNo.getText().toString().trim().matches("^[6789]\\d{9}$")) {
//            edtMobileNo.setError(getResources().getString(R.string.plsEnterValidMobNo));
//            edtMobileNo.requestFocus();
//        } else if (!commonCode.isValidString(EditsProfileActivity.this, qualification)) {
//            spnQualification.setError(getResources().getString(R.string.plsSelectQualification));
//            spnQualification.requestFocus();
//        }else if (!edtEmerNumber.getText().toString().trim().matches("^[6789]\\d{9}$")) {
//            edtEmerNumber.setError(getResources().getString(R.string.plsEnterValidMobNo));
//            edtEmerNumber.requestFocus();
//        }else if (bloodgroup.equalsIgnoreCase("")) {
//            spnBloodGrp.setError(getResources().getString(R.string.plsSelectBloodGrp));
//            spnBloodGrp.requestFocus();
//        }else if (!commonCode.isValidString(EditsProfileActivity.this, stateName)) {
//            spnState.setError(getResources().getString(R.string.plsSelectState));
//            spnState.requestFocus();
//        } else if (!commonCode.isValidString(EditsProfileActivity.this, districtName)) {
//            spnDistrict.setError(getResources().getString(R.string.plsSelectDistrict));
//            spnDistrict.requestFocus();
//        }else if (!commonCode.isValidString(EditsProfileActivity.this, address)) {
//            edtAddress.setError(getResources().getString(R.string.plsEnterAddress));
//            edtAddress.requestFocus();
//        }else{
//
////            http://localhost:8081/OnlineOBJExam/user/update/   + exam controller id
////
////            "message": "Updated"
////            "message":"Failed"
//
//            jsonurl = urllink.url + "user/update/"+id;
//            updateinfo();
//        }
    }

    private void updateinfo() {
        if (commonCode.checkConnection(EditsProfileActivity.this)) {
            progressDialog = new ProgressDialog(EditsProfileActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JSONObject jsonParams = new JSONObject();
            try {
                //jsonParams.put("role", role);
                jsonParams.put("first_name", firstname);
                jsonParams.put("middle_name", middlename);
                jsonParams.put("last_name", lastname);
                jsonParams.put("m_name", mothername);
                jsonParams.put("mobile", mobileno);
                jsonParams.put("email", emailid);
                jsonParams.put("address", address);

                jsonParams.put("gender", gender);
                jsonParams.put("nationality", countryName);
                jsonParams.put("state", stateName);
                jsonParams.put("city", districtName);
                jsonParams.put("bloodGroup", bloodgroup);
                jsonParams.put("highestQualification", qualification);
                jsonParams.put("birthdate", birthdate);
                jsonParams.put("admissionDate", admissiondate);
                jsonParams.put("emergencyMobNo", emergencymobileno);

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

            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, jsonurl,

                    jsonParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    try {
                        String message = response.getString("message");
                        if (message.equals("Updated")) {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.updatedSuccessfully), Toast.LENGTH_LONG).show();
                            regSuccessPopup();
                        } else if (message.equals("User already exist")) {
//                            mobNoAlreadyPopup();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.alreadyHaveAnAccount), Toast.LENGTH_SHORT).show();

                        } else if (message.equals("Failed")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_SHORT).show();
                        }
//                        else if (message.equals("Failed")) {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_SHORT).show();
//                        } else if (message.equals("Failed")) {
//                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failedTryAgain), Toast.LENGTH_SHORT).show();
//                        }
                        else {
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
                        Intent intent = new Intent(EditsProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.serverError), Toast.LENGTH_LONG).show();
                    }
                }
            })
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
            commonCode.AlertDialog_Pbtn(EditsProfileActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void regSuccessPopup() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.dialog_updated_success, null);

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
//                Intent abc  = new Intent(EditsProfileActivity.this,ExamControllersList.class);
//                startActivity(abc);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent abc  = new Intent(EditsProfileActivity.this,ExamControllersList.class);
//        startActivity(abc);
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        bundle.putString("userTypeName", role);
//        bundle.putString("first_name", firstname);
//        bundle.putString("middle_name", middlename);
//        bundle.putString("last_name", lastname);
//        bundle.putString("m_name", mothername);
//        bundle.putString("birthdate", birthdate);
//        bundle.putString("admissionDate", admissiondate);
//        bundle.putString("gender", gender);
//        bundle.putString("email", emailid);
//        bundle.putString("mobile", mobileno);
//        bundle.putString("bloodGroup", bloodgroup);
//        bundle.putString("emergencyMobNo", emergencymobileno);
//        bundle.putString("highestQualification", qualification);
//        bundle.putString("nationality", country);
//        bundle.putString("state", state);
//        bundle.putString("city", city);
//        bundle.putString("address", address);
//        bundle.putBoolean("softDelete", activeInactive);


    }
}
