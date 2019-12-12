package edisonbro.com.edisonbroautomation.Devices;



/**
 *  FILENAME: Dimmer_Frag.java

 *  Created by shreeshail v

 *  DATE: 07-OCTOBER-2018

 *  DESCRIPTION:  Fragment to operate selected FM Device individually .

 *  Copyright (C) EdisonBro Smart Labs Pvt Ltd. All rights reserved.
 */

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import edisonbro.com.edisonbroautomation.CombFrag;
import edisonbro.com.edisonbroautomation.Connections.TcpTransfer;
import edisonbro.com.edisonbroautomation.Connections.Tcp_con;
import edisonbro.com.edisonbroautomation.Edisonbro_AnalyticsApplication;
import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticStatus;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariables;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp_dwn_config;
import edisonbro.com.edisonbroautomation.databasewired.MasterAdapter;
import edisonbro.com.edisonbroautomation.databasewired.SwbAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.WirelessConfigurationAdapter;
import edisonbro.com.edisonbroautomation.operatorsettings.UpdateHome_Existing;
import edisonbro.com.edisonbroautomation.scrollfunction.ShaderSeekArc;

public class mIR_Frag extends Fragment implements TcpTransfer,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String WIRELESS_HOUSE_NAME = "";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private static final String TAG1="Fm Indi- ";
    //***************************************************************
    int n=0, count=0, intValue = 0;
    String devno, roomno="0", housename, houseno, roomname, groupId = "000", broadcastMsg = "01",devtypesett,model,Roomname;
    //***************************************************************
    private static final int READ_BYTE = 1, READ_LINE = 2
            ,ServStatus = 3, signallevel = 4, NetwrkType = 5, MAXUSER = 6, ERRUSER = 7,  UPDATE=8;
    //************************************************************************
    int sl;
    boolean check=true,statusserv,remoteconn,remoteconn3g,nonetwork;
    String servpreviousstate,remoteconprevstate ,rs, readMessage2,inp;

    //************************************************************************
    Main_Navigation_Activity mn_nav_obj;
    CombFrag combf;
    private Tracker mTracker;
    String name ="Dimmer Individual";
    //********************************************************************************
    int delay = 0; // delay for 1 sec.
    int period = 1000;
    Timer timer = null;
    //********************************************************************
    // public ImageView dim_img;
    View view;
    ImageView i;

    TextView tvtest;



    //**********************added by shreeshail***********************************************
    LinearLayout settingslayout,layou_setting;
    TextView permision;
    TextView tv_res;
   // Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b_on,b_off,b_usd,b_paly_pause,b_mute,b_mode,b_vol_inc,b_vol_dec,b_far,b_back;
    Button btns1,btns2,btns3,btns4,btns5,btns6,btnset,btnclear;
    private String cmds;
    private TextView display,irresponse,swbnotfound;

    RadioGroup swbnamesRadioGroup;

    private SwbAdapter swbadap;
    private MasterAdapter mas_adap;
    private WirelessConfigurationAdapter WhouseDB = null;


    private String Asso_data = "";

    private ArrayList<HashMap<String , String>> swbnamenumber;
    private ArrayList<HashMap<String , String>> deviceTYPE;
    String remoteKEYselection = "1";
    String A_lData = "E";

    private ProgressDialog mProgressDialog;

    private Animation animation;
    int receivedstate = 0;

    private int radioswbindex = 0;

    //****************************************************************

    public mIR_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dimmer_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static mIR_Frag newInstance(String param1, String param2) {
        mIR_Frag fragment = new mIR_Frag();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.mir_frag, container, false);



            new Tcp_con(this);

            if (Tcp_con.isClientStarted) {

                // transmitdata(920);

            } else {
                Tcp_con.stacontxt = getActivity().getApplicationContext();
                Tcp_con.serverdetailsfetch(getActivity(), StaticVariabes_div.housename);
                Tcp_con.registerReceivers(getActivity().getApplicationContext());
            }

            try {
                tvtest = (TextView) view.findViewById(R.id.tvtest);
            } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "msg" + e.toString(), Toast.LENGTH_SHORT).show();

            }

            if (tvtest != null) {
                if (StaticVariabes_div.dev_name != null)
                    tvtest.setText(StaticVariabes_div.dev_name);
            }

            mn_nav_obj = (Main_Navigation_Activity) getActivity();

            Edisonbro_AnalyticsApplication application = (Edisonbro_AnalyticsApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();

            combf = ((CombFrag) this.getParentFragment());
            combf.feature_sett_enab_disable(true);

        settingslayout = (LinearLayout) view.findViewById(R.id.settingslayout);
        layou_setting = (LinearLayout) view.findViewById(R.id.layou_setting);
        permision = (TextView) view.findViewById(R.id.permision);


        if(StaticVariabes_div.loggeduser_type.equals("SA")) {
           /* int a;
            String b, c, d, e, f;
            a = StaticVariabes_div.swbcounts; //
            b = StaticVariabes_div.swbnoa[0];
            c = StaticVariabes_div.swbmodela[0];
            d = StaticVariabes_div.swbdevicenames[0];
            d = StaticVariabes_div.swbnoa[0];
            e = StaticVariabes_div.room_n;
            f = StaticVariabes_div.roomname;*/

            settingslayout.setVisibility(View.VISIBLE);
            swbnotfound = (TextView) view.findViewById(R.id.swbnotfound);

            // Toast.makeText(getContext(),"data : "+e+f+" devno :"+d,Toast.LENGTH_SHORT).show();
            btns1 = (Button) view.findViewById(R.id.btns1);
            btns2 = (Button) view.findViewById(R.id.btns2);
            btns3 = (Button) view.findViewById(R.id.btns3);
            btns4 = (Button) view.findViewById(R.id.btns4);
            btns5 = (Button) view.findViewById(R.id.btns5);
            btns6 = (Button) view.findViewById(R.id.btns6);


            btnset = (Button) view.findViewById(R.id.btnset);

            btnclear = (Button) view.findViewById(R.id.btnclear);

            swbnamesRadioGroup = (RadioGroup) view.findViewById(R.id.swbnames);
            swbnamesRadioGroup.setEnabled(false);

            irresponse = (TextView) view.findViewById(R.id.irresponse);


            //display= (TextView) view.findViewById(R.id.tv_resp);

            btns1.setOnClickListener(this);
            btns2.setOnClickListener(this);
            btns3.setOnClickListener(this);
            btns4.setOnClickListener(this);
            btns5.setOnClickListener(this);
            btns6.setOnClickListener(this);

            btnset.setOnClickListener(this);

            btnclear.setOnClickListener(this);


            swbadap = new SwbAdapter(getActivity().getApplicationContext());
            mas_adap = new MasterAdapter(getActivity().getApplicationContext());
            //assigning house name
            WIRELESS_HOUSE_NAME= StaticVariables.HOUSE_NAME+"_WLS";

            //setting house name for wireless database
            StaticVariables.WHOUSE_NAME=WIRELESS_HOUSE_NAME;
            WhouseDB = new WirelessConfigurationAdapter(getActivity().getApplicationContext());

            swbnamenumber = new ArrayList<HashMap<String, String>>();
            deviceTYPE = new ArrayList<HashMap<String, String>>();

            createdRadiobtn();
            devicetypes();


            remoteKEYselection = "1";
            setswitchboardsupdated(1);
           // setswitchboards(1);
            btnselect(1);

            animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_around_center_point);

        }
        else {
            permision.setVisibility(View.VISIBLE);
        }
        return  view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void transmitdata(String remotkey, String dTYPE, String dEVNO)
    {

        String str;

        devno=StaticVariabes_div.mirnoa[StaticVariabes_div.mirpst];

        roomno= StaticVariabes_div.room_n;
        while(devno.length()<4)devno="0"+devno;
        while(roomno.length()<2)roomno="0"+roomno;

        while(dTYPE.length()<3)dTYPE="0"+dTYPE;
        while(dEVNO.length()<4)dEVNO="0"+dEVNO;



        str = "0" + "01" + "999" + devno + roomno + remotkey + dTYPE + dEVNO + "0000000000";//val4 + "000000000000000";

        StaticVariabes_div.log("str" + str, TAG1);

        byte[] op=str.getBytes();
        byte[] result = new byte[32];
        result[0] = (byte) '*';
        result[31] = (byte) '#';
        for (int i = 1; i < 31; i++)
            result[i] = op[(i - 1)];
        Tcp_con.WriteBytes(result);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void transmitdata2(String remotkey, String dID, String ldata)
    {

        String str;
        devno=StaticVariabes_div.mirnoa[StaticVariabes_div.mirpst];
        roomno= StaticVariabes_div.room_n;
        while(devno.length()<4)devno="0"+devno;
        while(roomno.length()<2)roomno="0"+roomno;

        str = "0" + "01" + "999" + devno + roomno + remotkey + dID + ldata;//val4 + "000000000000000";

        StaticVariabes_div.log("str" + str, TAG1);

        byte[] op=str.getBytes();
        byte[] result = new byte[32];
        result[0] = (byte) '*';
        result[31] = (byte) '#';
        for (int i = 1; i < 31; i++)
            result[i] = op[(i - 1)];
        Tcp_con.WriteBytes(result);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              //  Toast.makeText(getContext(),"sent",Toast.LENGTH_SHORT).show();
            }
        });

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

                }

            }
        });
    }

    public void receiveddata(int msg,String data,byte[] bytestatus){

        switch (msg) {
            case READ_BYTE:
                byte[] readBuf = bytestatus;
                // DataIn(readBuf);
                final String readMessage = new String(readBuf, 0,readBuf.length);
                StaticVariabes_div.log("msg read :- " + data + " msg", TAG1);

                DataIn(readBuf);

                break;
            case READ_LINE:
                StaticVariabes_div.log("msg read A_s" + data, TAG1);
                readMessage2 =data;
                if(readMessage2.equals("*OK#")){
                    mn_nav_obj.serv_status(true);
                   // transmitdata(920);
                }else{
                    combf.timerresponse(readMessage2);
                }

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
                        //transmitdata(920);

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
                // StaticVariabes_div.log("servsignallevel swb" + signallevelB, TAG1);
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
                        //  onShift();
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
            inp=new String(input);//Blaster.ReadData(input, 1);
            StaticVariabes_div.log("input f"+inp, TAG1);
            String DevType = inp.substring(1, 4);
            StaticVariabes_div.log("DevType dim"+DevType, TAG1);
            String Dev = inp.substring(4, 8);
            StaticVariabes_div.log("input"+byt.length, TAG1);
            final String DS =inp.substring(8, 9);
            final String dval =inp.substring(9, 10);
            StaticVariabes_div.log("DS"+DS+"Devno"+Dev+"devno"+devno+"dval"+dval+"inp"+inp, TAG1);


            if(DevType.equals("727")){
                if(Dev.equals(devno)){
                    try{

                        getActivity().runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            public void run() {
                                StaticVariabes_div.log("dim intValue res" + intValue, TAG1);

                                if(DS.equalsIgnoreCase("0") || DS.equalsIgnoreCase("E")) {
                                    receivedstate = 1;

                                    irresponse.setText(dval);
                                    if(!DS.equalsIgnoreCase("0")) {
                                        try {
                                            swbadap.open();
                                            mas_adap.open();
                                            WhouseDB.open();
                                            if (swbnamesRadioGroup.getChildCount()>0) {
                                               //dval = String.valueOf(radioswbindex);
                                                String roomnumber = StaticVariabes_div.room_n;
                                                String roomname = StaticVariabes_div.roomname;
                                                String devicetype = "";
                                                for (HashMap<String, String> map : deviceTYPE)
                                                    for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                                                        String keyval = mapEntry.getKey();
                                                        if (keyval.equalsIgnoreCase(StaticVariabes_div.mirmodela[StaticVariabes_div.mirpst]))
                                                            devicetype = mapEntry.getValue();
                                                    }
                                                String deviceID = mas_adap.getdevID(StaticVariabes_div.mirnoa[StaticVariabes_div.mirpst]);
                                                String deviceName = StaticVariabes_div.mirdevicenames[StaticVariabes_div.mirpst];


                                                String A_dTYPE = "", A_dEVNO = "", A_dID = "", A_deviceName = "";

                                                for (HashMap<String, String> map : deviceTYPE)
                                                    for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                                                        String keyval = mapEntry.getKey();
                                                       // if (keyval.equalsIgnoreCase(StaticVariabes_div.swbmodela[Integer.parseInt(dval) - 1]))
                                                        if (keyval.equalsIgnoreCase(StaticVariabes_div.swbmodela[radioswbindex]))
                                                            A_dTYPE = mapEntry.getValue();
                                                    }
                                                //A_dEVNO = StaticVariabes_div.swbnoa[Integer.parseInt(dval) - 1];
                                                A_dEVNO = StaticVariabes_div.swbnoa[radioswbindex];
                                                A_dID = swbadap.getdevID(StaticVariabes_div.swbnoa[radioswbindex]);
                                                A_deviceName = StaticVariabes_div.swbdevicenames[radioswbindex];

                                                Asso_data = "0" + "01" + "999" + devno + roomno + remoteKEYselection + A_dTYPE + A_dEVNO + "0000000000" + A_dID + A_lData;

                                                boolean ret = WhouseDB.Insert_WRLS_MIR_details(Integer.parseInt(roomnumber), roomname, devicetype, Integer.parseInt(devno), deviceName, deviceID, Asso_data, Integer.parseInt(roomnumber), roomname, A_dTYPE, Integer.parseInt(A_dEVNO), A_deviceName, A_dID, "S" + remoteKEYselection);

                                                if (ret) {
                                                    setswitchboards(Integer.parseInt(remoteKEYselection));

                                                }

                                                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception e) {
                                            Log.e("MIR response", e.getMessage());
                                        } finally {
                                            mas_adap.close();
                                            WhouseDB.close();
                                            swbadap.close();
                                        }

                                    }
                                }


                            }
                        });

                       /* if(DS.equals("0"))
                        {
                            StaticVariabes_div.log("DS value ON"+DS, TAG1);
                            StaticVariabes_div.log("dval"+dval, TAG1);
                            // intValue = Integer.parseInt(dval);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    StaticVariabes_div.log("dim intValue res" + intValue, TAG1);
                                   // b_off.setBackgroundResource(R.drawable.on_off);
                                   // b_on.setBackgroundResource(R.drawable.all_on);

                                }
                            });

                        }
                        else if(DS.equals("1"))
                        {
                            StaticVariabes_div.log("DS value 0FF"+DS, TAG1);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                   // b_on.setBackgroundResource(R.drawable.on_off);
                                   // b_off.setBackgroundResource(R.drawable.all_off);
                                }
                            });
                        }*/
                    }catch(NumberFormatException ne){

                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),"Invalid data recieved",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }


    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btns1:
                swbnamesRadioGroup.clearCheck();
                reEnable();
                remoteKEYselection = "1";
                setswitchboardsupdated(1);
                setswitchboards(1);
                btnselect(1);

                cmds="101";
                //transmitdata(101);
                //datasend();
                break;

            case R.id.btns2:
                swbnamesRadioGroup.clearCheck();
                reEnable();

                remoteKEYselection = "2";
                setswitchboardsupdated(2);
                setswitchboards(2);
                btnselect(2);

                break;
            case R.id.btns3:
                swbnamesRadioGroup.clearCheck();
                reEnable();
                remoteKEYselection = "3";
                setswitchboardsupdated(3);
                setswitchboards(3);
                btnselect(3);

                break;
            case R.id.btns4:
                swbnamesRadioGroup.clearCheck();
                reEnable();
                remoteKEYselection = "4";
                setswitchboardsupdated(4);
                setswitchboards(4);
                btnselect(4);

                break;

            case R.id.btns5:
                swbnamesRadioGroup.clearCheck();
                reEnable();
                remoteKEYselection = "5";
                setswitchboardsupdated(5);
                setswitchboards(5);
                btnselect(5);

                break;
            case R.id.btns6:
                swbnamesRadioGroup.clearCheck();
                reEnable();
                remoteKEYselection = "6";
                setswitchboards(6);
                setswitchboardsupdated(6);
                btnselect(6);

                break;
            case R.id.btnset:
                //setOperation();
                int id = swbnamesRadioGroup.getCheckedRadioButtonId();
                if(id>0) {
                    try {
                        new setOperationclass().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(getContext(),"Please select smart switch",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnclear:
                WhouseDB.open();
                WhouseDB.deleteMIRDevicedata(StaticVariabes_div.mirnoa[StaticVariabes_div.mirpst]);
                WhouseDB.close();
                transmitdata2("A","AAAAAAAAAAAAAAAA","A");
                reEnable();
                break;

        }
    }

    private void btnselect(int btn){
        btns1.setBackground(getResources().getDrawable(R.drawable.btn_bg_n));
        btns2.setBackground(getResources().getDrawable(R.drawable.btn_bg_n));
        btns3.setBackground(getResources().getDrawable(R.drawable.btn_bg_n));
        btns4.setBackground(getResources().getDrawable(R.drawable.btn_bg_n));
        btns5.setBackground(getResources().getDrawable(R.drawable.btn_bg_n));
        btns6.setBackground(getResources().getDrawable(R.drawable.btn_bg_n));
        if (btn == 1)
            btns1.setBackground(getResources().getDrawable(R.drawable.btn_bg_p));
        if (btn == 2)
            btns2.setBackground(getResources().getDrawable(R.drawable.btn_bg_p));
        if (btn == 3)
            btns3.setBackground(getResources().getDrawable(R.drawable.btn_bg_p));
        if (btn == 4)
            btns4.setBackground(getResources().getDrawable(R.drawable.btn_bg_p));
        if (btn == 5)
            btns5.setBackground(getResources().getDrawable(R.drawable.btn_bg_p));
        if (btn == 6)
            btns6.setBackground(getResources().getDrawable(R.drawable.btn_bg_p));

        swbnamesRadioGroup.setEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createdRadiobtn(){
        swbadap.open();
        swbnamenumber = swbadap.getdevnames(StaticVariabes_div.room_n);
        swbadap.close();
        if(swbnamenumber.size()>0) {
            swbnamenumber.clear();
            for (int i = 0; i < StaticVariabes_div.swbcounts; i++) {

                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("" + i, StaticVariabes_div.swbdevicenames[i]);
                swbnamenumber.add(hashMap);
                // Initialize a new RadioButton
                RadioButton rb_new = new RadioButton(getContext());

                // Set a Text for new RadioButton
                rb_new.setText(StaticVariabes_div.swbdevicenames[i]);

                // Set the text color of Radio Button
                // rb_flash.setTextColor(Color.BLACK);

                // Finally, add the new RadioButton to the RadioGroup
                swbnamesRadioGroup.addView(rb_new);


            }
           // swbnamesRadioGroup.check(swbnamesRadioGroup.getChildAt(0).getId());
        }
        else {
            layou_setting.setVisibility(View.GONE);
            swbnotfound.setVisibility(View.VISIBLE);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setOperation(){
        swbadap.open();
        int key = 0;
        String value = "";
        String devicetype = "" ;

        int rValue = swbnamesRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) view.findViewById(rValue);


        for (HashMap<String, String> map : swbnamenumber)
            for (Map.Entry<String, String> mapEntry : map.entrySet())
            {
                value = mapEntry.getValue();
                if(value.equalsIgnoreCase(radioButton.getText().toString())) {
                    key = Integer.parseInt(mapEntry.getKey());
                    radioswbindex = key;
                }
            }
        for (HashMap<String, String> map : deviceTYPE)
            for (Map.Entry<String, String> mapEntry : map.entrySet())
            {
                String keyval =  mapEntry.getKey();
                if(keyval.equalsIgnoreCase(StaticVariabes_div.swbmodela[key]))
                    devicetype = mapEntry.getValue();
            }

        if(devicetype!=null && devicetype.length()>0){

            String devicenumber = StaticVariabes_div.swbnoa[key];
            final String deviceID = swbadap.getdevID(StaticVariabes_div.swbnoa[key]);
           // Toast.makeText(getContext(),""+radioButton.getText().toString()+" devicetype : "+devicetype+" devicenumber : "+devicenumber+" deviceID : "+deviceID,Toast.LENGTH_SHORT).show();

            transmitdata(remoteKEYselection,devicetype,devicenumber);

           /* delay(1000);
            if(key == 2)
                transmitdata2(remoteKEYselection,deviceID,"F");
            else
                transmitdata2(remoteKEYselection,deviceID,"E");*/

            final int finalKey = key;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {


            //final int finalKey = finalKey;
            new CountDownTimer(500, 100) {
                public void onFinish() {
                    if(finalKey == 2) {
                        transmitdata2(remoteKEYselection, deviceID, "F");
                        A_lData = "F";
                    }
                    else {
                        transmitdata2(remoteKEYselection, deviceID, "E");
                        A_lData = "E";
                    }
                }
                public void onTick(long millisUntilFinished) {
                   // Toast.makeText(getContext(),""+millisUntilFinished,Toast.LENGTH_SHORT).show();

                    // millisUntilFinished    The amount of time until finished.
                }
            }.start();
                }
            });



        }
         swbadap.close();

    }

    private void devicetypes(){
        HashMap<String,String> hashMap = null;
        hashMap = new HashMap<String, String>();
        hashMap.put("S010","001");
        hashMap.put("S020","002");
        hashMap.put("S030","003");
        hashMap.put("S040","004");
        hashMap.put("S060","005");
        hashMap.put("S080","006");
        hashMap.put("S120","007");
        hashMap.put("S021","008");
        hashMap.put("S031","009");
        hashMap.put("S042","010");
        hashMap.put("S051","011");
        hashMap.put("S052","012");
        hashMap.put("S062","013");
        hashMap.put("S071","014");
        hashMap.put("S102","015");
        hashMap.put("S111","016");

        hashMap.put("WIR1","727");
        deviceTYPE.add(hashMap);
    }

    private class setOperationclass extends AsyncTask<Void,Void,Void>{
        ProgressDialog progDailog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected Void doInBackground(Void... voids) {
            /*getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnset.startAnimation(animation);
                }
            });*/
            setOperation();
            /*getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(receivedstate == 1)
                        Toast.makeText(getContext(),"Updated",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
            });*/
           /* getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btnset.clearAnimation();
                }
            });*/
            receivedstate = 0;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }


    //delay method
    private void delay(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setswitchboards(int selectedkey){

        WhouseDB.open();
        String result = WhouseDB.getmirdata(StaticVariabes_div.mirnoa[StaticVariabes_div.mirpst],"S"+selectedkey);
        WhouseDB.close();

        if(result != null) {
            for (int i = 0; i < swbnamesRadioGroup.getChildCount(); i++) {

                if (result.equalsIgnoreCase(StaticVariabes_div.swbnoa[i])) {
                    for (int j = 0; j < swbnamesRadioGroup.getChildCount(); j++) {
                        if (i != j)
                            swbnamesRadioGroup.getChildAt(j).setEnabled(false);
                    }
                    swbnamesRadioGroup.check(swbnamesRadioGroup.getChildAt(i).getId());
                    swbnamesRadioGroup.getChildAt(i).setEnabled(true);
                    break;
                }

            }
        }

    }

    private void setswitchboardsupdated(int keyval){

        try {
            WhouseDB.open();
            ArrayList<String> result = WhouseDB.getmirdata(StaticVariabes_div.mirnoa[StaticVariabes_div.mirpst]);
            WhouseDB.close();

            if(result != null) {
                for (int i = 0; i < swbnamesRadioGroup.getChildCount(); i++) {
                    for (int k=0;k<result.size();k++){
                        if (result.get(k).equalsIgnoreCase(StaticVariabes_div.swbnoa[i]))
                            swbnamesRadioGroup.getChildAt(i).setEnabled(false);
                    }
                }
                if(keyval <= swbnamesRadioGroup.getChildCount()) {
                  //  swbnamesRadioGroup.getChildAt(keyval - 1).setEnabled(true);
                   // swbnamesRadioGroup.check(swbnamesRadioGroup.getChildAt(keyval - 1).getId());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void reEnable(){
        for (int j = 0; j < swbnamesRadioGroup.getChildCount(); j++)
                swbnamesRadioGroup.getChildAt(j).setEnabled(true);
    }

}
