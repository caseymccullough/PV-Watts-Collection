package com.techelevator.pvwatts.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class JdbcSaleDaoTests extends BaseDaoTests {

    private static final Sale SALE_1 = new Sale(1, 1, LocalDate.parse("2022-01-01"), null, "Customer 1");
    private static final Sale SALE_2 = new Sale(2, 1, LocalDate.parse("2022-02-01"), LocalDate.parse("2022-02-02"), "Customer 1");
    private static final Sale SALE_3 = new Sale(3, 2, LocalDate.parse("2022-03-01"), null, "Customer 2");
    private static final Sale SALE_4 = new Sale(4, 2, LocalDate.parse("2022-01-01"), LocalDate.parse("2022-01-02"), "Customer 2");

    private JdbcSaleDao saleDao;
    private Sale testSale;
    
    @Before
    public void setup() {
        saleDao = new JdbcSaleDao(dataSource);
        testSale = new Sale(1, 1, LocalDate.now(), null, "Customer 1");
    }

    @Test
    public void getSaleById_returns_correct_sale_for_id() {
        Sale sale = saleDao.getSaleById(1);
        Assert.assertNotNull("getSaleById returned null", sale);
        assertSalesMatch("getSaleById returned wrong or partial data", SALE_1, sale);

        sale = saleDao.getSaleById(4);
        Assert.assertNotNull("getSaleById returned null", sale);
        assertSalesMatch("getSaleById returned wrong or partial data", SALE_4, sale);
    }

    @Test
    public void getSaleById_returns_null_when_id_not_found() {
        Sale sale = saleDao.getSaleById(99);
        Assert.assertNull("getSaleById failed to return null for id not in database", sale);
    }

    @Test
    public void getUnshippedSales_returns_sales_with_null_ship_date() {
        List<Sale> sales = saleDao.getUnshippedSales();
        Assert.assertEquals("getUnshippedSales returned wrong number of sales", 2, sales.size());
        Assert.assertEquals("getUnshippedSales: first sale id does not match expected, check sort order",
                SALE_1.getSaleId(), sales.get(0).getSaleId());
        assertSalesMatch("getUnshippedSales returned wrong or partial data", SALE_1, sales.get(0));
        assertSalesMatch("getUnshippedSales returned wrong or partial data", SALE_3, sales.get(1));
    }

    @Test
    public void getSalesByCustomerId_returns_list_of_all_sales_for_customer() {
        List<Sale> sales = saleDao.getSalesByCustomerId(1);
        Assert.assertEquals("getSalesByCustomerId returned wrong number of sales", 2, sales.size());
        Assert.assertEquals("getSalesByCustomerId: first sale id does not match expected, check sort order",
                SALE_1.getSaleId(), sales.get(0).getSaleId());
        assertSalesMatch("getSalesByCustomerId returned wrong or partial data", SALE_1, sales.get(0));
        assertSalesMatch("getSalesByCustomerId returned wrong or partial data", SALE_2, sales.get(1));

        sales = saleDao.getSalesByCustomerId(2);
        Assert.assertEquals("getSalesByCustomerId returned wrong number of sales", 2, sales.size());
        Assert.assertEquals("getSalesByCustomerId: first sale id does not match expected, check sort order",
                SALE_3.getSaleId(), sales.get(0).getSaleId());
        assertSalesMatch("getSalesByCustomerId returned wrong or partial data", SALE_3, sales.get(0));
        assertSalesMatch("getSalesByCustomerId returned wrong or partial data", SALE_4, sales.get(1));

        sales = saleDao.getSalesByCustomerId(3);
        Assert.assertEquals("getSalesByCustomerId should return 0 sales for customer 3", 0, sales.size());
    }

    @Test
    public void getSalesByProductId_returns_list_of_all_sales_for_product() {
        List<Sale> sales = saleDao.getSalesByProductId(1);
        Assert.assertEquals("getSalesByProductId returned wrong number of sales", 3, sales.size());
        Assert.assertEquals("getSalesByProductId: first sale id does not match expected, check sort order",
                SALE_1.getSaleId(), sales.get(0).getSaleId());
        assertSalesMatch("getSalesByProductId returned wrong or partial data", SALE_1, sales.get(0));
        assertSalesMatch("getSalesByProductId returned wrong or partial data", SALE_2, sales.get(1));
        assertSalesMatch("getSalesByProductId returned wrong or partial data", SALE_3, sales.get(2));

        sales = saleDao.getSalesByProductId(2);
        Assert.assertEquals("getSalesByProductId returned wrong number of sales", 1, sales.size());
        assertSalesMatch("getSalesByProductId returned wrong or partial data", SALE_1, sales.get(0));

        sales = saleDao.getSalesByProductId(3);
        Assert.assertEquals("getSalesByProductId should return 0 sales for product 3", 0, sales.size());
    }

    @Test
    public void createSale_returns_sale_with_id_and_expected_values() {
        Sale createdSale = saleDao.createSale(testSale);

        Assert.assertNotNull("createSale returned null", createdSale);

        int newId = createdSale.getSaleId();
        Assert.assertTrue("createSale failed to return a sale with an id", newId > 0);

        testSale.setSaleId(newId);
        assertSalesMatch("createSale returned sale with wrong or partial data", testSale, createdSale);
    }

    @Test
    public void created_sale_has_expected_values_when_retrieved() {
        Sale createdSale = saleDao.createSale(testSale);

        int newId = createdSale.getSaleId();
        Sale retrievedSale = saleDao.getSaleById(newId);

        assertSalesMatch("createSale did not save correct data in database", createdSale, retrievedSale);
    }

    @Test
    public void updated_sale_has_expected_values_when_retrieved() {
        Sale sale = saleDao.getSaleById(1);
        sale.setCustomerId(2);
        sale.setCustomerName("Customer 2");
        sale.setSaleDate(LocalDate.now());
        sale.setShipDate(LocalDate.now());

        saleDao.updateSale(sale);

        Sale updatedSale = saleDao.getSaleById(1);
        assertSalesMatch("updateSale failed to update all fields in database", sale, updatedSale);
    }

    @Test
    public void deleted_sale_cant_be_retrieved() {
        // NOTE: We must delete one that has no line items
        saleDao.deleteSaleById(4);

        Sale sale = saleDao.getSaleById(4);
        Assert.assertNull("deleteSaleById failed to remove sale from database", sale);

        List<Sale> sales = saleDao.getSalesByCustomerId(2);
        Assert.assertEquals("deleteSaleById removed the wrong number of sales", 1, sales.size());
    }

    private void assertSalesMatch(String messagePrefix, Sale expected, Sale actual) {
        String message = messagePrefix + ": unexpected data in field '%s'.";
        Assert.assertEquals(String.format(message, "saleId"), expected.getSaleId(), actual.getSaleId());
        Assert.assertEquals(String.format(message, "customerId"), expected.getCustomerId(), actual.getCustomerId());
        Assert.assertEquals(String.format(message, "saleDate"), expected.getSaleDate(), actual.getSaleDate());
        Assert.assertEquals(String.format(message, "shipDate"), expected.getShipDate(), actual.getShipDate());
        Assert.assertEquals(String.format(message, "customerName"), expected.getCustomerName(), actual.getCustomerName());

    }

}
