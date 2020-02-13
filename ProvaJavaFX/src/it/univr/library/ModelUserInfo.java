package it.univr.library;

import java.util.List;

public interface ModelUserInfo
{
    public String getPhoneNumber(User user);
    public List<String> getCities();
    public List<String> getPostalCodes();
    public Boolean doesMailAlreadyExist(RegisteredClient user);
    public Boolean doesMailUnregisteredAlreadyExist(RegisteredClient user);
    public void addUser(RegisteredClient user);
    public void addUnregisteredUser(RegisteredClient user, Address shipAddress);
    public void updateUser(RegisteredClient user);
    public void addAddress(RegisteredClient user, Address address);
    public void unlinkAddressFromUser(RegisteredClient user, Address addressToDelete);
    public void createLibroCard(RegisteredClient user);
}
