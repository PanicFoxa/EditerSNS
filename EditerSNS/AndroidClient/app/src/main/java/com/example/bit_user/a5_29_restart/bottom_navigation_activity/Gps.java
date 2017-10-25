package com.example.bit_user.a5_29_restart.bottom_navigation_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bit_user.a5_29_restart.BottomNavigationViewHelper;
import com.example.bit_user.a5_29_restart.R;
import com.example.bit_user.a5_29_restart.navigation_fragment.BookMark;
import com.example.bit_user.a5_29_restart.navigation_fragment.CoupleDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.MyDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.Setting;

/**
 * 이 페이지는 바텀네비 'GPS 기능' 에 해당하는 클래스입니다
 */

public class Gps extends AppCompatActivity
{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_nav_gps_activity);

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
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.navi_myStory : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout,new MyDiary()).commit();}break;
                    case R.id.navi_bookMark : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout,new BookMark()).commit();}break;
                    case R.id.navi_coupleStory : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout,new CoupleDiary()).commit();}break;
                    case R.id.navi_setting : {getFragmentManager().beginTransaction().replace(R.id.main_Frame_layout, new Setting()).commit();}break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        // ********************************************************* //

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        // 바텀 네비게이션 클릭 시 애니메이션 하이라이트 순서에 맞게 주기 //
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
        // ********************************************************* //

        // 바텀 네비게이션 클릭 리스너 //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.bottom_navi_search : {Intent intent = new Intent(Gps.this, Read.class);startActivity(intent);}break;
                    case R.id.bottom_navi_write : {Intent intent = new Intent(Gps.this, Write.class);startActivity(intent);}break;
                    case R.id.bottom_navi_notice : {}break;
                    case R.id.bottom_navi_five : {}break;
                }
                return true;
            }
        });
        // ********************************************************* //
    }
}
