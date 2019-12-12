package edisonbro.com.edisonbroautomation.Devices;

/**
 *  FILENAME: GardenSprinkler_Group.java
 *  DATE: 07-08-2018

 *  DESCRIPTION: Group Fragment to operate selected group sprinkler Devices together using master layout and
 *  option to operate each device in group individually .

 *  Copyright (C) EdisonBro Smart Labs Pvt Ltd. All rights reserved.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.Arrays;
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
import edisonbro.com.edisonbroautomation.blaster.Blaster;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp_dwn_config;
import edisonbro.com.edisonbroautomation.operatorsettings.UpdateHome_Existing;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GardenSprinkler_Group.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GardenSprinkler_Group#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GardenSprinkler_Group extends Fragment implements TcpTransfer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    View view;
    int count=0;
    int ispeed=121;
    ////////////////////////////////////////////////////
    private static final String TAG1="GSK Group- ";
    String devno, roomno="0", housename, houseno, roomname, groupId = "000", broadcastMsg = "01",devtypesett,model,Roomname;
    ///////////////////////////////////////////////////////////////////
    private static final int READ_BYTE = 1;
    private static final int READ_LINE = 2;
    private static final int ServStatus = 3;
    private static final int signallevel = 4;
    private static final int NetwrkType = 5;
    private static final int MAXUSER = 6;
    private static final int ERRUSER = 7;
    private static final int  UPDATE=8;
    //**********************************************
    int sl;
    boolean check=true,statusserv,remoteconn,remoteconn3g,nonetwork;
    String servpreviousstate,remoteconprevstate ,rs, readMessage2,inp;
    //************************************************************************
    Main_Navigation_Activity mn_nav_obj;
    //********************************************************************************
    int delay = 0; // delay for 1 sec.
    int period = 1000;
    Timer timer = null;
    //********************************************************************

    private Button btn_on,btn_off;
    private TextView tvtest,tv_onOff;

    boolean gskstatus=false;
    boolean groupmaster=false;
    ImageView gsk_img_grp;
    CombFrag combf;

    private Tracker mTracker;
    String name ="GSK Group";

    String gskgrouppost;

    public GardenSprinkler_Group() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GardenSprinkler_Group.
     */
    // TODO: Rename and change types and number of parameters
    public static GardenSprinkler_Group newInstance(String param1, String param2) {
        GardenSprinkler_Group fragment = new GardenSprinkler_Group();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_garden_sprinkler_group, container, false);


        btn_on=(Button) view.findViewById(R.id.btnongsk);
        btn_off=(Button) view.findViewById(R.id.btnoffgsk);
        tv_onOff= (TextView) view.findViewById(R.id.tv_on_off);

        gsk_img_grp= (ImageView) view.findViewById(R.id.image);

        devno = StaticVariabes_div.devnumber;
        while (devno.length() < 4) devno = "0" + devno;
        roomno = StaticVariabes_div.room_n;
        while (roomno.length() < 2) roomno = "0" + roomno;

        combf = ((CombFrag) this.getParentFragment());

        gskgrouppost=""+StaticVariabes_div.curpst;

        if (StaticVariabes_div.load_group_master) {
            if (StaticVariabes_div.gskpst == 0) {
                gskstatus = true;
                groupmaster = true;
                gsk_img_grp.setVisibility(View.VISIBLE);
                gsk_img_grp.setImageResource(R.drawable.group_icon_sprinkler);
                StaticVariabes_div.loaded_lay_Multiple = false;
                combf.feature_sett_enab_disable(false);
            } else {
                gskstatus = false;
                groupmaster = false;
                gsk_img_grp.setVisibility(View.INVISIBLE);
                StaticVariabes_div.loaded_lay_Multiple = false;
                combf.feature_sett_enab_disable(true);
            }
        } else {
            gskstatus = false;
            groupmaster = false;
            gsk_img_grp.setVisibility(View.INVISIBLE);
            StaticVariabes_div.loaded_lay_Multiple = false;
            combf.feature_sett_enab_disable(true);
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

        StaticVariabes_div.dev_typ_num="044";

        Tcp_con mTcp = new Tcp_con(this);

        if(Tcp_con.isClientStarted){

            ButtonOut("920");

        }else{
            Tcp_con.stacontxt = getActivity().getApplicationContext();
            Tcp_con.serverdetailsfetch(getActivity(), StaticVariabes_div.housename);
            Tcp_con.registerReceivers(getActivity().getApplicationContext());
        }

        mn_nav_obj=(Main_Navigation_Activity) getActivity();

        Edisonbro_AnalyticsApplication application = (Edisonbro_AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        btn_on.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ButtonOut("201");
                Track_button_event("GSK Group","GSK ON","GSK Shortclick");
            }
        })	;

        btn_off.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ButtonOut("301");
                Track_button_event("GSK Group","GSK OFF","GSK Shortclick");
            }
        })	;
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



    @Override
    public void read(final int type, final String stringData, final byte[] byteData)
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                receiveddata(type,stringData,byteData);
            }
        });
    }

    void ButtonOut(String bo) {
        if (Tcp_con.isClientStarted) {
            devno=StaticVariabes_div.devnumber;
            // devno= StaticVariabes_div.acnoa[StaticVariabes_div.acpst];
            while (devno.length() < 4)devno = "0" + devno;
            while(roomno.length()<2)roomno="0"+roomno;
            String str = "0" + broadcastMsg + groupId + devno +roomno+ bo + "000000000000000";

            if(StaticVariabes_div.gskpst==0&&StaticVariabes_div.load_group_master) {
                str = "0" + "15" + StaticVariabes_div.gskgrpsarr[0] + "0000" + roomno+bo+"000000000000000";
                StaticVariabes_div.log("str" + str, TAG1);
            }else{
                str = "0" + "01" + "000" + devno + roomno + bo + "000000000000000";
                StaticVariabes_div.log("str" + str, TAG1);
            }

            StaticVariabes_div.log( "out" + str, TAG1);
            //byte[] op= Blaster.WriteData(str, 1);
            byte[] op=str.getBytes();
            byte[] result = new byte[32];
            result[0] = (byte) '*';
            result[31] = (byte) '#';
            for (int i = 1; i < 31; i++)
                result[i] = op[(i - 1)];
            StaticVariabes_div.log("bout" + result + "$$$" + bo, TAG1);
            Tcp_con.WriteBytes(result);
        }
    }
    void DataIn(byte[] byt)
    {
        if (byt != null && byt.length == 32) {
            byte[] input = new byte[30];
            System.arraycopy(byt, 1, input, 0, ((byt.length) - 2));
            try{
                inp=new String(input);//Blaster.ReadData(input, 1);
                StaticVariabes_div.log("input"+inp, TAG1);
                String DevType = inp.substring(1, 4);
                StaticVariabes_div.log( "DevType "+DevType, TAG1);
                String Dev = inp.substring(4, 8);
                char s14 = inp.charAt(8);
                String DS=String.valueOf(s14);

                StaticVariabes_div.log(" DS"+DS+" Devno"+Dev+" devno"+devno+" inp"+inp, TAG1);

                if(DevType.equals("044")){

                    if(StaticVariabes_div.load_group_master&&(gskgrouppost.equals("0"))) {
                        StaticVariabes_div.log("load_group_master-Dev " + Integer.parseInt(Dev), TAG1);


                        for(int y=0;y<StaticVariabes_div.gskgrpsarr.length;y++){
                            StaticVariabes_div.log("load_group_devices " + StaticVariabes_div.gskgrpsarr[y], TAG1);
                        }


                        if (Dev!=null) {
                            StaticVariabes_div.log("Dev!null- " + Dev, TAG1);
                            ArrayList list = new ArrayList(Arrays.asList(StaticVariabes_div.gskgrpsarr));

                            String tempdev = "" + Integer.parseInt(Dev);
                            StaticVariabes_div.log("contains-tempdev " + tempdev, TAG1);
                            if (list.contains(tempdev)) {
                                StaticVariabes_div.log("contains- " + Dev, TAG1);
                                if (DS.equals("1")) {
                                    StaticVariabes_div.log("DS value 1" + DS, TAG1);
                                    R1s(true);

                                } else if (DS.equals("0")) {
                                    R1s(false);

                                }

                            }

                        }
                    }else
                    {
                        if (Dev.equals(devno)) {

                            if (DS.equals("1")) {
                                StaticVariabes_div.log("DS value 1" + DS, TAG1);
                                R1s(true);

                            } else if (DS.equals("0")) {
                                R1s(false);

                            }
                        }
                    }
                }


            }catch(Exception e){

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),"Invalid data recieved",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity().getApplicationContext(),"Invalid data length recieved",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    void R1s(boolean b)
    {
        if(b)
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    tv_onOff.setText("ON");
                    tv_onOff.setTextColor(Color.GREEN);

                }
            });
        else
            getActivity().runOnUiThread(new Runnable() {
                public void run() {

                    tv_onOff.setText("OFF");
                    tv_onOff.setTextColor(Color.RED);
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
                    ButtonOut("920");

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
                        ButtonOut("920");
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
}
