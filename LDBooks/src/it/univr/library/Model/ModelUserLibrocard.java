package it.univr.library.Model;

import it.univr.library.Data.Librocard;
import it.univr.library.Data.Order;
import it.univr.library.Data.RegisteredClient;
import it.univr.library.Data.User;

public interface ModelUserLibrocard
{
    public Librocard getLibrocardInformation(User user);
    public void updateLibroCardPoints(Order order);
    public void createLibroCard(RegisteredClient user);
}
