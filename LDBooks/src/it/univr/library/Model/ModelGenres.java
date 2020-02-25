package it.univr.library.Model;

import it.univr.library.Data.Genre;

import java.util.ArrayList;

public interface ModelGenres
{
    public ArrayList<Genre> getGenres();
    public void addNewGenre(String newGenre);
}
