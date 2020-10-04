package models;

import java.util.Map;
import java.util.Objects;

public class DeliveryCostCalculator {
    private final double FIXED_COST = 2.99;
    private double costPerDelivery;
    private double costPerProduct;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct) {
        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
    }

    public double calculateFor(Map<Category, Double> amountCategoryMap, Map<Product, Integer> productMap) {
        if (Objects.isNull(amountCategoryMap) || Objects.isNull(productMap) || amountCategoryMap.isEmpty() || productMap.isEmpty())
            throw new IllegalArgumentException();
        else {
            return (costPerDelivery * amountCategoryMap.keySet().size()) + (costPerProduct * (productMap.keySet().stream().mapToInt(productMap::get).sum())) + FIXED_COST;
        }
    }
}
