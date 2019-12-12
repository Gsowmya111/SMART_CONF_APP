package edisonbro.com.edisonbroautomation.customercarepager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;

import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.custmercarefrag.FAQ;
import edisonbro.com.edisonbroautomation.custmercarefrag.HELP;
import edisonbro.com.edisonbroautomation.custmercarefrag.TicketFragment;

public class Helpandsupportactivity extends AppCompatActivity {
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    HELP help;
    FAQ faq;
    TicketFragment ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setTitle("Help & support");
        setContentView(R.layout.cc_helpandsupportactivity_layout);


        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());



        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setPageMargin(pageMargin);

       // ActionBar actionBar = getSupportActionBar();
      //  actionBar.setDisplayHomeAsUpEnabled(true);


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

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

        setupViewPager(viewPager);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                /*Intent intent = new Intent(this, CustomTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        faq=new FAQ();
        help=new HELP();
        ticket=new TicketFragment();
        adapter.addFragment(faq,"Product");
        adapter.addFragment(help,"My Account");
        adapter.addFragment(ticket,"App Queries");
        viewPager.setAdapter(adapter);
    }
}
