package com.example.hgfhgh.wifiterm;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by hgfhgh on 08.09.2017.
 */
public class SettingsClass extends AppCompatActivity {
    static SharedPreferences sPref;

    static boolean almLsEnb;

    public SettingsClass() {
        sPref = getSharedPreferences("Settings", MODE_PRIVATE);

        almLsEnb = sPref.getBoolean("ALM_LS_ENB", false);
    }

    public static boolean getAlmLsEnb(){
        return almLsEnb;
    }


}
