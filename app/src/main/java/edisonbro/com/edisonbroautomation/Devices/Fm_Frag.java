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
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Timer;
import java.util.TimerTask;

import edisonbro.com.edisonbroautomation.CombFrag;
import edisonbro.com.edisonbroautomation.Connections.TcpTransfer;
import edisonbro.com.edisonbroautomation.Connections.Tcp_con;
import edisonbro.com.edisonbroautomation.Edisonbro_AnalyticsApplication;
import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticStatus;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp_dwn_config;
import edisonbro.com.edisonbroautomation.operatorsettings.UpdateHome_Existing;
import edisonbro.com.edisonbroautomation.scrollfunction.ShaderSeekArc;

public class Fm_Frag extends Fragment implements TcpTransfer,View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    TextView tv_res;
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,b_on,b_off,b_usd,b_paly_pause,b_mute,b_mode,b_vol_inc,b_vol_dec,b_far,b_back;
    private String cmds;
    private TextView display;
    private Animation myAnim;

    //****************************************************************

    public Fm_Frag() {
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
    public static Fm_Frag newInstance(String param1, String param2) {
        Fm_Frag fragment = new Fm_Frag();
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
        view =inflater.inflate(R.layout.fragment_fm_, container, false);


        new Tcp_con(this);

        if(Tcp_con.isClientStarted){

            transmitdata(920);

        }else{
            Tcp_con.stacontxt = getActivity().getApplicationContext();
            Tcp_con.serverdetailsfetch(getActivity(), StaticVariabes_div.housename);
            Tcp_con.registerReceivers(getActivity().getApplicationContext());
        }

        try {
            tvtest = (TextView) view.findViewById(R.id.tvtest);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),"msg"+e.toString(),Toast.LENGTH_SHORT).show();

        }

        if(tvtest!=null) {
            if (StaticVariabes_div.dev_name != null)
                tvtest.setText(StaticVariabes_div.dev_name);
        }

        mn_nav_obj=(Main_Navigation_Activity) getActivity();

        Edisonbro_AnalyticsApplication application = (Edisonbro_AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        combf = ((CombFrag) this.getParentFragment());
        combf.feature_sett_enab_disable(true);


        b1 = (Button) view.findViewById(R.id.btn1);
        b2 = (Button) view.findViewById(R.id.btn2);
        b3 = (Button) view.findViewById(R.id.btn3);
        b4 = (Button) view.findViewById(R.id.btn4);
        b5 = (Button) view.findViewById(R.id.btn5);
        b6 = (Button) view.findViewById(R.id.btn6);
        b7 = (Button) view.findViewById(R.id.btn7);
        b8 = (Button) view.findViewById(R.id.btn8);
        b9 = (Button) view.findViewById(R.id.btn9);
        b0 = (Button) view.findViewById(R.id.btn0);

        b_on = (Button) view.findViewById(R.id.fm_on);
        b_off = (Button) view.findViewById(R.id.fm_off);
        b_usd = (Button) view.findViewById(R.id.fm_USD);
        b_paly_pause = (Button) view.findViewById(R.id.fm_play_pause);
        b_mute = (Button) view.findViewById(R.id.fm_mute);
        b_mode = (Button) view.findViewById(R.id.fm_mode);
        b_vol_inc = (Button) view.findViewById(R.id.fm_vol_inc);
        b_vol_dec = (Button) view.findViewById(R.id.fm_vol_dec);
        b_far = (Button) view.findViewById(R.id.fm_f_play);
        b_back = (Button) view.findViewById(R.id.fm_b_play);

        display= (TextView) view.findViewById(R.id.tv_resp);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        b0.setOnClickListener(this);

        b_on.setOnClickListener(this);
        b_off.setOnClickListener(this);
        b_usd.setOnClickListener(this);
        b_paly_pause.setOnClickListener(this);
        b_mute.setOnClickListener(this);
        b_mode.setOnClickListener(this);
        b_vol_inc.setOnClickListener(this);
        b_vol_dec.setOnClickListener(this);
        b_far.setOnClickListener(this);
        b_back.setOnClickListener(this);

        myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        return  view;
    }

    void transmitdata(int val3)
    {  String str,val4;

        val4=""+val3;
        StaticVariabes_div.log("val3" + val3, TAG1);
        devno=StaticVariabes_div.fmnoa[StaticVariabes_div.fmpst];

        roomno= StaticVariabes_div.room_n;
        while(devno.length()<4)devno="0"+devno;
        while(val4.length()<3)val4="0"+val4;
        while(roomno.length()<2)roomno="0"+roomno;

        StaticVariabes_div.log("val4" + val4, TAG1);

        str = "0" + "01" + "000" + devno + roomno + val4 + "000000000000000";
        StaticVariabes_div.log("str" + str, TAG1);

        byte[] op=str.getBytes();
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
                    transmitdata(920);
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
                        transmitdata(920);

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
            String DS =inp.substring(8, 9);
            String dval =inp.substring(9, 13);
            StaticVariabes_div.log("DS"+DS+"Devno"+Dev+"devno"+devno+"dval"+dval+"inp"+inp, TAG1);


            if(DevType.equals("046")){
                if(Dev.equals(devno)){
                    try{

                        if(DS.equals("0"))
                        {
                            StaticVariabes_div.log("DS value ON"+DS, TAG1);
                            StaticVariabes_div.log("dval"+dval, TAG1);
                           // intValue = Integer.parseInt(dval);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    StaticVariabes_div.log("dim intValue res" + intValue, TAG1);
                                    b_off.setBackgroundResource(R.drawable.on_off);
                                    b_on.setBackgroundResource(R.drawable.all_on);

                                }
                            });

                        }
                        else if(DS.equals("1"))
                        {
                            StaticVariabes_div.log("DS value 0FF"+DS, TAG1);
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    b_on.setBackgroundResource(R.drawable.on_off);
                                    b_off.setBackgroundResource(R.drawable.all_off);
                                }
                            });
                        }
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


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn1:
                cmds="101";
                transmitdata(101);
                //datasend();
                break;
            case R.id.btn2:
                cmds="102";
                transmitdata(102);
                break;
            case R.id.btn3:
                cmds="103";
                transmitdata(103);
                break;
            case R.id.btn4:
                cmds="104";
                transmitdata(104);
                break;
            case R.id.btn5:
                cmds="105";
                transmitdata(105);
                break;
            case R.id.btn6:
                cmds="106";
                transmitdata(106);
                break;
            case R.id.btn7:
                cmds="107";
                transmitdata(107);
                break;
            case R.id.btn8:
                cmds="108";
                transmitdata(108);
                break;
            case R.id.btn9:
                cmds="109";
                transmitdata(109);
                break;
            case R.id.btn0:
                cmds="110";
                transmitdata(110);
                break;
            case R.id.fm_on:
                cmds="201";
                transmitdata(201);
                b_on.startAnimation(myAnim);
                b_off.startAnimation(myAnim);
                break;
            case R.id.fm_off:
                cmds="301";
                transmitdata(301);
                b_on.startAnimation(myAnim);
                b_off.startAnimation(myAnim);
                break;
            case R.id.fm_USD:
                cmds="119";
                transmitdata(119);
                break;
            case R.id.fm_play_pause:
                cmds="112";
                transmitdata(112);
                break;
            case R.id.fm_mute:
                cmds="115";
                transmitdata(115);
                break;
            case R.id.fm_mode:
                cmds="118";
                transmitdata(118);
                break;
            case R.id.fm_vol_inc:
                cmds="113";
                transmitdata(113);
                break;
            case R.id.fm_vol_dec:
                cmds="114";
                transmitdata(114);
                break;
            case R.id.fm_f_play:
                cmds="116";
                transmitdata(116);
                break;
            case R.id.fm_b_play:
                cmds="117";
                transmitdata(117);
                break;
        }
    }

}
