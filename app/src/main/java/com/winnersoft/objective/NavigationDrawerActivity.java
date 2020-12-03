package com.winnersoft.objective;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ProgressDialog progressDialog;
    String SecurityToken;
    private SharedPreferences sharedPreferences;
    private Urllink urllink = new Urllink();
    private String url = urllink.url;
    private CommonCode commonCode = new CommonCode();
    private String userRole = "", userName = "", roleId = "", userEmailId = "", teacherid = "", schoolid = "";

    //Nav Headawr
//    CircleImageView ivProfilePic;
    TextView tvHeader, tvRole, tvUserName, tvDetails;
    Dialog myDialog;
    String imageUrl;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        progressDialog = new ProgressDialog(this);
        myDialog = new Dialog(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SecurityToken = sharedPreferences.getString("securitytoken", "");
        userRole = sharedPreferences.getString("roleName", "");
        roleId = sharedPreferences.getString("roleId", "");
        userName = sharedPreferences.getString("username", "");
        CommonCode commonCode = new CommonCode();
        commonCode.setupUI(findViewById(R.id.drawer), this);
        drawerLayout = findViewById(R.id.drawer);
        //navigation bar menu
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer) {
            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
//        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        tvRole = header.findViewById(R.id.tv_role);
        tvUserName = header.findViewById(R.id.tv_name);

        tvUserName.setText(userName);
        tvRole.setText(userRole);

        navigationView.setNavigationItemSelectedListener(this);


        if (userRole.equalsIgnoreCase("admin")) {
            int imagearray[] = {R.drawable.ic_menu, R.drawable.ic_menu,
                    R.drawable.ic_menu, R.drawable.ic_menu, R.drawable.ic_menu, R.drawable.ic_menu,R.drawable.ic_menu};

            String[] admin_menu = {getResources().getString(R.string.importStudent),
                    getResources().getString(R.string.scheduleExam),
                    getResources().getString(R.string.generateSitNo),
                    getResources().getString(R.string.importQuePaperAns),
                    getResources().getString(R.string.generateQueSet),
                    getResources().getString(R.string.approveReject),
                    getResources().getString(R.string.logout)};
            final Menu menu = navigationView.getMenu();
            for (int i = 0; i < admin_menu.length; i++) {
                MenuItem menuItem = menu.add(admin_menu[i]);
                menuItem.setIcon(imagearray[i]);
            }
        } else if (userRole.equalsIgnoreCase("student")) {
            int imagearray[] = {R.drawable.ic_menu, R.drawable.ic_menu,
                    R.drawable.ic_menu, R.drawable.ic_menu, R.drawable.ic_menu, R.drawable.ic_menu,R.drawable.ic_menu};
            String[] student_menu = {getResources().getString(R.string.myExam),
                    getResources().getString(R.string.startExam),
                    getResources().getString(R.string.downloadHallTicket),
                    getResources().getString(R.string.logout)};
            final Menu menu = navigationView.getMenu();
            for (int i = 0; i < student_menu.length; i++) {
                MenuItem menuItem = menu.add(student_menu[i]);
                menuItem.setIcon(imagearray[i]);
            }
        }

        //Chck user and Redirect to home page
        if (userRole.equalsIgnoreCase("SuperAdmin")) {
//            AdminDashboard adminDashboard = new AdminDashboard();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, adminDashboard).commit();

//            Intent i = new Intent(NavigationDrawerActivity.this, OnlineExamActivity.class);
//            startActivity(i);
        } else if (userRole.equalsIgnoreCase("student")) {
            Intent i = new Intent(NavigationDrawerActivity.this, OnlineExamActivity.class);
            startActivity(i);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (userRole.equalsIgnoreCase("SuperAdmin")) {
            getMenuInflater().inflate(R.menu.navigation_menu, menu);
        } else if (userRole.equalsIgnoreCase("student")) {
            getMenuInflater().inflate(R.menu.navigation_menu, menu);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = item.getTitle().toString();
        if (title.equals(getResources().getString(R.string.importStudent))) {
            //Chck user and Redirect to home page
            if (userRole.equals("SuperAdmin")) {
//                SuperAdminDashboard superAdminDashboard = new SuperAdminDashboard();
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, superAdminDashboard).commit();
            }

        }else if(title.equals(getResources().getString(R.string.scheduleExam))){

        }else if(title.equals(getResources().getString(R.string.generateSitNo))){

        }else if(title.equals(getResources().getString(R.string.importQuePaperAns))){

        }else if(title.equals(getResources().getString(R.string.generateQueSet))){

        }else if(title.equals(getResources().getString(R.string.approveReject))){

        }else if(title.equals(getResources().getString(R.string.startExam))){

        }else if(title.equals(getResources().getString(R.string.myExam))){

        }else if(title.equals(getResources().getString(R.string.downloadHallTicket))){

        }else if (title.equals(getResources().getString(R.string.logout))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawerActivity.this, R.style.MyDialogTheme);
            builder.setTitle(getResources().getString(R.string.logoutQue));
            builder.setIcon(R.drawable.warning_sign);
            builder.setMessage(getResources().getString(R.string.doYouWantLogout));
            builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                    sp.edit().clear().commit();
                    Intent intent = new Intent(NavigationDrawerActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.cancel();
                }
            }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create();
            builder.show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
//        switch (item.getItemId()) {
//            case R.id.menu_import_student:
//
//                break;
//            case R.id.menu_schedule_exam:
//
//                break;
//            case R.id.menu_generate_sit_no:
//
//                break;
//            case R.id.menu_import_que_ans:
//
//                break;
//            case R.id.menu_generate_que_set:
//
//                break;
//            case R.id.menu_approve_reject:
//
//                break;
//        }

    }
}
