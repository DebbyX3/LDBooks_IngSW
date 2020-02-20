package it.univr.library;

import it.univr.library.Address;
import it.univr.library.Book;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order
{
    private Address address;
    private ArrayList<Book> books = new ArrayList<>();
    private String code;
    private Long date;
    private BigDecimal totalPrice;
    private int balancePoints;
    private String paymentType;
    private String emailNotRegisteredUser;
    private String emailRegisteredUser;
    private BigDecimal shippingCost;
    private String status;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBalancePoints() {
        return balancePoints;
    }

    public void setBalancePoints(int balancePoints) {
        this.balancePoints = balancePoints;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getEmailNotRegisteredUser() {
        return emailNotRegisteredUser;
    }

    public void setEmailNotRegisteredUser(String emailNotRegisteredUser) {
        this.emailNotRegisteredUser = emailNotRegisteredUser;
    }

    public String getEmailRegisteredUser() {
        return emailRegisteredUser;
    }

    public void setEmailRegisteredUser(String emailRegisteredUser) {
        this.emailRegisteredUser = emailRegisteredUser;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateFromUnixTime()
    {
        return new Date((long) this.getDate()*1000);
    }

    public String UnixDateToString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        return dateFormat.format(this.getDateFromUnixTime());
    }
}
