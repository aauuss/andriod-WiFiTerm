package com.example.hgfhgh.wifiterm;

import android.content.Context;


/**
 * Created by hgfhgh on 09.08.2017.
 */

public class AlarmDetector implements Runnable{
    static boolean alarmState;  // состояние аларма 1-авария 0 - норма
    static boolean alarmAck;    // флаг квитирования 1-квитировано 0 - не квитировано
    boolean run;
    static AlarmSound as;
    Context context;



    void setRun(){
        this.run = true;
    }
    void resetRun(){
        this.run = false;
    }

    public AlarmDetector(Context context){
        this.context = context;
        as = new AlarmSound(context); //один раз создаем объект AlarmSound в конструкторе класса AlarmDetector
    }

    @Override
    public void run() {
        while(run){

            if (MainActivity.ls == 1){
                alarmState = true;
            } else {
                alarmState = false;
            }

            if (!alarmState&&alarmAck){     //если аларма нет а скитирование есть то сбрасываем квитирование
                alarmAck = false;
            }

            if (alarmState&&!alarmAck){     //если аларм есть а квитирования нет то орем что есть дури и спим 100мс
                as.play(0);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {                        //иначе просто спим 100мс
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
