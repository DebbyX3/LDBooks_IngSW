package it.univr.library.Model;

import it.univr.library.Book;
import it.univr.library.Order;
import it.univr.library.User;

import java.util.ArrayList;

public interface ModelOrder
{
    public ArrayList<Order> getOrders(User user);
    public ArrayList<Order> getOrderNotRegisteresUser(String mailNotRegUser, String orderCode);
    public ArrayList<Order> getSpecificMailOrders(String mail);
    public ArrayList<String> getMailsOrders();
    public ArrayList<Order> getAllOrders();
    public void addNewOrderRegisteredClient(Order order);
    public void addNewOrderNotRegisteredClient(Order order);
    public Integer getLastOrderCode();
    public void linkBookToOrder(Book book, int orderCode, int quantity);
    public int getQuantityOrderSingleBook(Order order, Book book);
}
