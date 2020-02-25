package it.univr.library.Model;

import it.univr.library.Data.RegisteredClient;
import it.univr.library.Data.User;

public interface ModelRegisteredUser
{
    public RegisteredClient getRegisteredClient(User testUser);
}
