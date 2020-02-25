package it.univr.library.Model;

import it.univr.library.Data.PublishingHouse;

import java.util.ArrayList;

public interface ModelPublishingHouse
{
    public ArrayList<PublishingHouse> getPublishingHouses();
    public void addNewPublishingHouse(PublishingHouse newPublishingHouse);
}
