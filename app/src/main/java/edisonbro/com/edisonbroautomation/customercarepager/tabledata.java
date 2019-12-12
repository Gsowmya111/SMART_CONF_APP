package edisonbro.com.edisonbroautomation.customercarepager;

/**
 * Created by root on 12/1/18.
 */

public class tabledata {

    public static String[] abbreviations = { "TOUCH", "IR", "BUZZER",
            "MEMORY", "HARDWARE","MANUAL OVERRIDE", "CONFIG MODE","VERSION" ,"1","SET" ,"RESET IR","FAN SPEED"
       };
    public static String[] countries = { "When TOUCH is turned ON/OFF it allows the user to ENABLE/DISABLE all the direct touch actions on the Switchboard thus acting as a child lock when switch is turned OFF.",
            "When IR is turned ON/OFF it will ENABLE/DISABLE the working of IR remote on the Switchboard.",
            "Allows the user to control the Buzzer / Beep sound either ON/OFF when switches are operated.",
            "When MEMORY is turned ON it allows the user to store the switch status onto the on-board memory. When MEMORY is turned OFF switchboard doesn't store the status of the switch and upon Power Failure and resumption, all the switches will be in OFF state.",
            "When HARDWARE is turned ON/OFF it allows the user to ENABLE/DISABLE the complete hardware not only from the mobile App but also from IR Remote & Touch.",
            "By turning ON MANUAL OVERRIDE, user can force the device to be turned ON and not to respond to PIR initiated commands.",
            "When CONFIG MODE is TURN ON it allows the user to enter into the configuration mode. (For further details refer Configuration & Administration Manual)",
            "Displays Firmware version number of device",
            "It indicates the serial number of switchboard in the room / living space. It allows the user to set particular serial number on to that switchboard.",
            "Allows the user to save the settings performed.",
            "Allows the user to reset the combination key's learnt from remote.",
            "Allows the user to customize fan speed .",
             };

    public static String[] Swd_design_name = { "EDISONBRO SMART SWITCH – 1 Switch", "EDISONBRO SMART SWITCH - 2 Switch", "EDISONBRO SMART SWITCH - 2+1 Switch",
            "EDISONBRO SMART SWITCH – 3 Switch", "EDISONBRO SMART SWITCH – 5+1 Switch", "EDISONBRO SMART SWITCH – 8 Switch", "EDISONBRO SMART SWITCH -   CALLING BELL",""
    };
    public static String[] Swd_design_combinations = { "1 Switch – 2 Plate (92mm x 92mm)",
            "2 Switch - 3 Plate (129.5mm x 92mm)",
            "2+1 Switch - 4 Plate (157.5mm x 92mm)",
            "3 Switch - 4 Plate (157.5mm x 92mm)",
            "5+1 Switch - 8 Plate (252mm x 92mm)",
            "8 Switch - 8 Plate (252mm x 92mm) ",
            "Calling Bell ",
            ""
    };

    public static String[] swb_op_controls = { "Lights Control", "Fan Control", "A/C Control",
            "Geyser Control", "All ON Control", "All OFF Control",""
    };
    public static String[] swb_op_operation = { "Lights can be Switched ON or OFF with a single feather touch. Once turned ON the embedded LED will glow at higher intensity",
            "The Fan can be Switched ON or OFF. Additionally, user can increase / decrease the speed up to 4 Levels with gentle touch",
            "User can control the A/C switch to TURN ON or TURN OFF with a single touch on the same switch board where your Lights & Fan are located",
            "User can also control the Geyser to be TURNED ON or OFF on the same capacitive touch switch board where your Lights & Fan are located",
            "Allows the user to turn ON ALL the connected appliances to the switchboard with a single Touch",
            "With a single touch of ALL OFF, user can turn off all the connected switches which are currently turned ON.",
            ""

    };

