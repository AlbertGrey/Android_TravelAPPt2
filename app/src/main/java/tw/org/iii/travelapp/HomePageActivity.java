package tw.org.iii.travelapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class HomePageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager pager;
    private FragmentManager manager;
    private HotPage page1;
    private AttrPage page2;
    private FoodPage page3;
    private ArrayList<Fragment> fragments;
    private LinearLayout iv_home, iv_guide, iv_camera, iv_search, iv_setting;
    private float screenWidth, screenHeight, newHeight;
    public static String urlIP = "http://36.235.39.18:8080";
    public static String userID = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        findView();
        getScreenSize();
        setIconListener();
        //toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_toolbar_menu);
        setSupportActionBar(toolbar);

        //詢問權限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            initdata();
        }
        //因為toolbar 而使用 tablayout
        tabLayout = findViewById(R.id.tablayout);
        //viewpager 滑動
        pager = findViewById(R.id.container);
        page1 = new HotPage();
        page2 = new AttrPage();
        page3 = new FoodPage();
        //fragment頁面
        manager = getSupportFragmentManager();
        MyAdapter adapter = new MyAdapter(manager);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);
        //與tablayout連動
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        pager.setOffscreenPageLimit(5);
        inittablayout();
//        initActionBar();
        //螢幕寬高
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        Log.v("grey","手機寬高 ＝" + metrics.widthPixels+" X "+metrics.heightPixels);
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_btn);

        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.search_btn:
                        Intent intent = new Intent(HomePageActivity.this,SearchPage.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
        return true;
    }



    //詢問權限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            initdata();
        }else{
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void initdata() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new HotPage());
        fragments.add(new AttrPage());
        fragments.add(new FoodPage());
    }

    private void inittablayout(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //使tab按鍵和pager連動
                if(pager!=null){
                    pager.setCurrentItem(tab.getPosition());
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("熱門"));
        tabLayout.addTab(tabLayout.newTab().setText("景點"));
        tabLayout.addTab(tabLayout.newTab().setText("美食"));
    }



    public void topage1(View view) {
    }

    public void topage2(View view) {
    }

    public void topage3(View view) {
    }

    public void topage4(View view) {
    }

    public void topage5(View view) {
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            if (fragments != null) {
                return fragments.size();
            }
            return 0;
        }
    }
    private void findView(){
        iv_home = findViewById(R.id.btn_home);
        iv_guide = findViewById(R.id.btn_guide);
        iv_camera = findViewById(R.id.btn_camera);
        iv_search = findViewById(R.id.btn_search);
        iv_setting = findViewById(R.id.btn_setting);
    }

    private void setIconListener(){
        iv_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_home.setBackgroundColor(Color.BLACK);
                iv_guide.setBackgroundColor(Color.BLACK);
                iv_camera.setBackgroundColor(Color.rgb(169,169,169));
                iv_search.setBackgroundColor(Color.BLACK);
                iv_setting.setBackgroundColor(Color.BLACK);
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_home.setBackgroundColor(Color.BLACK);
                iv_guide.setBackgroundColor(Color.BLACK);
                iv_camera.setBackgroundColor(Color.BLACK);
                iv_search.setBackgroundColor(Color.rgb(169,169,169));
                iv_setting.setBackgroundColor(Color.BLACK);
            }
        });

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }
    //取得螢幕大小
    private void getScreenSize(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        newHeight = screenWidth / 16 * 9;
    }
}

