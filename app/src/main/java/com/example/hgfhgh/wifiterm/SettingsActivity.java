package com.example.hgfhgh.wifiterm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    double alarmHHTop, alarmHHMid, alarmHHBot, alarmLLTop, alarmLLMid, alarmLLBot;
    boolean alarmHHTopEnb, alarmHHMidEnb, alarmHHBotEnb, alarmLLTopEnb, alarmLLMidEnb, alarmLLBotEnb, alarmLsEnb;

    EditText etAlarmHHTop, etAlarmHHMid, etAlarmHHBot, etAlarmLLTop, etAlarmLLMid, etAlarmLLBot;
    CheckBox checkHHTOP, checkHHMID,checkHHBOT,checkLLTOP,checkLLMID,checkLLBOT, checkLS;

    static String  ALM_HH_TOP = "Alarm_hh_mid",
            ALM_HH_MID = "Alarm_hh_mid",
            ALM_HH_BOT = "Alarm_hh_bot",
            ALM_LL_TOP = "Alarm_ll_top",
            ALM_LL_MID = "Alarm_ll_mid",
            ALM_LL_BOT = "Alarm_ll_bot",
            ALM_HH_TOP_ENB = "Alarm_hh_top_enable",
            ALM_HH_MID_ENB = "Alarm_hh_mid_enable",
            ALM_HH_BOT_ENB = "Alarm_hh_bot_enable",
            ALM_LL_TOP_ENB = "Alarm_ll_top_enable",
            ALM_LL_MID_ENB = "Alarm_ll_mid_enable",
            ALM_LL_BOT_ENB = "Alarm_ll_bot_enable",
            ALM_LS_ENB = "Alarm_ls_enable";

    static SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        etAlarmHHTop = (EditText) findViewById(R.id.etAlmHhTop);
        etAlarmHHMid = (EditText) findViewById(R.id.etAlmHhMid);
        etAlarmHHBot = (EditText) findViewById(R.id.etAlmHhBot);
        etAlarmLLTop = (EditText) findViewById(R.id.etAlmLlTop);
        etAlarmLLMid = (EditText) findViewById(R.id.etAlmLlMid);
        etAlarmLLBot = (EditText) findViewById(R.id.etAlmLlBot);

        checkLS = (CheckBox) findViewById(R.id.checkLSenb);

        sPref = getSharedPreferences("Settings",MODE_PRIVATE);

        try {
            etAlarmLLTop.setText(String.valueOf(sPref.getFloat(ALM_LL_TOP,0)));
            etAlarmLLMid.setText(String.valueOf(sPref.getFloat(ALM_LL_MID,0)));
            etAlarmLLBot.setText(String.valueOf(sPref.getFloat(ALM_LL_BOT,0)));
            etAlarmHHTop.setText(String.valueOf(sPref.getFloat(ALM_HH_TOP,0)));
            etAlarmHHMid.setText(String.valueOf(sPref.getFloat(ALM_HH_TOP,0)));
            etAlarmHHBot.setText(String.valueOf(sPref.getFloat(ALM_HH_BOT,0)));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Something is wrong ...", Toast.LENGTH_LONG).show();
        }

        try {
            if (sPref.getBoolean(ALM_LS_ENB,false))
            checkLS.setChecked(true);
            else checkLS.setChecked(false);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Something is wrong ...", Toast.LENGTH_LONG).show();
        }
    }

    public void onSaveClick(View view) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
        sPref = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        try {
            editor.putFloat(ALM_HH_TOP, Float.parseFloat(etAlarmHHTop.getText().toString()));
            editor.putFloat(ALM_HH_MID, Float.parseFloat(etAlarmHHMid.getText().toString()));
            editor.putFloat(ALM_HH_BOT, Float.parseFloat(etAlarmHHBot.getText().toString()));
            editor.putFloat(ALM_LL_TOP, Float.parseFloat(etAlarmLLTop.getText().toString()));
            editor.putFloat(ALM_LL_MID, Float.parseFloat(etAlarmLLMid.getText().toString()));
            editor.putFloat(ALM_LL_BOT, Float.parseFloat(etAlarmLLBot.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something is wrong ...", Toast.LENGTH_LONG).show();
        }

        try {
            editor.putBoolean(ALM_LS_ENB, checkLS.isChecked());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something is wrong ...", Toast.LENGTH_LONG).show();
        }
        editor.commit();

    }


}