    public static String[] irremote_op_controls = { "ALL OFF", "ALL ON", "1",
            "2", "3", "4","5","6","7","8","9","0","+1","FAN1","FAN2","+","-","Lock","Unlock","MEMORY ON","MEMORY OFF","LEARNING KEY","Buzzer","LED(ON/OFF)","S1","S2","S3","S4","S5","S6",""
    };
    public static String[] irremote_op_operation = {
            "Allows the user to TURN OFF all the switches present on the Switchboard with a press of this button. ",
            "Allows the user to TURN ON all the switches present on the Switchboard with a press of this button.",
            "Allows the user to control the first switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the second switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the third switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the fourth switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the fifth switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the sixth switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the seventh switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the eighth switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the ninth switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control any additional switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control any additional switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the first FAN switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to control the second FAN switch of the switchboard to TURN ON or TURN OFF with a press of this button.",
            "Allows the user to Increase the speed of the selected FAN and go high upto 4 levels by pressing this button.",
            "Allows the user to Decrease the speed of the selected FAN and go high upto 4 levels by pressing this button.",
            "Allows the user to LOCK all the operations of the switchboard with a press of this button.",
            "Allows the user to UNLOCK all the operations of the switchboard with a press of this button.",
            "Allows the user to switch Memory ON for the entire switchboard. When turned ON, the status of each individual switch is stored onboard. This will help to automatically restore the switches to their state (ON / OFF) when there is a powercut and resumption of power supply.",

            "Allows the user to switch Memory OFF for the entire switchboard. When TURNED OFF, status of each individual switch is not stored and upon power cut and restoration, all switches will be in the OFF state.",
            "Allows the user to learn from another IR remote.",
            "Allows the user to switch ON/OFF the buzzer with a press of this button (The buzzer is the beep sound coming from the switchboard after pressing any button).",
            "Allows the user to switch ON/OFF the LED light glowing on the switchboard with a press of this button.",
            "Allows the user to select first switchboard of the Room / Living Space with a press of this button.",
            "Allows the user to select second switchboard of the Room / Living Space with a press of this button.",
            "Allows the user to select third switchboard of the Room / Living Space with a press of this button.",
            "Allows the user to select fourth switchboard of the Room / Living Space with a press of this button.",
            "Allows the user to select fifth switchboard of the Room / Living Space with a press of this button.",
            "Allows the user to select sixth switchboard of the Room / Living Space with a press of this button.",
            ""

    };


    public static String[] app_op_operation = {
            "Allows the user to TURN OFF all the switches present on the Switchboard with a press of this button",
            "Allows the user to TURN ON all the switches present on the Switchboard with a press of this button",
            "Allows the user to control the first switch of the switchboard to TURN ON or TURN OFF with a press of this button",
            "Allows the user to control the second switch of the switchboard to TURN ON or TURN OFF with a press of this button",
            ""


    };

    public static String[] perform_operation_img = {

            "1", "2", "3","4", "5", "6","7","8","9","10","11","12","13",""
    };
    public static String[] perform_operation = {
            "Allows the user to TURN ON all the Switches of the selected switch board.",
            "Allows the user to TURN OFF all the Switches of the selected switch board.",
            "Allows the user to identify the status of bulb is ON, with a touch on the icon user can TURN OFF the bulb.",
            "Allows the user to identify the status of bulb is OFF, with a touch on the icon user can TURN ON the bulb.",
            "Allows the user to identify the status of FAN is ON, with a touch on the icon user can TURN OFF the Fan.",
            "Allows the user to identify the status of FAN is OFF, with a touch on the icon user can TURN ON the Fan.",
            "Allows the user to identify the status of HVAC Appliance is ON, with a touch on the icon user can TURN OFF the HVAC Appliance.",
            "Allows the user to identify the status of HVAC Appliance is OFF, with a touch on the icon user can TURN ON the HVAC Appliance.",
            "It is a view pager. Allows the user to identify that there are multiple switchboards and swiping can help to navigate.",
            "Allows the user to regulate the speed of the FAN switch of the switchboard to go low, up to 4 levels, pressing this icon.",
            "Allows the user to regulate the speed of the FAN switch of the switchboard to go high, up to 4 levels, pressing this icon.",
            "Allows the user to access the different settings on the Switchboard with the touch of this button. Settings button is useful to configure switch as per user’s needs.",
            "Allows the user to set the Timer for any switch in the Switchboard.",
            ""

    };


