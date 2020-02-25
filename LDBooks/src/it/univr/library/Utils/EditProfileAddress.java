package it.univr.library.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * This class is used to contain Textfields and Combobox in the EditProfile page.
 * It allows to store them in a list and to make it accessible from the ControllerEditProfile.
 * When the user clicks the edit button, the controller access to the list and transforms it into
 * an ArrayList of Addresses.
 */
public class EditProfileAddress
{
    private TextField streetTextField;
    private TextField houseNumberTextField;
    private ComboBox citiesAndCapsComboBox;
    private ObservableList<String> citiesAndCapsComboboxData = FXCollections.observableArrayList();
}
