package it.univr.library;

public class Address
{
    private String street;
    private String houseNumber;
    private String city;
    private String postalCode;

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
}