    public static String[] set_timerone = { "SELECTED DAYS", "CYCLIC", "SELECTED DAYS",
            "CYCLIC", "SELECTED DATE", "REPEAT PATTERN","SET TIMER",""
    };
    public static String[] set_timertwo = { "Allows the user to set the operation for the selected switches on the Switchboard.",
            "Shows the list of switches on which Timer operation is set.",
            "Allows the user to set the timer on selected days for selected switches.",
            "Allows the user to set the timer on the selected switches in cyclical manner.",
            "Allows the user to set the timer on selected switches to operate on the particular date",
            "Allows the user to repeat the operations indefinitely for the chosen \"Repeat on Days\" or \"Cyclic\" options in the Timer settings.",
            "When SET TIMER is TURNED ON it saves the timer settings on selected switches and operates accordingly.",
            ""
   };

    public static String[] set_timerthree = { "Edit", "Delete", "Set Room",
            "Delete all",""
    };
    public static String[] set_timerfour = { "Allows the user to edit the existing timer list.",
            "Allows the user to delete the particular timer list which has been set already.",
            "Allows the user to select the particular room and display the list of Timer configuration for all the devices in that room.",
            "Allows the user to delete all the data from the Timer list for the selected room.",
            ""
            };


  public static String[] mood_setone = { "Mood", "bulb","fan","speed","cancel",
            "save",""
    };
    public static String[] mood_settwo = { "Displays the list of devices selected / configured in a particular mood.",
            "Allows the user to set the bulb ON / OFF  for the selected MOOD.",
            "Allows the user to set the fan ON / OFF for the selected MOOD.",
            "Allows the user to set the fan speed in the above chosen MOOD.",
            "Allows the user to go back to the previous screen without saving any changes.",
            "Allows the user to set the Mood for the switchboard for the selected room.",
            ""
            };

public static String[] mood_setthree = { "Edit", "Delete","Select room",
            "Delete all",""
    };
    public static String[] mood_setfour = {
            "Allows the user to edit the existing mood list. After editing the old mood list will be replaced with the new one.",
            "Allows the user to delete the particular mood list which has been set already. Respective mood data will be removed from the table on press of this button.",
            "Allows the user to select the particular room’s data to be displayed on the mood list.",
            "Allows the user to delete all the data from the mood list.",
            ""
            };
    public static String[] PIR_user_guid_control = { "1", "2","3",
            "4","5","6","7","8","9","10","11","12","13","14","15","16",""};
    public static String[] PIR_user_guid = {"Allows the user to TURN ON Motion/Intensity of the PIR.",
            "Allows the user to TURN OFF Motion / Intensity of the PIR.",
            "This button indicates that priority was set to that respective operation.",
            "This button indicates that priority was not set to that respective operation.",

            "This indicates that direct PIR is OFF.",
            "This indicates that direct PIR is ON.",

            "This indicates that regular PIR is OFF.",
            "This indicates that regular PIR is ON.",

            "This indicates that light intensity is OFF.",
            "This indicates that light intensity is ON.",

            "Page Viewer: - It help to access all PIR in the room by swiping\n left or right.",

            "Allows the user to set the time span for PIR, to send OFF command to the device when no motion is detected in its range in that time period.",

            "Allows the user to configure PIR as per user's need with the touch of this button.",

            "Allows the user to set the Timer for any PIR on the selected room.",
            "Allows the user to select particular Group PIR on press of this button.",
            "Allows the user to select Individual/single PIR on press of this button.",
            ""
    };

