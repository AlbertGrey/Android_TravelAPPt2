package tw.org.iii.travelapp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bm.library.PhotoView;

public class ShowStickerActivity extends AppCompatActivity {
    private PhotoView photoView;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sticker);

        photoView = findViewById(R.id.showSticker_photoView);
        sp = getSharedPreferences("sticker", MODE_PRIVATE);
        String sticker = sp.getString("sticker", null);

        photoView.enable();
        Bitmap bitmap = BitmapFactory.decodeFile(sticker);
        photoView.setImageBitmap(bitmap);

    }
}
