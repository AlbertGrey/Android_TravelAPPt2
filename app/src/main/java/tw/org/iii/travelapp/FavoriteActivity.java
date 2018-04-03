package tw.org.iii.travelapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FavoriteActivity extends AppCompatActivity {
    private ListView favorite_list;
    private int [] imgs;
    private String [] title, category;
    private FavoriteActivity.MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle("我的最愛");
        favorite_list = findViewById(R.id.favorite_list);
        init();


    }

    private void init(){
        imgs = new int[]{R.drawable.meatball, R.drawable.rice_cake, R.drawable.dajiamazu, R.drawable.bell_cake};
        title = new String[]{"沙鹿肉圓福", "清水阿財米糕", "大甲鎮瀾宮", "大甲吊鐘燒"};
        category = new String[]{"熱門", "美食", "景點", "美食"};
        myAdapter = new MyAdapter(FavoriteActivity.this);
        favorite_list.setAdapter(myAdapter);
        favorite_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        favorite_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("brad", "" + position);


            }
        });
    }

    //BaseAdapter for ListView
    private class MyAdapter extends BaseAdapter {

        Context myContext;
        LayoutInflater inflater;

        public MyAdapter(Context context){
            this.myContext = context;
            inflater = LayoutInflater.from(this.myContext);
        }
        //取得list的數量
        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //設置ListView的layout,沒有根目錄root值為null
            convertView = inflater.inflate(R.layout.layout_favorite_listview, null);
            //取得ListView layout的每個view
            ImageView item_img = convertView.findViewById(R.id.favorite_item_img);
            TextView item_title = convertView.findViewById(R.id.favorite_item_title);
            TextView item_category = convertView.findViewById(R.id.favorite_category);
            //給值
            item_img.setImageResource(imgs[position]);
            item_title.setText(title[position]);
            item_category.setText(category[position]);
            //回傳convertView
            return convertView;
        }
    }
}