  public static String[] PIR_chg_set_control = { "HARDWARE","CONFIG MODE","VERSION","SET",""};
    public static String[] PIR_chg_set_desc = {

            "When HARDWARE is turned ON/OFF it allows the user to ENABLE/DISABLE the complete hardware not only from the mobile App but also from IR Remote & Touch.",
            "When CONFIG MODE is turned ON it allows the user to enter into the configuration mode. (For further details refer Configuration & Administration Manual)",
            "Displays Firmware version number of device",
            "Allows the user to push settings made across the direct pir to gateway",
            ""
    };
public static String[] PIR_timer_control = { "1", "2","SELECTED DAYS","CYCLIC","SELECTED DATE","REPEAT PATTERN","SET TIMER",""};
    public static String[] PIR_timer_desc = {

            "Allows the user to set the operation for the selected PIR in the selected room.",
            "Shows the list of PIR on which Timer operation is set.",
            "Allows the user to set the timer on selected days for selected PIR.",
            "Allows the user to set the timer on the selected PIR in cyclical manner.",
            "Allows the user to set the timer on selected PIR to operate on the particular date.",
            "Allows the user to repeat operations indefinitely for the chosen \"Repeat on Days\" or \"Cyclic\" options in the Timer settings.",
            "When SET TIMER is TURNED ON it saves the timer settings on selected PIR and operates accordingly.",
            ""
    };



    ///////////////////////////////////////////////////////////////////////////////////


    public static String[] RGB_user_guid_control = { "1", "2","3",
            "4","5","6","7","8","9","10","11","12","13","14","15",""};
    public static String[] RGB_user_guid = {

            "Allows the user to TURN ON all the RGBs with the press of this button",
            "Allows the user to TURN ON/OFF the RGB with the press of this button",

            "The user can SELECT the desired colour tone with a single gentle Touch to any one of the colour.",
            "The user can create their own colour tone by using this colour picker.",
            "The user can control the brightness of the selected colour by using this seekbar.",
            "This function makes the fixture to scroll through the colours automatically.",
            "Allows the user to activates the strobe effect with the press of this button.",
            "This function makes the fixture fade in and fade out of the different colours automatically. ",
            "Allows the user to activates the smooth effect with the press of this button.",
            "This  button  will  increase  the  speed  of  the  selected  flash,  strobe, smooth or fade.",
            "Allows  the  user  to  identify  the  selected  speed  of  the  flash,  strobe, smooth or fade.",
            "This  button  will  decrease  the  speed  of  the  selected  flash,  strobe, smooth or fade.",
            "Allows the user to identify the selected colour, flash, strobe, fade or smooth. ",
            "This will contain all the RGB groups and allow the user to select the particular group.",
            "Allows  the  user  to  select  individual  RGB  with  the  press  of  this button.",


            ""
    };

    ///////////////////////////////////////////////////////////////////////////////////

    public static String[] RGB_group = { "1", "2","3",""};
    public static String[] RGB_group_desc = {
            "Allows the user to TURN ON all the RGBs with the press of this button.",
            "Allows the user to TURN OFF all the RGBs with the press of this button.",
            "The user can SELECT the desired colour tone with a single gentle Touch to any one of the colour.",
            ""
    };


    public static String[] RGB_timer_control = { "1", "2","FROM","TO","SELECTED DAYS","CYCLIC","SELECTED DATE","REPEAT PATTERN","SET TIMER",""};
    public static String[] RGB_timer_desc = {

            "Allows the user to set the operation for the selected RGB in the selected room.",
            "Shows the list of RGB on which Timer operation is set.",
            "Allows the user to set time to at what time RGB should get ON.",
            "Allows the user to set time to at what time RGB should get OFF.",
            "Allows the user to set the timer on selected days for selected RGB.",
            "Allows the user to set the timer on the selected RGB in cyclical manner.",
            "Allows the user to set the timer on selected RGB to operate on the particular date.",
            "Allows the user to repeat operations indefinitely for the chosen \"Repeat on Days\" or \"Cyclic\" options in the Timer settings.",
            "When SET TIMER is TURNED ON it saves the timer settings on selected RGB and operates accordingly.",
            ""
    };

