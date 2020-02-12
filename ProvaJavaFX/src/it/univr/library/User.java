package it.univr.library;

import java.util.List;

public abstract class User
{
    private String name;
    private String surname;
    private String email;
    private String password;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;

        normalizeUser();
    }

    protected User() { //keep this!
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private void normalizeUser()
    {
        if(name != null)
            name = name.trim();
        if(surname != null)
            surname = surname.trim();
        if(email != null)
            email = email.trim();
    }

    @Override
    public String toString()
    {
        return name + " " + surname + ", " + email + ", " + password;
    }


    //public abstract List<Address> getAddresses();
}
