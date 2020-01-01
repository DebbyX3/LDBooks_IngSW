package it.univr.provaAbstract;

import it.univr.library.Address;
import it.univr.library.RegisteredClient;

public class ProvaMain
{
    public static void main(String[] Args)
    {
        RegisteredClient user = new RegisteredClient("Sumo", "Culo", "p.d@f.it", "qwerty", "0123545",
                                                     new Address("Via culo", "35B", "Concamarise", "32012"));
        System.out.println(user);
    }
}