    /////////////////////////////////////////////////////////////////////////////////////////

    public static String[] RGB_chg_set_control = { "BUZZER","MEMORY","HARDWARE", "MANUAL OVERRIDE","CONFIG MODE","VERSION",""};
    public static String[] RGB_chg_set_desc = {

            "Allows  the  user  to  control  the  Buzzer  /  Beep  sound  either  ON/OFF when switches are operated.",
            "Stores the previous state data when power goes off and on.",
            "Allows the user to ENABLE and DISABLE the hardware device. If it is off then entire device will not work.",
            "By turning ON MANUAL OVERRIDE, user can force the device to be turned ON and not to respond to PIR initiated commands.",

            "When CONFIG MODE is TURN ON it allows the user to enter into the configuration mode. (For further details refer Configuration & Administration Manual)",


            "Displays Firmware version number of device",

            ""
    };

    ////////////////////////////////////////////////////////////////////////////////////

    public static String[] RGB_mood_set = { "1", "2","3",
            "4","5","6","7","8","9","10","11","12","13","14","15",""};
    public static String[] RGB_mood_set2 = {
            "Displays the list of devices selected/configured in a particular mood.",
            "Allows the user to TURN ON/OFF the RGB with the press of this button",

            "The user can SELECT the desired colour tone with a single gentle Touch to any one of the colour.",
            "The user can create their own colour tone by using this colour picker.",
            "The user can control the brightness of the selected colour by using this seekbar.",
            "This function makes the fixture to scroll through the colours automatically.",
            "Allows the user to activates the strobe effect with the press of this button.",
            "This function makes the fixture fade in and fade out of the different colours automatically. ",
            "Allows the user to activates the smooth effect with the press of this button.",
            "This  button  will  increase  the  speed  of  the  selected  flash,  strobe, smooth or fade.",
            "Allows  the  user  to  identify  the  selected  speed  of  the  flash,  strobe, smooth or fade.",
            "This  button  will  decrease  the  speed  of  the  selected  flash,  strobe, smooth or fade.",
            "Allows the user to identify the selected colour, flash, strobe, fade or smooth. ",

            "Allows the user to go back to the previous screen without saving any changes.",
            "Allows the user to set the Mood for the RGB for the selected room.",
            ""
    };


    //////////////////////////////////////////////////////////////////////////////////

 public static String[] RGB_mood_set_list = { "1", "2","3",
            "4",""};
    public static String[] RGB_mood_set2_list = {
            "Allows the user to edit the existing mood list. After editing the old mood list will be replaced with the new one.",
            "Allows the user to delete the particular mood list which has been set already. Respective mood data will be removed from the table on press of this button",

            "Allows the user to select the particular room’s data to be displayed on the mood list. ",
            "Allows the user to delete all the data from the mood list .",
             ""
    };

 //////////////////////////////////////////////////////////////////////////////////
  //  Dimmer
 public static String[] Dimmer_perform_op = { "1", "2","3",
            "4","5",""};
    public static String[] Dimmer_perform_op_exp = {
            "Allows the user to CONTROL intensity of light [Low, medium, High] or desired level.\n" +
                    "• If Dimmers are grouped, the above operation happens on all the Dimmers configured in the group.\n" +
                    "• If it is individual, above operation happens only on that selected Dimmer.",

            "This will show the list of dimmer groups and allow the user to select the particular group.",

            "Allows the user to select individual dimmer with the press of this button.",

            "Allows the user to set data to control dimmer operations [ Buzzer,Touch, etc.] with the press of this button.",

            "Allows the user to set the TIMER on selected dimmer with the press of this button.",

             ""
    };

//////////////////////////////////////////////////////////////////////////////////

