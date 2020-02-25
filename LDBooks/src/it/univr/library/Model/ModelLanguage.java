package it.univr.library.Model;

import it.univr.library.Data.Language;

import java.util.ArrayList;

public interface ModelLanguage
{
    public ArrayList<Language> getLanguages();
    public void addNewLanguage(Language language);
}
