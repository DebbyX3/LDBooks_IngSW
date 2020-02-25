package it.univr.library.Model;

import it.univr.library.Data.Address;
import it.univr.library.Data.RegisteredClient;
import it.univr.library.Data.User;

public interface ModelUserInfo
{
    public String getPhoneNumber(User user);
    public Boolean doesMailAlreadyExist(RegisteredClient user);
    public Boolean doesMailUnregisteredAlreadyExist(RegisteredClient user);
    public void addUser(RegisteredClient user);
    public void addUnregisteredUser(RegisteredClient user, Address shipAddress);
    public void updateUser(RegisteredClient user);
}
