package it.univr.library.Model;

import it.univr.library.Librocard;
import it.univr.library.Order;
import it.univr.library.RegisteredClient;
import it.univr.library.User;

public interface ModelUserLibrocard
{
    public Librocard getLibrocardInformation(User user);
    public void updateLibroCardPoints(Order order);
    public void createLibroCard(RegisteredClient user);
}
