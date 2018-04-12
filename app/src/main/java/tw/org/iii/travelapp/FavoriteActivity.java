package tw.org.iii.travelapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    private String description, img_url;
    private ArrayList<DataStation> dataList;
    private Bitmap bitmap;
    private ArrayList<Bitmap> bitmapArrayList;

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
        dataList = new ArrayList<>();
        bitmapArrayList = new ArrayList<>();
        favorite_list = findViewById(R.id.favorite_list);

        MyTask myTask = new MyTask();
        myTask.execute("http://36.234.13.158:8080/J2EE/getData.jsp?start=0&rows=10");
        getScreenSize();
//        init();


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
            return dataList.size();
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
//                    imgs.remove(position);
//                    title.remove(position);
                    dataList.remove(position);
                    bitmapArrayList.remove(position);
                    myAdapter.notifyDataSetChanged();
                }
            });
            //給值
//            item_img.setImageResource(imgs.get(position));
//            item_title.setText(title.get(position));
            item_title.setText(dataList.get(position).getDescription());

//            Resources res = getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(res, imgs.get(position));
//            //原圖大小
//            int width = bitmap.getWidth();
//            int height = bitmap.getHeight();
//            //想要的大小
//            int newWidth = (int)screenWidth;
//            //縮放倍率
//            float scalWidth = (float)newWidth / width;
//            float scalHeight = newHeight / height;
//            Log.v("brad", "" + width +":" + height + "\n" +
//                    newWidth + ":" + newHeight + "\n" +
//                    scalWidth + ":" + scalHeight);
//            Matrix matrix = new Matrix();
//            matrix.postScale(scalWidth, scalHeight);
            //取得新的圖
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
//            item_img.setImageBitmap(bitmap);

            String imgURL = dataList.get(position).getImg_url();
            StringBuilder sb = new StringBuilder(imgURL);
            sb.insert(4,"s");
            Log.v("brad", "URL:" + sb.toString());
            Log.v("brad", "bitmapArrayList.size():"+bitmapArrayList.size());

//            new GetImageFromURL(item_img).execute(sb.toString());
            item_img.setImageBitmap(bitmapArrayList.get(position));
            //回傳convertView
            return convertView;
        }
    }
    //解析JSON字串
    private void parseJSON(String s){
        int time = 1;
        try {
            JSONArray array = new JSONArray(s);
            for (int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                JSONArray imgs = obj.getJSONArray("imgs");
//                for(int x = 0; x < imgs.length(); x++){
                JSONObject obj2 = imgs.getJSONObject(0);
                description = obj2.getString("description");
                img_url = obj2.getString("url");
//                }
                String cat2 = obj.getString("CAT2");
                String xbody = obj.getString("xbody");
                String address = obj.getString("address");
                String stitle = obj.getString("stitle");
                String memo_time = obj.getString("MEMO_TIME");
                double lng = obj.getDouble("lng");
                double lat = obj.getDouble("lat");

//                Log.v("brad:","img->"+description+"/"+img_url+" /"+cat2+"/"+xbody+"/"+address+"/"+stitle+"/"+memo_time+"/"+lng+"/"+lat);
                Log.v("brad", time + "." + description);
                Log.v("brad", img_url);
                DataStation t = new DataStation(description, img_url);
//                Log.v("brad", "T是：" + t.toString());
                dataList.add(t);
                time++;
            }

            String last = dataList.get(dataList.size()-1).getImg_url();
            Log.v("brad", "最後一個Img_url是:" + last);





        } catch (JSONException e) {
            Log.v("brad", e.toString());
        }

    }


    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = getData(strings[0]);
//            Log.v("brad", "URL:" + img_url);
            parseJSON(data);


            for(int i = 0; i < dataList.size(); i++) {
                StringBuilder sb = new StringBuilder(dataList.get(i).getImg_url());
                sb.insert(4,"s");
//                new GetImageFromURL().execute(sb.toString());
//                Log.v("brad", "URL123:" + sb.toString());

                String urldiaplay = sb.toString();
                bitmap = null;
                try {
//                URL newurl = new URL(urldiaplay);
//                HttpURLConnection conn = (HttpURLConnection) newurl.openConnection();
//                conn.setDoInput(true);
//                conn.connect();
//                InputStream srt = conn.getInputStream();
                    InputStream srt = new URL(urldiaplay).openStream();
                    bitmap = BitmapFactory.decodeStream(srt);

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
                    bitmapArrayList.add(bitmap);
                    Log.v("brad", "bitmap.size:" + bitmapArrayList.size());

                } catch (Exception e) {
                    Log.v("brad", e.toString());
                }

            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            init();
//            StringBuilder sb = new StringBuilder(img_url);
//            sb.insert(4,"s");
//            Log.v("brad", "URL:" + sb.toString());
//            new GetImageFromURL(iv).execute(sb.toString());

//            Log.v("brad", "dataList.size():"+dataList.get(9).getImg_url());

        }

        private String getData(String url){
            StringBuffer sb = new StringBuffer();
            try {
                URL newURL = new URL(url);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(newURL.openStream()));
                String line;
                while( (line = br.readLine()) != null){
                    Log.v("brad", line);
                    sb.append(line);
                }
            } catch (MalformedURLException e) {
                Log.v("brad", e.toString());
            } catch (IOException e) {
                Log.v("brad", e.toString());
            }
            return sb.toString();
        }
    }
    //從URL取得圖片
    public class GetImageFromURL extends AsyncTask<String, Void, Bitmap>{

        ImageView imageView;

        public GetImageFromURL(){

        }

        public GetImageFromURL(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            String urldiaplay = url[0];
            bitmap = null;
            try {
//                URL newurl = new URL(urldiaplay);
//                HttpURLConnection conn = (HttpURLConnection) newurl.openConnection();
//                conn.setDoInput(true);
//                conn.connect();
//                InputStream srt = conn.getInputStream();
                InputStream srt = new URL(urldiaplay).openStream();
                bitmap = BitmapFactory.decodeStream(srt);

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
                bitmapArrayList.add(bitmap);
                Log.v("brad", "bitmap.size:" + bitmapArrayList.size());

            } catch (Exception e) {
                Log.v("brad", e.toString());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);


//            imageView.setImageBitmap(bitmap);
        }
    }
}
