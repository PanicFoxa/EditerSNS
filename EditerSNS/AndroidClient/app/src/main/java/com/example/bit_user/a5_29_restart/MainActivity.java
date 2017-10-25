package com.example.bit_user.a5_29_restart;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bit_user.a5_29_restart.bottom_navigation_activity.Gps;
import com.example.bit_user.a5_29_restart.bottom_navigation_activity.Notice;
import com.example.bit_user.a5_29_restart.bottom_navigation_activity.Read;
import com.example.bit_user.a5_29_restart.bottom_navigation_activity.Write;
import com.example.bit_user.a5_29_restart.navigation_fragment.BookMark;
import com.example.bit_user.a5_29_restart.navigation_fragment.CoupleDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.MyDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.Setting;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    DialogInterface alertdig;


    static final int Login_s = 2005;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    public static String useremail = null;
    android.support.design.widget.CoordinatorLayout invisiblebtn;

    public class Networkuser extends AsyncTask<String, Void, Object> {

        private HttpClient client;
        private HttpPost post;
        public String user_id;


        Networkuser(String user_id){
            this.user_id = user_id;
        }

        @Override
        protected Void doInBackground(String... arg0) {
            try {
                JSONObject ob = new JSONObject();
                ob.put("user_id",user_id);
                client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 30000);
                post = new HttpPost(arg0[0]);
                StringEntity entity = new StringEntity(ob.toString(), HTTP.UTF_8);
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(entity);
                client.execute(post);
                updateUI(useremail);
            } catch (Exception err) {
                err.printStackTrace();
            }
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        invisiblebtn= (android.support.design.widget.CoordinatorLayout) findViewById(R.id.invisiblebtn);


        //로그인

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signIn();







        // 툴바 (액션 바)
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // ********************************************************* //

        // 네비게이션바
        drawerLayout = (DrawerLayout)findViewById(R.id.main_Drawer_Layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // ********************************************************* //


        // 네비게이션바 클릭 이벤트 -> 프래그먼트 이동
        navigationView = (NavigationView)findViewById(R.id.navigation_bar);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navi_myStory : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout,new MyDiary()).commit();}break;
                    case R.id.navi_bookMark : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout,new BookMark()).commit();}break;
                    case R.id.navi_coupleStory : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout,new CoupleDiary()).commit();}break;
                    case R.id.navi_setting : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout, new Setting()).commit();}break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // ********************************************************* //*/


        // 바텀 네비게이션 객체 만들기 + 바텀 네비게이션 애니메이션 없애기 //
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        // ********************************************************* //

        // 바텀 네비게이션 클릭 시 애니메이션 하이라이트 순서에 맞게 주기 //
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        // ********************************************************* //

        // 바텀 네비게이션 클릭 리스너 //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.bottom_navi_search : {Intent intent = new Intent(MainActivity.this, Read.class);startActivity(intent);}break;
                    case R.id.bottom_navi_write : {Intent intent = new Intent(MainActivity.this, Write.class);startActivity(intent);}break;
                    case R.id.bottom_navi_notice : {Intent intent = new Intent(MainActivity.this, Notice.class);startActivity(intent);}break;
                    case R.id.bottom_navi_five : {Intent intent = new Intent(MainActivity.this, Gps.class);startActivity(intent);}break;
                }
                return true;
            }
        });


        // ********************************************************* //



    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        switch (requestCode){
            case RC_SIGN_IN:{
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }break;
            case Login_s:{
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Google 계정 등록");
                alertDialog.setMessage("Google 계정을 등록하시겠습니까?");
                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String page = Common.server_url + "/mbtest/member/insert";
                        new Networkuser(useremail).execute(page);
                        invisiblebtn.setVisibility(View.VISIBLE);
                    }
                });
                alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialog.setCancelable(true);
                alertdig = alertDialog.show();
            }break;
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            updateUI(acct.getEmail());
        }
    }

    public void updateUI(String email) {
        useremail = email;
        Thread th = new Thread(new Runnable() {
            @TargetApi(Build.VERSION_CODES.M)
            public void run() {
                try {
                    if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(android.Manifest.permission.ACCESS_NETWORK_STATE )!= PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(android.Manifest.permission.INTERNET )!= PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Toast.makeText(MainActivity.this, "외부메모리 읽기 쓰기 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                        requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.INTERNET}, 1);
                    }
                    String page = Common.server_url + "/mbtest/member/logincheck";
                    HttpClient http = new DefaultHttpClient();
                    // post 방식으로 보낼 데이터
                    ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                    postData.add(new BasicNameValuePair("user_id", useremail));

                    UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
                    HttpPost httpPost = new HttpPost(page);
                    httpPost.setEntity(request);
                    // app/libs에 httpcore-4.2.2.jar 추가
                    HttpResponse response = http.execute(httpPost);
                    String body = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObj = new JSONObject(body);
                    JSONArray jArray = (JSONArray) jsonObj.get("checklist");
                    if (jArray.length() > 0) {
                        Intent intent = new Intent(MainActivity.this, Read.class);
                        startActivity(intent);
                    }else{
                        googleinsert();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        th.start();
    }


    public void googleinsert(){
        Intent Login_sintent = new Intent();
        startActivityForResult(Login_sintent, Login_s);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
