package it.univr.library.Utils;

import it.univr.library.Data.Book;

import java.util.ArrayList;
import java.util.List;

public class BookGroup
{
    private List <Book> books;

    public BookGroup(List<Book> books)
    {
        this.books = books;
    }

    public BookGroup(BookGroup books)
    {
        this.books = new ArrayList<>();
        this.books.addAll(books.getBooks());
    }

    public BookGroup()
    {
        this.books = new ArrayList<>();
    }

    public boolean addBook(Book book)
    {
        return books.add(book);
    }

    public boolean addAllBooks(List<Book> books)
    {
        return this.books.addAll(books);
    }

    public List<Book> getBooks()
    {
        return books;
    }

    public List<String> getBooksISBN()
    {
        List<String> ISBNList = new ArrayList<>();

        for (Book b : books)
            ISBNList.add(b.getISBN());

        return ISBNList;
    }

    public int groupSize()
    {
        return books.size();
    }
}

/*
* class MultiFormatBooks {
    List<Book> books;
}

In questo modo cicli solo su oggetti MultiFormatBooks, alcuni hanno solo un libro e altri ne hanno di più ma a te non importa:

foreach (MultiFormatBook mb: mbooks) {
  // Start VBox

  foreach (Book b : books) {
    buildSingleBook(b);
  }

  // End VBox
}*/