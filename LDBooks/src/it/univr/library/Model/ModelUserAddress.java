package it.univr.library.Model;

import it.univr.library.Data.Address;
import it.univr.library.Data.RegisteredClient;
import it.univr.library.Data.User;

import java.util.List;

public interface ModelUserAddress
{
    public List<Address> getAddressesRegisteredUser(User testUser);
    public List<Address> getAddressesNotRegisteredUser(User testUser);
    public void addAddressNotRegisteredUser(Address address);
    public void addAddressRegisteredUser(RegisteredClient user, Address address);
    public void unlinkAddressFromUser(RegisteredClient user, Address addressToDelete);
    public List<String> getCities();
    public List<String> getPostalCodes();
}
