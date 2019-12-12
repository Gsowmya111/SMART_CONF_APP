package edisonbro.com.edisonbroautomation.customercarepager;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edisonbro.com.edisonbroautomation.R;


public class RGB_info extends AppCompatActivity {
    TableLayout pir_table,pir_table2;
    public String p_name;
    public LinearLayout layout1,layout2,layout3,layout4;
    TableLayout piruserguid;
    public Integer[] imgicon2={R.drawable.pir_priority_set,R.drawable.pir_priority_unset,R.drawable.settings_icon,R.drawable.timer_settings,R.drawable.mood0,R.drawable.pir_motion,R.drawable.pir_light,R.drawable.all_on,R.drawable.all_off,R.drawable.group_pir_select,R.drawable.individual_pir_select};
    public Integer[] imgicon={R.drawable.all_on,R.drawable.all_off,R.drawable.allcolor,R.drawable.colorbar,R.drawable.progress_bar,R.drawable.flash,R.drawable.strobe,R.drawable.fade,R.drawable.smooth,R.drawable.up_arrow01,R.drawable.ebca01,R.drawable.down_arrow01,R.drawable.colorselected,R.drawable.group_pir_select,R.drawable.individual_pir_select};

    public Integer[] imgicon_mood={R.drawable.timer_001,R.drawable.toggle,R.drawable.allcolor,R.drawable.colorbar,R.drawable.progress_bar,R.drawable.flash,R.drawable.strobe,R.drawable.fade,R.drawable.smooth,R.drawable.up_arrow01,R.drawable.ebca01,R.drawable.down_arrow01,R.drawable.colorselected,R.drawable.moodcancel,R.drawable.moodsave};

    public Integer[] imgtimelist={R.drawable.edit_inlist,R.drawable.delete_inlist,R.drawable.select_room,R.drawable.deleteall};

    public Integer[] rgb_gp_img={R.drawable.all_on,R.drawable.all_off,R.drawable.allcolor};

    public Integer[] imgicon_mood_list={R.drawable.edit_inlist,R.drawable.delete_inlist,R.drawable.select_room,R.drawable.deleteall};


    TableLayout rgb_table,rgb_table2;


    public View popupview;
    public PopupWindow popupWindow;
    public ImageView rgb_controller_img,rgb_mood_set_img1,rgb_mood_set_img2,rgb_perform_img1,rgb_perform_img2,rgb_perform_img3,rgb_select_img,rgb_set_timer_img1,rgb_set_timer_img2,rgb_set_timer_img3,rgb_change_set_img;
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


        popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
        popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        if(p_name.equalsIgnoreCase("EdisonBro Wireless Smart RGB Controller")) {

            setContentView(R.layout.cc_rgb_controller);

            rgb_controller_img=(ImageView)findViewById(R.id.rgb_controller_img);
            rgb_controller_popup_img();

        }
        else if(p_name.equalsIgnoreCase("Select Room & RGB")) {
            setContentView(R.layout.cc_rgb_select);

            rgb_select_img=(ImageView)findViewById(R.id.rgb_select_img);
            rgb_select_popup_img();


        }else if(p_name.equalsIgnoreCase("Perform Operations")) {
            setContentView(R.layout.cc_rgb_perform_op);
            rgb_table=(TableLayout)findViewById(R.id.rgb_table);
            rgb_table2=(TableLayout)findViewById(R.id.rgb_table2);
            pir_perform();
            rgb_perform2();

            rgb_perform_img1=(ImageView)findViewById(R.id.rgb_perform_img1);
            rgb_perform_img2=(ImageView)findViewById(R.id.rgb_perform_img2);
            rgb_perform_img3=(ImageView)findViewById(R.id.rgb_perform_img3);
            rgb_perform_popup_img();
        }
        else if(p_name.equalsIgnoreCase("Change Settings")) {
            setContentView(R.layout.cc_change_setting);
            rgb_table=(TableLayout)findViewById(R.id.rgb_table);
           // rgb_table2=(TableLayout)findViewById(R.id.rgb_table2);
           rgb_chng_set();

            rgb_change_set_img=(ImageView)findViewById(R.id.rgb_change_set_img);
            rgb_change_set_popup_img();

        }
        else if(p_name.equalsIgnoreCase("Set Timer")) {
            setContentView(R.layout.cc_rgb_set_timer);
            pir_table=(TableLayout)findViewById(R.id.pir_table);
            pir_table2=(TableLayout)findViewById(R.id.pir_table2);
            rgb_set_timer();

            rgb_set_timer_img1=(ImageView)findViewById(R.id.rgb_set_timer_img1);
            rgb_set_timer_img2=(ImageView)findViewById(R.id.rgb_set_timer_img2);
            rgb_set_timer_img3=(ImageView)findViewById(R.id.rgb_set_timer_img3);
            rgb_set_timer_popup_img();

        }
        else if(p_name.equalsIgnoreCase("Set Moods")) {
            setContentView(R.layout.cc_rgb_mood_set);
           rgb_table=(TableLayout)findViewById(R.id.rgb_table);
           rgb_table2=(TableLayout)findViewById(R.id.rgb_table2);
            rgb_moods();
            rgb_moods_list();
           // pir_perform();
          //  rgb_perform2();


            rgb_mood_set_img1=(ImageView)findViewById(R.id.rgb_mood_set_img1);
            rgb_mood_set_img2=(ImageView)findViewById(R.id.rgb_mood_set_img2);
            rgb_mood_set_popup_img();


        }

