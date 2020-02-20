package it.univr.library.Model;

import it.univr.library.Address;
import it.univr.library.RegisteredClient;
import it.univr.library.User;

import java.util.List;

public interface ModelUserInfo
{
    public String getPhoneNumber(User user);
    public Boolean doesMailAlreadyExist(RegisteredClient user);
    public Boolean doesMailUnregisteredAlreadyExist(RegisteredClient user);
    public void addUser(RegisteredClient user);
    public void addUnregisteredUser(RegisteredClient user, Address shipAddress);
    public void updateUser(RegisteredClient user);
}
