package tw.org.iii.travelapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ThemesActivity extends AppCompatActivity {
    private LinearLayout themes_layout;
    private String list[] = {"紅色", "黃色", "藍色", "綠色", "灰色", "深灰色", "白色(預設)"};
    private String backgroundColor;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);

        setTitle("佈景主題更換");
        themes_layout = findViewById(R.id.themes_main);
        getThemeData();

        init();
    }

    private void init(){
        ArrayAdapter<Object> adapter = new ArrayAdapter<Object>(
                ThemesActivity.this,
                android.R.layout.simple_list_item_single_choice,
                list);

        ListView lv = findViewById(R.id.themes_list);

        lv.setAdapter(adapter);

        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                backgroundColor = (String)parent.getItemAtPosition(position);
                setThemes();
            }
        });
    }

    private void getThemeData(){
        sp = getSharedPreferences("themedata", MODE_PRIVATE);
        editor = sp.edit();

        String color = sp.getString("backgroundColor", null);
        //if判斷句用來解決清除app資料時，無法獲得資料而造成的例外
        if(color != null) {
            switch (color) {
                case "紅色":
                    themes_layout.setBackgroundColor(Color.RED);
                    break;
                case "黃色":
                    themes_layout.setBackgroundColor(Color.YELLOW);
                    break;
                case "綠色":
                    themes_layout.setBackgroundColor(Color.GREEN);
                    break;
                case "藍色":
                    themes_layout.setBackgroundColor(Color.BLUE);
                    break;
                case "灰色":
                    themes_layout.setBackgroundColor(Color.GRAY);
                    break;
                case "深灰色":
                    themes_layout.setBackgroundColor(Color.DKGRAY);
                    break;
                case "白色(預設)":
                    themes_layout.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    }
    //套用按鈕
    public void apply(View view){
        editor.putString("backgroundColor", backgroundColor);
        editor.commit();
        Toast.makeText(this, "背景顏色 (" + backgroundColor + ")已套用",
                Toast.LENGTH_SHORT).show();
    }
    //設定背景顏色
    private void setThemes(){
        if(backgroundColor != null) {
            switch (backgroundColor) {
                case "紅色":
                    themes_layout.setBackgroundColor(Color.RED);
                    break;
                case "黃色":
                    themes_layout.setBackgroundColor(Color.YELLOW);
                    break;
                case "綠色":
                    themes_layout.setBackgroundColor(Color.GREEN);
                    break;
                case "藍色":
                    themes_layout.setBackgroundColor(Color.BLUE);
                    break;
                case "灰色":
                    themes_layout.setBackgroundColor(Color.GRAY);
                    break;
                case "深灰色":
                    themes_layout.setBackgroundColor(Color.DKGRAY);
                    break;
                case "白色(預設)":
                    themes_layout.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
    }
}