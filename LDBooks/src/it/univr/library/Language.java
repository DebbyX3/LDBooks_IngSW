package it.univr.library;

public class Language
{
    private String name;

    public Language(String name)
    {
        this.name = name.trim();
    }

    @Override
    public String toString()
    {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Language language = (Language) o;
        return name.toUpperCase().equals(language.getName().toUpperCase());
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
