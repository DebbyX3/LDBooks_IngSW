package it.univr.library;

public class Language
{
    private String nome;

    public Language(String nome)
    {
        this.nome = nome;
    }

    @Override
    public String toString()
    {
        return nome;
    }

}
