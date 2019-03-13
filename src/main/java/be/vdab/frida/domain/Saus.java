package be.vdab.frida.domain;

import java.util.Arrays;
import java.util.List;

public class Saus {
    private final long id;
    private final String naam;
    private final List<String> ingredienten;

    public Saus(long id, String naam, List<String> ingredienten) {
        this.id = id;
        this.naam = naam;
        this.ingredienten = ingredienten;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public List<String> getIngredienten() {
        return ingredienten;
    }
}
