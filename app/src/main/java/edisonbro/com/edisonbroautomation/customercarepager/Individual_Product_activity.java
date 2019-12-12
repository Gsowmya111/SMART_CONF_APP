package edisonbro.com.edisonbroautomation.customercarepager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.custmercareAdap.SearchableAdapter;

public class Individual_Product_activity extends AppCompatActivity {


    public List<String> filteredData;
    public SearchableAdapter searchableadapter;

    public SimpleAdapter simpleAdapter,simpleAdapter2;
    public ListView listView,listView_search,popuplistview;
    public LinearLayout listView_lay,listView_search_lay,main_layout;
    public EditText search_p;

    public ArrayAdapter<String> adapter;

    public TextView cleartext;

    public String p_name;
    public ProgressDialog progress;

    public List<HashMap<String,String>> aList2;
    public List<HashMap<String,String>> searchList;

    // Array of strings storing country names
    String[] countries = new String[] {
            "Smart Switchboard Design",
            "Smart Switchboard Operation",
            "IR Remote Operations",
            "Mobile App Operations",
            "Maintenance",
            "Troubleshooting"

    };

    // Array of strings storing country names
    String[] searchitems = new String[] {
            "Smart Switch",
            "Smart PIR Sensor",
            "EB Gateway",
            "Smart Curtain Controller",
            "Smart RGB",
            "Smart Dimmer",

            "Smart Switchboard Design",
            "Smart Switchboard Operation",
            "IR Remote Operations",
            "Switchboard Mobile App Operations",
            "Select Room & Switchboard",
            "Switchboard Perform Operations",
            "Switchboard Change icon",
            "Switchboard Change Settings",
            "Switchboard Set Timer",
            "Switchboard Set Moods",
            "Switchboard Maintenance",
            "Switchboard Troubleshooting",

            "Applications of EB PIR Sensor",
            "PIR Mobile App Operations",
            "PIR Select Room & PIR",
            "PIR Perform Operations",
            "PIR Change Settings",
            "PIR Set Timer",
            "PIR Troubleshooting",

            "Gateway Information",
            "Gateway Mobile Application",
            "Gateway Troubleshoot",

            "EdisonBro Smart Curtain Controller",
            "Curtain Mobile App Operations",
            "Curtain Select Room & Curtain",
            "Curtain Perform Operations",
            "Curtain Change Settings",
            "Curtain Set Timer",
            "Curtain Set Moods",
            "Curtain Troubleshooting",

            "EdisonBro Wireless Smart RGB Controller",
            "RGB Mobile App Operations",
            "RGB Select Room & RGB",
            "RGB Perform Operations",
            "RGB Change Settings",
            "RGB Set Timer",
            "RGB Set Moods",


            "EdisonBro Wireless Smart Dimmer Controller",
            "Dimmer Mobile App Operations",
            "Dimmer Select Room & Dimmer",
            "Dimmer Perform Operations",
            "Dimmer Change Settings",
            "Dimmer Set Timer",
            "Dimmer Set Moods"

    };

    int[] flags2 = new int[]{
            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.dimr_icon,

            R.drawable.swb,
            R.drawable.pirb,
            R.drawable.server,
            R.drawable.curtainb,
            R.drawable.rgb,
            R.drawable.curtainb,
            R.drawable.rgb,

            R.drawable.swb,


    };

     /*"Perform Operations",
             "Maintenance",
             "Troubleshooting"*/

    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
            R.drawable.swb,
            R.drawable.switch_operation,
            R.drawable.remote,
            R.drawable.logo,
            R.drawable.swb_mainten,
            R.drawable.troubleshoot

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
        setTitle("EB Intelligent "+p_name);
        setContentView(R.layout.cc_content_product_activity);
        /*ActionBar actionBar = getSupportActionBar();
        int avers= Build.VERSION.SDK_INT;
        if(avers!=24)
            if(avers>19)
                actionBar.setDisplayHomeAsUpEnabled(true);*/
       // actionBar.setDisplayHomeAsUpEnabled(true);