 public static String[] Dimmer_timer= { "SELECTED DAYS", "CYCLIC","SELECTED DAYS",
            "CYCLIC","SELECTED DATE","REPEAT PATTERN","SET TIMER",""};
    public static String[] Dimmer_timer_exp = {
            "Allows the user to set the operation for the selected dimmer [Low, medium, High] or desired level in the selected room.",


            "Shows the list of dimmers on which Timer operation is set.",

            "Allows the user to set the timer on selected days for selected dimmer.",

            "Allows the user to set the timer on the selected dimmer in cyclical manner.",

            "Allows the user to set timer on selected dimmer to operate on a particular date.",

            "Allows the user to repeat operations indefinitely for chosen operation, which are - \"Repeat on Days\" or \"Repeat on Date\" options in the Timer settings.",

            "On press of SET TIMER, it saves the timer settings on selected dimmer.",

             ""
    };


//////////////////////////////////////////////////////////////////////////////////

 public static String[] Dimmer_timer_list = { "1", "2","3",
            "4",""};
    public static String[] Dimmer_timer_list_exp = {
            "Allows the user to edit the existing timer list.",


            "Allows the user to delete the particular timer list which has been set already.",

            "Allows the user to select the particular room and display the list of Timer configuration for all the devices in that room.",

            "Allows the user to delete all the data from the Timer list for the selected room.",



             ""
    };


//////////////////////////////////////////////////////////////////////////////////

 public static String[] Dimmer_change_setting = { "BUZZER","MEMORY", "HARDWARE",
            "MANUAL OVERRIDE","CONFIG MODE","VERSION","SET",""};
    public static String[] Dimmer_change_setting_exp = {
            "Allows the user to control the Buzzer / Beep sound either ON/OFF when dimmers are operated.",
            "Stores the previous state data when power goes off and on.",
            "When HARDWARE is turned ON/OFF it allows the user to ENABLE/DISABLE the complete hardware (curtains) not only from the mobile App but also from IR Remote & Touch.",

            "By turning ON MANUAL OVERRIDE, user can force the device to be turned ON and not to respond to PIR initiated commands.",

            "When CONFIG MODE is TURN ON it allows the user to enter into the configuration mode. (For further details refer Configuration & Administration Manual)",

            "Displays Firmware version number of device",

            "Allows the user to set min and max brightness values between which dimmers are operated.",

             ""
    };

//////////////////////////////////////////////////////////////////////////////////

    public static String[] Dimmer_mood = { "1", "2","3",
            "4",""};
    public static String[] Dimmer_mood_exp = {
            "Displays the list of devices selected / configured in a particular mood.",


            "Allows the user to set the dimmer intensity of light [Low, medium, High] or desired level.",

            "Allows the user to go back to the previous screen without saving any changes.",

            "Allows the user to set the Mood for the dimmer for the selected room.",



            ""
    };

//////////////////////////////////////////////////////////////////////////////////

    public static String[] Dimmer_mood_list = { "1", "2","3",
            "4",""};
    public static String[] Dimmer_mood_list_exp = {
            "Allows the user to edit the existing mood list. After editing, the old mood list will be replaced with the new one.",


            "Allows the user to delete the particular mood from the list. Respective mood data will be removed from the table on press of this button.",

            "Allows the user to select the particular room where the mood settings can be listed and changed if required.",

            "Allows the user to delete all the data from the mood list.",




            ""
    };

//////////////////////////////////////////////////////////////////////
    //curtain

    public static String[] Curtain_perform_op = { "1", "2","3",
            "4","5","6","7",""};
    public static String[] Curtain_perform_op_exp = {
            "Allows the user to CONTROL [open, stop, close] both sheer and normal curtain at the same time.\n" +
                    "• If curtains are grouped, the above operation happens on all the curtains configured in the group.\n" +
                    "• If it is individual, above operation happens only on that selected curtain.",


            "Allows the user to CONTROL [open, stop, close] the sheer of the curtain.\n" +
                    "• If curtains are grouped, the above operation happens on all the sheer of the curtains configured in the group.\n" +
                    "• If it is individual, above operation happens only on sheer of the selected curtain.",

            "Allows the user to CONTROL [open, stop, close] the normal curtain only.\n" +
                    "• If curtains are grouped, the above operation happens on all the normal curtains configured in the group.\n" +
                    "• If it is individual, above operation happens only on normal curtain of the selected curtain.",

            "This will show the list of curtain groups and allow the user to select the particular group.",

            "Allows the user to select individual curtain with the press of this button.",

            "Allows the user to set data to control curtain operations [ Buzzer,Touch, etc.] with the press of this button.",

            "Allows the user to set the TIMER on selected curtain (sheer, normal curtain or both) with the press of this button.",

            ""
    };

//////////////////////////////////////////////////////////////////////////////////

