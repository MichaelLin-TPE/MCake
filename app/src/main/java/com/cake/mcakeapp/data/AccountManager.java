package com.cake.mcakeapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.tool.JsonHelper;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AccountManager {

    private static SharedPreferences sharedPreferences;

    private static AccountManager instance = null;

    private static final String USERS_DATA = "users_data";

    private static final String UUID = "uuid";

    public static AccountManager getInstance(){
        if (instance == null){
            init();
            instance = new AccountManager();
            return instance;
        }
        return instance;
    }

    private static void init() {
        sharedPreferences = MyApplication.getInstance().getSharedPreferences("userData", Context.MODE_PRIVATE);
    }

    public void setUserListJson(String json){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERS_DATA,json);
        editor.apply();
    }

    public ArrayList<UserData> getUserList(){

        String json = sharedPreferences.getString(USERS_DATA,"");
        if (json == null || json.isEmpty()){
            return null;
        }
        return JsonHelper.getGson().fromJson(json,new TypeToken<ArrayList<UserData>>(){}.getType());
    }


    public void setUserUUID(String uuid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(UUID,uuid);
        editor.apply();
    }

    public String getUuid(){
        return sharedPreferences.getString(UUID,"");
    }
}
