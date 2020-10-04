package models;

import java.util.Objects;

public class Campaign {
    private Category category;
    private Double rate;
    private Integer quantity;
    private DiscountType discountType;

    public Campaign(Category category, Double rate, Integer quantity, DiscountType discountType) {
        this.category = category;
        this.rate = rate;
        this.quantity = quantity;
        this.discountType = discountType;
    }


    public Double discount(Category category, Integer quantity, Double totalPrice) {
        double discountByCategory = 0;
        if (Objects.isNull(category) || Objects.isNull(quantity) || Objects.isNull(totalPrice)) {
            throw new IllegalArgumentException();
        }
        if (!category.equals(this.category)) {
            return 0d;
        } else {
            if (quantity >= this.quantity) {
                if (this.discountType == DiscountType.RATE) {
                    discountByCategory = ((totalPrice * rate) / 100);
                } else if (this.discountType == DiscountType.AMOUNT) {
                    return rate;
                }
            } else return 0d;
        }
        return discountByCategory;
    }

}
