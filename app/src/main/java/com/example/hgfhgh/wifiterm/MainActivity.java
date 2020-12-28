package com.example.hgfhgh.wifiterm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    static double tempTop;
    static double tempMid;
    static double tempBot;
    static int ls; // 0-норма 1-авария
    static boolean alarmFlag;
    static String connectionStatus;



    static TextView msgText;       //ждя отладки
    static TextView sTempTop;
    static TextView sTempMid;
    static TextView sTempBot;
    static TextView sLs;
    static ImageView redLS;
    static ImageView greenLS;

    Thread alarmThread;
    AlarmDetector ad;

    IncomingReader iReader;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sTempTop = (TextView) findViewById(R.id.sTempTop);
        sTempMid = (TextView) findViewById(R.id.sTempMid);
        sTempBot = (TextView) findViewById(R.id.sTempBot);
        sLs = (TextView) findViewById(R.id.sLs);
        redLS = (ImageView) findViewById(R.id.redLS);
        greenLS = (ImageView) findViewById(R.id.greenLS);

        msgText = (TextView) findViewById(R.id.msgText);

        iReader = (IncomingReader) getLastCustomNonConfigurationInstance();
        if (iReader == null) {
            iReader = new IncomingReader();
            iReader.execute();
        }

        if (alarmThread == null) {
            ad = new AlarmDetector(this);
            alarmThread = new Thread(ad);
            ad.setRun();
            alarmThread.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ad.resetRun();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return iReader;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menuSettings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onAckButtonClick(View view) {
        AlarmDetector.alarmAck = true;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    static class IncomingReader extends AsyncTask<Void, String, Void> {
        String message;
        String connectionStatus;

        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                InputStreamReader streamReader;
                BufferedReader reader;
                Socket socket;
                message = "***";
                try{
                    socket = new Socket("192.168.1.52", 8080);
                    socket.setSoTimeout(5000);
                    streamReader = new InputStreamReader(socket.getInputStream());
                    reader = new BufferedReader(streamReader);
                    message = reader.readLine();
                    if (message == null){
                        message = "NaN";
                        connectionStatus = "Data error";
                    } else {
                        connectionStatus = "Connection Ok";
                    }
                    reader.close();
                    if (false){return null;}
                } catch (Exception e) {
                    e.printStackTrace();
                    connectionStatus = e.getMessage().toString();
                }
                publishProgress(message,connectionStatus);
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            String inMessage;
            String strMas[] = new String[4];
            inMessage = values[0];
            int i = 0;
            try {
                for (String retval : inMessage.split(":", 4)) {
                    strMas[i] = retval;
                    i++;
                }
                tempTop = (Double) Double.parseDouble(strMas[0]);
                tempMid = (Double) Double.parseDouble(strMas[1]);
                tempBot = (Double) Double.parseDouble(strMas[2]);
                ls = (Integer) Integer.parseInt(strMas[3]);
            } catch (Exception e){
                e.printStackTrace();
            }
            sTempTop.setText(String.valueOf(tempTop));
            sTempMid.setText(String.valueOf(tempMid));
            sTempBot.setText(String.valueOf(tempBot));
            sLs.setText(String.valueOf(ls));
            msgText.setText(values[1]);

            if (ls == 0){
                redLS.setVisibility(100);
                greenLS.setVisibility(0);
                alarmFlag = false;
            }
            if (ls == 1){
                redLS.setVisibility(0);
                greenLS.setVisibility(100);
                alarmFlag = true;
            }
        }
    }       //----end incomingReader class

}
