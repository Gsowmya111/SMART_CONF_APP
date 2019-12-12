package edisonbro.com.edisonbroautomation.operatorsettings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import edisonbro.com.edisonbroautomation.Connections.TcpTransfer;
import edisonbro.com.edisonbroautomation.Connections.Tcp_con;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticStatus;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariables;
import edisonbro.com.edisonbroautomation.databasewired.MasterAdapter;
import edisonbro.com.edisonbroautomation.databasewired.SwbAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.HouseConfigurationAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.WirelessConfigurationAdapter;

public class Edit_Alexa extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TcpTransfer {

    private static final String TAG1 = "Edit Alexa Name-";
    Spinner dev_name_spinner,room_spinner,new_room_spinner;
    HouseConfigurationAdapter houseDB=null;
    WirelessConfigurationAdapter WhouseDB=null;
    private MasterAdapter mas_adap;
    EditText et_new_devname,et_new_devid;
    SpinnerAdapter usr_devname_Adapter,roomNameAdapter;
    UpdateAdapter up_adap;
    Button edit_btn,move_btn,btn_uploadalexasettng;
    ImageView navigateBack,btnconstatus;
    TextView tv_alexaid;
    ArrayList<String> UniqueRoomList=new ArrayList<String>();
    ArrayList<String> UniquedevnameList=new ArrayList<String>();
    String alexanoarr[],alexadevnamearr[];
    int spinnerLayoutId= R.layout.spinnerlayout;
    String CurrentRoomNo,alexa_id,NewRoomNo;
    private ArrayList<String> listdevicesnumbers,listdevicesnames;


    private static final int READ_BYTE = 1;
    private static final int READ_LINE = 2;
    private static final int ServStatus = 3;
    private static final int signallevel = 4;
    private static final int NetwrkType = 5;
    private static final int MAXUSER = 6;
    private static final int ERRUSER = 7;

    //**********************************************
    int sl;
    boolean check=true,statusserv,remoteconn,remoteconn3g,nonetwork;
    String servpreviousstate,remoteconprevstate ,rs, readMessage2,inp;
    //**********************************************************************

