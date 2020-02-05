package it.univr.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class ControllerViewProfile
{
    @FXML
    private HBox headerHBox;

    @FXML
    private VBox informationVBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private VBox addressVbox;

    @FXML
    private Button editProfileButton;

    User user;
    private Map<Book, Integer> cart;

    public ControllerViewProfile()
    {

    }

    @FXML
    private void initialize()
    {
        editProfileButton.setOnAction(this::handleEditProfileButton);
    }



    public void setUser(User user) {
        this.user = user;
    }

    public void setCart(Map<Book, Integer> cart) {
        this.cart = cart;
    }

    public void setHeader()
    {
        ControllerHeader controllerHeader = new ControllerHeader();
        controllerHeader.createHeader(user, headerHBox,cart);
    }

    private void handleEditProfileButton(ActionEvent actionEvent)
    {
        StageManager editProfileStage = new StageManager();
        editProfileStage.setStageEditProfile((Stage) editProfileButton.getScene().getWindow(), user, cart);
    }

    public void populateUserInformation()
    {
        //genero schermata fxml con le informazioni e le riempio
        View viewInformationUser = new ViewInformationUser();

        viewInformationUser.buildViewProfileInformation(userToRegisteredUser((Client) user), nameLabel, surnameLabel, phoneLabel, emailLabel, addressVbox);
    }

    private RegisteredClient userToRegisteredUser(Client user)
    {
        Model DBInformation = new ModelDatabaseUserInformation();
        RegisteredClient regUser =
                new RegisteredClient(user.getName(), user.getSurname(), user.getEmail(),
                        user.getPassword(), user.getPhoneNumber(), DBInformation.getAddressesRegisteredUser(user));

        return regUser;
    }


}
