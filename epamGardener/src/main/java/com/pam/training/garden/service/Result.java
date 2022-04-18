package com.pam.training.garden.service;

import java.util.Objects;

public class Result {
    private double area;
    private double waterAmount;
    private boolean areaOk;
    private boolean waterOk;

    public Result(double area, double waterAmount, boolean areaOk, boolean waterOk) {
        this.area = area;
        this.waterAmount = waterAmount;
        this.areaOk = areaOk;
        this.waterOk = waterOk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Double.compare(result.area, area) == 0 && Double.compare(result.waterAmount, waterAmount) == 0
                && areaOk == result.areaOk && waterOk == result.waterOk;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area, waterAmount, areaOk, waterOk);
    }

    @Override
    public String toString() {
        String result = String.format("***Result*** \n\nRequired area: %.1f m2 " +
                "\nWater need: %.1f l\n", area, waterAmount);
        return (areaOk && waterOk) ? result + planFeasible() : result + planNotFeasible();
    }

    private String planFeasible() {
        return "Plan is feasible in your garden! :)";
    }

    private String planNotFeasible() {
        String result = "";
        if (!areaOk) {
            result += "- Not enough area.\n";
        }
        if (!waterOk) {
            result += "- Not enough water.";
        }

        return String.format("Plan is NOT feasible in your garden! :( \n%s", result);
    }
}
