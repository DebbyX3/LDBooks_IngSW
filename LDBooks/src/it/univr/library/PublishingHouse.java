package it.univr.library;

public class PublishingHouse implements Comparable<PublishingHouse>
{
    private String name;

    public PublishingHouse(String name) {
        this.name = name.trim();
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

        PublishingHouse p = (PublishingHouse) o;
        return name.toUpperCase().equals(p.getName().toUpperCase());
    }

    @Override
    public int compareTo(PublishingHouse o) {
        return name.compareTo(o.getName());
    }
}