        tv_title=(TextView)findViewById(R.id.textView10);
        if(p_name.equalsIgnoreCase("EdisonBro Wireless Smart RGB Controller")) {
            tv_title.setText("EB Smart RGB");
        }else if(p_name.equalsIgnoreCase("Select Room & RGB")) {
            tv_title.setText("Select RGB");
        }else{
            tv_title.setText("" + p_name);
        }
       // ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        b_back=(Button) findViewById(R.id.btnback);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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


    void pir_perform() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        int size=tabledata.RGB_user_guid_control.length;
        size--;
        for (int current = 0; current < tabledata.RGB_user_guid_control.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.RGB_user_guid_control[current]);
            t2.setText(tabledata.RGB_user_guid[current]);

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


            rgb_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }

    void rgb_perform2() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        int size=tabledata.RGB_group.length;
        size--;
        for (int current = 0; current < tabledata.RGB_group.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.RGB_group[current]);
            t2.setText(tabledata.RGB_group_desc[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


            if(current!=size) {
                m1.setImageResource(rgb_gp_img[current]);
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


            rgb_table2.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }


    void rgb_set_timer() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.RGB_timer_control.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.RGB_timer_control[current]);
            t2.setText(tabledata.RGB_timer_desc[current]);

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
                m1.setImageResource(imgtimelist[current]);
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

    void rgb_chng_set() {
        TableRow row;
        TextView t1, t2;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        for (int current = 0; current < tabledata.RGB_chg_set_control.length; current++) {
            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.RGB_chg_set_control[current]);
            t2.setText(tabledata.RGB_chg_set_desc[current]);

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

            rgb_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }



    void rgb_moods() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        int size=tabledata.RGB_user_guid_control.length;
        size--;
        for (int current = 0; current < tabledata.RGB_mood_set.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.RGB_mood_set[current]);
            t2.setText(tabledata.RGB_mood_set2[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


            if(current!=size) {
                m1.setImageResource(imgicon_mood[current]);
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


            rgb_table.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }
    void rgb_moods_list() {

        TableRow row;
        TextView t1, t2;
        ImageView m1;
        //Converting to dip unit
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());

        int size=tabledata.RGB_mood_set_list.length;
        size--;
        for (int current = 0; current < tabledata.RGB_mood_set_list.length; current++) {

            row = new TableRow(this);

            t1 = new TextView(this);
            t1.setTextColor(getResources().getColor(R.color.black));
            t2 = new TextView(this);
            t2.setTextColor(getResources().getColor(R.color.black));

            t1.setText(tabledata.RGB_mood_set_list[current]);
            t2.setText(tabledata.RGB_mood_set2_list[current]);

            m1=new ImageView(this);
            if(current==2 || current == 3)
                m1.setLayoutParams(new TableRow.LayoutParams(50,50));
            else
                m1.setLayoutParams(new TableRow.LayoutParams(70,70));


            if(current!=size) {
                m1.setImageResource(imgicon_mood_list[current]);
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


            rgb_table2.addView(row, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

        }
    }



    /// popup images ///

    //RGB controller  //
    public void rgb_controller_popup_img()
    {
        rgb_controller_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_controller1);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }


    //RGB Mood setting  //
    public void rgb_mood_set_popup_img()
    {
        rgb_mood_set_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_mood);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        rgb_mood_set_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.curtain_mood_list);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }
        //RGB Perform operation setting  //
    public void rgb_perform_popup_img()
    {
        rgb_perform_img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_group_main);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        rgb_perform_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_group);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        rgb_perform_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_individual);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }

    //RGB Select  //
    public void rgb_select_popup_img()
    {
        rgb_select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_img1);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }

    //RGB Set timer //
    public void rgb_set_timer_popup_img()
    {
        rgb_set_timer_img1.setOnClickListener(new View.OnClickListener() {
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
        });

        rgb_set_timer_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_time_op);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        rgb_set_timer_img3.setOnClickListener(new View.OnClickListener() {
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

    //RGB change settings  //
    public void rgb_change_set_popup_img()
    {
        rgb_change_set_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);

                imgr.setImageResource(R.drawable.rgb_setting);

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
