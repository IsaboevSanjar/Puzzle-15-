package uz.isytem.puzzle15.core.cache;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import uz.isytem.puzzle15.R;
import uz.isytem.puzzle15.core.models.UserData;

public class MemoryHelper {
    private static MemoryHelper helper;
    private SharedPreferences preferences;

    private MemoryHelper(Context context) {
        //context
        preferences = context.getSharedPreferences("puzzle15", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (helper == null) {
            helper = new MemoryHelper(context);
        }
    }

    public static MemoryHelper getHelper() {
        return helper;
    }

    public void saveDate(UserData userData) {
        preferences.edit().putString("name_" + getLastIndex(), userData.getName()).apply();
        preferences.edit().putInt("step_" + getLastIndex(), userData.getStep()).apply();
        preferences.edit().putInt("time_" + getLastIndex(), userData.getStep()).apply();
        saveLastIndex();
    }

    private void saveLastIndex() {
        preferences.edit().putInt("index", getLastIndex() + 1).apply();
    }

    private int getLastIndex() {
        return preferences.getInt("index", 0);
    }

    public UserData getData(int index) {
        UserData userData = new UserData(
                preferences.getString("name_" + index, ""),
                preferences.getInt("step_" + index, 0),
                preferences.getInt("time_" + index, 0)
        );
        return userData;
    }

    public ArrayList<UserData> getLastResults() {
        ArrayList<UserData> list = new ArrayList<>();
        int n = getLastIndex();
        for (int i = 0; i < n; i++) {
            list.add(getData(i));
        }
        return list;
    }

    public void clearData() {
        preferences.edit().clear().apply();
        //        preferences.edit().remove()
    }

    public boolean getSoundState() {
        return preferences.getBoolean("sound_state", false);
    }

    public void setSoundState(boolean b) {
        preferences.edit().putBoolean("sound_state", b).apply();
    }

    public boolean getThemeState() {
        return preferences.getBoolean("theme_state", false);
    }

    public void setThemeState(boolean b) {
        preferences.edit().putBoolean("theme_state", b).apply();
    }

    public boolean getSoundStateBtn() {
        return preferences.getBoolean("sound_btn_state", false);
    }

    public void setSoundStateBtn(boolean b) {
        preferences.edit().putBoolean("sound_btn_state", b).apply();
    }

    public int getThemeId() {
        return preferences.getInt("theme_order", R.drawable.again_pexels);
    }

    public void setThemeId(int themeId) {
        preferences.edit().putInt("theme_order", themeId).apply();
    }

}
