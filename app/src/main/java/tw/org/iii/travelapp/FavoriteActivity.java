package tw.org.iii.travelapp;

import android.content.Context;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavoriteActivity extends AppCompatActivity {

    private ListView favorite_list;
    private FavoriteActivity.MyAdapter myAdapter;
    private float screenWidth, screenHeight, newHeight;
    private ImageView item_img;
    private String stitle, img_url;
    private ArrayList<DataStation> dataList;
    private RequestQueue queue;
    private String url = "http://36.235.38.228:8080/fsit04/User_favorite";
    private String userId = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        setTitle("我的最愛");
        queue= Volley.newRequestQueue(this);

        for(int i = 1; i <= 20; i++){
            addFavorite(userId, "" + i);
        }

        dataList = new ArrayList<>();
        favorite_list = findViewById(R.id.favorite_list);
        getScreenSize();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(url);
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
        myAdapter = new MyAdapter(FavoriteActivity.this);
        favorite_list.setAdapter(myAdapter);
        favorite_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        favorite_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //觸發按鈕事件
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intentToDetail(position);
           }
        });
    }
    //intent至detail頁面
    private void intentToDetail(int position){
        String img_url = dataList.get(position).getImg_url();
        String stitle = dataList.get(position).getStitle();
        String xbody = dataList.get(position).getXbody();
        String total_id = dataList.get(position).getTotal_id();
        double lat = dataList.get(position).getLat();
        double lng = dataList.get(position).getLng();
        String address = dataList.get(position).getAddress();

        Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
        intent.putExtra("stitle", total_id + "." + stitle);
        intent.putExtra("xbody", xbody);
        intent.putExtra("img_url", img_url);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        intent.putExtra("address", address);
        startActivity(intent);
    }

    @Override
    public void finish() {
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
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.layout_favorite_listview, null);
            }
            //取得ListView layout的每個view
            item_img = convertView.findViewById(R.id.favorite_item_img);
            TextView item_title = convertView.findViewById(R.id.favorite_item_title);
            ImageView removeTv = convertView.findViewById(R.id.favorite_remove);
            //從我的最愛移除
            removeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String total_Id = dataList.get(position).getTotal_id();
                    deleteFavorite(userId, total_Id);
                    dataList.remove(position);
                    myAdapter.notifyDataSetChanged();
                }
            });
            //設定title
            String stitle = dataList.get(position).getStitle();
            String total_id = dataList.get(position).getTotal_id();
            item_title.setText(total_id + "." + stitle);
            //設定photo
            GlideApp
                    .with(FavoriteActivity.this)
                    .load(dataList.get(position).getImg_url())
                        .override((int)screenWidth, (int)newHeight)
//                        .centerCrop()
//                        .placeholder(R.drawable.loading)
                    .into(item_img);
            //回傳convertView
            return convertView;
        }
    }
    //解析JSON字串
    private void parseJSON(String s){
        try {
            JSONArray array = new JSONArray(s);
            for (int i=0; i<array.length(); i++){
                JSONObject obj = array.getJSONObject(i);
                JSONArray imgs = obj.getJSONArray("Img");
                JSONObject obj2 = imgs.getJSONObject(0);
                String total_id = obj.getString("total_id");
                String description = obj2.getString("description");
                img_url = obj2.getString("url");
                String cat2 = obj.getString("CAT2");
                String xbody = obj.getString("xbody");
                String address = obj.getString("address");
                stitle = obj.getString("name");
                String memo_time = obj.getString("MEMO_TIME");
                double lng = obj.getDouble("lng");
                double lat = obj.getDouble("lat");

                DataStation data = new DataStation(
                        total_id, stitle, img_url, xbody, lat, lng, address);
                dataList.add(data);
            }
        } catch (JSONException e) {
            Log.v("brad", e.toString());
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
                String data = getData(strings[0]);
                parseJSON(data);
                return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            init();
        }
        //從URL獲取JSON字串
        private String getData(String url){
            StringBuffer sb = new StringBuffer();
            try {
                URL newURL = new URL(url);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(newURL.openStream()));
                String line;
                while( (line = br.readLine()) != null){
                    sb.append(line);
                }
            } catch (IOException e) {
                Log.v("brad", e.toString());
            }
            return sb.toString();
        }
    }
    /** 加入我的最愛
     *
     * @param user_id     用戶id
     * @param total_id   地點的id
     */
    private void addFavorite(String user_id,String total_id){
        final String p1 =user_id;
        final String p2=total_id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad",response);
                    }
                }, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> m1 =new HashMap<>();
                m1.put("user_id",p1);
                m1.put("total_id", p2);
                return m1;
            }
        };
        queue.add(stringRequest);
    }
    /** 刪除我的最愛
     *
     * @param user_id     用戶id
     * @param total_id   地點的id
     */
    private void deleteFavorite(String user_id,String total_id){
        final String p1 = user_id;
        final String p2 = total_id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("brad",response);
                    }
                }, null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> m1 =new HashMap<>();
                m1.put("_method","DELETE");
                m1.put("user_id",p1);
                m1.put("total_id", p2);

                return m1;
            }
        };
        queue.add(stringRequest);
    }
    /** 取得我的最愛
     *
     * @param user_id 用戶id
     */
    private void getFavorite(String user_id){
        final String p1=user_id;
        String url ="http://36.234.10.186:8080/fsit04/User_favorite?user_id="+p1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseGetFavorite(response);
                    }
                }, null);
        queue.add(stringRequest);
    }
    /**  用戶我的最愛parseJSON
     *
     * @param response
     */
    private void parseGetFavorite(String response){
        try {
            JSONArray array1 = new JSONArray(response);
            for(int i= 0;i<array1.length();i++) {
                JSONObject ob1 =array1.getJSONObject(i);

                String name = ob1.getString("name");
                Log.v("brad",name);

                String type= ob1.getString("type");
                Log.v("brad",type);

                String CAT2 = ob1.getString("CAT2");
                Log.v("brad",CAT2);

                String MEMO_TIME = ob1.getString("MEMO_TIME");
                Log.v("brad",MEMO_TIME);

                String address = ob1.getString("address");
                Log.v("brad",address);

                String xbody = ob1.getString("xbody");
                Log.v("brad",xbody);

                String lat = ob1.getString("lat");
                Log.v("brad",lat);

                String lng = ob1.getString("lng");
                Log.v("brad",lng);

                JSONArray imgs =ob1.getJSONArray("Img");
                for(int y= 0;y<array1.length();y++){
                    String description =imgs.getJSONObject(y).getString("description");
                    Log.v("brad",description);
                    String imgUrl = imgs.getJSONObject(y).getString("url");
                    Log.v("brad",imgUrl);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
