package it.univr.library;

public class ChartFilter {
    private Genre genre;
    private String category;

    //null parameters by default
    public ChartFilter(){}

    public ChartFilter(Genre genre, String category) {
        this.genre = genre;
        this.category = category;
    }

    public ChartFilter(Genre genre)
    {
        this(genre, null);
    }

    public Genre getGenre()
    {
        return genre;
    }

    public String getCategory()
    {
        return category;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public boolean isGenreSetted()
    {
        return genre != null;
    }

    public boolean isCategorySetted()
    {
        return category != null;
    }
}
