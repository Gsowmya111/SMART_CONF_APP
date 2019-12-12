package edisonbro.com.edisonbroautomation.Connections;


/**
 *  FILENAME: Http_Connect.java
 *  DATE: 07-08-2018

 *  DESCRIPTION:  class to store value of timerlist.
 *
 *  Copyright (C) EdisonBro Smart Labs Pvt Ltd. All rights reserved.
 */

import android.content.Context;
import android.widget.ArrayAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import edisonbro.com.edisonbroautomation.EnergySaving.EnergySavingActivity;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.databasewired.LogAdapter;


/**
 * Created by Divya on 10/22/2017.
 */

public class Http_Connect {
    public static String Urlconip_port="nonet";
   // static String Urlcon = "http://"+Urlconip_port+"/timerstatus.php";
    //static String Urlconinsert = "http://"+Urlconip_port+"/settimer.php";
    static String Urlcon_log = "http://edisonbro.in/pir_resp.php";
    static	ArrayList<String> Data=new ArrayList<String>();
    static ArrayList<String> Data1=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    static String Fulldata_array[];
    static String returndata,ins_upd_response;

    boolean bulbstate1_4[] = { false, false, false, false };
    boolean bulbstate5_8[] = { false, false, false, false };
    boolean bulbstate9_12[] = { false, false, false, false };

    public static	ArrayList<String> Num,DeviceName,User_nam
            ,DeviceType, OperatedType, SwitchNumber
            , Days, Date, Time,FromTimeRep_date, ToTimeRep_date
            , ontime_cyclic, offtime_cyclic, RepeatAlways_rep_date
            ,OnDataRep_date, OffDataRep_date
            , OnCyclicData, OffCyclicData ,DeviceNumber ,FromTimeCyclic,ToTimeCyclic,
            FromTime_Todisplay,ToTime_Todisplay,Data_Todisplay,Off_Data_Todisplay,Devicetype_number,Datetime_log,Data_Log;
    public static List<String> Stat_devn_arr,Stat_dytt_arr,S1db,dtedb,S2db,S3db,S4db,S5db,S6db,S7db,S8db;

    LogAdapter log_adap;
    private static final String TAG1="Log_data - ";

    String S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11,S12;

    String S_State_1_4[] ={S1 ,S2 ,S3 ,S4 ,S5 ,S6 ,S7,S8 ,S9 ,S10 ,S11 ,S12 };


