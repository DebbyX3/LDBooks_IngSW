package it.univr.library.Model;

import it.univr.library.Data.Author;

import java.util.ArrayList;
import java.util.List;

public interface ModelAuthor
{
    public ArrayList<Author> getAuthors();
    public ArrayList<Author> getAuthorsForSpecificBook(String isbn);
    public void addNewAuthor(String newNameAuthor, String newSurnameAuthor);
    public int getAuthorID(String authorName, String authorSurname);
    public void linkBookToAuthors(int idAuthor, String isbn);
    public void deleteLinkBookToAuthors(int idAuthor, String isbn);
    public ArrayList<Author> createArrayListAuthors(List<String> idNameSurnameAuthors);
}
