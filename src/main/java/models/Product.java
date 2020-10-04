package models;

public class Product {

    private String title;
    private Double price;
    private Category category;

    public Product(String title, Double price, Category category) {
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }


    public Double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }


}
