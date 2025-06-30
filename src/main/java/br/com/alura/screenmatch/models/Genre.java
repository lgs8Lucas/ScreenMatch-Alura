package br.com.alura.screenmatch.models;

public enum Genre {
    ACTION("Action", "Ação"),
    COMEDY("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    ROMANCE("Romance", "Romance"),
    ADVENTURE("Adventure", "Aventura"),
    DRAMA("Drama", "Drama"),
    FANTASY("Fantasy", "Fantasia"),
    HORROR("Horror", "Horror"),
    MUSICAL("Musical", "Musical"),
    MYSTERY("Mystery", "Mistério"),
    ANIMATION("Animation", "Animação");

    private String OmbdCategory;
    private String PortugueseCategory;

    Genre(String OmbdCategory, String PortugueseCategory) {
        this.OmbdCategory = OmbdCategory;
        this.PortugueseCategory = PortugueseCategory;
    }

    public static Genre fromString(String text) {
        for (Genre g : Genre.values()) {
            if (g.OmbdCategory.equalsIgnoreCase(text)) {
                return g;
            }
        }
        throw new IllegalArgumentException("No categories found for the given string: " + text);
    }

    public static Genre fromPortuguese(String text) {
        for (Genre g : Genre.values()) {
            if (g.PortugueseCategory.equalsIgnoreCase(text)) {
                return g;
            }
        }
        throw new IllegalArgumentException("No categories found for the given string: " + text);
    }

}
