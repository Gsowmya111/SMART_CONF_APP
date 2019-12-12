package edisonbro.com.edisonbroautomation.EnergySaving;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import edisonbro.com.edisonbroautomation.Connections.Http_Connect;
import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.databasewired.LogAdapter;
import edisonbro.com.edisonbroautomation.loganalytics.LogAnalyticsActivity;


import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class EnergySavingActivity extends AppCompatActivity {
    LogAdapter log_adap;
    Http_Connect httpcon;
    Button btn_submit,btn_back;
    Spinner spinnr_month;
    ProgressDialog pd;
    TextView tv_time_nor_hrs,tv_kw_nor,tv_total_nor,tv_time_hvac_hrs,tv_kw_hvac,tv_total_hvac,
            tv_total_cost,tv_total_cost_hvac,tv_total_dayscount;

    long S1secs=0,S1hvacsecs=0,S2secs=0,S2hvacsecs=0,S3secs=0,S3hvacsecs=0,S4secs=0,S4hvacsecs=0
            ,S5secs=0,S5hvacsecs=0,S6secs=0,S6hvacsecs=0,S7secs=0,S7hvacsecs=0,S8secs=0,S8hvacsecs=0;
    //String  switch_arr[]={""}
    List<String> arr_dates,arr_onlydate,arr_logforcalc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_energy_saving);
        btn_submit= (Button) findViewById(R.id.btn_submit);

        tv_time_nor_hrs= (TextView) findViewById(R.id.tv_hrs_nor);
        tv_kw_nor= (TextView) findViewById(R.id.tv_kw_nor);
        tv_total_nor= (TextView) findViewById(R.id.total_norm);
        tv_total_cost= (TextView) findViewById(R.id.total_norm_cost);

        tv_time_hvac_hrs= (TextView) findViewById(R.id.tv_hrs_hvac);
        tv_kw_hvac= (TextView) findViewById(R.id.tv_kw_hvac);
        tv_total_hvac= (TextView) findViewById(R.id.total_hvac);
        tv_total_cost_hvac= (TextView) findViewById(R.id.total_hvac_cost);

        tv_total_dayscount= (TextView) findViewById(R.id.tv_dys_cnt);

        spinnr_month= (Spinner) findViewById(R.id.sp_month);

        btn_back= (Button) findViewById(R.id.btnback);

        btn_submit.setEnabled(false);
        btn_submit.setVisibility(View.INVISIBLE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt=new Intent(EnergySavingActivity.this, Main_Navigation_Activity.class);
                startActivity(intnt);
                finish();
            }
        });

        final String[] str = {"1", "2", "3", "4","5","6"};
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>(this, R.layout.spnr_dropdown_layout, str);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnr_month.setAdapter(adp2);
        log_adap = new LogAdapter(this, StaticVariabes_div.housename+"_Log",StaticVariabes_div.housename+"_Log");
        //log_adap.open();

        create_excel();

        //update_excel("20","23","1","2","3","4","1","2","3","4","2018-9-26 11:29:30");

        httpcon=new Http_Connect();

        spinnr_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tv_time_nor_hrs.setText("***");
                tv_kw_nor.setText("***");
                tv_total_nor.setText("***");
                tv_total_cost.setText("***");

                tv_time_hvac_hrs.setText("***");
                tv_kw_hvac.setText("***");
                tv_total_hvac.setText("***");
                tv_total_cost_hvac.setText("***");

                btn_submit.setVisibility(View.INVISIBLE);

                final String mnt=adapterView.getItemAtPosition(i).toString();
                StaticVariabes_div.log("mnt" + mnt, "Energy");


                Thread thlog=new Thread(){
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                //ProgressDialog("Downloading Timer Data From Server....");
                                pd = new ProgressDialog(EnergySavingActivity.this);
                                pd.setMessage("loading");
                                pd.setCancelable(false);
                                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    pd.show();

                            }
                        });

                        String ttm=httpcon.FetchData_log("a","b",mnt,EnergySavingActivity.this);

                        if(ttm.equals("No data")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    btn_submit.setEnabled(false);
                                     Toast.makeText(EnergySavingActivity.this, "No data Available", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else  if(ttm.equals("Success")){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    btn_submit.setEnabled(true);
                                    btn_submit.setVisibility(View.VISIBLE);
                                   // Toast.makeText(EnergySavingActivity.this, "No data Available", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else  if(ttm.equals("Failed")){

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                    btn_submit.setEnabled(false);
                                    Toast.makeText(EnergySavingActivity.this, "Error Fetching Try Again", Toast.LENGTH_LONG).show();

                                }
                            });

                        }

                    }
                };
                thlog.start();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                    S1secs=0;S1hvacsecs=0;S2secs=0;S2hvacsecs=0;S3secs=0;S3hvacsecs=0;S4secs=0;S4hvacsecs=0
                    ;S5secs=0;S5hvacsecs=0;S6secs=0;S6hvacsecs=0;S7secs=0;S7hvacsecs=0;S8secs=0;S8hvacsecs=0;

                     arr_dates =new ArrayList<String>();
                     arr_logforcalc=new ArrayList<String>();
                     arr_onlydate =new ArrayList<String>();
                    log_adap.open();
                    log_adap.getdistinct_devno();



                    for (int k=0;k<Http_Connect.Stat_devn_arr.size();k++){

                        StaticVariabes_div.log("devnos" + Http_Connect.Stat_devn_arr.get(k), "Energy");
                    }




                    for(int m=0;m<Http_Connect.Stat_devn_arr.size();m++){

                        String devt=log_adap.get_devtype_single(Http_Connect.Stat_devn_arr.get(m));

                        StaticVariabes_div.log("devtyps" + devt + "devno"+Http_Connect.Stat_devn_arr.get(m), "Energy");

                        if(devt.equals("008")) {
                            dev008(m,devt);
                        }else if(devt.equals("011")){
                            dev_011(m,devt);
                        }else if(devt.equals("001")){
                            dev_001(m,devt);
                        } else if(devt.equals("002")){
                            dev_002(m);
                        }else if(devt.equals("003")){
                            dev_003(m);
                        }else if(devt.equals("006")){
                            dev_006(m);
                        }


                    }


               /* for (int k=0;k<Http_Connect.S1db.size();k++){

                    StaticVariabes_div.log("out" + Http_Connect.dtedb.get(k), "Energy");
                }*/
                try {
                    Thread.sleep(300);
                    log_adap.close();
                    // getTimeDifference(Http_Connect.dtedb.get(0),Http_Connect.dtedb.get(1));

                    for (int k=0;k<arr_dates.size();k++){

                        String arr_ddt[]=arr_dates.get(k).split(" ");

                        arr_onlydate.add(arr_ddt[0]);

                       // StaticVariabes_div.log("out dates all" + arr_onlydate.get(k), "Energy");
                    }


                    Set<String> uniquedates = new HashSet<String>(arr_onlydate);

                    for (int k=0;k<arr_onlydate.size();k++){

                        StaticVariabes_div.log("out dates all" + arr_onlydate.get(k), "Energy");
                    }

                    List<String> mainList_date = new ArrayList<String>();
                    mainList_date.addAll(uniquedates);

                    for (int k=0;k<uniquedates.size();k++){

                        StaticVariabes_div.log("out dates uniq" + mainList_date.get(k), "Energy");
                    }

                    tv_total_dayscount.setText(""+uniquedates.size());
                    update_excel_dates(mainList_date);

                    long total_secs_nrm=S1secs+S2secs+S3secs+S4secs+S5secs+S6secs+S7secs+S8secs;
                    long total_secs_hvac=S1hvacsecs+S2hvacsecs+S3hvacsecs+S4hvacsecs+S5hvacsecs+S6hvacsecs+S7hvacsecs+S8hvacsecs;


                    // int se=(int) (total_secs_nrm);

                    //  int  total_hrs_nrm=se/3600;
                    long  total_hrs_nrm=total_secs_nrm/3600;
                    long  total_min_nrm= (total_secs_nrm % 3600) / 60;
                    long  total_sec_nrm=total_secs_nrm % 60;

                    tv_time_nor_hrs.setText(total_hrs_nrm+"hrs "+total_min_nrm+"min "+total_sec_nrm+"sec");

                    tv_kw_nor.setText("60");

                    int tota=(int) (total_secs_nrm*0.01667);

                   // //tv_total_nor.setText(""+tota+"W");

                    double costt=(double)tota/1000;

                    double final_cost=costt*12;

                    // StaticVariabes_div.log("tota " + (8/1000), "Energy");


                    String final_cost_inrs = String.format("%.02f", final_cost);
                   // // tv_total_cost.setText(final_cost_inrs+" Rs");


                    long  total_hrs_hvacc=total_secs_hvac/3600;
                    long  total_min_hvacc= (total_secs_hvac % 3600) / 60;
                    long  total_sec_hvacc=total_secs_hvac % 60;

                    tv_time_hvac_hrs.setText(total_hrs_hvacc+"hrs "+total_min_hvacc+"min "+total_sec_hvacc+"sec");

                    tv_kw_hvac.setText("600");

                    int totahvac=(int) (total_secs_hvac*0.1667);

                   // //tv_total_hvac.setText(""+totahvac+"W");

                    double costt_hvac=(double)totahvac/1000;

                    double final_cost_hvac=costt_hvac*12;

                    String final_cost_inrs_hvac = String.format("%.02f", final_cost_hvac);
                  // //  tv_total_cost_hvac.setText(final_cost_inrs_hvac+" Rs");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    public void dev008(int m,String devtype){

        log_adap.getdata_devno(Http_Connect.Stat_devn_arr.get(m));

        for(int s=0;s<Http_Connect.dtedb.size();s++){

            StaticVariabes_div.log(Http_Connect.dtedb.get(s)+" Http_Connect.dtedb  s " + s , "Energy");
        }

        update_excel(Http_Connect.Stat_devn_arr.get(m),devtype);

        for(int s=0;s<2;s++){

            StaticVariabes_div.log("check1 s" + s, "Energy");

            if(s==0) {
                for (int ar = 0; ar<Http_Connect.S1db.size()-1; ar++){

                    StaticVariabes_div.log("check1 ar" + ar, "Energy");

                    if(Http_Connect.S1db.get(ar).equals("1")&&Http_Connect.S1db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S1secs = S1secs + seconds_nw;
                            StaticVariabes_div.log("S1secs" + S1secs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }else  if(s==1) {
                StaticVariabes_div.log("check2 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S2db.size()-1; ar++){

                    StaticVariabes_div.log("check2 ar" + ar, "Energy");

                    if(Http_Connect.S2db.get(ar).equals("1")&&Http_Connect.S2db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S2hvacsecs = S2hvacsecs + seconds_nw;
                            StaticVariabes_div.log("S2hvacsecs" + S2hvacsecs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }

        }
    }

    public void dev_011(int m,String devtype){

        log_adap.getdata_devno(Http_Connect.Stat_devn_arr.get(m));


        for(int s=0;s<Http_Connect.dtedb.size();s++){

            StaticVariabes_div.log(Http_Connect.dtedb.get(s)+" Http_Connect.dtedb  s " + s , "Energy");

        }

        update_excel(Http_Connect.Stat_devn_arr.get(m),devtype);

        for(int s=0;s<5;s++){



            if(s==0) {
                StaticVariabes_div.log("check1 s " + s, "Energy");
                for (int ar = 0; ar<Http_Connect.S1db.size()-1; ar++){

                    StaticVariabes_div.log("check1 ar " + ar, "Energy");

                    if(Http_Connect.S1db.get(ar).equals("1")&&Http_Connect.S1db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S1secs = S1secs + seconds_nw;
                            StaticVariabes_div.log("S1secs" + S1secs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }else  if(s==1) {
                StaticVariabes_div.log("check2 s " + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S2db.size()-1; ar++){

                    StaticVariabes_div.log("check2 ar " + ar, "Energy");

                    StaticVariabes_div.log(Http_Connect.S2db.get(ar)+" Http_Connect.S2db.get ar " + ar, "Energy");

                    int j= ar+1;
                    StaticVariabes_div.log(Http_Connect.S2db.get(j)+" Http_Connect.S2db.get ar " + j, "Energy");


                    if(Http_Connect.S2db.get(ar).equals("1")&&Http_Connect.S2db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S2secs = S2secs + seconds_nw;
                            StaticVariabes_div.log("S2secs" + S2secs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }else  if(s==2) {
                StaticVariabes_div.log("check3 s " + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S3db.size()-1; ar++){

                    StaticVariabes_div.log("check3 ar " + ar, "Energy");

                    if(Http_Connect.S3db.get(ar).equals("1")&&Http_Connect.S3db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S3secs = S3secs + seconds_nw;
                            StaticVariabes_div.log("S3secs" + S3secs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }else  if(s==3) {
                StaticVariabes_div.log("check4 s " + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S4db.size()-1; ar++){

                    StaticVariabes_div.log("check4 ar " + ar, "Energy");

                    if(Http_Connect.S4db.get(ar).equals("1")&&Http_Connect.S4db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S4hvacsecs = S4hvacsecs + seconds_nw;
                            StaticVariabes_div.log("S4hvacsecs" + S4hvacsecs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }else  if(s==4) {
                StaticVariabes_div.log("check5 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S5db.size()-1; ar++){

                    StaticVariabes_div.log("check5 ar" + ar, "Energy");

                    if(Http_Connect.S5db.get(ar).equals("1")&&Http_Connect.S5db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S5hvacsecs = S5hvacsecs + seconds_nw;
                            StaticVariabes_div.log("S5hvacsecs" + S5hvacsecs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }

        }
    }

    public void dev_001(int m,String devtype){

        log_adap.getdata_devno(Http_Connect.Stat_devn_arr.get(m));

        for(int s=0;s<Http_Connect.dtedb.size();s++){

            StaticVariabes_div.log(Http_Connect.dtedb.get(s)+" Http_Connect.dtedb  s " + s , "Energy");
        }

        update_excel(Http_Connect.Stat_devn_arr.get(m),devtype);

        for(int s=0;s<1;s++){

            StaticVariabes_div.log("check1 s" + s, "Energy");

            if(s==0) {
                for (int ar = 0; ar<Http_Connect.S1db.size()-1; ar++){

                    StaticVariabes_div.log("check1 ar" + ar, "Energy");

                    if(Http_Connect.S1db.get(ar).equals("1")&&Http_Connect.S1db.get(ar+1).equals("0")){
                        String date1[]=Http_Connect.dtedb.get(ar).split(" ");
                        String date2[]=Http_Connect.dtedb.get(ar + 1).split(" ");
                        if(date1[0].equals(date2[0])) {
                            long seconds_nw = getTimeDifference(Http_Connect.dtedb.get(ar), Http_Connect.dtedb.get(ar + 1));
                            S1hvacsecs = S1hvacsecs + seconds_nw;
                            StaticVariabes_div.log("S1hvacsecs" + S1hvacsecs, "Energy");
                            arr_dates.add(Http_Connect.dtedb.get(ar));
                            arr_dates.add(Http_Connect.dtedb.get(ar + 1));
                        }
                    }
                }
            }

        }
    }
    public void dev_002(int m){

        log_adap.getdata_devno(Http_Connect.Stat_devn_arr.get(m));

        for(int s=0;s<Http_Connect.dtedb.size();s++){

            StaticVariabes_div.log(Http_Connect.dtedb.get(s)+" Http_Connect.dtedb  s " + s , "Energy");
        }

        for(int s=0;s<2;s++){

            StaticVariabes_div.log("check1 s" + s, "Energy");

            if(s==0) {
                for (int ar = 0; ar<Http_Connect.S1db.size()-1; ar++){

                    StaticVariabes_div.log("check1 ar" + ar, "Energy");

                    if(Http_Connect.S1db.get(ar).equals("1")&&Http_Connect.S1db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S1secs=S1secs+seconds_nw;
                        StaticVariabes_div.log("S1secs" + S1secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==1) {
                StaticVariabes_div.log("check2 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S2db.size()-1; ar++){

                    StaticVariabes_div.log("check2 ar" + ar, "Energy");

                    if(Http_Connect.S2db.get(ar).equals("1")&&Http_Connect.S2db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S2hvacsecs=S2hvacsecs+seconds_nw;
                        StaticVariabes_div.log("S2hvacsecs" + S2hvacsecs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }

        }
    }

    public void dev_003(int m){

        log_adap.getdata_devno(Http_Connect.Stat_devn_arr.get(m));

        for(int s=0;s<Http_Connect.dtedb.size();s++){

            StaticVariabes_div.log(Http_Connect.dtedb.get(s)+" Http_Connect.dtedb  s " + s , "Energy");
        }

        for(int s=0;s<3;s++){

            StaticVariabes_div.log("check1 s" + s, "Energy");

            if(s==0) {
                for (int ar = 0; ar<Http_Connect.S1db.size()-1; ar++){

                    StaticVariabes_div.log("check1 ar" + ar, "Energy");

                    if(Http_Connect.S1db.get(ar).equals("1")&&Http_Connect.S1db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S1secs=S1secs+seconds_nw;
                        StaticVariabes_div.log("S1secs" + S1secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==1) {
                StaticVariabes_div.log("check2 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S2db.size()-1; ar++){

                    StaticVariabes_div.log("check2 ar" + ar, "Energy");

                    if(Http_Connect.S2db.get(ar).equals("1")&&Http_Connect.S2db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S2secs=S2secs+seconds_nw;
                        StaticVariabes_div.log("S2secs" + S2secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==2) {
                StaticVariabes_div.log("check3 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S3db.size()-1; ar++){

                    StaticVariabes_div.log("check3 ar" + ar, "Energy");

                    if(Http_Connect.S3db.get(ar).equals("1")&&Http_Connect.S3db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S3hvacsecs=S3hvacsecs+seconds_nw;
                        StaticVariabes_div.log("S3hvacsecs" + S3hvacsecs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }

        }
    }

    public void dev_006(int m){

        log_adap.getdata_devno(Http_Connect.Stat_devn_arr.get(m));


        for(int s=0;s<Http_Connect.dtedb.size();s++){

            StaticVariabes_div.log(Http_Connect.dtedb.get(s)+" Http_Connect.dtedb  s " + s , "Energy");
        }


        for(int s=0;s<8;s++){



            if(s==0) {
                StaticVariabes_div.log("check1 s " + s, "Energy");
                for (int ar = 0; ar<Http_Connect.S1db.size()-1; ar++){

                    StaticVariabes_div.log("check1 ar " + ar, "Energy");

                    if(Http_Connect.S1db.get(ar).equals("1")&&Http_Connect.S1db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S1secs=S1secs+seconds_nw;
                        StaticVariabes_div.log("S1secs" + S1secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==1) {
                StaticVariabes_div.log("check2 s " + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S2db.size()-1; ar++){

                    StaticVariabes_div.log("check2 ar " + ar, "Energy");

                    StaticVariabes_div.log(Http_Connect.S2db.get(ar)+" Http_Connect.S2db.get ar " + ar, "Energy");

                    int j= ar+1;
                    StaticVariabes_div.log(Http_Connect.S2db.get(j)+" Http_Connect.S2db.get ar " + j, "Energy");


                    if(Http_Connect.S2db.get(ar).equals("1")&&Http_Connect.S2db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S2secs=S2secs+seconds_nw;
                        StaticVariabes_div.log("S2secs" + S2secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==2) {
                StaticVariabes_div.log("check3 s " + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S3db.size()-1; ar++){

                    StaticVariabes_div.log("check3 ar " + ar, "Energy");

                    if(Http_Connect.S3db.get(ar).equals("1")&&Http_Connect.S3db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S3secs=S3secs+seconds_nw;
                        StaticVariabes_div.log("S3secs" + S3secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==3) {
                StaticVariabes_div.log("check4 s " + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S4db.size()-1; ar++){

                    StaticVariabes_div.log("check4 ar " + ar, "Energy");

                    if(Http_Connect.S4db.get(ar).equals("1")&&Http_Connect.S4db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S4secs=S4secs+seconds_nw;
                        StaticVariabes_div.log("S4hvacsecs" + S4secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==4) {
                StaticVariabes_div.log("check5 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S5db.size()-1; ar++){

                    StaticVariabes_div.log("check5 ar" + ar, "Energy");

                    if(Http_Connect.S5db.get(ar).equals("1")&&Http_Connect.S5db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S5secs=S5secs+seconds_nw;
                        StaticVariabes_div.log("S5hvacsecs" + S5hvacsecs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==4) {
                StaticVariabes_div.log("check5 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S5db.size()-1; ar++){

                    StaticVariabes_div.log("check5 ar" + ar, "Energy");

                    if(Http_Connect.S5db.get(ar).equals("1")&&Http_Connect.S5db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S5secs=S5secs+seconds_nw;
                        StaticVariabes_div.log("S5secs" + S5secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==5) {
                StaticVariabes_div.log("check6 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S6db.size()-1; ar++){

                    StaticVariabes_div.log("check6 ar" + ar, "Energy");

                    if(Http_Connect.S6db.get(ar).equals("1")&&Http_Connect.S6db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S6secs=S6secs+seconds_nw;
                        StaticVariabes_div.log("S6secs" + S6secs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==6) {
                StaticVariabes_div.log("check7 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S7db.size()-1; ar++){

                    StaticVariabes_div.log("check7 ar" + ar, "Energy");

                    if(Http_Connect.S7db.get(ar).equals("1")&&Http_Connect.S7db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S7hvacsecs=S7hvacsecs+seconds_nw;
                        StaticVariabes_div.log("S7hvacsecs" + S7hvacsecs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }else  if(s==7) {
                StaticVariabes_div.log("check8 s" + s, "Energy");

                for (int ar = 0; ar<Http_Connect.S8db.size()-1; ar++){

                    StaticVariabes_div.log("check8 ar" + ar, "Energy");

                    if(Http_Connect.S8db.get(ar).equals("1")&&Http_Connect.S8db.get(ar+1).equals("0")){
                        long seconds_nw=getTimeDifference(Http_Connect.dtedb.get(ar),Http_Connect.dtedb.get(ar+1));
                        S8hvacsecs=S8hvacsecs+seconds_nw;
                        StaticVariabes_div.log("S8hvacsecs" + S8hvacsecs, "Energy");
                        arr_dates.add(Http_Connect.dtedb.get(ar));
                        arr_dates.add(Http_Connect.dtedb.get(ar+1));
                    }
                }
            }

        }
    }
    public long getTimeDifference(String dateStart, String dateStop) {
         //dateStart = "11/03/14 09:29:58";
         //dateStop = "11/03/14 09:33:43";
       // dateStart = "2018/9/15 12:40:53";


        /*String[] result = dateStart.split(" ");
        String date_a=result[0];
        String time_b=result[1];*/

       // dateStop = "2018/9/17 12:45:50";

       /* String[] resultt = dateStop.split(" ");
        String date_c=result[0];
        String time_d=result[1];*/

        dateStart=dateStart.replace("-","/");
        dateStop=dateStop.replace("-","/");

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Get msec from each, and subtract.
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) diff / (1000 * 60 * 60 * 24);

        long total_diffSeconds = diff / 1000;


        StaticVariabes_div.log("diffSeconds" + diffSeconds, "Energy");

        StaticVariabes_div.log("diffMinutes" + diffMinutes, "Energy");

        StaticVariabes_div.log("diffHours" + diffHours, "Energy");

        StaticVariabes_div.log("diffInDays" + diffInDays, "Energy");

        StaticVariabes_div.log("total_diffSeconds" + total_diffSeconds, "Energy");

        return total_diffSeconds;
    }


    public void create_excel(){
        File sd = Environment.getExternalStorageDirectory();
        File directory = new File(sd.getAbsolutePath() + "/EdisonBro");

        String csvFile = "EB_EnergySave.xls";
       // String csvFile = "sample.xlsx";


        //File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
        try {

            //file path
            File file = new File(directory, csvFile);
            Toast.makeText(getApplication(), "file create"+file, Toast.LENGTH_SHORT).show();
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("logData", 0);
            WritableSheet sheet2 = workbook.createSheet("UniqueDates", 1);



            sheet.addCell(new Label(0, 0, "Devno")); // column and row
            sheet.addCell(new Label(1, 0, "Devicetype"));
            sheet.addCell(new Label(2, 0, "S1"));
            sheet.addCell(new Label(3, 0, "S2"));
            sheet.addCell(new Label(4, 0, "S3"));
            sheet.addCell(new Label(5, 0, "S4"));
            sheet.addCell(new Label(6, 0, "S5"));
            sheet.addCell(new Label(7, 0, "S6"));
            sheet.addCell(new Label(8, 0, "S7"));
            sheet.addCell(new Label(9, 0, "S8"));
            sheet.addCell(new Label(10, 0, "DateTime"));


            sheet2.addCell(new Label(0, 0, "Datess"));
            //closing cursor
            workbook.write();
            workbook.close();
            Toast.makeText(getApplication(), "Created Excel Sheet", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update_excel(String devno,String devtyp) {


        File sd = Environment.getExternalStorageDirectory();
        File directory = new File(sd.getAbsolutePath() + "/EdisonBro");
        String csvFile = "EB_EnergySave.xls";
       // String csvFile = "sample.xlsx";

        try {

            //file path
            File file = new File(directory, csvFile);

            //Toast.makeText(getApplication(), "file"+file, Toast.LENGTH_SHORT).show();


            if (!file.exists()) {
                Toast.makeText(getApplication(), "File does not exist", Toast.LENGTH_SHORT).show();
                StaticVariabes_div.log("File does not exist", "Energy");
            }else{
                Toast.makeText(getApplication(), "exist", Toast.LENGTH_SHORT).show();

            }

            if(!file.canRead()) {
                Toast.makeText(getApplication(), "Error in reading. Need permission", Toast.LENGTH_SHORT).show();
                StaticVariabes_div.log( "Error in reading. Need permission", "Energy");
            }else{
                Toast.makeText(getApplication(), "read true", Toast.LENGTH_SHORT).show();
            }
            //WorkbookSettings ws = new WorkbookSettings();

           // Workbook workbook = Workbook.getWorkbook(new File(file.toString()), ws);

            Workbook workbook = Workbook.getWorkbook(file);


            Sheet sheet = workbook.getSheet(0);

            int rows=sheet.getRows();
            int clos=sheet.getColumns();

            WritableWorkbook wrkbook_save = Workbook.createWorkbook(file, workbook);
            WritableSheet wrksheet_save = wrkbook_save.getSheet(0);

         /*   ,Http_Connect.S1db.get(s),Http_Connect.S2db.get(s),Http_Connect.S3db.get(s),
                    Http_Connect.S4db.get(s),Http_Connect.S5db.get(s),Http_Connect.S6db.get(s),Http_Connect.S7db.get(s),Http_Connect.S8db.get(s)
                    ,Http_Connect.dtedb.get(s)*/

            for(int s=0;s<Http_Connect.dtedb.size();s++) {

                Label label0 = new Label(0, rows, devno);
                Label label1 = new Label(1, rows, devtyp);
                Label label2 = new Label(2, rows, Http_Connect.S1db.get(s));
                Label label3 = new Label(3, rows, Http_Connect.S2db.get(s));
                Label label4 = new Label(4, rows, Http_Connect.S3db.get(s));
                Label label5 = new Label(5, rows, Http_Connect.S4db.get(s));
                Label label6 = new Label(6, rows, Http_Connect.S5db.get(s));
                Label label7 = new Label(7, rows, Http_Connect.S6db.get(s));
                Label label8 = new Label(8, rows, Http_Connect.S7db.get(s));
                Label label9 = new Label(9, rows, Http_Connect.S8db.get(s));
                Label label10 = new Label(10, rows, Http_Connect.dtedb.get(s));

                // wrksheet_save.addCell(label0);
                wrksheet_save.addCell(label0);
                wrksheet_save.addCell(label1);
                wrksheet_save.addCell(label2);
                wrksheet_save.addCell(label3);
                wrksheet_save.addCell(label4);
                wrksheet_save.addCell(label5);
                wrksheet_save.addCell(label6);
                wrksheet_save.addCell(label7);
                wrksheet_save.addCell(label8);
                wrksheet_save.addCell(label9);
                wrksheet_save.addCell(label10);

                rows++;
            }
            //closing cursor
            wrkbook_save.write();
            wrkbook_save.close();
            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            e.printStackTrace();
        }




    }


    public void update_excel_dates(List<String> dates) {


        File sd = Environment.getExternalStorageDirectory();
        File directory = new File(sd.getAbsolutePath() + "/EdisonBro");
        String csvFile = "EB_EnergySave.xls";
        // String csvFile = "sample.xlsx";

        try {

            //file path
            File file = new File(directory, csvFile);

            //Toast.makeText(getApplication(), "file"+file, Toast.LENGTH_SHORT).show();


            if (!file.exists()) {
                Toast.makeText(getApplication(), "File does not exist", Toast.LENGTH_SHORT).show();
                StaticVariabes_div.log("File does not exist", "Energy");
            }else{
              //  Toast.makeText(getApplication(), "exist", Toast.LENGTH_SHORT).show();

            }

            if(!file.canRead()) {
                Toast.makeText(getApplication(), "Error in reading. Need permission", Toast.LENGTH_SHORT).show();
                StaticVariabes_div.log( "Error in reading. Need permission", "Energy");
            }else{
               // Toast.makeText(getApplication(), "read true", Toast.LENGTH_SHORT).show();
            }
            //WorkbookSettings ws = new WorkbookSettings();

            // Workbook workbook = Workbook.getWorkbook(new File(file.toString()), ws);

            Workbook workbook = Workbook.getWorkbook(file);

            //WritableSheet sheet = workbook.createSheet("logData", 0);
            Sheet sheet = workbook.getSheet(1);

            int rows=sheet.getRows();
            int clos=sheet.getColumns();

            WritableWorkbook wrkbook_save = Workbook.createWorkbook(file, workbook);
            WritableSheet wrksheet_save = wrkbook_save.getSheet(1);

         /*   ,Http_Connect.S1db.get(s),Http_Connect.S2db.get(s),Http_Connect.S3db.get(s),
                    Http_Connect.S4db.get(s),Http_Connect.S5db.get(s),Http_Connect.S6db.get(s),Http_Connect.S7db.get(s),Http_Connect.S8db.get(s)
                    ,Http_Connect.dtedb.get(s)*/

            for(int s=0;s<dates.size();s++) {

                Label label0 = new Label(0, rows, dates.get(s));

                // wrksheet_save.addCell(label0);
                wrksheet_save.addCell(label0);

                rows++;
            }
            //closing cursor
            wrkbook_save.write();
            wrkbook_save.close();
            Toast.makeText(getApplication(), "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();

        }catch (Exception e) {
            e.printStackTrace();
        }




    }

    public void onBackPressed() {
        Intent intnt=new Intent(EnergySavingActivity.this, Main_Navigation_Activity.class);
        startActivity(intnt);
        finish();
    }
}
