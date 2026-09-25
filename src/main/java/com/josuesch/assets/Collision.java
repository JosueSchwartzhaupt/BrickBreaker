package com.josuesch.assets;

public record Collision(Placeble hit, double maxSpeed, Direction direction)
        implements Comparable<Collision> {
    @Override
    public int compareTo(Collision o) {
        return Double.compare(this.maxSpeed, o.maxSpeed);
    }
}
