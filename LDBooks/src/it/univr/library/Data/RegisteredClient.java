package it.univr.library.Data;

import java.util.ArrayList;
import java.util.List;

public class RegisteredClient extends Client
{
    private List<Address> addresses = new ArrayList<Address>();

    public RegisteredClient(String name, String surname, String email, String password, String phoneNumber, List<Address> addresses) {
        super(name, surname, email, password, phoneNumber);
        this.addresses = addresses;

        normalizeUser();
    }

    public RegisteredClient(String name, String surname, String email, String password, String phoneNumber, Address singleAddress) {
        super(name, surname, email, password, phoneNumber);
        this.addresses.add(singleAddress);

        normalizeUser();
    }

    public RegisteredClient(){};

    public RegisteredClient(Address singleAddress)
    {
        addresses.add(singleAddress);
    }

    public RegisteredClient(List<Address> listOfAddress)
    {
        this.addresses = listOfAddress;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setSingleAddress(Address singleAddress)
    {
        addresses.add(singleAddress);
    }

    public void setAddresses(List<Address> addresses)
    {
        this.addresses = addresses;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", " + addresses;
    }

    private void normalizeUser()
    {
        for(Address address: addresses)
            address.normalizeAddress();
    }
}
