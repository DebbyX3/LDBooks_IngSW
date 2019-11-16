package it.univr.library;

import java.util.ArrayList;

public class Cities
{
    private ArrayList<String> cities;
    private ArrayList<String> CAPs;

    public void setCities(ArrayList<String> cities)
    {
        this.cities = cities;
    }

    public void setCAPs(ArrayList<String> CAPs)
    {
        this.CAPs = CAPs;
    }

    public ArrayList<String> getCities()
    {
        return cities;
    }

    public ArrayList<String> getCAPs()
    {
        return CAPs;
    }
}
