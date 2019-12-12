package edisonbro.com.edisonbroautomation.customercarepager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;


public class Ebc_SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc_loading_dialog);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        final Handler handler=new Handler(); //new Handler()
        handler.postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Ebc_SplashScreen.this, CustomTabActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

        Button skip=(Button)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);
                Intent i = new Intent(Ebc_SplashScreen.this, CustomTabActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        });
        LinearLayout start_pop=(LinearLayout)findViewById(R.id.start_pop);
        start_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacksAndMessages(null);
                Intent i = new Intent(Ebc_SplashScreen.this, CustomTabActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        });
    }

    public void onBackPressed() {
        Intent intnt=new Intent(Ebc_SplashScreen.this, Main_Navigation_Activity.class);
        startActivity(intnt);
        finish();
    }
}