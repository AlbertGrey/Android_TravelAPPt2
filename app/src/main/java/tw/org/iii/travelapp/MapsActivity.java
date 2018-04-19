package tw.org.iii.travelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;

    private Marker[] marker;
    private ArrayList<HashMap<String,Object>> destinations;
    private MyDragView dragView;
    private MyListView listView;
    private ArrayList<HashMap<String,String>> data;
    private SimpleAdapter adapter;
    private String address, stitle, location;
    private LatLng latLng;
    private double lat, lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //TODO 從intent拿到資料後把要呈現的資料加入到  destinations是要畫標記的陣列  跟  data是LISTVIEW要呈現的資料

        //接收intent->address
        getIntentData();


        getDragViewAndSetListView();

        destinations = new ArrayList<>();
//        //這是範例
//        HashMap<String,Object> m1 = new HashMap<>();
//        m1.put("lat",new LatLng(25.047155,121.514465));
//        m1.put("position","臺北");
//        destinations.add(m1);
//        HashMap<String,Object> m2 = new HashMap<>();
//        m2.put("lat",new LatLng(25.171855,121.440422));
//        m2.put("position","淡水");
//        destinations.add(m2);

        HashMap<String, Object> mapData = new HashMap<>();
        mapData.put("stitle", stitle);
        mapData.put("location", location);
        mapData.put("lat", lat);
        mapData.put("lng", lng);
        destinations.add(mapData);

        //把資料加到ListView
        for(HashMap<String,Object> hm : destinations){
            HashMap<String,String> listItem = new HashMap<>();
            listItem.put("title",destinations.indexOf(hm)+"");
            listItem.put("texts",hm.get("location").toString());
            data.add(listItem);
        }


//        //把資料加到ListView
//        for(HashMap<String,Object> hm : destinations){
//            HashMap<String,String> listItem = new HashMap<>();
//            listItem.put("title",destinations.indexOf(hm)+"");
//            listItem.put("texts",hm.get("position").toString());
//            data.add(listItem);
//        }
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mMap.clear();
                HashMap<String, Object> hashMap = destinations.get(position);

//                latLng = (LatLng)hashMap.get("lat");
                address = (String)hashMap.get("location");
                double lat = (double)hashMap.get("lat");
                double lng = (double)hashMap.get("lng");
//                double lat = latLng.latitude;
//                double lng = latLng.longitude;
//                Log.v("brad", "address = " + address);
//                Log.v("brad", "lat = " + lat);
//                Log.v("brad", "lng = " + lng);
                MarkerOptions m2 =new MarkerOptions()
                        .position(new LatLng(lat, lng))
                        .title(address);
                mMap.addMarker(m2).showInfoWindow();
                //移動視角到哪個經緯度
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
                //設定視角的大小程度
                mMap.moveCamera(CameraUpdateFactory.zoomTo(13.0f));
            }
        });
        marker = new Marker[destinations.size()];
    }
    //找出DragView 跟他裡面的ListView
    private  void getDragViewAndSetListView(){
        dragView =findViewById(R.id.myDragView);
        data = dragView.getDataList();
        listView =dragView.getListView();
        adapter=dragView.getSimpleAdapter();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("chad",position+"");
            }
        });
    }
    //地圖加載完成調用會傳入一個GoogleMap
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //這個經緯度是預設視角的位置
        LatLng taipei = new LatLng(25.047155, 121.514465);

//        for(HashMap<String,Object> hm : destinations){
//            MarkerOptions m2 =new MarkerOptions()
//                    .position((LatLng) hm.get("stitle"))
//                    .title(hm.get("location").toString());
//            mMap.addMarker(m2).showInfoWindow();
//        }

//        for(HashMap<String,Object> hm : destinations){
//            MarkerOptions m2 =new MarkerOptions().position((LatLng) hm.get("lat")).title(hm.get("position").toString());
//            mMap.addMarker(m2).showInfoWindow();
//        }

        //移動視角到哪個經緯度
        mMap.moveCamera(CameraUpdateFactory.newLatLng(taipei));
        //設定視角的大小程度
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
        //設定MarkClickListener
        mMap.setOnMarkerClickListener(new MyOnMarkerClickListener());

    }
    //在地圖上載入圖片
    private void addImg(){
        LatLng postion =new LatLng(25.171855,121.440422);
        GroundOverlayOptions imgOnMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.ds))
                .position(postion, 8600f, 6500f);
        mMap.addGroundOverlay(imgOnMap);
    }
    //開始導航
    public void gotoMap(View view) {
        // Search for restaurants nearby
        //google.navigation:q=a+street+address
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    //標記的clickListener
    private class MyOnMarkerClickListener implements GoogleMap.OnMarkerClickListener{
        @Override
        public boolean onMarkerClick(Marker marker) {
            LatLng l1 =marker.getPosition();
            return false;
        }
    }

    private void getIntentData(){
        Intent intent =getIntent();
        stitle = intent.getStringExtra("stitle");
        location = intent.getStringExtra("address");
        lat = intent.getDoubleExtra("lat", -1);
        lng = intent.getDoubleExtra("lng", -1);
    }

}