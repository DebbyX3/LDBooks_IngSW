package it.univr.library.Model;

import it.univr.library.Language;

import java.util.ArrayList;

public interface ModelLanguage
{
    public ArrayList<Language> getLanguages();
    public void addNewLanguage(Language language);
}
