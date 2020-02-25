package it.univr.library.Data;

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

        normalizeAddress();
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

    public String toString()
    {
        return this.getStreet() + " " + this.getHouseNumber() + " " + this.getCity() + " " + this.getPostalCode();
    }

    public boolean isFilled()
    {
        return  (street != null && !street.trim().isEmpty()) &&
                (houseNumber != null && !houseNumber.trim().isEmpty()) &&
                (city != null && !city.trim().isEmpty()) &&
                (postalCode != null && !postalCode.trim().isEmpty());
    }

    public boolean isEmpty()
    {
        return  (street == null || street.trim().isEmpty()) &&
                (houseNumber == null || houseNumber.trim().isEmpty()) &&
                (city == null || city.trim().isEmpty()) &&
                (postalCode == null || postalCode.trim().isEmpty());
    }

    public boolean isPartiallyEmpty()
    {
        // It is partially empty when it is neither completely filled nor completely empty
        return !(isFilled() || isEmpty()); // the same as (!isFilled() && !isEmpty())
    }

    public void normalizeAddress()
    {
        if(street != null)
            street = street.trim();
        if(houseNumber != null)
            houseNumber = houseNumber.trim();
        if(city != null)
            city = city.trim();
        if(postalCode != null)
            postalCode = postalCode.trim();
    }
}
