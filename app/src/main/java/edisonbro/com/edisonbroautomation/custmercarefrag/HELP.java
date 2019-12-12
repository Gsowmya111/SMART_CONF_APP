package edisonbro.com.edisonbroautomation.custmercarefrag;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.customercarepager.Product_activity;


public class HELP extends Fragment {
    CustomList adapter;
    Integer[] icon;//new Integer[50];
    String[] name;//=new String[50];

    edisonbro.com.edisonbroautomation.custmercareAdap.ExpandableListAdapter_Help Eadapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private int lastExpandedPosition = -1;

    public ListView lv;
    public SimpleAdapter simpleAdapter;



    public HELP() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.cc_help_layout, container, false);
       // final SearchView searchView = (SearchView) view.findViewById(R.id.search);
        final TextView tv=(TextView) view.findViewById(R.id.searchtexthint);
      // searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.hintSearchMess) + "</font>"));



       /* int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = searchView.findViewById(searchPlateId);
        if (searchPlateView != null) {
            searchPlateView.setBackgroundColor(Color.BLACK);
        }
*/

        final boolean[] activityStartup = {true};
       /* searchView.setIconifiedByDefault(false);

        int magId = getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView magImage = (ImageView) searchView.findViewById(magId);
        magImage.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        magImage.setImageDrawable(null);

        magImage.setVisibility(View.GONE);



        int searchPlateId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_plate", null, null);
        View searchPlateView = searchView.findViewById(searchPlateId);
        if (searchPlateView != null) {
            searchPlateView.setBackgroundColor(Color.TRANSPARENT);
        }

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    if (activityStartup[0]) {

                       // searchView.clearFocus();
                        activityStartup[0] = false;
                        searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));


                    }
                    else {
                        searchView.setQueryHint("");
                        tv.setText(R.string.hintSearchMess);

                    }
                }
                else {
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    searchView.setQueryHint("Serch by Keyword ex: product");
                    tv.setText("");
                }

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
       // searchView.setIconified(true);

       // final ExpandableListView lv=(ExpandableListView) view.findViewById(R.id.listhelp);
        lv=(ListView) view.findViewById(R.id.listhelp);

        icon= new Integer[]{R.drawable.product_icon, R.drawable.account, R.drawable.appquery};
        name= new String[]{"EB Smart Products","My EB Account","EB App Queries"};



        adapter = new
                CustomList((Activity) getContext(), name,icon);

        prepareListData();


        //for expended list view
        //Eadapter = new ebcustomer.tabwithviewpager.Adapters.ExpandableListAdapter(getContext(), listDataHeader, listDataChild,icon);

        // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<3;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("p_image", Integer.toString(icon[i]));
            hm.put("p_name",name[i]);
            aList.add(hm);
        }


        // Keys used in Hashmap
        String[] from = { "p_image","p_name"};

        // Ids of views in listview_layout
        int[] to = { R.id.img_item_qq,R.id.txt_item_qq_title};

        simpleAdapter =new SimpleAdapter(getContext(), aList, R.layout.cc_list_items, from, to);


        //for Expended listview
        //lv.setAdapter(Eadapter);
        lv.setAdapter(simpleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView item=(TextView)view.findViewById(R.id.txt_item_qq_title);
                if(item.getText().toString().equalsIgnoreCase("EB Smart Products"))
                {
                    Intent productactivity=new Intent(getContext(), Product_activity.class);
                    startActivity(productactivity);
                }
            }
        });





        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.cc_menu_chat_fragment, menu);
            super.onCreateOptionsMenu(menu, inflater);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        //for(int i=0;i<roomnamesdb.length;i++)

            listDataHeader.add("Products");
            listDataHeader.add("My Account");
            listDataHeader.add("App Queries");
        // Adding child data
       /*listDataHeader.add("How are you?");
       listDataHeader.add("Where are you?");
       // listDataHeader.add("Coming Soon..");
*/
        // Adding child data
        List<String> subroom = new ArrayList<String>();
        subroom.add("SwitchBoard");
        subroom.add("PIR Sensor");
        subroom.add("Door Bell");
        subroom.add("Curtain Controller");
       /*top250.add("Bathroom");
       top250.add("subroom2");*/


        //for(int i=0;i<listDataHeader.size();i++)
       // {
       //     subroom=db.fetch_subroom(house_id,spinner3.getSelectedItem().toString(),listDataHeader.get(i));



            //listDataChild.put(listDataHeader.get(0), subroom);



            // listDataChild.put(listDataHeader.get(1), null);
            //listDataChild.put(listDataHeader.get(2), null);

      //  }


       /*List<String> nowShowing = new ArrayList<String>();
       nowShowing.add("I am in Banglore.");*/
        // nowShowing.add("Despicable Me 2");

        /*List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");*/

      /* listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
       listDataChild.put(listDataHeader.get(1), nowShowing);*/
        // listDataChild.put(listDataHeader.get(2), comingSoon);
    }

}


// Expended Listview code
/*
/* lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    Intent withouticon=new Intent(getContext(),switchboardmanual.class);
                    startActivity(withouticon);
                }
            }
        });*/

/*lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
@Override
public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
        return false;
        }
        });
        lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

@Override
public void onGroupExpand(int groupPosition) {
        if (lastExpandedPosition != -1
        && groupPosition != lastExpandedPosition) {
        lv.collapseGroup(lastExpandedPosition);
        }
        lastExpandedPosition = groupPosition;

        Intent productactivity=new Intent(getContext(), Product_activity.class);
        startActivity(productactivity);
        }
        });

        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
@Override
public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

        if(i1==0) {
        Intent withouticon = new Intent(getContext(), switchboardmanual.class);
        startActivity(withouticon);
        }
        return false;
        }
        });*/
