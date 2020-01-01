package it.univr.library;

//Prima della modifica
/*public class User {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;


    public String getNameQuery() {
        return name;
    }

    public String getName() {
        return name.replaceAll("''", "'");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnameQuery() {
        return surname;
    }

    public String getSurname() {
        return surname.replaceAll("''", "'");
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
*/

//Nuova modifica
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
    }

    protected User() {
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

    @Override
    public String toString()
    {
        return name + " " + surname + ", " + email + ", " + password;
    }
}