    int delay = 0; // delay for 1 sec.
    int period = 1000;
    Timer timer = null;
    //********************************************************************
    ProgressDialog progressBar;
    String sPackName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_edit_alexa);
        Context context = getApplicationContext();
        sPackName = context.getPackageName();
        navigateBack=(ImageView) findViewById(R.id.imageView2);
        edit_btn=(Button) findViewById(R.id.edit_btn);
        room_spinner = (Spinner) findViewById(R.id.sp_room_list);
        dev_name_spinner = (Spinner) findViewById(R.id.sp_devname_list);

        et_new_devname=(EditText) findViewById(R.id.et_new_devname);
        et_new_devid=(EditText) findViewById(R.id.et_new_devid);
        tv_alexaid=(TextView)findViewById(R.id.txt_mac);
        new_room_spinner = (Spinner) findViewById(R.id.sp_newroom_list);
        btnconstatus=(ImageView) findViewById(R.id.server_status);
        move_btn=(Button) findViewById(R.id.move_btn);
        btn_uploadalexasettng= (Button) findViewById(R.id.upload_alexasett);
        room_spinner.setOnItemSelectedListener(this);
        dev_name_spinner.setOnItemSelectedListener(this);

        //setting house name for wireless database
        StaticVariables.WHOUSE_NAME= StaticVariables.HOUSE_NAME+"_WLS";;
        //setting database name with which wireless database is to save
        StaticVariables.HOUSE_DB_NAME=StaticVariables.HOUSE_NAME+"_WLS";;

        SwbAdapter.OriginalDataBase= StaticVariabes_div.housename+".db";
        MasterAdapter.OriginalDataBase=StaticVariabes_div.housename+".db";


        try{
            houseDB=new HouseConfigurationAdapter(this);
            houseDB.open();			//opening house database

            WhouseDB=new WirelessConfigurationAdapter(this);
            WhouseDB.open();

            up_adap=new UpdateAdapter(this);

            mas_adap=new MasterAdapter(this);


            fill_wire_wirelessroom();

        }catch(Exception e){
            e.printStackTrace();
        }

        Tcp_con mTcp = new Tcp_con(Edit_Alexa.this);


        if (Tcp_con.isClientStarted) {

            receiveddata(NetwrkType, StaticStatus.Network_Type, null);
            receiveddata(ServStatus, StaticStatus.Server_status, null);

        } else {
            Tcp_con.stacontxt = Edit_Alexa.this;
            Tcp_con.serverdetailsfetch(this, StaticVariabes_div.housename);
            Tcp_con.registerReceivers(Edit_Alexa.this);
        }
        navigateBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });

        move_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String roomName=room_spinner.getSelectedItem().toString();

                if(roomName!=null&&roomName.length()>0) {
                    if(roomName.equals("Select Room")){
                        Toast.makeText(Edit_Alexa.this,"please select room",Toast.LENGTH_SHORT).show();

                    }else{
                        //getting current room number
                        CurrentRoomNo = "" + houseDB.CurrentRoomNumber(roomName);
                        //getting current room name
                        if(dev_name_spinner.getSelectedItem()!=null){
                        String dev = dev_name_spinner.getSelectedItem().toString();

                        if(dev!=null&&dev.length()>0) {
                            String newroomsp = new_room_spinner.getSelectedItem().toString();


                            if (newroomsp != null && newroomsp.length() > 0) {

                                NewRoomNo = "" + houseDB.CurrentRoomNumber(newroomsp);
                                if(!(roomName.equals(newroomsp))) {

                                    String alxaexists = houseDB.isDeviceExists_alexa("" + NewRoomNo, "ALXA");

                                    if(!(alxaexists.equals("TRUE"))) {

                                    boolean upd = houseDB.updateMasterTable(NewRoomNo, newroomsp, dev);
                                    if (upd) {

                                        fill_wire_wirelessroom();
                                        alexa_id = "";
                                        tv_alexaid.setText("");
                                        popupinfo("Moved Successfully");
                                    } else {
                                        popupinfo("Error Please Try Again");
                                    }

                                }else {

                                    popupinfo("Alexa Exists In Room Please Select Other Room");
                                }

                                }else {
                                    popupinfo("please select other room");
                                }
                            } else {
                                Toast.makeText(Edit_Alexa.this, "please select room", Toast.LENGTH_SHORT).show();

                            }
                         }
                        }else{
                            Toast.makeText(Edit_Alexa.this,"please select device",Toast.LENGTH_SHORT).show();

                        }

                    }
                }else{
                    Toast.makeText(Edit_Alexa.this,"please select room",Toast.LENGTH_SHORT).show();

                }
            }
        });


        edit_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String roomName=room_spinner.getSelectedItem().toString();

                if(roomName!=null&&roomName.length()>0) {
                    if(roomName.equals("Select Room")){
                        Toast.makeText(Edit_Alexa.this,"Please Select Room",Toast.LENGTH_SHORT).show();

                    }else{
                        //getting current room number
                        CurrentRoomNo = "" + houseDB.CurrentRoomNumber(roomName);
                        //getting current room name
                        if(dev_name_spinner.getSelectedItem()!=null){
                            String dev = dev_name_spinner.getSelectedItem().toString();

                            if(dev!=null&&dev.length()>0) {
                                String newdevname = et_new_devname.getText().toString();
                                String newdevid = et_new_devid.getText().toString();


                                if (newdevname != null && newdevname.length() > 0&&newdevid!=null&& newdevid.length() > 0) {

                                    if(!(newdevname.equals(dev))) {
                                        boolean upd = houseDB.update_MstrTble_name_id(dev,newdevname,newdevid,"both");
                                        if (upd) {

                                            fill_wire_wirelessroom();
                                            alexa_id = "";
                                            tv_alexaid.setText("");
                                            et_new_devname.setText("");
                                            et_new_devid.setText("");
                                            popupinfo("Name And Id Updated Successfully");
                                        } else {
                                            popupinfo("Error Please Try Again");
                                        }
                                    }else {
                                        popupinfo("Please Enter Other Name");
                                    }
                                }else if(newdevname != null && newdevname.length() > 0&& newdevid.length() ==0){
                                    if(!(newdevname.equals(dev))) {
                                        boolean upd = houseDB.update_MstrTble_name_id(dev,newdevname,newdevid,"nam");
                                        if (upd) {

                                            fill_wire_wirelessroom();
                                            alexa_id = "";
                                            tv_alexaid.setText("");
                                            et_new_devname.setText("");
                                            et_new_devid.setText("");
                                            popupinfo("Name Updated Successfully");
                                        } else {
                                            popupinfo("Error Please Try Again");
                                        }
                                    }else {
                                        popupinfo("Please Enter Other Name");
                                    }
                                }else if(newdevname.length() == 0&&newdevid!=null&& newdevid.length() > 0){
                                    if(!(newdevid.equals(alexa_id))) {
                                        boolean upd = houseDB.update_MstrTble_name_id(dev,newdevname,newdevid,"id");
                                        if (upd) {

                                            fill_wire_wirelessroom();
                                            alexa_id = "";
                                            tv_alexaid.setText("");
                                            et_new_devname.setText("");
                                            et_new_devid.setText("");
                                            popupinfo("Id Updated Successfully");
                                        } else {
                                            popupinfo("Error Please Try Again");
                                        }
                                    }else {
                                        popupinfo("Please Enter Other Name");
                                    }
                                }
                                else {
                                    Toast.makeText(Edit_Alexa.this, "Please Enter Details", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }else{
                            Toast.makeText(Edit_Alexa.this,"Please Select Device",Toast.LENGTH_SHORT).show();

                        }

                    }
                }else{
                    Toast.makeText(Edit_Alexa.this,"Please Select Room",Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_uploadalexasettng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupupload();
            }
        });
    }
    public void read(final int type, final String stringData, final byte[] byteData)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                receiveddata(type,stringData,byteData);

            }
        });
    }

    public void popupupload() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("INFO");
        alertDialogBuilder.setMessage("Are You Sure You Want To Upload Settings").setCancelable(false)

                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (Tcp_con.isClientStarted) {
                            progressstart("Uploading Alexa Settings.....");


                            long delayInMillis = 8000;
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    progressstop ();
//                                    Toast.makeText(Add_Alexa.this,"Failed to Upload .Try Again",Toast.LENGTH_LONG).show();
                                }
                            }, delayInMillis);

                            Thread upThread = new Thread() {
                                public void run() {
                                    UploadDb(sPackName, StaticVariabes_div.housename + ".db", "$117&");
                                }
                            };upThread.start();
                            //tv_poperr.setText("Uploading Please wait ..........");
                        } else {
                            popup("Server Not Connected");
                        }

                    }
                });
        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
    public void popupinfo(String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("INFO");
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
    void goPrevious(){
        //going back to admin page
        Intent it=new Intent(Edit_Alexa.this,Configuration_Main.class);
        startActivity(it);
        finish();
    }

    public void fill_wire_wirelessroom(){
        //Fetching list of All room names from database and adding to local array list
        UniqueRoomList=new ArrayList<String>();
        UniqueRoomList.addAll(houseDB.RoomNameList());
        if(!WirelessConfigurationAdapter.sdb.isOpen()) {
            WhouseDB.open();
            UniqueRoomList.addAll(WhouseDB.WirelessPanelsRoomNameList());
        }
        UniqueRoomList.add("Select Room");

        //Loading data in room name spinner
        //roomNameAdapter=new CustomSpinnerAdapter(this, spinnerLayoutId, UniqueRoomList);
        //room_spinner.setAdapter(roomNameAdapter);

        Set<String> temprr = new LinkedHashSet<String>(UniqueRoomList );

        ArrayList<String> setList=new ArrayList<String>(temprr);

        UniqueRoomList=setList;

        //Loading data in room name spinner
        roomNameAdapter=new CustomSpinnerAdapter(this, spinnerLayoutId, UniqueRoomList);
        room_spinner.setAdapter(roomNameAdapter);

        new_room_spinner.setAdapter(roomNameAdapter);
        // Displaying Last item of list
    }
    //spinner on item click listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        switch(parent.getId()){
            case R.id.sp_room_list:{
                if(position!=UniqueRoomList.size()-1){

                    alexa_id="";
                    tv_alexaid.setText("");
                    //getting current room name
                    String roomName=room_spinner.getSelectedItem().toString();
                    //getting current room number
                    CurrentRoomNo=""+houseDB.CurrentRoomNumber(roomName);

                    prepareList(CurrentRoomNo);

                }
                break;
            }
            case R.id.sp_devname_list:{
                if(position!=UniquedevnameList.size()-1){

                    //getting current room name
                    String  dev=dev_name_spinner.getSelectedItem().toString();
                    //getting current room number
                    alexa_id=""+houseDB.getalexa_id(dev);

                    tv_alexaid.setText(alexa_id);

                }
                break;
            }
            default:
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void prepareList(String roomno)
    {

        StaticVariabes_div.log("Edit in preparelist roomno"+roomno, TAG1);

        listdevicesnumbers = new ArrayList<String>();
        listdevicesnames = new ArrayList<String>();

        String[] finallistarr=null;
        String[] finaldevnumlistarr=null;
        String[]finaldevnamelistarr=null;


//****************************************************************************************************


        mas_adap.open();


        //................................................................................
        int alexacount=mas_adap.getCount_housenoroomnodevtypename(roomno,"ALXA");
        alexanoarr=new String[alexacount];
        alexadevnamearr=new String[alexacount];

        alexanoarr=mas_adap.getall_housenoroomnodevicetypename(alexacount, roomno, "ALXA");
        alexadevnamearr=mas_adap.getall_roomnodevicenames(alexacount, roomno, "ALXA");

        if(alexacount!=0){
            listdevicesnumbers.addAll(Arrays.asList(alexanoarr));
            listdevicesnames.addAll(Arrays.asList(alexadevnamearr));
        }
        //................................................................................


        mas_adap.close();

        finaldevnumlistarr=listdevicesnumbers.toArray( new String[listdevicesnumbers.size()] );
        finaldevnamelistarr=listdevicesnames.toArray( new String[listdevicesnames.size()] );


        if(listdevicesnames!=null){
            UniquedevnameList.clear();
            UniquedevnameList.addAll(listdevicesnames);
            UniquedevnameList.add("Select Device");



            usr_devname_Adapter=new CustomSpinnerAdapter(this, spinnerLayoutId, UniquedevnameList);
            dev_name_spinner.setAdapter(usr_devname_Adapter);
           // dev_name_spinner.setSelection(UniquedevnameList.size()-1);
        }


        for(int k=0;k<finaldevnamelistarr.length;k++){
            StaticVariabes_div.log("ffinaldevnamelistarr"+finaldevnamelistarr[k], TAG1);
        }


    }

    //backpress event
    @Override
    public void onBackPressed() {
        goPrevious();
    }

    public void receiveddata(int msg,String strdata,byte[] bytestatus){

        switch (msg) {
            case READ_BYTE:
                byte[] readBuf = bytestatus;
                // DataIn(readBuf);
                final String readMessage = new String(readBuf, 0,readBuf.length);
                StaticVariabes_div.log("msg read bytes:- " + readMessage + " msg", TAG1);
                //  tvtest.setText(readMessage);
                // DataIn(readBuf);

                break;
            case READ_LINE:
                //  readMessage2 = (String) msg.obj;
                StaticVariabes_div.log("msg read string" + strdata, TAG1);
                readMessage2 =strdata;
                if(readMessage2.equals("*OK#")){
                    serv_status(true);
                    //ButtonOut("920");

                }
                ReadlineData(readMessage2);
                //  tvtest.setText(readMessage2);
                break;
            case ServStatus:
                //final String ServerStatusB = (String) msg.obj;
                final String ServerStatusB =strdata;
                StaticStatus.Server_status=strdata;

                StaticVariabes_div.log("serv status swb" + ServerStatusB, TAG1);
                if(ServerStatusB!=null){
                    if (ServerStatusB.equals("TRUE")) {
                        StaticStatus.Server_status_bool=true;
                        statusserv = true;
                        servpreviousstate="TRUE";
                        nonetwork=false;
                        // Cc.dataswb = true;
                        //  ButtonOut("920");
                        // Cc.TcpReadLine = false;

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

                serv_status(statusserv);

                break;
            case signallevel:
                //    final String signallevelB = (String) msg.obj;
                final String signallevelB = strdata;
                // StaticVariabes_div.log("servsignallevel swb" + signallevelB, TAG1);

                // final String signallevelB = data;
                //  StaticVariabes_div.log("servsignallevel swb" + signallevelB, TAG1);



                break;
            case NetwrkType:

                final String RemoteB = strdata;
                StaticStatus.Network_Type=RemoteB;
                StaticVariabes_div.log("serv Remote swb" + RemoteB, TAG1);

                break;
            case MAXUSER:
                final String maxuser = strdata;
                StaticVariabes_div.log("maxuser swb" + maxuser, TAG1);

                if (maxuser.equals("TRUE")) {
                    popup("User Exceeded");
                    serv_status(false);
                } else {

                }

                break;
            case  ERRUSER:
                final String erruser = strdata;
                StaticVariabes_div.log("erruser swb" + erruser, TAG1);

                if (erruser.equals("TRUE")) {
                    popup("INVALID USER/PASSWORD");
                    serv_status(false);
                } else {

                }

                break;
        }
    }

    public void ReadlineData( final String RL) {
        StaticVariabes_div.log("RL:- " + RL, TAG1);


        if (RL != null && RL.startsWith("*") && RL.endsWith("$")) {
            if (RL.contains("*117$")) {
                // uploadstatus=true;

                runOnUiThread(new Runnable() {
                    public void run() {
                        // tv_poperr.setText("***");

                        try {
                            Thread.sleep(600);
                            progressstop();
                            popup("Alexa Settings Uploaded Successfully ");
                            Tcp_con.WriteBytes("{alexa@".getBytes());
                            StaticVariabes_div.log("outstream command  {alexa@ ","tcpout");

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });

            } else if (RL.contains("*122$")) {

            }

        }
    }

    void progressstart(final String Messg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressBar==null){
                    progressBar = new ProgressDialog(Edit_Alexa.this);
                    progressBar.setCancelable(false);
                    progressBar.setMessage(Messg);
                    // progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();
                }else{
                    if(progressBar!=null){
                        progressBar.dismiss();
                        progressBar=null;
                    }
                }
            }
        });


    }
    void progressstop(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressBar!=null){
                    progressBar.dismiss();
                    progressBar=null;
                }
            }
        });
    }


    public void serv_status(final boolean serv)
    {
        runOnUiThread(new Runnable() {
            public void run() {
                if(serv){
                    btnconstatus.setBackgroundResource(R.drawable.connected);
                }
                else
                    btnconstatus.setBackgroundResource(R.drawable.not_connected);
            }
        });
    }

    public void popup(String msg){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
}
