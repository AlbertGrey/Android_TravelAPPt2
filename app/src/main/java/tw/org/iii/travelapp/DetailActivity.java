package tw.org.iii.travelapp;

import android.content.Intent;
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
    private TextView textViewStitle, textViewDesc, textViewAddress, textViewLatLng;
    private int screenWidth, screenHeight, newHeight;
    private String stitle, xbody, img_url, address;
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
        textViewLatLng.setText(lat + ", " + lng);
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
                intentToMap();
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
        textViewLatLng = findViewById(R.id.detail_textViewLatLng);
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
    }
    //intent到MapsActivity
    private void intentToMap(){
        Intent intent = new Intent(DetailActivity.this, MapsActivity.class);
        intent.putExtra("stitle", stitle);
        intent.putExtra("address", address);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        startActivity(intent);
    }

}
