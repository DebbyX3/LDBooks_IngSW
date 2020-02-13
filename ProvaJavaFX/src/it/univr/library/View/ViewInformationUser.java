package it.univr.library.View;

import it.univr.library.AddressFields;
import it.univr.library.RegisteredClient;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public interface ViewInformationUser
{
    public void buildViewProfileInformation(RegisteredClient registeredUser, Label nameLabel, Label surnameLabel, Label phoneLabel, Label emailLabel, VBox addressVbox);
    public void addEmptyAddressEditProfile(VBox addressVBox);
    public void buildEditProfileInformation(RegisteredClient registeredUser, TextField nameTextField, TextField surnameTextField, TextField phoneTextField, TextField passwordTextField, Label mailLabel, VBox addressVBox);
    public List<AddressFields> getAddressFieldsList();
}
