package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class AddressFields
{
    private TextField streetTextField;
    private TextField houseNumberTextField;

    private ComboBox citiesAndPostalCodesComboBox;
    private ObservableList<String> citiesAndPostalCodesComboboxData;

    ControllerAddress controllerAddress = new ControllerAddress();

    public AddressFields(Address address)
    {
        this(address.getStreet(), address.getHouseNumber(), address.getCity(), address.getPostalCode());
    }

    public AddressFields(String street, String houseNumber, String city, String postalCode)
    {
        streetTextField = new TextField(street);
        houseNumberTextField = new TextField(houseNumber);
        citiesAndPostalCodesComboboxData = controllerAddress.populateCitiesAndPostalCodesCombobox();

        citiesAndPostalCodesComboBox = new ComboBox();
        citiesAndPostalCodesComboBox.setItems(citiesAndPostalCodesComboboxData);
        citiesAndPostalCodesComboBox.getSelectionModel().select(controllerAddress.getCities().indexOf(city));
    }

    public Address toAddress()
    {
        return new Address(streetTextField.getText(), houseNumberTextField.getText(),
                controllerAddress.getCityFromCombobox(citiesAndPostalCodesComboBox),
                controllerAddress.getPostalCodeFromCombobox(citiesAndPostalCodesComboBox));
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
