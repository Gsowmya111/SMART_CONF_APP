package edisonbro.com.edisonbroautomation.customercarepager;

import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edisonbro.com.edisonbroautomation.R;

public class PIR_app_operation extends AppCompatActivity {


    public View popupview;

    TableLayout pir_table,pir_table2;
    public String p_name;
    public TextView desc,description,description2,description3,description4;
    public LinearLayout sw_image,remote_lay,secondlay,thirdlay;
    public RelativeLayout heading;
    public ProgressDialog progress;
    public PopupWindow popupWindow;

    public Integer[] imgicon={R.drawable.all_on,R.drawable.all_off,R.drawable.radio01,R.drawable.radio,R.drawable.direc_pir_sen_off,R.drawable.direc_pir_sen_on,R.drawable.motion,R.drawable.pir_sens_on,R.drawable.light,R.drawable.lightsens_on,R.drawable.dots,R.drawable.pirzero,R.drawable.settings_icon,R.drawable.timer_settings,R.drawable.group_pir_select,R.drawable.individual_pir_select};
    public Integer[] imgicon2={R.drawable.edit_inlist,R.drawable.delete_inlist,R.drawable.select_room,R.drawable.deleteall};
    public Integer[] moodicons={R.drawable.timer_001,R.drawable.bulb01,R.drawable.fan01,R.drawable.moodspinner,R.drawable.moodcancel,R.drawable.moodsave};

    public Integer[] performimg={R.drawable.all_on,R.drawable.all_off,R.drawable.bulb02,R.drawable.bulb01,R.drawable.fan02,R.drawable.fan01,R.drawable.voltbulb01,R.drawable.voltbulb02,R.drawable.performdots,R.drawable.performdown,R.drawable.performup,R.drawable.settings_icon,R.drawable.timer_settings};

    public ImageView perform_pop_img1,perform_pop_img2,pir_select_img,pir_change_setting_img,pir_set_timer_img1,pir_set_timer_img2,pir_set_timer_img3;

    public int ontik=0;

    TableLayout country_table,table2,table3;

