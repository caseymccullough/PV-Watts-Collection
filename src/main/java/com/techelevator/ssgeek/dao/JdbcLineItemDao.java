package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcLineItemDao implements LineItemDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcLineItemDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<LineItem> getLineItemsBySaleId(int saleId) {
        List<LineItem> lineItems = new ArrayList<>();
        String sql = "SELECT line_item_id, sale_id, li.product_id, quantity, p.name AS product_name, p.price " +
                "FROM line_item li " +
                "JOIN product p on li.product_id = p.product_id " +
                "WHERE sale_id = ? " +
                "ORDER BY li.line_item_id";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, saleId);
            while (results.next()) {
                LineItem lineItem = mapRowToLineItem(results);
                lineItems.add(lineItem);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return lineItems;
    }

    private LineItem mapRowToLineItem(SqlRowSet results) {
        // Create a model object and fill in its properties with values from the result set.
        LineItem lineItem = new LineItem();
        lineItem.setLineItemId(results.getInt("line_item_id"));
        lineItem.setSaleId(results.getInt("sale_id"));
        lineItem.setProductId(results.getInt("product_id"));
        lineItem.setQuantity(results.getInt("quantity"));
        lineItem.setProductName(results.getString("product_name"));
        lineItem.setPrice(results.getBigDecimal("price"));
        return lineItem;
    }
}