    public static String[] Curtain_timer= { "SELECTED DAYS", "CYCLIC","SELECTED DAYS",
            "CYCLIC","SELECTED DATE","REPEAT PATTERN","SET TIMER",""};
    public static String[] Curtain_timer_exp = {
            "Allows the user to set the operation for the selected curtain (sheer,normal curtain or both) in the selected room.",


            "Shows the list of curtains on which Timer operation is set.",

            "Allows the user to set the timer on selected days for selected curtain (sheer, normal curtain or both).",

            "Allows the user to set the timer on the selected curtain (sheer, normal curtain or both) in cyclical manner.",

            "Allows the user to set timer on selected curtain (sheer, normal curtain or both) to operate on a particular date.",

            "Allows the user to repeat operations indefinitely for chosen operation, which are - \"Repeat on Days\" or \"Cyclic\" options in the Timer settings.",

            "On press of SET TIMER, it saves the timer settings on selected curtain.",

            ""
    };


//////////////////////////////////////////////////////////////////////////////////

    public static String[] Curtain_timer_list = { "1", "2","3",
            "4",""};
    public static String[] Curtain_timer_list_exp = {
            "Allows the user to edit the existing timer list.",


            "Allows the user to delete the particular timer list which has been set already.",

            "Allows the user to select the particular room and display the list of Timer configuration for all the devices in that room.",

            "Allows the user to delete all the data from the Timer list for the selected room.",



            ""
    };


//////////////////////////////////////////////////////////////////////////////////

    public static String[] Curtain_change_setting = { "BUZZER", "HARDWARE",
            "MANUAL OVERRIDE","CONFIG MODE","INVERSE","VERSION",""};
    public static String[] Curtain_change_setting_exp = {
            "Allows the user to control the Buzzer / Beep sound either ON/OFF when curtains are operated.",

            "When HARDWARE is turned ON/OFF it allows the user to ENABLE/DISABLE the complete hardware (curtains) not only from the mobile App but also from IR Remote & Touch.",

            "By turning ON MANUAL OVERRIDE, user can force the device to be turned ON and not to respond to PIR initiated commands.",

            "When CONFIG MODE is TURN ON it allows the user to enter into the configuration mode. (For further details refer Configuration & Administration Manual)",

            "Allows user to inverse the close and open operation of curtain",

            "Displays Firmware version number of device",

            ""
    };

//////////////////////////////////////////////////////////////////////////////////

    public static String[] Curtain_mood = { "1", "2","3",
            "4","5",""};
    public static String[] Curtain_mood_exp = {
            "Displays the list of devices selected / configured in a particular mood.",


            "Allows the user to set the curtain to OPEN for the selected MOOD.",

            "Allows the user to set the curtain to CLOSE for the selected MOOD.",

            "Allows the user to go back to the previous screen without saving any changes.",

            "Allows the user to set the Mood for the curtain for the selected room.",



            ""
    };

//////////////////////////////////////////////////////////////////////////////////

    public static String[] Curtain_mood_list = { "1", "2","3",
            "4",""};
    public static String[] Curtain_mood_list_exp = {
            "Allows the user to edit the existing mood list. After editing, the old mood list will be replaced with the new one.",


            "Allows the user to delete the particular mood from the list. Respective mood data will be removed from the table on press of this button.",

            "Allows the user to select the particular room where the mood settings can be listed and changed if required.",

            "Allows the user to delete all the data from the mood list.",




            ""
    };
}
