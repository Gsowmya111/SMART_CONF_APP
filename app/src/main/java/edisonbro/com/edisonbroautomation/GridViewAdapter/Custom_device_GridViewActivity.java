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

import edisonbro.com.edisonbroautomation.MutatorMethods.DeviceListArray;
import edisonbro.com.edisonbroautomation.R;

public class Custom_device_GridViewActivity extends BaseAdapter {

    private Context mContext;

    ArrayList<DeviceListArray> deviceListArrays;

    public Custom_device_GridViewActivity(Context context, ArrayList<DeviceListArray> deviceListArrays) {
        mContext = context;
        this.deviceListArrays = deviceListArrays;
        hasStableIds();

    }

    @Override
    public int getCount() {
        return deviceListArrays.size();
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
            convertView = inflater.inflate(R.layout.s_device_gridview_layout, null);
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


        if(deviceListArrays.size()>0){

            DeviceListArray deviceListArrayobj = deviceListArrays.get(i);

            viewHolder.textViewAndroid.setText("" + deviceListArrayobj.getDeviceName());
            viewHolder.imageViewAndroid.setImageResource(deviceListArrayobj.getDeviceImage());
        }

       /* HashMap<Integer, String> hashMap = roomlist.get(i);
        for (Map.Entry<Integer, String> mapEntry : hashMap.entrySet())
        {
            int key = mapEntry.getKey();
            String value = mapEntry.getValue();
            try{
                if(viewHolder.textViewAndroid!=null) {
                    viewHolder.textViewAndroid.setText("" + value);
                    viewHolder.imageViewAndroid.setImageResource(key);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


        }*/
        return convertView;
    }

    static class ViewHolderItem {
        TextView textViewAndroid;
        ImageView imageViewAndroid;

    }

}