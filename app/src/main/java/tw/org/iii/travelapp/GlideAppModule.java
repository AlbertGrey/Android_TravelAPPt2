package tw.org.iii.travelapp;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by MacGyver on 2018/4/13.
 */
@GlideModule
public class GlideAppModule extends AppGlideModule {

//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        super.applyOptions(context, builder);
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide, Registry registry) {
//        super.registerComponents(context, glide, registry);
//    }
}
