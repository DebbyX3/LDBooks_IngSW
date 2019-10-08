package it.univr.library;

public class Genre
{
    private String nome;

    public Genre(String nome)
    {
        this.nome = nome;
    }

    @Override
    public String toString()
    {
        return nome;
    }
}
