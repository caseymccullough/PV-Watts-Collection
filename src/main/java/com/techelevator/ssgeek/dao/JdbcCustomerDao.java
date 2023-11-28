package com.techelevator.ssgeek.dao;

import com.techelevator.ssgeek.exception.DaoException;
import com.techelevator.ssgeek.model.Customer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcCustomerDao implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcCustomerDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Customer getCustomerById(int customerId) {
        Customer customer = null;
        String sql = "SELECT customer_id, name, street_address1, street_address2, city, state, zip_code " +
                     "FROM customer " +
                     "WHERE customer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, customerId);
            if (results.next()) {
                customer = mapRowToCustomer(results);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, name, street_address1, street_address2, city, state, zip_code " +
                "FROM customer " +
                "ORDER BY customer_id";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Customer customer = mapRowToCustomer(results);
                customers.add(customer);
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return customers;
    }

    @Override
    public Customer createCustomer(Customer newCustomer) {
        Customer customer = null;
        String sql = "INSERT INTO customer (name, street_address1, street_address2, city, state, zip_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING customer_id;";
        try {
            int newId = jdbcTemplate.queryForObject(sql, int.class, newCustomer.getName(), newCustomer.getStreetAddress1(),
                    newCustomer.getStreetAddress2(), newCustomer.getCity(), newCustomer.getState(), newCustomer.getZipCode());
            customer = getCustomerById(newId);
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer updatedCustomer) {
        Customer customer = null;
        String sql = "UPDATE customer " +
                     "SET name = ?, street_address1 = ?, street_address2 = ?, city = ?, state = ?, zip_code = ? " +
                     "WHERE customer_id = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, updatedCustomer.getName(), updatedCustomer.getStreetAddress1(),
                    updatedCustomer.getStreetAddress2(), updatedCustomer.getCity(), updatedCustomer.getState(), updatedCustomer.getZipCode(),
                    updatedCustomer.getCustomerId());
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            customer = getCustomerById(updatedCustomer.getCustomerId());
        }
        catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return customer;
    }

    private Customer mapRowToCustomer(SqlRowSet results) {
        // Create a model object and fill in its properties with values from the result set.
        Customer customer = new Customer();
        customer.setCustomerId(results.getInt("customer_id"));
        customer.setName(results.getString("name"));
        customer.setStreetAddress1(results.getString("street_address1"));
        customer.setStreetAddress2(results.getString("street_address2"));
        customer.setCity(results.getString("city"));
        customer.setState(results.getString("state"));
        customer.setZipCode(results.getString("zip_code"));
        return customer;
    }
}
