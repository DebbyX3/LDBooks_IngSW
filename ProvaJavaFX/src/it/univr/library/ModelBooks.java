package it.univr.library;

import java.util.ArrayList;

public interface ModelBooks {
    public ArrayList<Book> getAllBooks();
    public ArrayList<Book> getAllBooks(Filter filter);
    public ArrayList<Book> getAllBooks(ChartFilter filter);
    public Book getSpecificBooksForGenre(String isbn);
    public ArrayList<Book> getSpecificBooksForGenre(Genre genre);
    public void addNewBookToDB(Book book);
    public void updateBook(Book book);
    public void updateQuantityAvailableBook(int quantity, String isbn);

}
