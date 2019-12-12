package edisonbro.com.edisonbroautomation.operatorsettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import edisonbro.com.edisonbroautomation.Connections.TcpTransfer;
import edisonbro.com.edisonbroautomation.Connections.Tcp_con;
import edisonbro.com.edisonbroautomation.Connections.UsbService;
import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticStatus;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariabes_div;
import edisonbro.com.edisonbroautomation.StaticClasses.StaticVariables;
import edisonbro.com.edisonbroautomation.connectionswirelessir.Tcp;
import edisonbro.com.edisonbroautomation.databasewireless.HouseConfigurationAdapter;
import edisonbro.com.edisonbroautomation.databasewireless.WirelessConfigurationAdapter;



public class WirelessConfigurationDetails extends Activity implements TcpTransfer,OnClickListener
,OnItemSelectedListener,OnCheckedChangeListener{

	private static final String TAG1="WirelessConfigDetials - ";

	EditText room_name, bt_MacId,group_Id,et_user_dev_name;
	Button submit_btn,clear_btn,b_home;
	Spinner dev_type,room_names_list,groupId,sp_icons,sp_iconstype;
	SpinnerAdapter deviceAdapter,roomNameAdapter ,groupIdAdapter;
	ImageView bt_status,serverStatus,navigateBack;
	ProgressDialog pdlg;
	CheckBox groupCheck;

	int spinnerLayoutId=R.layout.spinnerlayout;
	String DeviceType = null, Bluetooth_ID = null,RoomName=null;
	final int READ_LINE_BT=1 ,READ_BYTE_BT=2,EXCEPTION_BT=3,TCP_LOST=4,BT_LOST=5;
	int selectedItemPosition=0;
	boolean exceptionOccured=false;

	List<String>  Short_DevNames_List;
	List<String> Full_DevNames_List;
	List<String> Device_Type_List;
	List<String> deviceGroupIdList;

	int BT_ON  = R.drawable.usb_icon_on;
	int BT_OFF = R.drawable.usb_icon_off;
	int server_online=R.drawable.connected;
	int server_offline=R.drawable.not_connected;

	LinearLayout other_room_layout;

	Tcp tcp=null;
	//Bluetooth bluetooth=null;
	HouseConfigurationAdapter houseDB=null;
	WirelessConfigurationAdapter WhouseDB=null;

	boolean IS_SERVER_CONNECTED=false;
	ArrayList<String> UniqueRoomList;
	TreeSet<String> uniqueRoomsList_wls;
	LinearLayout roomname_lyaout,groupIdLayout;

	final String BT_CONFIG_MODE="$$$";
	final String BT_SET_BAUDRATE="SU,19.2"+"\r\n"; 
	final String BT_EXIT_CONFIG_MODE="---"+"\r\n";
	final String BT_CHECK_BAUDRATE="GU"+"\r\n";

	static volatile int CMD_COUNT=1;
	static boolean isTcpConnecting=false;

	String NFC_TAG="NFC Tag";
	String DEFAULT_GROUPID="0", selectedGroupId=DEFAULT_GROUPID;

	String PIR_DevType="WPS1", PIR_Direct_DevType="WPD1", mIR_Direct_DevType="WIR1";

	// Declaring the Integer Array with resourse Id's of Images for the Spinners
	/*Integer[] images = { 0, R.drawable.im_entrance3, R.drawable.im_conference3,
			R.drawable.im_reception3, R.drawable.im_bedroom3, R.drawable.im_diningroom3,
			R.drawable.im_kitchen3,R.drawable.im_livingroom3,R.drawable.im_studyroom3,R.drawable.im_playroom3 ,R.drawable.im_dgm3,
			R.drawable.im_cgm3,R.drawable.im_gm3,R.drawable.im_ps3,R.drawable.im_civilroom3,R.drawable.im_corridor3,R.drawable.im_visitorroom3,
			R.drawable.im_serverroom3,R.drawable.im_liftlobby3,R.drawable.im_stairs3,R.drawable.im_toiletcorridor3,R.drawable.im_toiletdis3,
			R.drawable.im_toiletf3,R.drawable.im_toiletm3,
	};*/
	Integer[] images;

	Integer[] ALL = { 0, R.drawable.im_entrance3, R.drawable.im_conference3,
			R.drawable.im_reception3, R.drawable.im_bedroom3, R.drawable.im_diningroom3,
			R.drawable.im_kitchen3,R.drawable.im_livingroom3,R.drawable.im_studyroom3,R.drawable.im_playroom3 ,R.drawable.im_dgm3,
			R.drawable.im_cgm3,R.drawable.im_gm3,R.drawable.im_ps3,R.drawable.im_civilroom3,R.drawable.im_corridor3,R.drawable.im_visitorroom3,
			R.drawable.im_serverroom3,R.drawable.im_liftlobby3,R.drawable.im_stairs3,R.drawable.im_toiletcorridor3,R.drawable.im_toiletdis3,
			R.drawable.im_toiletf3,R.drawable.im_toiletm3,R.drawable.im_accounts3,R.drawable.im_bathroomm3,R.drawable.im_chef3,R.drawable.im_childcare3,R.drawable.im_cross3,R.drawable.im_entertain3,R.drawable.im_finance3,
			R.drawable.im_garden3,R.drawable.im_guest3,R.drawable.im_gym3,R.drawable.im_hall3,R.drawable.im_laundryy3,R.drawable.im_liftspace3,R.drawable.im_movie3,R.drawable.im_multimedia3,R.drawable.im_om3,R.drawable.im_pa3,R.drawable.im_poojaa3,R.drawable.im_psaccount3,R.drawable.im_psec3,
			R.drawable.im_recept3,R.drawable.im_servent3,R.drawable.im_sikh3,R.drawable.im_storeroom3,R.drawable.im_swimmingpool3,R.drawable.im_workstation3
	};


	Integer[] Home = { 0, R.drawable.im_entrance3,
			R.drawable.im_bedroom3, R.drawable.im_diningroom3,
			R.drawable.im_kitchen3,R.drawable.im_livingroom3,R.drawable.im_studyroom3,R.drawable.im_playroom3 ,
			R.drawable.im_corridor3,R.drawable.im_visitorroom3,
			R.drawable.im_serverroom3,R.drawable.im_liftlobby3,R.drawable.im_stairs3,R.drawable.im_toiletcorridor3,R.drawable.im_toiletdis3,
			R.drawable.im_toiletf3,R.drawable.im_toiletm3,R.drawable.im_accounts3,R.drawable.im_bathroomm3,R.drawable.im_chef3,R.drawable.im_childcare3,R.drawable.im_cross3,R.drawable.im_entertain3,
			R.drawable.im_garden3,R.drawable.im_guest3,R.drawable.im_gym3,R.drawable.im_hall3,R.drawable.im_laundryy3,R.drawable.im_liftspace3,R.drawable.im_movie3,R.drawable.im_multimedia3,R.drawable.im_om3,R.drawable.im_poojaa3,
			R.drawable.im_servent3,R.drawable.im_sikh3,R.drawable.im_storeroom3,R.drawable.im_swimmingpool3,R.drawable.im_workstation3
	};

	Integer[] Office = { 0, R.drawable.im_entrance3, R.drawable.im_conference3,
			R.drawable.im_reception3, R.drawable.im_diningroom3,
			R.drawable.im_kitchen3,R.drawable.im_livingroom3,R.drawable.im_playroom3 ,R.drawable.im_dgm3,
			R.drawable.im_cgm3,R.drawable.im_gm3,R.drawable.im_ps3,R.drawable.im_civilroom3,R.drawable.im_corridor3,R.drawable.im_visitorroom3,
			R.drawable.im_serverroom3,R.drawable.im_liftlobby3,R.drawable.im_stairs3,R.drawable.im_toiletcorridor3,R.drawable.im_toiletdis3,
			R.drawable.im_toiletf3,R.drawable.im_toiletm3,R.drawable.im_accounts3,R.drawable.im_entertain3,R.drawable.im_finance3,
			R.drawable.im_liftspace3,R.drawable.im_movie3,R.drawable.im_multimedia3,R.drawable.im_pa3,R.drawable.im_psaccount3,R.drawable.im_psec3,
			R.drawable.im_recept3,R.drawable.im_storeroom3,R.drawable.im_workstation3
	};

	Integer[] Hotel = { 0, R.drawable.im_entrance3, R.drawable.im_conference3,
			R.drawable.im_reception3, R.drawable.im_bedroom3, R.drawable.im_diningroom3,
			R.drawable.im_kitchen3,R.drawable.im_livingroom3,R.drawable.im_playroom3 ,R.drawable.im_corridor3,R.drawable.im_visitorroom3,
			R.drawable.im_serverroom3,R.drawable.im_liftlobby3,R.drawable.im_stairs3,R.drawable.im_toiletcorridor3,R.drawable.im_toiletdis3,
			R.drawable.im_toiletf3,R.drawable.im_toiletm3,R.drawable.im_accounts3,R.drawable.im_bathroomm3,R.drawable.im_chef3,R.drawable.im_childcare3,R.drawable.im_cross3,R.drawable.im_entertain3,R.drawable.im_finance3,
			R.drawable.im_garden3,R.drawable.im_guest3,R.drawable.im_gym3,R.drawable.im_hall3,R.drawable.im_laundryy3,R.drawable.im_liftspace3,R.drawable.im_movie3,R.drawable.im_multimedia3,
			R.drawable.im_recept3,R.drawable.im_servent3,R.drawable.im_storeroom3,R.drawable.im_swimmingpool3,
	};
	String User_Device_Name,Room_Icon_Image;

	String WIRELESS_HOUSE_NAME=null;

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

	Button btsig;
	ImageView btnconstatus;
	boolean Usb_device_connected=false;

	/*
      * Notifications from UsbService will be received here.
      */
	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()) {
				case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
					Toast.makeText(context, "USB Ready", Toast.LENGTH_SHORT).show();
					//usbService.write(("AT" + "\r\n").getBytes());
					bt_status.setImageResource(BT_ON);
					Usb_device_connected=true;
					break;
				case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
					Toast.makeText(context, "USB Permission not granted", Toast.LENGTH_SHORT).show();
					break;
				case UsbService.ACTION_NO_USB: // NO USB CONNECTED
					Toast.makeText(context, "No USB connected", Toast.LENGTH_SHORT).show();
					Usb_device_connected=false;
					//popup("No USB connected");
					break;
				case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
					Toast.makeText(context, "USB disconnected", Toast.LENGTH_SHORT).show();
					bt_status.setImageResource(BT_OFF);
					popup("USB disconnected");
					break;
				case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
					Toast.makeText(context, "USB device not supported", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};

	private UsbService usbService;

	private MyHandler mHandler;

	boolean isDbExists=false;


	private final ServiceConnection usbConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			usbService = ((UsbService.UsbBinder) arg1).getService();
			usbService.setHandler(mHandler);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			usbService = null;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.wireless_addnewdevice);
		//setting activity view to method for hiding keyboard
		setupUI(findViewById(R.id.ConfigDetailsLayout));
		mHandler = new MyHandler(this);


		room_names_list = (Spinner) findViewById(R.id.room_name_spin);
		bt_MacId = (EditText) findViewById(R.id.mac_id);
		dev_type = (Spinner) findViewById(R.id.devtype);
		bt_status=(ImageView) findViewById(R.id.bt_status);
		btnconstatus=(ImageView) findViewById(R.id.server_status);
		btsig=(Button) findViewById(R.id.btnsignal);
		room_name=(EditText) findViewById(R.id.room_name);
		sp_icons= (Spinner) findViewById(R.id.spinner_icons);
		sp_iconstype= (Spinner) findViewById(R.id.spinner_iconstype);
		et_user_dev_name= (EditText) findViewById(R.id.et_usrdevname);

		navigateBack=(ImageView) findViewById(R.id.imageView2);

		submit_btn = (Button) findViewById(R.id.submit);
		clear_btn= (Button) findViewById(R.id.clear);

		roomname_lyaout=(LinearLayout) findViewById(R.id.roomNameLyt);
		groupIdLayout=(LinearLayout) findViewById(R.id.groupCheckBoxLayout); 

		//setting text field layout visibility gone
		roomname_lyaout.setVisibility(View.GONE);

		group_Id= (EditText) findViewById(R.id.gid);
		groupId=(Spinner) findViewById(R.id.groupID); 
		groupCheck=(CheckBox) findViewById(R.id.groupCheck);

		//check change listener for check box
		groupCheck.setOnCheckedChangeListener(this);

		//setting click listener button
		clear_btn.setOnClickListener(this); 
		submit_btn.setOnClickListener(this);

		// setting item selected listener for spinner
		dev_type.setOnItemSelectedListener(this);	
		room_names_list.setOnItemSelectedListener(this);	 
		groupId.setOnItemSelectedListener(this);
		sp_icons.setOnItemSelectedListener(this);

		//setting click listener for server status image
		btnconstatus.setOnClickListener(this);
		navigateBack.setOnClickListener(this);


		b_home= (Button) findViewById(R.id.btnhome);
		b_home.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intnt=new Intent(WirelessConfigurationDetails.this, Main_Navigation_Activity.class);
				startActivity(intnt);
				finish();
			}
		});


		//initialize grouplist
		deviceGroupIdList=new ArrayList<String>();

		//making group id spinner invisible
		disableCheckBox();

       //assigning house name
		WIRELESS_HOUSE_NAME=StaticVariables.HOUSE_NAME+"_WLS";

		//setting house name for wireless database
		StaticVariables.WHOUSE_NAME=WIRELESS_HOUSE_NAME;

		//setting database name with which wireless database is to save
		StaticVariables.HOUSE_DB_NAME=WIRELESS_HOUSE_NAME;

		//setting port number  for wireless db
	//	StaticVariables.Port=StaticVariables.WIRELSESS_SERVER_PORT;

		//StaticVariables.Port=StaticVariables.MAIN_SERVER_PORT;

		Tcp.tcpPort=StaticVariables.MAIN_SERVER_PORT;

		Tcp.tcpHost= Tcp_con.tcpAddress;
		//Tcp.tcpPort= Tcp_con.tcpPort;

		//closing tcp connection
		//Tcp.tcpConnectionClose();

		try{
			//opening wireless Panel database	
			WhouseDB=new WirelessConfigurationAdapter(this);
			WhouseDB.open();

			houseDB=new HouseConfigurationAdapter(this);
			houseDB.open();			//opening house database			
		}catch(Exception e){
			e.printStackTrace();
		}



		try {
			isDbExists=WhouseDB.checkdb();
		} catch (IOException e) {
			//e.printStackTrace();
			StaticVariables.printLog("TAG","unable to open database");
		}

		if(isDbExists){

		}else {
			popupdev("Please go to the Configuration Settings and download wireless database First");
		}

		FullDevNameList();				// Assigning Full names For Devices
		ShortDevNameList();				// Assigning short names For Devices
		DevTypeList();					// Assigning Device Types For Devices


		//Fetching list of All room names from database and adding to local array list
		UniqueRoomList=new ArrayList<String>();
		TreeSet<String> rooms_wls=new TreeSet<String>(); 
		try{
			rooms_wls=houseDB.RoomNameList();
			//Fetching list of All room names from database and adding to local array list
			if(rooms_wls.size()>0)
				UniqueRoomList.addAll(rooms_wls);
		}catch(Exception e){
			e.printStackTrace();
		}


		UniqueRoomList.add("Add New Room");
		UniqueRoomList.add("Select Room");

		//Loading data in room name spinner
		roomNameAdapter=new CustomSpinnerAdapter(this, spinnerLayoutId, UniqueRoomList);
		room_names_list.setAdapter(roomNameAdapter);	// Setting adapter to spinner



		// Setting a Custom Adapter to the Spinner
		//sp_icons.setAdapter(new MyAdapter(WirelessConfigurationDetails.this, R.layout.custom, images));
		sp_icons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				if(position!=0) {
					String imageName = getResources().getResourceName(images[position]);
					//int actual=imageName.lastIndexOf(":drawable/",0);
					int actual=imageName.indexOf(":drawable/",0);
					String actimg=imageName.substring(actual+10,imageName.length()-1);
					//Toast.makeText(WirelessConfigurationDetails.this, actimg+"  name " + imageName, Toast.LENGTH_SHORT).show();
					Room_Icon_Image=actimg;
				}else{

					Room_Icon_Image="select";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});


		sp_iconstype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


				String swbicnsselectd=String.valueOf(sp_iconstype.getSelectedItem());

				if(swbicnsselectd.equals("Home")) {
					sp_icons.setAdapter(new MyAdapter(WirelessConfigurationDetails.this, R.layout.custom, Home));
					images=Home;
				}else if(swbicnsselectd.equals("Hotel")) {
					sp_icons.setAdapter(new MyAdapter(WirelessConfigurationDetails.this, R.layout.custom, Hotel));
					images=Hotel;
				}else if(swbicnsselectd.equals("Office")) {
					sp_icons.setAdapter(new MyAdapter(WirelessConfigurationDetails.this, R.layout.custom, Office));
					images=Office;
				}else{

					sp_icons.setAdapter(new MyAdapter(WirelessConfigurationDetails.this, R.layout.custom, ALL));
					images=ALL;
				}


			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		//fetching intent
		Intent DataPacket=getIntent();				
		boolean isAddNewDevice=false;

		try{
			if(DataPacket!=null)
				isAddNewDevice=DataPacket.getBooleanExtra("isAddNewDevice",false);
		}catch(Exception e){
			e.printStackTrace();
		}

		//making current room name selected
		if(isAddNewDevice){
			String currentRoom= StaticVariables.CurrentConfigurationRoom;
			int inddex=UniqueRoomList.indexOf(currentRoom);

			room_names_list.setSelection(inddex);
		}else{

			//setting selection in spinner
			if(UniqueRoomList.size()>2){ 
				room_names_list.setSelection(UniqueRoomList.size()-1); 
			}else{ 
				room_names_list.setSelection(UniqueRoomList.size()-2); 
			}

		}





		// Loading data into custom adapater
		deviceAdapter=new CustomSpinnerAdapter(this, spinnerLayoutId, Full_DevNames_List);
		dev_type.setAdapter(deviceAdapter);		// Setting adapter to spinner
		setDeviceNameSelection();

	/*	try{
			//Fetching bluetooth MacId from Database
			Cursor mcursor=houseDB.fetchData(HouseConfigurationAdapter.CONFIG_TABLE);
			Bluetooth_ID=mcursor.getString(mcursor.getColumnIndex(HouseConfigurationAdapter.CONFIG_MacID));
			Bluetooth_ID="12345123451234512";
			if(Bluetooth_ID!=null && Bluetooth_ID.length()==17){
				bt_MacId.setText(Bluetooth_ID);
			}

			//closing cursor
			if(mcursor!=null){
				mcursor.close();
			}

		}catch(Exception e){
			e.printStackTrace(); 
		}*/

		//calling connection in activity
	//	RegainingConnection();

		Tcp_con mTcp = new Tcp_con(this);

		if(Tcp_con.isClientStarted){

			receiveddata(NetwrkType, StaticStatus.Network_Type,null);
			receiveddata(ServStatus,StaticStatus.Server_status,null);

		}else{
			Tcp_con.stacontxt=WirelessConfigurationDetails.this;
			Tcp_con.serverdetailsfetch(this, StaticVariabes_div.housename);
			Tcp_con.registerReceivers(WirelessConfigurationDetails.this);
		}

		if(UsbService.SERVICE_CONNECTED) {

			StaticVariables.printLog("usb","SERVICE_CONNECTED");

			if (UsbService.serialPortConnected) {
				StaticVariables.printLog("usb","serialPortConnected");
				bt_status.setImageResource(BT_ON);
			} else {
				popup("Usb not connected 1");
			}
		}else {
			StaticVariables.printLog("usb","SERVICE_NOT_CONNECTED");

			if (Usb_device_connected) {
				bt_status.setImageResource(BT_ON);
			} else {
				//popup("Usb not connected 2");
			}
		}

	}

	//method to get auto TCP connection in class
