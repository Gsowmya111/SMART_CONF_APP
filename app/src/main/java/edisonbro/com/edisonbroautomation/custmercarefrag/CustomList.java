package edisonbro.com.edisonbroautomation.custmercarefrag;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edisonbro.com.edisonbroautomation.R;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public CustomList(Activity context,
                      String[] web, Integer[] imageId) {
        super(context, R.layout.cc_list_items, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.cc_list_items, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_item_qq_title);

        int len=imageId.length;
        int len1=web.length;


        ImageView imageView = (ImageView) rowView.findViewById(R.id.img_item_qq);
        txtTitle.setText(web[position]);

        imageView.setImageResource(imageId[position]);



        return rowView;
    }
}