package edisonbro.com.edisonbroautomation.Devices;


/**
 *  FILENAME: Pir_Frag.java
 *  DATE: 07-08-2018

 *  DESCRIPTION:  Fragment to operate selected pir Device individually .

 *  Copyright (C) EdisonBro Smart Labs Pvt Ltd. All rights reserved.
 *
 *   functions:
 *  transmitdata : To transmit pir data through tcp.
 *  Track_button_event : To track event on button click.
 *  timesett_pirpopup : To set time to pir model.
 *  Statusupdatelight : To update light sensor status.
 *  Statusupdate2 : To update pir sensor status.
 *  receiveddata : To receive data from tcp.
 *  Datain : To process the data received from tcp.=
 *  popup : popup to display info.
 */


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import edisonbro.com.edisonbroautomation.CheckView;
import edisonbro.com.edisonbroautomation.CombFrag;
import edisonbro.com.edisonbroautomation.Connections.TcpTransfer;
import edisonbro.com.edisonbroautomation.Connections.Tcp_con;
import edisonbro.com.edisonbroautomation.CustomGridViewActivity;
import edisonbro.com.edisonbroautomation.Edisonbro_AnalyticsApplication;
import edisonbro.com.edisonbroautomation.GridViewAdapter.Custom_device_GridViewActivity;
import edisonbro.com.edisonbroautomation.GridViewAdapter.Custom_device_operation_GridViewActivity;
import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.MutatorMethods.DeviceListArray;
import edisonbro.com.edisonbroautomation.MutatorMethods.PIR_WirelessDB_Data;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticStatus;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariables;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp_dwn_config;
import edisonbro.com.edisonbroautomation.databasewired.CamAdapter;
import edisonbro.com.edisonbroautomation.databasewired.MasterAdapter;
import edisonbro.com.edisonbroautomation.databasewired.SwbAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.HouseConfigurationAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.IRB_Adapter_ir;
import edisonbro.com.edisonbroautomation.databasewireless.WirelessConfigurationAdapter;
import edisonbro.com.edisonbroautomation.operatorsettings.UpdateHome_Existing;
import edisonbro.com.edisonbroautomation.scrollfunction.ShaderSeekArc;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Pir_Frag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Pir_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pir_Frag extends Fragment implements TcpTransfer, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private static final String TAG1 = "Pir frag- ";
    //************************************************************************

    private static final int READ_BYTE = 1, READ_LINE = 2
            , ServStatus = 3, signallevel = 4, NetwrkType = 5, MAXUSER = 6, ERRUSER = 7, UPDATE=8;
    //************************************************************************
    String devno, roomno = "0", housename, houseno, roomname, groupId = "000", broadcastMsg = "01",
            devtypesett, model, Roomname;

    int sl;
    boolean check = true, statusserv, remoteconn, remoteconn3g, nonetwork;
    String servpreviousstate, remoteconprevstate, rs, readMessage2, inp;
    String timevalue, timeva, lightvalue,  strtext,strtemp;
    //************************************************************************
    Main_Navigation_Activity mn_nav_obj;
    CombFrag combf;
    private Tracker mTracker;
    String name ="PIR Individual";
    //********************************************************************************
    int delay = 0; // delay for 1 sec.
    int period = 1000;
    Timer timer = null;
    //******************************************************************************

    View view;
    RadioButton rb_pir, rb_ligsensor;
    TextView tv_lightvalue, tv_priority;
    LinearLayout lay_pir, lay_lig;
    ImageView imgpir, imglig, pir_img_grp;
    Button btn_lig_enable, btn_lig_disable, btn_pir_enable, btn_pir_disable, btn_timesetpir;
    private EditText et_time;
    private Button bt_one,bt_two,bt_three,bt_four,bt_five,bt_six,bt_seven,bt_eight,bt_nine,bt_zero,bt_cancel,bt_clear,bt_set;
    private AlertDialog dialog;
    private TextView tv,tvtest,tv_err;


    // Added by shreeshail //
    // pir popup //
    PopupWindow popupWindow_one,popupWindow_two,popupWindow_three,popupWindow_four,popupWindow_five;


    PopupWindow rgbPopup_one,rgbPopup_two;

    ArrayList<DeviceListArray> deviceListArrays;
    int currentpos = 0;
    private Button confdevices,panelsetting;
    WirelessConfigurationAdapter WhouseDB=null;
    boolean isDbExists=false;
    ArrayList<HashMap<String,String>> temparray = null,pirarraylist = null;

    HouseConfigurationAdapter houseDB=null;


    String swdnoarr[], swdtypearr[],swdmodeltypearr[],swddevicenamearr[],swbnoarr[],swbtypearr[],swbmodeltypearr[],swbdevicenamearr[]
            ,rgbnoarr[],rgbtypearr[], rgbmodeltypearr[],rgbdevicenamearr[]
            ,dimmrnoarr[],dimmrtypearr[],dimmrmodeltypearr[],dimmrdevicenamearr[]
            ,curnoarr[],curtypearr[],curmodeltypearr[],curdevicenamearr[]
            ,projscrnoarr[],projscrtypearr[],projscrmodeltypearr[],projscrdevicenamearr[]
            ,projliftnoarr[], projlifttypearr[],projliftmodeltypearr[]
            ,wtrpmpnoarr[], wtrpmptypearr[], wtrpmpmodeltypearr[]
            ,fannoarr[], fantypearr[],fanmodeltypearr[],fandevicenamearr[]
            ,camnoarr[],camtypearr[]
            ,irnoarr[], irtypearr[] , irmodeltypearr[]
            ,gysernoarr[],gysertypearr[],gysermodeltypearr[],gyserdevicenamearr[]
            ,acnoarr[],actypearr[], acmodeltypearr[],acdevicenamearr[]
            ,pirnoarr[],pirtypearr[],pirmodeltypearr[],pirdevicenamearr[]
            ,gsknoarr[],gsktypearr[],gskmodeltypearr[],gskdevicenamearr[]
            ,clbnoarr[],clbtypearr[],clbmodeltypearr[],clbdevicenamearr[]
            ,dlsnoarr[],dlstypearr[],dlsmodeltypearr[],dlsdevicenamearr[]
            ,fmnoarr[],fmtypearr[],fmmodeltypearr[],fmdevicenamearr[]
            ,aqunoarr[],aqutypearr[], aqumodeltypearr[]
            ,smrtdognoarr[],smrtdogtypearr[], smrtdogmodeltypearr[],smrtdogdevicenamearr[]
            ,alexanoarr[],alexatypearr[], alexamodeltypearr[],alexadevicenamearr[]
            ,mirnoarr[],mirtypearr[], mirmodeltypearr[],mirdevicenamearr[];
    //********************************************************************
    private ArrayList<String> listswbno,listswbmod,listswbdevnam
            , listdmrno, listdmrmod, listdmrdevnam,listrgbno, listrgbmod,listrgbdevnam
            , listcurtno,listcurtmod,listcurtdevnam, listpscno, listpscmod,listpscdevnam
            , listplcno, listplcmod, listplcdevnam, listirno, listirmod,listirdevnam
            , listcamno, listcammod,listcamdevnam, listaquno, listaqumod,listaqudevnam
            , listfmno, listfmmod,listfmdevnam, listacno, listacmod, listacdevnam
            , listgsrno, listgsrmod,listgsrdevnam ,listgskno, listgskmod,listgskdevnam
            , listclbno, listclbmod, listclbdevnam, listdlsno, listdlsmod, listdlsdevnam
            , listfanno,listfanmod,listfandevnam,listwpcno,listwpcmod,listwpcdevnam
            , listpirno,listpirmod,listpirdevnam,listsdgno,listsdgmod,listsdgdevnam
            , listswdno,listswdmod,listswddevnam,listalexano,listalexamod,listalexadevnam
            , listmirno,listmirmod,listmirdevnam;


    private SwbAdapter swbadap;
    private MasterAdapter mas_adap;
    private CamAdapter cam_adap;
    private IRB_Adapter_ir ir_adap;

    ArrayList<HashMap<Integer,String>> deviceList ;
    // PIR WirelessTable data //
    int Current_RoomNo ;                                // a
    String Current_RoomName ;                           // b
    String Current_WRLS_DevName ;                       // wdn
    int Current_WRLS_DevNo ;                            // wd
    int Current_WRLS_DevType ;                          // wc
    String Current_WRLS_DevID ;                         // wf
    String Current_DevName ;                            // dn
    String LocalDataRef ;                               // ea
    int PanelSwitchNumber ;                             // wsn
    String SwitchData_WLS ;                             // wsd
    String Current_DevType ;                            // c
    int Current_DevNo ;                                 // d
    String Current_DevID;                              // f
    String SwitchNumbers;                              // bb
    String Current_WRLS_RoomName;                      // wb
    String Current_Pirlightsensorval_WRLS;             // eb
    String Current_User_Dev_Name;                      // ec
    String Current_Group_Id;                           // ea


    String Flag_OFF_ON = "" ;                      // ec
    String fanSpeed = "0";
    String deviceTypeForPirPanel = "6";

    ProgressDialog progressBar;
    String sPackName;

    String SwitchData_WLS2 ;
    String LocalDataRef2 ;

    String SwitchData_WLS3 ;
    String LocalDataRef3 ;

    String SwitchData_WLSfanon ;
    String LocalDataReffanon = "722" ;

    String SwitchData_WLSfanoff ;
    String LocalDataReffanoff = "723" ;

    String SwitchData_WLSfanspeed1 ;
    String LocalDataReffanspeed1 = "711";

    String SwitchData_WLSfanspeed2 ;
    String LocalDataReffanspeed2 = "712";

    String SwitchData_WLSfanspeed3 ;
    String LocalDataReffanspeed3 = "713";

    String SwitchData_WLSfanspeed4 ;
    String LocalDataReffanspeed4 = "714";


    // *********************************RGB COMMANDS*******************************//


    final String RGB_ON_CMD = "102", RGB_OFF_CMD = "103", RGB_ON_OFF_CMD = "101";

    final String RGB_COLOR_PROCESS_CMD = "112";

    final String RGB_FLASH_CMD = "104", RGB_STROBE_CMD = "105", RGB_SMOOTH_CMD = "106", RGB_FADE_CMD = "107";

    final String RGB_BRIGHTNESS_START_CHAR_CMD = "13", RGB_BRIGHTNESS10_CMD = "140";
    // RGB_BRIGHTNESS1_CMD="0131" TO RGB_BRIGHTNESS10_CMD="0140"

    final String RGB_SPEED_START_CHAR_CMD = "12", RGB_SPEED10_CMD = "130";
    // RGB_SPEED1_CMD="121" TO RGB_SPEED10_CMD="130" i.e low to high


    final String RGB_RED_CMD = "255000000000", RGB_GREEN_CMD = "000255000000", RGB_BLUE_CMD = "000000255000",
            RGB_ORANGE_CMD = "255165000000", RGB_WHITE_CMD = "255255255000", RGB_PINK_CMD = "255000128000";

    String RGB_Color_selected,RGB_Effect_selected,RGB_brightness_data,SwitchData_brightness_data;
    // *********************************DMR COMMANDS********************************//

    String dimmerbrightnessvalue;


    final String DMR_ON_CMD = "102", DMR_OFF_CMD = "103", DMR_ON_OFF_CMD = "101";

    final String DMR_BRIGHTNESS_PROCESS_CMD = "112";

    final String DMR_HIGH_CMD = "255", DMR_MEDIUM_CMD = "040", DMR_LOW_CMD = "002";

    final String DMR_1_CMD = "003", DMR_2_CMD = "015", DMR_3_CMD = "030", DMR_4_CMD = "050", DMR_5_CMD = "071",
            DMR_6_CMD = "080", DMR_7_CMD = "100", DMR_8_CMD = "150", DMR_9_CMD = "200", DMR_10_CMD = "250";

    //*********************************CUR COMMANDS******************************************//

    final String CUR1_OPEN_CMD = "101", CUR1_CLOSE_CMD = "102", CUR1_STOP_CMD = "103";
    final String CUR2_OPEN_CMD = "105", CUR2_CLOSE_CMD = "106", CUR2_STOP_CMD = "107";

    //********************************************************************

    ////////////////////////

    public Pir_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pir_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Pir_Frag newInstance(String param1, String param2) {
        Pir_Frag fragment = new Pir_Frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        strtext= StaticVariabes_div.devtyp;

        if(strtext==null){
            strtext=strtemp;
        }
        StaticVariabes_div.log("strtext"+strtext, TAG1);
        view = inflater.inflate(R.layout.pir_frag, container, false);

        tv_lightvalue = (TextView) view.findViewById(R.id.et_lightval);
        imgpir = (ImageView) view.findViewById(R.id.imgpir);
        imglig = (ImageView) view.findViewById(R.id.imglightsensr);
        btn_lig_enable = (Button) view.findViewById(R.id.b_lig_enable);
        btn_lig_disable = (Button) view.findViewById(R.id.b_lig_disable);
        btn_pir_enable = (Button) view.findViewById(R.id.b_ena_pir);
        btn_pir_disable = (Button) view.findViewById(R.id.b_dis_pir);

        rb_pir = (RadioButton) view.findViewById(R.id.rb_pir);
        rb_ligsensor = (RadioButton) view.findViewById(R.id.rb_ligsensor);


        lay_pir = (LinearLayout) view.findViewById(R.id.laypir);
        lay_lig = (LinearLayout) view.findViewById(R.id.laylig);

        btn_timesetpir = (Button) view.findViewById(R.id.pirsettime2);

        try {
            tvtest = (TextView) view.findViewById(R.id.tvtest);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),"msg"+e.toString(),Toast.LENGTH_SHORT).show();

        }

        if(tvtest!=null) {
            if (StaticVariabes_div.dev_name != null)
                tvtest.setText(StaticVariabes_div.dev_name);
        }

        if(strtext.equals("WPD1")) {
            StaticVariabes_div.dev_typ_num = "720";
            imgpir.setImageResource(R.drawable.direc_pir_sen_off);
        }else{
            StaticVariabes_div.dev_typ_num = "718";
            imgpir.setImageResource(R.drawable.motion);
        }

        StaticVariabes_div.loaded_lay_Multiple = false;
        combf = ((CombFrag) this.getParentFragment());
        combf.feature_sett_enab_disable(true);

        Edisonbro_AnalyticsApplication application = (Edisonbro_AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        //^^^^Time Pir BUTTON^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        btn_timesetpir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timesett_pirpopup(timeva);
            }
        });
        //^^^^Enable BUTTON^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        btn_pir_enable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                transmitdata("909", "A");
                Track_button_event("PIR Individual","MotionSensor ON","MotionSensor Shortclick");
            }
        });

        //^^^^Disable BUTTON^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        btn_pir_disable.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                transmitdata("910", "A");
                Track_button_event("PIR Individual","MotionSensor OFF","MotionSensor Shortclick");

            }
        });

        //^^^^light Enable BUTTON^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        btn_lig_enable.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                transmitdata("101", "A");
                Track_button_event("PIR Individual","LightSensor ON","MotionSensor Shortclick");

            }
        });

        //^^^^light Disable BUTTON^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        btn_lig_disable.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                transmitdata("102", "A");
                Track_button_event("PIR Individual","LightSensor OFF","MotionSensor Shortclick");

            }
        });
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        rb_pir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (rb_pir.isChecked()) {
                    transmitdata("1", "C");
                    rb_ligsensor.setChecked(false);
                }
            }
        });

        rb_ligsensor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (rb_ligsensor.isChecked()) {
                    transmitdata("2", "C");
                    rb_pir.setChecked(false);
                }
            }
        });

        Tcp_con mTcp = new Tcp_con(this);

        if (Tcp_con.isClientStarted) {
            // receiveddata(NetwrkType,StaticStatus.Network_Type,null);
            // receiveddata(ServStatus,StaticStatus.Server_status,null);
            transmitdata("920", "A");

        } else {
            Tcp_con.stacontxt = getActivity().getApplicationContext();
            Tcp_con.serverdetailsfetch(getActivity(), StaticVariabes_div.housename);
            Tcp_con.registerReceivers(getActivity().getApplicationContext());
        }

        mn_nav_obj = (Main_Navigation_Activity) getActivity();


        // added by shreeshail  //
        // configured device popup initiate button //
        confdevices = (Button) view.findViewById(R.id.confdevices);
        confdevices.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                final View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popuplogpir,null,false);
                final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
                popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                popupWindow.showAtLocation(popupview, Gravity.CENTER,0,0);

                TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
                TextView ok = (TextView) popupview.findViewById(R.id.ok);
                TextView pirname = (TextView) popupview.findViewById(R.id.pirname);


                final TextView roomnameval = (TextView) popupview.findViewById(R.id.roomnameval);
                final TextView devicenameval = (TextView) popupview.findViewById(R.id.devicenameval);
                final TextView switchonval = (TextView) popupview.findViewById(R.id.switchonval);
                final TextView switchoffval = (TextView) popupview.findViewById(R.id.switchoffval);

                final TextView switchonval_idle = (TextView) popupview.findViewById(R.id.switchonval_idle);
                final TextView switchoffval_idle = (TextView) popupview.findViewById(R.id.switchoffval_idle);


                final TextView nodevice = (TextView) popupview.findViewById(R.id.nodevice);


                final RelativeLayout devicefoundlay = (RelativeLayout) popupview.findViewById(R.id.devicefoundlay);

                pirname.setText(StaticVariabes_div.dev_name);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });


                TabLayout tabs_custom = (TabLayout) popupview.findViewById(R.id.tabs_custom);


                //setting house name for wireless database
                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                try{
                    WhouseDB=new WirelessConfigurationAdapter(getContext());
                    WhouseDB.open();			//opening wireless database
                }catch(Exception e){
                    e.printStackTrace();
                }
                try {
                    isDbExists=WhouseDB.checkdb();
                } catch (IOException e) {
                    //e.printStackTrace();
                    StaticVariables.printLog("TAG","unable to open database");
                }
                temparray = new ArrayList<HashMap<String,String>>();
                pirarraylist = new ArrayList<HashMap<String,String>>();

                temparray = WhouseDB.getpircobfigureddata(StaticVariabes_div.devnumber);


                WhouseDB.close();

                if(temparray!=null && temparray.size()>0){
                    devicefoundlay.setVisibility(View.VISIBLE);
                    nodevice.setVisibility(View.GONE);

                    for (int k = 0; k < temparray.size(); k++) {
                        tabs_custom.addTab(tabs_custom.newTab().setText(temparray.get(k).get("ec")));
                    }

                    tabs_custom.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            //Toast.makeText(getContext(),"position : "+tab.getPosition(),Toast.LENGTH_SHORT).show();

                            try {
                                WhouseDB.open();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if(temparray.get(tab.getPosition()).get("dn").contains("S0")){
                                try{
                                    confdevicesSWB(popupview,temparray.get(tab.getPosition()).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);
                                }catch (Exception e){

                                }
                            }else if(temparray.get(tab.getPosition()).get("dn").contains("RGB1")){
                                try{
                                    confdevicesRGB(popupview,temparray.get(tab.getPosition()).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                                }catch (Exception e){
                                    e.printStackTrace();

                                }

                            }else if(temparray.get(tab.getPosition()).get("dn").contains("DMR1")){
                                try{
                                    confdevicesDIMMER(popupview,temparray.get(tab.getPosition()).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                                }catch (Exception e){
                                    e.printStackTrace();

                                }

                            }else if(temparray.get(tab.getPosition()).get("dn").contains("CLNR") || temparray.get(tab.getPosition()).get("dn").contains("PSC1") || temparray.get(tab.getPosition()).get("dn").contains("CLSH") || temparray.get(tab.getPosition()).get("dn").contains("CLS1") || temparray.get(tab.getPosition()).get("dn").contains("CRS1")){
                                try{
                                    confdevicesCURTAIN(popupview,temparray.get(tab.getPosition()).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }else {
                                try{
                                    confdevicesOTHERS(popupview,temparray.get(tab.getPosition()).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            try {
                                WhouseDB.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });


                    try {
                        WhouseDB.open();
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    if(temparray.get(0).get("dn").contains("S0")){

                        try{
                            confdevicesSWB(popupview,temparray.get(0).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }else if(temparray.get(0).get("dn").contains("RGB1")){
                        try{
                            confdevicesRGB(popupview, temparray.get(0).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else if(temparray.get(0).get("dn").contains("DMR1")){
                        try{
                            confdevicesDIMMER(popupview,temparray.get(0).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                        }catch (Exception e){
                            e.printStackTrace();

                        }

                    }else if(temparray.get(0).get("dn").contains("CLNR") || temparray.get(0).get("dn").contains("PSC1") || temparray.get(0).get("dn").contains("CLSH") || temparray.get(0).get("dn").contains("CLS1") || temparray.get(0 ).get("dn").contains("CRS1")){
                        try{
                            confdevicesCURTAIN(popupview,temparray.get(0).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                        }catch (Exception e){
                            e.printStackTrace();

                        }

                    }else {
                        try{
                            confdevicesOTHERS(popupview,temparray.get(0).get("ec"),roomnameval,devicenameval,switchonval,switchoffval,switchonval_idle,switchoffval_idle);

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    try {
                        WhouseDB.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else {
                    devicefoundlay.setVisibility(View.GONE);
                    nodevice.setVisibility(View.VISIBLE);
                }

                /*for(int i=0;i<temparray.size();i++){
                    boolean found = false;
                    for (int k=0;k<pirarraylist.size();k++){
                        if(temparray.get(i).get("ec").equalsIgnoreCase(pirarraylist.get(k).get("ec"))){
                            found = true;
                            String[] tempstr = temparray.get(i).get("ea").split(",");
                           // if(tempstr[0].contains(",")) {
                            if(temparray.get(i).get("wsn").equalsIgnoreCase("101")) {
                                int tempint = Integer.parseInt(tempstr[0]);
                                if (tempint < 300)
                                    pirarraylist.get(k).put("onswitch", pirarraylist.get(k).get("onswitch") + temparray.get(i).get("bb") + ",");
                                else if (tempint > 300)
                                    pirarraylist.get(k).put("offswitch", pirarraylist.get(k).get("offswitch") + temparray.get(i).get("bb") + ",");
                            }
                           // }
                        }
                    }
                    if(!found){

                        String[] tempstr = temparray.get(i).get("ea").split(",");
                       // if(tempstr[0].contains(",")) {
                        HashMap<String, String> hashMap2 = temparray.get(i);
                        for(int k=0;k<tempstr.length;k++) {
                            int tempint = Integer.parseInt(tempstr[k]);

                            if (temparray.get(i).get("wsn").equalsIgnoreCase("101")) {
                                if (tempint < 300) {
                                    if(hashMap2.containsKey("onswitch"))
                                        hashMap2.put("onswitch", ""+hashMap2.get("onswitch")+","+tempint);//temparray.get(i).get("bb") + ",");
                                    else
                                        hashMap2.put("onswitch", ""+tempint);//temparray.get(i).get("bb") + ",");

                                    //hashMap2.put("offswitch", "");
                                } else if (tempint > 300) {
                                    if(hashMap2.containsKey("offswitch"))
                                        hashMap2.put("offswitch", ""+hashMap2.get("offswitch")+","+tempint);//temparray.get(i).get("bb") + ",");
                                    else
                                        hashMap2.put("offswitch", ""+tempint);//temparray.get(i).get("bb") + ",");
                                    // hashMap2.put("onswitch", "");
                                }
                            }
                        } pirarraylist.add(hashMap2);
                        //}
                    }
                }*/

                /*if(pirarraylist!=null && pirarraylist.size()>0){

                    devicefoundlay.setVisibility(View.VISIBLE);
                    nodevice.setVisibility(View.GONE);

                        for (int k = 0; k < pirarraylist.size(); k++) {
                            tabs_custom.addTab(tabs_custom.newTab().setText(pirarraylist.get(k).get("ec")));
                        }

                    roomnameval.setText(pirarraylist.get(0).get("b"));
                    devicenameval.setText(pirarraylist.get(0).get("ec"));

                    String swon = pirarraylist.get(0).get("onswitch");
                    String swoff = pirarraylist.get(0).get("offswitch");
                    if (swon.endsWith(",")) {
                        swon = swon.substring(0, swon.length() - 1) + " ";
                    } if (swoff.endsWith(",")) {
                        swoff = swoff.substring(0, swoff.length() - 1) + " ";
                    }

                    switchonval.setText(swon);
                    switchoffval.setText(swoff);

                        tabs_custom.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                Toast.makeText(getContext(),"position : "+tab.getPosition(),Toast.LENGTH_SHORT).show();

                                roomnameval.setText(pirarraylist.get(tab.getPosition()).get("b"));
                                devicenameval.setText(pirarraylist.get(tab.getPosition()).get("ec"));

                                String swon = pirarraylist.get(tab.getPosition()).get("onswitch");
                                String swoff = pirarraylist.get(tab.getPosition()).get("offswitch");
                                if (swon.endsWith(",")) {
                                    swon = swon.substring(0, swon.length() - 1) + " ";
                                } if (swoff.endsWith(",")) {
                                    swoff = swoff.substring(0, swoff.length() - 1) + " ";
                                }

                                switchonval.setText(swon);
                                switchoffval.setText(swoff);
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });



                }else {
                    devicefoundlay.setVisibility(View.GONE);
                    nodevice.setVisibility(View.VISIBLE);
                }*/

            }
        });
        //////////////////////////


        // added by shreeshail  //
        // configured device popup initiate button //
        panelsetting = (Button) view.findViewById(R.id.panelsetting);

        if(StaticVariabes_div.loggeduser_type.equals("A")||StaticVariabes_div.loggeduser_type.equals("SA")) {
            panelsetting.setVisibility(View.VISIBLE);
            confdevices.setVisibility(View.VISIBLE);
        }else {
            panelsetting.setVisibility(View.GONE);
            confdevices.setVisibility(View.GONE);
        }

        panelsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String devicetype = StaticVariabes_div.dev_typ_num;

                if (devicetype.equalsIgnoreCase("720"))
                    popup_all_devices_list(StaticVariabes_div.roomname);
                else if (devicetype.equalsIgnoreCase("718")) {
                    final View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_pannel_room_main,null,false);
                    popupWindow_one = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
                    popupWindow_one.setAnimationStyle(R.style.PauseDialogAnimation);
                    popupWindow_one.showAtLocation(popupview, Gravity.CENTER,0,0);

                    TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow_one.dismiss();
                        }
                    });

                    TextView ok = (TextView) popupview.findViewById(R.id.ok);
                    ok.setVisibility(View.GONE);

                    ArrayList<HashMap<Integer,String>> roomlist = new ArrayList<HashMap<Integer,String>> ();

                    roomlist = CombFrag.roomnameimage;

                    GridView gridView = (GridView) popupview.findViewById(R.id.gridView);

                    CustomGridViewActivity customGridViewActivity = new CustomGridViewActivity(getActivity(), roomlist);
                    gridView.setAdapter(customGridViewActivity);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            TextView android_gridview_text = (TextView) view.findViewById(R.id.android_gridview_text);
                            //Toast.makeText(getActivity(),"room name = "+android_gridview_text.getText().toString(),Toast.LENGTH_SHORT).show();

                            popup_all_devices_list(android_gridview_text.getText().toString());
                        }
                    });
                }

            }

        });

        //////////////////////////

        return view;
    }

    public void Track_button_event(String catagoryname,String actionname,String labelname){
        Tracker t = ((Edisonbro_AnalyticsApplication) getActivity().getApplication()).getDefaultTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder()
                .setCategory(catagoryname)
                .setAction(actionname)
                .setLabel(labelname)
                .build());
    }
    public void timesett_pirpopup(String prevtext) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.pirtimesett_dialog, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(view);
        et_time =(EditText) alertLayout.findViewById(R.id.et_time);
        bt_one =(Button) alertLayout.findViewById(R.id.bt_one);
        bt_two = (Button)alertLayout.findViewById(R.id.bt_two);
        bt_three =(Button) alertLayout.findViewById(R.id.bt_three);
        bt_four = (Button)alertLayout.findViewById(R.id.bt_four);
        bt_five =(Button) alertLayout.findViewById(R.id.bt_five);
        bt_six = (Button)alertLayout.findViewById(R.id.bt_six);
        bt_seven = (Button)alertLayout.findViewById(R.id.bt_seven);
        bt_eight = (Button)alertLayout.findViewById(R.id.bt_eight);
        bt_nine = (Button)(Button)alertLayout.findViewById(R.id.bt_nine);
        bt_zero =(Button) alertLayout.findViewById(R.id.bt_zero);
        bt_cancel = (Button)alertLayout.findViewById(R.id.bt_cancel);
        bt_clear = (Button)alertLayout.findViewById(R.id.bt_clear);
        bt_set = (Button) alertLayout.findViewById(R.id.bt_set);
        tv_err=(TextView) alertLayout.findViewById(R.id.tv_errmsg);

        bt_one.setOnClickListener(this);
        bt_two.setOnClickListener(this);
        bt_three.setOnClickListener(this);
        bt_four.setOnClickListener(this);
        bt_five.setOnClickListener(this);
        bt_six.setOnClickListener(this);
        bt_seven.setOnClickListener(this);
        bt_eight.setOnClickListener(this);
        bt_nine.setOnClickListener(this);
        bt_zero.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
        bt_set.setOnClickListener(this);


        TextView title = new TextView(getActivity());
        // You Can Customise your Title here
        title.setText("Set Time");
        title.setBackgroundColor(Color.parseColor("#0E4D92"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        alert.setCustomTitle(title);
        alert.setView(alertLayout);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.show();
        if(prevtext==null){
            prevtext="0000";
        }
        et_time.setText(prevtext);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_one:
                et_time.append("1");
                break;
            case R.id.bt_two:
                et_time.append("2");
                break;
            case R.id.bt_three:
                et_time.append("3");
                break;
            case R.id.bt_four:
                et_time.append("4");
                break;
            case R.id.bt_five:
                et_time.append("5");
                break;
            case R.id.bt_six:
                et_time.append("6");
                break;
            case R.id.bt_seven:
                et_time.append("7");
                break;
            case R.id.bt_eight:
                et_time.append("8");
                break;
            case R.id.bt_nine:
                et_time.append("9");
                break;
            case R.id.bt_zero:
                et_time.append("0");
                break;
            case R.id.bt_cancel:
                dialog.cancel();
                break;
            case R.id.bt_clear:
                et_time.setText("");
                break;
            case R.id.bt_set:

                timevalue = et_time.getText().toString();
              //  tv.setText(data);
                while(timevalue.length()<4)timevalue="0"+timevalue;
                if(isValidtime(timevalue)){

                    int tm=Integer.parseInt(timevalue);

                    if(tm>4) {
                        StaticVariabes_div.log("timevalue" + timevalue + "length" + timevalue.length(), TAG1);

                        if (timevalue != null)
                            transmitdata(timevalue, "B");

                        dialog.cancel();
                    }else{
                       // dialog.cancel();
                        //timesett_pirpopup(timevalue);
                        et_time.requestFocus();
                        tv_err.setText("Min Value 5");
                        et_time.setError("Min Value 5");
                    }
                }else{
                   // dialog.cancel();
                   // timesett_pirpopup(timevalue);
                    et_time.requestFocus();
                    tv_err.setText("Max Length 4digit");
                    et_time.setError("Max Length 4digit");

                }
                Track_button_event("PIR Individual","MotionSensor change value","MotionSensor Shortclick");
                break;
        }

    }

    private boolean isValidtime(String time) {
        if (time != null && time.length()<5) {

            return true;
        }
        return false;
    }

    void transmitdata(String val4,String type)
    {  String str=null;
        // val4=""+val3;
        devno=StaticVariabes_div.pirnoa[StaticVariabes_div.pirrpst];

        roomno= StaticVariabes_div.room_n;
        while(devno.length()<4)devno="0"+devno;

        while(roomno.length()<2)roomno="0"+roomno;


        if(type.equals("A")) {
            while(val4.length()<3)val4="0"+val4;

                str = "0" + "01" + "000" + devno + roomno+val4+"000000000000000";
                StaticVariabes_div.log("str" + str, TAG1);

        }  else if(type.equals("C")) {

            while(val4.length()<1)val4="0";

                str = "0" + "01" + "000" + devno + roomno+"103"+"0000"+val4+"0000000000";
                StaticVariabes_div.log("str" + str, TAG1);

        }else {

            while(val4.length()<4)val4="0"+val4;

                str = "0" + "01" + "000" + devno + roomno+"000"+val4+"00000000000";
                StaticVariabes_div.log("str" + str, TAG1);

        }

        byte[] op=str.getBytes(); //commented
        //byte[] op= Blaster.WriteData(str, 1); //uncommented
        byte[] result = new byte[32];
        result[0] = (byte) '*';
        result[31] = (byte) '#';
        for (int i = 1; i < 31; i++)
            result[i] = op[(i - 1)];
        StaticVariabes_div.log("bout" + result + "$$$" + val4, TAG1);
        Tcp_con.WriteBytes(result);

    }
    @Override
    public void read(final int type, final String stringData, final byte[] byteData)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    receiveddata(type, stringData, byteData);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void receiveddata(int msg,String data,byte[] bytestatus){

        switch (msg) {
            case READ_BYTE:
                byte[] readBuf = bytestatus;
                final String readMessage = new String(readBuf, 0,readBuf.length);
                StaticVariabes_div.log("msg read :- " + data + " msg", TAG1);
                DataIn(readBuf);
                break;
            case READ_LINE:
                StaticVariabes_div.log("msg read A_s" + data, TAG1);
                readMessage2 =data;
                if(readMessage2.equals("*OK#")){
                    mn_nav_obj.serv_status(true);
                    transmitdata("920","A");
                }else{
                    combf.timerresponse(readMessage2);
                }

                // Added by shreeshail //

                if (data != null && data.startsWith("*") && data.endsWith("$")) {
                    if (data.contains("*122$")) {
                        // uploadstatus=true;

                        ((Activity)getActivity()).runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(200);
                                    progressstop();
                                    // tv_poperr.setText("***");
                                    popup("Wireless Settings Uploaded Successfully ");
                                    setPIRpannelsetting();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                // uploadstatus=false;
                                // serv_status(false);
                            }
                        });

                    }
                }

                if (data != null && data.startsWith("*") && data.contains(":")) {
                    if (data.contains("*000:")) {
                        // uploadstatus=false;
                        ((Activity)getActivity()).runOnUiThread(new Runnable() {
                            public void run() {
                                //tv_poperr.setText("***");
                                progressstop();
                                popup("Wireless Settings Not Uploaded Try Again");
                            }
                        });
                    }
                }

                //********************//

                break;
            case ServStatus:
                final String ServerStatusB =data;
                StaticVariabes_div.log("serv status swb" + ServerStatusB, TAG1);
                if(ServerStatusB!=null){
                    if (ServerStatusB.equals("TRUE")) {
                        StaticStatus.Server_status_bool=true;
                        statusserv = true;
                        servpreviousstate="TRUE";
                        nonetwork=false;
                            transmitdata("920","A");
                    }else {
                        StaticStatus.Server_status_bool=false;
                        statusserv = false;
                        servpreviousstate="FALSE";
                    }
                }else{
                    StaticStatus.Server_status_bool=false;
                    statusserv = false;
                    servpreviousstate="FALSE";
                }

                mn_nav_obj.serv_status(statusserv);

                break;
            case signallevel:
                final String signallevelB = data;
                if(signallevelB!=null){
                    sl = Integer.parseInt(signallevelB);
                    rs=signallevelB;

                    if((StaticStatus.Network_Type.equals("TRUE")||(StaticStatus.Network_Type.equals("TRUE3G")))){
                        mn_nav_obj.network_signal(sl,true);
                        if(StaticStatus.Network_Type.equals("TRUE3G")||StaticStatus.Network_Type.equals("NONET")){
                            if(timer!=null){
                                timer.cancel();
                                timer=null;
                            }
                        }

                    }else{
                        mn_nav_obj.network_signal(sl,false);
                    }
                }
                break;
            case NetwrkType:
                final String RemoteB = data;
                StaticStatus.Network_Type=RemoteB;
                StaticVariabes_div.log("serv Remote swb" + RemoteB, TAG1);
                if (RemoteB.equals("TRUE")) {
                    nonetwork=false;
                    remoteconn = true;
                    remoteconn3g = false;
                    remoteconprevstate="TRUE";

                    mn_nav_obj.network_signal(sl,remoteconn);

                    if(timer==null){
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask()
                        {
                            public void run()
                            {
                                Tcp_con.rssirec();  // display the data

                            }
                        }, delay, period);
                    }
                }else if(RemoteB.equals("TRUE3G")){
                    nonetwork=false;
                    remoteconn = true;
                    remoteconn3g = true;
                    remoteconprevstate="TRUE3G";
                    nonetwork=false;
                    if(timer!=null){
                        timer.cancel();
                        timer=null;
                    }
                    mn_nav_obj.network_signal(sl,remoteconn);

                } else if (RemoteB.equals("NONET"))
                {
                    statusserv = false;
                    servpreviousstate="FALSE";
                    nonetwork=true;
                    if(timer!=null){
                        timer.cancel();
                        timer=null;
                    }
                    remoteconn = false;
                    remoteconn3g = false;

                    mn_nav_obj.network_signal(sl,remoteconn);

                }else {
                    nonetwork=false;
                    remoteconn = false;
                    remoteconn3g = false;
                    remoteconprevstate="FALSE";
                    if(timer==null){
                        timer = new Timer();
                        timer.scheduleAtFixedRate(new TimerTask()
                        {
                            public void run()
                            {
                                Tcp_con.rssirec();  // display the data
                            }
                        }, delay, period);
                    }
                    mn_nav_obj.network_signal(sl,remoteconn);

                }

                break;
            case MAXUSER:
                final String maxuser = data;
                StaticVariabes_div.log("maxuser swb" + maxuser, TAG1);

                if (maxuser.equals("TRUE")) {
                    popup("User Exceeded");
                    mn_nav_obj.serv_status(false);
                } else {

                }

                break;
            case  ERRUSER:
                final String erruser = data;
                StaticVariabes_div.log("erruser swb" + erruser, TAG1);

                if (erruser.equals("TRUE")) {
                    popup("INVALID USER/PASSWORD");
                    mn_nav_obj.serv_status(false);
                } else {

                }

                break;
            case  UPDATE:
                final String update = data;
                StaticVariabes_div.log("StaticVariabes_div.House_dbver_num_gateway" + StaticVariabes_div.House_dbver_num_gateway, TAG1);
                StaticVariabes_div.log("StaticVariabes_div.House_dbver_num_local" + StaticVariabes_div.House_dbver_num_local, TAG1);

                if (update.equals("TRUE")) {

                    if (Float.valueOf(StaticVariabes_div.House_dbver_num_gateway) > Float.valueOf(StaticVariabes_div.House_dbver_num_local)) {
                        //popup("UPDDATE");
                        Tcp_con.stopClient();
                        Tcp_con.isClientStarted=false;

                        Tcp_dwn_config.tcpHost=Tcp_con.tcpAddress;
                        Tcp_dwn_config.tcpPort=Tcp_con.tcpPort;

                        Intent intt=new Intent(getActivity(),UpdateHome_Existing.class);
                        intt.putExtra("isusersett","updatesett");
                        startActivity(intt);
                    } if (Float.valueOf(StaticVariabes_div.House_dbver_num_gateway) < Float.valueOf(StaticVariabes_div.House_dbver_num_local)) {
                        Toast.makeText(getActivity(),"App Database Version is Higher Than Gateway Version",Toast.LENGTH_LONG).show();
                        //popup("App Database Version is Higher Than Gateway Version");
                    }else
                    {
                        // popup("No UPDDATE");
                    }
                }
                break;
        }
    }

    public void popup(String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("INFO");
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    void DataIn(byte[] byt)
    {
        if (byt != null && byt.length == 32) {
            byte[] input = new byte[30];
            System.arraycopy(byt, 1, input, 0, ((byt.length) - 2));
            try{
                inp=new String(input); //commented
                //inp = Blaster.ReadData(input, 1); //uncommented
                if(inp!=null){

                    StaticVariabes_div.log("input length"+byt.length+"input full  "+inp, TAG1);
                    String DevType = inp.substring(1, 4);
                    String Dev = inp.substring(4, 8);
                    String DS =inp.substring(8, 10);
                    String dval =inp.substring(8, 12);
                    final String priorityval =inp.substring(12, 13);
                    String lightenval =inp.substring(13, 14);
                    String lightval =inp.substring(14, 15);
                    char E1 = inp.charAt(28);
                    char E2 = inp.charAt(29);

                    StaticVariabes_div.log("DS"+DS+"Devno"+Dev+"devno"+devno+"DevType"+DevType, TAG1);
                    StaticVariabes_div.log("DS"+DS+"Devno"+Dev+"devno"+devno+"dval"+dval+"inp"+inp, TAG1);

                    StaticVariabes_div.log("only light val"+lightval, TAG1);

                    if(DevType.equals(StaticVariabes_div.dev_typ_num)){

                        if(Dev.equals(devno)){

                                // powerstatus=false;

                                timeva=dval;
                                lightvalue=lightval;
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        if(priorityval.equals("1")){
                                            rb_pir.setChecked(true);
                                            rb_ligsensor.setChecked(false);
                                        }else{
                                            rb_pir.setChecked(false);
                                            rb_ligsensor.setChecked(true);
                                        }
                                        tv_lightvalue.setText(lightvalue);
                                        btn_timesetpir.setText(timeva);

                                    }
                                });

                                StaticVariabes_div.log("inside ds"+E2, TAG1);
                                switchstatus2("S"+E2);
                                Statusupdatelight(lightenval);

                                String E=String.valueOf(E2);

                                if((lightenval.equals("0"))||(E.equals("0"))|| (E.equals("1"))||(E.equals("4"))||E.equals("5")){
                                    invisible();
                                }else{
                                    visible();
                                }
                        }
                    }
                }
            }catch(Exception e){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                       // Toast.makeText(getActivity().getApplicationContext(),"Invalid data recieved",Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

    }
    /////////////////////////////////////////////////////////////////////////////
    public void Statusupdatelight(final String hardware){

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(hardware.equals("1")){
                    imglig.setImageResource(R.drawable.lightsens_on);
                }else{
                    imglig.setImageResource(R.drawable.light);
                }
            }
        });

    }

    private enum Statusset2 {
        S0, S1, S2, S3, S4, S5, S6, S7,
    }
    public void switchstatus2(String n){
        switch (Pir_Frag.Statusset2.valueOf(n)) {
            case S0:
                StaticVariabes_div.log("swb 0", TAG1);
                Statusupdate2(false);
                break;

            case S1:
                Statusupdate2(false);
                StaticVariabes_div.log("swb 1", TAG1);
                break;

            case S2:
                StaticVariabes_div.log("swb 2", TAG1);
                Statusupdate2(true);
                break;

            case S3:
                StaticVariabes_div.log("swb 3", TAG1);
                Statusupdate2(true);
                break;

            case S4:
                StaticVariabes_div.log("swb 4", TAG1);
                Statusupdate2(false);
                break;

            case S5:
                StaticVariabes_div.log("swb 5", TAG1);
                Statusupdate2(false);
                break;

            case S6:
                StaticVariabes_div.log("swb 6", TAG1);
                Statusupdate2(true);
                break;

            case S7:
                StaticVariabes_div.log("swb 7", TAG1);
                Statusupdate2(true);
                break;
            default:
                System.out.println("Not matching");
        }
    }

    public void Statusupdate2(final boolean hardware){

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                if(hardware){

                    if(strtext.equals("WPD1")) {
                        imgpir.setImageResource(R.drawable.direc_pir_sen_on);
                    }else{
                        imgpir.setImageResource(R.drawable.pir_sens_on);
                    }
                }else{
                    if(strtext.equals("WPD1")) {
                        imgpir.setImageResource(R.drawable.direc_pir_sen_off);
                    }else{
                        imgpir.setImageResource(R.drawable.motion);
                    }
                }
            }
        });
    }

    public void visible(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                lay_pir.setVisibility(View.VISIBLE);
                lay_lig.setVisibility(View.VISIBLE);
                rb_pir.setVisibility(View.VISIBLE);
                rb_ligsensor.setVisibility(View.VISIBLE);
                tv_priority.setVisibility(View.VISIBLE);
            }
        });
    }

    public void invisible(){
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                lay_pir.setVisibility(View.INVISIBLE);
                lay_lig.setVisibility(View.INVISIBLE);

                rb_pir.setVisibility(View.INVISIBLE);
                rb_ligsensor.setVisibility(View.INVISIBLE);
                tv_priority.setVisibility(View.INVISIBLE);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onResume() {
        super.onResume();
        Log.i(TAG1, "Setting screen name: " + name);
        //using tracker variable to set Screen Name
        mTracker.setScreenName(name);
        //sending the screen to analytics using ScreenViewBuilder() method
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    // Added by shreehail //
   /* public  ArrayList<Integer>  gridfill(String roomname){

        deviceList = new ArrayList<HashMap<Integer, String>>();


        *//*listname = new ArrayList<String>();
        listicon = new ArrayList<Integer>();
        listiconpressed=new  ArrayList<Integer>();*//*

        swbadap.open();
        String roomno=swbadap.getroomno(roomname);
        swbadap.close();

        mas_adap.open();
        if(roomno.equals("")){
            StaticVariabes_div.log("roomno null", TAG1);
            roomno=mas_adap.getroomno(roomname);
        }
        else{
            StaticVariabes_div.log("room selected"+"roomname"+roomname+"houseno"+"roomno"+roomno, TAG1);
        }
        mas_adap.close();


        cam_adap.open();
        if(roomno.equals("")){
            StaticVariabes_div.log("cam roomno null", TAG1);
            roomno=cam_adap.getroomno(roomname);
        }
        else{
            StaticVariabes_div.log("room selected"+"roomname"+roomname+"houseno"+"roomno"+roomno, TAG1);
        }
        cam_adap.close();


        ir_adap.open();
        if(roomno.equals("")){
            StaticVariabes_div.log("ir roomno null", TAG1);
            roomno=ir_adap.getroomno(roomname);
        }
        else{
            StaticVariabes_div.log("room selected"+"roomname"+roomname+"houseno"+"roomno"+roomno, TAG1);
        }
        ir_adap.close();


        room_num =roomno;
        StaticVariabes_div.room_n=roomno;


        if(StaticVariabes_div.loggeduser_type.equals("U")||StaticVariabes_div.loggeduser_type.equals("G")){

            listswbno = new ArrayList<String>();
            listswbmod = new ArrayList<String>();
            listswbdevnam = new ArrayList<String>();

            listswdno = new ArrayList<String>();
            listswdmod = new ArrayList<String>();
            listswddevnam = new ArrayList<String>();

            listrgbno = new ArrayList<String>();
            listrgbmod = new ArrayList<String>();
            listrgbdevnam = new ArrayList<String>();

            listdmrno = new ArrayList<String>();
            listdmrmod = new ArrayList<String>();
            listdmrdevnam = new ArrayList<String>();

            listcurtno = new ArrayList<String>();
            listcurtmod = new ArrayList<String>();
            listcurtdevnam = new ArrayList<String>();

            listpscno = new ArrayList<String> ();
            listpscmod = new ArrayList<String> ();
            listpscdevnam = new ArrayList<String> ();

            listplcno = new ArrayList<String> ();
            listplcmod = new ArrayList<String> ();
            listplcdevnam = new ArrayList<String> ();

            listirno = new ArrayList<String> ();
            listirmod = new ArrayList<String>() ;
            listirdevnam = new ArrayList<String>() ;

            listcamno = new ArrayList<String> ();
            listcammod = new ArrayList<String>() ;
            listcamdevnam = new ArrayList<String>() ;

            listaquno = new ArrayList<String> () ;
            listaqumod = new ArrayList<String> () ;
            listaqudevnam = new ArrayList<String> () ;

            listfmno = new ArrayList<String> ();
            listfmmod = new ArrayList<String> () ;
            listfmdevnam = new ArrayList<String> () ;

            listgskno = new ArrayList<String> ();
            listgskmod = new ArrayList<String> () ;
            listgskdevnam = new ArrayList<String> () ;

            listacno = new ArrayList<String> ();
            listacmod = new ArrayList<String> () ;
            listacdevnam= new ArrayList<String> () ;

            listgsrno = new ArrayList<String> ();
            listgsrmod = new ArrayList<String> () ;
            listgsrdevnam = new ArrayList<String> () ;

            listpirno = new ArrayList<String> ();
            listpirmod = new ArrayList<String> () ;
            listpirdevnam = new ArrayList<String> () ;

            listclbno = new ArrayList<String> ();
            listclbmod = new ArrayList<String> () ;
            listclbdevnam = new ArrayList<String> () ;

            listdlsno = new ArrayList<String> ();
            listdlsmod = new ArrayList<String> () ;
            listdlsdevnam = new ArrayList<String> () ;

            listwpcno = new ArrayList<String> ();
            listwpcmod = new ArrayList<String> () ;
            listwpcdevnam = new ArrayList<String> () ;

            listfanno = new ArrayList<String> ();
            listfanmod = new ArrayList<String> () ;
            listfandevnam = new ArrayList<String> () ;

            listsdgno = new ArrayList<String> ();
            listsdgmod = new ArrayList<String> () ;
            listsdgdevnam = new ArrayList<String> () ;

            listalexano = new ArrayList<String> ();
            listalexamod = new ArrayList<String> () ;
            listalexadevnam = new ArrayList<String> () ;

            listmirno = new ArrayList<String> ();
            listmirmod = new ArrayList<String> () ;
            listmirdevnam = new ArrayList<String> () ;

            //

            StaticVariabes_div.log("roomdevicesnumberaccessarr len" + StaticVariabes_div.roomdevicesnumberaccessarr.length, TAG1);

            for(int k=0;k<StaticVariabes_div.roomdevicesnumberaccessarr.length;k++){

                while (roomno.length() < 2)roomno = "0" + roomno;
                String arrroomno=StaticVariabes_div.roomnoarr[k];
                while (arrroomno.length() < 2)arrroomno = "0" + arrroomno;

                if(roomno.equals(arrroomno)){
                    switch(CombFrag.roomdevtypes.valueOf(StaticVariabes_div.roomdevicestypearr[k])) {
                        case SWB:
                            listswbno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listswbmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listswbdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case RGB:
                            listrgbno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listrgbmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listrgbdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case DMR:
                            listdmrno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listdmrmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listdmrdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case CUR:
                            listcurtno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listcurtmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listcurtdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case PSC:
                            listpscno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listpscmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listpscdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case PLC:
                            listplcno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listplcmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listplcdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case IR:
                            listirno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listirmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listirdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case CAM:
                            listcamno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listcammod.add(StaticVariabes_div.roommodletypearr[k]);
                            listcamdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case AQU:
                            listaquno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listaqumod.add(StaticVariabes_div.roommodletypearr[k]);
                            listaqudevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case FMD:
                            listfmno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listfmmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listfmdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case GSK:
                            listgskno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listgskmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listgskdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case ACR:
                            listacno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listacmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listacdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case GSR:
                            listgsrno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listgsrmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listgsrdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case FAN:
                            listfanno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listfanmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listfandevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case CLB:
                            listclbno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listclbmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listclbdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case DLS:
                            listdlsno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listdlsmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listdlsdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case PIR:
                            listpirno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listpirmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listpirdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case WPC:case WSM1:case WBM1: case WSS1: case WOTS:
                            listwpcno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listwpcmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listwpcdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case SDG:
                            listsdgno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listsdgmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listsdgdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case SWD:
                            listswdno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listswdmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listswddevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case ALXA:
                            listalexano.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listalexamod.add(StaticVariabes_div.roommodletypearr[k]);
                            listalexadevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        case mIR:
                            listmirno.add(StaticVariabes_div.roomdevicesnumberaccessarr[k]);
                            listmirmod.add(StaticVariabes_div.roommodletypearr[k]);
                            listmirdevnam.add(StaticVariabes_div.roomdevnamearr[k]);
                            break;
                        default:
                            break;

                    }


                }

            }


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
           *//* listname = new ArrayList<String>();
            listicon = new ArrayList<Integer>();*//*

/*//****************************************************************************************************


            int swbcount = listswbno.size();
            swbnoarr = new String[swbcount];
            swbtypearr = new String[swbcount];
            swbmodeltypearr = new String[swbcount];
            swbdevicenamearr = new String[swbcount];

            swbnoarr = listswbno.toArray( new String[listswbno.size()] );
            swbmodeltypearr = listswbmod.toArray( new String[listswbmod.size()] );
            swbdevicenamearr =listswbdevnam.toArray( new String[listswbmod.size()] );
            // Toast.makeText(this,"")
            StaticVariabes_div.log("swbdevicenamearr" + swbdevicenamearr.length, TAG1);
            StaticVariabes_div.log("swb count" + swbcount, TAG1);

            if (swbcount != 0) {
                StaticVariabes_div.Swbinit(swbcount, swbnoarr, swbmodeltypearr, swbdevicenamearr);
                listname.add("Swb");
                listicon.add(R.drawable.switchboard_grid_n);
                listiconpressed.add(R.drawable.switchboard_grid_p);

                HashMap<Integer,String> hashMap = new HashMap<Integer,String>();
                hashMap.put(R.drawable.switchboard_grid_n,"Swb");
            }


            int swdcount = listswdno.size();
            swdnoarr = new String[swdcount];
            swdtypearr = new String[swdcount];
            swdmodeltypearr = new String[swdcount];
            swddevicenamearr = new String[swdcount];

            swdnoarr = listswdno.toArray( new String[listswdno.size()] );
            swdmodeltypearr = listswdmod.toArray( new String[listswdmod.size()] );
            swbdevicenamearr =listswddevnam.toArray( new String[listswddevnam.size()] );

            StaticVariabes_div.log("swd count" + swdcount, TAG1);

            if (swdcount != 0) {
                StaticVariabes_div.Swdinit(swdcount, swdnoarr, swdmodeltypearr);
                listname.add("Swd");
                listicon.add(R.mipmap.bell_grid);
                listiconpressed.add(R.mipmap.bell_grid);
            }


            swbadap.close();



//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
            mas_adap.open();
            //................................................................................
            int rgbcount=listrgbno.size();
            rgbnoarr=new String[rgbcount];
            rgbtypearr=new String[rgbcount];
            rgbmodeltypearr=new String[rgbcount];
            rgbdevicenamearr = new String[rgbcount];

            rgbnoarr=listrgbno.toArray( new String[listrgbno.size()] );
            rgbmodeltypearr=listrgbmod.toArray( new String[listrgbmod.size()] );
            rgbdevicenamearr=listrgbdevnam.toArray( new String[listrgbdevnam.size()] );

            StaticVariabes_div.log("rgb count"+rgbcount, TAG1);
            StaticVariabes_div.log("rgb count" + rgbcount, TAG1);
            if (rgbcount != 0) {
                StaticVariabes_div.rgbbinit(rgbcount, rgbnoarr, rgbmodeltypearr, rgbdevicenamearr);
                listname.add("Rgb");
                listicon.add(R.drawable.rgb_grid_n);
                listiconpressed.add(R.drawable.rgb_grid_p);
            }
            //................................................................................

            int dmrcount=listdmrno.size();
            dimmrnoarr=new String[dmrcount];
            dimmrtypearr=new String[dmrcount];
            dimmrmodeltypearr=new String[dmrcount];
            dimmrdevicenamearr = new String[dmrcount];

            dimmrnoarr=listdmrno.toArray( new String[listdmrno.size()] );
            dimmrmodeltypearr=listdmrmod.toArray( new String[listdmrmod.size()] );
            dimmrdevicenamearr=listdmrdevnam.toArray( new String[listdmrdevnam.size()] );

            StaticVariabes_div.log("dimmr count"+dmrcount, TAG1);

            if (dmrcount != 0) {
                StaticVariabes_div.log("called from usr check"+dmrcount, TAG1);
                StaticVariabes_div.dmmrinit(dmrcount, dimmrnoarr, dimmrmodeltypearr, dimmrdevicenamearr);
                listname.add("Dmr");
                listicon.add(R.drawable.dimmer_grid_n);
                listiconpressed.add(R.drawable.dimmer_grid_p);
            }
            //................................................................................

            int curcount=listcurtno.size();
            curnoarr=new String[curcount];
            curtypearr=new String[curcount];
            curmodeltypearr=new String[curcount];
            curdevicenamearr = new String[curcount];

            curnoarr=listcurtno.toArray( new String[listcurtno.size()] );
            curmodeltypearr=listcurtmod.toArray( new String[listcurtmod.size()] );
            curdevicenamearr=listcurtdevnam.toArray( new String[listcurtdevnam.size()] );
            StaticVariabes_div.log("cur count"+curcount, TAG1);

            if (curcount != 0) {
                StaticVariabes_div.Curtinit(curcount, curnoarr, curmodeltypearr, curdevicenamearr);
                listname.add("Cur");
                listicon.add(R.drawable.curtain_grid_n);
                listiconpressed.add(R.drawable.curtain_grid_p);
            }
            for(int k=0;k<curmodeltypearr.length;k++){
                StaticVariabes_div.log("cur curmodeltypearr"+curmodeltypearr[k], TAG1);
            }
            for(int k=0;k<curnoarr.length;k++){
                StaticVariabes_div.log("cur curnoarr"+curnoarr[k], TAG1);
            }


            //................................................................................


            int fancount=listfanno.size();
            fannoarr=new String[fancount];
            fantypearr=new String[fancount];
            fanmodeltypearr=new String[fancount];
            fandevicenamearr = new String[fancount];

            fannoarr=listfanno.toArray( new String[listfanno.size()] );
            fanmodeltypearr=listfanmod.toArray( new String[listfanmod.size()] );
            fandevicenamearr=listfandevnam.toArray( new String[listfandevnam.size()] );

            StaticVariabes_div.log("fan count"+fancount, TAG1);

            if (fancount != 0) {
                StaticVariabes_div.faninit(fancount, fannoarr, fanmodeltypearr);
                listname.add("Fan");
                listicon.add(R.mipmap.fan_grid);
                listiconpressed.add(R.mipmap.fan_grid);
            }


            //..................................................................................................

            int geysercount=listgsrno.size();
            gysernoarr=new String[geysercount];
            gysertypearr=new String[geysercount];
            gysermodeltypearr=new String[geysercount];
            gyserdevicenamearr = new String[geysercount];

            gysernoarr=listgsrno.toArray( new String[listgsrno.size()] );
            gysermodeltypearr=listgsrmod.toArray( new String[listgsrmod.size()] );
            gyserdevicenamearr=listgsrdevnam.toArray( new String[listgsrdevnam.size()] );

            StaticVariabes_div.log("geysercount"+geysercount, TAG1);
            if (geysercount != 0) {
                StaticVariabes_div.geyserinit(geysercount, gysernoarr, gysermodeltypearr);
                listname.add("Gsr");
                listicon.add(R.mipmap.geyser_grid_n);
                listiconpressed.add(R.mipmap.geyser_grid_p);
            }

            //...........................................................................................
            int account=listacno.size();
            acnoarr=new String[account];
            actypearr=new String[account];
            acmodeltypearr=new String[account];
            acdevicenamearr = new String[account];

            acnoarr=listacno.toArray( new String[listacno.size()] );
            acmodeltypearr=listacmod.toArray( new String[listacmod.size()] );
            acdevicenamearr=listacdevnam.toArray( new String[listacdevnam.size()] );

            StaticVariabes_div.log("account"+account, TAG1);
            if (account != 0) {
                StaticVariabes_div.acinit(account, acnoarr, acmodeltypearr, acdevicenamearr);
                listname.add("Acr");
                listicon.add(R.mipmap.ac_grid_n);
                listiconpressed.add(R.mipmap.ac_grid_p);
            }
            //............................................................................

            int pircount=listpirno.size();
            pirnoarr=new String[pircount];
            pirtypearr=new String[pircount];
            pirmodeltypearr=new String[pircount];
            pirdevicenamearr = new String[pircount];

            pirnoarr=listpirno.toArray( new String[listpirno.size()] );
            pirmodeltypearr=listpirmod.toArray( new String[listpirmod.size()] );
            pirdevicenamearr=listpirdevnam.toArray( new String[listpirdevnam.size()] );

            StaticVariabes_div.log("pircount"+pircount, TAG1);
            if (pircount != 0) {
                StaticVariabes_div.pirinit(pircount, pirnoarr, pirmodeltypearr, pirdevicenamearr);
                /// StaticVariabes_div.pirinit(pircount,pirnoarr);
                listname.add("Pir");
                listicon.add(R.drawable.pir_grid_n);
                listiconpressed.add(R.drawable.pir_grid_p);
            }
            //............................................................................

            int clbcount=listclbno.size();
            clbnoarr=new String[clbcount];
            clbtypearr=new String[clbcount];
            clbmodeltypearr=new String[clbcount];
            clbdevicenamearr = new String[clbcount];

            clbnoarr=listclbno.toArray( new String[listclbno.size()] );
            clbmodeltypearr=listclbmod.toArray( new String[listclbmod.size()] );
            clbdevicenamearr=listacdevnam.toArray( new String[listclbdevnam.size()] );

            StaticVariabes_div.log("clbcount"+clbcount, TAG1);

            if (clbcount != 0) {
                StaticVariabes_div.clbinit(clbcount, clbnoarr, clbmodeltypearr);
                listname.add("Clb");
                listicon.add(R.mipmap.bell_grid_n);
                listiconpressed.add(R.mipmap.bell_grid_p);
            }
            //............................................................................
            int dlscount=listdlsno.size();
            dlsnoarr=new String[dlscount];
            dlstypearr=new String[dlscount];
            dlsmodeltypearr=new String[dlscount];
            dlsdevicenamearr = new String[dlscount];

            dlsnoarr=listdlsno.toArray( new String[listdlsno.size()] );
            dlsmodeltypearr=listdlsmod.toArray( new String[listdlsmod.size()] );
            dlsdevicenamearr=listacdevnam.toArray( new String[listdlsdevnam.size()] );

            StaticVariabes_div.log("dlscount"+dlscount, TAG1);

            if (dlscount != 0) {
                StaticVariabes_div.dlsinit(dlscount, dlsnoarr, dlsmodeltypearr);
                listname.add("Dls");
                listicon.add(R.mipmap.doorlock_grid_n);
                listiconpressed.add(R.mipmap.doorlock_grid_p);
            }

            //............................................................................

        *//*int fmcount=listfmno.size();
        StaticVariabes_div.log("fmcount"+fmcount, TAG1);
        fmnoarr=new String[fmcount];
        fmtypearr=new String[fmcount];
        fmmodeltypearr=new String[fmcount];

        fmnoarr=listfmno.toArray( new String[listfmno.size()] );
        fmmodeltypearr=listfmmod.toArray( new String[listfmmod.size()] );
        fmdevicenamearr=listacdevnam.toArray( new String[listfmdevnam.size()] );

        StaticVariabes_div.log("fmcount"+fmcount, TAG1);

        if (fmcount != 0) {
            StaticVariabes_div.fminit(fmcount, fmnoarr, fmmodeltypearr);
            listname.add("Fmd");
            listicon.add(R.mipmap.fm_grid_n);
            listiconpressed.add(R.mipmap.fm_grid_p);
        }
*//*
            int fmrcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "FMD");
            fmnoarr = new String[fmrcount];
            fmtypearr = new String[fmrcount];
            fmmodeltypearr = new String[fmrcount];
            fmdevicenamearr = new String[fmrcount];

            fmnoarr = mas_adap.getall_housenoroomnodevicetypename(fmrcount, roomno, "FMD");
            fmmodeltypearr = mas_adap.getall_devicetypemodel(fmrcount, roomno, "FMD");
            fmdevicenamearr = mas_adap.getall_roomnodevicenames(fmrcount, roomno, "FMD");
            StaticVariabes_div.log("fmrcount" + fmrcount, TAG1);

            if (fmrcount != 0) {
                StaticVariabes_div.fminit(fmrcount, fmnoarr, fmmodeltypearr, fmdevicenamearr);
                listname.add("Fmd");
                listicon.add(R.mipmap.fm_grid_n);
                listiconpressed.add(R.mipmap.fm_grid_p);
            }
            //...................................................................................................

            int projscrcount=listpscno.size();
            projscrnoarr=new String[projscrcount];
            projscrtypearr=new String[projscrcount];
            projscrmodeltypearr=new String[projscrcount];
            projscrdevicenamearr = new String[projscrcount];

            projscrnoarr=listpscno.toArray( new String[listpscno.size()] );
            projscrmodeltypearr=listpscmod.toArray( new String[listpscmod.size()] );
            projscrdevicenamearr=listpscdevnam.toArray( new String[listpscdevnam.size()] );

            StaticVariabes_div.log("projscrcount"+projscrcount, TAG1);
            if (projscrcount != 0) {
                StaticVariabes_div.projscrinit(projscrcount, projscrnoarr, projscrmodeltypearr,projscrdevicenamearr);
                listname.add("Psc");
                listicon.add(R.mipmap.projctr_grid_n);
                listiconpressed.add(R.mipmap.projctr_grid_p);
            }
            //...................................................................................................

            int projliftcount=listplcno.size();
            projliftnoarr=new String[projliftcount];
            projlifttypearr=new String[projliftcount];
            projliftmodeltypearr=new String[projliftcount];

            projliftnoarr=listplcno.toArray( new String[listplcno.size()] );
            projliftmodeltypearr=listplcno.toArray( new String[listplcno.size()] );
            StaticVariabes_div.log("projliftcount"+projliftcount, TAG1);
            if (projliftcount != 0) {
                StaticVariabes_div.projliftinit(projliftcount, projliftnoarr, projliftmodeltypearr);
                listname.add("Plc");
                listicon.add(R.mipmap.projectorlift_grid_n);
                listiconpressed.add(R.mipmap.projectorlift_grid_p);
            }
            //..................................................................................................

            int gskcount=listgskno.size();
            gsknoarr=new String[gskcount];
            gsktypearr=new String[gskcount];
            gskmodeltypearr=new String[gskcount];
            gskdevicenamearr = new String[gskcount];

            gsknoarr=listgskno.toArray( new String[listgskno.size()] );
            gskmodeltypearr=listgskmod.toArray( new String[listgskmod.size()] );
            gskdevicenamearr = listgskmod.toArray( new String[listgskmod.size()] );

            StaticVariabes_div.log("gskcount"+gskcount, TAG1);
            if (gskcount != 0) {
                StaticVariabes_div.gskinit(gskcount, gsknoarr, gskmodeltypearr,gskdevicenamearr);
                listname.add("Gsk");
                listicon.add(R.mipmap.sprinkler_grid_n);
                listiconpressed.add(R.mipmap.sprinkler_grid_p);
            }
            //...........................................................................................

            int sdgcount=listsdgno.size();
            smrtdognoarr=new String[sdgcount];
            smrtdogtypearr=new String[sdgcount];
            smrtdogmodeltypearr=new String[sdgcount];
            smrtdogdevicenamearr=new String[sdgcount];

            smrtdognoarr=listsdgno.toArray( new String[listsdgno.size()] );
            smrtdogmodeltypearr=listsdgmod.toArray( new String[listsdgmod.size()] );
            smrtdogdevicenamearr=listsdgmod.toArray( new String[listsdgmod.size()] );
            StaticVariabes_div.log("sdgcount"+sdgcount, TAG1);
            if (sdgcount != 0) {
                StaticVariabes_div.smrtdoginit(sdgcount, smrtdognoarr, smrtdogmodeltypearr, smrtdogdevicenamearr);
                listname.add("Sdg");
                listicon.add(R.drawable.dog_grid_n);
                listiconpressed.add(R.drawable.dog_grid_p);
            }

            //................................................................................................
            int alxacount=listalexano.size();
            alexanoarr=new String[alxacount];
            alexatypearr=new String[alxacount];
            alexamodeltypearr=new String[alxacount];
            alexadevicenamearr=new String[alxacount];

            alexanoarr=listalexano.toArray( new String[listalexano.size()] );
            alexamodeltypearr=listalexamod.toArray( new String[listalexamod.size()] );
            alexadevicenamearr=listalexamod.toArray( new String[listalexamod.size()] );
            StaticVariabes_div.log("alxacount"+alxacount, TAG1);
            if (alxacount != 0) {
                StaticVariabes_div.alexainit(alxacount, alexanoarr, alexamodeltypearr, alexadevicenamearr);
                listname.add("Alx");
                listicon.add(R.drawable.alxa_grid_n);
                listiconpressed.add(R.drawable.alxa_grid_p);
            }

            //................................................................................................
            //................................................................................................

            ///// Added by shreeshail ////// Begin ///

            int mircount=listmirno.size();
            mirnoarr=new String[mircount];
            mirtypearr=new String[mircount];
            mirmodeltypearr=new String[mircount];
            mirdevicenamearr=new String[mircount];

            mirnoarr=listmirno.toArray( new String[listmirno.size()] );
            mirmodeltypearr=listmirmod.toArray( new String[listmirmod.size()] );
            mirdevicenamearr=listmirmod.toArray( new String[listmirmod.size()] );
            StaticVariabes_div.log("mircount"+mircount, TAG1);
            if (mircount != 0) {
                StaticVariabes_div.mirinit(mircount, mirnoarr, mirmodeltypearr, mirdevicenamearr);
                listname.add(" ");
                listicon.add(R.drawable.alxa_grid_n);
                listiconpressed.add(R.drawable.alxa_grid_p);
            }

            /////////// End /////////////////////////

            //................................................................................................


            mas_adap.close();

        }else {


            StaticVariabes_div.log("gridfill else part Admin" , TAG1);
            swbadap.open();
            //  String roomno=swbadap.getroomno(roomname);
            int swbcount = swbadap.getCount_housenoroomnodevtypename(roomno, "SWB");
            swbnoarr = new String[swbcount];
            swbtypearr = new String[swbcount];
            swbmodeltypearr = new String[swbcount];
            swbdevicenamearr = new String[swbcount];

            swbnoarr = swbadap.getall_housenoroomnodevicetypename(swbcount, roomno, "SWB");
            swbmodeltypearr = swbadap.getall_devicetypemodel(swbcount, roomno, "SWB");
            swbdevicenamearr = swbadap.getall_devicenames(swbcount, roomno, "SWB");
            // Toast.makeText(this,"")
            StaticVariabes_div.log("swbdevicenamearr" + swbdevicenamearr.length, TAG1);
            StaticVariabes_div.log("swb count" + swbcount, TAG1);

            if (swbcount != 0) {
                StaticVariabes_div.Swbinit(swbcount, swbnoarr, swbmodeltypearr, swbdevicenamearr);
                listname.add("Swb");
                listicon.add(R.drawable.switchboard_grid_n);
                listiconpressed.add(R.drawable.switchboard_grid_p);
            }

            int swdcount = swbadap.getCount_housenoroomnodevtypename(roomno, "SWD");
            swdnoarr = new String[swdcount];
            swdtypearr = new String[swdcount];
            swdmodeltypearr = new String[swdcount];

            swdnoarr = swbadap.getall_housenoroomnodevicetypename(swdcount, roomno, "SWD");
            swdmodeltypearr = swbadap.getall_devicetypemodel(swdcount, roomno, "SWD");
            StaticVariabes_div.log("swd count" + swdcount, TAG1);

            if (swdcount != 0) {
                StaticVariabes_div.Swdinit(swdcount, swdnoarr, swdmodeltypearr);
                listname.add("Swd");
                listicon.add(R.mipmap.bell_grid);
                listiconpressed.add(R.mipmap.bell_grid);
            }

            swbadap.close();


            mas_adap.open();
            //................................................................................
            int rgbcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "RGB");
            rgbnoarr = new String[rgbcount];
            rgbtypearr = new String[rgbcount];
            rgbmodeltypearr = new String[rgbcount];
            rgbdevicenamearr = new String[rgbcount];

            rgbnoarr = mas_adap.getall_housenoroomnodevicetypename(rgbcount, roomno, "RGB");
            rgbmodeltypearr = mas_adap.getall_devicetypemodel(rgbcount, roomno, "RGB");
            rgbdevicenamearr = mas_adap.getall_roomnodevicenames(rgbcount, roomno, "RGB");

            StaticVariabes_div.log("rgb count" + rgbcount, TAG1);
            if (rgbcount != 0) {
                StaticVariabes_div.rgbbinit(rgbcount, rgbnoarr, rgbmodeltypearr, rgbdevicenamearr);
                listname.add("Rgb");
                listicon.add(R.drawable.rgb_grid_n);
                listiconpressed.add(R.drawable.rgb_grid_p);
            }
            //................................................................................
            int dmrcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "DMR");
            dimmrnoarr = new String[dmrcount];
            dimmrtypearr = new String[dmrcount];
            dimmrmodeltypearr = new String[dmrcount];
            dimmrdevicenamearr = new String[dmrcount];

            dimmrnoarr = mas_adap.getall_housenoroomnodevicetypename(dmrcount, roomno, "DMR");
            dimmrmodeltypearr = mas_adap.getall_devicetypemodel(dmrcount, roomno, "DMR");
            dimmrdevicenamearr = mas_adap.getall_roomnodevicenames(dmrcount, roomno, "DMR");
            StaticVariabes_div.log("dimmr count" + dmrcount, TAG1);

            if (dmrcount != 0) {
                StaticVariabes_div.dmmrinit(dmrcount, dimmrnoarr, dimmrmodeltypearr, dimmrdevicenamearr);
                listname.add("Dmr");
                listicon.add(R.drawable.dimmer_grid_n);
                listiconpressed.add(R.drawable.dimmer_grid_p);
            }
            //................................................................................

            int curcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "CUR");
            curnoarr = new String[curcount];
            curtypearr = new String[curcount];
            curmodeltypearr = new String[curcount];
            curdevicenamearr = new String[curcount];

            curnoarr = mas_adap.getall_housenoroomnodevicetypename(curcount, roomno, "CUR");
            curmodeltypearr = mas_adap.getall_devicetypemodel(curcount, roomno, "CUR");
            curdevicenamearr = mas_adap.getall_roomnodevicenames(curcount, roomno, "CUR");
            StaticVariabes_div.log("cur count" + curcount, TAG1);

            if (curcount != 0) {
                StaticVariabes_div.Curtinit(curcount, curnoarr, curmodeltypearr, curdevicenamearr);
                listname.add("Cur");
                listicon.add(R.drawable.curtain_grid_n);
                listiconpressed.add(R.drawable.curtain_grid_p);
            }


            //................................................................................

            int fancount = mas_adap.getCount_housenoroomnodevtypename(roomno, "FAN");
            fannoarr = new String[fancount];
            fantypearr = new String[fancount];
            fanmodeltypearr = new String[fancount];
            fandevicenamearr = new String[fancount];

            fannoarr = mas_adap.getall_housenoroomnodevicetypename(fancount, roomno, "FAN");
            fanmodeltypearr = mas_adap.getall_devicetypemodel(fancount, roomno, "FAN");
            fandevicenamearr = mas_adap.getall_roomnodevicenames(fancount, roomno, "FAN");
            StaticVariabes_div.log("fan count" + fancount, TAG1);

            if (fancount != 0) {
                StaticVariabes_div.faninit(fancount, fannoarr, fanmodeltypearr);
                listname.add("Fan");
                listicon.add(R.mipmap.fan_grid);
                listiconpressed.add(R.mipmap.fan_grid);
            }


            //..................................................................................................
            int geysercount = mas_adap.getCount_housenoroomnodevtypename(roomno, "GSR");
            gysernoarr = new String[geysercount];
            gysertypearr = new String[geysercount];
            gysermodeltypearr = new String[geysercount];
            gyserdevicenamearr = new String[geysercount];

            gysernoarr = mas_adap.getall_housenoroomnodevicetypename(geysercount, roomno, "GSR");
            gysermodeltypearr = mas_adap.getall_devicetypemodel(geysercount, roomno, "GSR");
            gyserdevicenamearr = mas_adap.getall_roomnodevicenames(fancount, roomno, "GSR");
            StaticVariabes_div.log("geysercount" + geysercount, TAG1);

            if (geysercount != 0) {
                StaticVariabes_div.geyserinit(geysercount, gysernoarr, gysermodeltypearr);
                listname.add("Gsr");
                listicon.add(R.mipmap.geyser_grid_n);
                listiconpressed.add(R.mipmap.geyser_grid_p);
            }
            //...........................................................................................
            int account = mas_adap.getCount_housenoroomnodevtypename(roomno, "ACR");
            acnoarr = new String[account];
            actypearr = new String[account];
            acmodeltypearr = new String[account];
            acdevicenamearr = new String[account];

            acnoarr = mas_adap.getall_housenoroomnodevicetypename(account, roomno, "ACR");
            acmodeltypearr = mas_adap.getall_devicetypemodel(account, roomno, "ACR");
            acdevicenamearr = mas_adap.getall_roomnodevicenames(account, roomno, "ACR");
            StaticVariabes_div.log("account" + account, TAG1);

            if (account != 0) {
                StaticVariabes_div.acinit(account, acnoarr, acmodeltypearr, acdevicenamearr);
                listname.add("Acr");
                listicon.add(R.mipmap.ac_grid_n);
                listiconpressed.add(R.mipmap.ac_grid_p);
            }
            for (int k = 0; k < acmodeltypearr.length; k++) {
                StaticVariabes_div.log("ac acmodeltypearr" + acmodeltypearr[k], TAG1);
            }
            for (int k = 0; k < acnoarr.length; k++) {
                StaticVariabes_div.log("ac acnoarr" + acnoarr[k], TAG1);
            }
            //............................................................................

            int pircount = mas_adap.getCount_housenoroomnodevtypename(roomno, "PIR");
            pirnoarr = new String[pircount];
            pirtypearr = new String[pircount];
            pirmodeltypearr = new String[pircount];
            pirdevicenamearr = new String[pircount];

            pirnoarr = mas_adap.getall_housenoroomnodevicetypename(pircount, roomno, "PIR");
            pirmodeltypearr = mas_adap.getall_devicetypemodel(pircount, roomno, "PIR");
            pirdevicenamearr = mas_adap.getall_roomnodevicenames(pircount, roomno, "PIR");
            StaticVariabes_div.log("pircount test" + pircount, TAG1);

            if (pircount != 0) {
                StaticVariabes_div.pirinit(pircount, pirnoarr, pirmodeltypearr, pirdevicenamearr);
                /// StaticVariabes_div.pirinit(pircount,pirnoarr);
                listname.add("Pir");
                listicon.add(R.drawable.pir_grid_n);
                listiconpressed.add(R.drawable.pir_grid_p);
            }
            //............................................................................

            int clbcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "CLB");
            clbnoarr = new String[clbcount];
            clbtypearr = new String[clbcount];
            clbmodeltypearr = new String[clbcount];

            clbnoarr = mas_adap.getall_housenoroomnodevicetypename(clbcount, roomno, "CLB");
            clbmodeltypearr = mas_adap.getall_devicetypemodel(clbcount, roomno, "CLB");
            clbdevicenamearr = mas_adap.getall_roomnodevicenames(clbcount, roomno, "CLB");
            StaticVariabes_div.log("clbcount" + clbcount, TAG1);

            if (clbcount != 0) {
                StaticVariabes_div.clbinit(clbcount, clbnoarr, clbmodeltypearr);
                listname.add("Clb");
                listicon.add(R.mipmap.bell_grid_n);
                listiconpressed.add(R.mipmap.bell_grid_p);
            }
            //............................................................................

            int dlscount = mas_adap.getCount_housenoroomnodevtypename(roomno, "DLS");
            dlsnoarr = new String[dlscount];
            dlstypearr = new String[dlscount];
            dlsmodeltypearr = new String[dlscount];

            dlsnoarr = mas_adap.getall_housenoroomnodevicetypename(dlscount, roomno, "DLS");
            dlsmodeltypearr = mas_adap.getall_devicetypemodel(dlscount, roomno, "DLS");
            StaticVariabes_div.log("dlscount" + dlscount, TAG1);

            if (dlscount != 0) {
                StaticVariabes_div.dlsinit(dlscount, dlsnoarr, dlsmodeltypearr);
                listname.add("Dls");
                listicon.add(R.mipmap.doorlock_grid_n);
                listiconpressed.add(R.mipmap.doorlock_grid_p);
            }
            //............................................................................

        *//*int fmcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "FMD");
        StaticVariabes_div.log("fmcount" + fmcount, TAG1);

        fmnoarr = new String[fmcount];
        fmtypearr = new String[fmcount];
        fmmodeltypearr = new String[fmcount];

        fmnoarr = mas_adap.getall_housenoroomnodevicetypename(fmcount, roomno, "FMD");
        fmmodeltypearr = mas_adap.getall_devicetypemodel(fmcount, roomno, "FMD");
        StaticVariabes_div.log("fmcount" + fmcount, TAG1);

        if (fmcount != 0) {
            StaticVariabes_div.fminit(fmcount, fmnoarr, fmmodeltypearr);
            listname.add("Fmd");
            listicon.add(R.mipmap.fm_grid_n);
            listiconpressed.add(R.mipmap.fm_grid_p);
        }

        for (int k = 0; k < fmmodeltypearr.length; k++) {
            StaticVariabes_div.log("fm fmmodeltypearr" + fmmodeltypearr[k], TAG1);
        }
        for (int k = 0; k < fmnoarr.length; k++) {
            StaticVariabes_div.log("fm fmnoarr" + fmnoarr[k], TAG1);
        }*//*
            //...................................................................................................

            int projscrcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "PSC");
            projscrnoarr = new String[projscrcount];
            projscrtypearr = new String[projscrcount];
            projscrmodeltypearr = new String[projscrcount];
            projscrdevicenamearr = new String[projscrcount];

            projscrnoarr = mas_adap.getall_housenoroomnodevicetypename(projscrcount, roomno, "PSC");
            projscrmodeltypearr = mas_adap.getall_devicetypemodel(projscrcount, roomno, "PSC");
            projscrdevicenamearr = mas_adap.getall_roomnodevicenames(projscrcount, roomno, "PSC");

            StaticVariabes_div.log("projscrcount" + projscrcount, TAG1);

            if (projscrcount != 0) {
                StaticVariabes_div.projscrinit(projscrcount, projscrnoarr, projscrmodeltypearr,projscrdevicenamearr);
                listname.add("Psc");
                listicon.add(R.mipmap.projctr_grid_n);
                listiconpressed.add(R.mipmap.projctr_grid_p);
            }
            //...................................................................................................

            int projliftcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "PLC");
            projliftnoarr = new String[projliftcount];
            projlifttypearr = new String[projliftcount];
            projliftmodeltypearr = new String[projliftcount];

            projliftnoarr = mas_adap.getall_housenoroomnodevicetypename(projliftcount, roomno, "PLC");
            projliftmodeltypearr = mas_adap.getall_devicetypemodel(projliftcount, roomno, "PLC");
            StaticVariabes_div.log("projliftcount" + projliftcount, TAG1);

            if (projliftcount != 0) {
                StaticVariabes_div.projliftinit(projliftcount, projliftnoarr, projliftmodeltypearr);
                listname.add("Plc");
                listicon.add(R.mipmap.projectorlift_grid_n);
                listiconpressed.add(R.mipmap.projectorlift_grid_p);
            }
            //..................................................................................................

            int gskcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "GSK");
            gsknoarr = new String[gskcount];
            gsktypearr = new String[gskcount];
            gskmodeltypearr = new String[gskcount];
            gskdevicenamearr = new String[gskcount];

            gsknoarr = mas_adap.getall_housenoroomnodevicetypename(gskcount, roomno, "GSK");
            gskmodeltypearr = mas_adap.getall_devicetypemodel(gskcount, roomno, "GSK");
            gskdevicenamearr = mas_adap.getall_roomnodevicenames(gskcount, roomno, "GSK");

            StaticVariabes_div.log("gskcount" + gskcount, TAG1);

            if (gskcount != 0) {
                StaticVariabes_div.gskinit(gskcount, gsknoarr, gskmodeltypearr,gskdevicenamearr);
                listname.add("Gsk");
                listicon.add(R.mipmap.sprinkler_grid_n);
                listiconpressed.add(R.mipmap.sprinkler_grid_p);
            }

            //..................................................................................................
            int smrtdogcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "SDG");
            smrtdognoarr = new String[smrtdogcount];
            smrtdogtypearr = new String[smrtdogcount];
            smrtdogmodeltypearr = new String[smrtdogcount];
            smrtdogdevicenamearr = new String[smrtdogcount];

            smrtdognoarr = mas_adap.getall_housenoroomnodevicetypename(smrtdogcount, roomno, "SDG");
            smrtdogmodeltypearr = mas_adap.getall_devicetypemodel(smrtdogcount, roomno, "SDG");
            smrtdogdevicenamearr = mas_adap.getall_roomnodevicenames(smrtdogcount, roomno, "SDG");
            StaticVariabes_div.log("smrtdogcount" + smrtdogcount, TAG1);

            if (smrtdogcount != 0) {
                StaticVariabes_div.smrtdoginit(smrtdogcount, smrtdognoarr, smrtdogmodeltypearr, smrtdogdevicenamearr);
                listname.add("Sdg");
                listicon.add(R.drawable.dog_grid_n);
                listiconpressed.add(R.drawable.dog_grid_p);
            }

            //...........................................................................................

            int alexacount = mas_adap.getCount_housenoroomnodevtypename(roomno, "ALXA");
            alexanoarr = new String[alexacount];
            alexatypearr = new String[alexacount];
            alexamodeltypearr = new String[alexacount];
            alexadevicenamearr = new String[alexacount];

            alexanoarr = mas_adap.getall_housenoroomnodevicetypename(alexacount, roomno, "ALXA");
            alexamodeltypearr = mas_adap.getall_devicetypemodel(alexacount, roomno, "ALXA");
            alexadevicenamearr = mas_adap.getall_roomnodevicenames(alexacount, roomno, "ALXA");
            StaticVariabes_div.log("alexacount" + smrtdogcount, TAG1);

            if (alexacount != 0) {
                StaticVariabes_div.alexainit(alexacount, alexanoarr, alexamodeltypearr, alexadevicenamearr);
                listname.add("Alx");
                listicon.add(R.drawable.alxa_grid_n);
                listiconpressed.add(R.drawable.alxa_grid_p);
            }

            //...........................................................................................

            ///// Added by shreeshail ////// Begin ///

            int mircount = mas_adap.getCount_housenoroomnodevtypename(roomno, "mIR");
            mirnoarr = new String[mircount];
            mirtypearr = new String[mircount];
            mirmodeltypearr = new String[mircount];
            mirdevicenamearr = new String[mircount];

            mirnoarr = mas_adap.getall_housenoroomnodevicetypename(mircount, roomno, "mIR");
            mirmodeltypearr = mas_adap.getall_devicetypemodel(mircount, roomno, "mIR");
            mirdevicenamearr = mas_adap.getall_roomnodevicenames(mircount, roomno, "mIR");
            StaticVariabes_div.log("mircount" + smrtdogcount, TAG1);

            if (mircount != 0) {
                StaticVariabes_div.mirinit(mircount, mirnoarr, mirmodeltypearr, mirdevicenamearr);
                listname.add("mIR");
                listicon.add(R.drawable.mir_n);
                listiconpressed.add(R.drawable.mir_p);
            }


            /////////// End /////////////////////////

            //...........................................................................................

            int fmrcount = mas_adap.getCount_housenoroomnodevtypename(roomno, "FMD");
            fmnoarr = new String[fmrcount];
            fmtypearr = new String[fmrcount];
            fmmodeltypearr = new String[fmrcount];
            fmdevicenamearr = new String[fmrcount];

            fmnoarr = mas_adap.getall_housenoroomnodevicetypename(fmrcount, roomno, "FMD");
            fmmodeltypearr = mas_adap.getall_devicetypemodel(fmrcount, roomno, "FMD");
            fmdevicenamearr = mas_adap.getall_roomnodevicenames(fmrcount, roomno, "FMD");
            StaticVariabes_div.log("fmrcount" + smrtdogcount, TAG1);

            if (fmrcount != 0) {
                StaticVariabes_div.fminit(fmrcount, fmnoarr, fmmodeltypearr, fmdevicenamearr);
                listname.add("Fmd");
                listicon.add(R.mipmap.fm_grid_n);
                listiconpressed.add(R.mipmap.fm_grid_p);
            }

            //...........................................................................................

            mas_adap.close();
        }

        Pressedgridimgs=listiconpressed.toArray(new Integer[listiconpressed.size()]);
        Normalgridimgs=listicon.toArray(new Integer[listicon.size()]);
        return listicon;
    }
*/

    //*********************//

    // Added by shreeshail //
    private Integer getdeviceimage(String typestr){
        if(typestr.contains("S0") || typestr.contains("s0"))
            typestr = "S010";

        switch (typestr){
            case "S010": return R.mipmap.switchboard_grid_p;
            case "DMR1": return R.mipmap.dimmer_grid_p;
            case "RGB1": return R.mipmap.rgb_grid_p;
            case "CLS1": return R.mipmap.switchboard_grid_p;
            case "CRS1": return R.mipmap.switchboard_grid_p;
            case "CLNR": return R.mipmap.switchboard_grid_p;
            case "CLSH": return R.mipmap.switchboard_grid_p;
            case "SDG1": return R.mipmap.dog_grid_p;
            case "PSC1": return R.mipmap.projctr_grid_p;
            case "GSK1": return R.mipmap.sprinkler_grid_p;
        }
        return R.mipmap.switchboard_grid_p;
    }
    //********************//

    //# ============================================================================== #//
    // LISTING ALL SELECTED ROOM DEVICES //
    public void popup_all_devices_list(String selectedroomname){


        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_pannel_room,null,false);
        popupWindow_two = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow_two.setAnimationStyle(R.style.PauseDialogAnimation);
        popupWindow_two.showAtLocation(popupview, Gravity.CENTER,0,0);

        TextView titletxt = (TextView) popupview.findViewById(R.id.titletxt);
        //titletxt.setText(""+android_gridview_text.getText().toString());
        titletxt.setText(""+selectedroomname);

        int CurrentRoomNo=0;

        deviceList = new ArrayList<HashMap<Integer, String>>();

        try{
            houseDB=new HouseConfigurationAdapter(getActivity());
            houseDB.open();			//opening house database
        }catch(Exception e){
            e.printStackTrace();
        }

        String Current_WRLS_DevName = StaticVariabes_div.devtyp;
        //getting current room number
        CurrentRoomNo=houseDB.CurrentRoomNumber(selectedroomname);
                       /* HashMap<String,String> map=houseDB.getMoodDevices_userdevicenames(CurrentRoomNo,Current_WRLS_DevName);
                        HashMap<String,String> map_devty=houseDB.getMoodDevices(CurrentRoomNo,Current_WRLS_DevName);*/

        deviceListArrays = houseDB.getDeviceListArray(CurrentRoomNo,Current_WRLS_DevName);
                           /* for (Map.Entry<String, String> mapEntry : map.entrySet())
                            {
                                HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
                                String key = mapEntry.getKey();

                                String typestr = map_devty.get(key);

                                Integer img = getdeviceimage(typestr);

                                String value = mapEntry.getValue();
                                hashMap.put(img,value);
                                deviceList.add(hashMap);
                            }*/
        GridView gridView = (GridView) popupview.findViewById(R.id.gridView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DeviceListArray deviceListArrayobj = deviceListArrays.get(i);

                // Toast.makeText(getActivity(),deviceListArrayobj.getDeviceShortName(),Toast.LENGTH_SHORT).show();

                if(deviceListArrayobj.getDeviceShortName().contains("S0") || deviceListArrayobj.getDeviceShortName().contains("s0"))
                    SwitchPopup(view,i,deviceListArrayobj);
                else if(deviceListArrayobj.getDeviceShortName().contains("RGB1"))
                    rgbPopupShow(view,i,deviceListArrayobj);
                else if(deviceListArrayobj.getDeviceShortName().contains("CLS1") || deviceListArrayobj.getDeviceShortName().contains("CRS1") || deviceListArrayobj.getDeviceShortName().contains("CLSH") || deviceListArrayobj.getDeviceShortName().contains("CLNR") || deviceListArrayobj.getDeviceShortName().contains("PSC1"))
                    curtainPopupShow(view,i,deviceListArrayobj);
                else if(deviceListArrayobj.getDeviceShortName().contains("GSK1") )
                    GardenSprinklerPopupShow(view,i,deviceListArrayobj);
                else if(deviceListArrayobj.getDeviceShortName().contains("DMR1") )
                    DimmerPopupShow(view,i,deviceListArrayobj);

            }
        });
        houseDB.close();

        TextView nodevice = (TextView) popupview.findViewById(R.id.nodevice);


        if(deviceListArrays.size()>0)
            nodevice.setVisibility(View.GONE);
        else
            nodevice.setVisibility(View.VISIBLE);


        Custom_device_GridViewActivity customGridViewActivity = new Custom_device_GridViewActivity(getActivity(), deviceListArrays);
        gridView.setAdapter(customGridViewActivity);

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow_two.dismiss();
            }
        });
        TextView ok = (TextView) popupview.findViewById(R.id.ok);
        ok.setText("Home");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow_one!=null && popupWindow_one.isShowing())
                    popupWindow_one.dismiss();
                if(popupWindow_two!=null && popupWindow_two.isShowing())
                    popupWindow_two.dismiss();
            }
        });
    }
    //# ============================================================================== #//


    //# ============================================================================== #//
    // METHOD'S TO LIST ALL PIR CONFIGURED DEVICES //
    // SwitchBoard //
    private void confdevicesSWB(View popupview,String ec, TextView roomnameval, TextView devicenameval, TextView switchonval, TextView switchoffval, TextView switchonval_idle, TextView switchoffval_idle){

        try{

            RelativeLayout swbopertion = (RelativeLayout) popupview.findViewById(R.id.swbopertion);
            swbopertion.setVisibility(View.VISIBLE);

            RelativeLayout curtainopertion = (RelativeLayout) popupview.findViewById(R.id.curtainopertion);
            curtainopertion.setVisibility(View.GONE);

            RelativeLayout otheroperation = (RelativeLayout) popupview.findViewById(R.id.otheroperation);
            otheroperation.setVisibility(View.GONE);

            RelativeLayout rgboperation = (RelativeLayout) popupview.findViewById(R.id.rgboperation);
            rgboperation.setVisibility(View.GONE);

            RelativeLayout dimmeropertion = (RelativeLayout) popupview.findViewById(R.id.dimmeropertion);
            dimmeropertion.setVisibility(View.GONE);

            switchonval.setText("");
            switchoffval.setText("");
            switchonval_idle.setText("");
            switchoffval_idle.setText("");


            PIR_WirelessDB_Data pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"101");

            String switchon_motion="",switchon_idle="",switchoff_motion="",switchoff_idle="";
            if(pir_wirelessDB_data!=null){

                roomnameval.setText(pir_wirelessDB_data.getb());
                devicenameval.setText(pir_wirelessDB_data.getec());


                String eaarray = pir_wirelessDB_data.getea();
                String[] ea;
                if(eaarray.contains(",")) {
                    ea = eaarray.split(",");
                    for (int k=0;k<ea.length;k++){
                        long temp = Long.parseLong(ea[k]);
                        if(temp>=201 && temp<=210)
                            switchon_motion = switchon_motion+ea[k].substring(ea[k].length()-1)+",";
                        else if(temp>=301 && temp<=310)
                            switchoff_motion = switchoff_motion+ea[k].substring(ea[k].length()-1)+",";
                        else if(temp==722)
                            switchon_motion = switchon_motion+"Fan1"+",";
                        else if(temp==723)
                            switchoff_motion = switchoff_motion+"Fan1"+",";
                        else if(temp==711 || temp==712 || temp==713 || temp==714) {
                            String fanspped = String.valueOf(temp);
                            switchon_motion = switchon_motion + "Fan1("+fanspped.substring(fanspped.length()-1)+")" + ",";
                        }

                    }
                }else {
                    long temp = Long.parseLong(eaarray);
                    if(temp>=201 && temp<=210)
                        switchon_motion = switchon_motion+eaarray.substring(eaarray.length()-1)+",";
                    else if(temp>=301 && temp<=310)
                        switchoff_motion = switchoff_motion+eaarray.substring(eaarray.length()-1)+",";
                    else if(temp==722)
                        switchon_motion = switchon_motion+"Fan1"+",";
                    else if(temp==723)
                        switchoff_motion = switchoff_motion+"Fan1"+",";
                    else if(temp==711 || temp==712 || temp==713 || temp==714) {
                        String fanspped = String.valueOf(temp);
                        switchon_motion = switchon_motion + "Fan1("+fanspped.substring(fanspped.length()-1)+")" + ",";
                    }
                }
                if (switchon_motion.endsWith(",")) {
                    switchon_motion = switchon_motion.substring(0, switchon_motion.length() - 1) + " ";
                } if (switchoff_motion.endsWith(",")) {
                    switchoff_motion = switchoff_motion.substring(0, switchoff_motion.length() - 1) + " ";
                }
                switchonval.setText(switchon_motion);
                switchoffval.setText(switchoff_motion);


            }



            PIR_WirelessDB_Data pir_wirelessDB_data2= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"102");


            if(pir_wirelessDB_data2!=null){

                roomnameval.setText(pir_wirelessDB_data2.getb());
                devicenameval.setText(pir_wirelessDB_data2.getec());


                String eaarray = pir_wirelessDB_data2.getea();
                String[] ea;
                if(eaarray.contains(",")) {
                    ea = eaarray.split(",");
                    for (int k=0;k<ea.length;k++){
                        long temp = Long.parseLong(ea[k]);
                        if(temp>=201 && temp<=210)
                            switchon_idle = switchon_idle+ea[k].substring(ea[k].length()-1)+",";
                        else if(temp>=301 && temp<=310)
                            switchoff_idle = switchoff_idle+ea[k].substring(ea[k].length()-1)+",";
                        else if(temp==722)
                            switchon_idle = switchon_idle+"Fan1"+",";
                        else if(temp==723)
                            switchoff_idle = switchoff_idle+"Fan1"+",";
                        else if(temp==711 || temp==712 || temp==713 || temp==714) {
                            String fanspped = String.valueOf(temp);
                            switchon_idle = switchon_idle + "Fan1("+fanspped.substring(fanspped.length()-1)+")" + ",";
                        }

                    }
                }else {
                    long temp = Long.parseLong(eaarray);
                    if(temp>=201 && temp<=210)
                        switchon_idle = switchon_idle+eaarray.substring(eaarray.length()-1)+",";
                    else if(temp>=301 && temp<=310)
                        switchoff_idle = switchoff_idle+eaarray.substring(eaarray.length()-1)+",";
                    else if(temp==722)
                        switchon_idle = switchon_idle+"Fan1"+",";
                    else if(temp==723)
                        switchoff_idle = switchoff_idle+"Fan1"+",";
                    else if(temp==711 || temp==712 || temp==713 || temp==714) {
                        String fanspped = String.valueOf(temp);
                        switchon_idle = switchon_idle + "Fan1("+fanspped.substring(fanspped.length()-1)+")" + ",";
                    }
                }
                if (switchon_idle.endsWith(",")) {
                    switchon_idle = switchon_idle.substring(0, switchon_idle.length() - 1) + " ";
                } if (switchoff_idle.endsWith(",")) {
                    switchoff_idle = switchoff_idle.substring(0, switchoff_idle.length() - 1) + " ";
                }
                switchonval_idle.setText(switchon_idle);
                switchoffval_idle.setText(switchoff_idle);


            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    // RGB //
    private void confdevicesRGB(View popupview, String ec, TextView roomnameval, TextView devicenameval, TextView switchonval, TextView switchoffval, TextView switchonval_idle, TextView switchoffval_idle) {

        RelativeLayout swbopertion = (RelativeLayout) popupview.findViewById(R.id.swbopertion);
        swbopertion.setVisibility(View.GONE);

        RelativeLayout curtainopertion = (RelativeLayout) popupview.findViewById(R.id.curtainopertion);
        curtainopertion.setVisibility(View.GONE);

        RelativeLayout otheroperation = (RelativeLayout) popupview.findViewById(R.id.otheroperation);
        otheroperation.setVisibility(View.GONE);

        RelativeLayout rgboperation = (RelativeLayout) popupview.findViewById(R.id.rgboperation);
        rgboperation.setVisibility(View.VISIBLE);

        RelativeLayout dimmeropertion = (RelativeLayout) popupview.findViewById(R.id.dimmeropertion);
        dimmeropertion.setVisibility(View.GONE);

        TextView rgbpowerval,rgbcolorval,rgbeffectsval,rgbbrightnessval,rgbtransactionval;
        TextView rgbpowerval_idle,rgbcolorval_idle,rgbeffectsval_idle,rgbbrightnessval_idle,rgbtransactionval_idle;

        rgbpowerval = (TextView) popupview.findViewById(R.id.rgbpowerval);
        rgbcolorval = (TextView) popupview.findViewById(R.id.rgbcolorval);
        rgbeffectsval = (TextView) popupview.findViewById(R.id.rgbeffectsval);
        rgbbrightnessval = (TextView) popupview.findViewById(R.id.rgbbrightnessval);
        rgbtransactionval = (TextView) popupview.findViewById(R.id.rgbtransactionval);

        rgbpowerval_idle = (TextView) popupview.findViewById(R.id.rgbpowerval_idle);
        rgbcolorval_idle = (TextView) popupview.findViewById(R.id.rgbcolorval_idle);
        rgbeffectsval_idle = (TextView) popupview.findViewById(R.id.rgbeffectsval_idle);
        rgbbrightnessval_idle = (TextView) popupview.findViewById(R.id.rgbbrightnessval_idle);
        rgbtransactionval_idle = (TextView) popupview.findViewById(R.id.rgbtransactionval_idle);




        String rgbpower = "",rgbcolor = "",rgbeffect= "",rgbbrightness = "",rgbtrasaction = "";
        //String rgbpower_idle = "",rgbcolor_idle = "",rgbeffect_idle = "",rgbbrightness_idle = "",rgbtrasaction_idle = "";


        PIR_WirelessDB_Data pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"101");


        if(pir_wirelessDB_data!=null){

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());


            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase(RGB_ON_CMD))
                        rgbpower = "ON";
                    else if(ea[k].equalsIgnoreCase(RGB_OFF_CMD))
                        rgbpower = "OFF";
                    else if(ea[k].equalsIgnoreCase(RGB_FLASH_CMD))
                        rgbeffect = "FLASH";
                    else if(ea[k].equalsIgnoreCase(RGB_STROBE_CMD))
                        rgbeffect = "STROBE";
                    else if(ea[k].equalsIgnoreCase(RGB_SMOOTH_CMD))
                        rgbeffect = "SMOOTH";
                    else if(ea[k].equalsIgnoreCase(RGB_FADE_CMD))
                        rgbeffect = "FADE";
                    else if(ea[k].equalsIgnoreCase(RGB_RED_CMD))
                        rgbcolor = "RED";
                    else if(ea[k].equalsIgnoreCase(RGB_GREEN_CMD))
                        rgbcolor = "GREEN";
                    else if(ea[k].equalsIgnoreCase(RGB_BLUE_CMD))
                        rgbcolor = "BLUE";
                    else if(ea[k].equalsIgnoreCase(RGB_WHITE_CMD))
                        rgbcolor = "WHITE";
                    else if(ea[k].equalsIgnoreCase(RGB_ORANGE_CMD))
                        rgbcolor = "ORANGE";
                    else if(ea[k].equalsIgnoreCase(RGB_PINK_CMD))
                        rgbcolor = "PINK";
                    else if(temp>=121 && temp<=130)
                        rgbbrightness = ea[k].substring(ea[k].length()-1);
                    else if(temp>=131 && temp<=140)
                        rgbtrasaction = ea[k].substring(ea[k].length()-1);

                    if(rgbbrightness.equalsIgnoreCase("0"))
                        rgbbrightness = "10";
                    if(rgbtrasaction.equalsIgnoreCase("0"))
                        rgbtrasaction = "10";

                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase(RGB_ON_CMD))
                    rgbpower = "ON";
                else if(eaarray.equalsIgnoreCase(RGB_OFF_CMD))
                    rgbpower = "OFF";
                else if(eaarray.equalsIgnoreCase(RGB_FLASH_CMD))
                    rgbeffect = "FLASH";
                else if(eaarray.equalsIgnoreCase(RGB_STROBE_CMD))
                    rgbeffect = "STROBE";
                else if(eaarray.equalsIgnoreCase(RGB_SMOOTH_CMD))
                    rgbeffect = "SMOOTH";
                else if(eaarray.equalsIgnoreCase(RGB_FADE_CMD))
                    rgbeffect = "FADE";
                else if(eaarray.equalsIgnoreCase(RGB_RED_CMD))
                    rgbcolor = "RED";
                else if(eaarray.equalsIgnoreCase(RGB_GREEN_CMD))
                    rgbcolor = "GREEN";
                else if(eaarray.equalsIgnoreCase(RGB_BLUE_CMD))
                    rgbcolor = "BLUE";
                else if(eaarray.equalsIgnoreCase(RGB_WHITE_CMD))
                    rgbcolor = "WHITE";
                else if(eaarray.equalsIgnoreCase(RGB_ORANGE_CMD))
                    rgbcolor = "ORANGE";
                else if(eaarray.equalsIgnoreCase(RGB_PINK_CMD))
                    rgbcolor = "PINK";
                else if(temp>=121 && temp<=130)
                    rgbbrightness = eaarray.substring(eaarray.length()-1);
                else if(temp>=131 && temp<=140)
                    rgbtrasaction = eaarray.substring(eaarray.length()-1);

                if(rgbbrightness.equalsIgnoreCase("0"))
                    rgbbrightness = "10";
                if(rgbtrasaction.equalsIgnoreCase("0"))
                    rgbtrasaction = "10";
            }
            rgbpowerval.setText(rgbpower);
            rgbcolorval.setText(rgbcolor);
            rgbeffectsval.setText(rgbeffect);
            rgbbrightnessval.setText(rgbbrightness);
            rgbtransactionval.setText(rgbtrasaction);

        }

        pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"102");


        if(pir_wirelessDB_data!=null){
            rgbpower = "";rgbcolor = "";rgbeffect= "";rgbbrightness = "";rgbtrasaction = "";

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());


            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase(RGB_ON_CMD))
                        rgbpower = "ON";
                    else if(ea[k].equalsIgnoreCase(RGB_OFF_CMD))
                        rgbpower = "OFF";
                    else if(ea[k].equalsIgnoreCase(RGB_FLASH_CMD))
                        rgbeffect = "FLASH";
                    else if(ea[k].equalsIgnoreCase(RGB_STROBE_CMD))
                        rgbeffect = "STROBE";
                    else if(ea[k].equalsIgnoreCase(RGB_SMOOTH_CMD))
                        rgbeffect = "SMOOTH";
                    else if(ea[k].equalsIgnoreCase(RGB_FADE_CMD))
                        rgbeffect = "FADE";
                    else if(ea[k].equalsIgnoreCase(RGB_RED_CMD))
                        rgbcolor = "RED";
                    else if(ea[k].equalsIgnoreCase(RGB_GREEN_CMD))
                        rgbcolor = "GREEN";
                    else if(ea[k].equalsIgnoreCase(RGB_BLUE_CMD))
                        rgbcolor = "BLUE";
                    else if(ea[k].equalsIgnoreCase(RGB_WHITE_CMD))
                        rgbcolor = "WHITE";
                    else if(ea[k].equalsIgnoreCase(RGB_ORANGE_CMD))
                        rgbcolor = "ORANGE";
                    else if(ea[k].equalsIgnoreCase(RGB_PINK_CMD))
                        rgbcolor = "PINK";
                    else if(temp>=121 && temp<=130)
                        rgbbrightness = ea[k].substring(ea[k].length()-1);
                    else if(temp>=131 && temp<=140)
                        rgbtrasaction = ea[k].substring(ea[k].length()-1);

                    if(rgbbrightness.equalsIgnoreCase("0"))
                        rgbbrightness = "10";
                    if(rgbtrasaction.equalsIgnoreCase("0"))
                        rgbtrasaction = "10";

                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase(RGB_ON_CMD))
                    rgbpower = "ON";
                else if(eaarray.equalsIgnoreCase(RGB_OFF_CMD))
                    rgbpower = "OFF";
                else if(eaarray.equalsIgnoreCase(RGB_FLASH_CMD))
                    rgbeffect = "FLASH";
                else if(eaarray.equalsIgnoreCase(RGB_STROBE_CMD))
                    rgbeffect = "STROBE";
                else if(eaarray.equalsIgnoreCase(RGB_SMOOTH_CMD))
                    rgbeffect = "SMOOTH";
                else if(eaarray.equalsIgnoreCase(RGB_FADE_CMD))
                    rgbeffect = "FADE";
                else if(eaarray.equalsIgnoreCase(RGB_RED_CMD))
                    rgbcolor = "RED";
                else if(eaarray.equalsIgnoreCase(RGB_GREEN_CMD))
                    rgbcolor = "GREEN";
                else if(eaarray.equalsIgnoreCase(RGB_BLUE_CMD))
                    rgbcolor = "BLUE";
                else if(eaarray.equalsIgnoreCase(RGB_WHITE_CMD))
                    rgbcolor = "WHITE";
                else if(eaarray.equalsIgnoreCase(RGB_ORANGE_CMD))
                    rgbcolor = "ORANGE";
                else if(eaarray.equalsIgnoreCase(RGB_PINK_CMD))
                    rgbcolor = "PINK";
                else if(temp>=121 && temp<=130)
                    rgbbrightness = eaarray.substring(eaarray.length()-1);
                else if(temp>=131 && temp<=140)
                    rgbtrasaction = eaarray.substring(eaarray.length()-1);

                if(rgbbrightness.equalsIgnoreCase("0"))
                    rgbbrightness = "10";
                if(rgbtrasaction.equalsIgnoreCase("0"))
                    rgbtrasaction = "10";
            }
            rgbpowerval_idle.setText(rgbpower);
            rgbcolorval_idle.setText(rgbcolor);
            rgbeffectsval_idle.setText(rgbeffect);
            rgbbrightnessval_idle.setText(rgbbrightness);
            rgbtransactionval_idle.setText(rgbtrasaction);

        }

    }
    // Dimmer //
    private void confdevicesDIMMER(View popupview, String ec, TextView roomnameval, TextView devicenameval, TextView switchonval, TextView switchoffval, TextView switchonval_idle, TextView switchoffval_idle) {

        RelativeLayout swbopertion = (RelativeLayout) popupview.findViewById(R.id.swbopertion);
        swbopertion.setVisibility(View.GONE);

        RelativeLayout rgboperation = (RelativeLayout) popupview.findViewById(R.id.rgboperation);
        rgboperation.setVisibility(View.GONE);

        RelativeLayout curtainopertion = (RelativeLayout) popupview.findViewById(R.id.curtainopertion);
        curtainopertion.setVisibility(View.GONE);

        RelativeLayout otheroperation = (RelativeLayout) popupview.findViewById(R.id.otheroperation);
        otheroperation.setVisibility(View.GONE);

        RelativeLayout dimmeropertion = (RelativeLayout) popupview.findViewById(R.id.dimmeropertion);
        dimmeropertion.setVisibility(View.VISIBLE);


        TextView dimmerpowerval,dimmerbrightnessval;
        TextView dimmerpowerval_idle,dimmerbrightnessval_idle;

        dimmerpowerval = (TextView) popupview.findViewById(R.id.dimmerpowerval);
        dimmerbrightnessval = (TextView) popupview.findViewById(R.id.dimmerbrightnessval);


        dimmerpowerval_idle = (TextView) popupview.findViewById(R.id.dimmerpowerval_idle);
        dimmerbrightnessval_idle = (TextView) popupview.findViewById(R.id.dimmerbrightnessval_idle);


        String dimmerpower = "",dimmerbrightness = "";
        //String dimmerpower_idle = "",dimmerbrightness_idle = "";


        PIR_WirelessDB_Data pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"101");


        if(pir_wirelessDB_data!=null){

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());

            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase(DMR_ON_CMD))
                        dimmerpower = "ON";
                    else if(ea[k].equalsIgnoreCase(DMR_OFF_CMD))
                        dimmerpower = "OFF";
                    else if(ea[k].equalsIgnoreCase(DMR_HIGH_CMD))
                        dimmerbrightness = "HIGH";
                    else if(ea[k].equalsIgnoreCase(DMR_MEDIUM_CMD))
                        dimmerbrightness = "MEDIUM";
                    else if(ea[k].equalsIgnoreCase(DMR_LOW_CMD))
                        dimmerbrightness = "LOW";

                    else {

                        int tempint = Integer.parseInt(ea[k]);
                        tempint= (100*tempint)/255;
                        dimmerbrightness = String.valueOf(tempint);
                    }
                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase(DMR_ON_CMD))
                    dimmerpower = "ON";
                else if(eaarray.equalsIgnoreCase(DMR_OFF_CMD))
                    dimmerpower = "OFF";
                else if(eaarray.equalsIgnoreCase(DMR_HIGH_CMD))
                    dimmerbrightness = "HIGH";
                else if(eaarray.equalsIgnoreCase(DMR_MEDIUM_CMD))
                    dimmerbrightness = "MEDIUM";
                else if(eaarray.equalsIgnoreCase(DMR_LOW_CMD))
                    dimmerbrightness = "LOW";

                else {

                    int tempint = Integer.parseInt(eaarray);
                    tempint= (100*tempint)/255;

                    dimmerbrightness = String.valueOf(tempint);
                }
            }
            dimmerpowerval.setText(dimmerpower);
            dimmerbrightnessval.setText(dimmerbrightness);

        }

        pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"102");


        if(pir_wirelessDB_data!=null){

            dimmerpower = "";dimmerbrightness = "";

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());


            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase(DMR_ON_CMD))
                        dimmerpower = "ON";
                    else if(ea[k].equalsIgnoreCase(DMR_OFF_CMD))
                        dimmerpower = "OFF";
                    else if(ea[k].equalsIgnoreCase(DMR_HIGH_CMD))
                        dimmerbrightness = "HIGH";
                    else if(ea[k].equalsIgnoreCase(DMR_MEDIUM_CMD))
                        dimmerbrightness = "MEDIUM";
                    else if(ea[k].equalsIgnoreCase(DMR_LOW_CMD))
                        dimmerbrightness = "LOW";
                    else if(ea[k].equalsIgnoreCase(DMR_1_CMD))
                        dimmerbrightness = "1";
                    else if(ea[k].equalsIgnoreCase(DMR_2_CMD))
                        dimmerbrightness = "2";
                    else if(ea[k].equalsIgnoreCase(DMR_3_CMD))
                        dimmerbrightness = "3";
                    else if(ea[k].equalsIgnoreCase(DMR_4_CMD))
                        dimmerbrightness = "4";
                    else if(ea[k].equalsIgnoreCase(DMR_5_CMD))
                        dimmerbrightness = "5";
                    else if(ea[k].equalsIgnoreCase(DMR_6_CMD))
                        dimmerbrightness = "6";
                    else if(ea[k].equalsIgnoreCase(DMR_7_CMD))
                        dimmerbrightness = "7";
                    else if(ea[k].equalsIgnoreCase(DMR_8_CMD))
                        dimmerbrightness = "8";
                    else if(ea[k].equalsIgnoreCase(DMR_9_CMD))
                        dimmerbrightness = "9";
                    else if(ea[k].equalsIgnoreCase(DMR_10_CMD))
                        dimmerbrightness = "10";


                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase(DMR_ON_CMD))
                    dimmerpower = "ON";
                else if(eaarray.equalsIgnoreCase(DMR_OFF_CMD))
                    dimmerpower = "OFF";
                else if(eaarray.equalsIgnoreCase(DMR_HIGH_CMD))
                    dimmerbrightness = "HIGH";
                else if(eaarray.equalsIgnoreCase(DMR_MEDIUM_CMD))
                    dimmerbrightness = "MEDIUM";
                else if(eaarray.equalsIgnoreCase(DMR_LOW_CMD))
                    dimmerbrightness = "LOW";
                else if(eaarray.equalsIgnoreCase(DMR_1_CMD))
                    dimmerbrightness = "1";
                else if(eaarray.equalsIgnoreCase(DMR_2_CMD))
                    dimmerbrightness = "2";
                else if(eaarray.equalsIgnoreCase(DMR_3_CMD))
                    dimmerbrightness = "3";
                else if(eaarray.equalsIgnoreCase(DMR_4_CMD))
                    dimmerbrightness = "4";
                else if(eaarray.equalsIgnoreCase(DMR_5_CMD))
                    dimmerbrightness = "5";
                else if(eaarray.equalsIgnoreCase(DMR_6_CMD))
                    dimmerbrightness = "6";
                else if(eaarray.equalsIgnoreCase(DMR_7_CMD))
                    dimmerbrightness = "7";
                else if(eaarray.equalsIgnoreCase(DMR_8_CMD))
                    dimmerbrightness = "8";
                else if(eaarray.equalsIgnoreCase(DMR_9_CMD))
                    dimmerbrightness = "9";
                else if(eaarray.equalsIgnoreCase(DMR_10_CMD))
                    dimmerbrightness = "10";
            }


            dimmerpowerval_idle.setText(dimmerpower);
            dimmerbrightnessval_idle.setText(dimmerbrightness);

        }

    }
    // Curtain ////05/jan/2019
    private void confdevicesCURTAIN(View popupview, String ec, TextView roomnameval, TextView devicenameval, TextView switchonval, TextView switchoffval, TextView switchonval_idle, TextView switchoffval_idle) {

        RelativeLayout swbopertion = (RelativeLayout) popupview.findViewById(R.id.swbopertion);
        swbopertion.setVisibility(View.GONE);

        RelativeLayout rgboperation = (RelativeLayout) popupview.findViewById(R.id.rgboperation);
        rgboperation.setVisibility(View.GONE);

        RelativeLayout dimmeropertion = (RelativeLayout) popupview.findViewById(R.id.dimmeropertion);
        dimmeropertion.setVisibility(View.GONE);

        RelativeLayout otheroperation = (RelativeLayout) popupview.findViewById(R.id.otheroperation);
        otheroperation.setVisibility(View.GONE);

        RelativeLayout curtainopertion = (RelativeLayout) popupview.findViewById(R.id.curtainopertion);
        curtainopertion.setVisibility(View.VISIBLE);

        TextView curtainactionval;
        TextView curtainactionval_idle;

        curtainactionval = (TextView) popupview.findViewById(R.id.curtainactionval);

        curtainactionval_idle = (TextView) popupview.findViewById(R.id.curtainactionval_idle);

        String curtainaction = "";
        //String dimmerpower_idle = "",dimmerbrightness_idle = "";



        PIR_WirelessDB_Data pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"101");


        if(pir_wirelessDB_data!=null){

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());

            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase(CUR1_OPEN_CMD))
                        curtainaction = "OPEN";
                    else if(ea[k].equalsIgnoreCase(CUR1_CLOSE_CMD))
                        curtainaction = "CLOSE";
                    else if(ea[k].equalsIgnoreCase(CUR1_STOP_CMD))
                        curtainaction = "STOP";

                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase(CUR1_OPEN_CMD))
                    curtainaction = "OPEN";
                else if(eaarray.equalsIgnoreCase(CUR1_CLOSE_CMD))
                    curtainaction = "CLOSE";
                else if(eaarray.equalsIgnoreCase(CUR1_STOP_CMD))
                    curtainaction = "STOP";

            }
            curtainactionval.setText(curtainaction);
            //curtainactionval_idle.setText(curtainaction);

        }
        curtainaction = "";

        pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"102");


        if(pir_wirelessDB_data!=null){

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());

            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase(CUR1_OPEN_CMD))
                        curtainaction = "OPEN";
                    else if(ea[k].equalsIgnoreCase(CUR1_CLOSE_CMD))
                        curtainaction = "CLOSE";
                    else if(ea[k].equalsIgnoreCase(CUR1_STOP_CMD))
                        curtainaction = "STOP";

                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase(CUR1_OPEN_CMD))
                    curtainaction = "OPEN";
                else if(eaarray.equalsIgnoreCase(CUR1_CLOSE_CMD))
                    curtainaction = "CLOSE";
                else if(eaarray.equalsIgnoreCase(CUR1_STOP_CMD))
                    curtainaction = "STOP";

            }
            //curtainactionval.setText(curtainaction);
            curtainactionval_idle.setText(curtainaction);

        }



    }
    // Other Devices // //07/jan/2019
    private void confdevicesOTHERS(View popupview, String ec, TextView roomnameval, TextView devicenameval, TextView switchonval, TextView switchoffval, TextView switchonval_idle, TextView switchoffval_idle) {

        RelativeLayout swbopertion = (RelativeLayout) popupview.findViewById(R.id.swbopertion);
        swbopertion.setVisibility(View.GONE);

        RelativeLayout rgboperation = (RelativeLayout) popupview.findViewById(R.id.rgboperation);
        rgboperation.setVisibility(View.GONE);

        RelativeLayout dimmeropertion = (RelativeLayout) popupview.findViewById(R.id.dimmeropertion);
        dimmeropertion.setVisibility(View.GONE);

        RelativeLayout curtainopertion = (RelativeLayout) popupview.findViewById(R.id.curtainopertion);
        curtainopertion.setVisibility(View.GONE);

        RelativeLayout otheroperation = (RelativeLayout) popupview.findViewById(R.id.otheroperation);
        otheroperation.setVisibility(View.VISIBLE);

        TextView otherspowerval;
        TextView otherspowerval_idle;

        otherspowerval = (TextView) popupview.findViewById(R.id.otherspowerval);

        otherspowerval_idle = (TextView) popupview.findViewById(R.id.otherspowerval_idle);

        String otherspower = "";


        PIR_WirelessDB_Data pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"101");


        if(pir_wirelessDB_data!=null){

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());

            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase("201"))
                        otherspower = "ON";
                    else if(ea[k].equalsIgnoreCase("301"))
                        otherspower = "OF";


                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase("201"))
                    otherspower = "ON";
                else if(eaarray.equalsIgnoreCase("301"))
                    otherspower = "OFF";


            }
            otherspowerval.setText(otherspower);


        }
        otherspower = "";


        pir_wirelessDB_data= WhouseDB.getpircobfiguredwirelessdata(StaticVariabes_div.devnumber,ec,"102");


        if(pir_wirelessDB_data!=null){

            roomnameval.setText(pir_wirelessDB_data.getb());
            devicenameval.setText(pir_wirelessDB_data.getec());

            String eaarray = pir_wirelessDB_data.getea();
            String[] ea;
            if(eaarray.contains(",")) {
                ea = eaarray.split(",");
                for (int k=0;k<ea.length;k++){
                    //String tempstr = ea[k];
                    long temp = Long.parseLong(ea[k]);
                    if(ea[k].equalsIgnoreCase("201"))
                        otherspower = "ON";
                    else if(ea[k].equalsIgnoreCase("301"))
                        otherspower = "OF";


                }
            }else {
                long temp = Long.parseLong(eaarray);
                if(eaarray.equalsIgnoreCase("201"))
                    otherspower = "ON";
                else if(eaarray.equalsIgnoreCase("301"))
                    otherspower = "OFF";


            }
            otherspowerval_idle.setText(otherspower);


        }


    }
    //# ============================================================================== #//


    //# ============================================================================== #//
    // PANEL SETTING METHOD'S //
    // GardenSprinkler // // 09/JAN/2019 //
    private void shownextlevelpopup(final TextView android_gridview_text, final ImageView android_gridview_image, final CheckView checkbtn, final TextView flagconf, final DeviceListArray deviceListArrayobj){
        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_switch_select,null,false);
        final PopupWindow s_popup_switch_select_popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        s_popup_switch_select_popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
        s_popup_switch_select_popupWindow.showAtLocation(popupview, Gravity.CENTER,0,0);

        final String fanspeedlevel[] = {"One", "Two", "Three", "Four"};

        final ImageView android_gridview_image_popup = (ImageView) popupview.findViewById(R.id.android_gridview_image) ;
        final TextView android_gridview_text_popup = (TextView) popupview.findViewById(R.id.android_gridview_text) ;
        final TextView set = (TextView) popupview.findViewById(R.id.set) ;

        final RelativeLayout fanspeedrealy = (RelativeLayout) popupview.findViewById(R.id.fanspeedrealy) ;

        if(android_gridview_text.getText().toString().equalsIgnoreCase("fan")){
            fanspeedrealy.setVisibility(View.VISIBLE);
            android_gridview_image.setImageResource(R.mipmap.fan02);
            android_gridview_image_popup.setImageResource(R.mipmap.fan02);
            android_gridview_text_popup.setText("Turned ON");
        }
        final TextView changefanspeed = (TextView) popupview.findViewById(R.id.changefanspeed) ;
        final TextSwitcher fanspeedswitcher = (TextSwitcher) popupview.findViewById(R.id.fanspeedswitcher) ;


        // Set the ViewFactory of the TextSwitcher that will create TextView object when asked
        fanspeedswitcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                // TODO Auto-generated method stub
                // create a TextView
                TextView t = new TextView(getActivity());
                // set the gravity of text to top and center horizontal
                t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                // set displayed text size
                t.setTextSize(16);

                t.setGravity(Gravity.CENTER);
                t.setTextColor(getResources().getColor(R.color.black));

                t.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                return t;
            }
        });

        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        // set the animation type to TextSwitcher
        fanspeedswitcher.setInAnimation(in);
        fanspeedswitcher.setOutAnimation(out);

        fanspeedswitcher.setText("0");
        currentpos = -1;
        changefanspeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentpos++;
                if(currentpos >= fanspeedlevel.length)
                    currentpos = 0;
                fanspeedswitcher.setText(fanspeedlevel[currentpos]);

                fanSpeed = ""+(currentpos+1);

                Toast.makeText(getActivity(),"fan speed ="+fanSpeed,Toast.LENGTH_SHORT).show();

            }
        });

        final TextView titletxt = (TextView) popupview.findViewById(R.id.titletxt);
        titletxt.setText(""+android_gridview_text.getText().toString());

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_popup_switch_select_popupWindow.dismiss();
            }
        });

        RelativeLayout selectonoff = (RelativeLayout) popupview.findViewById(R.id.selectonoff);

        selectonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")){
                    if(android_gridview_text.getText().toString().equalsIgnoreCase("fan")) {
                        android_gridview_image_popup.setImageResource(R.mipmap.fan02);
                        fanspeedrealy.setVisibility(View.VISIBLE);
                    }
                    else
                        android_gridview_image_popup.setImageResource(R.mipmap.bulb03);
                    android_gridview_text_popup.setText("Turned ON");
                }else if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")){
                    if(android_gridview_text.getText().toString().equalsIgnoreCase("fan")) {
                        android_gridview_image_popup.setImageResource(R.mipmap.fan01);
                        fanspeedrealy.setVisibility(View.GONE);
                    }
                    else
                        android_gridview_image_popup.setImageResource(R.mipmap.bulb01);
                    android_gridview_text_popup.setText("Turned OFF");
                }

            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec

                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";

                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";
                    if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF"))
                        Flag_OFF_ON = "OFF" ;
                    else
                        Flag_OFF_ON = "ON" ;

                    final String switchtitle = titletxt.getText().toString();

                    if(switchtitle.contains("Switch") && switchtitle.contains(" ")){
                        String[] temp = switchtitle.split(" ");
                        String tempLocalDataRef = temp[1];
                        SwitchNumbers = temp[1];

                        if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                            if (tempLocalDataRef != null && tempLocalDataRef.length() == 1){
                                LocalDataRef = "20" + tempLocalDataRef;
                                LocalDataRef2 = "30" + tempLocalDataRef;
                            }

                            else if (tempLocalDataRef != null && tempLocalDataRef.length() == 2) {
                                LocalDataRef = "2" + tempLocalDataRef;
                                LocalDataRef2 = "3" + tempLocalDataRef;
                            }
                        }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){
                            if (tempLocalDataRef != null && tempLocalDataRef.length() == 1) {
                                LocalDataRef = "30" + tempLocalDataRef;
                                LocalDataRef2 = "20" + tempLocalDataRef;
                            }
                            else if (tempLocalDataRef != null && tempLocalDataRef.length() == 2) {
                                LocalDataRef = "3" + tempLocalDataRef;
                                LocalDataRef2 = "2" + tempLocalDataRef;
                            }
                        }

                        String tempdeviceno = String.valueOf(Current_DevNo);
                        String temproomno = String.valueOf(Current_RoomNo);
                        while(tempdeviceno.length()<4)
                            tempdeviceno="0"+tempdeviceno;
                        while(temproomno.length()<2)
                            temproomno="0"+temproomno;

                        SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                        SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;


                    }else if(switchtitle.contains("Fan")){

                        if(Current_DevName.equalsIgnoreCase("S021"))
                            SwitchNumbers = "3";
                        else if(Current_DevName.equalsIgnoreCase("S051"))
                            SwitchNumbers = "6";
                        else if(Current_DevName.equalsIgnoreCase("S081"))
                            SwitchNumbers = "9";

                        String fandata = "722";
                        if(Flag_OFF_ON.equalsIgnoreCase("ON")){

                            if(!fanSpeed.equalsIgnoreCase("0"))
                                fandata = "71"+fanSpeed ;
                            else
                                fandata = "722";

                        }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){
                            fandata = "723";
                        }

                        LocalDataRef = fandata;
                        String tempdeviceno = String.valueOf(Current_DevNo);
                        String temproomno = String.valueOf(Current_RoomNo);
                        while(tempdeviceno.length()<4)
                            tempdeviceno="0"+tempdeviceno;
                        while(temproomno.length()<2)
                            temproomno="0"+temproomno;

                        SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;


                        SwitchData_WLSfanon = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanon+"00000000000000"+deviceTypeForPirPanel;
                        SwitchData_WLSfanoff = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanoff+"00000000000000"+deviceTypeForPirPanel;
                        SwitchData_WLSfanspeed1 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed1+"00000000000000"+deviceTypeForPirPanel;
                        SwitchData_WLSfanspeed2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed2+"00000000000000"+deviceTypeForPirPanel;
                        SwitchData_WLSfanspeed3 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed3+"00000000000000"+deviceTypeForPirPanel;
                        SwitchData_WLSfanspeed4 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed4+"00000000000000"+deviceTypeForPirPanel;

                    }

                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                        if(switchtitle.contains("Fan")){
                            if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanon)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanon, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanon, tempSwitchData_WLS);


                            }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanoff)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanoff, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanoff, tempSwitchData_WLS);


                            }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed1)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed1, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed1, tempSwitchData_WLS);


                            }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed2)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed2, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed2, tempSwitchData_WLS);


                            }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed3)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed3, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed3, tempSwitchData_WLS);


                            }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed4)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed4, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed4, tempSwitchData_WLS);


                            } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                        }else {

                            if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                            } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                        }


                    }

                    // Insert/update data to wirelesstable //
                    boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                    WhouseDB.close();

                    if (isInserted) {

                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                        if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {
                            alertmsg1.setText(getText(R.string.pirselectalert2));
                        } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                            alertmsg1.setText(getText(R.string.pirselectalert));
                        }

                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");
                                // setPIRpannelsetting();
                                if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                    if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan01);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb01);
                                } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                                    if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan02);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb03);
                                }
                                checkbtn.check();
                                flagconf.setText("1");

                                fanSpeed = "0" ;

                            }
                        });

                        TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                    Flag_OFF_ON = "OFF";
                                else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    Flag_OFF_ON = "ON";


                                if(switchtitle.contains("Switch") && switchtitle.contains(" ")){
                                    String[] temp = switchtitle.split(" ");
                                    String tempLocalDataRef = temp[1];
                                    SwitchNumbers = temp[1];

                                    if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                        if (tempLocalDataRef != null && tempLocalDataRef.length() == 1){
                                            LocalDataRef = "20" + tempLocalDataRef;
                                            LocalDataRef2 = "30" + tempLocalDataRef;
                                        }

                                        else if (tempLocalDataRef != null && tempLocalDataRef.length() == 2) {
                                            LocalDataRef = "2" + tempLocalDataRef;
                                            LocalDataRef2 = "3" + tempLocalDataRef;
                                        }
                                    }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){
                                        if (tempLocalDataRef != null && tempLocalDataRef.length() == 1) {
                                            LocalDataRef = "30" + tempLocalDataRef;
                                            LocalDataRef2 = "20" + tempLocalDataRef;
                                        }
                                        else if (tempLocalDataRef != null && tempLocalDataRef.length() == 2) {
                                            LocalDataRef = "3" + tempLocalDataRef;
                                            LocalDataRef2 = "2" + tempLocalDataRef;
                                        }
                                    }


                                    String tempdeviceno = String.valueOf(Current_DevNo);
                                    String temproomno = String.valueOf(Current_RoomNo);
                                    while(tempdeviceno.length()<4)
                                        tempdeviceno="0"+tempdeviceno;
                                    while(temproomno.length()<2)
                                        temproomno="0"+temproomno;

                                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;

                                }else if(switchtitle.contains("Fan")){

                                    if(Current_DevName.equalsIgnoreCase("S021"))
                                        SwitchNumbers = "3";
                                    else if(Current_DevName.equalsIgnoreCase("S051"))
                                        SwitchNumbers = "6";
                                    else if(Current_DevName.equalsIgnoreCase("S081"))
                                        SwitchNumbers = "9";

                                    String fandata = "722";
                                    if(Flag_OFF_ON.equalsIgnoreCase("ON")){

                                        if(!fanSpeed.equalsIgnoreCase("0"))
                                            fandata = "71"+fanSpeed ;
                                        else
                                            fandata = "722";

                                    }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){
                                        fandata = "723";
                                    }
                                    LocalDataRef = fandata;
                                    String tempdeviceno = String.valueOf(Current_DevNo);
                                    String temproomno = String.valueOf(Current_RoomNo);
                                    while(tempdeviceno.length()<4)
                                        tempdeviceno="0"+tempdeviceno;
                                    while(temproomno.length()<2)
                                        temproomno="0"+temproomno;

                                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;


                                    SwitchData_WLSfanon = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanon+"00000000000000"+deviceTypeForPirPanel;
                                    SwitchData_WLSfanoff = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanoff+"00000000000000"+deviceTypeForPirPanel;
                                    SwitchData_WLSfanspeed1 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed1+"00000000000000"+deviceTypeForPirPanel;
                                    SwitchData_WLSfanspeed2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed2+"00000000000000"+deviceTypeForPirPanel;
                                    SwitchData_WLSfanspeed3 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed3+"00000000000000"+deviceTypeForPirPanel;
                                    SwitchData_WLSfanspeed4 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataReffanspeed4+"00000000000000"+deviceTypeForPirPanel;

                                }


                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    /*if(switchtitle.contains("Switch") && switchtitle.contains(" ")){
                                        if(LocalDataRef.contains(",")){
                                            String[] tempLocalDataRefarry = LocalDataRef.split(",");

                                            for(int k=0;k<tempLocalDataRefarry.length;k++){
                                                if(tempLocalDataRefarry[k].contains(tempLocalDataRef) || tempLocalDataRefarry[k].contains(tempLocalDataRef2)){

                                                }
                                            }


                                        }else {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef ;
                                        }
                                    }*/

                                    if(switchtitle.contains("Fan")){
                                        if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanon)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanon, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanon, tempSwitchData_WLS);

                                        }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanoff)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanoff, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanoff, tempSwitchData_WLS);

                                        }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed1)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed1, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed1, tempSwitchData_WLS);

                                        }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed2)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed2, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed2, tempSwitchData_WLS);


                                        }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed3)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed3, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed3, tempSwitchData_WLS);


                                        }else if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataReffanspeed4)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataReffanspeed4, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLSfanspeed4, tempSwitchData_WLS);


                                        } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                            SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                            SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                        }
                                    }else {
                                        if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataRef2)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                                        } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                            SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                            SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                        }
                                    }
                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();


                                    if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                        if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                            android_gridview_image.setImageResource(R.mipmap.fan01);
                                        else
                                            android_gridview_image.setImageResource(R.mipmap.bulb01);
                                    } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                                        if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                            android_gridview_image.setImageResource(R.mipmap.fan02);
                                        else
                                            android_gridview_image.setImageResource(R.mipmap.bulb03);
                                    }
                                    checkbtn.check();
                                    flagconf.setText("1");

                                    fanSpeed = "0" ;
                                }
                            }
                        });
                        s_popup_switch_select_popupWindow.dismiss();
                    }
                }
            }
        });
    }
    private void SwitchPopup(View view, int i, final DeviceListArray deviceListArrayobj){

        TextView android_gridview_text = (TextView) view.findViewById(R.id.android_gridview_text);

        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_pannel_room,null,false);
        popupWindow_three = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        popupWindow_three.setAnimationStyle(R.style.PauseDialogAnimation);
        popupWindow_three.showAtLocation(popupview, Gravity.CENTER,0,0);

        TextView titletxt = (TextView) popupview.findViewById(R.id.titletxt);
        titletxt.setText(""+android_gridview_text.getText().toString());

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow_three.dismiss();
            }
        });
        TextView ok = (TextView) popupview.findViewById(R.id.ok);
        ok.setText("Home");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow_one!=null && popupWindow_one.isShowing())
                    popupWindow_one.dismiss();
                if(popupWindow_two!=null && popupWindow_two.isShowing())
                    popupWindow_two.dismiss();
                if(popupWindow_three!=null && popupWindow_three.isShowing())
                    popupWindow_three.dismiss();
            }
        });
        ArrayList<HashMap<Integer, String>> deviceListOperation = new ArrayList<HashMap<Integer, String>>();
       /* for(int k=0;k<noofSwitches(deviceListArrayobj.getDeviceShortName());k++){
            HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
            if(k==5)
                hashMap.put(R.mipmap.fan01,"Fan");
            else
                hashMap.put(R.mipmap.bulb01,"Switch "+(k+1));
            deviceListOperation.add(hashMap);

        }*/
        for(int k=0;k<noofSwitches(deviceListArrayobj.getDeviceShortName());k++){
            HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
            hashMap.put(R.mipmap.bulb01,"Switch "+(k+1));
            deviceListOperation.add(hashMap);
        }

        for(int k=0;k<noofFans(deviceListArrayobj.getDeviceShortName());k++){
            HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
            hashMap.put(R.mipmap.fan01,"Fan");
            deviceListOperation.add(hashMap);
        }

        GridView gridView = (GridView) popupview.findViewById(R.id.gridView);
        Custom_device_operation_GridViewActivity customGridViewActivity = new Custom_device_operation_GridViewActivity(getActivity(), deviceListOperation);
        gridView.setAdapter(customGridViewActivity);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final TextView android_gridview_text = (TextView) view.findViewById(R.id.android_gridview_text);

                final ImageView android_gridview_image = (ImageView) view.findViewById(R.id.android_gridview_image);

               /* if(android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                    android_gridview_image.setImageResource(R.mipmap.fan02);
                else
                    android_gridview_image.setImageResource(R.mipmap.bulb03);*/

                final edisonbro.com.edisonbroautomation.CheckView checkbtn = (CheckView) view.findViewById(R.id.check);
                final TextView flagconf = (TextView) view.findViewById(R.id.flagconf);

                if(flagconf.getText().toString().equalsIgnoreCase("1")){
                    View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert,null,false);
                    popupWindow_four = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
                    popupWindow_four.setAnimationStyle(R.style.PauseDialogAnimation);
                    popupWindow_four.showAtLocation(popupview, Gravity.CENTER,0,0);

                    TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                    alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                    alertmsg1.setText(getText(R.string.change_operation));
                    alertmsg1.setTextColor(getResources().getColor(R.color.colorPrimaryDarktext));

                    TextView skip = (TextView) popupview.findViewById(R.id.skip);
                    skip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow_four.dismiss();
                        }
                    });
                    TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                    pircontinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow_four.dismiss();
                            shownextlevelpopup(android_gridview_text,android_gridview_image,checkbtn,flagconf,deviceListArrayobj);
                        }
                    });
                }
                else {
                    shownextlevelpopup(android_gridview_text,android_gridview_image,checkbtn,flagconf,deviceListArrayobj);
                }
            }
        });
    }
    // RGB //29 DEC 2018
    public void rgbPopupShow(View view, final int i, final DeviceListArray deviceListArrayobj){
        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_rgb_operation_selection,null,false);
        rgbPopup_one = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        rgbPopup_one.setAnimationStyle(R.style.PauseDialogAnimation);
        rgbPopup_one.showAtLocation(popupview, Gravity.CENTER,0,0);

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        TextView home = (TextView) popupview.findViewById(R.id.home);
        TextView titletxt = (TextView) popupview.findViewById(R.id.titletxt);

        TextView android_gridview_text = (TextView) view.findViewById(R.id.android_gridview_text);
        titletxt.setText(""+android_gridview_text.getText().toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbPopup_one.dismiss();
            }
        });home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow_one!=null && popupWindow_one.isShowing())
                    popupWindow_one.dismiss();
                if(popupWindow_two!=null && popupWindow_two.isShowing())
                    popupWindow_two.dismiss();
                if(rgbPopup_one!=null && rgbPopup_one.isShowing())
                    rgbPopup_one.dismiss();

            }
        });
        TextView rgbcolour,rgbeffects,rgbonoff,rgbbrightness,rgbtransactions;
        rgbcolour = (TextView) popupview.findViewById(R.id.rgbcolour);
        rgbeffects = (TextView) popupview.findViewById(R.id.rgbeffects);
        rgbonoff = (TextView) popupview.findViewById(R.id.rgbonoff);
        rgbbrightness = (TextView) popupview.findViewById(R.id.rgbbrightness);
        rgbtransactions = (TextView) popupview.findViewById(R.id.rgbtransactions);

        rgbcolour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbColorPopupShow(view, i, deviceListArrayobj);
            }
        });rgbeffects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbEffectsPopupShow(view, i, deviceListArrayobj);

            }
        });rgbonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgb_onoff_selection(view, i, deviceListArrayobj);

            }
        });rgbbrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgb_brightness_selection(view, i, deviceListArrayobj,"Brightness");

            }
        });rgbtransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgb_brightness_selection(view, i, deviceListArrayobj,"Transaction");

            }
        });



    }
    public void rgbColorPopupShow(View view, int i, final DeviceListArray deviceListArrayobj){
        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_rgb_color_selection,null,false);
        final PopupWindow rgbPopup_rgbColor = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        rgbPopup_rgbColor.setAnimationStyle(R.style.PauseDialogAnimation);
        rgbPopup_rgbColor.showAtLocation(popupview, Gravity.CENTER,0,0);

        RGB_Color_selected = RGB_RED_CMD;

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbPopup_rgbColor.dismiss();
            }
        });


        TextView rgbred,rgbgreen,rgbblue,rgborange,rgbpink,rgbwhite;
        rgbred    = (TextView) popupview.findViewById(R.id.rgbred);
        rgbgreen  = (TextView) popupview.findViewById(R.id.rgbgreen);
        rgbblue   = (TextView) popupview.findViewById(R.id.rgbblue);
        rgborange = (TextView) popupview.findViewById(R.id.rgborange);
        rgbpink   = (TextView) popupview.findViewById(R.id.rgbpink);
        rgbwhite  = (TextView) popupview.findViewById(R.id.rgbwhite);

        final CardView rgbredcardview    = (CardView) popupview.findViewById(R.id.rgbredcardview);
        final CardView rgbgreencardview  = (CardView) popupview.findViewById(R.id.rgbgreencardview);
        final CardView rgbbluecardview   = (CardView) popupview.findViewById(R.id.rgbbluecardview);
        final CardView rgborangecardview = (CardView) popupview.findViewById(R.id.rgborangecardview);
        final CardView rgbpinkcardview   = (CardView) popupview.findViewById(R.id.rgbpinkcardview);
        final CardView rgbwhitecardview  = (CardView) popupview.findViewById(R.id.rgbwhitecardview);



        rgbred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.red));
                rgbgreencardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbpinkcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Color_selected = RGB_RED_CMD ;
            }
        });rgbgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbgreencardview.setCardBackgroundColor(getResources().getColor(R.color.green));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbpinkcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Color_selected = RGB_GREEN_CMD;
            }
        });rgbblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbgreencardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.blue));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbpinkcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Color_selected = RGB_BLUE_CMD;
            }
        });rgborange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbgreencardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.orange));
                rgbpinkcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Color_selected = RGB_ORANGE_CMD;
            }
        });rgbpink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbgreencardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbpinkcardview.setCardBackgroundColor(getResources().getColor(R.color.pink));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Color_selected = RGB_PINK_CMD;
            }
        });rgbwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbgreencardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbpinkcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1));

                RGB_Color_selected = RGB_WHITE_CMD;
            }
        });


        final TextView set = (TextView) popupview.findViewById(R.id.set) ;



        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "1";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec

                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";

                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";

                    Flag_OFF_ON = "ON" ;
                    LocalDataRef = RGB_ON_CMD;
                    LocalDataRef2 = RGB_OFF_CMD;


                    String tempdeviceno = String.valueOf(Current_DevNo);
                    String temproomno = String.valueOf(Current_RoomNo);
                    while(tempdeviceno.length()<4)
                        tempdeviceno="0"+tempdeviceno;
                    while(temproomno.length()<2)
                        temproomno="0"+temproomno;

                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;



                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");
                        {
                            if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                            } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                        }
                    }
                    // Insert/update data to wirelesstable //
                    boolean isInserted = true; WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                    if(isInserted){
                        SwitchNumbers = "2";
                        Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                        if(Additionalinfo!=null && Additionalinfo.size()>0){
                            String SwitchData_RGB_Color_selected = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_Color_selected+"00"+deviceTypeForPirPanel;

                            String SwitchData_RGB_Color_RED = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_RED_CMD+"00"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Color_GREEN = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_GREEN_CMD+"00"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Color_BLUE = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_BLUE_CMD+"00"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Color_WHITE = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_WHITE_CMD+"00"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Color_ORANGE = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_ORANGE_CMD+"00"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Color_PINK = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_COLOR_PROCESS_CMD+RGB_PINK_CMD+"00"+deviceTypeForPirPanel;

                            String tempLocalDataRef = RGB_Color_selected ;
                            String tempSwitchData_WLS = SwitchData_RGB_Color_selected ;
                            String tempSwitchNumbers = SwitchNumbers ;

                            RGB_Color_selected = Additionalinfo.get(0).get("localrefdata") ;
                            SwitchData_RGB_Color_selected = Additionalinfo.get(0).get("switchdata");
                            SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                            Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                            if (RGB_RED_CMD != null && RGB_Color_selected.contains(RGB_RED_CMD)) {
                                RGB_Color_selected = RGB_Color_selected.replaceAll(RGB_RED_CMD, tempLocalDataRef);
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected.replaceAll(SwitchData_RGB_Color_RED, tempSwitchData_WLS);

                            }else if (RGB_GREEN_CMD != null && RGB_Color_selected.contains(RGB_GREEN_CMD)) {
                                RGB_Color_selected = RGB_Color_selected.replaceAll(RGB_GREEN_CMD, tempLocalDataRef);
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected.replaceAll(SwitchData_RGB_Color_GREEN, tempSwitchData_WLS);

                            }else if (RGB_BLUE_CMD != null && RGB_Color_selected.contains(RGB_BLUE_CMD)) {
                                RGB_Color_selected = RGB_Color_selected.replaceAll(RGB_BLUE_CMD, tempLocalDataRef);
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected.replaceAll(SwitchData_RGB_Color_BLUE, tempSwitchData_WLS);

                            }else if (RGB_WHITE_CMD != null && RGB_Color_selected.contains(RGB_WHITE_CMD)) {
                                RGB_Color_selected = RGB_Color_selected.replaceAll(RGB_WHITE_CMD, tempLocalDataRef);
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected.replaceAll(SwitchData_RGB_Color_WHITE, tempSwitchData_WLS);

                            }else if (RGB_ORANGE_CMD != null && RGB_Color_selected.contains(RGB_ORANGE_CMD)) {
                                RGB_Color_selected = RGB_Color_selected.replaceAll(RGB_ORANGE_CMD, tempLocalDataRef);
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected.replaceAll(SwitchData_RGB_Color_ORANGE, tempSwitchData_WLS);

                            }else if (RGB_PINK_CMD != null && RGB_Color_selected.contains(RGB_PINK_CMD)) {
                                RGB_Color_selected = RGB_Color_selected.replaceAll(RGB_PINK_CMD, tempLocalDataRef);
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected.replaceAll(SwitchData_RGB_Color_PINK, tempSwitchData_WLS);

                            } else if (tempLocalDataRef != null && !RGB_Color_selected.contains(tempLocalDataRef)) {
                                RGB_Color_selected = RGB_Color_selected + "," + tempLocalDataRef;
                                SwitchData_RGB_Color_selected = SwitchData_RGB_Color_selected + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                            //SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            isInserted = true; WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                    Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                    Current_DevName, RGB_Color_selected, PanelSwitchNumber, SwitchData_RGB_Color_selected, Current_DevType,
                                    Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                        }
                    }

                    WhouseDB.close();

                    if (isInserted) {
                        SwitchNumbers = "1";
                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);


                        alertmsg1.setText(getText(R.string.pirselectalert));


                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");

                                //checkbtn.check();
                                //flagconf.setText("1");

                                fanSpeed = "0" ;

                            }
                        });

                        TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                    Flag_OFF_ON = "OFF";

                                    LocalDataRef = RGB_OFF_CMD;
                                    LocalDataRef2 = RGB_ON_CMD;

                                }
                                else if(Flag_OFF_ON.equalsIgnoreCase("OFF")) {
                                    Flag_OFF_ON = "ON";

                                    LocalDataRef2 = RGB_OFF_CMD;
                                    LocalDataRef = RGB_ON_CMD;
                                }
                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;


                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }


                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();

                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        rgbPopup_rgbColor.dismiss();
                    }

                }
            }
        });
    }
    public void rgbEffectsPopupShow(View view, int i, final DeviceListArray deviceListArrayobj){
        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_rgb_effects_selection,null,false);
        final PopupWindow rgbPopup_rgbEffects = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        rgbPopup_rgbEffects.setAnimationStyle(R.style.PauseDialogAnimation);
        rgbPopup_rgbEffects.showAtLocation(popupview, Gravity.CENTER,0,0);

        RGB_Effect_selected = RGB_SMOOTH_CMD;

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        TextView set = (TextView) popupview.findViewById(R.id.set);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbPopup_rgbEffects.dismiss();
            }
        });
        TextView rgbflash,rgbfade,rgbstrobe,rgbsmooth;
        rgbflash = (TextView) popupview.findViewById(R.id.rgbflash);
        rgbfade = (TextView) popupview.findViewById(R.id.rgbfade);
        rgbstrobe = (TextView) popupview.findViewById(R.id.rgbstrobe);
        rgbsmooth = (TextView) popupview.findViewById(R.id.rgbsmooth);


        final CardView rgbredcardview = (CardView) popupview.findViewById(R.id.rgbredcardview);
        final CardView rgbbluecardview = (CardView) popupview.findViewById(R.id.rgbbluecardview);
        final CardView rgborangecardview = (CardView) popupview.findViewById(R.id.rgborangecardview);
        final CardView rgbwhitecardview = (CardView) popupview.findViewById(R.id.rgbwhitecardview);


        rgbflash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.red));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Effect_selected = RGB_FLASH_CMD;

            }
        });rgbfade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.green));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Effect_selected = RGB_FADE_CMD;

            }
        });rgbstrobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.blue));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.white));

                RGB_Effect_selected = RGB_STROBE_CMD;

            }
        });rgbsmooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgbredcardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbbluecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgborangecardview.setCardBackgroundColor(getResources().getColor(R.color.white));
                rgbwhitecardview.setCardBackgroundColor(getResources().getColor(R.color.pink));

                RGB_Effect_selected = RGB_SMOOTH_CMD;

            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "1";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec
                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";
                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";

                    Flag_OFF_ON = "ON" ;
                    LocalDataRef = RGB_ON_CMD;
                    LocalDataRef2 = RGB_OFF_CMD;


                    String tempdeviceno = String.valueOf(Current_DevNo);
                    String temproomno = String.valueOf(Current_RoomNo);
                    while(tempdeviceno.length()<4)
                        tempdeviceno="0"+tempdeviceno;
                    while(temproomno.length()<2)
                        temproomno="0"+temproomno;

                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;



                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");
                        {
                            if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                            } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                        }
                    }
                    // Insert/update data to wirelesstable //
                    boolean isInserted = true; WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                    if(isInserted){
                        SwitchNumbers = "3";
                        Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                        if(Additionalinfo!=null && Additionalinfo.size()>0){
                            String SwitchData_RGB_Effect_selected = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_Effect_selected+"00000000000000"+deviceTypeForPirPanel;

                            String SwitchData_RGB_Effect_FLASH = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_FLASH_CMD+"00000000000000"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Effect_FADE = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_FADE_CMD+"00000000000000"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Effect_STROBE = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_STROBE_CMD+"00000000000000"+deviceTypeForPirPanel;
                            String SwitchData_RGB_Effect_SMOOTH = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+RGB_SMOOTH_CMD+"00000000000000"+deviceTypeForPirPanel;

                            String tempLocalDataRef = RGB_Effect_selected ;
                            String tempSwitchData_WLS = SwitchData_RGB_Effect_selected ;
                            String tempSwitchNumbers = SwitchNumbers ;

                            RGB_Effect_selected = Additionalinfo.get(0).get("localrefdata") ;
                            SwitchData_RGB_Effect_selected = Additionalinfo.get(0).get("switchdata");
                            SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                            Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                            if (RGB_FLASH_CMD != null && RGB_Effect_selected.contains(RGB_FLASH_CMD)) {
                                RGB_Effect_selected = RGB_Effect_selected.replaceAll(RGB_FLASH_CMD, tempLocalDataRef);
                                SwitchData_RGB_Effect_selected = SwitchData_RGB_Effect_selected.replaceAll(SwitchData_RGB_Effect_FLASH, tempSwitchData_WLS);

                            }else if (RGB_FADE_CMD != null && RGB_Effect_selected.contains(RGB_FADE_CMD)) {
                                RGB_Effect_selected = RGB_Effect_selected.replaceAll(RGB_FADE_CMD, tempLocalDataRef);
                                SwitchData_RGB_Effect_selected = SwitchData_RGB_Effect_selected.replaceAll(SwitchData_RGB_Effect_FADE, tempSwitchData_WLS);

                            }else if (RGB_STROBE_CMD != null && RGB_Effect_selected.contains(RGB_STROBE_CMD)) {
                                RGB_Effect_selected = RGB_Effect_selected.replaceAll(RGB_STROBE_CMD, tempLocalDataRef);
                                SwitchData_RGB_Effect_selected = SwitchData_RGB_Effect_selected.replaceAll(SwitchData_RGB_Effect_STROBE, tempSwitchData_WLS);

                            }else if (RGB_SMOOTH_CMD != null && RGB_Effect_selected.contains(RGB_SMOOTH_CMD)) {
                                RGB_Effect_selected = RGB_Effect_selected.replaceAll(RGB_SMOOTH_CMD, tempLocalDataRef);
                                SwitchData_RGB_Effect_selected = SwitchData_RGB_Effect_selected.replaceAll(SwitchData_RGB_Effect_SMOOTH, tempSwitchData_WLS);

                            } else if (tempLocalDataRef != null && !RGB_Effect_selected.contains(tempLocalDataRef)) {
                                RGB_Effect_selected = RGB_Effect_selected + "," + tempLocalDataRef;
                                SwitchData_RGB_Effect_selected = SwitchData_RGB_Effect_selected + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                            isInserted = true; WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                    Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                    Current_DevName, RGB_Effect_selected, PanelSwitchNumber, SwitchData_RGB_Effect_selected, Current_DevType,
                                    Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                        }
                    }

                    WhouseDB.close();

                    if (isInserted) {
                        SwitchNumbers = "1";
                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);


                        alertmsg1.setText(getText(R.string.pirselectalert));


                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");

                                //checkbtn.check();
                                //flagconf.setText("1");

                                fanSpeed = "0" ;

                            }
                        });

                        TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                    Flag_OFF_ON = "OFF";

                                    LocalDataRef = RGB_OFF_CMD;
                                    LocalDataRef2 = RGB_ON_CMD;

                                }
                                else if(Flag_OFF_ON.equalsIgnoreCase("OFF")) {
                                    Flag_OFF_ON = "ON";

                                    LocalDataRef2 = RGB_OFF_CMD;
                                    LocalDataRef = RGB_ON_CMD;
                                }
                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;


                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }


                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();

                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        rgbPopup_rgbEffects.dismiss();
                    }

                }
            }
        });
    }
    public void rgb_onoff_selection(View view, int i, final DeviceListArray deviceListArrayobj){
        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_rgb_onoff_selection,null,false);
        final PopupWindow s_popup_switch_select_popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        s_popup_switch_select_popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
        s_popup_switch_select_popupWindow.showAtLocation(popupview, Gravity.CENTER,0,0);
        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);

        final ImageView android_gridview_image_popup = (ImageView) popupview.findViewById(R.id.android_gridview_image) ;
        final TextView android_gridview_text_popup = (TextView) popupview.findViewById(R.id.android_gridview_text) ;
        final TextView set = (TextView) popupview.findViewById(R.id.set) ;


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_popup_switch_select_popupWindow.dismiss();
            }
        });

        RelativeLayout selectonoff = (RelativeLayout) popupview.findViewById(R.id.selectonoff);

        selectonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")){
                    android_gridview_image_popup.setImageResource(R.mipmap.rgb_grid_p);
                    android_gridview_text_popup.setText("Turned ON");

                }else if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")){

                    android_gridview_image_popup.setImageResource(R.mipmap.rgb_grid_n);
                    android_gridview_text_popup.setText("Turned OFF");
                }
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "1";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec

                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";
                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";
                    if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {
                        Flag_OFF_ON = "OFF";
                        LocalDataRef = RGB_OFF_CMD;
                        LocalDataRef2 = RGB_ON_CMD;
                    }
                    else{
                        Flag_OFF_ON = "ON" ;
                        LocalDataRef = RGB_ON_CMD;
                        LocalDataRef2 = RGB_OFF_CMD;
                    }

                    String tempdeviceno = String.valueOf(Current_DevNo);
                    String temproomno = String.valueOf(Current_RoomNo);
                    while(tempdeviceno.length()<4)
                        tempdeviceno="0"+tempdeviceno;
                    while(temproomno.length()<2)
                        temproomno="0"+temproomno;

                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;



                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                        {
                            if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                            } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                        }
                    }
                    // Insert/update data to wirelesstable //
                    boolean isInserted = true; WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                    WhouseDB.close();

                    if (isInserted) {

                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                        if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {
                            alertmsg1.setText(getText(R.string.pirselectalert2));
                        } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                            alertmsg1.setText(getText(R.string.pirselectalert));
                        }

                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");
                                // setPIRpannelsetting();
                                if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                   /* if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan01);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb01);*/
                                } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                                    /*if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan02);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb03);*/
                                }
                                //checkbtn.check();
                                //flagconf.setText("1");

                                fanSpeed = "0" ;

                            }
                        });

                        TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                    Flag_OFF_ON = "OFF";

                                    LocalDataRef = RGB_OFF_CMD;
                                    LocalDataRef2 = RGB_ON_CMD;

                                }
                                else if(Flag_OFF_ON.equalsIgnoreCase("OFF")) {
                                    Flag_OFF_ON = "ON";

                                    LocalDataRef2 = RGB_OFF_CMD;
                                    LocalDataRef = RGB_ON_CMD;
                                }
                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;


                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }


                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();

                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        s_popup_switch_select_popupWindow.dismiss();
                    }

                }
            }
        });
    }
    public void rgb_brightness_selection(View view, int i, final DeviceListArray deviceListArrayobj, final String Flag_type){

        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_rgb_brightness_selection,null,false);
        final PopupWindow s_popup_rgb_brightness_popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        s_popup_rgb_brightness_popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
        s_popup_rgb_brightness_popupWindow.showAtLocation(popupview, Gravity.CENTER,0,0);
        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_popup_rgb_brightness_popupWindow.dismiss();
            }
        });

        //Get the widgets reference from XML layout

        final NumberPicker np = (NumberPicker) popupview.findViewById(R.id.np);
        TextView android_gridview_text = (TextView) popupview.findViewById(R.id.android_gridview_text);

        if(Flag_type.equalsIgnoreCase("Brightness"))
            android_gridview_text.setText("RGB Brightness Level");
        else if(Flag_type.equalsIgnoreCase("Transaction"))
            android_gridview_text.setText("RGB Transaction Level");

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(1);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
            }
        });

        final TextView set = (TextView) popupview.findViewById(R.id.set) ;

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Flag_type.equalsIgnoreCase("Brightness")) {

                    RGB_brightness_data = String.valueOf(np.getValue());

                    if (np.getValue() < 10)
                        RGB_brightness_data = "13" + RGB_brightness_data;
                    else
                        RGB_brightness_data = "140";
                    houseDB.open();
                    ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                    houseDB.close();

                    if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                        final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                        Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                        Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                        Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                        Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                        Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                        Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                        Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                        LocalDataRef = "";                                                              // ea
                        PanelSwitchNumber = 101;                                                             // wsn
                        SwitchData_WLS = "";                                                            // wsd
                        Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                        Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                        Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                        SwitchNumbers = "1";                                                             // bb
                        Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                        Current_Pirlightsensorval_WRLS = "";                                            // eb
                        Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec
                        Current_Group_Id = deviceListArrayobj.getDevgroupId();
                        if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                            while (Current_Group_Id.length() < 3) {
                                Current_Group_Id = "0" + Current_Group_Id;
                            }
                        }else
                            Current_Group_Id = "000";
                        if ((Current_WRLS_DevType == 718)) {
                            deviceTypeForPirPanel = "4";
                        } else if ((Current_WRLS_DevType == 720)) {
                            deviceTypeForPirPanel = "5";
                        } else {
                            deviceTypeForPirPanel = "6";
                        }


                        Current_Pirlightsensorval_WRLS = "0";

                        Flag_OFF_ON = "ON";
                        LocalDataRef = RGB_ON_CMD;
                        LocalDataRef2 = RGB_OFF_CMD;


                        String tempdeviceno = String.valueOf(Current_DevNo);
                        String temproomno = String.valueOf(Current_RoomNo);
                        while (tempdeviceno.length() < 4)
                            tempdeviceno = "0" + tempdeviceno;
                        while (temproomno.length() < 2)
                            temproomno = "0" + temproomno;

                        SwitchData_WLS = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef + "00000000000000" + deviceTypeForPirPanel;
                        SwitchData_WLS2 = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef2 + "00000000000000" + deviceTypeForPirPanel;


                        //setting house name for wireless database
                        StaticVariables.WHOUSE_NAME = StaticVariables.HOUSE_NAME + "_WLS";
                        ;
                        try {
                            WhouseDB = new WirelessConfigurationAdapter(getActivity());
                            WhouseDB.open();            //opening wireless database
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            isDbExists = WhouseDB.checkdb();
                        } catch (IOException e) {
                            //e.printStackTrace();
                            StaticVariables.printLog("TAG", "unable to open database");
                        }
                        WhouseDB.open();
                        ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(), deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                        if (Additionalinfo != null && Additionalinfo.size() > 0) {

                            String tempLocalDataRef = LocalDataRef;
                            String tempSwitchData_WLS = SwitchData_WLS;
                            String tempSwitchNumbers = SwitchNumbers;

                            LocalDataRef = Additionalinfo.get(0).get("localrefdata");
                            SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                            SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber");
                            Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");
                            {
                                if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                    LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                    SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                                } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                    LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                    SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                    SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                }
                            }
                        }

                        // Insert/update data to wirelesstable //
                        boolean isInserted = true;
                        WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                        if (isInserted) {
                            SwitchNumbers = "4";
                            Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(), deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                            if (Additionalinfo != null && Additionalinfo.size() > 0) {
                                String SwitchData_RGB_brightness_data = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + RGB_brightness_data + "00000000000000" + deviceTypeForPirPanel;


                                String tempLocalDataRef = RGB_brightness_data;
                                String tempSwitchData_WLS = SwitchData_RGB_brightness_data;
                                String tempSwitchNumbers = SwitchNumbers;

                                RGB_brightness_data = Additionalinfo.get(0).get("localrefdata");
                                SwitchData_brightness_data = Additionalinfo.get(0).get("switchdata");
                                SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber");
                                Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                                for (int i = 131; i <= 140; i++) {
                                    String brightness_data = String.valueOf(i);
                                    String SwitchData_brightness_data_temp;
                                    if (brightness_data != null && RGB_brightness_data.contains(brightness_data)) {
                                        RGB_brightness_data = RGB_brightness_data.replaceAll(brightness_data, tempLocalDataRef);
                                        SwitchData_brightness_data_temp = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + brightness_data + "00000000000000" + deviceTypeForPirPanel;

                                        SwitchData_brightness_data = SwitchData_brightness_data.replaceAll(SwitchData_brightness_data_temp, tempSwitchData_WLS);
                                        // SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                        break;
                                    }

                                }
                                if (tempLocalDataRef != null && !RGB_brightness_data.contains(tempLocalDataRef)) {
                                    RGB_brightness_data = RGB_brightness_data + "," + tempLocalDataRef;
                                    SwitchData_brightness_data = SwitchData_brightness_data + "," + tempSwitchData_WLS;
                                    SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                }
                                isInserted = true;
                                WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, RGB_brightness_data, PanelSwitchNumber, SwitchData_brightness_data, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                            }
                        }
                        WhouseDB.close();

                        if (isInserted) {
                            SwitchNumbers = "1";
                            View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                            final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                            popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                            popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                            TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                            alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);


                            alertmsg1.setText(getText(R.string.pirselectalert));


                            TextView skip = (TextView) popupview.findViewById(R.id.skip);
                            skip.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();

                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //checkbtn.check();
                                    //flagconf.setText("1");
                                    fanSpeed = "0";
                                }
                            });

                            TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                            pircontinue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                    PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                    if (Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                        Flag_OFF_ON = "OFF";

                                        LocalDataRef = RGB_OFF_CMD;
                                        LocalDataRef2 = RGB_ON_CMD;

                                    } else if (Flag_OFF_ON.equalsIgnoreCase("OFF")) {
                                        Flag_OFF_ON = "ON";

                                        LocalDataRef2 = RGB_OFF_CMD;
                                        LocalDataRef = RGB_ON_CMD;
                                    }
                                    String tempdeviceno = String.valueOf(Current_DevNo);
                                    String temproomno = String.valueOf(Current_RoomNo);
                                    while (tempdeviceno.length() < 4)
                                        tempdeviceno = "0" + tempdeviceno;
                                    while (temproomno.length() < 2)
                                        temproomno = "0" + temproomno;

                                    SwitchData_WLS = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef + "00000000000000" + deviceTypeForPirPanel;
                                    SwitchData_WLS2 = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef2 + "00000000000000" + deviceTypeForPirPanel;


                                    //setting house name for wireless database
                                    StaticVariables.WHOUSE_NAME = StaticVariables.HOUSE_NAME + "_WLS";
                                    ;
                                    try {
                                        WhouseDB = new WirelessConfigurationAdapter(getActivity());
                                        WhouseDB.open();            //opening wireless database
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        isDbExists = WhouseDB.checkdb();
                                    } catch (IOException e) {
                                        //e.printStackTrace();
                                        StaticVariables.printLog("TAG", "unable to open database");
                                    }
                                    WhouseDB.open();
                                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(), deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                    if (Additionalinfo != null && Additionalinfo.size() > 0) {

                                        String tempLocalDataRef = LocalDataRef;

                                        String tempSwitchData_WLS = SwitchData_WLS;
                                        String tempSwitchNumbers = SwitchNumbers;

                                        LocalDataRef = Additionalinfo.get(0).get("localrefdata");
                                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber");
                                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                        if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                        } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                            SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                            SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                        }


                                        //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                    }

                                    // Insert/update data to wirelesstable //
                                    boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                    WhouseDB.close();
                                    popupWindow.dismiss();
                                    if (isInserted) {
                                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                        popup_wired("Wired Settings Uploaded Successfully ");
                                        //setPIRpannelsetting();
                                        fanSpeed = "0";
                                    }
                                }
                            });
                            s_popup_rgb_brightness_popupWindow.dismiss();
                        }
                    }

                }else if (Flag_type.equalsIgnoreCase("Transaction")) {

                    RGB_brightness_data = String.valueOf(np.getValue());

                    if (np.getValue() < 10)
                        RGB_brightness_data = "12" + RGB_brightness_data;
                    else
                        RGB_brightness_data = "130";
                    houseDB.open();
                    ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                    houseDB.close();

                    if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                        final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                        Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                        Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                        Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                        Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                        Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                        Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                        Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                        LocalDataRef = "";                                                              // ea
                        PanelSwitchNumber = 101;                                                             // wsn
                        SwitchData_WLS = "";                                                            // wsd
                        Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                        Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                        Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                        SwitchNumbers = "1";                                                             // bb
                        Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                        Current_Pirlightsensorval_WRLS = "";                                            // eb
                        Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec
                        Current_Group_Id = deviceListArrayobj.getDevgroupId();
                        if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                            while (Current_Group_Id.length() < 3) {
                                Current_Group_Id = "0" + Current_Group_Id;
                            }
                        }else
                            Current_Group_Id = "000";
                        if ((Current_WRLS_DevType == 718)) {
                            deviceTypeForPirPanel = "4";
                        } else if ((Current_WRLS_DevType == 720)) {
                            deviceTypeForPirPanel = "5";
                        } else {
                            deviceTypeForPirPanel = "6";
                        }


                        Current_Pirlightsensorval_WRLS = "0";

                        Flag_OFF_ON = "ON";
                        LocalDataRef = RGB_ON_CMD;
                        LocalDataRef2 = RGB_OFF_CMD;


                        String tempdeviceno = String.valueOf(Current_DevNo);
                        String temproomno = String.valueOf(Current_RoomNo);
                        while (tempdeviceno.length() < 4)
                            tempdeviceno = "0" + tempdeviceno;
                        while (temproomno.length() < 2)
                            temproomno = "0" + temproomno;

                        SwitchData_WLS = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef + "00000000000000" + deviceTypeForPirPanel;
                        SwitchData_WLS2 = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef2 + "00000000000000" + deviceTypeForPirPanel;


                        //setting house name for wireless database
                        StaticVariables.WHOUSE_NAME = StaticVariables.HOUSE_NAME + "_WLS";
                        ;
                        try {
                            WhouseDB = new WirelessConfigurationAdapter(getActivity());
                            WhouseDB.open();            //opening wireless database
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            isDbExists = WhouseDB.checkdb();
                        } catch (IOException e) {
                            //e.printStackTrace();
                            StaticVariables.printLog("TAG", "unable to open database");
                        }
                        WhouseDB.open();
                        ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(), deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                        if (Additionalinfo != null && Additionalinfo.size() > 0) {

                            String tempLocalDataRef = LocalDataRef;
                            String tempSwitchData_WLS = SwitchData_WLS;
                            String tempSwitchNumbers = SwitchNumbers;

                            LocalDataRef = Additionalinfo.get(0).get("localrefdata");
                            SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                            SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber");
                            Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");
                            {
                                if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                    LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                    SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                                } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                    LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                    SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                    SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                }
                            }
                        }
                        // Insert/update data to wirelesstable //
                        boolean isInserted = true;
                        WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                        if (isInserted) {
                            SwitchNumbers = "5";
                            Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(), deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                            if (Additionalinfo != null && Additionalinfo.size() > 0) {
                                String SwitchData_RGB_brightness_data = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + RGB_brightness_data + "00000000000000" + deviceTypeForPirPanel;


                                String tempLocalDataRef = RGB_brightness_data;
                                String tempSwitchData_WLS = SwitchData_RGB_brightness_data;
                                String tempSwitchNumbers = SwitchNumbers;

                                RGB_brightness_data = Additionalinfo.get(0).get("localrefdata");
                                SwitchData_brightness_data = Additionalinfo.get(0).get("switchdata");
                                SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber");
                                Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                                for (int i = 121; i <= 130; i++) {
                                    String brightness_data = String.valueOf(i);
                                    String SwitchData_brightness_data_temp;
                                    if (brightness_data != null && RGB_brightness_data.contains(brightness_data)) {
                                        RGB_brightness_data = RGB_brightness_data.replaceAll(brightness_data, tempLocalDataRef);
                                        SwitchData_brightness_data_temp = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + brightness_data + "00000000000000" + deviceTypeForPirPanel;

                                        SwitchData_brightness_data = SwitchData_brightness_data.replaceAll(SwitchData_brightness_data_temp, tempSwitchData_WLS);
                                        break;
                                    }

                                }
                                if (tempLocalDataRef != null && !RGB_brightness_data.contains(tempLocalDataRef)) {
                                    RGB_brightness_data = RGB_brightness_data + "," + tempLocalDataRef;
                                    SwitchData_brightness_data = SwitchData_brightness_data + "," + tempSwitchData_WLS;
                                    SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                }
                                isInserted = true;
                                WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, RGB_brightness_data, PanelSwitchNumber, SwitchData_brightness_data, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                            }
                        }
                        WhouseDB.close();

                        if (isInserted) {
                            SwitchNumbers = "1";
                            View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                            final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                            popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                            popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                            TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                            alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);


                            alertmsg1.setText(getText(R.string.pirselectalert));


                            TextView skip = (TextView) popupview.findViewById(R.id.skip);
                            skip.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();

                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //checkbtn.check();
                                    //flagconf.setText("1");
                                    fanSpeed = "0";
                                }
                            });

                            TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                            pircontinue.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                    PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                    if (Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                        Flag_OFF_ON = "OFF";

                                        LocalDataRef = RGB_OFF_CMD;
                                        LocalDataRef2 = RGB_ON_CMD;

                                    } else if (Flag_OFF_ON.equalsIgnoreCase("OFF")) {
                                        Flag_OFF_ON = "ON";

                                        LocalDataRef2 = RGB_OFF_CMD;
                                        LocalDataRef = RGB_ON_CMD;
                                    }
                                    String tempdeviceno = String.valueOf(Current_DevNo);
                                    String temproomno = String.valueOf(Current_RoomNo);
                                    while (tempdeviceno.length() < 4)
                                        tempdeviceno = "0" + tempdeviceno;
                                    while (temproomno.length() < 2)
                                        temproomno = "0" + temproomno;

                                    SwitchData_WLS = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef + "00000000000000" + deviceTypeForPirPanel;
                                    SwitchData_WLS2 = "0" + "01" + Current_Group_Id + tempdeviceno + temproomno + LocalDataRef2 + "00000000000000" + deviceTypeForPirPanel;


                                    //setting house name for wireless database
                                    StaticVariables.WHOUSE_NAME = StaticVariables.HOUSE_NAME + "_WLS";
                                    ;
                                    try {
                                        WhouseDB = new WirelessConfigurationAdapter(getActivity());
                                        WhouseDB.open();            //opening wireless database
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        isDbExists = WhouseDB.checkdb();
                                    } catch (IOException e) {
                                        //e.printStackTrace();
                                        StaticVariables.printLog("TAG", "unable to open database");
                                    }
                                    WhouseDB.open();
                                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(), deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                    if (Additionalinfo != null && Additionalinfo.size() > 0) {

                                        String tempLocalDataRef = LocalDataRef;

                                        String tempSwitchData_WLS = SwitchData_WLS;
                                        String tempSwitchNumbers = SwitchNumbers;

                                        LocalDataRef = Additionalinfo.get(0).get("localrefdata");
                                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber");
                                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                        if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                            LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                        } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                            SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                            SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                        }


                                        //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                    }

                                    // Insert/update data to wirelesstable //
                                    boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                    WhouseDB.close();
                                    popupWindow.dismiss();
                                    if (isInserted) {
                                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                        popup_wired("Wired Settings Uploaded Successfully ");
                                        //setPIRpannelsetting();
                                        fanSpeed = "0";
                                    }
                                }
                            });
                            s_popup_rgb_brightness_popupWindow.dismiss();
                        }
                    }
                }
            }
        });
    }
    // Curtain //
    public void curtainPopupShow(View view, final int i, final DeviceListArray deviceListArrayobj){
        View popupview = ((Activity)getActivity()).getLayoutInflater().inflate(R.layout.s_popup_curtain_operation_selection,null,false);
        final PopupWindow curtainPopup_one = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT,true);
        curtainPopup_one.setAnimationStyle(R.style.PauseDialogAnimation);
        curtainPopup_one.showAtLocation(popupview, Gravity.CENTER,0,0);

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        TextView titletxt = (TextView) popupview.findViewById(R.id.titletxt);

        final TextView android_gridview_text = (TextView) view.findViewById(R.id.android_gridview_text);
        titletxt.setText(""+android_gridview_text.getText().toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curtainPopup_one.dismiss();
            }
        });

        final ImageView curtainopen = (ImageView) popupview.findViewById(R.id.curtainopen);
        final ImageView curtainstop = (ImageView) popupview.findViewById(R.id.curtainstop);
        final ImageView curtainclose = (ImageView) popupview.findViewById(R.id.curtainclose);


        curtainopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curtainopen.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cur_open_p));
                curtainstop.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.stop));
                curtainclose.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.close));
                android_gridview_text.setText("Curtain Open");
            }
        });curtainstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curtainopen.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.open));
                curtainstop.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.stop_p));
                curtainclose.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.close));
                android_gridview_text.setText("Curtain Stop");
            }
        });curtainclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curtainopen.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.open));
                curtainstop.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.stop));
                curtainclose.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cur_close_p));
                android_gridview_text.setText("Curtain Close");
            }
        });

        final TextView set = (TextView) popupview.findViewById(R.id.set) ;

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "1";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec
                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";
                    LocalDataRef3 = "";
                    SwitchData_WLS3="";


                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";
                    if(android_gridview_text.getText().toString().equalsIgnoreCase("Curtain Open")) {
                        Flag_OFF_ON = "OPEN";
                        LocalDataRef = CUR1_OPEN_CMD;
                        LocalDataRef2 = CUR1_CLOSE_CMD;
                        LocalDataRef3 = CUR1_STOP_CMD;
                    }
                    else if(android_gridview_text.getText().toString().equalsIgnoreCase("Curtain Close")){
                        Flag_OFF_ON = "CLOSE" ;
                        LocalDataRef = CUR1_CLOSE_CMD;
                        LocalDataRef2 = CUR1_OPEN_CMD;
                        LocalDataRef3 = CUR1_STOP_CMD;
                    }else if(android_gridview_text.getText().toString().equalsIgnoreCase("Curtain Stop")){
                        Flag_OFF_ON = "STOP" ;
                        LocalDataRef = CUR1_STOP_CMD;
                        LocalDataRef2 = CUR1_CLOSE_CMD;
                        LocalDataRef3 = CUR1_OPEN_CMD;
                    }

                    String tempdeviceno = String.valueOf(Current_DevNo);
                    String temproomno = String.valueOf(Current_RoomNo);
                    while(tempdeviceno.length()<4)
                        tempdeviceno="0"+tempdeviceno;
                    while(temproomno.length()<2)
                        temproomno="0"+temproomno;

                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS3 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef3+"00000000000000"+deviceTypeForPirPanel;

                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");

                        {
                            if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);

                            }else if (LocalDataRef3 != null && LocalDataRef.contains(LocalDataRef3)) {
                                LocalDataRef = LocalDataRef.replaceAll(LocalDataRef3, tempLocalDataRef);
                                SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS3, tempSwitchData_WLS);

                            } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                            }
                        }
                    }

                    // Insert/update data to wirelesstable //
                    boolean isInserted = true; WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                    WhouseDB.close();

                    if (isInserted) {

                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                        if (android_gridview_text.getText().toString().equalsIgnoreCase("Turned OFF")) {
                            alertmsg1.setText(getText(R.string.pirselectalert2));
                        } else if (android_gridview_text.getText().toString().equalsIgnoreCase("Turned ON")) {
                            alertmsg1.setText(getText(R.string.pirselectalert));
                        }

                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");
                                // setPIRpannelsetting();
                                if (android_gridview_text.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                   /* if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan01);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb01);*/
                                } else if (android_gridview_text.getText().toString().equalsIgnoreCase("Turned ON")) {
                                    /*if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan02);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb03);*/
                                }
                                //checkbtn.check();
                                //flagconf.setText("1");

                                fanSpeed = "0" ;

                            }
                        });

                        final TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        final TextView curtainstop = (TextView) popupview.findViewById(R.id.curtainstop);
                        curtainstop.setVisibility(View.VISIBLE);

                        if(Flag_OFF_ON.equalsIgnoreCase("OPEN"))
                            pircontinue.setText("Close");
                        else if(Flag_OFF_ON.equalsIgnoreCase("CLOSE"))
                            pircontinue.setText("Open");
                        else if(Flag_OFF_ON.equalsIgnoreCase("STOP")) {
                            pircontinue.setText("Close");
                            curtainstop.setText("Open");
                        }

                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(pircontinue.getText().toString().equalsIgnoreCase("Open")) {
                                    Flag_OFF_ON = "OPEN";
                                    LocalDataRef = CUR1_OPEN_CMD;
                                    LocalDataRef2 = CUR1_CLOSE_CMD;
                                    LocalDataRef3 = CUR1_STOP_CMD;
                                }
                                else if(pircontinue.getText().toString().equalsIgnoreCase("Close")){
                                    Flag_OFF_ON = "CLOSE" ;
                                    LocalDataRef = CUR1_CLOSE_CMD;
                                    LocalDataRef2 = CUR1_OPEN_CMD;
                                    LocalDataRef3 = CUR1_STOP_CMD;
                                }else if(pircontinue.getText().toString().equalsIgnoreCase("Stop")){
                                    Flag_OFF_ON = "STOP" ;
                                    LocalDataRef = CUR1_STOP_CMD;
                                    LocalDataRef2 = CUR1_CLOSE_CMD;
                                    LocalDataRef3 = CUR1_OPEN_CMD;
                                }

                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS3 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef3+"00000000000000"+deviceTypeForPirPanel;


                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    }else if (LocalDataRef3 != null && LocalDataRef.contains(LocalDataRef3)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef3, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS3, tempSwitchData_WLS);


                                    }  else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }


                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();




                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        curtainstop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(curtainstop.getText().toString().equalsIgnoreCase("Open")) {
                                    Flag_OFF_ON = "OPEN";
                                    LocalDataRef = CUR1_OPEN_CMD;
                                    LocalDataRef2 = CUR1_CLOSE_CMD;
                                    LocalDataRef3 = CUR1_STOP_CMD;
                                }
                                else if(curtainstop.getText().toString().equalsIgnoreCase("Close")){
                                    Flag_OFF_ON = "CLOSE" ;
                                    LocalDataRef = CUR1_CLOSE_CMD;
                                    LocalDataRef2 = CUR1_OPEN_CMD;
                                    LocalDataRef3 = CUR1_STOP_CMD;
                                }else if(curtainstop.getText().toString().equalsIgnoreCase("Stop")){
                                    Flag_OFF_ON = "STOP" ;
                                    LocalDataRef = CUR1_STOP_CMD;
                                    LocalDataRef2 = CUR1_CLOSE_CMD;
                                    LocalDataRef3 = CUR1_OPEN_CMD;
                                }

                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS3 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef3+"00000000000000"+deviceTypeForPirPanel;


                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    }else if (LocalDataRef3 != null && LocalDataRef.contains(LocalDataRef3)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef3, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS3, tempSwitchData_WLS);


                                    }  else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }

                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);
                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();
                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        curtainPopup_one.dismiss();
                    }

                }
            }
        });
    }
    // GardenSprinkler //
    public void GardenSprinklerPopupShow(View view, final int i, final DeviceListArray deviceListArrayobj) {
        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_switch_select, null, false);
        final PopupWindow GardenSprinklePopup_one = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        GardenSprinklePopup_one.setAnimationStyle(R.style.PauseDialogAnimation);
        GardenSprinklePopup_one.showAtLocation(popupview, Gravity.CENTER, 0, 0);

        final ImageView android_gridview_image_popup = (ImageView) popupview.findViewById(R.id.android_gridview_image) ;
        final TextView android_gridview_text_popup = (TextView) popupview.findViewById(R.id.android_gridview_text) ;
        final TextView set = (TextView) popupview.findViewById(R.id.set) ;

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GardenSprinklePopup_one.dismiss();
            }
        });

        android_gridview_image_popup.setImageResource(R.mipmap.sprinkler_grid_p);
        android_gridview_text_popup.setText("Turned ON");

        RelativeLayout selectonoff = (RelativeLayout) popupview.findViewById(R.id.selectonoff);

        selectonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")){
                    android_gridview_image_popup.setImageResource(R.mipmap.sprinkler_grid_p);
                    android_gridview_text_popup.setText("Turned ON");
                }else if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")){
                    android_gridview_image_popup.setImageResource(R.mipmap.sprinkler_grid_n);
                    android_gridview_text_popup.setText("Turned OFF");
                }

            }
        });




        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec

                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";

                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";
                    if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF"))
                        Flag_OFF_ON = "OFF" ;
                    else
                        Flag_OFF_ON = "ON" ;

                    if(Flag_OFF_ON.equalsIgnoreCase("ON")) {

                        LocalDataRef = "201";
                        LocalDataRef2 = "301";

                    }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){

                        LocalDataRef = "301";
                        LocalDataRef2 = "201";
                    }

                    String tempdeviceno = String.valueOf(Current_DevNo);
                    String temproomno = String.valueOf(Current_RoomNo);
                    while(tempdeviceno.length()<4)
                        tempdeviceno="0"+tempdeviceno;
                    while(temproomno.length()<2)
                        temproomno="0"+temproomno;

                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;



                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");



                        if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                            LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                        } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                            SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                            SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                        }


                    }

                    // Insert/update data to wirelesstable //
                    boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                    WhouseDB.close();

                    if (isInserted) {

                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                        if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {
                            alertmsg1.setText(getText(R.string.pirselectalert2));
                        } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                            alertmsg1.setText(getText(R.string.pirselectalert));
                        }

                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");
                                // setPIRpannelsetting();
                                /* if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                    if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan01);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb01);
                                } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                                    if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan02);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb03);
                                }
                                checkbtn.check();
                                flagconf.setText("1");*/

                                fanSpeed = "0" ;

                            }
                        });

                        TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                    Flag_OFF_ON = "OFF";
                                else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    Flag_OFF_ON = "ON";




                                if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                    LocalDataRef = "201";
                                    LocalDataRef2 = "301";
                                }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){
                                    LocalDataRef = "301";
                                    LocalDataRef2 = "201";
                                }


                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;



                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    /*if(switchtitle.contains("Switch") && switchtitle.contains(" ")){
                                        if(LocalDataRef.contains(",")){
                                            String[] tempLocalDataRefarry = LocalDataRef.split(",");

                                            for(int k=0;k<tempLocalDataRefarry.length;k++){
                                                if(tempLocalDataRefarry[k].contains(tempLocalDataRef) || tempLocalDataRefarry[k].contains(tempLocalDataRef2)){

                                                }
                                            }


                                        }else {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef ;
                                        }
                                    }*/


                                    if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }

                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }

                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");
                                    //setPIRpannelsetting();


                                   /* if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                        if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                            android_gridview_image.setImageResource(R.mipmap.fan01);
                                        else
                                            android_gridview_image.setImageResource(R.mipmap.bulb01);
                                    } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                                        if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                            android_gridview_image.setImageResource(R.mipmap.fan02);
                                        else
                                            android_gridview_image.setImageResource(R.mipmap.bulb03);
                                    }
                                    checkbtn.check();
                                    flagconf.setText("1");*/

                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        GardenSprinklePopup_one.dismiss();
                    }

                }
            }
        });

    }
    // Dimmer //
    public void DimmerPopupShow(View view, final int i, final DeviceListArray deviceListArrayobj) {
        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_dimmer_operation, null, false);
        final PopupWindow DimmerPopup_one = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        DimmerPopup_one.setAnimationStyle(R.style.PauseDialogAnimation);
        DimmerPopup_one.showAtLocation(popupview, Gravity.CENTER, 0, 0);

        TextView cancel = (TextView) popupview.findViewById(R.id.cancel);
        TextView set = (TextView) popupview.findViewById(R.id.set);
        TextView titletxt = (TextView) popupview.findViewById(R.id.titletxt);

        final TextView android_gridview_text = (TextView) view.findViewById(R.id.android_gridview_text);
        titletxt.setText(""+android_gridview_text.getText().toString());

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DimmerPopup_one.dismiss();
            }
        });


        final ShaderSeekArc seekArc = (ShaderSeekArc) popupview.findViewById(R.id.seek_arc2);

        final int low = Integer.parseInt(DMR_LOW_CMD);
        final int med = Integer.parseInt(DMR_MEDIUM_CMD);
        final int high = Integer.parseInt(DMR_HIGH_CMD);


        final ImageView image_dimmer_low = (ImageView) popupview.findViewById(R.id.image_dimmer_low);
        final ImageView image_dimmer_medium = (ImageView) popupview.findViewById(R.id.image_dimmer_medium);
        final ImageView image_dimmer_high = (ImageView) popupview.findViewById(R.id.image_dimmer_high);

        image_dimmer_medium.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_medium01));
        seekArc.setProgress(med);

        image_dimmer_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_dimmer_low.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_low01));
                image_dimmer_medium.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_medium));
                image_dimmer_high.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_high));

                seekArc.setProgress(low);
            }
        });image_dimmer_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_dimmer_low.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_low));
                image_dimmer_medium.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_medium01));
                image_dimmer_high.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_high));

                seekArc.setProgress(med);

            }
        });image_dimmer_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image_dimmer_low.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_low));
                image_dimmer_medium.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_medium));
                image_dimmer_high.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_high01));

                seekArc.setProgress(high);

            }
        });

        seekArc.setOnSeekArcChangeListener(new ShaderSeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(ShaderSeekArc seekArc, float progress) {


            }

            @Override
            public void onStartTrackingTouch(ShaderSeekArc seekArc) {
                image_dimmer_low.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_low));
                image_dimmer_medium.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_medium));
                image_dimmer_high.setImageDrawable(getResources().getDrawable(R.drawable.dimmer_high));

            }

            @Override
            public void onStopTrackingTouch(ShaderSeekArc seekArc) {

                //Toast.makeText(getActivity(),"stoped",Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout selectonoff = (RelativeLayout) popupview.findViewById(R.id.selectonoff);
        final LinearLayout brightnesslayout = (LinearLayout) popupview.findViewById(R.id.brightnesslayout);
        final ImageView android_gridview_image_popup = (ImageView) popupview.findViewById(R.id.android_gridview_image) ;
        final TextView android_gridview_text_popup = (TextView) popupview.findViewById(R.id.android_gridview_text) ;


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int brightnesslevel= (int) seekArc.getProgress();
                String brightnesslevelstr = String.valueOf(brightnesslevel);
                while (brightnesslevelstr.length()<3)
                    brightnesslevelstr = "0"+brightnesslevelstr;

                Toast.makeText(getActivity(),"value : "+brightnesslevelstr,Toast.LENGTH_SHORT).show();


                houseDB.open();
                ArrayList<DeviceListArray> WirelessdeviceListArrays = houseDB.getWirelessDeviceInfo(StaticVariabes_div.devnumber);
                houseDB.close();

                if (WirelessdeviceListArrays != null && WirelessdeviceListArrays.size() > 0) {
                    final DeviceListArray deviceListArraywireless = WirelessdeviceListArrays.get(0);
                    Current_RoomNo = Integer.parseInt(deviceListArrayobj.getDeviceRoomNumber());       // a
                    Current_RoomName = deviceListArrayobj.getDeviceRoomName();                      // b
                    Current_WRLS_DevName = deviceListArraywireless.getDeviceShortName();            // wdn
                    Current_WRLS_DevNo = Integer.parseInt(deviceListArraywireless.getDeviceNumber());  // wd
                    Current_WRLS_DevType = Integer.parseInt(deviceListArraywireless.getDeviceType());  // wc
                    Current_WRLS_DevID = deviceListArraywireless.getDeviceId();                     // wf
                    Current_DevName = deviceListArrayobj.getDeviceShortName();                      // dn
                    LocalDataRef = "";                                                              // ea
                    PanelSwitchNumber = 101;                                                             // wsn
                    SwitchData_WLS = "";                                                            // wsd
                    Current_DevType = deviceListArrayobj.getDeviceType();                           // c
                    Current_DevNo = Integer.parseInt(deviceListArrayobj.getDeviceNumber());            // d
                    Current_DevID = deviceListArrayobj.getDeviceId();                               // f
                    SwitchNumbers = "1";                                                             // bb
                    Current_WRLS_RoomName = deviceListArraywireless.getDeviceRoomName();            // wb
                    Current_Pirlightsensorval_WRLS = "";                                            // eb
                    Current_User_Dev_Name = deviceListArrayobj.getDeviceName();                     // ec

                    Current_Group_Id = deviceListArrayobj.getDevgroupId();
                    if(Current_Group_Id!=null && Current_Group_Id.length()>0) {
                        while (Current_Group_Id.length() < 3) {
                            Current_Group_Id = "0" + Current_Group_Id;
                        }
                    }else
                        Current_Group_Id = "000";

                    if((Current_WRLS_DevType==718)){
                        deviceTypeForPirPanel="4";
                    }else
                    if((Current_WRLS_DevType==720)){
                        deviceTypeForPirPanel="5";
                    }else{
                        deviceTypeForPirPanel="6";
                    }


                    Current_Pirlightsensorval_WRLS = "0";
                    if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF"))
                        Flag_OFF_ON = "OFF" ;
                    else
                        Flag_OFF_ON = "ON" ;

                    if(Flag_OFF_ON.equalsIgnoreCase("ON")) {

                        LocalDataRef = "102";
                        LocalDataRef2 = "103";

                    }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){

                        LocalDataRef = "103";
                        LocalDataRef2 = "102";
                    }

                    String tempdeviceno = String.valueOf(Current_DevNo);
                    String temproomno = String.valueOf(Current_RoomNo);
                    while(tempdeviceno.length()<4)
                        tempdeviceno="0"+tempdeviceno;
                    while(temproomno.length()<2)
                        temproomno="0"+temproomno;

                    SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                    SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;


                    dimmerbrightnessvalue = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+DMR_BRIGHTNESS_PROCESS_CMD+brightnesslevelstr+"00000000000"+deviceTypeForPirPanel;



                    //setting house name for wireless database
                    StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                    try{
                        WhouseDB=new WirelessConfigurationAdapter(getActivity());
                        WhouseDB.open();			//opening wireless database
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    try {
                        isDbExists=WhouseDB.checkdb();
                    } catch (IOException e) {
                        //e.printStackTrace();
                        StaticVariables.printLog("TAG","unable to open database");
                    }
                    WhouseDB.open();
                    ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                    /*if(Additionalinfo!=null && Additionalinfo.size()>0){

                        String tempLocalDataRef = LocalDataRef ;
                        String tempSwitchData_WLS = SwitchData_WLS ;
                        String tempSwitchNumbers = SwitchNumbers ;

                        LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                        SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                        SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                        Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");



                        if (LocalDataRef2 != null && LocalDataRef.contains(LocalDataRef2)) {
                            LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                            SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                        } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                            SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                            SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                        }


                    }*/

                    // Insert/update data to wirelesstable //
                    boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                            Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                            Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                            Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                    if(Flag_OFF_ON.equalsIgnoreCase("ON")){
                        if(isInserted){
                            String LocalDataRef_brt = LocalDataRef+","+brightnesslevelstr;

                            String tempSwitchNumbers_brt =SwitchNumbers+","+"2";

                            dimmerbrightnessvalue = SwitchData_WLS + "," + dimmerbrightnessvalue;

                            isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                    Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                    Current_DevName, LocalDataRef_brt, PanelSwitchNumber, dimmerbrightnessvalue, Current_DevType,
                                    Current_DevNo, Current_DevID, tempSwitchNumbers_brt, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                        }
                    }




                    WhouseDB.close();

                    if (isInserted) {

                        View popupview = ((Activity) getActivity()).getLayoutInflater().inflate(R.layout.s_popup_pir_alert, null, false);
                        final PopupWindow popupWindow = new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                        popupWindow.setAnimationStyle(R.style.PauseDialogAnimation);
                        popupWindow.showAtLocation(popupview, Gravity.CENTER, 0, 0);

                        TextView alertmsg1 = (TextView) popupview.findViewById(R.id.alertmsg1);
                        alertmsg1.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

                        if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {
                            alertmsg1.setText(getText(R.string.pirselectalert2));
                        } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                            alertmsg1.setText(getText(R.string.pirselectalert));
                        }

                        TextView skip = (TextView) popupview.findViewById(R.id.skip);
                        skip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                popupWindow.dismiss();

                                popup_wired("Wired Settings Uploaded Successfully ");
                                // setPIRpannelsetting();
                                /* if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")) {

                                    if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan01);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb01);
                                } else if (android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")) {
                                    if (android_gridview_text.getText().toString().equalsIgnoreCase("fan"))
                                        android_gridview_image.setImageResource(R.mipmap.fan02);
                                    else
                                        android_gridview_image.setImageResource(R.mipmap.bulb03);
                                }
                                checkbtn.check();
                                flagconf.setText("1");*/

                                fanSpeed = "0" ;

                            }
                        });

                        TextView pircontinue = (TextView) popupview.findViewById(R.id.pircontinue);
                        pircontinue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                PanelSwitchNumber = 102;
                               /* else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    PanelSwitchNumber = 101;*/

                                if(Flag_OFF_ON.equalsIgnoreCase("ON"))
                                    Flag_OFF_ON = "OFF";
                                else if(Flag_OFF_ON.equalsIgnoreCase("OFF"))
                                    Flag_OFF_ON = "ON";




                                if(Flag_OFF_ON.equalsIgnoreCase("ON")) {
                                    LocalDataRef = "102";
                                    LocalDataRef2 = "103";
                                }else if(Flag_OFF_ON.equalsIgnoreCase("OFF")){
                                    LocalDataRef = "103";
                                    LocalDataRef2 = "102";
                                }


                                String tempdeviceno = String.valueOf(Current_DevNo);
                                String temproomno = String.valueOf(Current_RoomNo);
                                while(tempdeviceno.length()<4)
                                    tempdeviceno="0"+tempdeviceno;
                                while(temproomno.length()<2)
                                    temproomno="0"+temproomno;

                                SwitchData_WLS = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef+"00000000000000"+deviceTypeForPirPanel;
                                SwitchData_WLS2 = "0"+"01"+Current_Group_Id+tempdeviceno+temproomno+LocalDataRef2+"00000000000000"+deviceTypeForPirPanel;



                                //setting house name for wireless database
                                StaticVariables.WHOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";;
                                try{
                                    WhouseDB=new WirelessConfigurationAdapter(getActivity());
                                    WhouseDB.open();			//opening wireless database
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                    isDbExists=WhouseDB.checkdb();
                                } catch (IOException e) {
                                    //e.printStackTrace();
                                    StaticVariables.printLog("TAG","unable to open database");
                                }
                                WhouseDB.open();
                                ArrayList<HashMap<String, String>> Additionalinfo = WhouseDB.WirelessPanelsAdditionaleInfo(deviceListArraywireless.getDeviceNumber(),deviceListArrayobj.getDeviceNumber(), String.valueOf(PanelSwitchNumber));

                                if(Additionalinfo!=null && Additionalinfo.size()>0){

                                    String tempLocalDataRef = LocalDataRef ;

                                    String tempSwitchData_WLS = SwitchData_WLS ;
                                    String tempSwitchNumbers = SwitchNumbers ;

                                    LocalDataRef = Additionalinfo.get(0).get("localrefdata") ;
                                    SwitchData_WLS = Additionalinfo.get(0).get("switchdata");
                                    SwitchNumbers = Additionalinfo.get(0).get("deviceswitchnumber") ;
                                    Current_Pirlightsensorval_WRLS = Additionalinfo.get(0).get("lightsensorval");


                                    /*if(switchtitle.contains("Switch") && switchtitle.contains(" ")){
                                        if(LocalDataRef.contains(",")){
                                            String[] tempLocalDataRefarry = LocalDataRef.split(",");

                                            for(int k=0;k<tempLocalDataRefarry.length;k++){
                                                if(tempLocalDataRefarry[k].contains(tempLocalDataRef) || tempLocalDataRefarry[k].contains(tempLocalDataRef2)){

                                                }
                                            }


                                        }else {
                                            LocalDataRef = LocalDataRef + "," + tempLocalDataRef ;
                                        }
                                    }*/


                                    if (tempLocalDataRef != null && LocalDataRef.contains(LocalDataRef2)) {
                                        LocalDataRef = LocalDataRef.replaceAll(LocalDataRef2, tempLocalDataRef);
                                        SwitchData_WLS = SwitchData_WLS.replaceAll(SwitchData_WLS2, tempSwitchData_WLS);


                                    } else if (tempLocalDataRef != null && !LocalDataRef.contains(tempLocalDataRef)) {
                                        LocalDataRef = LocalDataRef + "," + tempLocalDataRef;
                                        SwitchData_WLS = SwitchData_WLS + "," + tempSwitchData_WLS;
                                        SwitchNumbers = SwitchNumbers + "," + tempSwitchNumbers;
                                    }

                                    //if(LocalDataRef.contains("711") || LocalDataRef.contains("712") || LocalDataRef.contains("713") || LocalDataRef.contains("714") || )
                                }
                                // Insert/update data to wirelesstable //
                                boolean isInserted = WhouseDB.Update_WRLS_details_PIR(Current_RoomNo, Current_RoomName,
                                        Current_WRLS_DevName, Current_WRLS_DevNo, Current_WRLS_DevType, Current_WRLS_DevID,
                                        Current_DevName, LocalDataRef, PanelSwitchNumber, SwitchData_WLS, Current_DevType,
                                        Current_DevNo, Current_DevID, SwitchNumbers, Current_WRLS_RoomName, Current_Pirlightsensorval_WRLS, Current_User_Dev_Name);

                                WhouseDB.close();
                                popupWindow.dismiss();
                                if(isInserted) {
                                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                                    popup_wired("Wired Settings Uploaded Successfully ");

                                    fanSpeed = "0" ;
                                }
                            }
                        });

                        DimmerPopup_one.dismiss();
                    }

                }






            }
        });


        selectonoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned OFF")){

                    android_gridview_image_popup.setImageResource(R.mipmap.dimmer_grid_p);
                    android_gridview_text_popup.setText("Turned ON");

                    brightnesslayout.setVisibility(View.VISIBLE);

                }else if(android_gridview_text_popup.getText().toString().equalsIgnoreCase("Turned ON")){
                    android_gridview_image_popup.setImageResource(R.mipmap.dimmer_grid_n);
                    android_gridview_text_popup.setText("Turned OFF");
                    brightnesslayout.setVisibility(View.GONE);

                }

            }
        });
    }
    //# ============================================================================== #//

    //# ============================================================================== #//
    // FINDING NUMBER OF SWITCHES AND NUMBER OF FAN FROM DEVICE TYPE //
    int noofSwitches(String devType){
        switch (devType){
            case "S010" : return 1;
            case "S020" : return 2;
            case "S030" : return 3;
            case "S080" : return 8;
            case "S021" : return 2;
            case "S051" : return 5;
            default:return 0;
        }
    }
    private int noofFans(String devType){
        switch (devType){
            case "S021" : return 1;
            case "S051" : return 1;
            default:return 0;
        }
    }
    //# ============================================================================== #//

    //# ============================================================================== #//
    // METHOD'S TO UPLOADING UPDATED WIRLESS DB TO GATEWAY //
    private void setPIRpannelsetting(){
        devno = StaticVariabes_div.devnumber;
        while (devno.length() < 4) devno = "0" + devno;
        String str = "$116," + devno + "&";
        StaticVariabes_div.log("out" + str, TAG1);
        if (Tcp_con.isClientStarted) {
            StaticVariabes_div.log("started" + str, TAG1);
            Tcp_con.WriteBytes(str.getBytes());
            Toast.makeText(getActivity(),"Set : "+str,Toast.LENGTH_SHORT).show();
        } else {
            StaticVariabes_div.log("not started" + str, TAG1);
        }
        Track_button_event("Device Features & Settings", StaticVariabes_div.Selected_device + " Dir Set", "Dir Set Shortclick");

    }
    public void popup_wired(String msg) {

      /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("INFO");
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();*/
        //  onShift();
        if (Tcp_con.isClientStarted) {
            progressstart("Uploading Wireless Settings.....");

            long delayInMillis = 8000;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    progressstop ();
                }
            }, delayInMillis);

            Thread upwrlsThread = new Thread() {
                public void run() {
                    sPackName = getActivity().getPackageName();
                    UploadDb(sPackName, StaticVariabes_div.housename+"_WLS" + ".db","$122&");
                }
            };upwrlsThread.start();

            //tv_poperr.setText("Uploading Please wait ..........");
        }

                /*    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();*/
    }
    void progressstart(final String Messg) {
        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressBar == null) {
                    progressBar = new ProgressDialog(getActivity());
                    progressBar.setCancelable(false);
                    progressBar.setMessage(Messg);
                    // progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();
                } else {
                    if (progressBar != null) {
                        progressBar.dismiss();
                        progressBar = null;
                    }
                }
            }
        });
    }
    void progressstop(){
        ((Activity) getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressBar!=null){
                    progressBar.dismiss();
                    progressBar=null;
                }
            }
        });
    }
    public static void UploadDb(String PackageName, String DataBaseName,String cmd) {


        try {
          /*  if(ops!=null){
                ops.write("$117&\r\n".getBytes());
                ops.flush();
            }*/
            //  String command=cmd+"\r\n";
            String command=cmd;
            Tcp_con.WriteBytes(command.getBytes());
            StaticVariabes_div.log("outstream command"+ command,"tcpout");
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String path1 = "//data//" + PackageName + "//databases//" + DataBaseName;
        //String path = "/data/data"+ PackageName +"/databases/"+DataBaseName;
        File data = Environment.getDataDirectory();
        File currentDB = new File(data, path1);
        int flen2 = (int) currentDB.length();
        StaticVariabes_div.log(" * file - "+DataBaseName+" size  "  + flen2, TAG1);


      /*  if(ops!=null){
            try {
                ops.write((flen2 + "\r\n").getBytes());
                ops.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }*/

        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        String flenth=flen2+"";
        Tcp_con.WriteBytes(flenth.getBytes());

        StaticVariabes_div.log("outstream flen2 "+ flen2,"tcpout");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        FileInputStream fin = null;
        byte[] buf = new byte[flen2];
        try {
            fin = new FileInputStream(currentDB);
            BufferedInputStream bis = new BufferedInputStream(fin);
            bis.read(buf, 0, buf.length);
            //BufferedOutputStream bout = new BufferedOutputStream(ops);
            BufferedOutputStream bout = new BufferedOutputStream(Tcp_con.output);
            StaticVariabes_div.log("outstream buf"+ buf.length,"tcpout");
            bout.write(buf, 0, buf.length);
            bout.flush();

            Thread.sleep(500);

            bis.close();
            fin.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //# ============================================================================== #//




}
