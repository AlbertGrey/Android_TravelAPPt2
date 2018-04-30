package tw.org.iii.travelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private ScrollView scrollView;
    private MyGridView gridView;
    private TextView textViewStitle, textViewDesc, textViewAddress, textViewMemo_time;
    private int screenWidth, screenHeight, newHeight;
    private String stitle, xbody, img_url, address, memo_time;
    private double lat, lng;
    private LinearLayout navigation;
    private ArrayList<String> photoList;
    private LinearLayout backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //變更通知列底色
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#4f4f4f"));
        setTitle("景點資訊");

        findViews(); //找出views
        getScreenSize(); //取得螢幕大小
        getIntentData(); //取得Intent資料

        //設定stitle、xbody
        textViewStitle.setText(stitle);
        textViewDesc.setText(xbody);
        textViewAddress.setText(address);
        textViewMemo_time.setText(memo_time);
        //設定photo
        GlideApp
                .with(DetailActivity.this)
                .load(img_url)
                .override(screenWidth, newHeight)
                .centerCrop()
//                .placeholder(R.drawable.loading)
                .into(imageView);

        gridView.setAdapter(new ImageAdapter(this));

        setClickListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("memberdata", MODE_PRIVATE);
        String backGroundColor = sp.getString("backgroundColor", "#FFFFDD");
        backgroundColor.setBackgroundColor(Color.parseColor(backGroundColor));
    }

    //設定導航按鈕事件
    private void setClickListener(){
        //導航
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMap();
            }
        });
        //相片集
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(DetailActivity.this, ViewPagerActivity.class);
                intent.putStringArrayListExtra("photos", photoList);
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
    //找出views
    private void findViews(){
        scrollView = findViewById(R.id.detail_scrollView);
        imageView = findViewById(R.id.detail_image);
        gridView = findViewById(R.id.detail_gridView);
        textViewStitle = findViewById(R.id.detail_textViewTitle);
        textViewDesc = findViewById(R.id.detail_textViewDesc);
        textViewAddress = findViewById(R.id.detail_textViewAddress);
        textViewMemo_time = findViewById(R.id.detail_textViewMemoTime);
        navigation = findViewById(R.id.detail_navigation);
        backgroundColor = findViewById(R.id.detail_background);
    }
    //取得Intent資料
    private void getIntentData(){
        Intent intent = getIntent();
        stitle = intent.getStringExtra("stitle");
        xbody = intent.getStringExtra("xbody");
        img_url = intent.getStringExtra("img_url");
        lat = intent.getDoubleExtra("lat", -1);
        lng = intent.getDoubleExtra("lng", -1);
        address = intent.getStringExtra("address");
        memo_time = intent.getStringExtra("memo_time");
        photoList = intent.getStringArrayListExtra("photos");
    }
    //開始導航
    private void gotoMap() {
        // Search for restaurants nearby
        //google.navigation:q=a+street+address
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
    //GridView的Adapter
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context context) {
            mContext = context;
        }

        public int getCount() {
            return photoList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(
                        new ViewGroup.LayoutParams(screenWidth/3, newHeight/3));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }
            GlideApp.with(mContext)
                    .load(photoList.get(position))
                    .into(imageView);
            return imageView;
        }
    }
}
