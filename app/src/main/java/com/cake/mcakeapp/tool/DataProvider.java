package com.cake.mcakeapp.tool;

import android.view.Menu;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;
import com.cake.mcakeapp.data.MenuData;
import com.cake.mcakeapp.data.ProductData;

import java.util.ArrayList;

public class DataProvider {

    private static ArrayList<byte[]> photoArray;


    public static void setPhotoArray(ArrayList<byte[]> photo){
        photoArray = photo;
    }
    public static ArrayList<byte[]> getPhotoArray(){
        return photoArray;
    }


    public static ArrayList<MenuData> getMenuList(){
        ArrayList<MenuData> menuDataArrayList = new ArrayList<>();
        menuDataArrayList.add(new MenuData(R.drawable.product_list, MyApplication.getInstance().getApplicationContext().getString(R.string.product_list)));
        menuDataArrayList.add(new MenuData(R.drawable.member, MyApplication.getInstance().getApplicationContext().getString(R.string.memeber_center)));
        menuDataArrayList.add(new MenuData(R.drawable.order, MyApplication.getInstance().getApplicationContext().getString(R.string.search_order)));
        menuDataArrayList.add(new MenuData(R.drawable.cart, MyApplication.getInstance().getApplicationContext().getString(R.string.shopping_cart)));
        menuDataArrayList.add(new MenuData(R.drawable.contact, MyApplication.getInstance().getApplicationContext().getString(R.string.contact_us)));
        menuDataArrayList.add(new MenuData(R.drawable.comment,MyApplication.getInstance().getApplicationContext().getString(R.string.comment)));
        return menuDataArrayList;
    }

}
