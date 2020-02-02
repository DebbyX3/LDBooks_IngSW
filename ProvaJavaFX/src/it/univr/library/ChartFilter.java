package it.univr.library;

public class ChartFilter {
    private Genre genre;
    private Category category;

    //null parameters by default
    public ChartFilter(){}

    public ChartFilter(Genre genre, Category category) {
        this.genre = genre;
        this.category = category;
    }

    public ChartFilter(Genre genre)
    {
        this(genre, null);
    }

    public  ChartFilter(Category category){
        this(null, category);
    }

    public Genre getGenre()
    {
        return genre;
    }

    public Category getCategory()
    {
        return category;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    public void setCategory(Category category)
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
