package it.univr.library;

import java.util.ArrayList;

public interface Model
{
    public default ArrayList<Book> getBooks() { return null; }

    public default ArrayList<Book> getBooks(Filter filter) { return null; }

    public default ArrayList<Object> getCharts()
    {
        return null;
    }

    public default ArrayList<Genre> getGenres()
    {
        return null;
    }

    public default  ArrayList<Language> getLanguages(){ return null; }

    public default User getUser(User testUser){ return null;}

    public default ArrayList<Charts> getCharts(Filter filter)
    {
        return null;
    }

    public default RegisteredUser getRegisteredUser(User testUser){ return null;}
}