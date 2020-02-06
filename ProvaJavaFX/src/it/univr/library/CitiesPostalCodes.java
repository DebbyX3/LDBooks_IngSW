package it.univr.library;

import java.util.List;
import java.util.Objects;

public class CitiesPostalCodes
{
    private List<String> cities;
    private List<String> postalCodes;

    public CitiesPostalCodes(List<String> cities, List<String> postalCodes)
    {
        this.cities = cities;
        this.postalCodes = postalCodes;
    }

    public void setCities(List<String> cities)
    {
        this.cities = cities;
    }

    public void setPostalCodes(List<String> postalCodes)
    {
        this.postalCodes = postalCodes;
    }

    public List<String> getCities()
    {
        return cities;
    }

    public List<String> getPostalCodes()
    {
        return postalCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CitiesPostalCodes that = (CitiesPostalCodes) o;
        return cities.equals(that.cities) & postalCodes.equals(that.postalCodes);
    }

    @Override
    public int hashCode() {
        return cities.hashCode() ^ postalCodes.hashCode();
    }
}
