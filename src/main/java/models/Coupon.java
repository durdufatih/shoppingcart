package models;

import java.util.Objects;

public class Coupon {
    private double minAmount;
    private int rate;
    private DiscountType discountType;

    public Coupon(double minAmount, int rate, DiscountType discountType) {
        this.minAmount = minAmount;
        this.rate = rate;
        this.discountType = discountType;
    }

    public Double getDiscount(Double cartAmount) {
        if (Objects.isNull(cartAmount))
            throw new IllegalArgumentException();
        if (cartAmount >= this.minAmount) {
            if (discountType == DiscountType.RATE)
                return (cartAmount * rate) / 100;
            else if (discountType == DiscountType.AMOUNT)
                return Double.valueOf(rate);
            else
                throw new IllegalArgumentException();
        } else
            return 0d;
    }
}
