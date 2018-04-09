package tw.org.iii.travelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class FavoriteActivity extends AppCompatActivity {
    private ListView favorite_list;
    private FavoriteActivity.MyAdapter myAdapter;
    private float screenWidth, screenHeight, newHeight;
    private ImageView item_img;
    private LinkedList<String> title;
    private LinkedList<Integer> imgs;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String [] string_title;
    private int [] int_imgs;
    private Set<String> set_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle("我的最愛");

        string_title = new String[]{"沙鹿肉圓福", "清水阿財米糕",
                "大甲鎮瀾宮", "大甲吊鐘燒"};
        int_imgs = new int[] {R.drawable.meatball, R.drawable.rice_cake,
                R.drawable.dajiamazu, R.drawable.bell_cake};

        set_title = new HashSet<>();

        favorite_list = findViewById(R.id.favorite_list);
        getScreenSize();
        init();

    }
    //取得螢幕大小
    private void getScreenSize(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        newHeight = screenWidth / 16 * 9;
    }

    private void init(){
        sp = getSharedPreferences("favoritedata", MODE_PRIVATE);
        editor = sp.edit();

        title = new LinkedList<>();
        imgs = new LinkedList<>();
        if(title.size() == 0) {
            getItems();
        }

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

    private void getItems(){

        set_title = sp.getStringSet("title", null);
        if(set_title != null) {
            Log.v("brad", "記錄的item數量:" + set_title.size());
        }else{
            Log.v("brad", "記錄的item數量: null");
        }
//        if(set_title.size() == 0) {
            title.add(string_title[0]);
            title.add(string_title[1]);
            title.add(string_title[2]);
            title.add(string_title[3]);

            imgs.add(int_imgs[0]);
            imgs.add(int_imgs[1]);
            imgs.add(int_imgs[2]);
            imgs.add(int_imgs[3]);
//        }else{
//            Iterator it;
//            it = set_title.iterator();
//            while (it.hasNext()){
//                title.add(it.next().toString());
//            }
//        }
        for(String v : title){
            Log.v("brad", v + "\n");
        }

    }

    @Override
    public void finish() {
        Set<String> set_title = new HashSet<>();
        Set<Integer> set_imgs = new HashSet<>();
        set_title.addAll(title);
        set_imgs.addAll(imgs);
        editor.putStringSet("title", set_title);
        Iterator it1 = set_imgs.iterator();
        while(it1.hasNext()){
            editor.putInt("imgs", Integer.parseInt(it1.next().toString()));
        }

        editor.commit();
        set_title = sp.getStringSet("title", null);
        Log.v("brad", "記錄的item數量 = " + set_title.size());
        Iterator it;
        it = set_title.iterator();
        while(it.hasNext()){
            Log.v("brad", it.next().toString() + "\n");
        }

        super.finish();
    }

    //BaseAdapter for ListView
    private class MyAdapter extends BaseAdapter {

        private Context myContext;
        private LayoutInflater inflater;

        public MyAdapter(Context context){
            this.myContext = context;
            inflater = LayoutInflater.from(this.myContext);
        }
        //取得list的數量
        @Override
        public int getCount() {
            return imgs.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            //設置ListView的layout,沒有根目錄root值為null
            convertView = inflater.inflate(R.layout.layout_favorite_listview, null);
            //取得ListView layout的每個view
            item_img = convertView.findViewById(R.id.favorite_item_img);
            TextView item_title = convertView.findViewById(R.id.favorite_item_title);
            TextView removeTv = convertView.findViewById(R.id.favorite_remove);
            //從我的最愛移除
            removeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgs.remove(position);
                    title.remove(position);
                    myAdapter.notifyDataSetChanged();
                }
            });
            //給值
            item_img.setImageResource(imgs.get(position));
            item_title.setText(title.get(position));

            Resources res = getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(res, imgs.get(position));
            //原圖大小
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            //想要的大小
            int newWidth = (int)screenWidth;
            //縮放倍率
            float scalWidth = (float)newWidth / width;
            float scalHeight = newHeight / height;
            Log.v("brad", "" + width +":" + height + "\n" +
                    newWidth + ":" + newHeight + "\n" +
                    scalWidth + ":" + scalHeight);
            Matrix matrix = new Matrix();
            matrix.postScale(scalWidth, scalHeight);
            //取得新的圖
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            item_img.setImageBitmap(bitmap);

            //回傳convertView
            return convertView;
        }
    }
}
