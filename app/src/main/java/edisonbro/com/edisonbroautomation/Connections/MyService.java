package edisonbro.com.edisonbroautomation.Connections;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;

public class MyService extends Service {


    public static String temp = "";
    Context context;

    public MyService(Context applicationContext) {
        super();
        this.context = applicationContext;
    }
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
      try{
          startTimer();
      }catch (Exception e){
         e.printStackTrace();
      }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try{
           /* Log.i("EXIT", "ondestroy!");
            Intent broadcastIntent = new Intent(this, SensorRestarterBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
            stoptimertask();*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        try {
            //set a new Timer
            timer = new Timer();

            //initialize the TimerTask's job
           // initializeTimerTask();

            //schedule the timer, to wake up every 1 second
            timer.schedule(timerTask, 1000, 1000); //
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        try{
            /*timerTask = new TimerTask() {
                public void run() {
                    //  Log.i("in timer", "in timer ++++  "+ (counter++));

                    checkgatewayconnection();
                }
            };*/
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * not needed
     */
    public void stoptimertask() {
        try{
            //stop the timer, if it's not already null
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void checkgatewayconnection(){
        try {
            if(Tcp_con.isClientStarted){
                Log.i("Gateway status", "connected"+ StaticVariabes_div.loggeduser_type);
                //this.sendBroadcast(new Intent().setAction("bcNewMessage"));
            }else {
                Log.i("Gateway status", "Dis-connected"+StaticVariabes_div.loggeduser_type);
                this.sendBroadcast(new Intent().setAction("bcNewMessage"));
                if(StaticVariabes_div.loggeduser_type.length()<=0){
                    Log.i("Gateway status", "No user found");
                }
                else{

                    if(context != null){
                        Log.i("Gateway status", "NULL Context");
                    }else {
                        Tcp_con.stacontxt = context;
                        Tcp_con.serverdetailsfetch(context,StaticVariabes_div.housename);
                        Tcp_con.registerReceivers(context);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
