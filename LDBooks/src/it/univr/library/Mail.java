package it.univr.library;

import java.io.File;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Add to Gradle dependencies (build.gradle)
 * // https://mvnrepository.com/artifact/com.mashape.unirest/unirest-java
 *   compile group: 'com.mashape.unirest', name: 'unirest-java', version: '1.4.9'
 */

public class Mail {
    public static void main(String[] args) throws UnirestException {
        /*HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + "sandbox729683b30fa04621b8ec7fbc7204958e.mailgun.org" + "/messages")
            .basicAuth("api", "fead161b1649c0020c8eea9c57954c3b-52b6835e-bd642a2a")
            .field("from", "Mailgun Sandbox <postmaster@sandbox729683b30fa04621b8ec7fbc7204958e.mailgun.org>")
            .field("to", "Receiver <luca.marzari@studenti.univr.it>")
            .field("subject", "hello")
            .field("text", "testing")
            .asJson();

        System.out.println(request.getBody());*/

        HttpResponse<JsonNode> request = Unirest.post("https://testdb-01e5.restdb.io/mail")
                .header("x-apikey", "e10e30c781eaf64b743a33c378f7ac0811997")
                .field("to", "luca.marzari@studenti.univr.it")
                .field("subject", "Your order is ready")
                .field("html", "<p>Lorem ipsum dolor..., <b>vel</b> luctu.</p>")
                .field("company", "Acme Inc")
                .field("sendername", "Acme customer support")
                .asJson();

        System.out.println(request.getBody());
    }
}