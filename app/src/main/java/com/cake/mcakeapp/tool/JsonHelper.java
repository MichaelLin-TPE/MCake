package com.cake.mcakeapp.tool;

import com.google.gson.Gson;

public class JsonHelper {

    private static Gson gson = new Gson();

    public static Gson getGson(){
        return gson;
    }

}
