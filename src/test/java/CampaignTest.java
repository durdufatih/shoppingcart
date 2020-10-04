import models.Campaign;
import models.Category;
import models.DiscountType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CampaignTest {

    private Category food;
    private Category pens;
    private Campaign campaignRate;
    private Campaign campaignAmount;

    @Before
    public void init() {
        food = new Category(null, "food");
        pens = new Category(null, "pens");
        campaignRate = new Campaign(food, 20.0, 4, DiscountType.RATE);
        campaignAmount = new Campaign(food, 10.0, 4, DiscountType.AMOUNT);
    }

    @Test
    public void whenGivenValidCategoryAmount100AndQuantity4_ExpectedDiscount20() {
        Double discount = campaignRate.discount(food, 4, 100.0);
        Assert.assertEquals(new Double(20.0), discount);
    }

    @Test
    public void whenGivenNonSuitableCategoryAmount100AndQuantity4_ExpectedDiscount0() {
        Double discount = campaignRate.discount(pens, 4, 100.0);
        Assert.assertEquals(new Double(0.0), discount);
    }

    @Test
    public void whenGivenSuitableCategoryAmount100AndQuantity4DiscountRateIsAmountRate10_ExpectedDiscount10() {
        Double discount = campaignAmount.discount(food, 4, 100.0);
        Assert.assertEquals(new Double(10.0), discount);
    }

    @Test
    public void whenGivenNonSuitableCategoryAmount100AndQuantity4DiscountRateIsAmountRate10_ExpectedDiscount0() {
        Double discount = campaignAmount.discount(pens, 4, 100.0);
        Assert.assertEquals(new Double(0.0), discount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGivenNullCategoryAndNullAmountAndNullQuantityDiscountRateIsAmountRate10_ExpectedIllegalArgumentException() {
        Double discount = campaignAmount.discount(null, null, null);
        Assert.assertEquals(new Double(0.0), discount);
    }

    @Test
    public void whenGivenCategorySuitableAndAmount100AndQuantityLessThenValidQuantity_ExpectedDiscount0() {
        Double discount = campaignRate.discount(food, 2, 100.0);
        Assert.assertEquals(new Double(0.0), discount);
    }


}
