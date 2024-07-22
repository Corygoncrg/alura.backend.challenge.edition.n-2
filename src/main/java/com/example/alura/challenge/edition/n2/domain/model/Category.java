package com.example.alura.challenge.edition.n2.domain.model;

public enum Category {
    FOOD, HEALTH,HOME,
    TRANSPORT,EDUCATION,LEISURE,
    UNFORESEEN,OTHER;

    public static Category fromString(String categoryName) {
        if (categoryName == null) {
            throw new IllegalArgumentException("Category name cannot be null");
        }

        return switch (categoryName.toUpperCase()) {
            case "FOOD" -> FOOD;
            case "HEALTH" -> HEALTH;
            case "HOME" -> HOME;
            case "TRANSPORT" -> TRANSPORT;
            case "EDUCATION" -> EDUCATION;
            case "LEISURE" -> LEISURE;
            case "UNFORESEEN" -> UNFORESEEN;
            case "OTHER" -> OTHER;
            default -> throw new IllegalArgumentException("Unknown category: " + categoryName);
        };
    }
}
