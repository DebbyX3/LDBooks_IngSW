package it.univr.library;

public class Filter
{
    private Genre genre;
    private Language language;

    //null parameters by default
    public Filter(){}

    public Filter(Genre genre, Language language) {
        this.genre = genre;
        this.language = language;
    }

    public Filter(Genre genre)
    {
        this(genre, null);
    }

    public Genre getGenre()
    {
        return genre;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public boolean isGenreSetted()
    {
        return genre != null;
    }

    public boolean isLanguageSetted()
    {
        return language != null;
    }
}