    // Array of strings storing country names
    String[] countries = new String[]{
            "Switchboard",
    };

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
            R.drawable.sw,
    };
    Button b_back;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //  String title = getIntent().getStringExtra("title");
        p_name = getIntent().getStringExtra("p_name");
        super.onCreate(savedInstanceState);
        setTitle("EB Mobile App Operation");

        popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
        popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        if(p_name.equalsIgnoreCase("Select Room & PIR"))
        {
            setContentView(R.layout.cc_pir_app_selectpir);

            pir_select_img=(ImageView)findViewById(R.id.pir_select_img);
            pir_select_popup_img();

        }
        else if(p_name.equalsIgnoreCase("Perform Operations"))
        {
            setContentView(R.layout.cc_pir_app_perform);

            perform_pop_img1=(ImageView)findViewById(R.id.performimg1);
            perform_pop_img2=(ImageView)findViewById(R.id.performimg2);

            pir_table=(TableLayout)findViewById(R.id.pir_table);
            pir_perform();
            pir_perform_popup_img();
        }
        else if(p_name.equalsIgnoreCase("Change Settings"))
        {
            setContentView(R.layout.cc_pir_change_setting);
            pir_table=(TableLayout)findViewById(R.id.pir_table);
            pir_chng_set();

            pir_change_setting_img=(ImageView)findViewById(R.id.pir_change_setting_img);
            pir_changesetting_popup_img();



        } else if(p_name.equalsIgnoreCase("Set Timer"))
        {
            setContentView(R.layout.cc_pir_set_timer);
            pir_table=(TableLayout)findViewById(R.id.pir_table);
            pir_table2=(TableLayout)findViewById(R.id.pir_table2);
            pir_set_timer();

            pir_set_timer_img1=(ImageView)findViewById(R.id.pir_set_timer_img1);
            pir_set_timer_img2=(ImageView)findViewById(R.id.pir_set_timer_img2);
            pir_set_timer_img3=(ImageView)findViewById(R.id.pir_set_timer_img3);

            pir_set_time_popup_img();
        }
        else
        {
            setContentView(R.layout.cc_switchboard_app_op);

        }

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        tv_title=(TextView)findViewById(R.id.textView10);
        //tv_title.setText(""+p_name);
        tv_title.setText("Pir App Operation");

        b_back=(Button) findViewById(R.id.btnback);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


       /* progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait....");
        progress.show();

        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
                // statep=1;
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 500);


        desc=(TextView)findViewById(R.id.p_desc);
        desc.setText(p_name+" :");
        country_table=(TableLayout)findViewById(R.id.country_table);

        table2=(TableLayout)findViewById(R.id.table2);
        table3=(TableLayout)findViewById(R.id.table3);

        description=(TextView)findViewById(R.id.description);
        description2=(TextView)findViewById(R.id.description2);
        description3=(TextView)findViewById(R.id.description3);
        description4=(TextView)findViewById(R.id.description4);

        sw_image=(LinearLayout)findViewById(R.id.sw_image);
        remote_lay=(LinearLayout)findViewById(R.id.remote_lay);

        imgremote=(ImageView)findViewById(R.id.remoteimg);

        heading=(RelativeLayout)findViewById(R.id.heading) ;


        imgremote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                if(p_name.equalsIgnoreCase("Change icon")) {
                    imgr.setImageResource(R.drawable.changeicon);
                }
                else if(p_name.equalsIgnoreCase("Set Timer")) {
                    imgr.setImageResource(R.drawable.timer);
                }
                else if(p_name.equalsIgnoreCase("Set Moods")) {
                    imgr.setImageResource(R.drawable.moodset);
                }
                else if(p_name.equalsIgnoreCase("Perform Operations")) {
                    imgr.setImageResource(R.drawable.mood8);
                }
                else if(p_name.equalsIgnoreCase("Select Room & Switchboard"))
                {
                    imgr.setImageResource(R.drawable.sb_panel);
                }
                else
                    imgr.setImageResource(R.drawable.app_setting);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

            }
        });

        if(p_name.equalsIgnoreCase("Change icon"))
        {
            imgremote.setImageResource(R.drawable.changeicon);
            heading.setVisibility(View.GONE);
            description.setText(R.string.change_icon);
        }
        else if(p_name.equalsIgnoreCase("Select Room & Switchboard"))
        {
            imgremote.setImageResource(R.drawable.sb_panel);


            description.setText(R.string.select_room);
            heading.setVisibility(View.GONE);
            //fillCountryTable();
        }
        else if(p_name.equalsIgnoreCase("Perform Operations"))
        {
            imgremote.setImageResource(R.drawable.mood8);
            description.setText(R.string.perform_op);

            select_room();
        }
        else if(p_name.equalsIgnoreCase("Set Timer"))
        {
            secondlay=(LinearLayout) findViewById(R.id.secondlay);
            secondlay.setVisibility(View.VISIBLE);

            imgremote.setImageResource(R.drawable.timer);
            description.setText(R.string.set_time);

            secondimg=(ImageView)findViewById(R.id.secondimg);
            thirdimg=(ImageView)findViewById(R.id.thirdimg);

            secondimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.timer_op);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            thirdimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    //popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.timerlist);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });




            set_timer();
        }
        else if(p_name.equalsIgnoreCase("Set Moods"))
        {
            description.setText(R.string.moodone);

            ImageView third2=(ImageView)findViewById(R.id.third2);
            ImageView imageView4=(ImageView)findViewById(R.id.imageView4);
            ImageView imageView3=(ImageView)findViewById(R.id.imageView3);
            ImageView imageView5=(ImageView)findViewById(R.id.imageView5);
            ImageView imageView6=(ImageView)findViewById(R.id.imageView6);
            ImageView imageView7=(ImageView)findViewById(R.id.imageView7);
            ImageView imageView9=(ImageView)findViewById(R.id.imageView9);
            ImageView imageView10=(ImageView)findViewById(R.id.imageView10);
            ImageView imageView11=(ImageView)findViewById(R.id.imageView11);

            third2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.mood2);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.mood4);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.mood3);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });


            imageView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.moodswb1);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.moodswb2);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.moodswb31);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.moodswb3);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.mood8);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            imageView11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                    // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.moodswb8);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });
            thirdlay=(LinearLayout) findViewById(R.id.thirdlay);
            thirdlay.setVisibility(View.VISIBLE);

           // secondlay=(LinearLayout) findViewById(R.id.secondlay);
            //secondlay.setVisibility(View.VISIBLE);

            imgremote.setImageResource(R.drawable.moodset);

           *//* description.setText(R.string.moodone);
            description2.setText(R.string.moodtwo);
            description3.setText(R.string.moodthree);
            description4.setText(R.string.moodfour);
*//*
            secondimg=(ImageView)findViewById(R.id.secondimg);
            thirdimg=(ImageView)findViewById(R.id.thirdimg);

            secondimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.timer_op);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            thirdimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);
                   // popupWindow.showAtLocation(view,Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());


                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.timerlist);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            mood_set();

        }
        *//*else if(p_name.equalsIgnoreCase("Maintenance"))
        {
            heading.setVisibility(View.GONE);
            LinearLayout maintainance=(LinearLayout)findViewById(R.id.maintainance);
            maintainance.setVisibility(View.VISIBLE);
        }
        else if(p_name.equalsIgnoreCase("Troubleshooting"))
        {
            heading.setVisibility(View.GONE);
            LinearLayout troubleshoot=(LinearLayout)findViewById(R.id.troubleshoot);
            troubleshoot.setVisibility(View.VISIBLE);
        }*//*
        else
        {
            fillCountryTable();
        }



*/



