package ir.enigma.app.util;


import android.content.Context;
import android.content.SharedPreferences;
import com.chrisney.enigma.EnigmaUtils;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = EnigmaUtils.enigmatization(new byte[]{62, -52, 113, -50, -88, -77, -109, 9, 69, 13, -19, -27, -24, -53, 79, -87, 115, 76, -72, 105, 99, 84, 28, 79, 109, 2, 106, -99, -5, -54, 47, -84});
    public static final String KEY_TOKEN = EnigmaUtils.enigmatization(new byte[]{118, -82, 47, -38, 56, -16, -64, -64, 89, 93, 103, -69, -91, -12, 19, -33});

    private final Context mCtx;

    public static final String MBXCGQKXWR = "UMzveSQ0F6fdV";

    public SharedPrefManager(Context context) {
        mCtx = context;
    }


    public void putInt(String name, int item) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(name, item);
        editor.apply();
        if (MBXCGQKXWR.isEmpty()) MBXCGQKXWR.getClass().toString();

    }

    public Integer getInt(String name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name, -1);
    }

    public Integer getInt(String name, int defaultValue) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(name, defaultValue);
    }

    public void putString(String name, String item) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, item);
        editor.apply();

    }


    public String getString(String name) {
        return getString(name, null);
    }

    public String getString(String name, String defValue) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defValue);
    }

    public void putBoolean(String name, Boolean item) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name, item);
        editor.apply();

    }

    public Boolean getBoolean(String name) {
        return getBoolean(name, false);
    }

    public Boolean getBoolean(String name, boolean defaultValue) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(name, defaultValue);
    }

    public void putLong(String name, Long item) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(name, item);
        editor.apply();

    }

    public Long getLong(String name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(name, -1L);
    }

    public Long getLong(String name, long defaultValue) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(name, defaultValue);
    }

    public void putFloat(String name, Float item) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(name, item);
        editor.apply();
    }

    public Float getFloat(String name) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(name, -1F);
    }

}
