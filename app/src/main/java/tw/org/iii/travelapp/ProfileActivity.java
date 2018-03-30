package tw.org.iii.travelapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class ProfileActivity extends AppCompatActivity {
    private ListView listView;
    private LinkedList<HashMap<String,Object>> data;
    private String[] from = {"title", "cont"};
    private int[] to = {R.id.item_title, R.id.item_content};
    private SimpleAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_profile);
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        setTitle("個人資料");

        listView = findViewById(R.id.list);
        data = new LinkedList<>();
        init();
    }

    private void init(){
        HashMap<String,Object> data0 = new HashMap<>();
        data0.put(from[0], "帳號");
        data0.put(from[1], "0912345678");
        data.add(data0);

        HashMap<String,Object> data1 = new HashMap<>();
        data1.put(from[0], "密碼");
        data1.put(from[1], "********");
        data.add(data1);

        adapter = new SimpleAdapter(
                this, data, R.layout.layout_item, from, to);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("brad", "" + position);

            }
        });
    }


}
