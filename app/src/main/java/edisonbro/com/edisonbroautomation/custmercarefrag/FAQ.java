package edisonbro.com.edisonbroautomation.custmercarefrag;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.customercarepager.Faq_activity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQ extends Fragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    CustomList adapter;
    Integer[] icon;
    String[] name;
    private int lastExpandedPosition = -1;
    public SimpleAdapter simpleAdapter;
    public SearchView searchView;
    public EditText searchView2;

    public TextView cleartext;



   // private ArrayList<Question> questionList = new ArrayList<Question>();
    private ArrayList<Question> QuestionList = new ArrayList<Question>();

    //ExpandableListAdapter listAdapter;
    private ExpendedListAdapter_Faq listAdapter;

   // private MyListAdapter listAdapter;


    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public FAQ() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.cc_faq_layout, container, false);
       // searchView = (SearchView) view.findViewById(R.id.search);
        searchView2 = (EditText) view.findViewById(R.id.searchedit);
        final TextView tv=(TextView) view.findViewById(R.id.searchtexthint);
        cleartext=(TextView) view.findViewById(R.id.cleartext);
        final Button btn=(Button)view.findViewById(R.id.faqbtn);

        searchView2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listAdapter.filterData(searchView2.getText().toString());
                expandAll();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                listAdapter.filterData(searchView2.getText().toString());
                expandAll();
               // return false;

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listAdapter.filterData(searchView2.getText().toString());
                expandAll();
            }
        });

        searchView2.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {


                    if(b)
                    {
                        cleartext.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        cleartext.setVisibility(View.GONE);
                    }
            }
        });
        cleartext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView2.setText("");
                searchView2.clearFocus();
               // searchView2.setFocusable(false);
            }
        });



        expListView = (ExpandableListView) view.findViewById(R.id.listfaq);

        // searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.hintSearchMess) + "</font>"));



       /* int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = searchView.findViewById(searchPlateId);
        if (searchPlateView != null) {
            searchPlateView.setBackgroundColor(Color.BLACK);
        }
*/

        /*final boolean[] activityStartup = {true};
        searchView.setIconifiedByDefault(false);
        final int[] a = {0};
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    btn.setVisibility(View.VISIBLE);
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    if (activityStartup[0]) {


                        activityStartup[0] = false;
                        searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                        searchView.clearFocus();
                    }
                    else {
                        searchView.setQueryHint("");
                        tv.setText(R.string.hintSearchMess);

                    }
                    if(a[0]==0 || a[0]==1)
                    {
                        if(a[0]==0) {
                            searchView.clearFocus();
                            a[0]=1;
                        }
                        else
                        {
                            searchView.clearFocus();
                            a[0]=2;
                        }

                    }



                }
                else {
                    btn.setVisibility(View.GONE);
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    searchView.setQueryHint("Serch by Keyword ex: product");
                    tv.setText("");
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.clearFocus();
                tv.setText("");
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Toast t = Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT);
                // t.show();
                searchView.clearFocus();
                return false;
            }
        });

        searchView.onActionViewExpanded();*/

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
       // search = (SearchView) findViewById(R.id.search);
       /* searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);*/

      /*  int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) searchView.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setVisibility(View.GONE); */

     /*   int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = searchView.findViewById(searchPlateId);
        if (searchPlateView != null) {
            searchPlateView.setBackgroundColor(Color.TRANSPARENT);

        }*/

        final int[] a = {0};
        final boolean[] activityStartup = {true};
    /*    searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    btn.setVisibility(View.VISIBLE);
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    if (activityStartup[0]) {


                        activityStartup[0] = false;
                        searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                        searchView.clearFocus();
                    }
                    else {
                        searchView.setQueryHint("");
                        tv.setText(R.string.hintSearchMess);

                    }
                   /* if(a[0]==0 || a[0]==1)
                    {
                        if(a[0]==0) {
                            searchView.clearFocus();
                            a[0]=1;
                        }
                        else
                        {
                            searchView.clearFocus();
                            a[0]=2;
                        }

                    }



                }
                else {
                    btn.setVisibility(View.GONE);
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    searchView.setQueryHint("Serch by Keyword ex: product");
                    tv.setText("");
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.clearFocus();
                tv.setText("");
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Toast t = Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT);
                // t.show();
                searchView.clearFocus();
                return false;
            }
        });

*/
        // preparing list data
      //  prepareListData();

       // listAdapter = new ExpandableListAdapter(getContext(), listDataHeader, listDataChild);

      // simpleAdapter = new SimpleAdapter(getContext(), feedList, R.layout.list_view2, new String[]{"devicetype", "quantity","three"}, new int[]{R.id.one, R.id.two, R.id.three});

        // setting list adapter
       // expListView.setAdapter(listAdapter);

        // display the list
        displayList();
        // expand all Groups
        //expandAll();


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                View vParent = listAdapter.getGroupView(i, true, null, null);


                TextView tv1=(TextView)vParent.findViewById(R.id.lblListHeader);
                String val=tv1.getText().toString();
                Log.e("parent_value","valeu = "+val);


                Log.e("Expended list","child clicked : "+i);
                Intent faq=new Intent(getContext(),Faq_activity.class);
                faq.putExtra("title", val);
               // startActivity(faq);

                return false;
            }
        });


        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expListView.setTextFilterEnabled(true);


        return view;
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cc_menu_calls_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Why EdisonBro’s (EB) Smart Switch ?");
        listDataHeader.add("How Smart Switch can be operated ?");
       // listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("Our Smart Switch is an intelligent switch designed to deliver regular operation that a normal switch would do which includes operating Lights, \t Fan, Air Conditioner, Geyser etc in a totally wireless system. EdisonBro’s Smart Switch has changed the way traditional switches work.\n" +
                "  You can operate yours switches with a single touch when at home or away from home.");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Smart Switch can be operated by direct gentle Touch, IR Remote, Mobile App (iOS / Android), automatically by configuring Timers / Moods and finally as a result of PIR Sensor action.");
       // nowShowing.add("Despicable Me 2");

        /*List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");*/

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
       // listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    // method to expand all groups
    private void displayList() {

        // display the list
        loadSomeData();

        // get reference to the ExpandableListView
       // myList = (ExpandableListView) findViewById(R.id.expandableList);
        // create the adapter by passing your ArrayList data
       // listAdapter = new ExpendedList_adapter(getContext(), questionList);
        listAdapter = new ExpendedListAdapter_Faq(getContext(), QuestionList);
        // attach the adapter to the list
        expListView.setAdapter(listAdapter);

    }
    private void loadSomeData() {

        ArrayList<Answer> answerList = new ArrayList<Answer>();

        //1st question
        answerList = new ArrayList<Answer>();
        Answer answer = new Answer("EdisonBro Smart Switch is the Flagship Product of the company produced with over 10,000 hours of research and development effort. This Switch is carefully crafted with the best in class glass facade plate with gold design to suit the interiors of your living space. \n" +
                "It has built-in capacitive touch to support manual operation of the switch or it can be wirelessly operated using the IR Remote. It can also be operated using Smart Phone App (IOS / Android) from anywhere in the world. Each individual / group of switches can be configured to operate on basis of Time or can be connected to sensors and turn ON / OFF intelligently.","Why EdisonBro&#146;s (EB) Smart Switch ?");
        answerList.add(answer);

        Question question = new Question("Why EdisonBro (EB) Smart Switch ?", answerList);
        QuestionList.add(question);

        //2nd question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Yes. Without any wear & tear to your interiors, our switches are retrofittable into your existing electrical fixtures / panels.","Can I replace my existing switches with EdisonBro Smart Switches?");
        answerList.add(answer);
        question = new Question("Can I replace my existing switches with EdisonBro Smart Switches?", answerList);
        QuestionList.add(question);

        //3nd question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Zero maintenance as the front panel of Smart Switch Board is made up of glass. Glass being an insulator, resists electricity from passing through. It is easy to clean. \n Use a damp, soft, lint-free cloth to clean the surface of the switch board.","what maintenance does a Switch Board need?");
        answerList.add(answer);
        question = new Question("What maintenance does a Switch Board need?", answerList);
        QuestionList.add(question);

        //4th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Smart Switch can be operated by direct gentle Touch, IR Remote, Mobile App (IOS / Android), automatically by configuring Timers / Moods and finally as a result of PIR Sensor action.","How Smart Switch can be operated ?");
        answerList.add(answer);
        question = new Question("How Smart Switch can be operated ?", answerList);
        QuestionList.add(question);

        //5th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("If the Memory option is turned ON, it will remember the state information of the switches upon power failure and subsequent resumption. ","What will be the status of EB Smart Switch when power comes after power failure?");
        answerList.add(answer);
        question = new Question("What will be the status of EB Smart Switch when power comes after power failure?", answerList);
        QuestionList.add(question);


        //6th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When you touch the switch in EB Smart Switch , if EB Smart Switch is working then corresponding switch LED intensity will HIGH for ON and LOW for OFF ,if BUZZER is ON then you can hear the sound of BUZZER.","How can I identify EB Smart Switch switch is touched?");
        answerList.add(answer);
        question = new Question("How can I identify EB Smart Switch switch is touched?", answerList);
        QuestionList.add(question);

        //7th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("You can configure to operate a maximum of 5 Switches / Smart Home Devices [Curtain / Projector, etc] through PIR.","How many EB Smart Switch can be operated through PIR?");
        answerList.add(answer);
        question = new Question("How many EB Smart Switch can be operated through PIR?", answerList);
        QuestionList.add(question);

        //8th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Yes, you can do with device setting operation.","Can I disable EB Smart Switch touch operation?");
        answerList.add(answer);
        question = new Question("Can I disable EB Smart Switch touch operation?", answerList);
        QuestionList.add(question);

        //9th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Yes, you can do with device setting operation.","Is it possible to lock the EB Smart Switch operation from mobile app?");
        answerList.add(answer);
        question = new Question("Is it possible to lock the EB Smart Switch operation from mobile app?", answerList);
        QuestionList.add(question);

        //10th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("MOOD setting also known as SCENE setting is available on the App; when configured is applicable only to that Smart Phone user. Every user of the Home / Office can configure up to 3 different Moods as per his / her wish.\n" +
                "Kindly see the help section on how to set MOOD under EB Smart Products  - Smart Switch - Mobile App Operations - Set Mood.","What is the function of MOOD setting?");
        answerList.add(answer);
        question = new Question("What is the function of MOOD setting?", answerList);
        QuestionList.add(question);

        //11th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Yes, you can do with MOOD setting operation.","Can I configure same EB Smart Switch with different moods?");
        answerList.add(answer);
        question = new Question("Can I configure same EB Smart Switch with different MOODs?", answerList);
        QuestionList.add(question);

        //12th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("First you check whether TOUCH operation is disabled in EB Smart Living application , if yes enable it else there is absolutely no troubleshooting user can do.You need to contact EdisonBro Support for further assistance.","What can I do when EB Smart Switch touch is not working?");
        answerList.add(answer);
        question = new Question("What can I do when EB Smart Switch touch is not working?", answerList);
        QuestionList.add(question);

        //13th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Yes,If user wishes to change the icon of " +
                "any switch it can be done by a Long " +
                "press on the bulb switch. As it is " +
                "lighting Appliance switch the user " +
                "can set only lighting " +
                "appliances " +
                "(tube light, bed lamp, CFL, etc) icons.\n" +
                "Similarly, for HVAC Appliance " +
                "switch the user can set only HVAC " +
                "appliances (Air conditioner & " +
                "Geyser) icons.","Can I change the EB Smart Switch consisting device icons in mobile app?");
        answerList.add(answer);
        question = new Question("Can I change the EB Smart Switch consisting device icons in mobile app?", answerList);
        QuestionList.add(question);

        //14th question
      /*  answerList = new ArrayList<Answer>();
        answer = new Answer("No answer","What LED colors are there in swichboard and what they indicate?");
        answerList.add(answer);
        question = new Question("What LED colors are there in swichboard and what they indicate?", answerList);
        QuestionList.add(question);*/


        //15th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When TOUCH is turned ON/OFF it allows the user to ENABLE/DISABLE " +
                "all the direct touch actions on the EB Smart Switch thus acting as a child " +
                "lock when switch is turned OFF.","What is the function of TOUCH ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of TOUCH ON/OFF in device setting?", answerList);
        QuestionList.add(question);

        //16th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When IR is turned ON/OFF it will ENABLE/DISABLE the working of IR " +
                "remote on the EB Smart Switch.","What is the function of IR ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of IR ON/OFF in device setting?", answerList);
        QuestionList.add(question);

        //17th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("Allows the user to control the Buzzer / Beep sound either ON/OFF " +
                "when switches are operated.","What is the function of BUZZER ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of BUZZER ON/OFF in device setting?", answerList);
        QuestionList.add(question);


        //18th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When MEMORY is turned ON it allows the user to store the switch " +
                "status onto the on-board memory. When MEMORY is turned OFF " +
                "EB Smart Switch doesn't store the status of the switch and upon Power " +
                "Failure and resumption, all the switches will be in OFF state.","What is the function of MEMORY ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of MEMORY ON/OFF in device setting?", answerList);
        QuestionList.add(question);

        //19th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When HARDWARE is turned ON/OFF it allows the user to" +
                "ENABLE/DISABLE the complete hardware not only from the mobile" +
                "App but also from IR Remote & Touch.","What is the function of HARDWARE ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of HARDWARE ON/OFF in device setting?", answerList);
        QuestionList.add(question);


        //20th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When APP is turned ON/OFF it allows the user to ENABLE/DISABLE " +
                "operations through Smart phone App.","What is the function of APP ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of APP ON/OFF in device setting?", answerList);
        QuestionList.add(question);


        //21th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("By turning ON MANUAL OVERRIDE, user can force the device to be turned ON and not to respond to the PIR command.","What is the function of MANUAL OVERRIDE ON/OFF setting in app?");
        answerList.add(answer);
        question = new Question("What is the function of MANUAL OVERRIDE ON/OFF setting in app?", answerList);
        QuestionList.add(question);

        //22th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("When CONFIG MODE is turned ON it allows the user to enter into the" +
                "configuration mode. (For further details refer Configuration &" +
                "Administration Manual).","What is the function of CONFIG ON/OFF in device setting?");
        answerList.add(answer);
        question = new Question("What is the function of CONFIG ON/OFF in device setting?", answerList);
        QuestionList.add(question);


        //23th question
        answerList = new ArrayList<Answer>();
        answer = new Answer("First check if your App is connected to the EB Gateway which is indicated by Green Dot. If a Red dot appears, then your internet connection either on the Phone or Gateway is not working and hence operations from the App are not working.If both the internet connections are working, kindly look for the Green LED of Gateway to blink on this confirms App is able to communicate with the EB Gateway.Then you need to contact EdisonBro Customer Care for further assistance.\n","My Smart Switch is working Fine with Touch & IR but not App. Why?");
        answerList.add(answer);
        question = new Question("My Smart Switch is working Fine with Touch & IR but not App. Why?", answerList);
        QuestionList.add(question);

        //Empty
    /*answerList = new ArrayList<Answer>();
        answer = new Answer("","");
        answerList.add(answer);
        question = new Question("", answerList);
        QuestionList.add(question);



    */

       /* ArrayList<Answer> AnswerList = new ArrayList<Answer>();
        Answer answer = new Answer("I am fine ");
        AnswerList.add(answer);

        Question question = new Question("How are you", AnswerList);
        questionList.add(question);



        AnswerList = new ArrayList<Answer>();
        answer = new Answer("I am in banglore ");
        AnswerList.add(answer);

        question = new Question("How are you", AnswerList);
        questionList.add(question);*/
    }

    // method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expListView.expandGroup(i);
        }
    }


    @Override
    public boolean onClose() {
        // TODO Auto-generated method stub
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // TODO Auto-generated method stub
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // TODO Auto-generated method stub
        listAdapter.filterData(query);
        expandAll();
        return false;
    }


}



