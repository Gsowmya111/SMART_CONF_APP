package edisonbro.com.edisonbroautomation.customercarepager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import edisonbro.com.edisonbroautomation.Main_Navigation_Activity;
import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.custmercarefrag.FAQ;
import edisonbro.com.edisonbroautomation.custmercarefrag.HELP;
import edisonbro.com.edisonbroautomation.custmercarefrag.TicketFragment;

public class CustomTabActivity extends AppCompatActivity {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    HELP help;
    FAQ faq;
    TicketFragment TicketFragment;

    String[] tabTitle={"FAQ","HELP","TICKETS"};
    int[] unreadCount={0,1,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc_activity_tab_with_icon);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        setupViewPager(viewPager);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#064a93"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));


        Intent withicon = new Intent(this, TabWithIconActivity.class);
        startActivity(withicon);
        finish();
        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.cc_menu_home, menu);
        // Associate searchable configuration with the SearchView
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Intent withicon=new Intent(this,TabWithIconActivity.class);
                startActivity(withicon);
                return true;
           /* case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_with_icon:
                withicon = new Intent(this, TabWithIconActivity.class);
                startActivity(withicon);
                finish();
                return true;
            case R.id.action_without_icon:
                Intent withouticon=new Intent(this,TabWithIconActivity.class);
                startActivity(withouticon);
                finish();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        faq=new FAQ();
        help=new HELP();
        TicketFragment=new TicketFragment();
        adapter.addFragment(faq,"FAQ");
        adapter.addFragment(help,"HELP");
        adapter.addFragment(TicketFragment,"TICKETS");
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.cc_custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if(unreadCount[pos]>0)
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText(""+unreadCount[pos]);
        }
        else
            tv_count.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons()
    {

        for(int i=0;i<tabTitle.length;i++)
        {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/
            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }

    @Override
    public void onBackPressed() {
        backpressed();


        // set dialog message
     /*      final AlertDialog alertDialogBuilder1=new AlertDialog.Builder(this)
               // .setView(promptsView)
                .setCancelable(false)
                .setPositiveButton("OK",null)
                .setNegativeButton("Cancel",null)
                .create();

        alertDialogBuilder1.setOnShowListener(new DialogInterface.OnShowListener()
        {

         @Override
            public void onShow(DialogInterface dialogInterface) {
                Button d=alertDialogBuilder1.getButton(AlertDialog.BUTTON_POSITIVE);
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Button can=alertDialogBuilder1.getButton(AlertDialog.BUTTON_NEGATIVE);
                can.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        });
*/


        //super.onBackPressed();
    }


    public void backpressed(){
        Intent withicon=new Intent(this,Main_Navigation_Activity.class);
        startActivity(withicon);
        finish();
    }
}