    public   String FetchData_log(String user, String pass, String mno, Context ctx)
    {
        log_adap = new LogAdapter(ctx, StaticVariabes_div.housename+"_Log",StaticVariabes_div.housename+"_Log");
        log_adap.open();
        log_adap.deleteall();

        StaticVariabes_div.log("HttpConnect","mno in fetch"+mno);

        if(Urlcon_log!=null){

            Data.clear();
            Data1.clear();
            Fulldata_array=null;
            HttpParams param = new BasicHttpParams();
            param.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            ConnManagerParams.setTimeout(param, 15000);
            HttpConnectionParams.setConnectionTimeout(param, 15000);
            HttpConnectionParams.setSoTimeout(param, 15000);
            HttpClient client = new DefaultHttpClient(param);
            HttpPost post = new HttpPost(Urlcon_log);
            ArrayList<NameValuePair> senddata = new ArrayList<NameValuePair>();

            senddata.add(new BasicNameValuePair("user",user));
            senddata.add(new BasicNameValuePair("pass", pass));
             senddata.add(new BasicNameValuePair("mno", mno));
            senddata.add(new BasicNameValuePair("cid", StaticVariabes_div.House_reference_num));
            //  senddata.add(new BasicNameValuePair("swno", swno));
            try {
                post.setEntity(new UrlEncodedFormEntity(senddata));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                //BluetoothReader.logTimeStamp("Before executing time");
                HttpResponse response = client.execute(post);
                //BluetoothReader.logTimeStamp("After executing time");
                StatusLine sts = response.getStatusLine();
                int code = sts.getStatusCode();
                StaticVariabes_div.log("HttpConnect","fetch code"+code);
                //log("response 1003:" + code);
                if (code == 200) {
                    String data;
                    StringBuilder sb = new StringBuilder();
                    HttpEntity entity = response.getEntity();
                    InputStream input = entity.getContent();

                    BufferedReader br = new BufferedReader(new InputStreamReader(input));

                    while ((data = br.readLine()) != null) {
                        sb.append(data);
                    }
                    String result = sb.toString();
                    StaticVariabes_div.log("HttpConnect","fetch whole res"+result);


                    //log("Response from server 1003:" + result);
                    if (result!=null) {

                        StaticVariabes_div.log("HttpConnect","fetch result not null"+result);


                        try {

                            // JSONObjects
                            JSONArray jsonArray =new JSONArray(result);//jsonRootObject

                            Num=new ArrayList<>();
                            Datetime_log = new ArrayList();
                            Data_Log = new ArrayList();


                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray
                                        .getJSONObject(i);

                                StaticVariabes_div.log("HttpConnect","jsonArray.length"+jsonArray.length());

                                String AccessDenied =(jsonObject.optString("Error").toString());
                                //Log.d("TAG1", AccessDenied);

                                StaticVariabes_div.log("HttpConnect","fetch res"+AccessDenied);

                                if(!AccessDenied.equals("Access Denied")&&!AccessDenied.equals("No data"))
                                {

                                    StaticVariabes_div.log("HttpConnect","json i"+i);

                                    Num.add(""+(i+1));

                                    Datetime_log.add(jsonObject.optString("datetime").toString());
                                    Data_Log.add( jsonObject.optString("data").toString());

                                    String datalog=jsonObject.optString("data").toString();
                                    String datetimelog=jsonObject.optString("datetime").toString();
                                    datetimelog=datetimelog.replace("/"," ");

                                    StaticVariabes_div.log("swb inp"+Data_Log, TAG1);
                                    String DevType = datalog.substring(1, 4);
                                    String Dev = datalog.substring(4, 8);
                                    char s14 = datalog.charAt(8);
                                    char s58 = datalog.charAt(9);
                                    char s912 = datalog.charAt(10);

                                    char F1 = datalog.charAt(16);
                                    char F2 = datalog.charAt(17);

                                    StaticVariabes_div.log("inpfull" + datalog, TAG1);

                                    StaticVariabes_div.log("DevType" + DevType, TAG1);
                                    StaticVariabes_div.log("Dev" + Dev, TAG1);

                                    StaticVariabes_div.log("s14" + s14, TAG1);
                                    StaticVariabes_div.log("s58" + s58, TAG1);
                                    StaticVariabes_div.log("s912" + s912, TAG1);
                                    StaticVariabes_div.log("F1" + F1, TAG1);
                                    StaticVariabes_div.log("F2" + F2, TAG1);

                                 /*   if(i==0){
                                        s14 ='0';
                                    }

                                    if(i==1){
                                        s14 = '3';
                                    }*/

                                    StaticVariabes_div.log(i+"s14" + s14, TAG1);

                                    switchStatus(s14,bulbstate1_4);
                                    switchStatus(s58,bulbstate5_8);
                                    switchStatus(s912,bulbstate9_12);


                                    int va=Integer.parseInt(DevType);
                                    StaticVariabes_div.log("va" + va, TAG1);
                                    String st=""+va;
                                    char ss;
                                    if(va==11){
                                        ss ='B';
                                    }else {
                                        ss = st.charAt(0);;
                                    }

                                    StaticVariabes_div.log("ss" + ss, TAG1);

                                    update_status(ss);
                                    log_adap.insertData(DevType,Dev,S_State_1_4[0],S_State_1_4[1],S_State_1_4[2],S_State_1_4[3],S_State_1_4[4],S_State_1_4[5],S_State_1_4[6],S_State_1_4[7],S_State_1_4[8],S_State_1_4[9],S_State_1_4[10],S_State_1_4[11],"0",datetimelog);

                                } else if(AccessDenied.equals("No data"))
                                {
                                    returndata= "No data";
                                    return returndata;

                                }

                                else
                                {
                                    returndata= "Access Denied";
                                    return returndata;

                                }

                            }

                            StaticVariabes_div.log("no of rows " + log_adap.numberOfRows(), TAG1);

                            log_adap.close();
                            returndata="Success";
                            return returndata;

                        } catch (JSONException e) {
                            e.printStackTrace();
                            returndata="Failed";
                            return returndata;
                        }catch (Exception ee){
                            ee.printStackTrace();
                            returndata="Failed";
                            return returndata;
                        }

                    }else{
                        returndata="Failed";
                        return returndata;
                    }


                } else {
                    //log("Failed to connect to server 1003");
                    returndata="Failed";
                    return returndata;
                }

            } catch (ClientProtocolException e) {
                //log(e.toString());
                returndata="Failed";
                return returndata;
            } catch (IOException e) {
                //log(e.toString());
                returndata="Failed";
                return returndata;
            }

        }else{
            returndata="Failed";
            return returndata;
        }

    }

    void update_status(char c) {

        for(int n=0;n<S_State_1_4.length;n++){
            S_State_1_4[n] =null ;
        }
        switch (c) {


            case '1': {

                for(int h=0;h<1;h++){
                    if(bulbstate1_4[h]){
                        S_State_1_4[h]="1";
                    }else{
                        S_State_1_4[h]="0";
                    }
                }
                for(int h=1;h<S_State_1_4.length;h++){
                    S_State_1_4[h]="NA";
                }

                break;
            }
            case '2': {
                for(int h=0;h<2;h++){
                    if(bulbstate1_4[h]){
                        S_State_1_4[h]="1";
                    }else{
                        S_State_1_4[h]="0";
                    }
                }

                for(int h=2;h<S_State_1_4.length;h++){
                    S_State_1_4[h]="NA";
                }
                break;
            }
            case '3': {
                for(int h=0;h<3;h++){
                    if(bulbstate1_4[h]){
                        S_State_1_4[h]="1";
                    }else{
                        S_State_1_4[h]="0";
                    }
                }

                for(int h=3;h<S_State_1_4.length;h++){
                    S_State_1_4[h]="NA";
                }
                break;
            }

            case '6': {
                for(int h=0;h<4;h++){
                    if(bulbstate1_4[h]){
                        S_State_1_4[h]="1";
                    }else{
                        S_State_1_4[h]="0";
                    }
                }

                for(int h=0;h<4;){
                    for(int j=4;j<8;j++) {
                        if (bulbstate5_8[h]) {
                            S_State_1_4[j] = "1";
                        } else {
                            S_State_1_4[j] = "0";
                        }
                        h++;
                    }
                }

                for(int h=8;h<S_State_1_4.length;h++){
                    S_State_1_4[h]="NA";
                }
                break;
            }

            case '8': {
                for(int h=0;h<2;h++){
                    if(bulbstate1_4[h]){
                        S_State_1_4[h]="1";
                    }else{
                        S_State_1_4[h]="0";
                    }
                }

                for(int h=2;h<S_State_1_4.length;h++){
                    S_State_1_4[h]="NA";
                }
                break;
            }
            case 'B': {
                for(int h=0;h<4;h++){
                    if(bulbstate1_4[h]){
                        S_State_1_4[h]="1";
                    }else{
                        S_State_1_4[h]="0";
                    }
                }

                for(int h=0;h<1;){
                    for(int j=4;j<5;j++) {
                        if (bulbstate5_8[h]) {
                            S_State_1_4[j] = "1";
                        } else {
                            S_State_1_4[j] = "0";
                        }
                        h++;
                    }
                }

                for(int h=5;h<S_State_1_4.length;h++){
                    S_State_1_4[h]="NA";
                }
                break;
            }

            default: {
                System.out.println("Enter a valid value update.");
            }
        } // END of switch
        //return bsa;
    }
    void switchStatus(char c, boolean bsa[]) {

        switch (c) {

            case '1': {

                bsa[0] = true;
                bsa[1] = false;
                bsa[2] = false;
                bsa[3] = false;
                break;
            }
            case '2': {

                bsa[0] = false;
                bsa[1] = true;
                bsa[2] = false;
                bsa[3] = false;
                break;
            }
            case '3': {

                bsa[0] = true;
                bsa[1] = true;
                bsa[2] = false;
                bsa[3] = false;
                break;
            }
            case '4': {

                bsa[0] = false;
                bsa[1] = false;
                bsa[2] = true;
                bsa[3] = false;
                break;
            }
            case '5': {
                bsa[0] = true;
                bsa[1] = false;
                bsa[2] = true;
                bsa[3] = false;
                break;
            }
            case '6': {
                bsa[0] = false;
                bsa[1] = true;
                bsa[2] = true;
                bsa[3] = false;
                break;
            }
            case '7': {
                bsa[0] = true;
                bsa[1] = true;
                bsa[2] = true;
                bsa[3] = false;
                break;
            }
            case '8': {
                bsa[0] = false;
                bsa[1] = false;
                bsa[2] = false;
                bsa[3] = true;
                break;
            }
            case '9': {
                bsa[0] = true;
                bsa[1] = false;
                bsa[2] = false;
                bsa[3] = true;
                break;
            }
            case 'A': {
                bsa[0] = false;
                bsa[1] = true;
                bsa[2] = false;
                bsa[3] = true;
                break;
            }
            case 'B': {
                bsa[0] = true;
                bsa[1] = true;
                bsa[2] = false;
                bsa[3] = true;
                break;
            }
            case 'C': {
                bsa[0] = false;
                bsa[1] = false;
                bsa[2] = true;
                bsa[3] = true;
                break;
            }
            case 'D': {
                bsa[0] = true;
                bsa[1] = false;
                bsa[2] = true;
                bsa[3] = true;
                break;
            }
            case 'E': {
                bsa[0] = false;
                bsa[1] = true;
                bsa[2] = true;
                bsa[3] = true;
                break;
            }
            case 'F': {
                bsa[0] = true;
                bsa[1] = true;
                bsa[2] = true;
                bsa[3] = true;
                break;
            }
            case '0': {
                bsa[0] = false;
                bsa[1] = false;
                bsa[2] = false;
                bsa[3] = false;
                break;
            }
            default: {
                System.out.println("Enter a valid value switch.");
            }
        } // END of switch
        //return bsa;
    }



}
