package it.univr.library.Model;

import it.univr.library.Address;
import it.univr.library.RegisteredClient;
import it.univr.library.User;

import java.util.List;

public interface ModelUserAddress
{
    public List<Address> getAddressesRegisteredUser(User testUser);
    public void addAddress(RegisteredClient user, Address address);
    public void unlinkAddressFromUser(RegisteredClient user, Address addressToDelete);
    public List<String> getCities();
    public List<String> getPostalCodes();
}
