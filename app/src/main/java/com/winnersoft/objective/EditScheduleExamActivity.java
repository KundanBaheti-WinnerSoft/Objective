package com.winnersoft.objective;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

public class EditScheduleExamActivity extends AppCompatActivity {
    EditText edtBatch,edtNoOfQue,edtTotalMark,edtStartDate,edtDay,edtStartTime,edtEndTime;
    String batch="",noofque="",totalmark="",startdate="",day="",starttime="",endtime="",jsonurl = "",examid;
    MaterialSpinner spnCourse;
    private int mHour, mMinute;
    private String fromTime;
    private String toTime;
    int courseId = 0;
    String courseName = "";
    Button btnSubmit;
    String SecurityToken, userName, userRole, userId;
    private SharedPreferences sharedPreferences;
    CommonCode commonCode = new CommonCode();
    private Urllink urllink = new Urllink();
    private ProgressDialog progressDialog;
    //Initialize calender
    Calendar calendar=Calendar.getInstance();
    //get year
    final int year=calendar.get(Calendar.YEAR);
    //get month
    final int month=calendar.get(Calendar.MONTH);
    //get day
    final int date=calendar.get(Calendar.DAY_OF_MONTH);
    final ArrayList<HashMap<String, String>> arraymapCourse = new ArrayList<>();
    ArrayList<String> nameListCourse = new ArrayList<>();
    public Bundle getBundle = null;
    TextView tvCourse;

    Boolean question_allocation,exam_center_allocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule_exam);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.editScheduleExam));
        getSupportActionBar().setSubtitle("");
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("role", "");
        userName = sharedPreferences.getString("username", "");
        userId = sharedPreferences.getString("userId", "");

        tvCourse=findViewById(R.id.tv_course);
        edtBatch=findViewById(R.id.edt_batch);
        edtNoOfQue=findViewById(R.id.edt_no_of_que);
        edtTotalMark=findViewById(R.id.edt_total_mark);
        edtStartDate=findViewById(R.id.edt_start_date);
        edtDay=findViewById(R.id.edt_day);
        edtStartTime=findViewById(R.id.edt_start_time);
        edtEndTime=findViewById(R.id.edt_end_time);
        spnCourse=findViewById(R.id.spn_course);

        btnSubmit=findViewById(R.id.btn_update);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        getAllCourse();
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        EditScheduleExamActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        String dob=dayOfMonth+"/"+month+"/"+year;
                        String dob=year+"-"+(month+1)+"-"+dayOfMonth;
                        edtStartDate.setText(dob);
                    }
                },year,month,date);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        edtStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePicker();
            }
        });

        edtEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimePicker();
            }
        });

        getBundle = this.getIntent().getExtras();
        courseName = getBundle.getString("courseName");
        batch = getBundle.getString("batch");
        noofque = getBundle.getString("noOfQuestions");
        startdate = getBundle.getString("dateOfExam");
        day = getBundle.getString("day");
        starttime = getBundle.getString("sTime");
        totalmark=getBundle.getString("totalMarks");
        endtime = getBundle.getString("eTime");
        examid = getBundle.getString("id");
        question_allocation = getBundle.getBoolean("question_allocation");
        exam_center_allocation = getBundle.getBoolean("exam_center_allocation");


        edtBatch.setText(batch);
        edtNoOfQue.setText(noofque);
        edtStartDate.setText(startdate);
        edtDay.setText(day);
        edtStartTime.setText(starttime);
        edtTotalMark.setText(totalmark);
        edtEndTime.setText(endtime);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void startTimePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditScheduleExamActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        int hour = hourOfDay;
                        int minutes = minute;
                        String timeSet = "";
//                        if (hour > 12) {
//                            hour -= 12;
//                            timeSet = "PM";
//                        } else if (hour == 0) {
//                            hour += 12;
//                            timeSet = "AM";
//                        } else if (hour == 12) {
//                            timeSet = "PM";
//                        } else {
//                            timeSet = "AM";
//                        }

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hour).append(':')
                                .append(min).append(":").append("00").append("").append(timeSet).toString();

                        edtStartTime.setText(aTime);
                        fromTime = aTime;


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void endTimePicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditScheduleExamActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        int hour = hourOfDay;
                        int minutes = minute;
                        String timeSet = "";
