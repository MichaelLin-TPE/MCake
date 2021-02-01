package com.cake.mcakeapp.tool;

import android.content.Context;
import android.graphics.Typeface;

import androidx.collection.SimpleArrayMap;

public class TypefaceHelper {

    private static final SimpleArrayMap<String, Typeface> TYPEFACE_CACHE = new SimpleArrayMap<String, Typeface>();

    public static Typeface get(Context context, String name) {
        synchronized (TYPEFACE_CACHE) {
            if (!TYPEFACE_CACHE.containsKey(name)) {

                try {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), name);
                    TYPEFACE_CACHE.put(name, t);
                } catch (Exception e) {
                    MichaelLog.i("Could not get typeface '" + name
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return TYPEFACE_CACHE.get(name);
        }
    }

}
