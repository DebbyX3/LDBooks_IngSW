package it.univr.library;

public class Genre
{
    private String name;

    public Genre(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() { return name; }
}
