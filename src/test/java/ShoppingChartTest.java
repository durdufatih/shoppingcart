import models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShoppingChartTest {

    private ShoppingCart shoppingCart;
    private Category food;
    private Category pens;
    private Product apple;
    private Product almond;
    private Product pencil;

    @Before
    public void init() {
        food = new Category(null, "food");
        pens = new Category(null, "pens");
        apple = new Product("apple", 100.0, food);
        pencil = new Product("pencil", 300.0, pens);
        almond = new Product("almond", 150.0, food);
        shoppingCart = new ShoppingCart(new DeliveryCostCalculator(2.0, 3.0));
    }

    @Test
    public void whenAddedOneItem_WaitListCountIsOne() {
        shoppingCart.addItem(apple, 3);
        Assert.assertEquals(shoppingCart.getItemList().size(), 1);
    }

    @Test
    public void whenAddedAlmondItem_WaitedSizeOfListEqualOne() {
        shoppingCart.addItem(almond, 1);
        Assert.assertEquals(shoppingCart.getItemList().size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddedNullProductItem_ExpectedIllegalArgumentException_AtAddItemMethod() {
        shoppingCart.addItem(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddedZeroQuantity_ExpectedIllegalArgumentException_AtAddItemMethod() {
        shoppingCart.addItem(almond, 0);
    }

    @Test
    public void whenGivenNoCampaignExpectedDiscountZero() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        shoppingCart.addItem(pencil, 5);
        Double totalDiscount = shoppingCart.applyDiscounts();
        Assert.assertEquals(new Double(0), totalDiscount);
    }

    @Test
    public void whenGivenCamgainFoodCategoryRate50Auantity5DiscountRateExpectedResultTrue() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        Campaign campaign1 = new Campaign(food, 50.0, 5, DiscountType.RATE);
        Double totalDiscount = shoppingCart.applyDiscounts(campaign1);
        Assert.assertEquals(new Double(375.0), totalDiscount);

    }

    @Test
    public void whenGivenCamgainFoodCategoryRate50Auantity5DiscountAmountExpectedResultTrue() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.AMOUNT);
        Double totalDiscount = shoppingCart.applyDiscounts(campaign3);
        Assert.assertEquals(new Double(5), totalDiscount);

    }

    @Test
    public void whenGivenTwoCamgainFoodCategoryDiscountRateAndAmountSameTimeExpectedResultTrue() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        Campaign campaign1 = new Campaign(food, 50.0, 5, DiscountType.RATE);
        Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.AMOUNT);
        Double totalDiscount = shoppingCart.applyDiscounts(campaign1, campaign3);
        Assert.assertEquals(new Double(375.0), totalDiscount);

    }

    @Test
    public void getApply() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        shoppingCart.addItem(pencil, 5);
        Campaign campaign1 = new Campaign(food, 50.0, 5, DiscountType.RATE);
        Campaign campaign2 = new Campaign(food, 20.0, 3, DiscountType.RATE);
        Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.AMOUNT);
        Campaign campaign4 = new Campaign(pens, 50.0, 5, DiscountType.RATE);
        Campaign campaign5 = new Campaign(pens, 20.0, 3, DiscountType.RATE);
        Campaign campaign6 = new Campaign(pens, 5.0, 5, DiscountType.AMOUNT);
        shoppingCart.applyDiscounts(campaign1, campaign2, campaign3, campaign4, campaign5, campaign6);
    }

    @Test
    public void whenGiven3CampaignAndOneCoupen_ExpectedTrue() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        shoppingCart.addItem(pencil, 5);
        Assert.assertEquals(new Double(2250.0), shoppingCart.totalAmount());
        Campaign campaign1 = new Campaign(food, 50.0, 5, DiscountType.RATE);
        Campaign campaign5 = new Campaign(pens, 20.0, 3, DiscountType.RATE);
        shoppingCart.applyDiscounts(campaign1, campaign5);
        Assert.assertEquals(new Double(375.0), shoppingCart.getCampaignDiscount());
        Coupon coupon = new Coupon(100, 10, DiscountType.RATE);
        Assert.assertEquals(new Double(187.5), shoppingCart.applyCoupon(coupon));
    }

    @Test
    public void whenGiven3CampaignAndOneCoupen_ExpectedTotalDiscountIsTrue() {
        shoppingCart.addItem(almond, 2);
        shoppingCart.addItem(almond, 3);
        shoppingCart.addItem(pencil, 5);
        Campaign campaign1 = new Campaign(food, 50.0, 5, DiscountType.RATE);
        Campaign campaign5 = new Campaign(pens, 20.0, 3, DiscountType.RATE);
        shoppingCart.applyDiscounts(campaign1, campaign5);
        Coupon coupon = new Coupon(100, 10, DiscountType.RATE);
        shoppingCart.applyCoupon(coupon);
        Assert.assertEquals(new Double(187.5), shoppingCart.getCouponDiscount());
        Assert.assertEquals(new Double((2250 - 375 - 187.5 + shoppingCart.getDeliveryCost())), shoppingCart.getTotalAmountAfterDiscounts());
        System.out.println(shoppingCart.print());


    }

    @Test
    public void whenGivenOneElementOneCategory_ExpectedValue7_99() {
        shoppingCart.addItem(apple, 1);
        Double cost = shoppingCart.getDeliveryCost();
        Assert.assertEquals(new Double(7.99), cost);
    }
}
