package tw.org.iii.travelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView listViewStitle, listViewDesc;
    private int screenWidth, screenHeight, newHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("景點資訊");

        imageView = findViewById(R.id.detail_image);
        listViewStitle = findViewById(R.id.detail_textViewTitle);
        listViewDesc = findViewById(R.id.detail_textViewDesc);

        getScreenSize();

        Intent intent = getIntent();
        String stitle = intent.getStringExtra("stitle");
        String xbody = intent.getStringExtra("xbody");
        String img_url = intent.getStringExtra("img_url");

        listViewStitle.setText(stitle);
        listViewDesc.setText(xbody);

        GlideApp
                .with(DetailActivity.this)
                .load(img_url)
                        .override(screenWidth, newHeight)
                        .centerCrop()
//                        .placeholder(R.drawable.loading)
                .into(imageView);

    }

    private void getScreenSize(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        newHeight = screenWidth / 16 * 9;
    }

}
