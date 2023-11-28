package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class JdbcProductDaoTests extends BaseDaoTests {

    private static final Product PRODUCT_1 = new Product(1, "Product 1", "Description 1", new BigDecimal("9.99"), "product-1.png");
    private static final Product PRODUCT_2 = new Product(2, "Product 2", "Description 2", new BigDecimal("19.00"), "product-2.png");
    private static final Product PRODUCT_3 = new Product(3, "Product 3", "Description 3", new BigDecimal("123.45"), "product-3.png");
    private static final Product PRODUCT_4 = new Product(4, "Product 4", "Description 4", new BigDecimal("0.99"), "product-4.png");

    private JdbcProductDao productDao;
    private Product testProduct;

    @Before
    public void setup() {
        productDao = new JdbcProductDao(dataSource);
        testProduct = new Product(1, "Test Name", "Test Description", BigDecimal.valueOf(11.22), "test-image.png");
    }

    @Test
    public void getProductById_returns_correct_product_for_id() {
        Product product = productDao.getProductById(1);
        Assert.assertNotNull("getProductById returned null", product);
        assertProductsMatch("getProductById returned wrong or partial data", PRODUCT_1, product);

        product = productDao.getProductById(4);
        Assert.assertNotNull("getProductById returned null", product);
        assertProductsMatch("getProductById returned wrong or partial data", PRODUCT_4, product);
    }

    @Test
    public void getProductById_returns_null_when_id_not_found() {
        Product product = productDao.getProductById(99);
        Assert.assertNull("getProductById failed to return null for id not in database", product);
    }

    @Test
    public void getProducts_returns_list_of_all_products() {
        List<Product> products = productDao.getProducts();
        Assert.assertEquals("getProducts returned wrong number of products", 4, products.size());
        Assert.assertEquals("getProducts: first product id does not match expected, check sort order",
                PRODUCT_1.getProductId(), products.get(0).getProductId());
        assertProductsMatch("getProducts returned wrong or partial data", PRODUCT_1, products.get(0));
        assertProductsMatch("getProducts returned wrong or partial data", PRODUCT_2, products.get(1));
        assertProductsMatch("getProducts returned wrong or partial data", PRODUCT_3, products.get(2));
        assertProductsMatch("getProducts returned wrong or partial data", PRODUCT_4, products.get(3));
    }

    @Test
    public void getProductsWithNoSales_returns_list_of_products_not_sold() {
        List<Product> products = productDao.getProductsWithNoSales();
        Assert.assertEquals("getProductsWithNoSales returned wrong number of products", 1, products.size());
        assertProductsMatch("getProductsWithNoSales returned wrong or partial data", PRODUCT_3, products.get(0));
    }

    @Test
    public void createProduct_returns_product_with_id_and_expected_values() {
        Product createdProduct = productDao.createProduct(testProduct);

        Assert.assertNotNull("createProduct returned null", createdProduct);

        int newId = createdProduct.getProductId();
        Assert.assertTrue("createProduct failed to return a product with an id", newId > 0);

        testProduct.setProductId(newId);
        assertProductsMatch("createProduct returned product with wrong or partial data", testProduct, createdProduct);
    }

    @Test
    public void created_product_has_expected_values_when_retrieved() {
        Product createdProduct = productDao.createProduct(testProduct);

        int newId = createdProduct.getProductId();
        Product retrievedProduct = productDao.getProductById(newId);

        assertProductsMatch("createProduct did not save correct data in database", createdProduct, retrievedProduct);
    }

    @Test
    public void updated_product_has_expected_values_when_retrieved() {
        Product product = productDao.getProductById(1);
        product.setName("A new name");
        product.setDescription("A new description");
        product.setPrice(new BigDecimal("2222.22"));
        product.setImageName("A new image name");

        productDao.updateProduct(product);

        Product updatedProduct = productDao.getProductById(1);
        assertProductsMatch("updateProduct failed to update all fields in database", product, updatedProduct);
    }

    @Test
    public void deleted_product_cant_be_retrieved() {
        // NOTE: We must delete one that isn't on any order
        productDao.deleteProductById(3);

        Product product = productDao.getProductById(3);
        Assert.assertNull("deleteProductById failed to remove product from database", product);

        List<Product> products = productDao.getProducts();
        Assert.assertEquals("deleteProductById removed the wrong number of products", 3, products.size());
    }

    private void assertProductsMatch(String messagePrefix, Product expected, Product actual) {
        String message = messagePrefix + ": unexpected data in field '%s'.";
        Assert.assertEquals(String.format(message, "productId"), expected.getProductId(), actual.getProductId());
        Assert.assertEquals(String.format(message, "name"), expected.getName(), actual.getName());
        Assert.assertEquals(String.format(message, "price"), expected.getPrice(), actual.getPrice());
        Assert.assertEquals(String.format(message, "imageName"), expected.getImageName(), actual.getImageName());
    }
}
