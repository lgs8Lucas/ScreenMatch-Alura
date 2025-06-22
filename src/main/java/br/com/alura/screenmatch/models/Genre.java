package br.com.alura.screenmatch.models;

public enum Genre {
    ACTION("Action"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    ROMANCE("Romance"),
    ADVENTURE("Adventure"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    HORROR("Horror"),
    MUSICAL("Musical"),
    MYSTERY("Mystery"),
    ANIMATION("Animation"),
    THRILLER("Thriller");

    private String OmbdCategory;

    Genre(String OmbdCategory) {
        this.OmbdCategory = OmbdCategory;
    }

    public static Genre fromString(String text) {
        for (Genre g : Genre.values()) {
            if (g.OmbdCategory.equalsIgnoreCase(text)) {
                return g;
            }
        }
        throw new IllegalArgumentException("No categories found for the given string: " + text);
    }

}
