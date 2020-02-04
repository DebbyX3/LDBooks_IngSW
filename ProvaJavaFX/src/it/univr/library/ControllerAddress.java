package it.univr.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControllerAddress
{
    private CitiesPostalCodes citiesPostalCodes;

    public ControllerAddress()
    {
        Model DBSignUp = new ModelDatabaseSignUp();

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
        return citiesPostalCodes.getCities().get(citiesAndPostalCodesComboBox.getSelectionModel().getSelectedIndex());
    }

    public String getPostalCodeFromCombobox(ComboBox citiesAndPostalCodesComboBox)
    {
        return citiesPostalCodes.getPostalCodes().get(citiesAndPostalCodesComboBox.getSelectionModel().getSelectedIndex());
    }

    public List<String> getCities() {
        return citiesPostalCodes.getCities();
    }

    public List<String> getPostalCodes() {
        return citiesPostalCodes.getPostalCodes();
    }
}
