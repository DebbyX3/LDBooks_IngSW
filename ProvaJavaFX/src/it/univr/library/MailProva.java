package it.univr.library;

import java.io.File;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.Alert;

public class MailProva
{
    public void sendMail(String mail, String name, String surname, String subject, String message)
    {
        try
        {
            /*HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox729683b30fa04621b8ec7fbc7204958e.mailgun.org" + "/messages")
                    .basicAuth("api", "fead161b1649c0020c8eea9c57954c3b-52b6835e-bd642a2a")
                    .field("from", "LD Books <postmaster@sandbox729683b30fa04621b8ec7fbc7204958e.mailgun.org>")
                    .field("to", name + " " + surname + " " + "<" + mail + ">")
                    .field("subject", subject)
                    .field("text", message)
                    .asJson();*/

            HttpResponse<JsonNode> request = Unirest.post("https://testdb-01e5.restdb.io/mail")
                    .header("x-apikey", "e10e30c781eaf64b743a33c378f7ac0811997")
                    .field("to", mail)
                    .field("subject", subject)
                    .field("html", "<p>" + message + "</p>")
                    .field("company", "LD Books")
                    .field("sendername", "LD Books")
                    .asJson();

            System.out.println(request.getBody());
        }
        catch(UnirestException e)
        {
            displayAlert("An error occurred while sending the confirmation mail");
        }
    }

    private void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Mail error");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
