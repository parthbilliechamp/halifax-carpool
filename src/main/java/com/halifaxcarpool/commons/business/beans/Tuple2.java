package com.halifaxcarpool.commons.business.beans;

import java.util.Objects;

public class Tuple2 {
    String source;
    String destination;

    public Tuple2(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple2 tuple2 = (Tuple2) o;
        return Objects.equals(source, tuple2.source) && Objects.equals(destination, tuple2.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}