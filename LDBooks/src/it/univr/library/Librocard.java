package it.univr.library;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Librocard
{
    private String numberID;
    private Integer totalPoints;
    private Long issueDate;
    private String email;

    public Librocard(){}

    public Librocard(String numberID, Integer totalPoints, Long issueDate, String email) {
        this.numberID = numberID;
        this.totalPoints = totalPoints;
        this.issueDate = issueDate;
        this.email = email.trim();
    }

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateFromUnixTime()
    {
        return new Date((long) issueDate*1000);
    }

    public String LibroCardDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        return dateFormat.format(this.getDateFromUnixTime());
    }
}
