package it.univr.library;

public interface ModelUserLibrocard
{
    public Librocard getLibrocardInformation(User user);
    public void updateLibroCardPoints(Order order);
}
