package edisonbro.com.edisonbroautomation.customercarepager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edisonbro.com.edisonbroautomation.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class ticket_generate extends AppCompatActivity {
    //This is our tablayout
    public Spinner service,dept;
    public EditText subject,desc;
    public Button choosefile,save,cancel;
    public TextView filename;
    public List<String> list1;

    private String selectedFilePath;
    private static final int PICK_FILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setTitle("Ticket Generate");
        setContentView(R.layout.cc_ticket_generate);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());

        service=(Spinner)findViewById(R.id.service);
        dept=(Spinner)findViewById(R.id.dept);
        subject=(EditText) findViewById(R.id.subject);
        desc=(EditText) findViewById(R.id.desc);
        choosefile=(Button) findViewById(R.id.btnfile);
        save=(Button) findViewById(R.id.submit);
        cancel=(Button) findViewById(R.id.cancel);
        filename=(TextView) findViewById(R.id.filename);


        service.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        dept.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

        ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
        list1=new ArrayList<String>();
        list1.add("Choose Service");
        list1.add("Service Type1");
        list1.add("Service Type2");
        list1.add("Service Type3");


        ArrayAdapter<String> adapter1;
        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list1);

        service.setAdapter(adapter1);

        list1=new ArrayList<String>();
        list1.add("Choose Department");
        list1.add("Electrician");
        list1.add("Software");
        list1.add("Hardware");


        adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list1);

        dept.setAdapter(adapter1);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });



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



    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("**//*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i("file","Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){

                    String[] strarray=selectedFilePath.split("/");
                    int len=strarray.length;
                    len--;
                    filename.setText(strarray[len]);
                }else{
                    Toast.makeText(this,"Cannot upload file to server", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
