package com.cake.mcakeapp.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class ImageHelper {

    private static ImageHelper instance = null;

    private static ImageLoader imageLoader;

    public static ImageHelper getInstance(){
        if (instance == null){
            init();
            instance = new ImageHelper();
            return instance;
        }
        return instance;
    }

    private static DisplayImageOptions options;

    public static void init(){
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.user)
                .showImageOnFail(R.drawable.user)
                .showImageOnLoading(R.drawable.user)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MyApplication.getInstance().getApplicationContext())
                .defaultDisplayImageOptions(options).build();
        imageLoader.init(config);
    }

    public void setImageResource(ImageView imageView , String url){
        imageLoader.displayImage(url,imageView);
    }
    public void setImageResource(ImageView imageView, byte[] imageArray){
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray,0,imageArray.length);
        imageView.setImageBitmap(bitmap);
    }

}
