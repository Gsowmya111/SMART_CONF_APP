package edisonbro.com.edisonbroautomation.customercarepager;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edisonbro.com.edisonbroautomation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PIR_info extends AppCompatActivity {

    public String p_name;
    public LinearLayout layout1,layout2,layout3,layout4;
    TableLayout piruserguid;
    public Integer[] imgicon2={R.drawable.pir_priority_set,R.drawable.pir_priority_unset,R.drawable.settings_icon,R.drawable.timer_settings,R.drawable.mood0,R.drawable.pir_motion,R.drawable.pir_light,R.drawable.all_on,R.drawable.all_off,R.drawable.group_pir_select,R.drawable.individual_pir_select};

    public PopupWindow popupWindow;
    public ImageView troubleshootimg1;
    public View popupview;
    Button b_back;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //  String title = getIntent().getStringExtra("title");
        p_name = getIntent().getStringExtra("p_name");
        super.onCreate(savedInstanceState);
        setTitle("EB Intelligent "+p_name);
        if(p_name.equalsIgnoreCase("Troubleshooting"))
        {
            setContentView(R.layout.cc_pir_troubleshoot);
            troubleshootimg1=(ImageView)findViewById(R.id.troubleshootimg1);
            popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
            popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        else {
            setContentView(R.layout.cc_pir_details);
            popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
            popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }

      // android.app.ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
       // this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout4 = (LinearLayout) findViewById(R.id.layout4);

        tv_title=(TextView)findViewById(R.id.textView10);
        if(p_name.equalsIgnoreCase("Applications of EB PIR Sensor")) {
            tv_title.setText("EB PIR Sensor");
        }else{
            tv_title.setText("" + p_name);
        }
        b_back=(Button) findViewById(R.id.btnback);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(p_name.equalsIgnoreCase("Applications of EB PIR Sensor"))
        {
            layout1.setVisibility(View.VISIBLE);
            ImageView pir_main=(ImageView)findViewById(R.id.pir_main);


            pir_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   /* View popupview=getLayoutInflater().inflate(R.layout.popup,null);
                    popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);*/

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.pir_main);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });
        }


        //PIR Sensor Troubleshooting 1st image popup

        if(p_name.equalsIgnoreCase("Troubleshooting"))
        {

            troubleshootimg1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.pir_main);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });
        }





        else if(p_name.equalsIgnoreCase("PIR Sensor User Guide"))
        {
            ImageView pir_main2=(ImageView)findViewById(R.id.pir_main2);
            pir_main2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.pir_main);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });


            ImageView user_img=(ImageView)findViewById(R.id.user_img);
            user_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.pir_user);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });


            layout2.setVisibility(View.VISIBLE);
            piruserguid=(TableLayout)findViewById(R.id.piruserguid);
            pir_userguid();
        }else if(p_name.equalsIgnoreCase("PIR Sensor Group User Guide"))
        {
            ImageView pir_main3=(ImageView)findViewById(R.id.pir_main3);
            pir_main3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.pir_main);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });


            ImageView group_img=(ImageView)findViewById(R.id.group_img);
            group_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

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

            layout3.setVisibility(View.VISIBLE);
        }else if(p_name.equalsIgnoreCase("PIR Sensor Individual User Guide"))
        {
            ImageView pir_main4=(ImageView)findViewById(R.id.pir_main4);
            pir_main4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

                    int location[]=new int[2];
                    view.getLocationOnScreen(location);
                    popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                    ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                    imgr.setImageResource(R.drawable.pir_main);

                    imgr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            ImageView individual_img=(ImageView)findViewById(R.id.individual_img);
            individual_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    View popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
                    final PopupWindow popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    popupWindow.setFocusable(true);

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


            layout4.setVisibility(View.VISIBLE);
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

   /* @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }*/

    void pir_userguid() {

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
                m1.setImageResource(imgicon2[current]);
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


            piruserguid.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

}
