package models;

public class Category {

    private Category parentCategory;
    private String name;

    public Category(Category parentCategory, String name) {
        this.parentCategory = parentCategory;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