/*
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
							btnconstatus.setImageResource(server_online);
							StaticVariables.printLog("TAG","CONNECTED");

						}else{
							btnconstatus.setImageResource(server_offline);
							StaticVariables.printLog("TAG","NOT CONNECTED");
						}
						isTcpConnecting=false;
					}
				});

			}
		};thread.start();			
	}	
*/

	//set device name in spinner 
	void setDeviceNameSelection(){


		//getting inten from previous activiy if evice mismatch happened
		try{

			Intent intent=getIntent();
			if(intent!=null){

				String devType=intent.getStringExtra("CONFIG_DEVICE_TYPE");

				if(devType!=null){

					// getting index of previously configured device
					int index=Device_Type_List.indexOf(devType);				

					// getting full name of device based on  device type index 
					String deviceNname=Full_DevNames_List.get(index);	

					StaticVariables.printLog("Tag","Device Name :"+deviceNname);

					//setting device name spinner selection
					dev_type.setSelection(index);		

				}else{
					// Displaying Last item of list
					dev_type.setSelection(Full_DevNames_List.size()-1);		

				}
			}else{
				// Displaying Last item of list
				dev_type.setSelection(Full_DevNames_List.size()-1);		

			}


		}catch(Exception e){
			e.printStackTrace();
		}

	}


	// Filling Device Name List with Full Names which will Visible to End User
	private void FullDevNameList(){

		Full_DevNames_List=new ArrayList<String>();

		Resources res = getResources();
		String[] fullNames = res.getStringArray(R.array.Full_DevNames_Wireless);
		Full_DevNames_List .addAll(Arrays.asList(fullNames));

		//adding select option item in list
		Full_DevNames_List.add("Select Device");

	}

	// Filling Device List with Short Names Which Will be used Internally 
	private void ShortDevNameList(){
		Resources res = getResources();
		String[] shortNames = res.getStringArray(R.array.Short_DevNames_Wireless);
		Short_DevNames_List = Arrays.asList(shortNames);
	}

	// Filling Device Type Which Will be used Internally 
	private void DevTypeList(){
		Resources res = getResources();
		String[] devType = res.getStringArray(R.array.Device_Type_Wireless);
		Device_Type_List = Arrays.asList(devType);

	}

	//TCP handler for handling TCP responses
	private Handler TcpHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case TCP_LOST:
			{
				runOnUiThread(new Runnable() {
					public void run() 
					{
						//showing server is disconnected	
						btnconstatus.setImageResource(server_offline);
					}
				});

				break;
			}
			}
		}
	};




	/*// creating Bluetooth Handler to handle Bluetooth Socket Data
	private Handler BtHandler=new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case READ_LINE_BT:
			{
				final String Data=(String) msg.obj;
				StaticVariables.printLog("BT RESPONSE","DATA GET IN HANDLER:"+Data);

				if(Data.contains("CMD") && CMD_COUNT==1){
					//writing cmd to check current baud rate of bluetooth device
					bluetooth.btWrite(BT_CHECK_BAUDRATE);
					//set cmd count 2
					CMD_COUNT=2;
				}else if(!Data.contains("19.2") && CMD_COUNT==2){
					*//*if baud rate is not set to standard
						communication value then setting baud rate   *//*
					bluetooth.btWrite(BT_SET_BAUDRATE);
					//set cmd count 3
					CMD_COUNT=3;
				}else if(Data.contains("19.2") && CMD_COUNT==2){
					//if baud rate is already set then exit from cmd mode 
					bluetooth.btWrite(BT_EXIT_CONFIG_MODE);
					//set cmd count 4
					CMD_COUNT=4;
				}else if(Data.contains("AOK") && CMD_COUNT==3){
					//if su cmd set baud rate  successfully then exiting from cmd mode
					bluetooth.btWrite(BT_EXIT_CONFIG_MODE);
					CMD_COUNT=4;
				}else if(Data.contains("ERR") && CMD_COUNT==3){
					pdlg.setProgress(100);
					if(pdlg!=null){
						pdlg.dismiss();	// Dismissing Progress Bar
						pdlg=null;
					}
					//reset cmd count
					CMD_COUNT=0;

					//closing bluetooth connections as baud rate not set
					bluetooth.btConnectionClose();

					// Showing Configuration Dialog if Bluetooth Connected
					ErrorAlert("ERROR","Unable to Configure Configuration Tool.");  	
				}else if(Data.contains("END") && CMD_COUNT==4){
					//if baudrate set successfully then showing succcess dialog
					runOnUiThread(new Runnable() {
						public void run() {
							pdlg.setProgress(100);
							if(pdlg!=null){
								pdlg.dismiss();	// Dismissing Progress Bar
								pdlg=null;
							}
							//reset cmd count
							CMD_COUNT=0;
							// switching activity
							switchActivity(); 	
						}
					});
				}

				break;
			}

			case READ_BYTE_BT:	{
				break;
			}

			case EXCEPTION_BT:
			{
				final String Data=(String) msg.obj;
				StaticVariables.printLog("BT RESPONSE","DATA GET IN HANDLER:"+Data);
				exceptionOccured=true;
				break;
			}

			case BT_LOST:
			{
				final String Data=(String) msg.obj;
				runOnUiThread(new Runnable() {
					public void run() 
					{
						//showing bluetooth is Disconnected
						bt_status.setImageResource(BT_OFF);	
					}
				});
				break;
			}
			}
		}
	};*/

