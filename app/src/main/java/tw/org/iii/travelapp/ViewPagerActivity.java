package tw.org.iii.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bm.library.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<String> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        viewPager = findViewById(R.id.pager);

        Intent intent = getIntent();
        photoList = intent.getStringArrayListExtra("photos");


        List<String> images = new ArrayList<>();
        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        images.add("https://www.travel.taipei/d_upload_ttn/sceneadmin/image/A0/B0/C0/D975/E0/F413/b7c25472-02a3-4a02-ac57-8d3f6e8ce31b.jpg");
        images.add("https://www.travel.taipei/d_upload_ttn/sceneadmin/image/A0/B0/C0/D102/E786/F657/5613da59-eccd-4f6e-a36e-57a5859e69a6.jpg");
        images.add("https://www.travel.taipei/d_upload_ttn/sceneadmin/image/A0/B0/C0/D308/E138/F645/89de2827-154f-4eef-b4e4-6257696d75e3.jpg");
        images.add("https://www.travel.taipei/d_upload_ttn/sceneadmin/image/A0/B0/C1/D236/E235/F92/ac302ead-7e8b-460d-9b5b-47ad6c499a2b.jpg");
        ViewPagerAdapter adapter = new ViewPagerAdapter (ViewPagerActivity.this, photoList);
        viewpager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends PagerAdapter {
        Context context;
        private List<String> _imagePaths;
        private LayoutInflater inflater;
        public ViewPagerAdapter (Context context, List<String> imagePaths) {
            this._imagePaths = imagePaths;
            this.context = context;
        }

        @Override
        public int getCount() {
            return this._imagePaths.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }
        @Override

        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_viewpager_photos, container,
                    false);
            photoView = (PhotoView) viewLayout.findViewById(R.id.image);
            photoView.enable();
            GlideApp.with(context)
                    .load(_imagePaths.get(position))
                    .fitCenter()
                    .into(photoView);
            (container).addView(viewLayout);
            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            (container).removeView((LinearLayout) object);
        }
    }
}
