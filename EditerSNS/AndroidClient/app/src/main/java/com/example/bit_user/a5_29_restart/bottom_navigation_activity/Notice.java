package com.example.bit_user.a5_29_restart.bottom_navigation_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bit_user.a5_29_restart.BottomNavigationViewHelper;
import com.example.bit_user.a5_29_restart.R;
import com.example.bit_user.a5_29_restart.gridData;
import com.example.bit_user.a5_29_restart.navigation_fragment.BookMark;
import com.example.bit_user.a5_29_restart.navigation_fragment.CoupleDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.MyDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.Setting;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 이 페이지는 바텀네비 '공지, 알림' 에 해당하는 클래스입니다
 */

public class Notice extends AppCompatActivity
{
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    private ListViewAdapter mAdapter = null;
    ImageView img;
    TextView ttv;
    GridView gridView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_nav_notice_activity);

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
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        // ********************************************************* //

        // 바텀 네비게이션 클릭 리스너 //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {

                    case R.id.bottom_navi_search : {Intent intent = new Intent(Notice.this, Read.class);startActivity(intent);}break;
                    case R.id.bottom_navi_write : {Intent intent = new Intent(Notice.this, Write.class);startActivity(intent);}break;
                    case R.id.bottom_navi_notice : {}break;
                    case R.id.bottom_navi_five : {}break;
                }
                return true;
            }
        });
        // ********************************************************* //
        img = (ImageView)findViewById(R.id.main_image);
        ttv = (TextView)findViewById(R.id.main_text);
        gridView = (GridView)findViewById(R.id.grid_view);

        mAdapter = new ListViewAdapter(this);

        gridView.setAdapter(mAdapter);

        mAdapter.addItem(getResources().getDrawable(R.drawable.map),
                "확인이 완료되었습니다",
                "");
        mAdapter.addItem(getResources().getDrawable(R.drawable.map),
                "안녕하세요",
                "");
        mAdapter.addItem(getResources().getDrawable(R.drawable.map),
                "슈퍼유저~~",
                "");
        mAdapter.addItem(getResources().getDrawable(R.drawable.map),
                "이미지가 null이면...",
                "");
        mAdapter.addItem(getResources().getDrawable(R.drawable.map),
                "슈퍼유저~~",
                "");
        mAdapter.addItem(getResources().getDrawable(R.drawable.map),
                "슈퍼유저~~",
                "");
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                gridData mData = mAdapter.mListData.get(position);
                Toast.makeText(Notice.this, mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });




    }


    private class ViewHolder {
        public ImageView mIcon;

        public TextView mText;

        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<gridData> mListData = new ArrayList<gridData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(Drawable icon, String mTitle, String mDate) {
            gridData addInfo = null;
            addInfo = new gridData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mDate = mDate;

            mListData.add(addInfo);
        }

        public void remove(int position) {
            mListData.remove(position);
            dataChange();
        }

        public void sort() {
            Collections.sort(mListData, gridData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange() {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.grid_view, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.main_image);
                holder.mText = (TextView) convertView.findViewById(R.id.main_text);
                holder.mDate = (TextView) convertView.findViewById(R.id.bu1);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            gridData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            } else {
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.mTitle);
            holder.mDate.setText(mData.mDate);

            return convertView;
        }
    }
}
