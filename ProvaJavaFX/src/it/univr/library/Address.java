package it.univr.library;

import java.util.Objects;

public class Address
{
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

    public Address(){}

    public Address(String street, String houseNumber, String city, String postalCode) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreetQuery() {
        return street;
    }

    public String getStreet() {
        return street.replaceAll("''", "'");
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Address address = (Address) o;
        return  this.getStreet().equals(address.getStreet()) &&
                this.getCity().equals(address.getCity()) &&
                this.getPostalCode().equals(address.getPostalCode()) &&
                this.getHouseNumber().equals(address.getHouseNumber());
    }

    @Override
    public int hashCode()
    {
        return street.hashCode() ^ houseNumber.hashCode() ^ city.hashCode() ^ postalCode.hashCode();
    }
}
