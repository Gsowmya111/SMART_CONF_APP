package edisonbro.com.edisonbroautomation;

/**
 *  FILENAME: Main_Navigation_Activity.java
 *  DATE: 07-08-2018

 *  DESCRIPTION: Navigationbar class displaying house names and settings.

 *  Copyright (C) EdisonBro Smart Labs Pvt Ltd. All rights reserved.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.app.FragmentTransaction;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import edisonbro.com.edisonbroautomation.Connections.HttpHandler;
import edisonbro.com.edisonbroautomation.Connections.MyService;
import edisonbro.com.edisonbroautomation.Connections.TcpTransfer;
import edisonbro.com.edisonbroautomation.Connections.Tcp_con;
import edisonbro.com.edisonbroautomation.EnergySaving.EnergySavingActivity;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticStatus;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariables;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp;
import edisonbro.com.edisonbroautomation.databasewired.LocalDatabaseHelper;
import edisonbro.com.edisonbroautomation.databasewired.LocalListArrangeTable;
import edisonbro.com.edisonbroautomation.databasewired.SQLiteHelper;
import edisonbro.com.edisonbroautomation.databasewired.ServerDetailsAdapter;
import edisonbro.com.edisonbroautomation.loganalytics.LogAnalyticsActivity;
import edisonbro.com.edisonbroautomation.operatorsettings.OperatorSettingsMain;
import edisonbro.com.edisonbroautomation.operatorsettings.SmartSettings;
import edisonbro.com.edisonbroautomation.usersettings.UserSettingActivity;


import static edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div.housenamearr;

public class Main_Navigation_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
;

    // added by shreeshail //
    public static boolean server_flag = false ;


    // delete //
    /*Intent mServiceIntent;
    private MyService mSensorService;*/


    String email = null;
     private File file;
    private List<String> myList;


    Context ctx;

    public Context getCtx() {
        return ctx;
    }



    LocalDatabaseHelper db=null;
    TextView tv9;
    Button button_back,button_pop;
    MenuItem mt;
    PopupMenu popupMenu;
    private String TAG = Main_Navigation_Activity.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> contactList;
    String[] myStringArray;
    private ServerDetailsAdapter sdadap;
    int op2;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private Button nav_back_btn,nav_add_home;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    final int REQUEST_CODE=1;
    LocalListArrangeTable locallist_adap;
    String newappPass,newappuser,logintype;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    Button btnwtype,btnconstatus,btsig;
    String housenoarr[];
    private static final String TAG1="Main Activity - ";

    private Tracker mTracker;
    String name = new String("Navigation Bar");

    // Added by shreeshail  /////  Begin ////
    String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private String[] StorageDirectories;

    ////////////////////end//////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_navigation);


        // Added by shreeshail ( To check Permission )/////  Begin ////
        if (Build.VERSION.SDK_INT >= 23)
            if (!checkPermission())
                checkPermissions();
        else
            checkPermissions();
        ////////////////////end//////////////////////////////



        // delete //

       /* try {
            ctx = this ;
            //mSensorService = new MyService(getCtx());
            mSensorService = new MyService(getApplicationContext());
            mServiceIntent = new Intent(getCtx(), mSensorService.getClass());
            if (!isMyServiceRunning(mSensorService.getClass())) {
                startService(mServiceIntent);
            }

        }catch (Exception e){
            e.printStackTrace();
        }*/
        //////////////////////////////////

       // Obtain the shared Tracker instance.
        Edisonbro_AnalyticsApplication application = (Edisonbro_AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mt=(MenuItem) findViewById(R.id.nav_operator_sett);

        StaticVariables.udp_use=false;

        button_pop = (Button) findViewById(R.id.button3);

        button_pop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*PopupMenu popupMenu = new PopupMenu(Main_Navigation_Activity.this, button_back);
                //View menuItemView = getView().findViewById(R.id.menu_popup);
                popupMenu.inflate(R.menu.color_menu);
                popupMenu.show();
                */
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnconstatus=(Button) toolbar.findViewById(R.id.btnconstatus);
        btsig=(Button) toolbar.findViewById(R.id.btnsignal);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        //  View menuItemView1 = getView().findViewById(R.id.menu_popup);

        //  popupMenu = new PopupMenu(MainActivity.this, findViewById(R.id.nav_privacy_policy));


        Menu menu = navigationView.getMenu();

           //initialize database
        try{
            db=new LocalDatabaseHelper(this);
            sdadap=new ServerDetailsAdapter(this);
        }catch(Exception e){
            e.printStackTrace();
        }

        Housenos();

       //array of house names
        myStringArray=housenamearr;

        int sizearr=myStringArray.length,i,j=100,k=100,L=101,sizearr1;
        for(i=0;i<sizearr;i++)
        {
            menu.add(R.id.second_group, i, j+i, myStringArray[i]).setIcon(R.mipmap.home);
            menu.setGroupCheckable(R.id.second_group, true, true);
            menu.setGroupVisible(R.id.second_group, true);
        }
        navHeader = navigationView.getHeaderView(0);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        nav_back_btn=(Button ) navHeader.findViewById(R.id.nav_back);
        nav_add_home= (Button ) navHeader.findViewById(R.id.btndwnselection);

        nav_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawers();
                    return;
                }
            }
        });
        nav_add_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tcp_con.stopClient();
                Tcp_con.isClientStarted=false;
                Tcp.tcpConnectionClose();
                Tcp.tcpConnected=false;
                StaticVariabes_div.myStringArr_Pos=0;
               // Tcp_con.mTcpTransfer=null;
               // Tcp_con.unregReceivers();
               // Tcp_con.context=null;
                Intent i =new Intent(Main_Navigation_Activity.this,Local_Remote_DownloadActivity.class);
               // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // startActivityForResult(i, REQUEST_CODE);
                startActivity(i);
                finish();
            }
        });

       // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG.setAction("Action", null).show();
            }
        });

        btnconstatus.setVisibility(View.INVISIBLE);
        btsig.setVisibility(View.INVISIBLE);


        if(StaticVariabes_div.myStringArr_Pos==0){
            navItemIndex = 0;
            CURRENT_TAG = "fragment"+StaticVariabes_div.myStringArr_Pos;
            loadHomeFragment();

        }else {
            op2=StaticVariabes_div.myStringArr_Pos;
            navItemIndex = StaticVariabes_div.myStringArr_Pos;
            CURRENT_TAG = "fragment"+StaticVariabes_div.myStringArr_Pos;
            loadHomeFragment();
        }


        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

       /* toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.btnconstatus:

                        // do what ever you want here
                }
                return true;
            }
        });*/

        // Method added by shreeshail //
        //  Begin //
        deleteLoaclfolder();
        ////////////////////end//////////////////////////////

        /*// Method added by shreeshail on 11 FEB 2019 //
        //  Begin //
        try {
            new ReconnectGateway().execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        ////////////////////end//////////////////////////////
*/

    }


    private void loadNavHeader() {

        Drawable myDrawable = getResources().getDrawable(R.mipmap.eb_background);
        imgNavHeaderBg.setImageDrawable(myDrawable);
    }

    private void loadHomeFragment() {

        btnconstatus.setVisibility(View.INVISIBLE);
        btsig.setVisibility(View.INVISIBLE);
        // selecting appropriate nav menu item
        selectNavMenu();

        if(myStringArray.length>0) {

            // set toolbar title
            setToolbarTitle();

        }

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                try{
                    // update the main content by replacing fragments
                    Fragment fragment = getHomeFragment();
                    // FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();

    }

    private Fragment getHomeFragment() {
        CombFrag homeFragment = new CombFrag();
        return homeFragment;
    }

    private void setToolbarTitle() {
        try {
            getSupportActionBar().setTitle(myStringArray[op2]);
            StaticVariabes_div.housename = myStringArray[op2];
            StaticVariables.HOUSE_NAME = myStringArray[op2];
            StaticVariabes_div.housenumber = housenoarr[op2];
            FetchLoginAccess(myStringArray[op2]);
            StaticVariabes_div.myStringArr_Pos = op2;
        }catch (ArrayIndexOutOfBoundsException ae){
            op2=op2-1;
            getSupportActionBar().setTitle(myStringArray[op2]);
            StaticVariabes_div.housename = myStringArray[op2];
            StaticVariables.HOUSE_NAME = myStringArray[op2];
            StaticVariabes_div.housenumber = housenoarr[op2];
            FetchLoginAccess(myStringArray[op2]);
            StaticVariabes_div.myStringArr_Pos = op2;
        }

        btnconstatus.setVisibility(View.INVISIBLE);
        btsig.setVisibility(View.INVISIBLE);
        Tcp_con.stopClient();
        Tcp_con.isClientStarted=false;
        Tcp.tcpConnectionClose();
        Tcp.tcpConnected=false;
        // Tcp_con.unregReceivers();
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //tv9.setText(Integer.toString(menuItem.getItemId()));
                String opt=Integer.toString(menuItem.getItemId());

                if(menuItem.getItemId() == R.id.nav_operator_sett)
                {

                    if(StaticVariabes_div.loggeduser_type!=null) {
                        if (StaticVariabes_div.loggeduser_type.equals("A") || StaticVariabes_div.loggeduser_type.equals("SA")) {
                            Tcp_con.stopClient();
                            Tcp_con.isClientStarted = false;
                            // Tcp_con.unregReceivers();
                            Tcp.tcpConnectionClose();
                            Tcp.tcpConnected=false;

                            Intent intnt = new Intent(Main_Navigation_Activity.this, OperatorSettingsMain.class);
                            startActivity(intnt);
                            finish();
                        } else {
                            Toast.makeText(Main_Navigation_Activity.this, "Access Denied", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Main_Navigation_Activity.this, "Please Login To Access", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }else
                if(menuItem.getItemId() == R.id.nav_user_sett)
                {
                    Tcp_con.stopClient();
                    Tcp_con.isClientStarted = false;
                    Tcp.tcpConnectionClose();
                    Tcp.tcpConnected=false;
                    //Tcp_con.unregReceivers();
                    Intent intnt=new Intent(Main_Navigation_Activity.this, UserSettingActivity.class);
                    startActivity(intnt);
                    finish();

                    // if user select the current navigation menu again, don't do anything
                    // just close the navigation drawer
                    if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
                        drawer.closeDrawers();

                        // show or hide the fab button
                        toggleFab();
                        //return;
                    }
                    return true;
                }else
                if(menuItem.getItemId() == R.id.nav_about_sett)
                {
                    Tcp_con.stopClient();
                    Tcp_con.isClientStarted = false;
                    Tcp.tcpConnectionClose();
                    Tcp.tcpConnected=false;
                    Intent intnt=new Intent(Main_Navigation_Activity.this, AboutUs.class);
                    startActivity(intnt);
                    finish();
                    return true;
                }else
                if(menuItem.getItemId() == R.id.nav_complaints_sett)
                {
                    Tcp_con.stopClient();
                    Tcp_con.isClientStarted = false;
                    Tcp.tcpConnectionClose();
                    Tcp.tcpConnected=false;
                   // Intent intnt=new Intent(Main_Navigation_Activity.this, SupportActivity.class);
                    Intent intnt=new Intent(Main_Navigation_Activity.this, edisonbro.com.edisonbroautomation.customercarepager.Ebc_SplashScreen.class);
                    startActivity(intnt);
                    finish();
                    return true;
                }else
                if(menuItem.getItemId() == R.id.nav_smart_sett)
                {

                    if(StaticVariabes_div.loggeduser_type!=null) {
                        if (StaticVariabes_div.loggeduser_type.equals("A") || StaticVariabes_div.loggeduser_type.equals("SA")) {
                            Tcp_con.stopClient();
                            Tcp_con.isClientStarted = false;
                            Tcp.tcpConnectionClose();
                            Tcp.tcpConnected=false;
                            Intent intnt = new Intent(Main_Navigation_Activity.this, SmartSettings.class);
                            startActivity(intnt);
                            finish();
                        } else {
                            Toast.makeText(Main_Navigation_Activity.this, "Access Denied", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                            Toast.makeText(Main_Navigation_Activity.this, "Please Login To Access", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else if(menuItem.getItemId() == R.id.nav_log_analytics)
                {
                    if(StaticVariabes_div.loggeduser_type!=null) {
                    if(StaticVariabes_div.loggeduser_type.equals("A")||StaticVariabes_div.loggeduser_type.equals("SA")) {
                        Tcp_con.stopClient();
                        Tcp_con.isClientStarted = false;
                        Tcp.tcpConnectionClose();
                        Tcp.tcpConnected=false;
                        Intent intnt = new Intent(Main_Navigation_Activity.this, LogAnalyticsActivity.class);
                        startActivity(intnt);
                        finish();
                    }else{
                        Toast.makeText(Main_Navigation_Activity.this,"Access Denied",Toast.LENGTH_SHORT).show();

                    }
                    } else {
                        Toast.makeText(Main_Navigation_Activity.this, "Please Login To Access", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }else if(menuItem.getItemId() == R.id.nav_log_energy)
                {
                    if(StaticVariabes_div.loggeduser_type!=null) {
                        if(StaticVariabes_div.loggeduser_type.equals("A")||StaticVariabes_div.loggeduser_type.equals("SA")) {
                            Tcp_con.stopClient();
                            Tcp_con.isClientStarted = false;
                            Tcp.tcpConnectionClose();
                            Tcp.tcpConnected=false;
                            Intent intnt = new Intent(Main_Navigation_Activity.this, EnergySavingActivity.class);
                            startActivity(intnt);
                            finish();
                        }else{
                            Toast.makeText(Main_Navigation_Activity.this,"Access Denied",Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(Main_Navigation_Activity.this, "Please Login To Access", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                else {

                    btnconstatus.setVisibility(View.INVISIBLE);
                    btsig.setVisibility(View.INVISIBLE);
                    int opt1 = menuItem.getItemId();
                    op2=opt1;
                    navItemIndex = opt1;
                  //  navItemIndex = 0;

                    CURRENT_TAG = "fragment" + op2;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                // navItemIndex=menuItem.getItemId();
                loadHomeFragment();
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.str_user_sett, R.string.str_oper_sett) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        }else {
            popup_exit("Do You Really Want To Exit?");
        }
    }

    public void popup_exit(final String msg){
       runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main_Navigation_Activity.this);
                alertDialogBuilder.setTitle("INFO");
                // set dialog message
                alertDialogBuilder
                        .setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                                exitapp();
                            }
                        });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });
    }


    public void popup_logout(final String msg){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main_Navigation_Activity.this);
                alertDialogBuilder.setTitle("INFO");
                // set dialog message
                alertDialogBuilder
                        .setMessage(msg)
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();

                                if((StaticVariabes_div.housename==null)||(StaticVariabes_div.housename=="")||(StaticVariabes_div.housename.equals("")) ){

                                    popup_exit("Please Download House First");

                                }else {
                                    logoutfromhome();
                                }

                            }
                        });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();

                    }
                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });

    }
    public void logoutfromhome(){
        db.opendb();
        boolean manuallogup=db.updateLoginAccess(StaticVariabes_div.housenumber, "M");
        if(manuallogup) {
            Toast.makeText(Main_Navigation_Activity.this, "Manual login", Toast.LENGTH_SHORT).show();
            Intent intn = new Intent(Main_Navigation_Activity.this, Main_Navigation_Activity.class);
            startActivity(intn);
            finish();
        }else{
            Toast.makeText(Main_Navigation_Activity.this, "Manual login not updated", Toast.LENGTH_SHORT).show();
        }
        db.close();
        Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        //if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
      //  }

        // when fragment is notifications, load the menu created for notifications
        /*if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {


            if(StaticVariabes_div.housebase>0) {
                db.opendb();
                Cursor cursd = db.fetch_logintype(StaticVariabes_div.housenumber);

                if (cursd != null) {
                    logintype = cursd.getString(cursd.getColumnIndexOrThrow(LocalDatabaseHelper.LOGIN_ACCESS));
                }
                db.close();


                if (logintype.equals("M")) {
                    Toast.makeText(Main_Navigation_Activity.this, "Already LoggedOut", Toast.LENGTH_SHORT).show();
                } else {

                    popup_logout("Do You Really Want To Logout ?");
                }
            }
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        /*if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();*/
    }

    public void network_signal(int signal1, final boolean serv) {

        btnconstatus.setVisibility(View.VISIBLE);
        btsig.setVisibility(View.VISIBLE);

        if (serv) {
            if (btsig!=null) {
                if (signal1 <= 1)
                    btsig.setBackgroundResource(R.drawable.remote_sig_1);
                else if (signal1 <= 2)
                    btsig.setBackgroundResource(R.drawable.remote_sig_2);
                else if (signal1 <= 3)
                    btsig.setBackgroundResource(R.drawable.remote_sig_3);
                else if (signal1 <= 4)
                    btsig.setBackgroundResource(R.drawable.remote_sig_4);
            }
            if (StaticStatus.Network_Type.equals("TRUE3G")) {
                btsig.setBackgroundResource(R.drawable.mobiledata);
            }

        } else {
            // btnwtype.setText("local");

            if (btsig!=null) {
                if (signal1 <= 1)
                    btsig.setBackgroundResource(R.drawable.local_sig_1);
                else if (signal1 <= 2)
                    btsig.setBackgroundResource(R.drawable.local_sig_2);
                else if (signal1 <= 3)
                    btsig.setBackgroundResource(R.drawable.local_sig_3);
                else if (signal1 <= 4)
                    btsig.setBackgroundResource(R.drawable.local_sig_4);
            }
        }

        if(StaticStatus.Network_Type.equals("NONET")){
            btsig.setBackgroundResource(R.drawable.no_network);
            // btnwtype.setText("no-net");
            btnconstatus.setBackgroundResource(R.drawable.not_connected);
        }

    }

    public void serv_status(final boolean serv)
    {
        btnconstatus.setVisibility(View.VISIBLE);
        btsig.setVisibility(View.VISIBLE);
        runOnUiThread(new Runnable() {
            public void run() {
                if(serv){
                    btnconstatus.setBackgroundResource(R.drawable.connected);
                    btnconstatus.setText("1");

                    server_flag = true ;

                }
                else {
                    btnconstatus.setBackgroundResource(R.drawable.not_connected);
                    btnconstatus.setText("0");

                    server_flag = false ;




                }
            }
        });
    }

    //for spinner

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    void FetchLoginAccess(String omName){

        db.opendb();

        Cursor cu_logacces=db.fetch_LOGINACCESS(omName);

        if(cu_logacces!=null) {

            StaticVariabes_div.loggeduser_type=cu_logacces.getString(cu_logacces.getColumnIndexOrThrow(LocalDatabaseHelper.DATA1));
            StaticVariabes_div.loggeduser=cu_logacces.getString(cu_logacces.getColumnIndexOrThrow(LocalDatabaseHelper.USER_NAME));
          //  StaticVariabes_div.loggedpwd=cu_logacces.getString(cu_logacces.getColumnIndexOrThrow(LocalDatabaseHelper.PASSWORD));
            byte testpwd[]=cu_logacces.getBlob(cu_logacces.getColumnIndexOrThrow(LocalDatabaseHelper.PASSWORD));

            StaticVariabes_div.log(testpwd.length+"login_acces mainactivity "+new String((testpwd)), TAG1);


            String Data_decrypted;byte decryptedd[];
            try {
                decryptedd = StaticVariabes_div.decrypt(StaticVariabes_div.IV.getBytes(), StaticVariabes_div.encryptionKey.getBytes(), testpwd);
                Data_decrypted = new String(decryptedd);

                StaticVariabes_div.log("login_acces Data_decrypted"+Data_decrypted, TAG1);
                StaticVariabes_div.loggedpwd=Data_decrypted;
                // Log.d("decrypt read: ", DataRead);

            } catch (Exception e) {
                e.printStackTrace();
            }
            StaticVariabes_div.log("login_acces "+StaticVariabes_div.loggeduser_type+"User_Name "+ StaticVariabes_div.loggeduser+"Passw "+ StaticVariabes_div.loggedpwd, TAG1);
        }
        db.close();
    }

    void insert_Applock() {
        db.opendb();
        int applockcount = db.getCount_Applock();

        if(applockcount<0){

        }else{
            db.insert("AppEnable","false");
            db.insert("AppPassword","1234");
            db.insert("AppEmail","NoEmail");
        }
        db.close();
    }

    void Housenos(){
        db.opendb();
        int housecount=db.getCount();
        housenoarr = new String[housecount];
        housenoarr=db.getallhouseno(housecount);
        StaticVariabes_div.housenumarr=housenoarr;
        for(int v=0;v<housecount;v++){
            StaticVariabes_div.log("house no"+housenoarr[v], TAG1);
        }
        StaticVariabes_div.housebase=housecount;
        StaticVariabes_div.housenameinit();
        if(housecount>0){

            for(int l=0;l<housecount;l++) {
                Cursor cuh=db.fetch_housename(housenoarr[l]);

                if(cuh!=null){

                    String housename=cuh.getString(cuh.getColumnIndexOrThrow(LocalDatabaseHelper.HOUSE_NAME));
                    housenamearr[l]=housename;

                }else{
                    StaticVariabes_div.log("no housename", TAG1);
                }

            }
        }


        db.close();

    }


    /*public void onBackPressed() {
        exitapp();
        //finish();
        // android.os.Process.killProcess(android.os.Process.myPid());
    }*/

    void exitapp(){
        System.gc();
        finish();
        System.exit(0);
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + name);
        //using tracker variable to set Screen Name
        mTracker.setScreenName(name);
        //sending the screen to analytics using ScreenViewBuilder() method
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


    // Method added by shreeshail //
    //  Begin //
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Main_Navigation_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }
    ////////////////////end//////////////////////////////

    // Method added by shreeshail //
    //  Begin //
    public boolean deleteLoaclfolder(){
        // Get SD card Loaction path for all devices //
        StorageDirectories = getStorageDirectories();

        File folder = new File(StorageDirectories[0] +
                File.separator + "HomeAutomationDB");
        boolean success = false;
        if (!folder.exists()) {
            //success = folder.mkdirs();
            success = true;
        }else{
            success = folder.delete();
        }
        return success;
    }
    private static final Pattern DIR_SEPORATOR = Pattern.compile("/");
    public static String[] getStorageDirectories()
    {
        // Final set of paths
        final Set<String> rv = new HashSet<String>();
        // Primary physical SD-CARD (not emulated)
        final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
        // All Secondary SD-CARDs (all exclude primary) separated by ":"
        final String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
        // Primary emulated SD-CARD
        final String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
        if(TextUtils.isEmpty(rawEmulatedStorageTarget))
        {
            // Device has physical external storage; use plain paths.
            if(TextUtils.isEmpty(rawExternalStorage))
            {
                // EXTERNAL_STORAGE undefined; falling back to default.
                rv.add("/storage/sdcard0");
            }
            else
            {
                rv.add(rawExternalStorage);
            }
        }
        else
        {
            // Device has emulated storage; external storage paths should have
            // userId burned into them.
            final String rawUserId;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            {
                rawUserId = "";
            }
            else
            {
                final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                final String[] folders = DIR_SEPORATOR.split(path);
                final String lastFolder = folders[folders.length - 1];
                boolean isDigit = false;
                try
                {
                    Integer.valueOf(lastFolder);
                    isDigit = true;
                }
                catch(NumberFormatException ignored)
                {
                }
                rawUserId = isDigit ? lastFolder : "";
            }
            // /storage/emulated/0[1,2,...]
            if(TextUtils.isEmpty(rawUserId))
            {
                rv.add(rawEmulatedStorageTarget);
            }
            else
            {
                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId);
            }
        }
        // Add all secondary storages
        if(!TextUtils.isEmpty(rawSecondaryStoragesStr))
        {
            // All Secondary SD-CARDs splited into array
            final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
            Collections.addAll(rv, rawSecondaryStorages);
        }
        return rv.toArray(new String[rv.size()]);
    }
    //////////////////// end //////////////////////////////

    // Added by shreeshail on 8th FEB 2019 //
    // checking TCP Connection and reconnectig if not //
    @SuppressLint("StaticFieldLeak")
    class ReconnectGateway extends AsyncTask<Void, Void, Void> implements TcpTransfer {


        @Override
        public void read(int type, String stringData, byte[] byteData) {

        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                //Tcp_con mTcp = new Tcp_con(this);

                try{
                if(btnconstatus.getText().toString().equalsIgnoreCase("0")){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Tcp_con.stacontxt = getBaseContext().getApplicationContext();
                            Tcp_con.serverdetailsfetch(getBaseContext(), StaticVariabes_div.housename);
                            Tcp_con.registerReceivers(getBaseContext().getApplicationContext());

                            Toast.makeText(getBaseContext(), "re-connected", Toast.LENGTH_SHORT).show();

                            btnconstatus.setText("1");
                        }
                    });
                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //return null;
        }
    }

    //End of class


    // delete //

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
    //////////////


}

