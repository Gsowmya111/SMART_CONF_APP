package edisonbro.com.edisonbroautomation.customercarepager;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import edisonbro.com.edisonbroautomation.R;

public class Gateway extends AppCompatActivity {



    public String p_name;
    public LinearLayout layout1,layout2,layout3,layout4;
    TableLayout piruserguid;
    public Integer[] imgicon2={R.drawable.pir_priority_set,R.drawable.pir_priority_unset,R.drawable.settings_icon,R.drawable.timer_settings,R.drawable.mood0,R.drawable.pir_motion,R.drawable.pir_light,R.drawable.all_on,R.drawable.all_off,R.drawable.group_pir_select,R.drawable.individual_pir_select};
    Button b_back;
    public View popupview;
    public PopupWindow popupWindow;
    public LinearLayout popup_lay_main;
    public ImageView gateway_img,gatway_app_img,gateway_troubleshoot_img,gatway_app_img2,gatway_app_img3,gatway_app_img4,gatway_app_img5,gatway_app_img6,gatway_app_img7,gateway_img2;
    TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //  String title = getIntent().getStringExtra("title");
        p_name = getIntent().getStringExtra("p_name");
        super.onCreate(savedInstanceState);
        setTitle("EB Intelligent "+p_name);

        popupview=getLayoutInflater().inflate(R.layout.cc_popup,null);
        popupWindow=new PopupWindow(popupview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        if(p_name.equalsIgnoreCase("Gateway Information")) {

            setContentView(R.layout.cc_gateway);


            gateway_img2=(ImageView)findViewById(R.id.gateway_img2);
            gateway_img=(ImageView)findViewById(R.id.gateway_img);
            gateway_Info_popup_img();

        }else if(p_name.equalsIgnoreCase("Gateway Mobile Application")) {
            setContentView(R.layout.cc_gateway_app);


            gatway_app_img=(ImageView)findViewById(R.id.gatway_app_img);
            gatway_app_img2=(ImageView)findViewById(R.id.gatway_app_img2);
            gatway_app_img3=(ImageView)findViewById(R.id.gatway_app_img3);
            gatway_app_img4=(ImageView)findViewById(R.id.gatway_app_img4);
            gatway_app_img5=(ImageView)findViewById(R.id.gatway_app_img5);
            gatway_app_img6=(ImageView)findViewById(R.id.gatway_app_img6);
            gatway_app_img7=(ImageView)findViewById(R.id.gatway_app_img7);
            gateway_app_popup_img();
        }else if(p_name.equalsIgnoreCase("Gateway Troubleshoot")) {
            setContentView(R.layout.cc_gateway_troubleshoot);

            gateway_troubleshoot_img=(ImageView)findViewById(R.id.gateway_troubleshoot_img);
            gateway_troubleshoot_popup_img();
        }

        tv_title=(TextView)findViewById(R.id.textView10);

        if(p_name.equalsIgnoreCase("Gateway Mobile Application")) {
            tv_title.setText("Gateway Application");
        }else  if(p_name.equalsIgnoreCase("Gateway Troubleshoot")) {
            tv_title.setText("Troubleshoot");
        }else{
            tv_title.setText("" + p_name);
        }

        b_back=(Button) findViewById(R.id.btnback);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       // ActionBar actionBar = getSupportActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);

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

   /* @Override
    public boolean setViewValue(View view, Object o, String s) {
        return false;
    }*/


    /// popup images ///

    //Gateway Information  //
    public void gateway_Info_popup_img()
    {
        gateway_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.homegateway);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gateway_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.gateway1);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });



    }

    //Gateway Information  //
    public void gateway_app_popup_img()
    {
        gatway_app_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gatway_app_img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway_opertr);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gatway_app_img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway_gateset);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gatway_app_img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway_gateset);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gatway_app_img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway_ipsetpop);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gatway_app_img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway_set1);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
        gatway_app_img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.app_gateway_set2);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    //Gateway Information  //
    public void gateway_troubleshoot_popup_img()
    {

        gateway_troubleshoot_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int location[]=new int[2];
                view.getLocationOnScreen(location);
                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,0,0);

                ImageView imgr=(ImageView)popupview.findViewById(R.id.popupimg);
                popup_lay_main=(LinearLayout)popupview.findViewById(R.id.popup_lay_main);

                imgr.setImageResource(R.drawable.gateway1);

                imgr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popup_lay_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });



    }


}
