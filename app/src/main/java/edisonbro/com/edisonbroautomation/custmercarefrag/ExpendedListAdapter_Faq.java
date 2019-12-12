package edisonbro.com.edisonbroautomation.custmercarefrag;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edisonbro.com.edisonbroautomation.R;

public class ExpendedListAdapter_Faq extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Question> continentList;
    private ArrayList<Question> originalList;

    public ExpendedListAdapter_Faq(Context context, ArrayList<Question> continentList) {
        this.context = context;
        this.continentList = new ArrayList<Question>();
        this.continentList.addAll(continentList);
        this.originalList = new ArrayList<Question>();
        this.originalList.addAll(continentList);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        ArrayList<Answer> countryList = continentList.get(groupPosition).getCountryList();
        return countryList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Answer country = (Answer) getChild(groupPosition, childPosition);
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cc_faq_answer_list, null);
        }

        TextView code = (TextView) convertView.findViewById(R.id.lblListItem);
		TextView name = (TextView) convertView.findViewById(R.id.hide);
		//TextView population = (TextView) convertView.findViewById(R.id.population);
        code.setText(country.getCode().trim());
		name.setText(country.getName().trim());
		//population.setText(NumberFormat.getNumberInstance(Locale.US).format(country.getPopulation()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        ArrayList<Answer> countryList = continentList.get(groupPosition).getCountryList();
        return countryList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return continentList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return continentList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Question continent = (Question) getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.cc_faq_question_list, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.lblListHeader);
        heading.setText(continent.getName().trim());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    public void filterData(String query)
    {
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        continentList.clear();

        if(query.isEmpty())
        {
            continentList.addAll(originalList);
        } else {
            for(Question continent: originalList)
            {
                ArrayList<Answer> countryList = continent.getCountryList();
                ArrayList<Answer> newList = new ArrayList<Answer>();
                for(Answer country: countryList)
                {
                    if(country.getCode().toLowerCase().contains(query) || country.getName().toLowerCase().contains(query))
                    {
                        newList.add(country);
                    }
                }
                if(newList.size() > 0)
                {
                    Question nContinent = new Question(continent.getName(), newList);
                    continentList.add(nContinent);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(continentList.size()));
        notifyDataSetChanged();
    }
}




/*
package ebcustomer.tabwithviewpager.Fragment;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import ebcustomer.tabwithviewpager.R;

public class ExpendedListAdapter_Faq extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Question> questionList;
    private ArrayList<Question> originalList;

    public ExpendedListAdapter_Faq(Context context, ArrayList<Question> continentList) {
        this.context = context;
        this.questionList = new ArrayList<Question>();
        this.questionList.addAll(questionList);
        this.originalList = new ArrayList<Question>();
        this.originalList.addAll(questionList);
    }
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        ArrayList<Answer> answerList = questionList.get(groupPosition).getCountryList();
        return answerList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Country country = (Country) getChild(groupPosition, childPosition);
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_answer_list, null);
        }

        TextView ans = (TextView) convertView.findViewById(R.id.lblListItem);

        ans.setText(country.getCode().trim());


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub

        ArrayList<Answer> answerList = questionList.get(groupPosition).getCountryList();
        //ArrayList<Country> countryList = continentList.get(groupPosition).getCountryList();
        return answerList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return questionList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return questionList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Question continent = (Question) getGroup(groupPosition);
        if(convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_question_list, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.lblListHeader);
        heading.setText(continent.getName().trim());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    public void filterData(String query)
    {
        query = query.toLowerCase();
        Log.v("MyListAdapter", String.valueOf(questionList.size()));
        questionList.clear();

        if(query.isEmpty())
        {
            questionList.addAll(originalList);
        } else {
            for(Question continent: originalList)
            {
                ArrayList<Answer> countryList = continent.getCountryList();
                ArrayList<Answer> newList = new ArrayList<Answer>();
                for(Answer country: countryList)
                {
                    if(country.getCode().toLowerCase().contains(query))
                    {
                        newList.add(country);
                    }
                }
                if(newList.size() > 0)
                {
                    Question nContinent = new Question(continent.getName(), newList);
                    questionList.add(nContinent);
                }
            }
        }

        Log.v("MyListAdapter", String.valueOf(questionList.size()));
        notifyDataSetChanged();
    }
}
*/
