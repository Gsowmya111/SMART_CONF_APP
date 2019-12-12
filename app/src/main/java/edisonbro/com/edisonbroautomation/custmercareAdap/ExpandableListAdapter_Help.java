package edisonbro.com.edisonbroautomation.custmercareAdap;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import edisonbro.com.edisonbroautomation.R;

public class ExpandableListAdapter_Help extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private final Integer[] imageId;

    public ExpandableListAdapter_Help(Context context, List<String> listDataHeader,
                                      HashMap<String, List<String>> listChildData, Integer[] imageId) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.imageId = imageId;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cc_subproduct, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.textproduct);

        txtListChild.setText(childText);
        String tvval=txtListChild.getText().toString();
        /*if(tvval.length()<0 || tvval.equalsIgnoreCase(""))
            txtListChild.setVisibility(View.GONE);*/
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int i=0;
        try {
            i= this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }catch (Exception e)
        {}
        return i;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.cc_list_items, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txt_item_qq_title);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.img_item_qq);

       // imageView.setImageResource(R.drawable.tentrance01);
        imageView.setImageResource(imageId[groupPosition]);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
