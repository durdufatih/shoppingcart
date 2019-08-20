import models.Category;
import models.DeliveryCostCalculator;
import models.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DeliverCostCalculatorTest {

    DeliveryCostCalculator deliveryCostCalculator;
    Map<Product, Integer> productIntegerMap;
    Map<Category, Double> categoryMap;
    private Category category;
    private Product product;


    @Before
    public void init() {

        deliveryCostCalculator = new DeliveryCostCalculator(2.0, 3.0);
        productIntegerMap = new HashMap<>();
        categoryMap = new HashMap<>();
        category = new Category(null, "food");
        product = new Product("Apple", 50.0, category);
        productIntegerMap.put(product, 1);
        categoryMap.put(product.getCategory(), 1.0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGivenNullAllParams_ExpectedIllegalArgumentException() {
        deliveryCostCalculator.calculateFor(null, null);
    }

    @Test
    public void whenGivenOneElementOneCategory_ExpectedValue7_99() {
        Double cost = deliveryCostCalculator.calculateFor(categoryMap, productIntegerMap);
        Assert.assertEquals(new Double(7.99), cost);
    }
}
