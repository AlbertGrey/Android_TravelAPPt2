package tw.org.iii.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textViewStitle, textViewDesc, textViewAddress, textViewMemo_time;
    private int screenWidth, screenHeight, newHeight;
    private String stitle, xbody, img_url, address, memo_time;
    private double lat, lng;
    private LinearLayout navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
//                        .placeholder(R.drawable.loading)
                .into(imageView);

        setClickListener();

    }
    //設定導航按鈕事件
    private void setClickListener(){
        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMap();
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
        imageView = findViewById(R.id.detail_image);
        textViewStitle = findViewById(R.id.detail_textViewTitle);
        textViewDesc = findViewById(R.id.detail_textViewDesc);
        textViewAddress = findViewById(R.id.detail_textViewAddress);
        textViewMemo_time = findViewById(R.id.detail_textViewMemoTime);
        navigation = findViewById(R.id.detail_navigation);
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
}
