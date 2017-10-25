package com.example.bit_user.a5_29_restart.bottom_navigation_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bit_user.a5_29_restart.BoardSketchVO;
import com.example.bit_user.a5_29_restart.BoardVO;
import com.example.bit_user.a5_29_restart.BottomNavigationViewHelper;
import com.example.bit_user.a5_29_restart.Common;
import com.example.bit_user.a5_29_restart.ListData;
import com.example.bit_user.a5_29_restart.R;
import com.example.bit_user.a5_29_restart.navigation_fragment.BookMark;
import com.example.bit_user.a5_29_restart.navigation_fragment.CoupleDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.MyDiary;
import com.example.bit_user.a5_29_restart.navigation_fragment.Setting;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 이 페이지는 바텀네비 '읽기' 에 해당하는 클래스입니다
 */

public class Read extends AppCompatActivity {

    LinearLayout ll;
    Button btn1;
    Animation ani,ani2;
    ListView listview;
    ImageView img;
    TextView resulttxtview;
    RelativeLayout resultsketch;
    Button nextgl;
    private ListViewAdapter mAdapter = null;
    FrameLayout fl;
    LinkedList<BoardVO> boardli = new LinkedList<>();
    boolean b= false;
    static Read readact;


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        readact = Read.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bot_nav_read_activity);

        resulttxtview = (TextView) findViewById(R.id.resulttxtview);
        resultsketch = (RelativeLayout) findViewById(R.id.resultsketch);
        nextgl = (Button) findViewById(R.id.nextgl);

        nextgl.setOnClickListener(new Button.OnClickListener(){
            int a=0;
            BoardVO bvo;
            @Override
            public void onClick(View v) {
              runOnUiThread(new Runnable() {
                    @SuppressWarnings("WrongConstant")
                    public void run() {
                        try{
                            bvo= boardli.get(a);
                        }catch(Exception e){
                            a=0;
                            bvo= boardli.get(a);
                        }
                        resultsketch.removeAllViews();

                        Drawable drawable = new BitmapDrawable(Read.this.getResources(), bvo.getBg_img());
                        img.setBackground(drawable);
                        resulttxtview.setText(bvo.getTxt_content());

                        int fcolor = Color.BLACK;
                        if(bvo.getTxt_color().equals(Common.color_white)) {
                            fcolor = Color.WHITE;
                        }
                        resulttxtview.setTextColor(fcolor);
//                            resulttxtview.setTypeface(Typeface.createFromAsset(MainActivity.this.getAssets(),bvo.getTxt_font()));
                        resulttxtview.setPaintFlags(Integer.parseInt(bvo.getTxt_weight()));
                        resulttxtview.setTextSize((int)Math.floor(bvo.getTxt_size()/2.6));
                        if(bvo.getTxt_orit()==1){
                            String setgase = resulttxtview.getText().toString();
                            String results = "";
                            for(int a = 0; a < setgase.length(); a++){
                                results += setgase.charAt(a)+"\n";
                            }
                            resulttxtview.setText(results);
                        }
                        resulttxtview.setGravity(Integer.parseInt(bvo.getTxt_location()));
                        resulttxtview.setTextAlignment(Integer.parseInt(bvo.getTxt_sort()));
                        ArrayList<BoardSketchVO> bo= bvo.getBoard_sketch();
                        Iterator<BoardSketchVO> boi = bo.iterator();

                        BoardSketchVO bsvo = boi.next();
                        if(bsvo!=null){
                            ImageView bosk = new ImageView(Read.this);
                            Drawable boskdr = new BitmapDrawable(Read.this.getResources(), bsvo.getRs_img());
                            bosk.setX(bsvo.getRs_x());
                            bosk.setY(bsvo.getRs_y());
                            bosk.setBackground(boskdr);
                            resultsketch.addView(bosk);
                            bosk.setImageResource(R.color.transparent);
                            bosk.getLayoutParams().width = (int) bsvo.getRs_w();
                            bosk.getLayoutParams().height = (int) bsvo.getRs_h();
                            bosk.requestLayout();
                        }

                    }
                });
                a++;
            }

        });

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

        // 바텀 네비게이션 객체 만들기 + 바텀 네비게이션 애니메이션 없애기 //
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        // ********************************************************* //

        // 바텀 네비게이션 클릭 시 애니메이션 하이라이트 순서에 맞게 주기 //
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        // ********************************************************* //

        // 바텀 네비게이션 클릭 리스너 //
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.bottom_navi_search : {}break;
                    case R.id.bottom_navi_write : {Intent intent = new Intent(Read.this, Write.class);startActivity(intent);}break;
                    case R.id.bottom_navi_notice : {}break;
                    case R.id.bottom_navi_five : {}break;
                }
                return true;
            }
        });
        // ********************************************************* //

        listview = (ListView)findViewById(R.id.list_view);
        fl = (FrameLayout)findViewById(R.id.frame_Layout);
        ani = AnimationUtils.loadAnimation(this, R.anim.act1);
        ll = (LinearLayout) findViewById(R.id.linear);
        ani2 = AnimationUtils.loadAnimation(this, R.anim.act2);
        img = (ImageView)findViewById(R.id.img);
        final ScrollView mScrollView = (ScrollView)findViewById(R.id.scroll);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b==true){
                    ll.setVisibility(View.GONE);
                    ll.startAnimation(ani2);
                    b = false;
                }
            }
        });

        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mScrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        mAdapter = new ListViewAdapter(this);

        listview.setAdapter(mAdapter);

        mAdapter.addItem(getResources().getDrawable(R.drawable.user2),
                "확인이 완료되었습니다",
                "좋아요");
        mAdapter.addItem(getResources().getDrawable(R.drawable.user2),
                "안녕하세요",
                "좋아요");
        mAdapter.addItem(getResources().getDrawable(R.drawable.user2),
                "슈퍼유저~~",
                "좋아요");
        mAdapter.addItem(getResources().getDrawable(R.drawable.user2),
                "이미지가 null이면...",
                "좋아요");
        mAdapter.addItem(getResources().getDrawable(R.drawable.user2),
                "슈퍼유저~~",
                "좋아요");
        mAdapter.addItem(getResources().getDrawable(R.drawable.user2),
                "슈퍼유저~~",
                "좋아요");
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                ListData mData = mAdapter.mListData.get(position);
                Toast.makeText(Read.this, mData.mTitle, Toast.LENGTH_SHORT).show();
            }
        });


        // ********************************************************* //


        // ********************************************************* //

        readthstart();
    }





    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public void readthstart(){
        readth.start();
    }
    Thread readth = new Thread(new Runnable() {
        public void run() {
            try {
                String page = Common.server_url + "/mbtest/board/boardlist";
                HttpClient http = new DefaultHttpClient();
                // post 방식으로 보낼 데이터
                ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();
                postData.add(new BasicNameValuePair("read", "1"));
                UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData, "utf-8");
                HttpPost httpPost = new HttpPost(page);
                httpPost.setEntity(request);
                HttpResponse response = http.execute(httpPost);
                String body = EntityUtils.toString(response.getEntity());
                JSONObject jsonObj = new JSONObject(body);
                JSONArray jArray = (JSONArray) jsonObj.get("boardlist");
                for(int a =0; a<jArray.length(); a++){
                    JSONObject resultob = jArray.getJSONObject(a);
                    ArrayList<BoardSketchVO> bosk = new ArrayList<>();
                    String read_num=resultob.getString("read_num");
                    String write_user=resultob.getString("write_user");
                    String read_lat=resultob.getString("read_lat");
                    String read_lont=resultob.getString("read_lont");
                    String share_whether=resultob.getString("share_whether");
                    String bg_img=resultob.getString("bg_img");
                    String txt_content=resultob.getString("txt_content");
                    String txt_color=resultob.getString("txt_color");
                    String txt_font=resultob.getString("txt_font");
                    String txt_weight=resultob.getString("txt_weight");
                    String txt_size=resultob.getString("txt_size");
                    String txt_orit=resultob.getString("txt_orit");
                    String txt_location=resultob.getString("txt_location");
                    String txt_sort=resultob.getString("txt_sort");
                    JSONArray board_sketch=resultob.getJSONArray("sketch_array");

                    for(int b = 0; b < board_sketch.length(); b++){
                        JSONObject boardob = board_sketch.getJSONObject(b);
                        InputStream is = null;
                        HttpURLConnection conn = null;
                        String pages = Common.server_url+"/mbtest/resources/boardimages/"+boardob.getString("rs_img");
                        URL url = new URL(pages);
                        conn = (HttpURLConnection)url.openConnection();
                        conn.connect();
                        is = conn.getInputStream();
                        Bitmap bm= BitmapFactory.decodeStream(is);
                        BoardSketchVO bsv = new BoardSketchVO(Integer.parseInt(boardob.getString("rsread_num")),bm,Float.parseFloat(boardob.getString("rs_x")),Float.parseFloat(boardob.getString("rs_y")),Float.parseFloat(boardob.getString("rs_w")),Float.parseFloat(boardob.getString("rs_h")));
                        bosk.add(bsv);
                        conn.disconnect();
                        is.close();
                    }
                    InputStream is = null;
                    HttpURLConnection conn = null;
                    String pages =Common.server_url+"/mbtest/resources/boardimages/"+bg_img;
                    URL url = new URL(pages);
                    conn = (HttpURLConnection)url.openConnection();
                    conn.connect();
                    is = conn.getInputStream();
                    Bitmap bm = BitmapFactory.decodeStream(is);
                    conn.disconnect();
                    is.close();
                    BoardVO bv = new BoardVO(Integer.parseInt(read_num),Integer.parseInt(write_user),Float.parseFloat(read_lat),Float.parseFloat(read_lont),Integer.parseInt(share_whether),bm,txt_content,txt_color,txt_font,txt_weight,Float.parseFloat(txt_size),Integer.parseInt(txt_orit),txt_location,txt_sort,bosk);
                    boardli.add(bv);
                }

                //화면 UI 변경

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                readth.interrupt();
            }
        }
    });

    private class ViewHolder {
        public ImageView mIcon;

        public TextView mText;

        public TextView mDate;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

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

        public void addItem(Drawable icon, String mTitle, String mDate){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.mTitle = mTitle;
            addInfo.mDate = mDate;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_view, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.imageView);
                holder.mText = (TextView) convertView.findViewById(R.id.textView);
                holder.mDate = (TextView) convertView.findViewById(R.id.button2);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.mText.setText(mData.mTitle);
            holder.mDate.setText(mData.mDate);

            return convertView;
        }
    }

}
