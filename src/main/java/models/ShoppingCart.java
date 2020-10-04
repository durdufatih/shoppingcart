package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ShoppingCart {
    private Logger logger = Logger.getLogger(ShoppingCart.class.getName());
    private double discountTotal = 0;
    private double discountCampaign = 0;
    private double discountCoupon = 0;
    private Map<Product, Integer> itemList = new HashMap<>();
    private DeliveryCostCalculator deliveryCostCalculator;

    public ShoppingCart(DeliveryCostCalculator deliveryCostCalculator) {
        this.deliveryCostCalculator = deliveryCostCalculator;
    }

    /**
     * This method give to abilty for add to shoping cart.
     *
     * @param product  Added item
     * @param quantity this is count of product
     * @return Map<Product
                    **/
    public Map<Product, Integer> addItem(Product product, int quantity) {

        if (Objects.isNull(product) || quantity <= 0)
            throw new IllegalArgumentException();
        if (Objects.nonNull(itemList.get(product))) {
            logger.info("Adding to Quantity...");
            itemList.put(product, itemList.get(product) + quantity);
        } else {
            logger.info("Adding New Item");
            itemList.put(product, quantity);
        }
        return itemList;
    }

    public Map<Product, Integer> getItemList() {
        return itemList;
    }

    /**
     * This metod give allow to make discount at shopping cart.This method works by category and This method find the best discount at the campaigns.
     *
     * @param campaigns this is campaign List You can give how much campaign do you want to add this cart.
     * @return <bold> Double result of max discount</bold>
     */
    public Double applyDiscounts(Campaign... campaigns) {
        double maxDiscount = 0;
        Map<Category, Double> categoryPriceMap = getAmountByCategory();
        logger.info("Getting category Total Prices...");

        for (Category category : categoryPriceMap.keySet()) {
            for (Campaign campaign : campaigns) {
                Integer count = itemList.keySet().stream().filter(item -> item.getCategory().equals(category)).mapToInt(item -> itemList.get(item)).sum();
                logger.info("Calculated Total Count");
                Double discont = campaign.discount(category, count, this.getAmountByCategory().get(category));
                if (discont > maxDiscount) {
                    logger.info("Setting max discount");
                    maxDiscount = discont;
                }
            }
        }
        discountCampaign = maxDiscount;
        discountTotal = discountTotal + discountCampaign;
        return maxDiscount;
    }

    /**
     * This method give total amount categoy by category
     *
     * @return
     */
    public Map<Category, Double> getAmountByCategory() {
        Map<Category, Double> categoryPrice = new HashMap<>();
        for (Product product : itemList.keySet()) {
            if (Objects.nonNull(categoryPrice.get(product)))
                categoryPrice.put(product.getCategory(), categoryPrice.get(product) + product.getPrice());
            else {
                categoryPrice.put(product.getCategory(), product.getPrice());
            }
        }
        return itemList.keySet().stream().collect(Collectors.toMap(Product::getCategory, x -> x.getPrice() * itemList.get(x)));
    }

    public Double totalAmount() {
        return itemList.keySet().stream().mapToDouble(item -> itemList.get(item) * item.getPrice()).sum();
    }

    public Double getTotalAmountAfterDiscounts() {
        return (totalAmount() - discountTotal) + getDeliveryCost();
    }

    public Double getCouponDiscount() {
        return discountCoupon;
    }

    public Double getCampaignDiscount() {
        return discountCampaign;
    }

    public Double getDeliveryCost() {
        return this.deliveryCostCalculator.calculateFor(this.getAmountByCategory(), this.getItemList());
    }

    public Double applyCoupon(Coupon coupon) {
        discountCoupon = coupon.getDiscount(totalAmount() - this.getCampaignDiscount());
        discountTotal = discountTotal + discountCoupon;
        return discountCoupon;
    }

    public String print() {
        StringBuilder resultString = new StringBuilder();
        for (Category category : this.getAmountByCategory().keySet()) {
            resultString.append("Category: ").append(category.getTitle()).append(" \n");
            for (Product product : this.itemList.keySet().stream().filter(item -> item.getCategory().equals(category)).collect(Collectors.toList())) {
                resultString.append("Items:").append("\n");
                resultString.append("Product Name:").append(product.getTitle()).append("\n");
                resultString.append("Quantity:").append(itemList.get(product)).append("\n");
                resultString.append("Unit Price:").append(product.getPrice()).append("\n");
                resultString.append("Total Price:").append(product.getPrice() * itemList.get(product)).append("\n");

            }
        }
        resultString.append("Total Amount:").append(getTotalAmountAfterDiscounts()).append("\n");
        resultString.append("Delivery Cost:").append(this.getDeliveryCost());
        return resultString.toString();
    }
}
