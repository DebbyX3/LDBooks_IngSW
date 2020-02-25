package it.univr.library.Model;

import it.univr.library.Data.Category;
import it.univr.library.Utils.ChartFilter;
import it.univr.library.Data.Charts;
import it.univr.library.Data.Genre;

import java.util.ArrayList;

public interface ModelCharts
{
    public ArrayList<Charts> getGeneralCharts();
    public ArrayList<Charts> getChartsForGenre(ChartFilter filter);
    public ArrayList<Charts> getChartsForCategory(ChartFilter filter);
    public ArrayList<Charts> getChartsForCategoryAndGenre(ChartFilter filter);
    public void updateChartsGenreAllCategoryAll(Charts bookToUpdateInCharts);
    public void updateChartsGenreSelectedCategoryAll(Charts bookToUpdateInCharts);
    public void updateChartsGenreAllCategorySelected(Charts bookToUpdateInCharts);
    public void updateChartsGenreSelectedCategorySelected(Charts bookToUpdateInCharts);
    public void insertBookOnTheChartsAllGenreAllCategory(Charts bookToInsertOnTheCharts);
    public void insertBookOnTheChartsSelectedGenreAllCategory(Charts bookToInsertOnTheCharts);
    public void insertBookOnTheChartsAllGenreSelectedCategory(Charts bookToInsertOnTheCharts);
    public void insertBookOnTheChartsSelectedGenreSelectedCategory(Charts bookToInsertOnTheCharts);
    public void deleteBookFromChartsAllGenreAllCategory(String isbn);
    public void deleteBookFromChartsSelectedGenreAllCategory(String isbn, Genre genre);
    public void deleteBookFromChartsAllGenreSelectedCategory(String isbn, Category category);
    public void deleteBookFromChartsSelectedGenreSelectedCategory(String isbn, Genre genre, Category category);
    public ArrayList<Category> getCategory();
}
