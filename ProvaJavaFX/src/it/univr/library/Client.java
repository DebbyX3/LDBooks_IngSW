package it.univr.library;

public class Client extends User
{
    private String phoneNumber;

    public Client(String name, String surname, String email, String password, String phoneNumber)
    {
        super(name, surname, email, password);
        this.phoneNumber = phoneNumber;
    }

    public Client(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString()
    {
        return super.toString() + ", " + phoneNumber;
    }
}
