package tw.org.iii.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ListView main_list;
    private PopupWindow popupWindow;
    private Button btnConfirm;
    private String [] data1, data2;
    private MyAdapter myAdapter;
    private LinearLayout iv_home, iv_guide, iv_camera, iv_search, iv_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_list = findViewById(R.id.main_list);
        iv_home = findViewById(R.id.btn_home);
        iv_guide = findViewById(R.id.btn_guide);
        iv_camera = findViewById(R.id.btn_camera);
        iv_search = findViewById(R.id.btn_search);
        iv_setting = findViewById(R.id.btn_setting);

        init();
        setIconListener();
    }

    private void init(){
        data1 = new String[]{"個人資料", "我的最愛", "佈景主題更換", "關於我"};
        data2 = new String[]{};
        myAdapter = new MyAdapter(MainActivity.this);
        main_list.setAdapter(myAdapter);
        main_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.v("brad", "" + position);
                switch (position){
                    case 0:
                        gotoProfile();
                        break;
                    case 1:
                        gotoFavorite();
                        break;
                    case 2:
                        gotoTheme();
                        break;
                    case 3:
                        gotoAboutMe();
                        break;
                }

            }
        });
    }

    private void setIconListener(){

        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_home.setBackgroundColor(Color.rgb(169,169,169));
                iv_guide.setBackgroundColor(Color.BLACK);
                iv_camera.setBackgroundColor(Color.BLACK);
                iv_search.setBackgroundColor(Color.BLACK);
                iv_setting.setBackgroundColor(Color.BLACK);

            }
        });

        iv_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_home.setBackgroundColor(Color.BLACK);
                iv_guide.setBackgroundColor(Color.rgb(169,169,169));
                iv_camera.setBackgroundColor(Color.BLACK);
                iv_search.setBackgroundColor(Color.BLACK);
                iv_setting.setBackgroundColor(Color.BLACK);
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
                iv_home.setBackgroundColor(Color.BLACK);
                iv_guide.setBackgroundColor(Color.BLACK);
                iv_camera.setBackgroundColor(Color.BLACK);
                iv_search.setBackgroundColor(Color.BLACK);
                iv_setting.setBackgroundColor(Color.rgb(169,169,169));
            }
        });
    }



    //個人資料選單
    private void gotoProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    //我的最愛
    private void gotoFavorite(){
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }
    //更換佈景主題
    private void gotoTheme(){
        Intent intent = new Intent(this, ThemesActivity.class);
        startActivity(intent);
    }
    //關於我選單
    private void gotoAboutMe() {
        View view = LayoutInflater.from(this)
                //設定輸出的layout
                .inflate(R.layout.layout_about_me, null);
        popupWindow = new PopupWindow(view);
        //設定popupWindow的寬、高
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.v("brad", event.toString());
                if(event.getAction() == MotionEvent.ACTION_UP){

                    popupWindow.dismiss();
                }
                return true;
            }
        });

        btnConfirm =  view.findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 0);

    }
    //BaseAdapter for ListView
    private class MyAdapter extends BaseAdapter{

        Context myContext;
        LayoutInflater inflater;

        public MyAdapter(Context context){
            this.myContext = context;
            inflater = LayoutInflater.from(this.myContext);
        }
        //取得list的數量
        @Override
        public int getCount() {
            return data1.length;
        }

        @Override
        public Object getItem(int position) {
            return data1[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //設置ListView的layout,沒有根目錄root值為null
            convertView = inflater.inflate(R.layout.layout_setting, null);
            //取得ListView layout的每個view
            TextView item_title = convertView.findViewById(R.id.item_title);
            TextView item_content = convertView.findViewById(R.id.item_content);
            //給值
            item_title.setText(data1[position]);
            item_content.setText("");
            //回傳convertView
            return convertView;
        }
    }
}
