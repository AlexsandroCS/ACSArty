package org.example.services;

import org.example.models.Nation;

public class CalculationService {
    public double calculateDistance(int milValue, Nation nation) {
        switch (nation) {
            case SOVIETICO:
                return calculateSovietDistance(milValue);
            case BRITANICO:
                return calculateBritishDistance(milValue);
            default:
                return calculateAlliedAxisDistance(milValue);
        }
    }

    private double calculateAlliedAxisDistance(int mil) {
        double m = -0.237035714285714;
        double b = 1001.46547619048;
        int xmin = 100;
        int xmax = 1600;

        for (int i = xmin; i < xmax; i++) {
            if (Math.round(m * i + b) == mil) {
                return i;
            }
        }
        return 0;
    }

    private double calculateSovietDistance(int mil) {
        double m = -0.2133;
        double b = 1141.28;
        return Math.round((mil - b) / m);
    }

    private double calculateBritishDistance(int mil) {
        double m = -0.1773;
        double b = 550.68;
        return Math.round((mil - b) / m);
    }
}