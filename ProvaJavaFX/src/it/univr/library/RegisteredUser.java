package it.univr.library;

import java.util.ArrayList;

public class RegisteredUser extends User {
    private ArrayList<Address> addresses = new ArrayList<Address>();

    public RegisteredUser(Address singleAddress)
    {
        addresses.add(singleAddress);
    }

    public RegisteredUser(ArrayList<Address> listOfAddress)
    {
        this.addresses = listOfAddress;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }



}
