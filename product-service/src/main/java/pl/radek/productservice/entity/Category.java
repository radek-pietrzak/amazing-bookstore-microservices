package pl.radek.productservice.entity;

public enum Category {

    FICTION("Fiction"),
    NON_FICTION("Non-fiction"),
    LITERATURE("Literature"),
    HISTORY("History"),
    SCIENCE("Science"),
    SELF_HELP("Self-help"),
    BUSINESS_AND_ECONOMICS("Business and economics"),
    TRAVEL("Travel"),
    BIOGRAPHY_AND_MEMOIR("Biography and memoir"),
    PHILOSOPHY("Philosophy"),
    RELIGION_AND_SPIRITUALITY("Religion and spirituality"),
    HEALTH_AND_WELLNESS("Health and wellness"),
    ART_AND_DESIGN("Art and design"),
    COOKING_AND_FOOD("Cooking and food"),
    CHILDREN_S_BOOKS("Children's books"),
    YOUNG_ADULT("Young adult"),
    MYSTERY_AND_THRILLER("Mystery and thriller"),
    ROMANCE("Romance"),
    SCIENCE_FICTION_AND_FANTASY("Science fiction and fantasy"),
    COMICS_AND_GRAPHIC_NOVELS("Comics and graphic novels");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
