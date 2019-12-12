package edisonbro.com.edisonbroautomation.customercarepager;

import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import edisonbro.com.edisonbroautomation.R;

public class SwitchBoard extends AppCompatActivity {


    public String p_name;
    public TextView desc;
    public LinearLayout sw_image,remote_lay,table_01,sw_img_lay;


    ////   simpleTextSwitcher  Start //////
    public TextSwitcher simpleTextSwitcher;
    public Button btnNext;
    // Array of String to Show In TextSwitcher
    String strings[] = {"\"Our Smart Switch is an intelligent switch designed to deliver regular operation that a normal switch would do which includes operating Lights, Fan, Air Conditioner, Geyser etc in a totally wireless system.\"",
            "\"EdisonBro’s Smart Switch has changed the way traditional switches work.\"",
            "\"You can operate yours switches with a single touch when at home or away from home.\"",
            "\"This User Guide is designed to provide instructions about usage/operations of the EdisonBro’s Smart Switch.\""};
    int messageCount = strings.length;
    // to keep current Index of textID array
    int currentIndex = -1;
    ////////////////////////////////
    ////   simpleTextSwitcher End  //////

    public PopupWindow popupWindow;


    ImageView imgremote;

    public int ontik=0;

    TableLayout country_table;

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

        //  String title = getIntent().getStringExtra("title");
        p_name = getIntent().getStringExtra("p_name");
        super.onCreate(savedInstanceState);
        setTitle("Smart Switchboard");

        setContentView(R.layout.cc_switchboard);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tv_title=(TextView)findViewById(R.id.textView10);
        tv_title.setText("Smart Switchboard");
        b_back=(Button) findViewById(R.id.btnback);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       /* ActionBar actionBar = getSupportActionBar();

        int avers= Build.VERSION.SDK_INT;
        if(avers!=24)
            if(avers>19)
                this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/



        //actionBar.setDisplayHomeAsUpEnabled(true);

        desc=(TextView)findViewById(R.id.p_desc);
        desc.setText(p_name+" :");
        country_table=(TableLayout)findViewById(R.id.country_table);

        sw_image=(LinearLayout)findViewById(R.id.sw_image);
        sw_img_lay=(LinearLayout)findViewById(R.id.sw_img_lay);
        remote_lay=(LinearLayout)findViewById(R.id.remote_lay);


        table_01=(LinearLayout)findViewById(R.id.table_01);

        imgremote=(ImageView)findViewById(R.id.remoteimg);


        imgremote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setFocusable(true);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,location[0],location[1]+view.getHeight());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);

                //popupWindow.sh

               /* LayoutInflater layoutInflater =
                        (LayoutInflater)getBaseContext()
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);

                popupWindow = new PopupWindow(
                        popupView, 500, ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.update();*/

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

               // popupWindow.showAsDropDown(view, 90, -70);


