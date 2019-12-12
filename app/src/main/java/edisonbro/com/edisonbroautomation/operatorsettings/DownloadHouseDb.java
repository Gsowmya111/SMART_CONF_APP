package edisonbro.com.edisonbroautomation.operatorsettings;

/**
 *  FILENAME: DownloadHouseDb.java
 *  DATE: 07-08-2018

 *  DESCRIPTION:  Activity to Download House.
 *
 *  Copyright (C) EdisonBro Smart Labs Pvt Ltd. All rights reserved.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariables;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp;
import edisonbro.com.edisonbroautomation.databasewired.ServerDetailsAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.HouseConfigurationAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.LocalDatabaseAdapter;


public class DownloadHouseDb extends Activity implements OnClickListener {

	Button download_btn;
	EditText houseName;
	private ProgressDialog pdlg;
	ImageView serverStatus,navigateBack;

	Tcp tcp = null;
	int DownloadProgress = 0;
	int server_online= R.drawable.connected;
	int server_offline=R.drawable.not_connected;

	final int READ_LINE=1 ,READ_BYTE=2,EXCEPTION=3,TCP_LOST=4,BT_LOST=5,ERR_USER=7,MAX_USER=8;
	final String GET_DB_DOWNLOAD = "$118&";
	String SERVER_PASSWORD = null;

	String uhouse = null;
	boolean exceptionOccured=false,isServerAuntheticated=false,IS_SERVER_NOT_AUNTHETICATED=false ;

	LocalDatabaseAdapter localDB = null;
	HouseConfigurationAdapter houseDB = null;
	boolean IS_SERVER_CONNECTED=false,isDialogOpened=false;
	static boolean isTcpConnecting=false ;
	ServerDetailsAdapter sdadap=null;
	final String GET_DB_DOWNLOAD_WLS = "$121&";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.dwn_house_firsttime);
		setupUI(findViewById(R.id.HouseDetailsLayout));	//setting activity view to method for hiding keyboard

		houseName=(EditText) findViewById(R.id.house_name);
		download_btn=(Button) findViewById(R.id.download_db);
		serverStatus=(ImageView) findViewById(R.id.server_status);
		navigateBack=(ImageView) findViewById(R.id.imageView2);

		//setting click listeners
		download_btn.setOnClickListener(this);
		serverStatus.setOnClickListener(this);
		navigateBack.setOnClickListener(this);

		try{
			localDB=new LocalDatabaseAdapter(this);
			localDB.opendb();

		}catch(Exception e){
			e.printStackTrace();
		}

		//calling connection in activity
		RegainingConnection();
	}

	//method to get auto connection in class
	void RegainingConnection(){
		//starting a background thread to make connection
		Thread thread =new Thread(){
			public void run(){
				isTcpConnecting=true;
				IS_SERVER_CONNECTED=Tcp.EstablishConnection(TcpHandler);
				runOnUiThread(new Runnable() {
					public void run()
					{
						if(IS_SERVER_CONNECTED){

							IS_SERVER_CONNECTED=false;

							if(!IS_SERVER_NOT_AUNTHETICATED){

								IS_SERVER_NOT_AUNTHETICATED=false;
								serverStatus.setImageResource(server_online);
								StaticVariables.printLog("TAG","CONNECTED");

							}else{

								IS_SERVER_NOT_AUNTHETICATED=false;

								serverStatus.setImageResource(server_offline);

								StaticVariables.printLog("TAG","NOT CONNECTED");

							}

						}else{

							serverStatus.setImageResource(server_offline);

							StaticVariables.printLog("TAG","NOT CONNECTED");

						}
						isTcpConnecting=false;
					}
				});

			}
		};thread.start();
	}

	//creating Handler for handling TCP connections
	private Handler TcpHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
				case READ_LINE:
				{
					final String Data=(String) msg.obj;

					break;
				}

				case READ_BYTE:	{

					break;
				}

				case EXCEPTION:
				{
					final String Data=(String) msg.obj;
					StaticVariables.printLog("TCP RESPONSE","DATA GET FROM TCP SOCKET :"+Data);
					exceptionOccured=true;		//exception occured
					break;
				}

				case TCP_LOST:
				{
					runOnUiThread(new Runnable() {
						public void run()
						{
							serverStatus.setImageResource(server_offline);	//TCP connection lost status updation in UI
						}
					});
					break;
				}
				case ERR_USER:
				{
					runOnUiThread(new Runnable() {
						public void run()
						{
							IS_SERVER_NOT_AUNTHETICATED=true;
							isDialogOpened=true;
							serverStatus.setImageResource(server_offline);
							TCPErrorAlert("Aunthetication Failed","Invalid UserName/Password.Please check and try again.");
						}
					});
					break;
				}
				case MAX_USER:
				{
					runOnUiThread(new Runnable() {
						public void run()
						{
							IS_SERVER_NOT_AUNTHETICATED=true;
							isDialogOpened=true;
							serverStatus.setImageResource(server_offline);
							TCPErrorAlert("Server Error","Server connections Limit Exceeded .Please disconnect some other client to connect with server.");

						}
					});
					break;
				}
			}
		}
	};

	// Error Alert showing that Bluetooth is Off
	private void TCPErrorAlert(final String title,final String msg) {
		runOnUiThread(new Runnable() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run() {
				AlertDialog.Builder dlg = new AlertDialog.Builder(DownloadHouseDb.this,AlertDialog.THEME_HOLO_DARK);
				dlg.setTitle(title);
				dlg.setMessage(msg);
				dlg.setCancelable(false);
				dlg.setIcon(android.R.drawable.ic_dialog_alert);

				dlg.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
										int which) {
						dialog.dismiss();
						isDialogOpened=false;
					}
				});

				AlertDialog d = dlg.create();
				d.show();
			}
		});
	}
	//connect and download thread
	private void ConnectAndDownload_wireless(){
		SERVER_PASSWORD=StaticVariabes_div.loggeduser+StaticVariabes_div.loggedpwd;
		StaticVariables.printLog("SERVER_PASSWORD","SERVER PASSWORD : "+SERVER_PASSWORD);

		//StaticVariables.HOUSE_NAME=StaticVariabes_div.housename;

		StaticVariables.HOUSE_DB_NAME=StaticVariables.HOUSE_NAME+"_WLS";
		Thread connectionThread=new Thread(){
			public void run(){

				runOnUiThread(new Runnable() {
					public void run() {
						ProgressDialog("Downloading Wireless Data From Server....");
					}
				});

				int i=0;
				boolean serverConnected=false;

				if(!Tcp.tcpConnected)
				{
					tcp=new Tcp(TcpHandler); //passing handler instance

					while(i<5)
					{
						if(exceptionOccured) {
							updateProgress(100);	//updating progress
							break;
						}else if(Tcp.tcpConnected){
							i=10;
							//Tcp.tcpWrite(SERVER_PASSWORD);	//sending password
							byte cipher[]=null;
							try {
								cipher = StaticVariabes_div.encrypt(StaticVariabes_div.IV.getBytes(), StaticVariabes_div.encryptionKey.getBytes(),SERVER_PASSWORD.getBytes());
							} catch (Exception e) {
								e.printStackTrace();
							}
							Tcp.tcpWrite(cipher,true);
							delay(1000);	//delay to get ok response from server
							if(!IS_SERVER_NOT_AUNTHETICATED){

								IS_SERVER_NOT_AUNTHETICATED=false;

								serverConnected=true;
								isServerAuntheticated=true;
							}else{
								IS_SERVER_NOT_AUNTHETICATED=false;
							}
							break;
						}

						i++;
						int j=0;
						while(j<20)	{
							j++;
							if(Tcp.tcpConnected ){
								break;
							}else {
								delay(100);
							}
						}

					}
				}else {
					serverConnected=true;
				}

				if(serverConnected)	{

					runOnUiThread(new Runnable() {
						public void run() {
							//showing server is connected
							serverStatus.setImageResource(server_online);
							//setting House name to save database
							StaticVariables.HOUSE_DB_NAME=StaticVariables.HOUSE_NAME+"_WLS";
						}
					});

					//Sending Db Download Command
					//Tcp.tcpWrite(GET_DB_DOWNLOAD_WLS,DownloadHouseDb.this);

					byte cipher[]=null;
					try {
						cipher = StaticVariabes_div.encrypt(StaticVariabes_div.IV.getBytes(), StaticVariabes_div.encryptionKey.getBytes(),GET_DB_DOWNLOAD_WLS.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
					Tcp.tcpWrite(cipher,true);

					StaticVariables.printLog("DWD","server connected and download command send");

					// setting timeout loop for download task

					int counter1=1;
					int progress=0;
					while(counter1<=10){

						counter1++;
						int counter2=1;
						while(counter2<10){

							if(Tcp.MainDownoad_Completed){
								//setting outer loop counter value =20 and exit from inner loop
								counter1=20;
								break;
							}else{
								delay(100);
							}

							counter2++;
						}
						//updating progress
						updateProgress(progress+=10);
						StaticVariables.printLog("DOWNLOAD TIMER COUNT","DWD TIMER : "+counter1);
					}

					//update progress and making UI thread sleep for 1 sec
					//to show final progress to user
					updateProgress(100);
					SleepUiThread(1000);

					if(counter1<20 && !Tcp.MainDownoad_Completed){
						Tcp.MainDownoad_Completed=false;
						if(!isDialogOpened){
							DownloadErrorDialog();	//showing error dialog
						}

					}else if(Tcp.MainDownoad_Completed){
						Tcp.MainDownoad_Completed=false;
						if(!isDialogOpened){
							// DownloadSuccessDialog();	//showing success dialog
							DownloadSuccessDialog_wireless();
						}
					}


				}else {

					runOnUiThread(new Runnable() {
						public void run() {
							updateProgress(100);	//update task progress
							//making UI thread sleep for 1 sec to show final progress to user
							SleepUiThread(1000);
							cancelDialog();		// Dismissing the progress dialog
							//DismissDialog();
						}
					});

					if(exceptionOccured){

						Tcp.tcpConnectionClose();	//closing connection
						if(!isDialogOpened){
							serverErrorDialog("Server UnReachable",
									"Please Check Your WiFi Settings And Check Whether Server is ON");

						}
					}

					resetVariables();//resetting variables


				}
			}
		};
		connectionThread.start();

	}
	void DownloadSuccessDialog_wireless()
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				//dismissing dialog
				cancelDialog();

				//DismissDialog();

				//updateServerInfo(); // Updating Server Information

				AlertDialog.Builder dialog=new AlertDialog.Builder(DownloadHouseDb.this, AlertDialog.THEME_HOLO_LIGHT);
				dialog.setTitle("Download Completed");
				dialog.setMessage("Wireless settings Downloaded Successfully.Go To Settings And Configure Devices.");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				dialog.setCancelable(false);

				dialog.setNegativeButton("Exit",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process.myPid());//fully exit from app
					}
				});
				dialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

						//closing tcp connection
						Tcp.tcpConnectionClose();

						Intent it=new Intent(DownloadHouseDb.this,Main_Navigation_Activity.class);
						it.setFlags(it.FLAG_ACTIVITY_NEW_TASK | it.FLAG_ACTIVITY_CLEAR_TASK);
						// adding transition to activity exit
						overridePendingTransition(R.anim.slideup, R.anim.slidedown);
						startActivity(it);
						finish();
						/*
						Intent it=new Intent(DownloadDatabse.this,ConfigurationType.class);
						startActivity(it);
						finish(); */
					}
				});


				dialog.show();
			}
		});
	}



	//connect and download thread
	private void ConnectAndDownload(){

		Thread connectionThread=new Thread(){
			public void run(){

				runOnUiThread(new Runnable() {
					public void run() {
						ProgressDialog("Downloading Data From Server....");
					}
				});

				int i=0;
				boolean serverConnected=false;

				if(!Tcp.tcpConnected)
				{
					tcp=new Tcp(TcpHandler); //passing handler instance

					while(i<5)
					{
						if(exceptionOccured) {
							updateProgress(100);	//updating progress
							break;
						}else if(Tcp.tcpConnected){
							i=10;
							//Tcp.tcpWrite(SERVER_PASSWORD);	//sending password
							byte cipher[]=null;
							try {
								cipher = StaticVariabes_div.encrypt(StaticVariabes_div.IV.getBytes(), StaticVariabes_div.encryptionKey.getBytes(),SERVER_PASSWORD.getBytes());
							} catch (Exception e) {
								e.printStackTrace();
							}
							Tcp.tcpWrite(cipher,true);
							delay(1000);	//delay to get ok response from server 
							if(!IS_SERVER_NOT_AUNTHETICATED){

								IS_SERVER_NOT_AUNTHETICATED=false;

								serverConnected=true;
								isServerAuntheticated=true;
							}else{
								IS_SERVER_NOT_AUNTHETICATED=false;
							}
							break;
						}

						i++;
						int j=0;
						while(j<20)	{
							j++;
							if(Tcp.tcpConnected ){
								break;
							}else {
								delay(100);
							}
						}

					}
				}else {
					serverConnected=true;
				}

				if(serverConnected)	{

					runOnUiThread(new Runnable() {
						public void run() {
							//showing server is connected
							serverStatus.setImageResource(server_online);
							//setting House name to save database
							StaticVariables.HOUSE_DB_NAME=StaticVariables.HOUSE_NAME;
						}
					});

					//Sending Db Download Command	
					//Tcp.tcpWrite(GET_DB_DOWNLOAD,DownloadHouseDb.this);

					byte cipher[]=null;
					try {
						cipher = StaticVariabes_div.encrypt(StaticVariabes_div.IV.getBytes(), StaticVariabes_div.encryptionKey.getBytes(),GET_DB_DOWNLOAD.getBytes());
					} catch (Exception e) {
						e.printStackTrace();
					}
					Tcp.tcpWrite(cipher,true);
					StaticVariables.printLog("DWD","server connected and download command send");

					// setting timeout loop for download task

					int counter1=1;
					int progress=0;
					while(counter1<=10){

						counter1++;
						int counter2=1;
						while(counter2<10){

							if(Tcp.MainDownoad_Completed){
								//setting outer loop counter value =20 and exit from inner loop
								counter1=20;
								break;
							}else{
								delay(100);
							}

							counter2++;
						}
						//updating progress
						updateProgress(progress+=10);
						StaticVariables.printLog("DOWNLOAD TIMER COUNT","DWD TIMER : "+counter1);
					}

					//update progress and making UI thread sleep for 1 sec
					//to show final progress to user
					updateProgress(100);
					SleepUiThread(1000);

					if(counter1<20 && !Tcp.MainDownoad_Completed){
						Tcp.MainDownoad_Completed=false;
						if(!isDialogOpened){
							DownloadErrorDialog();	//showing error dialog
						}

					}else if(Tcp.MainDownoad_Completed){
						Tcp.MainDownoad_Completed=false;
						if(!isDialogOpened){
							DownloadSuccessDialog();	//showing success dialog
						}
					}


				}else {

					runOnUiThread(new Runnable() {
						public void run() {
							updateProgress(100);	//update task progress
							//making UI thread sleep for 1 sec to show final progress to user
							SleepUiThread(1000);
							cancelDialog();		// Dismissing the progress dialog

						}
					});

					if(exceptionOccured){

						Tcp.tcpConnectionClose();	//closing connection
						if(!isDialogOpened){
							serverErrorDialog("Server UnReachable",
									"Please Check Your WiFi Settings And Check Whether Server is ON");

						}
					}

					resetVariables();//resetting variables


				}
			}
		};
		connectionThread.start();

	}



	//method to update the current task progress
	void updateProgress(final int progress){
		runOnUiThread(new Runnable() {
			public void run() {
				if(pdlg!=null && progress<=100){
					pdlg.setProgress(progress);	//setting current progress
				}
			}
		});

	}


	//progress dialog for task
	private void ProgressDialog(final String msg)
	{
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if(pdlg==null){
					pdlg=new ProgressDialog(DownloadHouseDb.this,ProgressDialog.THEME_HOLO_LIGHT);
					pdlg.setMessage(msg);
					pdlg.setIndeterminate(false);
					pdlg.setCancelable(false);
					pdlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					pdlg.setMax(100);
					Resources res = getResources();
					// Get the Drawable custom_progressbar                     
					Drawable customDrawable= res.getDrawable(R.drawable.progressbar);
					// set the drawable as progress drawavle
					pdlg.setProgressDrawable(customDrawable);
					pdlg.show();
				}
			}
		});

	}

	// server connection error
	void serverErrorDialog(final String titleMsg,final String msg)
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				cancelDialog();

				AlertDialog.Builder dialog=new AlertDialog.Builder(DownloadHouseDb.this, AlertDialog.THEME_HOLO_DARK);
				dialog.setTitle(titleMsg);
				dialog.setMessage(msg);
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);
				dialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				dialog.show();
			}
		});
	}

	//download error dialog
	void DownloadErrorDialog()
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				cancelDialog();

				AlertDialog.Builder dialog=new AlertDialog.Builder(DownloadHouseDb.this, AlertDialog.THEME_HOLO_DARK);
				dialog.setTitle("Download Failed");
				dialog.setMessage(
						"Possible Reasons :\n 1.Download Timeout Occured. "
								+"\n 2.Unable to fetch Data from Server."
								+"\n 3.Data Integrity Failed."
								+"\n Please Try Downloading Again !");
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);


				dialog.setNeutralButton("Exit",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process.myPid());// exit fully from app
					}
				});

				dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				dialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						//making variables true to get data from socket
						Tcp.tcpReadLineEnabled=true;
						Tcp.tcpDownloading=false;

						resetVariables();//resetting varaibles

						ConnectAndDownload(); 	//starting connect and download task

					}
				});

				dialog.show();
			}
		});
	}

	void DownloadSuccessDialog()
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				//dismissing dialog
				cancelDialog();

				insertHouse();		// Inserting house details in local database

				updateServerInfo(); // Updating Server Information

				AlertDialog.Builder dialog=new AlertDialog.Builder(DownloadHouseDb.this, AlertDialog.THEME_HOLO_LIGHT);
				dialog.setTitle("Download Completed");
				dialog.setMessage("Download Successfull.\n" +"Do You Want To Download Wireless Settings?");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				dialog.setCancelable(false);

				dialog.setNegativeButton("Exit",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process.myPid());// exit fully from app
					}
				});

				dialog.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

						ConnectAndDownload_wireless();

						/*Intent it=new Intent(DownloadHouseDb.this,Main_Navigation_Activity.class);
						startActivity(it);
						//adding transition to activity exit
						overridePendingTransition(R.anim.slideup, R.anim.slidedown);
						finish();*/
					}
				});

				dialog.show();
			}
		});
	}


	//dismissing the dialog
	void cancelDialog(){
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				if(pdlg!=null){
					pdlg.dismiss();		// Dismissing the progress dialog
					pdlg=null;
				}

			}
		});
	}

	//inserting house details in local table
	private void insertHouse(){
		try{

			String House_Name=StaticVariables.HOUSE_NAME;
			StaticVariabes_div.HOUSE_NAME=StaticVariables.HOUSE_NAME;
			int HNO=localDB.fetchMaxHouseNo();
			StaticVariables.printLog("DB","Last Max HNO : "+HNO);

			ServerDetailsAdapter.OriginalDataBase =StaticVariables.HOUSE_NAME+".db";
			sdadap=new ServerDetailsAdapter(this);
			sdadap.open();
			//Cursor cr=sdadap.fetchadminpass(StaticVariables.UserName_Used,StaticVariables.Password_Used);
			Cursor cr=sdadap.fetchlogintype(StaticVariables.UserName_Used);
			if(cr!=null){
				StaticVariables.Loggeduser_Used=cr.getString(cr.getColumnIndexOrThrow(ServerDetailsAdapter.KEY_da));
				Toast.makeText(this,"loggeduser"+StaticVariables.Loggeduser_Used,Toast.LENGTH_SHORT);

				/*if(StaticVariables.Loggeduser_Used!=null) {
				}*/

			}
			sdadap.close();

			byte cipher[]=null;
			try {
				cipher = StaticVariabes_div.encrypt(StaticVariabes_div.IV.getBytes(), StaticVariabes_div.encryptionKey.getBytes(),StaticVariables.Password_Used.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
			String Encryptd_password=new String(cipher);

			StaticVariables.printLog("Encryptd_password","Db "+Encryptd_password);


			if(HNO==0)  {
				//localDB.insert(1,House_Name);


				localDB.insert(1,House_Name,StaticVariables.UserName_Used,cipher,StaticVariables.Loggeduser_Used);

				//localDB.insert(1,House_Name,StaticVariables.UserName_Used,StaticVariables.Password_Used,StaticVariables.Loggeduser_Used);
				StaticVariables.printLog("DB","First Time Record Updated : "+House_Name);
			}
			else {
				int hnoGet=HNO+1;
				//localDB.insert(hnoGet,House_Name);
				localDB.insert(hnoGet,House_Name,StaticVariables.UserName_Used,cipher,StaticVariables.Loggeduser_Used);
				StaticVariables.printLog("DB","Next Record Inserted : "+House_Name);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//updating server table information
	private void updateServerInfo(){
		try{

			try{
				houseDB=new HouseConfigurationAdapter(this);
				houseDB.open();			//opening house database

			}catch(Exception e){
				e.printStackTrace();
			}

			ConnectivityManager cm=(ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
			NetworkInfo netinfo=cm.getNetworkInfo(cm.TYPE_WIFI);

			//WifiManager wm=(WifiManager) getSystemService(WIFI_SERVICE);
			WifiManager wm = (WifiManager) getApplicationContext().getSystemService(getApplication().WIFI_SERVICE);
			WifiInfo wif=wm.getConnectionInfo();

			if(netinfo != null && netinfo.getState()==NetworkInfo.State.CONNECTED){
				String ssidget=wif.getSSID();	//getting network SSID
				String ssid=null;
				if(ssidget.contains("\"") ){
					ssid=ssidget.substring(1, ssidget.length()-1);
				}else{
					ssid=ssidget;
				}

				//String ip=StaticVariables.Host;
				///int port=StaticVariables.Port;

				String ip=Tcp.tcpHost;
				int port=Tcp.tcpPort;

				StaticVariables.printLog("TCP","ssid get : "+ssidget+" Filtered SSID : "+ssid);
				try{
					boolean isUpdated=houseDB.updateServerTable(ip, port, ssid); // updating server info
					if(isUpdated)
						StaticVariables.printLog("DB","server info updated");
				}catch(Exception e){
					e.printStackTrace();
				}

			}

			houseDB.close();  //closing house database

		}catch(Exception e){
			e.printStackTrace();
		}
	}


	//delay for ui thread
	void SleepUiThread(final long delay)
	{
		runOnUiThread(new  Runnable() {
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}

	//delay for normal threads
	void delay(long delay)
	{
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// resetting all variables 
	void resetVariables(){
		exceptionOccured=false;
		isServerAuntheticated=false;
		//SERVER_AUNTHETICATED=null;
	}



	// button click event
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.download_db:
			{
				uhouse=houseName.getText().toString();
				boolean houseNameExists=false;
				boolean numeric=false,startswithnumber=false;
				//checking if house name already used or not for downloading DB
				if(uhouse!=null && uhouse.length()>0){

					//trimming leading and trailing space
					uhouse=uhouse.trim();

					houseNameExists=localDB.HouseNameExists(uhouse); //checking if  house name
					// existing in Db or not

					numeric=uhouse.matches("[0-9]+");

					StaticVariables.printLog("TAG","numeric "+numeric);

					startswithnumber=Character.isDigit(uhouse.charAt(0));
				}

				//client side input data validations
				if((uhouse==null || uhouse.length()==0 ) || houseNameExists)
				{
					if(uhouse==null  || uhouse.length()==0 )	{
						houseName.setError("Please Enter House Name");
					}else if(houseNameExists){
						houseName.setError("House Already Exists With This Name.Try Another Name!");
					}else if(numeric){
						houseName.setError("House Name Should be alpha numeric.Try Another Name!");
					}else if(startswithnumber){
						houseName.setError("House Name cant start with number.Try Another Name!");
					}else{
						houseName.setError(null);
						houseName.setText(uhouse);
					}

				}else {

					houseName.setError(null);

					//setting housname in static variables to download database

					StaticVariables.HOUSE_NAME=uhouse;
					StaticVariables.HOUSE_DB_NAME=uhouse;
					StaticVariables.printLog("NAMES","HOUSE NAME:"+StaticVariables.HOUSE_NAME);

					//setting servr password from static fields to connect server if in case connection lost
					//String uname=StaticVariables.UserName;
					//String upass=StaticVariables.Password;

					String uname=StaticVariabes_div.loggeduser;
					String upass=StaticVariabes_div.loggedpwd;

					if(uname!=null && upass!=null){
						SERVER_PASSWORD=uname+upass;
						StaticVariables.printLog("SERVER_PASSWORD","SERVER PASSWORD : "+SERVER_PASSWORD);

						resetVariables();//resetting varaibles

						ConnectAndDownload(); 	//starting connect and download task

					}
					else {
						StaticVariables.printLog("INCORRECT SERVER PASSWORD","Invalid Login Details");
					}
				}

				break;
			}
			case R.id.server_status:{
				if(!Tcp.tcpConnected){
					if(!isTcpConnecting){
						msg("connecting To Server...");
						RegainingConnection();

					}else{
						StaticVariables.printLog("TAG","already Connecting tcp");
					}
				}else{
					msg("server is already connected");
				}
				break;
			}
			case R.id.imageView2:{
				ExitDialog();
				break;
			}
		}
	}

	//showing toast message
	void msg(final String text){
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getApplicationContext(),
						text,Toast.LENGTH_SHORT).show();

			}
		});
	}

	//methods for hiding the soft input keyboard
	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager) activity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
				.getWindowToken(), 0);

	}

	public  void setupUI(View view)
	{
		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText))
		{

			view.setOnTouchListener(new OnTouchListener()
			{

				public boolean onTouch(View v, MotionEvent event)
				{
					hideSoftKeyboard(DownloadHouseDb.this);

					return false;
				}

			});
		}
	}

	//exit confirmation dialog
	void ExitDialog()
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@Override
			public void run()
			{
				AlertDialog.Builder dialog=new AlertDialog.Builder(DownloadHouseDb.this, AlertDialog.THEME_HOLO_DARK);
				dialog.setTitle("Exit");
				dialog.setMessage("Do You Really Want To Exit?");
				dialog.setIcon(android.R.drawable.ic_dialog_alert);
				dialog.setCancelable(false);

				dialog.setNegativeButton("NO",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						android.os.Process.killProcess(android.os.Process.myPid());//fully exit from app
					}
				});

				dialog.show();
			}
		});
	}

	@Override
	public void onBackPressed() {
		ExitDialog();

	}

	@Override
	protected void onPause() {
		super.onPause();
		if(LocalDatabaseAdapter.sdb.isOpen()){
			localDB.close();
			StaticVariables.printLog("TAG","DB CLOSED ");
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!LocalDatabaseAdapter.sdb.isOpen()){
			localDB.opendb();
			StaticVariables.printLog("TAG","DB open ");
		}
	}

}
