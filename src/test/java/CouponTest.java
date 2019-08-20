import models.Coupon;
import models.DiscountType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CouponTest {

    private Coupon coupon;
    private Coupon couponAmount;
    private Coupon couponInvalid;

    @Before
    public void init() {
        coupon = new Coupon(100, 50, DiscountType.RATE);
        couponAmount = new Coupon(100, 10, DiscountType.AMOUNT);
        couponInvalid = new Coupon(100, 10, null);
    }

    @Test
    public void whenMinAmount100Rate50DiscountTypeRateGivenTotalAmount200_ExpectedDiscount100() {
        Double discount = coupon.getDiscount(200.0);
        Assert.assertEquals(new Double(100), discount);
    }

    @Test
    public void whenMinAmount100Rate50DiscountTypeRateGivenTotalAmount50_ExpectedDiscount0() {
        Double discount = coupon.getDiscount(50.0);
        Assert.assertEquals(new Double(0), discount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinAmount100Rate50DiscountTypeRateGivenTotalAmountNull_ExpectedIllegalArgumentException() {
        Double discount = coupon.getDiscount(null);
        Assert.assertEquals(new Double(0), discount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenMinAmount100Rate50DiscountTypeNullGivenTotalAmount150_ExpectedIllegalArgumentException() {
        Double discount = couponInvalid.getDiscount(150.0);
        Assert.assertEquals(new Double(0), discount);
    }

    @Test
    public void whenMinAmount100Rate50DiscountTypeAmountGivenTotalAmount150_ExpectedDiscount10() {
        Double discount = couponAmount.getDiscount(150.0);
        Assert.assertEquals(new Double(10.0), discount);
    }
}
