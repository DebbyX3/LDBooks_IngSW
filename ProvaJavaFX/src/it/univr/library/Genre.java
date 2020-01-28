package it.univr.library;

import java.util.Objects;

public class Genre implements Comparable<Genre>
{
    private String name;

    public Genre(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Genre genre = (Genre) o;
        return name.equals(genre.getName());
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    @Override
    public int compareTo(Genre o) {
        return name.compareTo(o.name);
    }
}
