package it.univr.library;

public class Language
{
    private String name;

    public Language(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }
}
