package edisonbro.com.edisonbroautomation.GridViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edisonbro.com.edisonbroautomation.R;

public class Custom_device_operation_GridViewActivity extends BaseAdapter {

    private Context mContext;

    ArrayList<HashMap<Integer,String>> roomlist ;

    public Custom_device_operation_GridViewActivity(Context context, ArrayList<HashMap<Integer,String>> roomlist) {
        mContext = context;
        this.roomlist = roomlist;
        hasStableIds();

    }

    @Override
    public int getCount() {
        return roomlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolderItem viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderItem();
            gridViewAndroid = new View(mContext);
            convertView = inflater.inflate(R.layout.s_swb_gridview_layout, null);
            viewHolder.flagconf = (TextView) convertView.findViewById(R.id.flagconf);
            viewHolder.textViewAndroid = (TextView) convertView.findViewById(R.id.android_gridview_text);
            viewHolder.imageViewAndroid = (ImageView) convertView.findViewById(R.id.android_gridview_image);

            convertView.setTag(viewHolder);
            /*if(roomlist!=null && roomlist.size()>0){
                for (HashMap<Integer, String> hash : roomlist) {
                    for (Map.Entry<Integer, String> mapEntry : hash.entrySet())
                    {
                        int key = mapEntry.getKey();
                        String value = mapEntry.getValue();
                        textViewAndroid.setText(""+value);
                        imageViewAndroid.setImageResource(key);

                    }
                }
            }*/





        } else {
            gridViewAndroid = (View) convertView;
            viewHolder = (ViewHolderItem) convertView.getTag();
        }
        HashMap<Integer, String> hashMap = roomlist.get(i);
        for (Map.Entry<Integer, String> mapEntry : hashMap.entrySet())
        {
            int key = mapEntry.getKey();
            String value = mapEntry.getValue();
            try{
                if(viewHolder.textViewAndroid!=null) {
                    viewHolder.textViewAndroid.setText("" + value);
                    viewHolder.imageViewAndroid.setImageResource(key);
                    viewHolder.flagconf.setText("0");
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }
        return convertView;
    }

    static class ViewHolderItem {
        TextView textViewAndroid,flagconf;
        ImageView imageViewAndroid;

    }

}