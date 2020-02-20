package it.univr.library;

import it.univr.library.Controller.ControllerUserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddressFields
{
    private TextField streetTextField;
    private TextField houseNumberTextField;

    private ComboBox citiesAndPostalCodesComboBox;
    private ObservableList<String> citiesAndPostalCodesComboboxData = FXCollections.observableArrayList();

    ControllerUserInfo controllerUserInfo = new ControllerUserInfo();

    public AddressFields(Address address)
    {
        this(address.getStreet(), address.getHouseNumber(), address.getCity(), address.getPostalCode());
    }

    public AddressFields(String street, String houseNumber, String city, String postalCode)
    {
        streetTextField = new TextField(street);
        houseNumberTextField = new TextField(houseNumber);
        citiesAndPostalCodesComboboxData = controllerUserInfo.populateCitiesAndPostalCodesCombobox();
        citiesAndPostalCodesComboboxData.add("Empty");

        citiesAndPostalCodesComboBox = new ComboBox();
        citiesAndPostalCodesComboBox.setItems(citiesAndPostalCodesComboboxData);

        int indexCity = controllerUserInfo.getCities().indexOf(city);

        if(indexCity < 0 || indexCity > controllerUserInfo.getCities().size()-1)
            citiesAndPostalCodesComboBox.getSelectionModel().selectLast();
        else
            citiesAndPostalCodesComboBox.getSelectionModel().select(indexCity);
    }

    public Address toAddress()
    {
        return new Address(streetTextField.getText(), houseNumberTextField.getText(),
                controllerUserInfo.getCityFromCombobox(citiesAndPostalCodesComboBox),
                controllerUserInfo.getPostalCodeFromCombobox(citiesAndPostalCodesComboBox));
    }

    public TextField getStreetTextField() {
        return streetTextField;
    }

    public TextField getHouseNumberTextField() {
        return houseNumberTextField;
    }

    public ComboBox getCitiesAndPostalCodesComboBox() {
        return citiesAndPostalCodesComboBox;
    }
}
