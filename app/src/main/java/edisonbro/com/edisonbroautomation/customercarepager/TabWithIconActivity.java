package edisonbro.com.edisonbroautomation.customercarepager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;


import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.custmercarefrag.FAQ;
import edisonbro.com.edisonbroautomation.custmercarefrag.HELP;
import edisonbro.com.edisonbroautomation.custmercarefrag.TicketFragment;

public class TabWithIconActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    ViewPagerAdapter adapter;

    //Fragments

    HELP help;
    FAQ faq;
    TicketFragment ticket;
    Button b_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cc_activity_tab_with_icon);

        b_back=(Button) findViewById(R.id.btnback);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



       // LayoutInflater inflater = (LayoutInflater).getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
       // View popupview = inflater.from(TabWithIconActivity.this).inflate(R.layout.popup, null);

       /* View popupview=getLayoutInflater().inflate(R.layout.popup,null);
        final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);


        popupWindow.showAtLocation(popupview,Gravity.CENTER, 0, 0);
        ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
        imgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });



        new CountDownTimer(6000, 1000) {

            @Override
            public void onTick(long l) {
                int min = (int) (l / 1000);
                if (min == 5) {

                    popupWindow.dismiss();
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
*/
        //loadingPopup();
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);



        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#064a93"));
        tabLayout.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#FF0000"));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.cc_menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            /*case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;*/
           /* case R.id.action_without_icon:
                Intent withouticon=new Intent(this,TabWOIconActivity.class);
                startActivity(withouticon);
                finish();
                return true;*/
           /* case R.id.action_customtab:
                Intent custom_tab=new Intent(this,CustomTabActivity.class);
                startActivity(custom_tab);
                finish();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        faq=new FAQ();
        help=new HELP();
        ticket=new TicketFragment();
        adapter.addFragment(faq,"FAQ");
        adapter.addFragment(help,"HELP");
        adapter.addFragment(ticket,"TICKETS");
        viewPager.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        backpressed();

/*

          // set dialog message
        final AlertDialog alertDialogBuilder1=new AlertDialog.Builder(this)
                 //.setView(promptsView)
                .setCancelable(false)
                .setMessage("Do you want to close")
                .setPositiveButton("OK",null)
                .setNegativeButton("Cancel",null)
                .create();

      alertDialogBuilder1.setOnShowListener(new DialogInterface.OnShowListener()
        {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button d=alertDialogBuilder1.getButton(AlertDialog.BUTTON_POSITIVE);
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Button can=alertDialogBuilder1.getButton(AlertDialog.BUTTON_NEGATIVE);
                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialogBuilder1.dismiss();
                    }
                });


            }
        });

        alertDialogBuilder1.show();
      //  super.onBackPressed();*/
    }


    public void backpressed(){
        Intent withicon=new Intent(this,Main_Navigation_Activity.class);
        startActivity(withicon);
        finish();
    }
    private void loadingPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("EB Customer");
        // this is set the view from XML inside AlertDialog
        alert.setView(R.layout.cc_loading_dialog);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);

        LayoutInflater inflater = this.getLayoutInflater();
        final View layout = inflater.inflate(R.layout.cc_loading_dialog, null);
        LinearLayout start_pop=(LinearLayout)layout.findViewById(R.id.start_pop);



        final AlertDialog dialog = alert.create();
        dialog.show();
        start_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long l) {
             /*   int min = (int) (l / 1000);
                if (min == 5) {

                    dialog.dismiss();
                }*/
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        }.start();
    }
}
