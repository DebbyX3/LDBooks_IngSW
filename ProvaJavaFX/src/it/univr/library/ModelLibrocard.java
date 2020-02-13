package it.univr.library;

import java.util.ArrayList;

public interface ModelLibrocard
{
    public ArrayList<String> getMailsLibroCards();
    public ArrayList<Librocard> getAllLibroCards();
    public ArrayList<Librocard> getSpecificLibroCard(String mail);
}
