package com.techelevator.pvwatts.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class JdbcLineItemDaoTests extends BaseDaoTests {

    private static final LineItem LI_1 = new LineItem(1, 1, 1, 1, "Product 1", new BigDecimal("9.99"));
    private static final LineItem LI_2 = new LineItem(2, 1, 2, 1, "Product 2", new BigDecimal("19.00"));
    private static final LineItem LI_3 = new LineItem(3, 1, 4, 1, "Product 4", new BigDecimal("0.99"));
    private static final LineItem LI_4 = new LineItem(4, 2, 4, 10, "Product 4", new BigDecimal("0.99"));
    private static final LineItem LI_5 = new LineItem(5, 2, 1, 10, "Product 1", new BigDecimal("9.99"));
    private static final LineItem LI_6 = new LineItem(6, 3, 1, 100, "Product 1", new BigDecimal("9.99"));

    private JdbcLineItemDao lineItemDao;

    @Before
    public void setup() {
        lineItemDao = new JdbcLineItemDao(dataSource);
    }

    @Test
    public void getLineItemsBySaleId_returns_correct_items_for_sale() {
        List<LineItem> lineItems = lineItemDao.getLineItemsBySaleId(1);
        Assert.assertEquals("getLineItemsBySaleId returned wrong number of sales", 3, lineItems.size());
        Assert.assertEquals("getLineItemsBySaleId: first line item id does not match expected, check sort order",
                LI_1.getLineItemId(), lineItems.get(0).getLineItemId());
        assertLineItemsMatch("getLineItemsBySaleId returned wrong or partial data", LI_1, lineItems.get(0));
        assertLineItemsMatch("getLineItemsBySaleId returned wrong or partial data", LI_2, lineItems.get(1));
        assertLineItemsMatch("getLineItemsBySaleId returned wrong or partial data", LI_3, lineItems.get(2));

        lineItems = lineItemDao.getLineItemsBySaleId(2);
        Assert.assertEquals("getLineItemsBySaleId returned wrong number of sales", 2, lineItems.size());
        Assert.assertEquals("getLineItemsBySaleId: first line item id does not match expected, check sort order",
                LI_4.getLineItemId(), lineItems.get(0).getLineItemId());
        assertLineItemsMatch("getLineItemsBySaleId returned wrong or partial data", LI_4, lineItems.get(0));
        assertLineItemsMatch("getLineItemsBySaleId returned wrong or partial data", LI_5, lineItems.get(1));

        lineItems = lineItemDao.getLineItemsBySaleId(3);
        Assert.assertEquals("getLineItemsBySaleId returned wrong number of sales", 1, lineItems.size());
        assertLineItemsMatch("getLineItemsBySaleId returned wrong or partial data", LI_6, lineItems.get(0));

        lineItems = lineItemDao.getLineItemsBySaleId(99);
        Assert.assertEquals("getLineItemsBySaleId returned wrong number of sales", 0, lineItems.size());

    }

    private void assertLineItemsMatch(String messagePrefix, LineItem expected, LineItem actual) {
        String message = messagePrefix + ": unexpected data in field '%s'.";
        Assert.assertEquals(String.format(message, "lineItemId"), expected.getLineItemId(), actual.getLineItemId());
        Assert.assertEquals(String.format(message, "saleId"), expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(String.format(message, "productId"), expected.getProductId(), actual.getProductId());
        Assert.assertEquals(String.format(message, "price"), expected.getPrice(), actual.getPrice());
        Assert.assertEquals(String.format(message, "productName"), expected.getProductName(), actual.getProductName());
        Assert.assertEquals(String.format(message, "quantity"), expected.getQuantity(), actual.getQuantity());
    }

}