               /* LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);

                View popupView = layoutInflater.inflate(R.layout.popup, null);

                 popupWindow = new PopupWindow(
                        popupView,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);



                ImageView imgr=(ImageView)popupView.findViewById(R.id.popupimg);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });


               // Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
               // btnDismiss.setOnClickListener(new Button.OnClickListener(){



                popupWindow.showAsDropDown(popupView);*/

            }
        });



        if(p_name.equalsIgnoreCase("Smart Switchboard Operation"))
        {
            Smart_Switchboard_Operations();





        }
        else if(p_name.equalsIgnoreCase("Smart Switchboard Design"))
        {
            //RelativeLayout rl_country_heading=(RelativeLayout)findViewById(R.id.rl_country_heading);
            //rl_country_heading.setVisibility(View.GONE);

           // LinearLayout ll_country=(LinearLayout)findViewById(R.id.ll_country);
           // ll_country.setVisibility(View.GONE);

           // LinearLayout swbdetails=(LinearLayout)findViewById(R.id.swbdetails);
            //swbdetails.setVisibility(View.VISIBLE);

            LinearLayout switchboard_designlay=(LinearLayout) findViewById(R.id.switchboard_designlay);
            switchboard_designlay.setVisibility(View.VISIBLE);


            TextView switchboard_design=(TextView)findViewById(R.id.switchboard_design);
            switchboard_design.setText(R.string.switchboard_design);
            switchboard_design.setVisibility(View.VISIBLE);

            LinearLayout sw_image=(LinearLayout)findViewById(R.id.sw_image);
            sw_image.setBackgroundResource(R.drawable.sb_switch_detail);

            TextView tv_12=(TextView)findViewById(R.id.tv_12);
            TextView tv_13=(TextView)findViewById(R.id.tv_13);

            tv_12.setText("Name");
            tv_13.setText("Combinations");

           // sw_image.setVisibility(View.GONE);
            Smart_Switchboard_Designs();
            ImageView imageView12=(ImageView)findViewById(R.id.imageView12);
            sw_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                     popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);

                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.sb_switch_detail);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            text_switcher();

        }
        else if(p_name.equalsIgnoreCase("IR Remote Operations"))
        {
            remote_lay.setVisibility(View.VISIBLE);
            sw_img_lay.setVisibility(View.GONE);
            sw_image.setVisibility(View.GONE);
            IR_Remote_Operations();
        }
        else if(p_name.equalsIgnoreCase("Maintenance"))
        {
            //heading.setVisibility(View.GONE);
            RelativeLayout rl_country_heading=(RelativeLayout)findViewById(R.id.rl_country_heading);
            rl_country_heading.setVisibility(View.GONE);


            table_01.setVisibility(View.GONE);


            LinearLayout ll_country=(LinearLayout)findViewById(R.id.ll_country);
            ll_country.setVisibility(View.GONE);

            LinearLayout maintainance=(LinearLayout)findViewById(R.id.maintainance);
            maintainance.setVisibility(View.VISIBLE);


            ImageView imageView8=(ImageView)findViewById(R.id.imageView8);
            imageView8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);

                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.mantainbattery);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });
        }
        else if(p_name.equalsIgnoreCase("Troubleshooting"))
        {
            //heading.setVisibility(View.GONE);

            RelativeLayout rl_country_heading=(RelativeLayout)findViewById(R.id.rl_country_heading);
            rl_country_heading.setVisibility(View.GONE);

            LinearLayout ll_country=(LinearLayout)findViewById(R.id.ll_country);
            ll_country.setVisibility(View.GONE);

            table_01.setVisibility(View.GONE);


            LinearLayout troubleshoot=(LinearLayout)findViewById(R.id.troubleshoot);
            troubleshoot.setVisibility(View.VISIBLE);
        }
        else
        {
            //fillCountryTable();
        }

        if(!p_name.equalsIgnoreCase("Smart Switchboard Design")) {
            new CountDownTimer(60000, 1000) {

                @Override
                public void onTick(long l) {
                    int min = (int) (l / 1000);
                    if (min % 3 == 0) {
                        if (ontik == 0) {

                            sw_image.setBackgroundResource(R.drawable.sb_black2);
                            ontik = 1;
                        } else if (ontik == 1) {
                            sw_image.setBackgroundResource(R.drawable.sb_white2);
                            ontik = 0;
                        }

                    }
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }

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
           // t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);
           // t1.setPadding(20 * dip, 0, 0, 0);
            row.addView(t1);
            row.addView(t2);

            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

    void Smart_Switchboard_Operations()
    {
        TableRow row;
        TextView t1, t2;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.swb_op_controls.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.swb_op_controls[current]);
            t2.setText(tabledata.swb_op_operation[current]);

           // t1.setTypeface(null, 1);
          //  t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);
            // t1.setPadding(20 * dip, 0, 0, 0);
            row.addView(t1);
            row.addView(t2);

            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

    void Smart_Switchboard_Designs()
    {
        TableRow row;
        TextView t1, t2;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.Swd_design_name.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.Swd_design_name[current]);
            t2.setText(tabledata.Swd_design_combinations[current]);

           // t1.setTypeface(null, 1);
           // t2.setTypeface(null, 1);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));

            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
             t2.setPadding(20 * dip, 0, 5, 0);
            row.addView(t1);
            row.addView(t2);

            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }
    void IR_Remote_Operations()
    {
        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.irremote_op_controls.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

          //  m1=new ImageView(this);



            t1.setText(tabledata.irremote_op_controls[current]);
            t2.setText(tabledata.irremote_op_operation[current]);
           // t1.setBackgroundResource(R.drawable.all_off);
            //t1.setHeight(50);

           // t1.setTypeface(null, 4);
           // t2.setTypeface(null, 4);

            t1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));
            t2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.table_desc_text));



            t1.setWidth(75 * dip);
            t2.setWidth(150 * dip);
            t2.setPadding(20 * dip, 0, 5, 0);

           // t1.setGravity(Gravity.CENTER);
            // t1.setPadding(20 * dip, 0, 0, 0);

           /* if(current==0 || current==1) {
                if(current==0)
                     m1.setImageResource(R.drawable.all_off);
                else
                    m1.setImageResource(R.drawable.all_on);

               // m1.setMaxWidth(50 * dip);
               // m1.setMinimumWidth(50 * dip);
                row.addView(m1);
            }
            else*/

           if(tabledata.irremote_op_controls[current].equalsIgnoreCase("Buzzer"))
           {
               m1=new ImageView(this);
               m1.setLayoutParams(new TableRow.LayoutParams(70,70));
               m1.setImageResource(R.drawable.mute_icon);
               m1.requestLayout();

               row.addView(m1);
           }
           else
                row.addView(t1);
            row.addView(t2);

            country_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }



    public void text_switcher()
    {



        // get The references if Button and TextSwitcher
        btnNext = (Button) findViewById(R.id.next);
        simpleTextSwitcher = (TextSwitcher) findViewById(R.id.simpleTextSwitcher);
        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        simpleTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create a TextView
                TextView t = new TextView(getBaseContext());
                // set the gravity of text to top and center horizontal
                t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                // set displayed text size
                t.setTextSize(16);//R.dimen.et_txtsize);
                t.setTextColor(getResources().getColor(R.color.black));



                return t;
            }
        });

        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);

        // set the animation type to TextSwitcher
        simpleTextSwitcher.setInAnimation(in);
        simpleTextSwitcher.setOutAnimation(out);

        //text appear on start
        simpleTextSwitcher.setCurrentText(strings[0]);
        // ClickListener for NEXT button
        // When clicked on Button TextSwitcher will switch between labels
        // The current label will go out and next label will come in with specified animation
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                currentIndex++;
                // If index reaches maximum then reset it
                if (currentIndex == messageCount)
                    currentIndex = 0;
                simpleTextSwitcher.setText(strings[currentIndex]); // set Text in TextSwitcher
            }
        });

    }
}