//                        if (hour > 12) {
//                            hour -= 12;
//                            timeSet = "PM";
//                        } else if (hour == 0) {
//                            hour += 12;
//                            timeSet = "AM";
//                        } else if (hour == 12) {
//                            timeSet = "PM";
//                        } else {
//                            timeSet = "AM";
//                        }

                        String min = "";
                        if (minutes < 10)
                            min = "0" + minutes;
                        else
                            min = String.valueOf(minutes);

                        // Append in a StringBuilder
                        String bTime = new StringBuilder().append(hour).append(':')
                                .append(min).append(":").append("00").append("").append(timeSet).toString();

                        edtEndTime.setText(bTime);
                        toTime = bTime;


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void validation(){
        batch=edtBatch.getText().toString();
        noofque=edtNoOfQue.getText().toString();
        totalmark=edtTotalMark.getText().toString();
        startdate=edtStartDate.getText().toString();
        day=edtDay.getText().toString();
        starttime=edtStartTime.getText().toString();
        endtime=edtEndTime.getText().toString();

        if (endtime.equalsIgnoreCase("")||starttime.equalsIgnoreCase("")||day.equalsIgnoreCase("")||startdate.equalsIgnoreCase("")||totalmark.equalsIgnoreCase("")||noofque.equalsIgnoreCase("")||!commonCode.isValidString(EditScheduleExamActivity.this, courseName)||batch.equalsIgnoreCase("")){
            if (!commonCode.isValidString(EditScheduleExamActivity.this, courseName)) {
                setSpinnerError(spnCourse,getString(R.string.plsSelectCourse));
                spnCourse.setError(getResources().getString(R.string.plsSelectCourse));
                spnCourse.requestFocus();
            } if (batch.equalsIgnoreCase("")) {
                edtBatch.setError(getResources().getString(R.string.plsEnterBatch));
                edtBatch.requestFocus();
            }  if (noofque.equalsIgnoreCase("")) {
                edtNoOfQue.setError(getResources().getString(R.string.plsEnterNoOfQue));
                edtNoOfQue.requestFocus();
            }  if (totalmark.equalsIgnoreCase("")) {
                edtTotalMark.setError(getResources().getString(R.string.plsEnterTotalMarks));
                edtTotalMark.requestFocus();
            } if (startdate.equalsIgnoreCase("")) {
                edtStartDate.setError(getResources().getString(R.string.plsEnterStartDate));
                edtStartDate.requestFocus();
            } if (day.equalsIgnoreCase("")) {
                edtDay.setError(getResources().getString(R.string.plsEnterNoOfDays));
                edtDay.requestFocus();
            } if (starttime.equalsIgnoreCase("")) {
                edtStartTime.setError(getResources().getString(R.string.plsSelectStartTime));
                edtStartTime.requestFocus();
            } if (endtime.equalsIgnoreCase("")) {
                edtEndTime.setError(getResources().getString(R.string.plsSelectEndTime));
                edtEndTime.requestFocus();
            }
        }else{
            jsonurl = urllink.url + "examSchedule/update/"+examid;
            updateCourse();
        }
//        if (!commonCode.isValidString(EditScheduleExamActivity.this, courseName)) {
//            setSpinnerError(spnCourse,getString(R.string.plsSelectCourse));
//            spnCourse.setError(getResources().getString(R.string.plsSelectCourse));
//            spnCourse.requestFocus();
//        }else if (batch.equalsIgnoreCase("")) {
//            edtBatch.setError(getResources().getString(R.string.plsEnterBatch));
//            edtBatch.requestFocus();
//        } else if (noofque.equalsIgnoreCase("")) {
//            edtNoOfQue.setError(getResources().getString(R.string.plsEnterNoOfQue));
//            edtNoOfQue.requestFocus();
//        } else if (totalmark.equalsIgnoreCase("")) {
//            edtTotalMark.setError(getResources().getString(R.string.plsEnterTotalMarks));
//            edtTotalMark.requestFocus();
//        }else if (startdate.equalsIgnoreCase("")) {
//            edtStartDate.setError(getResources().getString(R.string.plsEnterStartDate));
//            edtStartDate.requestFocus();
//        }else if (day.equalsIgnoreCase("")) {
//            edtDay.setError(getResources().getString(R.string.plsEnterNoOfDays));
//            edtDay.requestFocus();
//        }else if (starttime.equalsIgnoreCase("")) {
//            edtStartTime.setError(getResources().getString(R.string.plsSelectStartTime));
//            edtStartTime.requestFocus();
//        }else if (endtime.equalsIgnoreCase("")) {
//            edtEndTime.setError(getResources().getString(R.string.plsSelectEndTime));
//            edtEndTime.requestFocus();
//        }else{
//            jsonurl = urllink.url + "examSchedule/update/"+examid;
////            http://localhost:8081/OnlineOBJExam/Course/save
//
//            updateCourse();
//        }

    }
    private void getAllCourse() {
        arraymapCourse.clear();
        nameListCourse.clear();
        if (commonCode.checkConnection(EditScheduleExamActivity.this)) {
            final ArrayList<HashMap<String, String>> arraymap = new ArrayList<>();
            //            original
//            getall course name list
//            String jsonurl = urllink.url + "Course/getAllCourse";

            //list of courses added by particular exam controller
            String jsonurl = urllink.url + "Course/getAllCourseByExController";
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            progressDialog = new ProgressDialog(EditScheduleExamActivity.this);
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

                        ArrayAdapter<String> stringArrayAdapter2 = new ArrayAdapter<>(EditScheduleExamActivity.this, android.R.layout.simple_spinner_item, nameListCourse);
                        spnCourse.setAdapter(stringArrayAdapter2);
                        // spnState.setSelection(21);
                        spnCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String valuename = parent.getItemAtPosition(position).toString();
                                if (!valuename.equals(getResources().getString(R.string.selCourse))) {
                                    courseId = Integer.parseInt(arraymapCourse.get(position).get("id"));
                                    courseName = (arraymapCourse.get(position).get("courseName"));
                                } else {
                                    courseId = 0;
                                    courseName = "";
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
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("x-Auth-token", SecurityToken);
                    return params;
                }
            };
            requestQueue.add(jsonArrayRequest);
        } else {
            commonCode.AlertDialog_Pbtn(EditScheduleExamActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }


    private void updateCourse() {
        if (commonCode.checkConnection(EditScheduleExamActivity.this)) {
            progressDialog = new ProgressDialog(EditScheduleExamActivity.this);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.show();
            progressDialog.setCancelable(true);
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("dateOfExam", startdate);
                jsonParams.put("batch", batch);
                jsonParams.put("sTime", starttime);
                jsonParams.put("eTime", endtime);
                jsonParams.put("day", day);
                jsonParams.put("noOfQuestions", noofque);
                jsonParams.put("totalMarks", totalmark);
                JSONObject adminObj = new JSONObject();
                adminObj.put("id", courseId);
                jsonParams.put("course", adminObj);


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
                            updateSuccessPopup();
                        } else if (message.equals("already exist")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.courseNameAlreadyExist), Toast.LENGTH_LONG).show();

//                            mobNoAlreadyPopup();
                        } else if (message.equals("Course code already exist")) {
//                            mobNoAlreadyPopup();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.courseCodeAlreadyExist), Toast.LENGTH_LONG).show();

                        } else if (message.equals("Failed")) {
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
                        Intent intent = new Intent(EditScheduleExamActivity.this, LoginActivity.class);
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
            commonCode.AlertDialog_Pbtn(EditScheduleExamActivity.this, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.plsConnectToInternet), getResources().getString(R.string.ok));
        }
    }

    private void updateSuccessPopup() {
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
            }
        });

    }
}
