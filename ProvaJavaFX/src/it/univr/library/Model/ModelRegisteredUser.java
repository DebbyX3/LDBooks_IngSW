package it.univr.library.Model;

import it.univr.library.Manager;
import it.univr.library.RegisteredClient;
import it.univr.library.User;

public interface ModelRegisteredUser
{
    public RegisteredClient getRegisteredClient(User testUser);
}
