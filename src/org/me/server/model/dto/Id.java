package org.me.server.model.dto;


import java.util.Comparator;
import java.util.Objects;

public class Id implements Comparable {

    private String value;

    public Id() {
        this.value = "";
    }

    public Id(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this.value.equals(""))  return false;

        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public int compareTo(Object o) {
        return this.value.compareTo(((Id)o).value);
    }
}
