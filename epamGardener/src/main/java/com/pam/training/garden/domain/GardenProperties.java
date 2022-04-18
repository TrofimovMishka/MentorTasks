package com.pam.training.garden.domain;

import java.util.Objects;

public class GardenProperties {
    private double area;
    private double waterSupply;

    public GardenProperties(double area, double waterSupply) {
        this.area = area;
        this.waterSupply = waterSupply;
    }

    public double getArea() {
        return area;
    }

    public double getWaterSupply() {
        return waterSupply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GardenProperties that = (GardenProperties) o;
        return Double.compare(that.area, area) == 0
                && Double.compare(that.waterSupply, waterSupply) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, waterSupply);
    }

    @Override
    public String toString() {
        return "GardenProperties{" +
                "area=" + area +
                ", waterSupply=" + waterSupply +
                '}';
    }
}
