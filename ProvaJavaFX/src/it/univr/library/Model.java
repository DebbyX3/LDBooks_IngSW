package it.univr.library;

import java.util.ArrayList;

public interface Model
{
    public default ArrayList<Book> getBooks() { return null; }

    public default ArrayList<Object> getCharts()
    {
        return null;
    }

    public default ArrayList<Genre> getGenres()
    {
        return null;
    }
}
