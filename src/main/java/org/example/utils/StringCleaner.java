package org.example.utils;

public class StringCleaner {
    public String cleanMilString(String milString) {
        String cleaned = milString;
        cleaned = cleaned.replace("S", "5");
        cleaned = cleaned.replace("O", "0");
        cleaned = cleaned.replace("B", "8");
        cleaned = cleaned.replace("A", "4");
        cleaned = cleaned.replace("I", "1");
        return cleaned;
    }
}