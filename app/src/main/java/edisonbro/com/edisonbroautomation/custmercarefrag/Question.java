package edisonbro.com.edisonbroautomation.custmercarefrag;

import java.util.ArrayList;

public class Question {

    private String name;
    private ArrayList<Answer> countryList = new ArrayList<Answer>();

    public Question(String name, ArrayList<Answer> countryList) {
        super();
        this.name = name;
        this.countryList = countryList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Answer> getCountryList() {
        return countryList;
    }

    public void setCountryList(ArrayList<Answer> countryList) {
        this.countryList = countryList;
    }


}
