package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerUserInfo
{
    private CitiesPostalCodes citiesPostalCodes;
    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE);


    public ControllerUserInfo()
    {
        ModelUserInfo DBSignUp = new ModelDatabaseUserInfo();

        citiesPostalCodes = new CitiesPostalCodes(DBSignUp.getCities(), DBSignUp.getPostalCodes());
    }

    public ObservableList<String> populateCitiesAndPostalCodesCombobox()
    {
        ObservableList<String> citiesAndPostalCodesComboboxData  = FXCollections.observableArrayList();
        List<String> citiesPostalCodesList = new ArrayList<>();

        Iterator<String> citiesIterator = citiesPostalCodes.getCities().iterator();
        Iterator<String> PostalCodesIterator = citiesPostalCodes.getPostalCodes().iterator();

        while(citiesIterator.hasNext() && PostalCodesIterator.hasNext())
        {
            citiesPostalCodesList.add(citiesIterator.next() + " - " + PostalCodesIterator.next());
        }

        citiesAndPostalCodesComboboxData.addAll(citiesPostalCodesList);

        return citiesAndPostalCodesComboboxData;
    }

    public String getCityFromCombobox(ComboBox citiesAndPostalCodesComboBox)
    {
        int selectedIndex = citiesAndPostalCodesComboBox.getSelectionModel().getSelectedIndex();

        if(selectedIndex >= citiesPostalCodes.getCities().size())
            return null;

        return citiesPostalCodes.getCities().get(selectedIndex);
    }

    public String getPostalCodeFromCombobox(ComboBox citiesAndPostalCodesComboBox)
    {
        int selectedIndex = citiesAndPostalCodesComboBox.getSelectionModel().getSelectedIndex();

        if(selectedIndex >= citiesPostalCodes.getPostalCodes().size())
            return null;

        return citiesPostalCodes.getPostalCodes().get(selectedIndex);
    }

    public List<String> getCities() {
        return citiesPostalCodes.getCities();
    }

    public List<String> getPostalCodes() {
        return citiesPostalCodes.getPostalCodes();
    }

    public boolean isTextFieldNullOrEmpty(TextField ... textField)
    {
        boolean res = false;

        for(TextField tf : textField)
            res = res || tf == null || tf.getText().isEmpty();

        return res;
    }

    public boolean isComboBoxNull(ComboBox ... comboBox)
    {
        boolean res = false;

        for(ComboBox cb : comboBox)
            res = res || cb == null;

        return res;
    }

    public RegisteredClient fetchUser(String name, String surname, String street, String houseNumber, String postalCode, String city, String phoneNumber, String mail, String psw)
    {
        List<Address> addressesList = new ArrayList<>();
        addressesList.add(new Address(street, houseNumber, city, postalCode));

        return fetchUser(name, surname, phoneNumber, mail, psw, addressesList);
    }

    public RegisteredClient fetchUser(String name, String surname, String phoneNumber, String mail, String psw, List<Address> addressesList)
    {
        RegisteredClient regUser = new RegisteredClient(name, surname, mail, psw, phoneNumber, addressesList);

        return regUser;
    }

    public boolean isAlpha(String s) {
        return s.matches("[A-zÀ-ú ']+");

    }

    public boolean isNumerical(String s) {
        return s.matches("[0-9]+");

    }

    public boolean isMailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
