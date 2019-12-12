package edisonbro.com.edisonbroautomation.customercarepager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import edisonbro.com.edisonbroautomation.R;
import edisonbro.com.edisonbroautomation.custmercarefrag.FAQ;
import edisonbro.com.edisonbroautomation.custmercarefrag.HELP;
import edisonbro.com.edisonbroautomation.custmercarefrag.TicketFragment;


public class TabWOIconActivity extends AppCompatActivity {

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.cc_activity_tab_with_icon);
        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);

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
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            /*case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_with_icon:
                Intent withicon=new Intent(this,TabWithIconActivity.class);
                startActivity(withicon);
                finish();
                return true;
            case R.id.action_customtab:
                Intent custom_tab=new Intent(this,CustomTabActivity.class);
                startActivity(custom_tab);
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
        ticket=new TicketFragment();
        adapter.addFragment(faq,"FAQ");
        adapter.addFragment(help,"HELP");
        adapter.addFragment(ticket,"TICKETS");
        viewPager.setAdapter(adapter);
    }

}
