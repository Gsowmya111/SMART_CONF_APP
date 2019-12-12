package edisonbro.com.edisonbroautomation.custmercarefrag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.customercarepager.ticket_generate;

import java.util.ArrayList;
import java.util.HashMap;



public class TicketFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ArrayList<HashMap<String, String>> feedList;

    public ListView listticket;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
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
       View view=inflater.inflate(R.layout.cc_ticket, container, false);
        feedList= new ArrayList<HashMap<String, String>>();

        listticket=(ListView)view.findViewById(R.id.listticket);
        Button btn=(Button)view.findViewById(R.id.btn_ticket);

        TextView edisonemail=(TextView)view.findViewById(R.id.edisonemail);
        final String email_string=getResources().getString(R.string.edisonmailid);
        edisonemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", email_string, null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Query");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    final TextView phonenumber=(TextView)view.findViewById(R.id.phonenumber);
        phonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    // TODO Auto-generated method stub
                    String phone_no= phonenumber.getText().toString().replaceAll("-", "");
                    startActivity( new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone_no)));


                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });



       /* int searchImgId = getContext().getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView searchImage = (ImageView) searchView.findViewById(searchImgId);


        searchImage.setVisibility(View.GONE);*/

       /* final boolean[] activityStartup = {true};
        //searchView.setIconified(false);
        searchView.onActionViewExpanded();

// Inside onCreate

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (activityStartup[0]) {

                        searchView.clearFocus();
                        activityStartup[0] = false;
                    }
                }
            }
        });
*/

       // final SearchView searchView = (SearchView) view.findViewById(R.id.search);
        final TextView tv=(TextView) view.findViewById(R.id.searchtexthint);
       // searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.hintSearchMess) + "</font>"));


        final boolean[] activityStartup = {true};
      //  searchView.setIconifiedByDefault(false);

      /*  searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    searchView.setQueryHint(Html.fromHtml("<font color = #4282d0>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

                    if (activityStartup[0]) {

                        searchView.clearFocus();
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
*/


        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tno","Ticket number");
        map.put("sub","Subject");
        map.put("res","View Response");
        feedList.add(map);

        map = new HashMap<String, String>();

        map.put("tno","TNO1234");
        map.put("sub","SwitchBoar issue");
        map.put("res","View Response ");
        feedList.add(map);


        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), feedList, R.layout.cc_ticket_list_layout, new String[]{"tno", "sub","res"}, new int[]{R.id.ticketno, R.id.subject, R.id.response});
        listticket.setAdapter(simpleAdapter);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),ticket_generate.class);
                startActivity(intent);
            }
        });

        return view;

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

}
