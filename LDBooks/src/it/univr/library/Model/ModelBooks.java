package it.univr.library.Model;

import it.univr.library.Data.Book;
import it.univr.library.Utils.ChartFilter;
import it.univr.library.Utils.Filter;

import java.util.ArrayList;

public interface ModelBooks {
    public ArrayList<Book> getAllBooks();
    public ArrayList<Book> getAllBooks(Filter filter);
    public ArrayList<Book> getAllBooks(ChartFilter filter);
    public Book getSpecificBook(String isbn);
    public ArrayList<Book> getSearchedBook(String title);
    public void addNewBook(Book book);
    public void updateBook(Book book);
    public void updateAvailableQuantityBook(int quantity, String isbn);
    public void flagBookAsNotValid(String ISBN);
    public boolean doesISBNAlreadyExists(String ISBN);
}