/*	//Bluetooth Connetion Task
	private class BluetoothConnect extends AsyncTask<String,Void, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			CMD_COUNT=1;
			ProgressDialog("Connecting To Configuration Tool..."); 	// Starting Progress Bar
		}

		@Override
		protected Boolean doInBackground(String... params) {
			boolean isBtConnected=false;
			try{
				bluetooth=new Bluetooth(BtHandler);	//passing bluetooth handler instance
				if(Bluetooth.btConnected){
					isBtConnected=true;
					//sending bluetooth baud rate commands
					bluetooth.btWrite(BT_CONFIG_MODE);
				}
			}catch(Exception e){
				e.printStackTrace();
			}

			return isBtConnected;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result){
				bt_status.setImageResource(BT_ON);	//showing bluetooth is connected

			}else{

				if(pdlg!=null){
					pdlg.setProgress(100);
					pdlg.dismiss();	// Dismissing Progress Bar 
					pdlg=null;
				}

				if(Bluetooth.isBluetoothOff){
					//Showing Bluetooth off Alert if Mobile Bluetooth is off
					EnableBluetoothAlert("Bluetooth OFF",
							"Your Mobile's Bluetooth is Currently OFF.\nPlease Turn it ON and Try Again.");
				}else{
					*//* Showing Bluetooth not Connected Error Dialog
					if Bluetooth not Connected*//*
					ErrorAlert("Configuration Tool Not Connected",
							"Please Ensure that Configuration Tool Mac Address is Correct & Configuration Tool is ON And Then Try Again.");	
				}
			}
		}

	}

	// Progress Dialog  Showing Bluetooth is Establishing Connection
	private void ProgressDialog(final String msg)
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
			@Override
			public void run() {
				if(pdlg==null){
					pdlg=new ProgressDialog(WirelessConfigurationDetails.this,ProgressDialog.THEME_HOLO_LIGHT);
					pdlg.setMessage(msg);
					pdlg.setIndeterminate(false);
					pdlg.setCancelable(false);
					pdlg.show();	
				}

			}
		});

	}

	// Error Alert showing that Bluetooth is Off
	private void EnableBluetoothAlert(final String title,final String msg) {
		runOnUiThread(new Runnable() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
			@Override
			public void run() {
				AlertDialog.Builder dlg = new AlertDialog.Builder(WirelessConfigurationDetails.this,AlertDialog.THEME_HOLO_DARK);
				dlg.setTitle(title);
				dlg.setMessage(msg);
				dlg.setCancelable(false);
				dlg.setIcon(android.R.drawable.ic_dialog_alert);

				dlg.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});		

				dlg.setPositiveButton("Enable BT",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						try{
							if(bluetooth!=null && !Bluetooth.btAdapter.isEnabled()){
								Bluetooth.btAdapter.enable();
								msg("Turning Bluetooth ON...");
							}
						}catch(Exception e){
							e.printStackTrace();
						}

						dialog.dismiss();
					}
				});		

				AlertDialog d = dlg.create();
				d.show();
			}
		});
	}




	//Error Alert Showing Unable to Establish Bluetooth Connection
	private void ErrorAlert(final String title,final String msg) {
		runOnUiThread(new Runnable() {
			@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
			@Override
			public void run() {
				AlertDialog.Builder dlg = new AlertDialog.Builder(WirelessConfigurationDetails.this,AlertDialog.THEME_HOLO_DARK);
				dlg.setTitle(title);
				dlg.setMessage(msg);
				dlg.setCancelable(false);
				dlg.setIcon(android.R.drawable.ic_dialog_alert);

				dlg.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,	int which) {
						dialog.dismiss();
					}
				});

				dlg.setPositiveButton("Try Again",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,	int which) {
						dialog.dismiss();
						// Again starting Bluetooth Connection Task 
						new BluetoothConnect().execute();
					}
				});		

				AlertDialog d = dlg.create();
				d.show();
			}
		});
	}	

	// Bluetooth connection successful dialog
	private void ConnectionSuccessDialog()
	{
		runOnUiThread(new Runnable() {

			@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
			@Override
			public void run() 
			{
				updateMacID(); 	// Updating bluetooth Mac Id in Database

				AlertDialog.Builder dialog=new AlertDialog.Builder(WirelessConfigurationDetails.this, AlertDialog.THEME_HOLO_LIGHT);
				dialog.setTitle("Configuration Tool Connected");
				dialog.setMessage("Configuration Tool connected SuccessFully.\nProceed to Configure Device ?");
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				dialog.setCancelable(false);
				dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				dialog.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						switchActivity();
					}
				});	

				dialog.show();
			}
		});
	}*/

	//switching activity with necessary data
	private void switchToNfcActivity(){

		// Fetching Device's Short Name from Device Name List
		String deviceName=Short_DevNames_List.get(selectedItemPosition);

		// Fetching Device's Short Name from Device Name List
		String deviceFullName=Full_DevNames_List.get(selectedItemPosition);

		// Fetching Device Type from Device Type List
		String deviceType=Device_Type_List.get(selectedItemPosition);


		StaticVariables.printLog("DEV INFO ","INFO : devtype: "+deviceType+" devname : "+deviceName
				+"\n"+" devfname : "+deviceFullName+" room name :"+RoomName);

		/*//Sending intent with Data
		Intent it=new Intent(WirelessConfigurationDetails.this,NfcTagDetection.class);
		it.putExtra("devType", deviceType);
		it.putExtra("devName", deviceName);
		it.putExtra("devFullName", deviceFullName);
		it.putExtra("WLS_RoomName",RoomName);
		startActivity(it);
		//adding transition to activity exit
		overridePendingTransition(R.anim.slideup, R.anim.slidedown);
		finish();*/
	}	

	//switching activity with necessary data
	private void switchActivity(){

		//updating mac-id
		updateMacID();

		// Fetching Device's Short Name from Device Name List
		String deviceName=Short_DevNames_List.get(selectedItemPosition);

		// Fetching Device's Short Name from Device Name List
		String deviceFullName=Full_DevNames_List.get(selectedItemPosition);

		// Fetching Device Type from Device Type List
		String deviceType=Device_Type_List.get(selectedItemPosition);

		//User_Device_Name=et_user_dev_name.getText().toString();

		StaticVariabes_div.log("deviceName"+deviceName,TAG1);

		/*//setting group id for WPC if selected
		if(!deviceName.equals(PIR_DevType)||!deviceName.equals(PIR_Direct_DevType))
		{
			StaticVariabes_div.log("inside deviceName"+deviceName,TAG1);
			//getting unique id  from house database
			selectedGroupId= DEFAULT_GROUPID;
		} 	*/


		StaticVariables.printLog("DEV INFO  INFO : devtype: "+deviceType+" devname : "+deviceName
				+"\n"+" devfname : "+deviceFullName+" room name :"+RoomName+"\n gid :"+selectedGroupId,TAG1);

		//Sending intent with Data //WirelessDeviceConfiguration.class
		Intent it=new Intent(WirelessConfigurationDetails.this,WirelessDeviceConfiguration.class);
		it.putExtra("devType", deviceType);
		it.putExtra("devName", deviceName);
		it.putExtra("devFullName", deviceFullName);
		it.putExtra("WLS_RoomName",RoomName);
		it.putExtra("groupId", selectedGroupId);
		it.putExtra("roomiconnam", Room_Icon_Image);
		it.putExtra("usrdevnam", User_Device_Name);
		startActivity(it);

		Toast.makeText(getBaseContext(),"intent data:"+selectedGroupId,Toast.LENGTH_SHORT).show();
		//adding transition to activity exit
		overridePendingTransition(R.anim.slideup, R.anim.slidedown);
		finish();
	}

	//Method to Update Bluetooth Mac Address in Database
	private void updateMacID() {
		try{
			String MacID=StaticVariables.Bluetooth_MacID;
			if(MacID!=null && MacID.length()==17){
				houseDB.updateConfigTable_MacID(MacID);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	// click events
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.submit: {
			StaticVariabes_div.log("isGroupChecked"+Tcp_con.isClientStarted,TAG1);
			if(Tcp_con.isClientStarted)
			{
				boolean isGroupChecked=false;
				String rname=null;

				try{

					DeviceType = dev_type.getSelectedItem().toString();
					//Bluetooth_ID = bt_MacId.getText().toString();
					rname=room_names_list.getSelectedItem().toString();

					isGroupChecked=groupCheck.isChecked();

					StaticVariabes_div.log("isGroupChecked"+isGroupChecked,TAG1);

				}catch(Exception e){
					e.printStackTrace();
				}

				boolean newroomselected=false;

				//checking if user has selected Add New Room option
				if(rname!=null && room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-2 ){
					RoomName=room_name.getText().toString().trim();
					newroomselected=true;
				}else{
					RoomName=rname.trim();
					newroomselected=false;
				}

				StaticVariabes_div.log("RoomName"+RoomName,"wirelessconfig");

				User_Device_Name=et_user_dev_name.getText().toString();

				String isdevnamUnique=houseDB.isDeviceName_Exists(User_Device_Name);
				StaticVariabes_div.log("isdevnamUnique"+isdevnamUnique,TAG1);

				if(isGroupChecked){
					//checking if Add New option is selected
					if(groupId.getSelectedItemPosition()==deviceGroupIdList.size()-2)
					{
						selectedGroupId=group_Id.getText().toString(); 
					}else{
						selectedGroupId=groupId.getSelectedItem().toString(); 
					}

				}

				StaticVariabes_div.log("selectedGroupId"+selectedGroupId,TAG1);
				//(Bluetooth_ID == null || Bluetooth_ID.length() == 0 || Bluetooth_ID.length() != 17)

				//cehcking all details are provided by user
				if ((RoomName == null || RoomName.length() == 0 || room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-1)
						|| selectedItemPosition==Full_DevNames_List.size()-1)
				{
					//checking if no room is selected
					if(room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-1){
						msg("Please Select a Room");
					}else if(RoomName==null || RoomName.length()==0 ){
						room_name.setError("Please Enter Room Name.");
					}else if(RoomName!=null && room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-2){
						room_name.setError(null);
					}

					/*if(User_Device_Name==null || User_Device_Name.length()==0 ){
						et_user_dev_name.setError("Please Enter Device Name.");
					}*/

					if(selectedItemPosition==Full_DevNames_List.size()-1){
						msg("Please Select a Device");
					}

				/*	//checking if nfc tag is selected as device
					if(DeviceType.equals(NFC_TAG)){ 
						//switch to nfc detection activity  
						switchToNfcActivity();

					}else{
						if (Bluetooth_ID == null || Bluetooth_ID.length() == 0) {
							bt_MacId.setError("Please Enter Configuration Tool Mac-ID");
						} else if (Bluetooth_ID.length() != 17) {
							bt_MacId.setError("Configuration Tool Mac-ID Must Be Of 17 Character");
						} else {
							bt_MacId.setError(null);
							bt_MacId.setText(Bluetooth_ID);
						}

					}*/


				}else if((newroomselected)&&((Room_Icon_Image==null) ||(Room_Icon_Image.length()==0)||Room_Icon_Image.equals("select")) ){
					msg("Please Select a Icon");
				} else if((User_Device_Name==null || User_Device_Name.length()==0) ){
					et_user_dev_name.setError("Please Enter Device Name.");
				}else if((User_Device_Name!=null) &&(User_Device_Name.length()>0) && (isdevnamUnique.equals("TRUE")) ){
					et_user_dev_name.setError("Device Name Already Exists");
				}else if(isGroupChecked && ((selectedGroupId.equals(DEFAULT_GROUPID)|| groupId.getSelectedItemPosition()==deviceGroupIdList.size()-1 || selectedGroupId.length()==0)) ){
					//if((selectedGroupId.equals(DEFAULT_GROUPID)|| groupId.getSelectedItemPosition()==deviceGroupIdList.size()-1 || selectedGroupId.length()==0)||selectedGroupId.equals("0"))
					msg("Please Provide a Valid Device Group ID");
				}
				else {


					//checking if nfc tag is selected as device
					if(DeviceType.equals(NFC_TAG)){

						//switch to nfc detection activity  
						switchToNfcActivity();

					}else{


						if(isGroupChecked){

							boolean isGroupIdInteger=false;
							//checking valid group id is given or not
							try{

								int groupId=Integer.parseInt(selectedGroupId);
								isGroupIdInteger=true;
							}catch(Exception e){
								e.printStackTrace();
							}

							if(isGroupIdInteger){

								/*checking whether given group id is not belonging 
								 *to some other device type i.e other than selected device type
								 */
								String devname=Short_DevNames_List.get(selectedItemPosition);

								boolean isGroupIdUnique=houseDB.isGroupIdUnique(devname, selectedGroupId);

								if(!isGroupIdUnique){ 
									msg("Device Group ID is Already in Use by Some Other Device");
								}else{ 

									/*if(!Bluetooth.btConnected){
										// clearing error marks from Text fields
										bt_MacId.setError(null);

										// Assigning bluetooth mac-address to static fields
										StaticVariables.Bluetooth_MacID=Bluetooth_ID;		

										// starting Bluetooth Connection Task
										new BluetoothConnect().execute();
									}else{
										//calling switch activity function
										// to switch activity with necessary data if bluetooth already connected
										switchActivity();	
									}*/

									//calling switch activity function
									// to switch activity with necessary data if Serial already connected

									if(!UsbService.serialPortConnected){
										popup("Usb not connected 3");
									}else {
										if (StaticStatus.Network_Type.equals("TRUE3G")||StaticStatus.Network_Type.equals("TRUE")) {
											popup("Device Cannot Be Configured In Remote Connection");
										}else {
											switchActivity();
										}
										//switchActivity();
									}

								}

							}else{
								msg("Group Id must be a Number");
							}



						}else{

							/*if(!Bluetooth.btConnected){
								// clearing error marks from Text fields
								bt_MacId.setError(null);

								// Assigning bluetooth mac-address to static fields
								StaticVariables.Bluetooth_MacID=Bluetooth_ID;		

								// starting Bluetooth Connection Task
								new BluetoothConnect().execute();
							}else{
								//calling switch activity function
								// to switch activity with necessary data if bluetooth already connected
								switchActivity();	
							}*/
							//calling switch activity function
							// to switch activity with necessary data if bluetooth already connected
							if(!UsbService.serialPortConnected){
								popup("Usb not connected 3");
							}else {
								if (StaticStatus.Network_Type.equals("TRUE3G")||StaticStatus.Network_Type.equals("TRUE")) {
									popup("Device Cannot Be Configured In Remote Connection");
								}else {
									switchActivity();
								}

							}

						}


					}

				}

			}else{
				msg("Please Ensure that Server is Connected!");
			}

			break;
		}
		case R.id.clear :
		{
			room_name.setText("");
			bt_MacId.setText("");

			room_name.setError(null);
			bt_MacId.setError(null);

			break;
		}

		case R.id.server_status:{
			if(!Tcp.tcpConnected){
				if(!isTcpConnecting){ 
					msg("connecting To Server...");
					//RegainingConnection();

				}else{
					StaticVariables.printLog("TAG","already Connecting tcp");
				}
			}else{
				msg("server is already connected");
			}
			break;
		}
		case R.id.imageView2:{
			goPrevious();
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


	// spinner item selected listener
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {

		switch(parent.getId()){
		case R.id.room_name_spin:{
			//checking if Add new room option is selected
			if(position==UniqueRoomList.size()-2){
				//displaying the text field
				roomname_lyaout.setVisibility(View.VISIBLE);
				room_name.setError(null);
				StaticVariables.printLog("TAG","layout  visible +"+room_names_list.getSelectedItem().toString());
			}else{
				//making text filed disappear
				roomname_lyaout.setVisibility(View.GONE);
				StaticVariables.printLog("TAG","layout invisible"+room_names_list.getSelectedItem().toString());
			}


			//refresh group id spinner
			refreshGroupIdSpinner();

			break;
		}

		case R.id.devtype:{ 

			selectedItemPosition=position;
			String deviceName=null;

			if(selectedItemPosition!=Full_DevNames_List.size()-1){
				// Fetching Device's Short Name from Device Name List
				deviceName=Short_DevNames_List.get(selectedItemPosition);

				//disabling check box if devtype is not PIR
				if(deviceName!=null && (deviceName.equals(PIR_DevType)||deviceName.equals(PIR_Direct_DevType)||deviceName.equals(mIR_Direct_DevType))){

					groupId.setVisibility(View.INVISIBLE);
					group_Id.setVisibility(View.INVISIBLE);
					groupCheck.setEnabled(true);


					//making groupcheckbox visible
					groupIdLayout.setVisibility(View.VISIBLE);


					//refresh group id spinner
					refreshGroupIdSpinner();

				}
				else{ 
					//making check box unchecked & disabled
					disableCheckBox(); 
				}

			}


			break;
		}
		case R.id.groupID:{
			if(groupId.getSelectedItemPosition()==deviceGroupIdList.size()-2){
				group_Id.setVisibility(View.VISIBLE);	
			}else{ 
				group_Id.setText("");
				group_Id.setVisibility(View.INVISIBLE);
			}
			break;
		}

		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}


	//disable checkbox
	void disableCheckBox(){

		//making groupid value clear
		group_Id.setText("");

		//making check box unchecked & disabled
		groupCheck.setChecked(false);
		groupCheck.setEnabled(false);

		//making groupcheckbox layout invisible
		groupIdLayout.setVisibility(View.GONE);

	}

	//refreshing group id spinner
	void refreshGroupIdSpinner(){
		if(groupCheck.isChecked()){
			String rname=null; 
			rname =room_names_list.getSelectedItem().toString();  

			//checking if user has selected other
			if(rname!=null && room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-2){ 
				rname=room_name.getText().toString();
			} 

			if((rname!=null && rname.trim().length()>0) && selectedItemPosition!=Full_DevNames_List.size()-1){

				groupId.setVisibility(View.VISIBLE);
				group_Id.setVisibility(View.INVISIBLE);

				//filling group id spinner
				fillGroupIdSpinner(rname);	

			} else{

				//making check box unchecked
				groupCheck.setChecked(false); 

				groupId.setVisibility(View.INVISIBLE);
				group_Id.setVisibility(View.INVISIBLE);

			}
		}
	}



	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		String rname=null; 
		rname =room_names_list.getSelectedItem().toString();  

		//checking if user has selected other
		if(rname!=null && room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-2){
			rname=room_name.getText().toString();
		} 

		if((rname!=null && rname.trim().length()>0 && room_names_list.getSelectedItemPosition()!=UniqueRoomList.size()-1) && 
				selectedItemPosition!=Full_DevNames_List.size()-1){
			if(isChecked){
				groupId.setVisibility(View.VISIBLE);
				group_Id.setVisibility(View.INVISIBLE);
				//filling group id spinner
				fillGroupIdSpinner(rname);	
			}else{
				groupId.setVisibility(View.INVISIBLE);
				group_Id.setVisibility(View.INVISIBLE); 
			}
		}else{

			//making check box unchecked
			groupCheck.setChecked(false); 

			groupId.setVisibility(View.INVISIBLE);
			group_Id.setVisibility(View.INVISIBLE);

			if(rname==null ||rname.trim().length()==0 || room_names_list.getSelectedItemPosition()==UniqueRoomList.size()-1 || 
					selectedItemPosition!=Full_DevNames_List.size()-1){
				Toast.makeText(getApplicationContext(),
						"Please Provide Room Name And Device Type.", Toast.LENGTH_SHORT).show();

			} 
		}

	}


	//method to fill group id's of selected room
	void fillGroupIdSpinner(String RoomName){
		try{
			// Fetching Device Type from Device Type List
			String deviceType=Device_Type_List.get(selectedItemPosition);

			//clearing group id list
			deviceGroupIdList.clear();

			if(deviceType.equals("718")||deviceType.equals("720")){

				deviceGroupIdList=houseDB.groupIdList_typename(RoomName, "PIR");

			}else{
				deviceGroupIdList=houseDB.groupIdList(RoomName, deviceType);

			}



			//adding last items in list for choosing other option
			deviceGroupIdList.add("Add New");
			deviceGroupIdList.add("Select");

			groupIdAdapter=new CustomSpinnerAdapter(this, spinnerLayoutId, deviceGroupIdList);
			// Setting adapter to spinner
			groupId.setAdapter(groupIdAdapter);
			StaticVariables.printLog("Tag","fill group list size :"+deviceGroupIdList.size());
			if(deviceGroupIdList.size()>2){
				//setting select as default item in spinner
				groupId.setSelection(deviceGroupIdList.size()-1); 

			}else{
				/*setting other as selected item in spinner 
				as no group id found related to selected device */
				groupId.setSelection(deviceGroupIdList.size()-2);

			}

		}catch(Exception e){
			e.printStackTrace();
		}

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
					hideSoftKeyboard(WirelessConfigurationDetails.this);

					return false;
				}

			});
		}
	}

	public class MyAdapter extends ArrayAdapter {

		public MyAdapter(Context context, int textViewResourceId,
						 Integer[] objects) {
			super(context, textViewResourceId, objects);
		}

		public View getCustomView(int position, View convertView,
								  ViewGroup parent) {

			// Inflating the layout for the custom Spinner
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.custom, parent, false);

			// Declaring and Typecasting the textview in the inflated layout
			TextView tvLanguage = (TextView) layout.findViewById(R.id.tvLanguage);

			// Setting the text using the array
			//tvLanguage.setText("Select images");
			//tvLanguage.setTextColor(Color.rgb(75, 180, 225));

			// Declaring and Typecasting the imageView in the inflated layout
			ImageView img = (ImageView) layout.findViewById(R.id.imgLanguage);

			// Setting an image using the id's in the array
			img.setImageResource(images[position]);

			// Setting Special atrributes for 1st element
			if (position == 0) {
				tvLanguage.setText("Select");
				// Removing the image view
				img.setVisibility(View.GONE);
				// Setting the size of the text
				tvLanguage.setTextSize(20f);
				// Setting the text Color
				tvLanguage.setTextColor(Color.BLACK);

			}
			else{
				tvLanguage.setVisibility(View.GONE);


			}

			return layout;
		}

		// It gets a View that displays in the drop down popup the data at the specified position
		@Override
		public View getDropDownView(int position, View convertView,
									ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		// It gets a View that displays the data at the specified position
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
	}





	@Override
	public void read(final int type, final String stringData, final byte[] byteData)
	{
		runOnUiThread(new Runnable() {
			@Override
			public void run() {


				//tvtest.setText(stringData);

				receiveddata(type,stringData,byteData);


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
				//tvtest.setText(readMessage);
				//DataIn(readBuf);

				break;
			case READ_LINE:
				//  readMessage2 = (String) msg.obj;
				StaticVariabes_div.log("msg read A_s" + data, TAG1);
				readMessage2 =data;
				if(readMessage2.equals("*OK#")){
					serv_status(true);
					//ButtonOut("920");

				}
				//tvtest.setText(readMessage2);
				break;
			case ServStatus:
				//final String ServerStatusB = (String) msg.obj;
				final String ServerStatusB =data;
				StaticVariabes_div.log("serv status swb" + ServerStatusB, TAG1);
				if(ServerStatusB!=null){
					if (ServerStatusB.equals("TRUE")) {
						StaticStatus.Server_status_bool=true;
						statusserv = true;
						servpreviousstate="TRUE";
						nonetwork=false;
						// Cc.dataswb = true;
					//**********	ButtonOut("920");
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
				final String signallevelB = data;
				//  StaticVariabes_div.log("servsignallevel swb" + signallevelB, TAG1);
				if(signallevelB!=null){
					sl = Integer.parseInt(signallevelB);
					rs=signallevelB;



					if((StaticStatus.Network_Type.equals("TRUE")||(StaticStatus.Network_Type.equals("TRUE3G")))){

						network_signal(sl,true);

						if(StaticStatus.Network_Type.equals("TRUE3G")||StaticStatus.Network_Type.equals("NONET")){
							if(timer!=null){
								timer.cancel();
								timer=null;
							}
						}

					}else{

						network_signal(sl,false);
					}

				}
				break;
			case NetwrkType:
				//final String RemoteB = (String) msg.obj;
				final String RemoteB = data;
				StaticStatus.Network_Type=RemoteB;
				StaticVariabes_div.log("serv Remote swb" + RemoteB, TAG1);
				if (RemoteB.equals("TRUE")) {
					nonetwork=false;
					remoteconn = true;
					remoteconn3g = false;
					remoteconprevstate="TRUE";

					network_signal(sl,remoteconn);

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

					network_signal(sl,remoteconn);

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

					network_signal(sl,remoteconn);

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
					network_signal(sl,remoteconn);

				}

				break;

			case MAXUSER:
				final String maxuser = data;
				StaticVariabes_div.log("maxuser swb" + maxuser, TAG1);

				if (maxuser.equals("TRUE")) {
					//popup("User Exceeded");
				    serv_status(false);
				} else {

				}
				//    }
				// });
				break;
			case  ERRUSER:
				final String erruser = data;
				StaticVariabes_div.log("erruser swb" + erruser, TAG1);

				if (erruser.equals("TRUE")) {
					//popup("INVALID USER/PASSWORD");
					serv_status(false);
				} else {

				}

				break;
		}
	}


	public void network_signal(int signal1, final boolean serv) {
		if (serv) {
			//  btnwtype.setText("Remote");


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

	//go back
	void goPrevious(){

		//going back to admin page
		Intent it=new Intent(WirelessConfigurationDetails.this,Configuration_Main.class);
		startActivity(it);
		//adding transition to activity exit
		overridePendingTransition(R.anim.slideup, R.anim.slidedown);
		finish();
	}

	//Back Press Event
	@Override
	public void onBackPressed() {
		goPrevious();
	}	


	@Override
	protected void onPause() { 
		super.onPause();
		unregisterReceiver(mUsbReceiver);
		unbindService(usbConnection);
		if(WirelessConfigurationAdapter.sdb!=null){
			if(HouseConfigurationAdapter.sdb.isOpen() && 
					WirelessConfigurationAdapter.sdb.isOpen()){
				houseDB.close();
				WhouseDB.close();

				StaticVariables.printLog("TAG","DB CLOSED ");
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		setFilters();  // Start listening notifications from UsbService
		startService(UsbService.class, usbConnection, null); // Start UsbService(if it was not started before) and Bind it
		if(WirelessConfigurationAdapter.sdb!=null){
			if(!HouseConfigurationAdapter.sdb.isOpen() && 
					!WirelessConfigurationAdapter.sdb.isOpen()){
				houseDB.open();
				WhouseDB.open();

				StaticVariables.printLog("TAG","DB open ");
			}
		}
	}

	public class MyHandler extends Handler {
		private final WeakReference<WirelessConfigurationDetails> mActivity;

		public MyHandler(WirelessConfigurationDetails activity) {
			mActivity = new WeakReference<>(activity);
		}

		@Override
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{

				case UsbService.MESSAGE_FROM_SERIAL_PORT:
				{
					final String Data=(String) msg.obj;
					StaticVariables.printLog("BT RESPONSE","DATA GET IN HANDLER:"+Data);
					runOnUiThread(new Runnable() {
						public void run()
						{
							Toast.makeText(WirelessConfigurationDetails.this,"Usb RESPONSE"+Data,Toast.LENGTH_SHORT).show();
						}
					});

					break;
				}

			}
		}
	};



	private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
		if (!UsbService.SERVICE_CONNECTED) {
			Intent startService = new Intent(this, service);
			if (extras != null && !extras.isEmpty()) {
				Set<String> keys = extras.keySet();
				for (String key : keys) {
					String extra = extras.getString(key);
					startService.putExtra(key, extra);
				}
			}
			startService(startService);
		}
		Intent bindingIntent = new Intent(this, service);
		bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	private void setFilters() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
		filter.addAction(UsbService.ACTION_NO_USB);
		filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
		filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
		filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
		registerReceiver(mUsbReceiver, filter);
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
						/*Intent it=new Intent(AddNewDevice.this,Configuration_Main.class);
						startActivity(it);
						//adding transition to activity exit
						overridePendingTransition(R.anim.slideup, R.anim.slidedown);
						finish();*/
						//  onShift();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	public void popupdev(String msg){

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		alertDialogBuilder.setTitle("INFO");
		alertDialogBuilder
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
						Intent it=new Intent(WirelessConfigurationDetails.this,Configuration_Main.class);
						startActivity(it);
						//adding transition to activity exit
						//overridePendingTransition(R.anim.slideup, R.anim.slidedown);
						finish();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}