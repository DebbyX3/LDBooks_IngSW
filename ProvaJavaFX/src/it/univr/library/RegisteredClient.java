package it.univr.library;

import it.univr.library.Address;
import it.univr.library.Client;

import java.util.ArrayList;

public class RegisteredClient extends Client
{
    private ArrayList<Address> addresses = new ArrayList<Address>();

    public RegisteredClient(String name, String surname, String email, String password, String phoneNumber, ArrayList<Address> addresses) {
        super(name, surname, email, password, phoneNumber);
        this.addresses = addresses;
    }

    public RegisteredClient(String name, String surname, String email, String password, String phoneNumber, Address singleAddress) {
        super(name, surname, email, password, phoneNumber);
        this.addresses.add(singleAddress);
    }

    public RegisteredClient(){};

    public RegisteredClient(Address singleAddress)
    {
        addresses.add(singleAddress);
    }

    public RegisteredClient(ArrayList<Address> listOfAddress)
    {
        this.addresses = listOfAddress;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setSingleAddress(Address singleAddress)
    {
        addresses.add(singleAddress);
    }

    public void setAddresses(ArrayList<Address> addresses)
    {
        this.addresses = addresses;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", " + addresses;
    }
}