/*
       new CountDownTimer(60000,1000)
       {

           @Override
           public void onTick(long l) {
               int min= (int) (l/1000);
               if(min%3==0)
               {
                   if(ontik==0)
                   {
                       sw_image.setBackgroundResource(R.drawable.sb_black2);
                       ontik=1;
                   }
                   else if(ontik==1)
                   {
                       sw_image.setBackgroundResource(R.drawable.sb_white2);
                       ontik=0;
                   }

               }
           }

           @Override
           public void onFinish() {

           }
       }.start();*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                /*Intent intent = new Intent(this, CustomTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void fillCountryTable() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.abbreviations.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.abbreviations[current]);
            t2.setText(tabledata.countries[current]);

           // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));


            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);
           // t1.setPadding(20 * dip, 0, 0, 0);


            if(tabledata.abbreviations[current].equalsIgnoreCase("1"))
            {
                m1=new ImageView(this);
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
                m1.setImageResource(R.drawable.dropdown);
                row.addView(m1);
                row.addView(t2);
            }
            else if(tabledata.abbreviations[current].equalsIgnoreCase("SET"))
            {
                m1=new ImageView(this);
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
                m1.setImageResource(R.drawable.set);
                row.addView(m1);
                row.addView(t2);
            }
            else if(tabledata.abbreviations[current].equalsIgnoreCase("RESET IR"))
            {
                m1=new ImageView(this);
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
                m1.setImageResource(R.drawable.reset_ir);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                row.addView(t1);
                row.addView(t2);
            }


            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

    void select_room() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        int size=tabledata.perform_operation_img.length;
        size--;
        for (int current = 0; current < tabledata.perform_operation_img.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.perform_operation_img[current]);
            t2.setText(tabledata.perform_operation[current]);

            m1=new ImageView(this);
            m1.setLayoutParams(new TableRow.LayoutParams(70,70));



            if(current!=size) {
                m1.setImageResource(performimg[current]);
                //t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                t1.setText("");
              //  t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(t1);
                row.addView(t2);
            }




           /* m1.setImageResource(performimg[current]);

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            t1.setTextSize(14);
            t2.setTextSize(14);


            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 0, 0);

            // t1.setPadding(20 * dip, 0, 0, 0);
            row.addView(m1);
            row.addView(t2);
*/
            country_table.addView(row,
           new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));


        }
    }

    void set_timer() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.set_timerone.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.set_timerone[current]);
            t2.setText(tabledata.set_timertwo[current]);

            m1=new ImageView(this);
            m1.setLayoutParams(new TableRow.LayoutParams(70,70));

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));


            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);
            //t1.setPadding(20 * dip, 0, 0, 0);


            if(current==0) {
                m1.setImageResource(R.drawable.set_opetn);
                //m1.getLayoutParams().width=20;
                //m1.getLayoutParams().height=20;
                m1.requestLayout();
                row.addView(m1);
            }
            else if(current==1) {
                m1.setImageResource(R.drawable.timer_001);
                //m1.getLayoutParams().width=20;
                //m1.getLayoutParams().height=20;
                m1.requestLayout();

                row.addView(m1);
            }
            else
                row.addView(t1);

            row.addView(t2);

            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
        int size=tabledata.set_timerthree.length;
        size--;
        for (int current = 0; current < tabledata.set_timerthree.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.set_timerthree[current]);
            t2.setText(tabledata.set_timerfour[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


            if(current!=size) {
                m1.setImageResource(imgicon2[current]);
               // t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                t1.setText("");
               // t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(t1);
                row.addView(t2);
            }

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            //t1.setTextSize(15);


            table2.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

void mood_set() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());


    int size=tabledata.mood_setone.length;
    size--;
        for (int current = 0; current < tabledata.mood_setone.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.mood_setone[current]);
            t2.setText(tabledata.mood_settwo[current]);

            m1=new ImageView(this);
            if(current==4 || current == 5|| current == 0)
                 m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));

            /* m1.setImageResource(moodicons[current]);

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            t1.setTextSize(14);
            t2.setTextSize(14);

           *//* t1.setWidth(50 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(8* dip, 0, 0, 0);
*//*

            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 0, 0);
            //t1.setPadding(20 * dip, 0, 0, 0);


            row.addView(m1);
            row.addView(t2);
*/
            if(current!=size) {
                m1.setImageResource(moodicons[current]);
                //t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                t1.setText("");
               // t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(t1);
                row.addView(t2);
            }


            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }

    int size1=tabledata.mood_setthree.length;
    size1--;
        for (int current = 0; current < tabledata.mood_setthree.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.mood_setthree[current]);
            t2.setText(tabledata.mood_setfour[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


           /* m1.setImageResource(imgicon2[current]);

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            //t1.setTextSize(15);
            t2.setTextSize(14);

           // t1.setWidth(50 * dip);

            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 0, 0);
            // t1.setPadding(20 * dip, 0, 0, 0);
            row.addView(m1);
            row.addView(t2);
*/

            if(current!=size1) {
                m1.setImageResource(imgicon2[current]);
             //   t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));


                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                t1.setText("");
               // t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(t1);
                row.addView(t2);
            }

            table3.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

    void pir_perform() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        int size=tabledata.PIR_user_guid_control.length;
        size--;
        for (int current = 0; current < tabledata.PIR_user_guid_control.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.PIR_user_guid_control[current]);
            t2.setText(tabledata.PIR_user_guid[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


            if(current!=size) {
                m1.setImageResource(imgicon[current]);
                //t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 0, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                t1.setText("");
                //  t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 0, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(t1);
                row.addView(t2);
            }

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            //t1.setTextSize(15);


            pir_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }
    void pir_chng_set() {
        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.PIR_chg_set_control.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.PIR_chg_set_control[current]);
            t2.setText(tabledata.PIR_chg_set_desc[current]);

            // t1.setTypeface(null, 1);
            //  t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);
            // t1.setPadding(20 * dip, 0, 0, 0);

            if(tabledata.PIR_chg_set_control[current].equalsIgnoreCase("SET"))
            {
                m1=new ImageView(this);
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
                m1.setImageResource(R.mipmap.set_icon);
                row.addView(m1);
                row.addView(t2);
            }else {
                row.addView(t1);
                row.addView(t2);
            }

            pir_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }


    void pir_set_timer() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.PIR_timer_control.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.PIR_timer_control[current]);
            t2.setText(tabledata.PIR_timer_desc[current]);

            m1=new ImageView(this);
            m1.setLayoutParams(new TableRow.LayoutParams(70,70));

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));


            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);
            //t1.setPadding(20 * dip, 0, 0, 0);


            if(current==0) {
                m1.setImageResource(R.drawable.set_opetn);
                //m1.getLayoutParams().width=20;
                //m1.getLayoutParams().height=20;
                m1.requestLayout();
                row.addView(m1);
            }
            else if(current==1) {
                m1.setImageResource(R.drawable.timer_001);
                //m1.getLayoutParams().width=20;
                //m1.getLayoutParams().height=20;
                m1.requestLayout();

                row.addView(m1);
            }
            else
                row.addView(t1);

            row.addView(t2);

            pir_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
        int size=tabledata.set_timerthree.length;
        size--;
        for (int current = 0; current < tabledata.set_timerthree.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.set_timerthree[current]);
            t2.setText(tabledata.set_timerfour[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


            if(current!=size) {
                m1.setImageResource(imgicon2[current]);
                // t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(m1);
                row.addView(t2);
            }
            else
            {
                t1.setText("");
                // t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
                t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

                // t1.setWidth(50 * dip);

                t1.setWidth(75 * dip);
                t2.setWidth(150 * dip);
                t2.setPadding(20 * dip, 0, 5, 0);
                // t1.setPadding(20 * dip, 0, 0, 0);
                row.addView(t1);
                row.addView(t2);
            }

            // t1.setTypeface(null, 1);
            //t2.setTypeface(null, 1);

            //t1.setTextSize(15);


            pir_table2.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }


    /// popup images ///

    // PIR Perform Operations //
    public void pir_perform_popup_img()
    {
        perform_pop_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   /* View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);*/

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pir_group_user);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        perform_pop_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                   /* View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);*/

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pir_individual_user);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }


    // PIR Select  //
    public void pir_select_popup_img()
    {
        pir_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pirselect1);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }

    // PIR change setting   //
    public void pir_changesetting_popup_img()
    {
        pir_change_setting_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pir_setting);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }



    // PIR Set Timer   //
    public void pir_set_time_popup_img()
    {
        pir_set_timer_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pir_settings);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });pir_set_timer_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pir_timer_operation);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });pir_set_timer_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.pir_time_edit);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }
}
