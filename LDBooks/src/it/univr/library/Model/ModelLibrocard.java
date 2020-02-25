package it.univr.library.Model;

import it.univr.library.Data.Librocard;

import java.util.ArrayList;

public interface ModelLibrocard
{
    public ArrayList<String> getMailsLibroCards();
    public ArrayList<Librocard> getAllLibroCards();
    public ArrayList<Librocard> getSpecificLibroCard(String mail);
}
