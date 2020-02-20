package it.univr.library.Model;

import it.univr.library.Format;

import java.util.ArrayList;

public interface ModelFormat
{
    public ArrayList<Format> getFormats();
    public void addNewFormat(Format newFormat);
}
