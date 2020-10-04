package models;

public class Category {

    private Category parentCategory;
    private String title;

    public Category(Category parentCategory, String title) {
        this.parentCategory = parentCategory;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
