package it.univr.library;

public class Format implements Comparable<Format>
{
    private String name;

    public Format(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Format format = (Format) o;
        return name.toUpperCase().equals(format.getName().toUpperCase());
    }

    @Override
    public int compareTo(Format o)
    {
        return name.compareTo(o.getName());
    }
}
