package edisonbro.com.edisonbroautomation.customercarepager;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import edisonbro.com.edisonbroautomation.R;


public class Faq_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String title = getIntent().getStringExtra("title");

        super.onCreate(savedInstanceState);
        setTitle(title);
        setContentView(R.layout.cc_faq_activity_layout);

        Button bt=(Button)findViewById(R.id.btfaq);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent faq=new Intent(getApplication(),MainActivity.class);

                startActivity(faq);*/
            }
        });

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
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

}