        listView=(ListView)findViewById(R.id.product_lv);
        listView_search=(ListView)findViewById(R.id.product_lv_search);

        listView_lay=(LinearLayout)findViewById(R.id.product_lv_lay);
        listView_search_lay=(LinearLayout)findViewById(R.id.product_lv_search_lay);
        main_layout=(LinearLayout)findViewById(R.id.main_layout);

        search_p=(EditText) findViewById(R.id.serach_p);

        cleartext=(TextView)findViewById(R.id.cleartext);

        tv_title=(TextView)findViewById(R.id.textView10);
        tv_title.setText(""+p_name);

        b_back=(Button) findViewById(R.id.btnback);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*progress = new ProgressDialog(this);
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
*/



        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
        searchList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<6;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("p_image", Integer.toString(flags[i]));
            hm.put("p_name",countries[i]);
            aList.add(hm);
        }


        aList2 = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<50;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("p_image", Integer.toString(flags2[i]));
            hm.put("p_name",searchitems[i]);
            searchList.add(hm);
        }

        filteredData = new ArrayList<>();
        for(int i=0;i<50;i++){

            filteredData.add(searchitems[i]);
        }




        // Keys used in Hashmap
        String[] from = { "p_image","p_name"};

        // Ids of views in listview_layout
        int[] to = { R.id.p_image,R.id.p_name};

        simpleAdapter =new SimpleAdapter(getBaseContext(), aList, R.layout.cc_product_listview, from, to);
        listView.setAdapter(simpleAdapter);
        listView.setTextFilterEnabled(true);

        //simpleAdapter2=new SimpleAdapter(getBaseContext(),aList2,R.layout.search_popup_list,new String[] {"p_name"} ,new int[] {R.id.listname});
       // listView_search.setAdapter(simpleAdapter2);
       // listView_search.setTextFilterEnabled(true);

        /*simpleAdapter2=new SimpleAdapter(getBaseContext(),searchList,R.layout.search_popup_list,new String[] {"p_name"} ,new int[] {R.id.listname});
        listView_search.setAdapter(simpleAdapter2);*/






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView p_name=(TextView)view.findViewById(R.id.p_name);

                if(p_name.getText().toString().equalsIgnoreCase("Mobile App Operations"))
                {
                    Intent intent = new Intent(getBaseContext(), Mobile_app_operation.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(getBaseContext(), SwitchBoard.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                }

            }
        });

        listView_search.setTextFilterEnabled(true);

        searchableadapter = new SearchableAdapter(this, filteredData); //new ArrayAdapter<String>(this, R.layout.search_popup_list, R.id.listname, searchitems);

        listView_search.setAdapter(searchableadapter);

        listView_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                TextView p_name=(TextView)view.findViewById(R.id.listname);
                String p_namestr=p_name.getText().toString();

                // 1 //
                if(p_namestr.equalsIgnoreCase("Smart Switch")) {

                    Intent intent = new Intent(getBaseContext(), Individual_Product_activity.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 2 //
                if(p_namestr.equalsIgnoreCase("Smart PIR Sensor")) {

                    Intent intent = new Intent(getBaseContext(), PIR_Product_activity.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 3 //
                if(p_namestr.equalsIgnoreCase("EB Gateway")) {

                    Intent intent = new Intent(getBaseContext(), GATEWAY_Product_activity.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 4 //
                if(p_namestr.equalsIgnoreCase("Smart Curtain Controller")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_Product_activity.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 5 //
                if(p_namestr.equalsIgnoreCase("Smart RGB")) {

                    Intent intent = new Intent(getBaseContext(), RGB_Product_activity.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 5 //
                if(p_namestr.equalsIgnoreCase("Smart Dimmer")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_Product_Activity.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 6 //
                if(p_namestr.equalsIgnoreCase("Smart Switchboard Design")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 7 //
                if(p_namestr.equalsIgnoreCase("Smart Switchboard Operation")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 8 //
                if(p_namestr.equalsIgnoreCase("IR Remote Operations")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 9 //
                if(p_namestr.equalsIgnoreCase("Switchboard Mobile App Operations")) {

                    Intent intent = new Intent(getBaseContext(), Mobile_app_operation.class);
                    intent.putExtra("p_name", "Mobile App Operations");
                    startActivity(intent);
                    //finish();
                }
                // 10 //
                if(p_namestr.equalsIgnoreCase("Select Room & Switchboard")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard_app_operation.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 11 //
                if(p_namestr.equalsIgnoreCase("Switchboard Perform Operations")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard_app_operation.class);
                    intent.putExtra("p_name", "Perform Operations");
                    startActivity(intent);
                    //finish();
                }
                // 12 //
                if(p_namestr.equalsIgnoreCase("Switchboard Change icon")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard_app_operation.class);
                    intent.putExtra("p_name", "Change icon");
                    startActivity(intent);
                    //finish();
                }
                // 13 //
                if(p_namestr.equalsIgnoreCase("Switchboard Change Settings")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard_app_operation.class);
                    intent.putExtra("p_name", "Change Settings");
                    startActivity(intent);
                    //finish();
                }
                // 14 //
                if(p_namestr.equalsIgnoreCase("Switchboard Set Timer")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard_app_operation.class);
                    intent.putExtra("p_name", "Set Timer");
                    startActivity(intent);
                    //finish();
                }
                // 15 //
                if(p_namestr.equalsIgnoreCase("Switchboard Set Moods")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard_app_operation.class);
                    intent.putExtra("p_name", "Set Moods");
                    startActivity(intent);
                    //finish();
                }
                // 16 //
                if(p_namestr.equalsIgnoreCase("Switchboard Maintenance")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard.class);
                    intent.putExtra("p_name", "Maintenance");
                    startActivity(intent);
                    //finish();
                }
                // 17 //
                if(p_namestr.equalsIgnoreCase("Switchboard Troubleshooting")) {

                    Intent intent = new Intent(getBaseContext(), SwitchBoard.class);
                    intent.putExtra("p_name", "Troubleshooting");
                    startActivity(intent);
                    //finish();
                }

                //////// PIR LIST ////////////

                // 18 //
                if(p_namestr.equalsIgnoreCase("Applications of EB PIR Sensor")) {

                    Intent intent = new Intent(getBaseContext(), PIR_info.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 19 //
                if(p_namestr.equalsIgnoreCase("PIR Mobile App Operations")) {

                    Intent intent = new Intent(getBaseContext(), PIR_Mobile_app_operation.class);
                    intent.putExtra("p_name", "Mobile App Operations");
                    startActivity(intent);
                    //finish();
                }
                // 20 //
                if(p_namestr.equalsIgnoreCase("PIR Troubleshooting")) {

                    Intent intent = new Intent(getBaseContext(), PIR_info.class);
                    intent.putExtra("p_name", "Troubleshooting");
                    startActivity(intent);
                    //finish();
                }
                // 21 //
                if(p_namestr.equalsIgnoreCase("PIR Select Room & PIR")) {

                    Intent intent = new Intent(getBaseContext(), PIR_app_operation.class);
                    intent.putExtra("p_name", "Select Room & PIR");
                    startActivity(intent);
                    //finish();
                }
                // 22 //
                if(p_namestr.equalsIgnoreCase("PIR Perform Operations")) {

                    Intent intent = new Intent(getBaseContext(), PIR_app_operation.class);
                    intent.putExtra("p_name", "Perform Operations");
                    startActivity(intent);
                    //finish();
                }
                // 23 //
                if(p_namestr.equalsIgnoreCase("PIR Change Settings")) {

                    Intent intent = new Intent(getBaseContext(), PIR_app_operation.class);
                    intent.putExtra("p_name", "Change Settings");
                    startActivity(intent);
                    //finish();
                }
                // 24 //
                if(p_namestr.equalsIgnoreCase("PIR Set Timer")) {

                    Intent intent = new Intent(getBaseContext(), PIR_app_operation.class);
                    intent.putExtra("p_name", "Set Timer");
                    startActivity(intent);
                    //finish();
                }


                /////  Gateway list //////
                // 25 //
                if(p_namestr.equalsIgnoreCase("Gateway Information")) {

                    Intent intent = new Intent(getBaseContext(), Gateway.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 26 //
                if(p_namestr.equalsIgnoreCase("Gateway Mobile Application")) {

                    Intent intent = new Intent(getBaseContext(), Gateway.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 27 //
                if(p_namestr.equalsIgnoreCase("Gateway Troubleshoot")) {

                    Intent intent = new Intent(getBaseContext(), Gateway.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }


                ////////  Curtain List   //////
                // 28 //
                if(p_namestr.equalsIgnoreCase("EdisonBro Smart Curtain Controller")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 29 //
                if(p_namestr.equalsIgnoreCase("Curtain Mobile App Operations")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_Mobile_app_list.class);
                    intent.putExtra("p_name", p_name.getText().toString());
                    startActivity(intent);
                    //finish();
                }
                // 30 //
                if(p_namestr.equalsIgnoreCase("Curtain Select Room & Curtain")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", "Select Room & Curtain");
                    startActivity(intent);
                    //finish();
                }
                // 31 //
                if(p_namestr.equalsIgnoreCase("Curtain Perform Operations")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", "Perform Operations");
                    startActivity(intent);
                    //finish();
                }
                // 32 //
                if(p_namestr.equalsIgnoreCase("Curtain Change Settings")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", "Change Settings");
                    startActivity(intent);
                    //finish();
                }
                // 33 //
                if(p_namestr.equalsIgnoreCase("Curtain Set Timer")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", "Set Timer");
                    startActivity(intent);
                    //finish();
                }
                // 34 //
                if(p_namestr.equalsIgnoreCase("Curtain Set Moods")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", "Set Moods");
                    startActivity(intent);
                    //finish();
                }
                // 35 //
                if(p_namestr.equalsIgnoreCase("Curtain Troubleshooting")) {

                    Intent intent = new Intent(getBaseContext(), Curtain_info.class);
                    intent.putExtra("p_name", "Troubleshooting");
                    startActivity(intent);
                    //finish();
                }

                //////// RGB List   ////////////
                // 36 //
                if(p_namestr.equalsIgnoreCase("EdisonBro Wireless Smart RGB Controller")) {

                    Intent intent = new Intent(getBaseContext(), RGB_info.class);
                    intent.putExtra("p_name", "EdisonBro Wireless Smart RGB Controller");
                    startActivity(intent);
                    //finish();
                }
                // 37 //
                if(p_namestr.equalsIgnoreCase("RGB Mobile App Operations")) {

                    Intent intent = new Intent(getBaseContext(), RGB_Mobile_app_list.class);
                    intent.putExtra("p_name", "Mobile App Operations");
                    startActivity(intent);
                    //finish();
                }
                // 38 //
                if(p_namestr.equalsIgnoreCase("RGB Select Room & RGB")) {

                    Intent intent = new Intent(getBaseContext(), RGB_info.class);
                    intent.putExtra("p_name", "Select Room & RGB");
                    startActivity(intent);
                    //finish();
                }
                // 39 //
                if(p_namestr.equalsIgnoreCase("RGB Perform Operations")) {

                    Intent intent = new Intent(getBaseContext(), RGB_info.class);
                    intent.putExtra("p_name", "Perform Operations");
                    startActivity(intent);
                    //finish();
                }
                // 40 //
                if(p_namestr.equalsIgnoreCase("RGB Change Settings")) {

                    Intent intent = new Intent(getBaseContext(), RGB_info.class);
                    intent.putExtra("p_name", "Change Settings");
                    startActivity(intent);
                    //finish();
                }
                // 41 //
                if(p_namestr.equalsIgnoreCase("RGB Set Timer")) {

                    Intent intent = new Intent(getBaseContext(), RGB_info.class);
                    intent.putExtra("p_name", "Set Timer");
                    startActivity(intent);
                    //finish();
                }
                // 42 //
                if(p_namestr.equalsIgnoreCase("RGB Set Moods")) {

                    Intent intent = new Intent(getBaseContext(), RGB_info.class);
                    intent.putExtra("p_name", "Set Moods");
                    startActivity(intent);
                    //finish();
                }

                //Dimmer
                // 43 //
                if(p_namestr.equalsIgnoreCase("EdisonBro Wireless Smart Dimmer Controller")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_info.class);
                    intent.putExtra("p_name", "EdisonBro Wireless Smart Dimmer Controller");
                    startActivity(intent);
                    //finish();
                }
                // 44 //
                if(p_namestr.equalsIgnoreCase("Dimmer Mobile App Operations")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_App_List.class);
                    intent.putExtra("p_name", "Mobile App Operations");
                    startActivity(intent);
                    //finish();
                }
                // 45 //
                if(p_namestr.equalsIgnoreCase("Dimmer Select Room & Dimmer")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_info.class);
                    intent.putExtra("p_name", "Select Room & Dimmer");
                    startActivity(intent);
                    //finish();
                }
                // 46 //
                if(p_namestr.equalsIgnoreCase("Dimmer Perform Operations")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_info.class);
                    intent.putExtra("p_name", "Perform Operations");
                    startActivity(intent);
                    //finish();
                }
                // 47 //
                if(p_namestr.equalsIgnoreCase("Dimmer Change Settings")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_info.class);
                    intent.putExtra("p_name", "Change Settings");
                    startActivity(intent);
                    //finish();
                }
                // 48 //
                if(p_namestr.equalsIgnoreCase("Dimmer Set Timer")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_info.class);
                    intent.putExtra("p_name", "Set Timer");
                    startActivity(intent);
                    //finish();
                }
                // 49 //
                if(p_namestr.equalsIgnoreCase("Dimmer Set Moods")) {

                    Intent intent = new Intent(getBaseContext(), Dimmer_info.class);
                    intent.putExtra("p_name", "Set Moods");
                    startActivity(intent);
                    //finish();
                }
            }
        });



        search_p.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // simpleAdapter.getFilter().filter(search_p.getText().toString());
               // simpleAdapter2.getFilter().filter(search_p.getText().toString().trim());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              //  simpleAdapter.getFilter().filter(search_p.getText().toString());
                if(i2==0)
                {
                    listView_lay.setVisibility(View.VISIBLE);
                    listView_search_lay.setVisibility(View.GONE);
                }
                else
                {
                    listView_lay.setVisibility(View.GONE);
                    listView_search_lay.setVisibility(View.VISIBLE);
                }

                searchableadapter.getFilter().filter(search_p.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
               // simpleAdapter.getFilter().filter(search_p.getText().toString());
                //simpleAdapter2.getFilter().filter(search_p.getText().toString().trim());
            }
        });

        search_p.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b)
                {
                    cleartext.setVisibility(View.VISIBLE);
                  //  listView_lay.setVisibility(View.GONE);
                  //  listView_search_lay.setVisibility(View.VISIBLE);
                    main_layout.setBackgroundColor(getResources().getColor(R.color.colorAccent2));

                }
                else
                {
                    cleartext.setVisibility(View.GONE);
                    listView_lay.setVisibility(View.VISIBLE);
                    listView_search_lay.setVisibility(View.GONE);
                    main_layout.setBackgroundColor(getResources().getColor(R.color.black));
                }
            }
        });
        cleartext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_p.setText("");
                search_p.clearFocus();
                // searchView2.setFocusable(false);
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
}
